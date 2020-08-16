package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXFixData;
import com.syt.jabx.bean.JABXSocketParam;
import com.syt.jabx.notify.IJABXLog;

/**
 * 核心程序請求資訊類別
 * @author jason
 *
 */
public class JABXKernelRequest extends IJABXParseBase {

	/**
	 * 應用程式管理物件
	 */
	private JABXKernel jabxKernel;

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * 結果分派處理物件
	 */
	private ZJABXResultProc resultProc;

	 /**
     * A thread to run ZABusResult
     */
    private Thread resultThread;

    /**
	 * 處理Gateway Session之物件
	 */
	private ZJABXChannelSocket channelSocket = null;

	public JABXKernelRequest(JABXKernel jabxKernel, IJABXLog jabxLog) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
		resultProc = jabxKernel.getResultProc();
	}

	/**
	 * 登入Server
	 * @param serverItem - ZJABXServerItem
	 */
	void login(ZJABXServerItem serverItem) {

		ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(serverItem.getIPAndPort());
		if (socket != null) {
			socket.destroy();
		}
		if (resultThread == null) {
			resultThread = new Thread(resultProc);
			resultThread.start();
		}
		socket = new ZJABXSocket(jabxKernel, jabxLog, serverItem);
		socket.login();
		jabxKernel.getRealtimeServerMap().put(serverItem.getIPAndPort(), socket);
	}

	/**
	 * 用戶登出
	 */
	void logout(long flowSum) {
	
		boolean isLogout = false;
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXServerItem serverItem;
		ZJABXSocket socket;
		for (int i = 0, count = soObj.getCount();i < count;i++) {
			serverItem = (ZJABXServerItem)soObj.atIndex(i);
			if (serverItem.getConnectStatus()) {
				socket = jabxKernel.getRealtimeServerMap().get(serverItem.getIPAndPort());
				if (socket != null) {
					if (isLogout) {
						socket.destroy();
					}else {
						isLogout = true;
						socket.logout(flowSum);
					}
				}
			}
		}

		try {
			if (resultProc != null) {
				resultProc.removeAllStreamPkt();// 移除resultProc中之所有ZJABXPacket物件
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 停止resultThread
	 * @throws InterruptedException
	 */
	private void stopResultThread() throws InterruptedException {
		if (resultThread != null) {
			resultProc.setRunFlag(false);
		}
	}

	/**
	 * 銷毀物件
	 */
	void destroy() {
		try {
			stopResultThread();
			Thread.sleep(JABXKernel.WAIT_THREAD_STOP_TIME);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}

		resultProc = null;
		resultThread = null;
		
		// 關閉串流及Socket
		stopAllSocket();
	}

	/**
	 * 依ipAndPort停止Socket物件
	 * @param ipAndPort - String(hostID:hostPort)
	 */
	void stopSocketByIpAndPort(String ipAndPort) {

		ZJABXSocket socket;
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXServerItem serverItem = soObj.atIpAndPort(ipAndPort);
		if (serverItem.getConnectStatus()) {
			socket = jabxKernel.getRealtimeServerMap().get(ipAndPort);
			if (socket != null) {
				socket.destroy();
			}
		}
	}

	/**
	 * 停止所有Socket連線
	 */
	void stopAllSocket() {

		ZJABXServerItem serverItem;
		ZJABXSocket socket;
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		if (soObj != null) {
			for (int i = 0, count = soObj.getCount();i < count;i++) {
				serverItem = (ZJABXServerItem)soObj.atIndex(i);
				if (serverItem.getConnectStatus()) {
					socket = jabxKernel.getRealtimeServerMap().get(serverItem.getIPAndPort());
					if (socket != null) {
						socket.destroy();
					}
				}
			}
		}
	}

	/**
	 * 下載Patch檔
	 * @param abxfunCode - int(功能代碼)
	 * @param requestID - int(API回覆碼)
	 * @param fileName - String(檔案名稱)
	 * @param fileSeqNo - String(檔案日期)
	 */
	private synchronized void downloadPatchFile(int abxfunCode, int requestID, String fileName, String fileSeqNo) {
		
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, abxfunCode, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, abxfunCode, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}
		
		ZJABXParameter param = new ZJABXParameter();
		param.setFuncID(abxfunCode);
		param.setParameter(fileName);
		jabxKernel.putInfoMapItem(String.valueOf(requestID), param);// 儲存查詢之功能代碼及檔案名稱
		
		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢ABUS錯誤代碼對照檔
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P00", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABUS_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 檔案名稱
			fixData.setValue(fileName);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 回覆欄位Tag列表
			fixData.setValue(fileSeqNo);
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P00", ctrlHeader, queryAry);			
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("JABXKernelRequest.downloadPatchFile()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;	
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("JABXKernelRequest.downloadPatchFile()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("JABXKernelRequest.downloadPatchFile()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("JABXKernelRequest.downloadPatchFile()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, abxfunCode, requestID, siObj.getIPAndPort(), nErrCode);
		}		
	}

	public String downloadFile(JSONObject jsonObj) {
		String errMsg = ZJABXValidation.validate(JSV_Const.LOGIN, jsonObj);
		if (!errMsg.equals("")) {
			return errMsg;
		}
		
		
		return "";
	}
	
	/**
	 * 下載ABUS錯誤代碼對照檔
	 * @param requestID - int(API回覆碼)
	 */
	void downloadErrCodeFile(int requestID) {		

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_ERRORCODE);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_ERRORCODE, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * 查詢交易所資訊(P90)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param exchangeIDs - String(查詢交易所代碼列表，可多筆以分號分隔，空白表全部)
	 */
	public void queryExchangeInfo(int requestID, String exchangeIDs) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_EXCHANGEINFO, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_EXCHANGEINFO, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢交易所公告內容
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P90", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABUS_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 交易所代碼列表
			fixData.setValue(exchangeIDs);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 回覆欄位Tag列表，空白代表全部欄位
			fixData.setValue("");
			queryList.add(fixData);
			// 500-02-End.

			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P90", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("JABXKernelRequest.queryExchangeInfo()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("JABXKernelRequest.queryExchangeInfo()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("JABXKernelRequest.queryExchangeInfo()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("JABXKernelRequest.queryExchangeInfo()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_EXCHANGEINFO, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

}
