package com.syt.jabx.kernel;

public class JSConst {

	/**
	 * 申請流水號
	 */
	public final static String AS_INPUT_NO = "ain";

	/**
	 * 申請來源
	 */
	public final static String AS_INPUT_SOURCE = "ais";

	/**
	 * 申請結果代碼
	 */
	public final static String AS_STATUS = "ast";

	// CU-Begin:共用常數
	/**
	 * 交易所代碼
	 */
	public final static String CU_EXCHANGEID = "ced";

	/**
	 * 回覆碼
	 */
	public final static String CU_REQUESTID = "crd";

	/**
	 * 報價欄位
	 */
	public final static String CU_QUOTE_FIELD = "cqf";

	/**
	 * 股票代碼
	 */
	public final static String CU_STKID = "csd";
	// CU-End.
	
	// DN-Begin: 下載檔案所使用之常數
	/**
	 * 股名檔編碼
	 */
	public final static String DN_ENCODEING = "deg";

	/**
	 * 下載檔案類型
	 */
	public final static String DN_FILETYPE = "dfe";
	
	/**
	 * 下載檔案代碼(交易所代碼,區域代碼,分類代碼...)
	 */
	public final static String DN_FILENO = "dfo";

	/**
	 * 下載檔案代碼2(交易所代碼,區域代碼,分類代碼...)
	 */
	public final static String DN_FILENO2 = "dfo2";

	/**
	 * 股名檔類別
	 */
	public final static String DN_PINYING_TYPE = "dpt";
	// DN-End.
	
	// SE-Begin: 下載檔案所使用之常數
	/**
	 * 訂閱即時交易所公告資訊
	 */
	public final static String SE_EXCHANGEBULLETIN_INFO = "sen";

	/**
	 * 是否訂閱資訊
	 */
	public final static String SE_FLAG_INFO = "sfg";

	/**
	 * 訂閱功能代碼
	 */
	public final static String SE_FUNCID = "sfd";

	/**
	 * 訂閱即時行情報導資訊
	 */
	public final static String SE_MARKETREPORT_INFO = "smt";

	/**
	 * 訂閱即時新聞資訊
	 */
	public final static String SE_NEWS_INFO = "sns";
	
	/**
	 * 訂閱資訊
	 */
	public final static String SE_QUOTE_INFO = "sqi";

	/**
	 * 訂閱即時指標資訊
	 */
	public final static String SE_REALTIMETECHNICAL_INFO = "srl";

	/**
	 * 訂閱即時主力大單資訊
	 */
	public final static String SE_SMARTMASTER_INFO = "ssr";
	
	/**
	 * 訂閱即時短線精靈資訊
	 */
	public final static String SE_SMARTSHORT_INFO = "sst";
	
	/**
	 * 訂閱即時排名資訊
	 */
	public final static String SE_SMARTRANK_INFO = "ssk";

	/**
	 * 訂閱時，不同視窗所使用之SessionID
	 */
	public final static String SE_SESSIONID = "ssd";

	// SE-End.

	// EI-Begin: 交易所訊息所使用之常數
	/**
	 * 交易所代碼
	 */
	public final static String EI_EXCHANGE = "eee";

	/**
	 * 交易所名稱
	 */
	public final static String EI_EXCHANGENAME = "een";

	/**
	 * 交易所屬性
	 */
	public final static String EI_EXCHANGENOTE = "eeo";

	/**
	 * 交易所開盤時間(交易所當地時間hhmm)
	 */
	public final static String EI_OPENTIME = "eoe";
	// EI-End.
	
	// LN-Begin: 登入訊息所使用之常數
	/**
	 * 連線存活時間(int)
	 */
	public final static String LN_ALIVETIME = "lat";

	/**
	 * 資料編碼方式(int)
	 */
	public final static String LN_COMPRESSTYPE = "lct";

	/**
	 * 登入主機ip(String)
	 */
	public final static String LN_LOGINADDR = "lla";

