package com.syt.jabx.kernel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXFixData;
import com.syt.jabx.bean.JABXSocketParam;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * ABus Socket連線,輸入及輸出處理之類別
 * @author Jason
 *
 */
final class ZJABXSocket extends IJABXParseBase {

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * 應用程序核心管理物件
	 */
	private JABXKernel jabxKernel;

	/**
	 * 結果分派處理物件
	 */
	private ZJABXResultProc resultProc;

    /**
     * A thread to read data from Socket
     */
    private Thread readSocketThread;    
    
	/**
	 * Socket物件
	 */
	private Socket socket;
	
	/**
	 * DataInput之串流
	 */
	private DataInputStream istm;
	
	/**
	 * DataOutput之串流
	 */
	private DataOutputStream ostm;

	/**
	 * CheckSum使用之Lock
	 */
	private byte[] checkSumLock = new byte[0];

    /**
     * Session是否連線中
     */
	private volatile boolean isConnecting;

    /**
     * 執行緒之執行模式
     */
	private volatile byte currentRunMode = JABXConst.STOP_MODE;

	/**
	 * 數據上傳或下載的時間
	 */
	private long dataUploadOrDownloadTime = System.currentTimeMillis();

	/**
	 * 存放ZJABXSocketParam之BlockingQueue
	 */
	private BlockingQueue<JABXSocketParam> paramQueue;

	/**
	 * 執行Socket傳送Thread之旗標
	 */
	private volatile boolean isRunParamFlag = true;

	/**
	 * A thread to send param to Socket
	 */
	private Thread sendParamThread;

	/**
	 * 伺服器項目物件
	 */
	private ZJABXServerItem serverItem;

