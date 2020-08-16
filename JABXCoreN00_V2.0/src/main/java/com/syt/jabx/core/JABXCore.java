package com.syt.jabx.core;

import org.json.JSONObject;

import com.syt.jabx.kernel.JABXKernel;
import com.syt.jabx.kernel.JABXQuoteTool;
import com.syt.jabx.kernel.JABXUserTool;
import com.syt.jabx.notify.IJABXActionListener;
import com.syt.jabx.notify.IJABXLog;
import com.syt.jabx.notify.IJABXNotifier;
import com.syt.jabx.notify.IJABXOutputMsg;

/**
 * 應用程序組成類別<br>
 * N00為標準版，僅含行情報價及用戶相關訊息。
 * @author jason
 *
 */
public class JABXCore {

	/**
	 * 核心版本號
	 */
	private final static String VERSION = "N00_V2.0";

	/**
	 * 取得IJABXRequest物件所使用之常數
	 */
	final static int OBJ_KERNEL_REQUEST = 5000;

	/**
	 * 取得IJABXQuoteOverview物件所使用之常數
	 */
	public final static int OBJ_QUOTE_OVERVIEW = OBJ_KERNEL_REQUEST + 1;

	/**
	 * 取得IJABXStockOverview物件所使用之常數
	 */
	public final static int OBJ_STOCK_COLLECTION = OBJ_KERNEL_REQUEST + 2;

	/**
	 * 取得IJABXServerOverview物件所使用之常數
	 */
	public final static int OBJ_SERVER_INFO = OBJ_KERNEL_REQUEST + 3;

	/**
	 * 取得IJABXUserAvailableProductClass物件所使用之常數
	 */
	public final static int OBJ_USER_INFO = OBJ_KERNEL_REQUEST + 4;

	/**
	 * 取得IJABXExchangeInfoOverview物件所使用之常數
	 */
	public final static int OBJ_EXCHANGE_INFO = OBJ_KERNEL_REQUEST + 5;

	/**
	 * 取得用戶相關工具物件所使用之常數
	 */
	public final static int OBJ_USER_REQUEST = OBJ_KERNEL_REQUEST + 101;

	/**
	 * 取得行情資訊工具物件所使用之常數
	 */
	public final static int OBJ_QUOTE_REQUEST = OBJ_KERNEL_REQUEST + 102;

	/**
	 * Socket解碼之工廠物件
	 */
	private ZJABXParseFactory parseFactory;

	/**
	 * 應用程序核心管理物件
	 */
	private JABXKernel jabxKernel;

	/**
	 * 用戶相關工具物件
	 */
	private JABXUserTool userTool;

	/**
	 * 行情資訊工具物件
	 */
	private JABXQuoteTool quoteTool;

	/**
	 * Constructor
	 * @param outputMsg - IJABXOutputMsg
	 * @param notifier - IJABXNotifier
	 * @param isReservedData - boolean
	 * @param apiEdition - char
	 */
	public JABXCore(IJABXOutputMsg outputMsg, IJABXNotifier notifier, boolean isReservedData, char apiEdition) {
		parseFactory = new ZJABXParseFactory();
		jabxKernel = new JABXKernel(outputMsg, notifier, parseFactory, isReservedData, apiEdition);
		userTool = new JABXUserTool(jabxKernel, jabxKernel.getJLog());
		quoteTool = new JABXQuoteTool(jabxKernel, jabxKernel.getJLog());
		
		parseFactory.setUserTool(userTool);
		parseFactory.setQuoteTool(quoteTool);
	}

	/**
	 * 取得核心版本號
	 * @return String
	 */
	public String getCoreVersion() {
		return JABXCore.VERSION;
	}

	/**
	 * 啟始應用程序
	 */
	public void start() {
		jabxKernel.start();
	}

	/**
	 * 停止應用程序
	 */
	public void stop() {
		quoteTool.stopThread();
		jabxKernel.stop();
	}

	/**
	 * 銷毀應用程序
	 */
	public void destroy() {
		jabxKernel.destroy();
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
		return jabxKernel.login(loginJson);
	}

	/**
	 * 用戶登出
	 */
	public void logout() {
		jabxKernel.logout();
	}

	/**
	 * 取得API回覆碼
	 * @return int
	 */
	public int getRequestID() {
		return jabxKernel.getRequestID();
	}

	/**
	 * 取得訂閱即時報價之SessionID
	 * @return int
	 */
	public int getSessionID() {
		return jabxKernel.getSessionID();
	}

	/**
	 * 添加監聽事件
	 * @param listener - IJABXActionListener
	 */
	public void addListener(IJABXActionListener listener) {
		jabxKernel.addListener(listener);
	}

	/**
	 * 移除listener監聽事件
	 * @param listener - IJABXActionListener
	 */
	public void removeListener(IJABXActionListener listener) {
		jabxKernel.removeListener(listener);
	}

	/**
	 * 移除所有監聽事件
	 */
	public void removeAllListener() {
		jabxKernel.removeAllListener();
	}

	/**
	 * 錯誤代碼處理
	 * @param result - JSONObject(訊息結果物件)
	 */
	public void setErrDescription(final JSONObject result) {
		jabxKernel.setErrDescription(result);
	}
	
	/**
	 * 銷毀Socket物件
	 * @param ipAndPort - String(ip:port)
	 */
	public void destroySocket(String ipAndPort) {
		jabxKernel.destroySocket(ipAndPort);
	}

	/**
	 * 查詢程序所使用之應用介面
	 * @param queryIndex - int(介面索引值)
	 * @return Object
	 */
	public Object queryInterface(int queryIndex) {
		Object obj = null;
		switch (queryIndex) {
		case OBJ_QUOTE_OVERVIEW:
			obj = quoteTool.getQuoteOverview();
			break;
		case OBJ_STOCK_COLLECTION:
			obj = quoteTool.getStockCollection();
			break;
		case OBJ_SERVER_INFO:
			obj = jabxKernel.getServerInfo();
			break;
		case OBJ_USER_INFO:
			obj = userTool.getUserInfo();
			break;
		case OBJ_EXCHANGE_INFO:
			obj = jabxKernel.getExchangeInfo();
			break;
		case OBJ_USER_REQUEST:
			obj = userTool.getRequest();
			break;
		case OBJ_QUOTE_REQUEST:
			obj = quoteTool.getRequest();
			break;
		}
		return obj;
	}

	/**
	 * 取得輸出訊息及Log之物件
	 * @return IJABXLog
	 */
	public IJABXLog getJLog() {
		return jabxKernel.getJLog();
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
		return jabxKernel.getDayPeriod(cycle, yyyyMMdd);
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
		return jabxKernel.getMinutePeriod(tradeTime, cycle, hhmm);
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
	 * Bit 39: XR-&gt; 股票除權 EXCLUD（除去）R为RIGHT（權利）的簡寫 <br>
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
		return jabxKernel.isOnOffForStkNote(stkNote, position);
	}
}