	/**
	 * 登入資料(String,用戶ID,用戶名或暱稱...,依LN_LOGINTYPE而定):<br>
	 * &nbsp;&nbsp;當JSConst.LN_LOGINDATA=4：＂交易所碼|券商代碼｜下單帳號＂<br>
	 * &nbsp;&nbsp;如：”T3｜F904000｜123467”<br>
	 * &nbsp;&nbsp;當JSConst.LN_LOGINDATA=4(大州後台)： ＂交易所碼|內部券商編號｜下單帳號＂<br>
	 * &nbsp;&nbsp;如：”T1｜0｜12346”<br>
	 * &nbsp;&nbsp;當JSConst.LN_LOGINDATA=4(香港證券)：＂交易所(區域)碼|－｜下單帳號＂<br>
	 * &nbsp;&nbsp;註：香港證券交易帳號是唯一的，所以無需券商代碼，以＂－＂填入。如：＂H1|-|JOHNLEE＂ 
	 */
	public final static String LN_LOGINDATA = "lld";

	/**
	 * 登入IP(String)
	 */
	public final static String LN_LOGINIP = "lli";

	/**
	 * 登入主機連線埠號(short)
	 */
	public final static String LN_LOGINPORT = "llp";

	/**
	 * 登入方式(int):<br>
	 * &nbsp;&nbsp;0-用戶ID<br>
	 * &nbsp;&nbsp;1-用戶名(預設)<br>
	 * &nbsp;&nbsp;2-用戶昵稱<br>
	 * &nbsp;&nbsp;3-歸戶帳號(大昌)<br>
	 * &nbsp;&nbsp;4-證期券商帳號(大昌、中國北方證券)<br>
	 */
	public final static String LN_LOGINTYPE = "llt";
	
	/**
	 * 平台ID(byte)
	 */
	public final static String LN_PLATFORMID = "lpm";
	
	/**
	 * 商品ID(byte)
	 */
	public final static String LN_PRODUCTID = "lpt";

	/**
	 * 連線逾時時間(int)
	 */
	public final static String LN_TIMEOUT = "ltt";

	/**
	 * 用戶GWID(int)
	 */
	public final static String LN_USERGWID = "lug";
	
	/**
	 * 用戶類型(int):<br>
	 * &nbsp;&nbsp;0xFF: 主機程序<br>
	 * &nbsp;&nbsp;0x01: 系統人員<br>
	 * &nbsp;&nbsp;0x02: 業務人員<br>
	 * &nbsp;&nbsp;0x10: 銀行用戶<br>
	 * &nbsp;&nbsp;0x20: 證券用戶<br>
	 */
	public final static String LN_USERTYPE = "lut";
	
	/**
	 * 密碼(String)
	 */
	public final static String LN_PASSWORD = "lpd";
	
	/**
	 * 密碼編碼方式(byte)
	 */
	public final static String LN_PWDENCODING = "lpg";

	/**
	 * 安全碼(byte)
	 */
	public final static String LN_SECURITYKEY = "lsk";
	// LN-End.

	// MS-Start: 主力大單資訊
	/**
	 * 主力大單最小成交金額
	 */
	public final static String MS_MINIMUM_AMOUNT = "mma";

	/**
	 * 主力大單最小成交量
	 */
	public final static String MS_MINIMUM_VOLUME = "mmv";
	// MS-End.
	// TT-Start: 交易所代碼及時間
	/**
	 * 收盤時間(HHmm)
	 */
	public final static String TT_CLOSEHHMM = "tcm"; 

	/**
	 * 交易所代碼
	 */
	public final static String TT_EXCHANGE = "tee";

	/**
	 * 開盤時間(HHmm)
	 */
	public final static String TT_OPENHHMM = "tom";

	/**
	 * 再開盤時間1(HHmm)
	 */
	public final static String TT_REOPENHHMM1 = "tre";

	/**
	 * 休息時間1(HHmm)
	 */
	public final static String TT_RESTHHMM1 = "trm";
	// TT-End.

	// WATCH-Begin: 即時資料所使用之常數
	/**
	 * 所屬股票全碼(String)
	 */
	public final static String WATCH_BELONG_FULLSTKID = "wbf";

	/**
	 * 商品群組代碼(String)
	 */
	public final static String WATCH_CLASS_GROUPID = "wcg";

	/**
	 * 商品代碼(String)
	 */
	public final static String WATCH_CLASS_TYPE = "wct";

	/**
	 * 收盤時間(int)
	 */
	public final static String WATCH_CLOSE_HHMM = "wch";