	/**
	 * 是否為第一筆R00資料
	 */
	private boolean isFirstR00;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 * @param serverItem - IJABXServerItem
	 */
	public ZJABXSocket(JABXKernel jabxKernel, IJABXLog jabxLog, ZJABXServerItem serverItem) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
		this.resultProc = jabxKernel.getResultProc();
		this.serverItem = serverItem;
		paramQueue = new LinkedBlockingQueue<JABXSocketParam>();
		isFirstR00 = true;
		this.serverItem.isBlockingQueue = false;
	}

	/**
	 * Constructor(提供ZJABXChannelSocket使用)
	 * @param jabxLog - IJABXLog
	 * @param serverItem - ZJABXServerItem
	 */
	public ZJABXSocket(IJABXLog jabxLog, ZJABXServerItem serverItem) {
		this.jabxLog = jabxLog;
		this.serverItem = serverItem;
	}

	/**
	 * 取得伺服器項目物件
	 * @return ZJABXServerItem
	 */
	ZJABXServerItem getServerItem() {
		return serverItem;
	}

	/**
	 * Socket連線
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws Exception
	 */
	void connect() throws UnknownHostException, IOException, Exception {
		try {
			isConnecting = false;
			socket = new Socket(serverItem.getHostIP(), serverItem.getHostPort());
			socket.setSoTimeout(serverItem.getNtimeout() * 1000);
			// 取得Socket輸入串流
			istm = new DataInputStream(socket.getInputStream());
			// 取得Socket輸出串流        
			ostm = new DataOutputStream(socket.getOutputStream());
			jabxLog.outputInfoAndLog("ZJABXSocket.connect()", serverItem.getIPAndPort());
			isConnecting = true;
		}catch(UnknownHostException e) {
			jabxLog.outputErrorAndLog("ZJABXSocket.connect()", e.getMessage());
			throw e;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("ZJABXSocket.connect()", e.getMessage());
			throw e;
		}catch(Exception e) {
			jabxLog.outputErrorAndLog("ZJABXSocket.connect()", e.getMessage());
			throw e;
		}
	}

	/**
	 * 登入ABus
	 */
	void login() {
		new LoginThread().start();
	}
	
	/**
	 * 用戶登入之Thread
	 * @author Jason
	 *
	 */
	private class LoginThread extends Thread {

		/**
		 * Constructor
		 */
		public LoginThread() {
		}
		
		/**
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub

			JSONObject loginInfo = jabxKernel.getLoginInfo();
			
			String info = String.format("IP:Port->%s,UserIP:%s", serverItem.getIPAndPort(), loginInfo.get(JSConst.LN_LOGINIP));
			jabxLog.outputInfoAndLog("LoginThread.run()", info);
			info = null;
			
			int nErrCode = JABXErrCode.NO_ERROR;
			try {
				connect();
				addInformationPacket(jabxKernel, JABXConst.ABXFUN_SESSION, JABXConst.ABXMSG_ONCONNECTION, serverItem.getIPAndPort(), JABXErrCode.NO_ERROR);//Socket連線成功
				
				//登入身份驗證	
				JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
				//設定訊息主代碼
				ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
				//設定訊息次代碼
				ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_LOGON);
				//500-01-Begin: 設定abyKey			
				JABXAbyKeyCtrl abyKey = new JABXAbyKeyCtrl();
				abyKey.setAbyFuncCode("U00");
				abyKey.setDwUserGWID(loginInfo.getInt(JSConst.LN_USERGWID));
				abyKey.setByProductID((byte)loginInfo.getInt(JSConst.LN_PRODUCTID));
				abyKey.setByPlatformID((byte)loginInfo.getInt(JSConst.LN_PLATFORMID));
				abyKey.setByChannelNo(serverItem.getChannelNo());
				abyKey.setBySecurityKey(JABXConst.INITIAL_SECURITYKEY);
				abyKey.setByAPCode((short)0);
				ctrlHeader.setAbyKey(abyKey);
				abyKey = null;
				//500-01-End.			
				ctrlHeader.setByCompressType(JABXConst.ABUS_COMPRESS_NULL);//設定資料壓縮方式

				//記錄查詢參數之List
				List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
				//500-02-Begin: 設定查詢參數
				JABXFixData fixData = new JABXFixData();
				fixData.setTag("1");//用戶類型
				fixData.setValue(String.valueOf(loginInfo.getInt(JSConst.LN_USERTYPE)));
				queryList.add(fixData);
				
				fixData = new JABXFixData();
				fixData.setTag("2");//登入方式
				fixData.setValue(String.valueOf(loginInfo.getInt(JSConst.LN_LOGINTYPE)));
				queryList.add(fixData);
				
				fixData = new JABXFixData();
				fixData.setTag("3");//登入資料
				fixData.setValue((String)loginInfo.get(JSConst.LN_LOGINDATA));
				queryList.add(fixData);
				
				fixData = new JABXFixData();
				fixData.setTag("4");//登入密碼
				byte userType = (byte)loginInfo.getInt(JSConst.LN_USERTYPE);
				byte loginType = (byte)loginInfo.getInt(JSConst.LN_LOGINTYPE);
				byte pwdEncoding = (byte)loginInfo.getInt(JSConst.LN_PWDENCODING);
				if (userType == 0x20 || userType == 255 && loginType == 1) {
					fixData.setValue((String)loginInfo.get(JSConst.LN_PASSWORD));// 證券用戶不加密
					pwdEncoding = JABXConst.ABGW_ENCODE_NONE;
				}else {
					fixData.setValue(jabxKernel.md5((String)loginInfo.get(JSConst.LN_PASSWORD)));
					pwdEncoding = JABXConst.ABGW_ENCODE_MD5;
				}
				queryList.add(fixData);
				
				fixData = new JABXFixData();
				fixData.setTag("5");//密碼編碼方式
				fixData.setValue(String.valueOf(pwdEncoding));			
				queryList.add(fixData);
				
				fixData = new JABXFixData();
				fixData.setTag("6");//登入IP
				fixData.setValue((String)loginInfo.get(JSConst.LN_LOGINIP));
				queryList.add(fixData);

				fixData = new JABXFixData();
				fixData.setTag("7");//資料編碼方式
				fixData.setValue(String.valueOf(loginInfo.getInt(JSConst.LN_COMPRESSTYPE)));			
				queryList.add(fixData);

				fixData = null;
				//500-02-End.
				
				//將查詢參數轉換成byte[]
				byte[] queryAry = getBytesFromListFixData(queryList);
				queryList = null;

				//輸出串流
				JABXSocketParam sparam = new JABXSocketParam("U00", ctrlHeader, queryAry);
				outputData(sparam);
				sparam = null;
				
				ctrlHeader = null;
				queryAry = null;
			}catch(UnknownHostException e) {
				nErrCode = JABXErrCode.ABUS_UNKONWNHOST_ERROR;
				jabxLog.outputErrorAndLog("ZJABXSocket.LoginThread.run()", e.getMessage());
				e.printStackTrace();
			}catch(IOException e) {
				nErrCode = JABXErrCode.ABUS_SOCKET_ERROR;
				jabxLog.outputErrorAndLog("ZJABXSocket.LoginThread.run()", e.getMessage());
				e.printStackTrace();
			}catch(NullPointerException e) {
				nErrCode = JABXErrCode.NULL_POINTER;
				jabxLog.outputErrorAndLog("ZJABXSocket.LoginThread.run()", e.getMessage());
				e.printStackTrace();
			}catch(Exception e) {
				nErrCode = JABXErrCode.UNKNOWN_ERROR;
				jabxLog.outputErrorAndLog("ZJABXSocket.LoginThread.run()", e.getMessage());
				e.printStackTrace();
			}

			if (nErrCode == JABXErrCode.NO_ERROR) {
				currentRunMode = JABXConst.ASYNC_MODE;
				readSocketThread = new Thread(new ReadSocketDataThread());
				readSocketThread.start();
				sendParamThread = new Thread(new SendParamThread());
				sendParamThread.start();
			}else {
				addInformationPacket(jabxKernel, JABXConst.ABXFUN_SESSION, JABXConst.ABXMSG_LOGIN, serverItem.getIPAndPort(), nErrCode);
			}

			ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
			if (soObj != null) {
				soObj.setConnectStatus(serverItem.getIPAndPort(), isConnecting);
			}
			serverItem.setConnectStatus(isConnecting);
			
			loginInfo = null;			
		}
	}

	/**
	 * 輸出Socket數據
	 * @param sparam - ZJABXSocketParam
	 */
	public void outputDataByParam(JABXSocketParam sparam) {
		//synchronized(paramQueueLock) {
		synchronized (serverItem.socketParamLock) {
			this.putParamQueue(sparam);
			serverItem.socketParamLock.notifyAll();
		}
	}

	/**
	 * CheckSum運算
	 * @param b - byte(Source)
	 * @param bAry - byte[]
	 * @return byte
	 */
	public byte calcCheckSum(byte b, byte[] bAry) {
		synchronized(checkSumLock) {
			if (bAry != null) {
				byte tmp = b;
				for (int i = 0;i < bAry.length;i++) {
					tmp ^= bAry[i];
				}
				return tmp;
			}else {
				return b;
			}
		}
	}

	/**
	 * 停止thisThread
	 */
	void stopAllThread() {
		if (readSocketThread != null) {
			//readSocketThread.interrupt();
			currentRunMode = JABXConst.STOP_MODE;
		}
		if (sendParamThread != null) {
			isRunParamFlag = false;
			paramQueue.clear();
			synchronized (serverItem.socketParamLock) {
				serverItem.socketParamLock.notifyAll();
			}
		}
	}

	/**
	 * 關閉Socket
	 */
	void closeStreamAndSocket() {
		if (istm != null) {
			try {
				istm.close();
			}catch(IOException e) {
				jabxLog.outputErrorAndLog("ZJABXSocket.closeStreamAndSocket()", e.getMessage());
			}
			istm = null;
		}
		if (ostm != null) {
			try {
				ostm.close();
			}catch(IOException e) {
				jabxLog.outputErrorAndLog("ZJABXSocket.closeStreamAndSocket()", e.getMessage());
			}
			ostm = null;
		}
		if (socket != null) {
			try {
				socket.close();
			}catch(IOException e) {
				jabxLog.outputErrorAndLog("ZJABXSocket.closeStreamAndSocket()", e.getMessage());
			}
			socket = null;
		}
	}

	/**
	 * 輸出登出數據
	 * @throws IOException
	 */
	private void writeLogout(long flowSum) throws Exception {
		if (!isConnecting) {
			return;
		}
		JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();
		ctrlHeader.setLlSeqNo(0);
		ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
		ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_LOGOUT);
		JSONObject loginInfo = jabxKernel.getLoginInfo();
		//500-01-Begin: 設定abyKey
		JABXAbyKeyCtrl abyKey = new JABXAbyKeyCtrl();
		abyKey.setAbyFuncCode("U01");
		abyKey.setDwUserGWID(loginInfo.getInt(JSConst.LN_USERGWID));
		abyKey.setByProductID((byte)loginInfo.getInt(JSConst.LN_PRODUCTID));
		abyKey.setByPlatformID((byte)loginInfo.getInt(JSConst.LN_PLATFORMID));
		abyKey.setByChannelNo(serverItem.getChannelNo());
		abyKey.setBySecurityKey(serverItem.getSecurityKey().byteValue());
		abyKey.setByAPCode((short)0);		
		ctrlHeader.setAbyKey(abyKey);
		abyKey = null;
		loginInfo = null;
		//500-01-End.
		ctrlHeader.setByCompressType(JABXConst.ABUS_COMPRESS_NULL);//設定資料壓縮方式
		
		//記錄查詢參數之List
		List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
		//500-02-Begin: 設定查詢參數
		JABXFixData fixData = new JABXFixData();
		fixData.setTag("1");//錯誤代碼
		fixData.setValue(String.valueOf(flowSum));
		queryList.add(fixData);
		
		fixData = null;
		//500-02-End.
		
		//將查詢參數轉換成byte[]
		byte[] queryAry = getBytesFromListFixData(queryList);
		queryList = null;

		//輸出串流
		JABXSocketParam sparam = new JABXSocketParam("U01", ctrlHeader, queryAry);
		outputData(sparam);
		sparam = null;
		
		ctrlHeader = null;
		queryAry = null;
	}

	/**
	 * 輸出Heartbit
	 * @param errorCode - int(錯誤碼)
	 * @throws IOException
	 */
	private void writeHeartbit(int errorCode) throws Exception {
		if (!isConnecting) {
			return;
		}
		JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();
		ctrlHeader.setLlSeqNo(0);
		ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
		ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_HEARTBIT);
		//500-01-Begin: 設定abyKey
		JABXAbyKeyCtrl abyKey = new JABXAbyKeyCtrl();//Heartbit之abyKey全為0
		abyKey.setAbyFuncCode("0000");
		JSONObject user = jabxKernel.getLoginInfo();
		if (user != null) {
			abyKey.setDwUserGWID((int)user.getInt(JSConst.LN_USERGWID));
		}
		user = null;
		abyKey.setByProductID((byte)0);
		abyKey.setByPlatformID((byte)0);
		abyKey.setByChannelNo((byte)0);
		abyKey.setBySecurityKey((byte)0);
		abyKey.setByAPCode((short)0);		
		ctrlHeader.setAbyKey(abyKey);
		abyKey = null;
		//500-01-End.
		ctrlHeader.setByCompressType(JABXConst.ABUS_COMPRESS_NULL);//設定資料壓縮方式
		
		//記錄查詢參數之List
		List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
		//500-02-Begin: 設定查詢參數
		JABXFixData fixData = new JABXFixData();
		fixData.setTag("1");//錯誤代碼
		fixData.setValue(String.valueOf(errorCode));
		queryList.add(fixData);
		fixData = new JABXFixData();
		fixData.setTag("2");//錯誤訊息
		fixData.setValue("");
		queryList.add(fixData);
		fixData = new JABXFixData();
		fixData.setTag("3");//日期時間
		fixData.setValue(jabxKernel.getNowDateByFormat("yyyyMMddHHmmss"));
		queryList.add(fixData);
		fixData = null;
		//500-02-End.
		
		//將查詢參數轉換成byte[]
		byte[] queryAry = getBytesFromListFixData(queryList);
		queryList = null;

		//輸出至串流
		JABXSocketParam sparam = new JABXSocketParam("0000", ctrlHeader, queryAry);
		outputData(sparam);
		sparam = null;

		ctrlHeader = null;
		queryAry = null;
	}

	/**
	 * 用戶登出
	 */
	void logout(long flowSum) {
		
		if (isConnecting) {
			try {
				// 輸出Logout訊息
				writeLogout(flowSum);
			}catch(Exception e) {
				jabxLog.outputErrorAndLog("ZJABXSocket.logout()", e.getMessage());
				addInformationPacket(jabxKernel, JABXConst.ABXFUN_SESSION, JABXConst.ABXMSG_LOGOUT, serverItem.getIPAndPort(), JABXErrCode.IO_ERROR);
			}
		}

		destroy();
	}

	/**
	 * 銷毀物件
	 */
	void destroy() {
		// 停止socket的Thread
		stopAllThread();
		try {
			Thread.sleep(JABXKernel.WAIT_THREAD_STOP_TIME);
		}catch(InterruptedException e) {
			jabxLog.outputInfoAndLog("ZJABXSocket.destroy()", e.getMessage());
		}

		readSocketThread = null;
		sendParamThread = null;

		// 關閉串流及Socket
		closeStreamAndSocket();
		
		isConnecting = false;
		
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		if (soObj != null) {
			soObj.setConnectStatus(serverItem.getIPAndPort(), isConnecting);
		}
		serverItem.setConnectStatus(isConnecting);
	}

	/**
	 * 自Socket連線輸出數據
	 * @param sparam - ZJABXSocketParam
	 * @throws IOException
	 */
	void outputData(JABXSocketParam sparam) throws Exception {
		if (isConnecting == false) {
			return;
		}

		synchronized (ostm) {
			if (jabxLog == null) {
				return;
			}

			String funcCode = sparam.getFuncCode();
			JABXCtrlHeader ctrlHeader = sparam.getCtrlHeader();
			JABXAbyKeyCtrl abyKey = (JABXAbyKeyCtrl)ctrlHeader.getAbyKey();
			byte[] queryAry = sparam.getQueryAry();
			// 設定查詢參數之資料長度
			ctrlHeader.setDwDataLen(queryAry.length);
			// 設定abyKey之channelNo
			abyKey.setByChannelNo(serverItem.getChannelNo());
			// 設定abyKey之SecurityKey
			abyKey.setBySecurityKey(serverItem.getSecurityKey().byteValue());
			// 計算CheckSum
			byte checkSum = ctrlHeader.calcCheckSum();
			checkSum = calcCheckSum(checkSum, queryAry);

			// 500-01-Begin: 輸出ControlHeader數據
			ostm.writeByte(ctrlHeader.getByLeadCode());//起始碼
			ostm.writeLong(ctrlHeader.getLlSeqNo());//序號
			ostm.writeByte(ctrlHeader.getByMsgMainType());//訊息主代碼
			ostm.writeInt(ctrlHeader.getByMsgSubType());//訊息次代碼
			// 500-01-01-Begin: 輸出abykey數據
			ostm.write(abyKey.getAbyFuncCodeBytes());//功能代碼
			ostm.writeInt(abyKey.getDwUserGWID());//用戶GW帳號
			ostm.writeByte(abyKey.getByProductID());//產品代碼
			ostm.writeByte(abyKey.getByPlatformID());//產品平台代碼
			ostm.writeByte(abyKey.getByChannelNo());//GW連線序號
			ostm.writeByte(abyKey.getBySecurityKey());//安全碼
			ostm.write(abyKey.getByReserve());//保留
			ostm.writeShort(abyKey.getByAPCode());//AP專用 
			abyKey = null;
			// 500-01-01-End.
			ostm.writeByte(ctrlHeader.getByCompressType());//設定資料壓縮方式
			ostm.writeInt(ctrlHeader.getDwDataLen());// 資料長度
			// 500-01-End.

			// 輸出查詢參數數據
			if (queryAry != null) {
				ostm.write(queryAry);
			}
			// 輸出CheckSum數據
			ostm.writeByte(checkSum);

			jabxLog.outputInfoAndLog(String.format("%s->%s", funcCode, serverItem.getIPAndPort()), String.format("CtrlHeader->%s", ctrlHeader.toString()));
			jabxLog.outputInfoAndLog(String.format("%s->%s", funcCode, serverItem.getIPAndPort()), String.format("QueryData->%s", new String(queryAry)));
		}
	}

	/**
	 * 自Socket輸入串流讀取資料
	 * @param nSize - int(資料長度)
	 * @param result - JSONObject
	 * @return byte[]
	 */
	private byte[] readData(int nSize, final JSONObject result) {
		if (nSize == 0) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.NODATA_ERROR);
			return null;
		}
		byte[] abyAry = null;
		
		try {
			abyAry = new byte[nSize];
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			jabxLog.outputErrorAndLog("ZJABXSocket.readData(int, ZJABXResult)", e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		int nRead = 0, byteRead;
		try {
			while (nRead < nSize) {
				byteRead = istm.read(abyAry, nRead, nSize - nRead);
				if (byteRead == -1) {
					break;
				}
				nRead += byteRead;
			}
		}catch(IOException e) {
    		result.put(JS_Result.ERR_CODE, JABXErrCode.IO_ERROR);
    		jabxLog.outputErrorAndLog("ZJABXSocket.readData(int, ZJABXResult)", e.getMessage());
    		e.printStackTrace();
    		return null;			
		}
		
		return abyAry;
	}

	/**
	 * 讀取Socket封包資料
	 * @throws IOException
	 */
	private void readRtPacket() throws IOException, OutOfMemoryError {
		if (istm == null) {
			return;
		}
		synchronized(istm) {
			try {
				if (istm.available() == 0) {
					return;
				}
				ZJABXPacket streamPkt = new ZJABXPacket();
				JSONObject result = new JSONObject();
				result.put(JS_Result.ERR_CODE, JABXErrCode.NO_ERROR);
				streamPkt.setResult(result);
				result.put(JS_Result.IP_PORT, serverItem.getIPAndPort());
				byte[] dataAry = readData(istm.available(), result);
				if (dataAry != null) {
					jabxKernel.addFlowSum(dataAry.length);// 計算已讀取之Socket流量
					dataUploadOrDownloadTime = System.currentTimeMillis();
				}
				int errCode = (int)result.get(JS_Result.ERR_CODE);
				if (errCode == JABXErrCode.NO_ERROR) {// 若無錯誤
					streamPkt.setType((byte)0);
					streamPkt.setData(dataAry);
					resultProc.putStreamPkt(streamPkt);// 讀取之Socket數據交由resultProc處理
				}else {// 數據異常
					if (errCode != JABXErrCode.NODATA_ERROR) {
						streamPkt.setType((byte)1);
						resultProc.putStreamPkt(streamPkt);// 數據異常交由resultProc處理
					}
				}
			}catch(OutOfMemoryError e) {
				jabxLog.outputErrorAndLog("ZJABXSocket.readRtPacket()", e.getMessage());
				throw e;
			}
		}
	}

	/**
	 * 傳送Socket參數之類別
	 * @author jason
	 *
	 */
	private class SendParamThread implements Runnable {
		
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isRunParamFlag) {
				synchronized (serverItem.socketParamLock) {
					if (paramQueue.isEmpty() || serverItem.isBlockingQueue) {
						try {
							serverItem.socketParamLock.wait();
						}catch(InterruptedException e) {
						}	
					}
					processAllParam();
				}
			}
			jabxLog.outputInfoAndLog("SendParamThread.run()", serverItem.getIPAndPort() + "->Stop running");
		}
	}

	private void processAllParam() {
		try {
			while (true) {
				if (paramQueue.isEmpty() || serverItem.isBlockingQueue) {
					break;
				}
				JABXSocketParam stParam = takeParamQueue();
				if (stParam != null) {
					if (stParam.getFuncCode().equals("R00")) {
						if (isFirstR00) {
							isFirstR00 = false;
							serverItem.isBlockingQueue = true;
						}else {
							String queryStr = new String(stParam.getQueryAry());
							int index1 = queryStr.indexOf("1=");
							int index2 = queryStr.indexOf("2=");
							String oldStr = queryStr.substring(index1 + 2, index2 - 1);
							queryStr = queryStr.replaceFirst(oldStr, String.valueOf(serverItem.getSessionID()));
							stParam.setQueryAry(queryStr.getBytes());
						}
						outputData(stParam);
						if (serverItem.isBlockingQueue) {
							break;
						}
					}else {
						outputData(stParam);
					}
				}
			}
		}catch(Exception e) {
			
		}
	}
	
	/**
	 * 放ZJABXSocketParam物件至paramQueue中
	 * @param pkt - ZJABXSocketParam
	 */	
	public void putParamQueue(JABXSocketParam pkt) {
		try {
			paramQueue.put(pkt);
		}catch(InterruptedException ie) {
			jabxLog.outputInfoAndLog("ZJABXSocket.putParamQueue()", ie.toString());
		}
	}
		
	/**
	 * 自paramQueue中取出一ZJABXSocketParam物件
	 * @return ZJABXSocketParam
	 */	
	private JABXSocketParam takeParamQueue() throws InterruptedException {
		try {
			return paramQueue.take();
		}catch(InterruptedException ie){
			jabxLog.outputInfoAndLog("ZJABXSocket.takeParamQueuet()", ie.toString());
		    throw ie;
		}
	}	

	/**
	 * 清除paramQueue中之所有物件
	 * @throws InterruptedException
	 */
	public void removeAllParamQueue() throws InterruptedException {
		paramQueue.clear();
	}

	/**
	 * 讀取Socket封包之類別
	 * @author Jason
	 *
	 */
	private class ReadSocketDataThread implements Runnable {

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int nErrCode = JABXErrCode.NO_ERROR;
			try {
				while (currentRunMode != JABXConst.STOP_MODE) {
					Thread.sleep(JABXConst.ASYNC_MODE_SLEEP_TIME);
					readRtPacket();// 讀取Socket封包資料
					long nowTime = System.currentTimeMillis();
					if ((nowTime - dataUploadOrDownloadTime) > JABXConst.HEARTBIT_KEEPALIVE_TIME) {
						writeHeartbit(0);// send keep alive
						dataUploadOrDownloadTime = nowTime;
					}
				}
			}catch(InterruptedException e) {
				isConnecting = false;
				currentRunMode = JABXConst.STOP_MODE;
				jabxLog.outputInfoAndLog("ReadSocketDataThread.run()->" + serverItem.getIPAndPort(), e.getMessage());
			}catch(IOException e) {
				nErrCode = JABXErrCode.IO_ERROR;
				jabxLog.outputErrorAndLog("ReadSocketDataThread.run()" + serverItem.getIPAndPort(), e.getMessage());
				e.printStackTrace();
			}catch(OutOfMemoryError e) {
				nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
				jabxLog.outputErrorAndLog("ReadSocketDataThread.run()" + serverItem.getIPAndPort(), e.getMessage());
				e.printStackTrace();
			}catch(NullPointerException e) {
				nErrCode = JABXErrCode.NULL_POINTER;
				jabxLog.outputErrorAndLog("ReadSocketDataThread.run()" + serverItem.getIPAndPort(), e.getMessage());
				e.printStackTrace();
			}catch(Exception e) {							
				nErrCode = JABXErrCode.UNKNOWN_ERROR;
				jabxLog.outputErrorAndLog("ReadSocketDataThread.run()" + serverItem.getIPAndPort(), e.getMessage());
				e.printStackTrace();
			}
			if (nErrCode != JABXErrCode.NO_ERROR) {
				currentRunMode = JABXConst.STOP_MODE;
				isConnecting = false;
				ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
				if (soObj != null) {
					soObj.setConnectStatus(serverItem.getIPAndPort(), isConnecting);
				}
				serverItem.setConnectStatus(isConnecting);
				addInformationPacket(jabxKernel, JABXConst.ABXFUN_SESSION, JABXConst.ABXMSG_DISCONNECTION, serverItem.getIPAndPort(), nErrCode);
			}
			jabxLog.outputInfoAndLog("ReadSocketDataThread.run()", serverItem.getIPAndPort() + "->Stop running");
		}
	}

	/**
	 * 自Socket讀滿bAry值
	 * @param bAry - byte[]
	 * @throws IOException
	 */
	public void readFully(byte[] bAry) throws IOException {
		//if (istm == null || istm.available() == 0) {
		if (istm == null) {
			return;
		}
		synchronized (istm) {
			istm.readFully(bAry);
		}
	}

	/**
	 * 自Socket讀取一byte值
	 * @return byte
	 * @throws IOException
	 */
	public byte readByte() throws IOException {
		if (istm == null) {
			return 0;
		}
		synchronized (istm) {
			return istm.readByte();
		}
	}
}