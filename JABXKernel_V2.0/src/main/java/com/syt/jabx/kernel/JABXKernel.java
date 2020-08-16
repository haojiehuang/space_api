package com.syt.jabx.kernel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.syt.jabx.bean.JABXReservedQuoteData;
import com.syt.jabx.consts.JS_ExchangeInfo;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.consts.JS_ServerInfo;
import com.syt.jabx.notify.IJABXActionListener;
import com.syt.jabx.notify.IJABXLog;
import com.syt.jabx.notify.IJABXNotifier;
import com.syt.jabx.notify.IJABXOutputMsg;

/**
 * 應用程序核心管理類別
 * @author jason
 *
 */
public class JABXKernel {

	/**
	 * 等待Thread停止之時間(單位為millisecond)
	 */
	static final int WAIT_THREAD_STOP_TIME = 1000;

	/**
	 * 輸出訊息之物件
	 */
	private IJABXOutputMsg outputMsg;

	/**
	 * 訊息通知物件
	 */
	private IJABXNotifier notifier;

	/**
	 * 訊息通知物件使用之Thread物件
	 */
	private Thread notifierThread;

	/**
	 *  Socket解碼之工廠物件
	 */
	private IJABXParseFactory parseFactory;

	/**
	 * 是否在記憶中保留股名檔及報價資料
	 */
	private boolean isReservedData = false;

	/**
	 * API版本(0.User edition, 1.Server edition)
	 */
	private char apiEdition = '0';

	/**
	 * 應用程序之Log檔存放路徑
	 */
	private File appLogFile;

	/**
	 * 應用程序之資料檔存放路徑
	 */
	private File appDataFile;

	/**
	 * 記錄應用程序Log之物件
	 */
	private ZJABXLog jabxLog;

	/**
	 * 封包解析之物件
	 */
	private ZJABXParseCode parseCode;

	/**
	 * 初始登入
	 */
	private boolean isInitialLogin = false;

	/**
	 * 計算CheckSum使用之Lock
	 */
	private byte[] checkSumLock = new byte[0];

	/**
	 * 記錄流量之field
	 */
	private long flowSum;

	/**
	 * 記錄流量之Lock
	 */
	private byte[] flowSumLock = new byte[0];

	/**
	 * API回覆碼
	 */
	private int requestID = 0;

	/**
	 * Lock of requestID
	 */
	private byte[] requestIDLock = new byte[0];

	/**
	 * 訂閱即時報價之SessionID
	 */
	private int sessionID = 0;

	/**
	 * Lock of requestID
	 */
	private byte[] sessionIDLock = new byte[0];

	/**
	 * 記錄Request函式中傳遞參數之Map，key為Integer(requestID)，value依不同功能，定義不同
	 */
	private Map<String, Object> infoMap = new HashMap<String, Object>();

	/**
	 * infoMap使用之Lock
	 */
	private byte[] infoMapLock = new byte[0];

	/**
	 * 第一次登入所使用之Server
	 */
	private ZJABXServerItem firstLoginServer;

	/**
	 * 伺服器索引的物件
	 */
	private volatile ZJABXServerOverview serverOverview;

	/**
	 * 用戶登入訊息物件
	 */
	private JSONObject loginInfo;

	/**
	 * 可用產品功能列表
	 */
	private List<String> listOfProductClass;
	
	/**
	 * 結果分派之物件
	 */
	private ZJABXResultProc resultProc;

	/**
	 * 即時報價Server之Map物件
	 */
	private Map<String, ZJABXSocket> realtimeServerMap;

	/**
	 * 總覧交易所基本資料之物件
	 */
	private volatile ZJABXExchangeInfoOverview exchangeInfoOverview;

	/**
	 * 核心程序請求資訊物件
	 */
	private JABXKernelRequest kernelRequest;

	/**
	 * 保留訂閱即時資料之物件
	 */
	private JABXReservedQuoteData reservedQuoteData;