	/**
	 * 合約年月(String), 一般為期貨商品
	 */
	public final static String WATCH_CONTRACT_DATE = "wcd";

	/**
	 *  交易幣別(String)
	 */
	public final static String WATCH_CURRENCY_CODE = "wcc";

	/**
	 * 小數位數或分數之分母(int) 
	 */
	public final static String WATCH_DECIMAL = "wd";

	/**
	 * 最後結算日/下市日(int), format: yyyyMMdd
	 */
	public final static String WATCH_DELLISTING_DATE = "wdd";

	/**
	 *  跌停價(int)
	 */
	public final static String WATCH_DOWNSTOP_PRICE = "wdp";

	/**
	 * 最後收盤日(int), format: yyyyMMdd
	 */
	public final static String WATCH_LAST_CLOSEDATE = "wlc";

	/**
	 * 最後交易日(int), format: yyyyMMdd
	 */
	public final static String WATCH_LAST_TRADEDATE = "wlt";

	/**
	 * 下市/重新上市(String)
	 */
	public final static String WATCH_LISTING = "wl";

	/**
	 * 當地日期時間(long)
	 */
	public final static String WATCH_LOCAL_TIME = "wlT";

	/**
	 * 交易單位(int)
	 */
	public final static String WATCH_LOT_SIZE = "wls";

	/**
	 * 市場特殊狀態(String)
	 */
	public final static String WATCH_MARKET_STATUS = "wms";

	/**
	 * 開盤時間(int), format: HHmm
	 */
	public final static String WATCH_OPEN_HHMM = "woh"; 

	/**
	 * 開盤參考價(int)
	 */
	public final static String WATCH_OPEN_REFPRICE = "wor";

	/**
	 *  假開盤時間(int), format: HHmm
	 */
	public final static String WATCH_PREOPEN_HHMM = "wph";

	/**
	 *  再開盤時間1(int), format: HHmm
	 */
	public final static String WATCH_REOPEN_HHMM1 = "wrh1";

	/**
	 *  再開盤時間2(int), format: HHmm
	 */
	public final static String WATCH_REOPEN_HHMM2 = "wrh2";

	/**
	 *  休息時間1(int), format: HHmm
	 */
	public final static String WATCH_REST_HHMM1 = "wrh1";

	/**
	 *  休息時間2(int), format: HHmm
	 */
	public final static String WATCH_REST_HHMM2 = "wrh2";

	/**
	 * 檔差碼(String)
	 */
	public final static String WATCH_SPREADTABLE_CODE = "wsc";

	/**
	 * 股票英文名稱(String)
	 */
	public final static String WATCH_STK_NAMEE = "wsne";

	/**
	 * 股票簡中名稱(String)
	 */
	public final static String WATCH_STK_NAMES = "wsns";

	/**
	 * 股票繁中名稱(String)
	 */
	public final static String WATCH_STK_NAMET = "wsnt";

	/**
	 * 證券屬性(String)
	 */
	public final static String WATCH_STK_NOTE = "wsn";
	
	/**
	 * 履約價(int)
	 */
	public final static String WATCH_STRIKE_PRICE = "wsp";

	/**
	 *  檔差(int)
	 */
	public final static String WATCH_TICK_DIFF = "wtd";

	/**
	 * 時區(int)
	 */
	public final static String WATCH_TIME_ZONE = "wtz";

	/**
	 * 交易代碼(String)
	 */
	public final static String WATCH_TRADE_CODE = "wtc";
	
	/**
	 * 產業群組代碼(String)
	 */
	public final static String WATCH_TRADE_GROUPID = "wtg";

	/**
	 * 交易狀態(String)
	 */
	public final static String WATCH_TRADE_STATUS = "wts";

	/**
	 * 產業代碼(String)
	 */
	public final static String WATCH_TRADE_TYPE = "wtt";
	
	/**
	 * 漲停價(int)
	 */
	public final static String WATCH_UPSTOP_PRICE = "wup";

	/**
	 * 昨未平倉(int)
	 */
	public final static String WATCH_YEST_OI = "wyo";

	/**
	 *  昨收價(int)
	 */
	public final static String WATCH_YEST_PRICE = "wyp";

	/**
	 *  昨收量(long)
	 */
	public final static String WATCH_YEST_VOLUME = "wyv";
	// WATCH-End.
}
	