	/**
	 * Constructor
	 * @param outputMsg - IJABXOutputMsg
	 * @param notifier - IJABXNotifier
	 * @param parseFactory - IJABXParseFactory
	 * @param isReservedData - boolean
	 * @param apiEdition - char
	 */
	public JABXKernel(IJABXOutputMsg outputMsg, 
			IJABXNotifier notifier,
			IJABXParseFactory parseFactory,
			boolean isReservedData, 
			char apiEdition) {
		this.outputMsg = outputMsg;
		this.notifier = notifier;
		this.parseFactory = parseFactory;
		this.isReservedData = isReservedData;
		this.apiEdition = apiEdition;
		StringBuffer fileSb = new StringBuffer();
		fileSb.append(outputMsg.getFilePath().getAbsolutePath()).append(File.separator).append("tklogs");
		appLogFile = new File(fileSb.toString());
		if (!appLogFile.exists()) {
			try {
				appLogFile.mkdir();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		fileSb.delete(0, fileSb.length());
		fileSb.append(outputMsg.getFilePath().getAbsolutePath()).append(File.separator).append("tkdata");
		appDataFile = new File(fileSb.toString());
		if (!appDataFile.exists()) {
			try {
				appDataFile.mkdir();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		realtimeServerMap = new HashMap<String, ZJABXSocket>();
		jabxLog = new ZJABXLog(outputMsg, appLogFile);
		loginInfo = new JSONObject();
		listOfProductClass = new ArrayList<String>();
		parseCode = new ZJABXParseCode(this, jabxLog, parseFactory);
		resultProc = new ZJABXResultProc(this, jabxLog);
	}

	/**
	 * 啟動應用程序
	 */
	public void start() {
		isInitialLogin = true;
		flowSum = 0;

		reservedQuoteData = new JABXReservedQuoteData();
		
		notifier.setRunFlag(true);
		notifierThread = new Thread(notifier);
		notifierThread.start();
	}

	/**
	 * 停止可執行物件
	 */
	public void stop() {
	}

	/**
	 * 釋放記憶體資源
	 */
	public void destroy() {
		if (notifierThread != null) {
			notifier.setRunFlag(false);
		}

		if (kernelRequest != null) {
			kernelRequest.destroy();
			kernelRequest = null;
		}
		isInitialLogin = true;
		parseCode = null;
		serverOverview = null;
		exchangeInfoOverview = null;

		try {
			Thread.sleep(WAIT_THREAD_STOP_TIME);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 銷毀Socket物件
	 * @param ipAndPort - String(ip:port)
	 */
	public void destroySocket(String ipAndPort) {
		if (kernelRequest != null) {
			kernelRequest.stopSocketByIpAndPort(ipAndPort);
		}
	}

	/**
	 * 用戶登入
	 * @param loginJson - JSONObject<br>
	 * 對應參數如下所示，括號中有'*'，代表必填:<br>
	 * JSConst.LN_LOGINADDR: String(*,Server登入ip)<br>
	 * JSConst.LN_LOGINPORT: short(*,Server登入port)<br>
	 * JSConst.LN_PRODUCTID: int(*,商品ID)<br>
	 * JSConst.LN_USERTYPE: int(*,用戶類型)<br>
	 * &nbsp;&nbsp;0xFF: 主機程序<br>
	 * &nbsp;&nbsp;0x01: 系統人員<br>
	 * &nbsp;&nbsp;0x02: 業務人員<br>
	 * &nbsp;&nbsp;0x10: 銀行用戶<br>
	 * &nbsp;&nbsp;0x20: 證券用戶<br>
	 * JSConst.LN_LOGINTYPE: int(*,登入方式)<br>
	 * &nbsp;&nbsp;0-用戶ID<br>
	 * &nbsp;&nbsp;1-用戶名(預設)<br>
	 * &nbsp;&nbsp;2-用戶昵稱<br>
	 * &nbsp;&nbsp;3-歸戶帳號(大昌)<br>
	 * &nbsp;&nbsp;4-證期券商帳號(大昌、中國北方證券)<br>
	 * JSConst.LN_LOGINDATA: String(*,登入資料)<br>
	 * 內容依登入方式而定<br>
	 * &nbsp;&nbsp;當JSConst.LN_LOGINTYPE=4：＂交易所碼|券商代碼｜下單帳號＂<br>
	 * &nbsp;&nbsp;如：”T3｜F904000｜123467”<br>
	 * &nbsp;&nbsp;當JSConst.LN_LOGINTYPE=4(大州後台)： ＂交易所碼|內部券商編號｜下單帳號＂<br>
	 * &nbsp;&nbsp;如：”T1｜0｜12346”<br>
	 * &nbsp;&nbsp;當JSConst.LN_LOGINTYPE=4(香港證券)：＂交易所(區域)碼|－｜下單帳號＂<br>
	 * &nbsp;&nbsp;註：香港證券交易帳號是唯一的，所以無需券商代碼，以＂－＂填入。如：＂H1|-|JOHNLEE＂<br>
	 * JSConst.LN_PASSWORD: String(*,登入密碼)<br>
	 * JSConst.LN_TIMEOUT: int(連線逾時時間)<br>
	 * JSConst.LN_ALIVETIME: int(連線存活時間)<br>
	 * JSConst.LN_LOGINIP: String(用戶登入之ip)
	 * @return String(非空白,代表傳入參數有錯誤)
	 */
	public String login(JSONObject loginJson) { 

		String errMsg = ZJABXValidation.validate(JSV_Const.LOGIN, loginJson);
		if (!errMsg.equals("")) {
			return errMsg;
		}
		
		String loginAddr = loginJson.getString(JSConst.LN_LOGINADDR);
		short loginPort = (short)loginJson.getInt(JSConst.LN_LOGINPORT);
		int productID = loginJson.getInt(JSConst.LN_PRODUCTID);
		int platformID = loginJson.getInt(JSConst.LN_PLATFORMID);
		int userType = loginJson.getInt(JSConst.LN_USERTYPE);
		int loginType = loginJson.getInt(JSConst.LN_LOGINTYPE);
		String account = loginJson.getString(JSConst.LN_LOGINDATA);
		String password = loginJson.getString(JSConst.LN_PASSWORD);
		int nTimeout = 60;
		if (loginJson.isNull(JSConst.LN_TIMEOUT) == false) {
			nTimeout = loginJson.getInt(JSConst.LN_TIMEOUT);
		}
		int nAliveTime = 50;
		if (loginJson.isNull(JSConst.LN_ALIVETIME) == false) {
			nAliveTime = loginJson.getInt(JSConst.LN_ALIVETIME);
		}
		String loginIP = "";
		if (loginJson.isNull(JSConst.LN_LOGINIP) == false) { 
			loginJson.getString(JSConst.LN_LOGINIP);
		}
		
		if (isInitialLogin) {
			flowSum = 0;
			parseCode = new ZJABXParseCode(this, jabxLog, parseFactory);
			kernelRequest = new JABXKernelRequest(this, jabxLog);
			serverOverview = new ZJABXServerOverview();
			exchangeInfoOverview = new ZJABXExchangeInfoOverview();
			isInitialLogin = false;
		}
		
		boolean isFirstLogin = false;
		String idAndPort = loginAddr + ":" + loginPort;
		ZJABXServerItem loginServer = serverOverview.atIpAndPort(idAndPort);
		if (loginServer == null) {
			isFirstLogin = true;
			loginServer = new ZJABXServerItem();
			loginServer.setHostType((byte)0);
			loginServer.setChannelNo((byte)0);
		}
		
		loginServer.setHostIP(loginAddr);
		loginServer.setHostPort(loginPort);
		loginServer.setNtimeout(nTimeout);
		loginServer.setNaliveTime(nAliveTime);

		loginInfo.put(JSConst.LN_PRODUCTID, productID);// 設定商品代碼
		loginInfo.put(JSConst.LN_PLATFORMID, platformID);// 設定平台代碼
		loginInfo.put(JSConst.LN_USERTYPE, userType);// 設定用戶類型
		loginInfo.put(JSConst.LN_LOGINTYPE, loginType);// 設定登入方式
		loginInfo.put(JSConst.LN_LOGINDATA, account);// 設定登入資料
		loginInfo.put(JSConst.LN_PASSWORD, password);// 設定登入密碼
		loginInfo.put(JSConst.LN_PWDENCODING, JABXConst.ABGW_ENCODE_MD5);// 設定密碼編碼方式
		loginInfo.put(JSConst.LN_LOGINIP, loginIP);// 設定登入ip
		loginInfo.put(JSConst.LN_COMPRESSTYPE, JABXConst.ABX_COMPRESS_GZIP);// 設定資料編碼方式
		loginInfo.put(JSConst.LN_USERGWID, 0);
		jabxLog.setAccount(account);
		
		if (isFirstLogin) {
			firstLoginServer = loginServer;
			serverOverview.addItem(loginServer);
		}
		kernelRequest.login(loginServer);
		
		return "";
	}

	/**
	 * 用戶登出
	 */
	public void logout() {
		synchronized(flowSumLock) {
			if (kernelRequest != null) {
				kernelRequest.logout(flowSum);
			}
		}
	}

	/**
	 * 取得封包解析之類別
	 * @return ZJABXParseCode
	 */
	ZJABXParseCode getParseCode() {
		return parseCode;
	}

	/**
	 * 取得IJABXNotifier物件
	 * @return IJABXNotifier
	 */
	IJABXNotifier getNotifier() {
		return notifier;
	}

	/**
	 * 取得API的版本
	 * @return char
	 */
	public char getApiEdition() {
		return apiEdition;
	}

	/**
	 * 取得記錄應用程序Log之物件
	 * @return IJABXLog
	 */
	public IJABXLog getJLog() {
		return jabxLog;
	}

	/**
	 * 流量加總
	 * @param flow - int
	 */
	void addFlowSum(int flow) {
		synchronized(flowSumLock) {
			flowSum += flow;
		}
	}

	/**
	 * 取得第一次登入之主機ID和埠號(hostID:hostPort)
	 * @return String(hostID:hostPort)
	 */
	String getFirstLoginServerIPAndPort() {
		return firstLoginServer.getIPAndPort();
	}

	/**
	 * 用戶登入訊息物件
	 * @return JSONObject
	 */
	JSONObject getLoginInfo() {
		return this.loginInfo;
	}

	/**
	 * 取得可用產品功能列表
	 * @return List&lt;String&gt;
	 */
	List<String> getListOfProductClass() {
		return this.listOfProductClass;
	}

	/**
	 * 設定可用產品功能列表
	 * @param list - List&lt;String&gt;
	 */
	void setListOfProductClass(List<String> list) {
		this.listOfProductClass = list;
	}

	/**
	 * 取得結果分派之物件
	 * @return ZJABXResultProc
	 */
	ZJABXResultProc getResultProc() {
		return this.resultProc;
	}

	/**
	 * 取得伺服器索引的物件
	 * @return IJABXServerOverview
	 */
	ZJABXServerOverview getServerOverview() {
		return this.serverOverview;
	}

	/**
	 * 取得伺服器訊息之物件
	 * @return JSConstArray
	 */
	public JSONArray getServerInfo() {
		if (this.serverOverview == null) {
			return null;
		}
		JSONArray jsonArray = new JSONArray();
		JSONObject json;
		int count = this.serverOverview.getCount();
		for (int i = 0;i < count;i++) {
			ZJABXServerItem item = (ZJABXServerItem)this.serverOverview.atIndex(i);
			json = new JSONObject();
			json.put(JS_ServerInfo.HOST_ID, item.getHostIP());
			json.put(JS_ServerInfo.HOST_PORT, item.getHostPort());
			json.put(JS_ServerInfo.HOST_TYPE, item.getHostType());
			json.put(JS_ServerInfo.CONNECT_STATUS, item.getConnectStatus());
			List<String> exList = item.getExchangeIDCollection();
			if (exList != null) {
				JSONArray exArray = new JSONArray();
				int listSize = exList.size();
				for (int j = 0;j < listSize;j++) {
					exArray.put(exList.get(j));
				}
				json.put(JS_ServerInfo.EXCHANGE_LIST, exArray);
			}
			
			jsonArray.put(json);
		}
		return jsonArray;
	}
	
	/**
	 * 取得總覧交易所基本資料之物件
	 * @return ZJABXExchangeInfoOverview
	 */
	ZJABXExchangeInfoOverview getExchangeInfoOverview() {
		return this.exchangeInfoOverview;
	}

	/**
	 * 設定總覧交易所基本資料之物件
	 * @param exchangeInfoOverview - JSONObject
	 */
	void setExchangeInfoOverview(ZJABXExchangeInfoOverview exchangeInfoOverview) {
		this.exchangeInfoOverview = exchangeInfoOverview;
	}

	/**
	 * 取得交易所訊息之陣列
	 * @return JSONArray
	 */
	public JSONArray getExchangeInfo() {
		if (this.exchangeInfoOverview == null) {
			return null;
		}
		JSONArray jsonArray = new JSONArray();
		JSONObject json;
		String key;
		Set<String> keySet = this.exchangeInfoOverview.keySet();
		Iterator<String> it = keySet.iterator();
		while(it.hasNext()) {
			key = it.next();
			ZJABXExchangeInfo item = this.exchangeInfoOverview.getExchangeInfo(key);
			json = new JSONObject();
			json.put(JS_ExchangeInfo.AFTER_TRADE_END_TIME, item.getAfterTradeEndTime());
			json.put(JS_ExchangeInfo.AFTER_TRADE_START_TIME, item.getAfterTradeStartTime());
			json.put(JS_ExchangeInfo.CLOSE_DATE, item.getCloseDate());
			json.put(JS_ExchangeInfo.CLOSE_TIME, item.getCloseTime());
			json.put(JS_ExchangeInfo.DECIMAL_SHOW, item.getDecimalShow());
			json.put(JS_ExchangeInfo.EXCHANGE, item.getExchange());
			json.put(JS_ExchangeInfo.EXCHANGE_NAME, item.getExchangeName());
			json.put(JS_ExchangeInfo.EXCHANGE_NOTE, item.getExchangeNote());
			json.put(JS_ExchangeInfo.HALT_STATUS, item.getHaltStatus());
			json.put(JS_ExchangeInfo.NON_TRADE_DATE, item.getNonTradeDate());
			json.put(JS_ExchangeInfo.OPEN_TIME, item.getOpenTime());
			json.put(JS_ExchangeInfo.PRE_OPEN_TIME, item.getPreOpenTime());
			json.put(JS_ExchangeInfo.RE_OPEN_TIME1, item.getReOpenTime1());
			json.put(JS_ExchangeInfo.RE_OPEN_TIME2, item.getReOpenTime2());
			json.put(JS_ExchangeInfo.REF_IDX_CODE, item.getRefIdxCode());
			json.put(JS_ExchangeInfo.REST_TIME1, item.getRestTime1());
			json.put(JS_ExchangeInfo.REST_TIME2, item.getRestTime2());
			json.put(JS_ExchangeInfo.SYS_CLOSE_TIME, item.getSysCloseTime());
			json.put(JS_ExchangeInfo.SYSTEM_FLAG, item.getSystemFlag());
			json.put(JS_ExchangeInfo.TIME_ZONE, item.getTimeZone());
			json.put(JS_ExchangeInfo.TRADE_DATE, item.getTradeDate());
			json.put(JS_ExchangeInfo.TRADE_STATUS, item.getTradeStatus());
			jsonArray.put(json);
		}
		return jsonArray;
	}

	/**
	 * MD5加密
	 * @param source - String(未加密碼前字串)
	 * @return String(加密碼後字串)
	 */
	public String md5(String source) {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(source.getBytes("utf8"));
			byte[] bAry = md5.digest();
			md5 = null;
			int i;
			for (int offset = 0;offset < bAry.length;offset++) {
				i = bAry[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					sb.append('0');
				}
				sb.append(Integer.toHexString(i));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 依format取得目前日期之字串
	 * @param format - String(日期之格式)
	 * @return String(format)
	 */
	String getNowDateByFormat(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	/**
	 * CheckSum運算
	 * @param str - String
	 * @param charSet - String
	 * @return byte
	 */
	byte calcCheckSum(String str, String charSet) {
		synchronized(checkSumLock) {
			if (str != null) {
				byte tmp = 0;
				try {
					byte[] bAry = str.getBytes(charSet);
					tmp = bAry[0];
					for (int i = 1;i < bAry.length;i++) {
						tmp ^= bAry[i];
					}
					bAry = null;
				}catch(Exception e) {
				}
				return tmp;
			}else {
				return 0;
			}
		}
	}

	/**
	 * 取得檔案之序號
	 * @param fileName - String
	 * @return String
	 */
	public String getFileSeqNo(String fileName) {
		
		File file = new File(appDataFile, fileName);

		if (!file.exists()) {
			file = null;
			return "0";
		}
		
		String seqNo = "0";
		synchronized(file) {						
			try {				
				InputStream in = new FileInputStream(file);				
				BufferedReader rd = new BufferedReader(new InputStreamReader(in, JABXConst.ABX_CHARSET));					
			
				String line = rd.readLine();
				if (line != null) {
					int checkSum = Integer.MAX_VALUE, calcCheckSum = Integer.MIN_VALUE;
					String[] strAry = line.trim().split("\\|");
					if (strAry.length >= 2) {
						try {
							checkSum = Integer.parseInt(strAry[0]);
						}catch(NumberFormatException e) {
							checkSum = -1;
						}
						seqNo = strAry[1];
					}
					boolean isSecondLine = true;
					while ((line = rd.readLine()) != null) {
						if (isSecondLine) {
							isSecondLine = false;
							calcCheckSum = calcCheckSum(line, JABXConst.ABX_CHARSET);
						}else {
							calcCheckSum ^= calcCheckSum(line, JABXConst.ABX_CHARSET);
						}
						calcCheckSum ^= 13;
						calcCheckSum ^= 10;
					}
					calcCheckSum = calcCheckSum < 0 ? calcCheckSum + 256 : calcCheckSum;
					if (checkSum != calcCheckSum) {
						jabxLog.outputInfoAndLog("getFileSeqNo", String.format("%s was broken, because file checksum(%d) is not equal caculated checksum(%d)", fileName, checkSum, calcCheckSum));
						seqNo = "0";
					}else {
						jabxLog.outputInfoAndLog("getFileSeqNo", String.format("%s is OK, file checksum(%d) is equal caculated checksum(%d)", fileName, checkSum, calcCheckSum));
					}
				}else {
					seqNo = "0";
				}

				line = null;

				rd.close();
				in.close();

				in = null;
				rd = null;
			}catch(Exception mle) {
				mle.printStackTrace();
			}			
		}

		int seqInt = 0;
		try {
			seqInt = Integer.parseInt(seqNo);
		}catch(NumberFormatException e) {
			seqInt = 0;
		}
		
		seqNo = null;
		file = null;
		
		return String.valueOf(seqInt);
	}

	/**
	 * 取得核心程序請求資訊物件
	 * @return JABXKernelRequest
	 */
	public JABXKernelRequest getKernelRequest() {
		return this.kernelRequest;
	}

	/**
	 * 取得API回覆碼
	 * @return int
	 */
	public int getRequestID() {
		synchronized(requestIDLock) {
			requestID++;
			if (requestID >= 65536) {
				requestID = 1;
			}
			return requestID;
		}
	}

	/**
	 * 取得訂閱即時報價之SessionID
	 * @return int
	 */
	public int getSessionID() {
		synchronized(sessionIDLock) {
			sessionID++;
			if (sessionID >= 65536) {
				sessionID = 1;
			}
			return sessionID;
		}
	}

	/**
	 * 將數據放入infoMap中
	 * @param key - Integer
	 * @param value - Object
	 */
	public void putInfoMapItem(String key, Object value) {
		synchronized(infoMapLock) {
			infoMap.put(key, value);
			StringBuffer sb = new StringBuffer();
			sb.append("Put infoMap (key, size)->(").append(key).append(", ").append(infoMap.size()).append(")");
			if (this.loginInfo != null) {
				outputMsg.mergeTimeInfo((String)this.loginInfo.get(JSConst.LN_LOGINDATA), "putInfoMapItem()", sb.toString());
			}else {
				outputMsg.mergeTimeInfo("#UnLogin#:", "putInfoMapItem()", sb.toString());
			}
		}
	}

	/**
	 * 取得infoMap中key之value
	 * @param key - Integer
	 * @return Object
	 */
	public Object getInfoMapValue(String key) {
		synchronized(infoMapLock) {
			Object value;
			if (infoMap.containsKey(key)) {
				value = infoMap.remove(key);
			}else {
				value = null;
			}
			StringBuffer sb = new StringBuffer();
			sb.append("Remove infoMap (key, size)->(").append(key).append(", ").append(infoMap.size()).append(")");
			if (this.loginInfo != null) {
				outputMsg.mergeTimeInfo((String)this.loginInfo.get(JSConst.LN_LOGINDATA), "getInfoMapValue()", sb.toString());
			}else {
				outputMsg.mergeTimeInfo("#UnLogin#:", "getInfoMapValue()", sb.toString());
			}
			return value;
		}
	}

	/**
	 * 是否在記憶中保留股名檔及報價資料
	 * @return boolean
	 */
	public boolean isReservedData() {
		return isReservedData;
	}

	/**
	 * 添加監聽事件
	 * @param listener - IJABXActionListener
	 */
	public void addListener(IJABXActionListener listener) {
		if (notifier != null) {
			notifier.addListener(listener);
		}
	}

	/**
	 * 移除listener監聽事件
	 * @param listener - IJABXActionListener
	 */
	public void removeListener(IJABXActionListener listener) {
		if (notifier != null) {
			notifier.removeListener(listener);
		}
	}

	/**
	 * 移除所有監聽事件
	 */
	public void removeAllListener() {
		if (notifier != null) {
			notifier.removeAllListener();
		}
	}

	/**
	 * 錯誤代碼處理
	 * @param result - JSONObject(訊息結果物件)
	 */
	public void setErrDescription(final JSONObject result) {
		int errorCode = (int)result.get(JS_Result.ERR_CODE);
		String errorDesc = (String)result.get(JS_Result.ERR_DESC);
		if (errorDesc == null || errorDesc.equals("")) {
			errorDesc = ZJABXErrorInfo.getErrorDesc(errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
		}
		errorDesc = null;
	}

	/**
	 * 依主機屬性取得主機資訊(僅2-回報主機及3-ABUSGW主機)
	 * @param hostType - byte 
	 * @return IdAndPortInfo
	 */
	ZJABXIPAndPortInfo getIPAndPortInfo(byte hostType) {
		// 500-01-Begin: 判斷是否有hostType屬性的Server，若無則使用登入時的Server
		boolean isAssignedServer = false;
		String ipAndPort = "";
		ZJABXServerOverview soObj = (ZJABXServerOverview)getServerOverview();
		if (soObj != null) {
			// 取得Server的主機代碼及埠號
			List<String> ipAndPortList = soObj.getServerListByHostType(hostType);
			if (ipAndPortList != null && ipAndPortList.size() != 0) {// 主機代碼及埠號不為空
				isAssignedServer = true;
				ipAndPort = ipAndPortList.get(0);
			}else {// 主機代碼及埠號為空
				ipAndPort = getFirstLoginServerIPAndPort();
			}
		}else {
			ipAndPort = getFirstLoginServerIPAndPort();
		}
		return new ZJABXIPAndPortInfo(ipAndPort, isAssignedServer);// 500-01-End.
	}

	/**
	 * 取得主機屬性為1的主機資訊
	 * @return IdAndPortInfo
	 */
	ZJABXIPAndPortInfo getIPAndPortInfoByServer_1() {
		// 600-01-Begin: 判斷是否有hostType屬性的Server，若無則使用登入時的Server
		boolean isAssignedServer = false;
		String ipAndPort = "";
		ZJABXServerOverview soObj = (ZJABXServerOverview)getServerOverview();
		// 取得主機屬性為1的Server的主機代碼及埠號
		List<String> ipAndPortList = soObj.getServerListByHostType(JABXConst.SERVER_TYPE_1);
		if (ipAndPortList != null && !ipAndPortList.isEmpty()) {// 主機代碼及埠號不為空
			isAssignedServer = true;
			ipAndPort = ipAndPortList.get(0);
		}else {// 無法取得主機屬性為1的Server
			// 取得主機屬性為3的Server的主機代碼及埠號
			ipAndPortList = soObj.getServerListByHostType(JABXConst.SERVER_TYPE_3);
			if (ipAndPortList != null && !ipAndPortList.isEmpty()) {// 主機代碼及埠號不為空
				isAssignedServer = true;
				ipAndPort = ipAndPortList.get(0);
			}else {// 無法取得主機屬性為3的Server
				ipAndPort = getFirstLoginServerIPAndPort();
			}
		}
		return new ZJABXIPAndPortInfo(ipAndPort, isAssignedServer);// 600-01-End.
	}

	/**
	 * 依交易所及主機類型取得主機代碼及埠號
	 * @param exchangeID - String
	 * @param serverType - byte
	 * @return String(hostID:hostPort)
	 */
	String getIPAndPortByExchangeID(String exchangeID, byte serverType) {
		String firstLoginIPAndPort = getFirstLoginServerIPAndPort();
		ZJABXServerOverview soObj = (ZJABXServerOverview)getServerOverview();
		if (soObj == null) {
			return firstLoginIPAndPort;
		}
		List<String> serverList = soObj.getServerListByHostType(serverType);// 依Server屬性取得Server列表
		if (serverList == null || serverList.size() == 0) {
			return firstLoginIPAndPort;
		}

		String ipAndPort, tmpIPAndPort = "";
		List<String> exchangeIDList;
		ZJABXServerItem siObj;
		boolean isInServerList = false;
		for (int i = 0, size = serverList.size();i < size;i++) {
			tmpIPAndPort = serverList.get(i);// 取得Server的hostIP:hostPort
			if (tmpIPAndPort.equals(firstLoginIPAndPort)) {
				continue;
			}
			siObj = soObj.atIpAndPort(serverList.get(i));// 取得對應tmpIPAndPort的Server物件
			if (siObj == null) {
				continue;
			}else {
				exchangeIDList = siObj.getExchangeIDCollection();// 取得交易所列表
				for (int j = 0, size2 = exchangeIDList.size();j < size2;j++) {
					if (exchangeID.equals(exchangeIDList.get(j))) {// 比對exchangeID是否在exchangeIDList中
						isInServerList = true;
						break;
					}
				}
				if (isInServerList) {
					break;
				}
			}
		}
		if (isInServerList) {
			ipAndPort = tmpIPAndPort;
		}else {
			ipAndPort = firstLoginIPAndPort;
		}
		
		return ipAndPort;
	}

	/**
	 * 取得即時報價Server之Map物件
	 * @return Map&lt;String, JABXSocket&gt;
	 */
	Map<String, ZJABXSocket> getRealtimeServerMap() {
		return realtimeServerMap;
	}

	/**
	 * 取得應用程序之資料檔存放路徑
	 * @return File
	 */
	public File getAppDataPath() {
		return appDataFile;
	}

	/**
	 * 取得保留訂閱即時資料之物件
	 * @return JABXReservedQuoteData
	 */
	JABXReservedQuoteData getReservedQuoteData() {
		return this.reservedQuoteData;
	}

	/**
	 * 取得日期所屬週期
	 * @param cycle - int(資料週期)<br>
	 * 週:JABXConst.ABX_PRICEDATA_WEEK<br>
	 * 月:JABXConst.ABX_PRICEDATA_MONTH<br>
	 * 季:JABXConst.ABX_PRICEDATA_QUARTER<br>
	 * 半年:JABXConst.ABX_PRICEDATA_HYEAR<br>
	 * 年:JABXConst.ABX_PRICEDATA_YEAR
	 * @param yyyyMMdd - int(日期)
	 * @return int(週期,格式為yyyyMMdd,-1代表資料週期錯誤)
	 */
	public int getDayPeriod(int cycle, int yyyyMMdd) {
		int yyyy = yyyyMMdd / 10000;
		int month = yyyyMMdd % 10000 / 100;
		int dd = yyyyMMdd % 100;
		int mmdd = month * 100 + dd;

		int result, period;
		switch (cycle) {
		case JABXConst.ABX_PRICEDATA_WEEK:// 週
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, yyyy);
			calendar.set(Calendar.MONTH, month - 1);
			calendar.set(Calendar.DATE, 1);
			boolean firstDayIsSunday = false;
			if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {// 月份的第一天為星期日
				firstDayIsSunday = true;
			}

			if (dd == 1) {
				period = 1;
			}else {
				calendar.set(Calendar.DATE, dd - 1);// 設定目前日期 - 1
				if (firstDayIsSunday) {
					period = calendar.get(Calendar.WEEK_OF_MONTH) + 1;
				}else {
					period = calendar.get(Calendar.WEEK_OF_MONTH);
				}
			}
			result = yyyy * 1000 + month * 10 + period;
			break;
		case JABXConst.ABX_PRICEDATA_MONTH:// 月
			result = yyyy * 100 + month;
			break;
		case JABXConst.ABX_PRICEDATA_QUARTER:// 季
			if (mmdd <= 331) {
				period = 331;
			}else if (mmdd <= 630) {
				period = 630;
			}else if (mmdd <= 930) {
				period = 930;
			}else {
				period = 1231;
			}
			result = yyyy * 10000 + period;
			break;
		case JABXConst.ABX_PRICEDATA_HYEAR:// 半年
			if (mmdd <= 630) {
				period = 6;
			}else {
				period = 12;
			}
			result = yyyy * 100 + period;
			break;
		case JABXConst.ABX_PRICEDATA_YEAR:// 年
			result = yyyy;
			break;
		default:
			result = -1;// 資料週期錯誤
			break;
		}

		return result;		
	}

	/**
	 * 取得分鐘所屬週期
	 * @param tradeTime - JSONObject<br>
	 * JSConst.TT_EXCHANGE: String(交易所代碼)<br>
	 * JSConst.TT_CLOSEHHMM: int(收盤時間,格式HHmm)<br>
	 * JSConst.TT_OPENHHMM: int(開盤時間, 格式HHmm)<br>
	 * JSConst.TT_REOPENHHMM1: int(再開盤時間1, 格式HHmm)<br>
	 * JSConst.TT_RESTHHMM1: int(休息時間1, 格式HHmm)
	 * @param cycle - int(資料週期)<br>
	 * 5分線: JABXConst.ABX_PRICEDATA_MIN5<br>
	 * 10分線: JABXConst.ABX_PRICEDATA_MIN10<br>
	 * 15分線: JABXConst.ABX_PRICEDATA_MIN15<br>
	 * 20分線: JABXConst.ABX_PRICEDATA_MIN20<br>
	 * 30分線: JABXConst.ABX_PRICEDATA_MIN30<br>
	 * 60分線: JABXConst.ABX_PRICEDATA_MIN60
	 * @param hhmm - int(時分,格式為HHmm)
	 * @return int(週期,格式為HHmm,-1代表傳入參數有問題)
	 */
	public int getMinutePeriod(JSONObject tradeTime, int cycle, int hhmm) {
		if (tradeTime == null) {
			return -1;
		}else {
			String errMsg = ZJABXValidation.validate(JSV_Const.TRADETIME, tradeTime);
			if (errMsg != "") {
				return -1;
			}
		}

		int openM, restM, reOpenM, closeM, intermm, ndiff, noffset = 0;
		int curM, baseM;

		String exchange = tradeTime.getString(JSConst.TT_EXCHANGE);
		int t_openHHmm = tradeTime.getInt(JSConst.TT_OPENHHMM);
		int t_restHHmm1 = tradeTime.getInt(JSConst.TT_RESTHHMM1);
		int t_reOpenHHmm1 = tradeTime.getInt(JSConst.TT_REOPENHHMM1);
		int t_closeHHmm = tradeTime.getInt(JSConst.TT_CLOSEHHMM);
		
		openM = t_openHHmm / 100 * 60 + t_openHHmm % 100;
		restM = t_restHHmm1 / 100 * 60 + t_restHHmm1 % 100;
		reOpenM = t_reOpenHHmm1 / 100 * 60 + t_reOpenHHmm1 % 100;
		closeM = t_closeHHmm / 100 * 60 + t_closeHHmm % 100;
		curM = hhmm / 100 * 60 + hhmm % 100;

		if (curM > restM && curM < reOpenM) {
			curM = reOpenM;
		}

		switch (cycle) {
		case JABXConst.ABX_PRICEDATA_MIN5:// 5分線
			intermm = 5;
			break;
		case JABXConst.ABX_PRICEDATA_MIN10:// 10分線
			intermm = 10;
			break;
		case JABXConst.ABX_PRICEDATA_MIN15:// 15分線
			intermm = 15;
			break;
		case JABXConst.ABX_PRICEDATA_MIN20:// 20分線
			intermm = 20;
			break;
		case JABXConst.ABX_PRICEDATA_MIN30:// 30分線
			intermm = 30;
			break;
		case JABXConst.ABX_PRICEDATA_MIN60:// 60分線
			intermm = 60;
			break;
		default:
			intermm = 5;
		}
		if (exchange.substring(0, 1).equals("C") 
			&& (cycle == JABXConst.ABX_PRICEDATA_MIN5 || cycle == JABXConst.ABX_PRICEDATA_MIN10)) {
			baseM = openM - 10;
		}else {
			baseM = openM;
		}

		noffset = (restM - baseM) % intermm;

		if (curM >= reOpenM) {
			baseM = reOpenM - noffset;
		}
		if (curM <= baseM + intermm && curM < restM) {
			ndiff = intermm;
		}else {
			ndiff = curM - baseM;
			if (ndiff % intermm > 0) {
				ndiff = (ndiff / intermm + 1) * intermm;
			}
		}
		curM = baseM + ndiff;
		if (curM > restM && curM < (reOpenM + intermm - noffset)) {
			curM = reOpenM + intermm - noffset;// 20分線 取上午10分鐘和下午10分鐘 用下午開盤加10分鐘的時間
		}else if (curM > closeM) {
			curM = closeM;
		}
		return curM / 60 * 100 + curM % 60;
	}

	/**
	 * 檢查股票StkNote欄位第position bit是否啟用(position從1開始)
	 * @param stkNote - String(股票StkNote欄位值)<br>
	 * 每個 bit 代表一屬性, 0 -&gt; off, 1-&gt;on<br>
	 * bit 數不定, 最多 256 Bytes : 即 256 * 8 bits <br>
	 * low bit 1 --&gt; bit 32 : 依循 TW <br>
	 * low bit 33--&gt; bit 56 : 依循 CN 新增 flag <br>
	 * low bit 57--&gt; bit 64 : 其他 <br>
	 * low bit 65--&gt; bit 96 : 依循 HK 新增 flag <br>
	 * low bit 97--&gt; bit128 : 依循 Thai 新增 flag 及 其他 <br>
	 * Bit 1: 管理商品 (Low Bit) <br>
	 * Bit 2: 交易記號 <br>
	 * Bit 3: 受益憑證 <br>
	 * Bit 4: ETF商品 <br>
	 * Bit 5: 權證記號 <br>
	 * Bit 6: 特別股 <br>
	 * Bit 7: 存託憑證 <br>
	 * Bit 8: 外國股票 <br>
	 * Bit 9: 可轉換公司債 <br>
	 * Bit 10: 附認股權公司債 <br>
	 * Bit 11: 警示股 <br>
	 * Bit 12: 指數 <br>
	 * Bit 13: 期貨 <br>
	 * Bit 14: 個股選擇權 <br>
	 * Bit 15: 指數選擇權 <br>
	 * Bit 16: 特殊類別 <br>
	 * Bit 17: 非十元面額註記 <br> 
	 * Bit 18: 異常推介個股註記 <br>
	 * Bit 19: 特殊異常證券註記 <br>
	 * Bit 20: 選擇權 <br>
	 * Bit 21: 週結算期貨 <br>
	 * Bit 22: 短天期選擇權 <br>
	 * Bit 23: 附認股權特別股 <br>
	 * Bit 24: 認股權憑證 <br>
	 * Bit 25: 附認股權公司債履約後之公司債 <br>
	 * Bit 26: 換股權利證書、新股權利證書、股款繳納憑證 <br>
	 * Bit 27: 交換公司債、交換金融債 <br>
	 * Bit 28: 公債 <br>
	 * Bit 29: 外國債券 <br>
	 * Bit 30: 受益證券 <br>
	 * Bit 31: 個股期貨 <br>
	 * Bit 32: 一般股票 <br>
	 * Bit 33: *ST---公司經營連續三年虧損，退市預警 <br>
	 * Bit 34: ST----公司經營連續二年虧損，特別處理。特別處理。Special Treatment，意即“特別處理”。 <br>
	 * Bit 35: S --公司還沒有完成股改。 <br>
	 * Bit 36: N股—上市首日股票 <br>
	 * Bit 37: PT股票-- Particular Transfer(特別轉讓)，為暫停上市股票提供流通渠道的“特別轉讓服務” <br>
	 * Bit 38: XD-&gt; 股票除息 EXCLUD（除去）DIVIDEN（利息）的簡寫 <br>
	 * Bit 39: XR-&gt; 股票除權 EXCLUD（除去）R為RIGHT（權利）的簡寫 <br>
	 * Bit 40: DR-&gt; 股票除息權 <br>
	 * Bit 41: 非交易型開放式基金(僅揭示基金淨值) <br>
	 * Bit 42: 債券 <br>
	 * … <br>
	 * Bit 61: 豁免平盤下借券賣出註記  --&gt; 台股 <br>
	 * Bit 62: 豁免平盤下融券賣出註記  --&gt; 台股 <br>
	 * Bit 63: 可先買後賣現股當沖證券  --&gt; 台股 <br>
	 * Bit 64: 可現股當沖註記          --&gt; 台股 <br>
	 * ... <br>
	 * Bit 65: 一籃子權證 <br>             
	 * Bit 66: 可放空(ShortSell)      --&gt;HK <br>
	 * Bit 67: 印花稅(StampDuty)      --&gt;HK <br>
	 * Bit 68: 市場波動調節機制(VCM)  --&gt;HK <br>
	 * Bit 69: 收市競價(CAS)          --&gt;HK <br>
	 * ... <br>
	 * Bit 97: 衍生權證 <br>
	 * Bit 98: 零股 <br>
	 * Bit 99: 期貨價差 <br>
	 * ... <br>
	 * <br>
	 * ex : 11=408 --&gt; 0x0408 -&gt; 0000 0100 0000 1000 -&gt; ETF商品,警示股 <br>
	 * @param position - int(要檢查的bit索引值，從1開始)
	 * @return boolean
	 */
	public boolean isOnOffForStkNote(String stkNote, int position) {
		boolean isOnOff = false;

		Map<Character, String> mapping = new HashMap<Character, String>();
		mapping.put('0', "0000");
		mapping.put('1', "0001");
		mapping.put('2', "0010");
		mapping.put('3', "0011");
		mapping.put('4', "0100");
		mapping.put('5', "0101");
		mapping.put('6', "0110");
		mapping.put('7', "0111");
		mapping.put('8', "1000");
		mapping.put('9', "1001");
		mapping.put('A', "1010");
		mapping.put('B', "1011");
		mapping.put('C', "1100");
		mapping.put('D', "1101");
		mapping.put('E', "1110");
		mapping.put('F', "1111");
		mapping.put('a', "1010");
		mapping.put('b', "1011");
		mapping.put('c', "1100");
		mapping.put('d', "1101");
		mapping.put('e', "1110");
		mapping.put('f', "1111");

		StringBuffer sb = new StringBuffer();
		String charHex;
		for (int i = 0, length = stkNote.length();i < length;i++) {
			charHex = mapping.get(stkNote.charAt(i));
			if (charHex != null) {
				sb.append(charHex);
			}else {
				sb.append("0000");
			}
		}
		mapping = null;

		if (position <= sb.length() && '1' == sb.charAt(sb.length() - position)) {
			isOnOff = true;
		}

		return isOnOff;
	}
}
