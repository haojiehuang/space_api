package com.syt.jabx.kernel;

/**
 * API常數定義之類別
 * @author Jason
 *
 */
public final class JABXConst {

	/**
	 * 數據編碼方式
	 */
	public final static String ABX_CHARSET = "UTF-8";

	/**
	 * Fix Data讀取資料長度的Tag常數
	 */
	public final static int FIX_DATA_LENGTH_NUM = 95;

	/**
	 * Fix Data讀取資料內容的Tag常數
	 */
	public final static int FIX_DATA_CONTENT = 96;

	/**
	 * 執行模式(結束執行)
	 */
	final static byte STOP_MODE = 0;

	/**
	 * 執行模式(非同步)
	 */
	final static byte ASYNC_MODE = 1;

	/**
	 * 下載數據之常數值
	 */
	final static String REBUILD = "Rebuild";

	// ********** 壓縮類型常數 ******************************
	/**
	 * 壓縮類型常數: 無壓縮(API內部使用)
	 */
	final static byte ABUS_COMPRESS_NULL = 0x00;

	/**
	 * 壓縮類型常數: 無壓縮
	 */
	public final static byte ABX_COMPRESS_NULL = 0x00;

	/**
	 * 壓縮類型常數: Huffman壓縮方式(API內部使用)
	 */
	final static byte ABUS_COMPRESS_HUFFMAN = 0x01;

	/**
	 * 壓縮類型常數: Huffman壓縮方式
	 */
	public final static byte ABX_COMPRESS_HUFFMAN = 0x01;

	/**
	 * 壓縮類型常數: Fast壓縮方式(API內部使用)
	 */
	final static byte ABUS_COMPRESS_FAST = 0x02;

	/**
	 * 壓縮類型常數: Fast壓縮方式
	 */
	public final static byte ABX_COMPRESS_FAST = 0x02;

	/**
	 * 壓縮類型常數: GZIP壓縮方式(API內部使用)
	 */
	final static byte ABUS_COMPRESS_GZIP = 0x03;

	/**
	 * 壓縮類型常數: GZIP壓縮方式
	 */
	public final static byte ABX_COMPRESS_GZIP = 0x03;

	/**
	 * Milliseconds to ASYNC_MODE sleep time
	 */
	static final int ASYNC_MODE_SLEEP_TIME = 50;
	
	/**
	 * Milliseconds to HEARTBIT keep alive time(30秒)
	 */
	static final int HEARTBIT_KEEPALIVE_TIME = 30000;

	/**
	 * Milliseconds to LINE HEARTBIT keep alive time(1分50秒)
	 */
	static final int LINE_HEARTBIT_KEEPALIVE_TIME = 110000;

	// ********** 交易所常數 ******************************
	
	/**
	 * 交易所常數: 台灣所有交易所(API內部使用)
	 */
	final static String ABUS_ZONECODE_TW = "TW";

	/**
	 * 交易所常數: 台灣所有交易所
	 */
	public final static String ABX_ZONECODE_TW = "TW";

	/**
	 * 交易所常數: 大陸所有交易所(API內部使用)
	 */
	final static String ABUS_ZONECODE_CN = "CN";

	/**
	 * 交易所常數: 大陸所有交易所
	 */
	public final static String ABX_ZONECODE_CN = "CN";

	/**
	 * 交易所常數: 香港所有交易所(API內部使用)
	 */
	final static String ABUS_ZONECODE_HK = "HK";

	/**
	 * 交易所常數: 香港所有交易所
	 */
	public final static String ABX_ZONECODE_HK = "HK";

	// ********** 訊息主代碼常數 ******************************
	/**
	 * 訊息主代碼常數: Control(控制)(API內部使用)
	 */
	public final static byte ABUS_MAINTYPE_CONTROL = 0x09;

	/**
	 * 訊息主代碼常數: Table(表格)(API內部使用)
	 */	
	public final static byte ABUS_MAINTYPE_TABLE = 0x08;

	/**
	 * 訊息主代碼常數: WatchList(即時報價)(API內部使用)
	 */
	public final static byte ABUS_MAINTYPE_WATCHLIST = 0x01;

	/**
	 * 訊息主代碼常數: Smart(短線精靈)(API內部使用)
	 */
	public final static byte ABUS_MAINTYPE_SMART = 0x02;

	/**
	 * 訊息主代碼常數: FreeFormat(其它自定格式)(API內部使用)
	 */
	public final static byte ABUS_MAINTYPE_FREEFORMAT = 0x03;

	// ********** 訊息次代碼常數 ******************************
	/**
	 * 連線心跳之常數(API內部使用)
	 */
	public final static int ABUS_CONTROL_HEARTBIT = 0x00000001;

	/**
	 * 登入之常數(API內部使用)
	 */
	public final static int ABUS_CONTROL_LOGON = 0x00000002;

	/**
	 * 登入返回之常數(API內部使用)
	 */
	public final static int ABUS_CONTROL_LOGONRESP = 0x00000003;
		 
	/**
	 * 登出之常數(API內部使用)
	 */
	public final static int ABUS_CONTROL_LOGOUT = 0x00000004;

	/**
	 * ABus回補數據之常數(API內部使用)
	 */
	public final static int ABUS_CONTROL_REBUILD = 0x00000005;

	/**
	 * ABus回補數據返回之常數(API內部使用)
	 */
	public final static int ABUS_CONTROL_REBUILDRESP = 0x00000006;

	/**
	 * 訂閱之常數(API內部使用)
	 */
	public final static int ABUS_CONTROL_SUBSCRIBE = 0x00000007;

	/**
	 * 訂閱返回之常數(API內部使用)
	 */
	public final static int ABUS_CONTROL_SUBSCRIBERESP = 0x00000008;  

	/**
	 * Gateway回補數據之常數(API內部使用)
	 */
	public final static int ABUS_CONTROL_NORMAL = 0x00000100;

	/**
	 * Gateway回補數據返回之常數(API內部使用)
	 */
	public final static int ABUS_CONTROL_NORMALRESP = 0x00000101;
	
	/**
	 * 股票基本資料訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_STKBASEINFO = 0x00000001;

	/**
	 * 股票相關資料訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_STKREFINFO = 0x00000002;

	/**
	 * 第1檔委買賣訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_ORDER_1 = 0x00000004;

	/**
	 * 第2至5檔委買訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_ORDER_2_5 = 0x00000008;

	/**
	 * 第6至10檔委買訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_ORDER_6_10 = 0x00000010;

	/**
	 * 成交資訊訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_TRADE = 0x00000020;

	/**
	 * 總委買賣量筆及成交筆訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_TOTREFINFO = 0x00000040;

	/**
	 * 其他一分鐘訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_1MININFO = 0x00000080;

	/**
	 * 其他報價訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_OTHERS = 0x00000100;

	/**
	 * 交易所統計資料訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_STATISTIC = 0x00000200;

	/**
	 * 交易所交易狀態訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_EXCHANGESTATUS = 0x00000400;

	/**
	 * BrokerQueue-香港訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_BROKERQUEUE = 0x00000800;

	/**
	 * 逐筆成交上海,深圳訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_DETAILTRADE = 0x00001000;

	/**
	 * 逐筆委託-深圳訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_DETAILORDER = 0x00002000;

	/**
	 * 零股買賣成交-台灣訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_OLDLOT = 0x00004000;

	/**
	 * 盤前虛擬撮合-上海,深圳訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_VIRTUALTRADE = 0x00008000;

	/**
	 * 一秒快照訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_1SECSNAPSHOT = 0x00010000;

	/**
	 * 委買賣每檔明細-上海,深圳Level-2訊息(API內部使用)
	 */
	public final static int ABUS_WATCH_ORDERLIST = 0x00020000;

	/**
	 * 系統公告(API內部使用)
	 */
	public final static int ABUS_FREEMSG_BROADCAST = 0x00000001;

	/**
	 * 交易所訊息(API內部使用)
	 */
	public final static int ABUS_FREEMSG_EXCHANGE = 0x00000002;

	/**
	 * 新聞訊息(API內部使用)
	 */
	public final static int ABUS_FREEMSG_NEWS = 0x00000004;	

	/**
	 * 產品訊息(API內部使用)
	 */
	public final static int ABUS_FREEMSG_PRODUCT = 0x00000008;

	/**
	 * 交易訊息(API內部使用)
	 */
	public final static int ABUS_FREEMSG_TRADE = 0x00000010;

	/**
	 * 即時指標訊息(API內部使用)
	 */
	public final static int ABUS_FREEMSG_LINE = 0x00000020;
	
	/**
	 * 即時智慧選股(短線精靈)(API內部使用)
	 */
	public final static int ABUS_SMARTMSG_SHORT = 0x00000001;

	/**
	 * 即時排名訊息(API內部使用)
	 */
	public final static int ABUS_SMARTMSG_RANK = 0x00000002;

	/**
	 * 主力大單訊息(API內部使用)
	 */
	public final static int ABUS_SMARTMSG_MASTER = 0x00000004;

	/**
	 * 用戶警示訊息(API內部使用)
	 */
	public final static int ABUS_SMARTMSG_WARN = 0x00000008;

	/**
	 * 股票基本資料訊息
	 */
	public final static int ABX_WATCH_STKBASEINFO = 0x00000001;

	/**
	 * 股票相關資料訊息
	 */
	public final static int ABX_WATCH_STKREFINFO = 0x00000002;

	/**
	 * 第1檔委買賣訊息
	 */
	public final static int ABX_WATCH_ORDER_1 = 0x00000004;

	/**
	 * 第2至5檔委買賣訊息
	 */
	public final static int ABX_WATCH_ORDER_2_5 = 0x00000008;

	/**
	 * 第6至10檔委買賣訊息
	 */
	public final static int ABX_WATCH_ORDER_6_10 = 0x00000010;

	/**
	 * 成交資訊訊息
	 */
	public final static int ABX_WATCH_TRADE = 0x00000020;

	/**
	 * 總委買賣量筆及成交筆訊息
	 */
	public final static int ABX_WATCH_TOTREFINFO = 0x00000040;

	/**
	 * 其他一分鐘訊息
	 */
	public final static int ABX_WATCH_MINUTE = 0x00000080;

	/**
	 * 其他報價訊息
	 */
	public final static int ABX_WATCH_OTHERS = 0x00000100;

	/**
	 * 交易所統計資料訊息
	 */
	public final static int ABX_WATCH_STATISTIC = 0x00000200;

	/**
	 * 交易所交易狀態訊息
	 */
	public final static int ABX_WATCH_EXCHANGESTATUS = 0x00000400;

	/**
	 * BrokerQueue-香港訊息
	 */
	public final static int ABX_WATCH_BROKERQUEUE = 0x00000800;

	/**
	 * 逐筆成交上海,深圳訊息
	 */
	public final static int ABX_WATCH_DETAILTRADE = 0x00001000;

	/**
	 * 逐筆委託-深圳訊息
	 */
	public final static int ABX_WATCH_DETAILORDER = 0x00002000;

	/**
	 * 零股買賣成交-台灣訊息
	 */
	public final static int ABX_WATCH_OLDLOTTRADE = 0x00004000;

	/**
	 * 盤前虛擬撮合-上海,深圳訊息
	 */
	public final static int ABX_WATCH_VIRTUALTRADE = 0x00008000;

	/**
	 * 一秒快照訊息
	 */
	public final static int ABX_WATCH_1SECSNAPSHOT = 0x00010000;

	/**
	 * 委買賣每檔明細-上海,深圳Level-2訊息
	 */
	public final static int ABX_WATCH_ORDERLIST = 0x00020000;

	/**
	 * 取得所有即時報價之class
	 */
	public final static int ABX_WATCH_ALL_CLASS = 0xFFFFFFFF;

	/**
	 * 系統公告
	 */
	public final static int ABX_FREEMSG_BROADCAST = 0x00000001;

	/**
	 * 交易所訊息
	 */
	public final static int ABX_FREEMSG_EXCHANGE = 0x00000002;

	/**
	 * 新聞訊息
	 */
	public final static int ABX_FREEMSG_NEWS = 0x00000004;

	/**
	 * 產品訊息
	 */
	public final static int ABX_FREEMSG_PRODUCT = 0x00000008;

	/**
	 * 交易訊息
	 */
	public final static int ABX_FREEMSG_TRADE = 0x00000010;

	/**
	 * 即時指標訊息
	 */
	public final static int ABX_FREEMSG_LINE = 0x00000020;
	
	/**
	 * 即時智慧選股(短線精靈)
	 */
	public final static int ABX_SMARTMSG_SHORT = 0x00000001;

	/**
	 * 即時排名訊息
	 */
	public final static int ABX_SMARTMSG_RANK = 0x00000002;

	/**
	 * 主力大單訊息
	 */
	public final static int ABX_SMARTMSG_MASTER = 0x00000004;

	/**
	 * 用戶警示訊息
	 */
	public final static int ABX_SMARTMSG_WARN = 0x00000008;

	//*******************
	/**
	 * 成交價格型態: 平盤量(API內部使用)
	 */
	final static int ABUS_VOLINOUT_EQUAL = 0;

	/**
	 * 成交價格型態: 內盤量(API內部使用)
	 */
	final static int ABUS_VOLINOUT_IN = 1;

	/**
	 * 成交價格型態: 外盤量(API內部使用)
	 */
	final static int ABUS_VOLINOUT_OUT = 2;

	//*******************
	/**
	 * 密碼編碼方式: 無
	 */
	public final static byte ABGW_ENCODE_NONE = 0x00;
	
	/**
	 * 密碼編碼方式: MD5
	 */
	public final static byte ABGW_ENCODE_MD5 = 0x01;

	/**
	 * 買方
	 */
	public final static String ABX_ORDERSIDE_BID = "B";

	/**
	 * 賣方
	 */
	public final static String ABX_ORDERSIDE_ASK = "A";

	/**
	 * 技術分析周期代碼: 日線
	 */
	final static int ABGW_PRICEDATA_DAY = 1;

	/**
	 * 技術分析周期代碼: 日線
	 */
	public final static int ABX_PRICEDATA_DAY = 1;

	/**
	 * 技術分析周期代碼: 周線
	 */
	final static int ABGW_PRICEDATA_WEEK = 2;

	/**
	 * 技術分析周期代碼: 周線
	 */
	public final static int ABX_PRICEDATA_WEEK = 2;

	/**
	 * 技術分析周期代碼: 月線
	 */
	final static int ABGW_PRICEDATA_MONTH = 3;

	/**
	 * 技術分析周期代碼: 月線
	 */
	public final static int ABX_PRICEDATA_MONTH = 3;

	/**
	 * 技術分析周期代碼: 季線
	 */
	final static int ABGW_PRICEDATA_QUARTER = 4;

	/**
	 * 技術分析周期代碼: 季線
	 */
	public final static int ABX_PRICEDATA_QUARTER = 4;
	
	/**
	 * 技術分析周期代碼: 半年線
	 */
	final static int ABGW_PRICEDATA_HYEAR = 5;

	/**
	 * 技術分析周期代碼: 半年線
	 */
	public final static int ABX_PRICEDATA_HYEAR = 5;

	/**
	 * 技術分析周期代碼: 年線
	 */
	final static int ABGW_PRICEDATA_YEAR = 6;

	/**
	 * 技術分析周期代碼: 年線
	 */
	public final static int ABX_PRICEDATA_YEAR = 6;

	/**
	 * 技術分析周期代碼: 1分線
	 */
	final static int ABGW_PRICEDATA_MIN = 10;

	/**
	 * 技術分析周期代碼: 1分線
	 */
	public final static int ABX_PRICEDATA_MIN = 10;

	/**
	 * 技術分析周期代碼: 5分線
	 */
	final static int ABGW_PRICEDATA_MIN5 = 11;

	/**
	 * 技術分析周期代碼: 5分線
	 */
	public final static int ABX_PRICEDATA_MIN5 = 11;

	/**
	 * 技術分析周期代碼: 10分線
	 */
	final static int ABGW_PRICEDATA_MIN10 = 12;

	/**
	 * 技術分析周期代碼: 10分線
	 */
	public final static int ABX_PRICEDATA_MIN10 = 12;

	/**
	 * 技術分析周期代碼: 15分線
	 */
	final static int ABGW_PRICEDATA_MIN15 = 13;

	/**
	 * 技術分析周期代碼: 15分線
	 */
	public final static int ABX_PRICEDATA_MIN15 = 13;

	/**
	 * 技術分析周期代碼: 20分線
	 */
	final static int ABGW_PRICEDATA_MIN20 = 14;

	/**
	 * 技術分析周期代碼: 20分線
	 */
	public final static int ABX_PRICEDATA_MIN20 = 14;

	/**
	 * 技術分析周期代碼: 30分線
	 */
	final static int ABGW_PRICEDATA_MIN30 = 15;

	/**
	 * 技術分析周期代碼: 30分線
	 */
	public final static int ABX_PRICEDATA_MIN30 = 15;

	/**
	 * 技術分析周期代碼: 60分線
	 */
	final static int ABGW_PRICEDATA_MIN60 = 16;

	/**
	 * 技術分析周期代碼: 60分線
	 */
	public final static int ABX_PRICEDATA_MIN60 = 16;

	/**
	 * 技術分析周期代碼: 往前復權基準值
	 */
	final static int ABGW_PRICEDATA_FRBASE = 100;

	/**
	 * 技術分析周期代碼: 往前復權基準值
	 */
	public final static int ABX_PRICEDATA_FRBASE = 100;

	/**
	 * 技術分析周期代碼: 往後復權基準值
	 */
	final static int ABGW_PRICEDATA_BRBASE = 200;

	/**
	 * 技術分析周期代碼: 往後復權基準值
	 */
	public final static int ABX_PRICEDATA_BRBASE = 200;

	/**
	 * 開盤價
	 */
	final static int ABGW_FIELD_OPENP = 0x1;

	/**
	 * 開盤價
	 */
	public final static int ABX_FIELD_OPENP = 0x1;

	/**
	 * 最高價
	 */
	final static int ABGW_FIELD_HIGHP = 0x2;

	/**
	 * 最高價
	 */
	public final static int ABX_FIELD_HIGHP = 0x2;

	/**
	 * 最低價
	 */
	final static int ABGW_FIELD_LOWP = 0x4;

	/**
	 * 最低價
	 */
	public final static int ABX_FIELD_LOWP = 0x4;

	/**
	 * 收盤價，期貨：結算價
	 */
	final static int ABGW_FIELD_CLOSEP = 0x8;

	/**
	 * 收盤價，期貨：結算價
	 */
	public final static int ABX_FIELD_CLOSEP = 0x8;

	/**
	 * 開盤參考價
	 */
	final static int ABGW_FIELD_OPENREFP = 0x10;

	/**
	 * 開盤參考價
	 */
	public final static int ABX_FIELD_OPENREFP = 0x10;

	/**
	 * 成交量
	 */
	final static int ABGW_FIELD_VOL = 0x20;

	/**
	 * 成交量
	 */
	public final static int ABX_FIELD_VOL = 0x20;

	/**
	 * 成交金額
	 */
	final static int ABGW_FIELD_AMT = 0x40;

	/**
	 * 成交金額
	 */
	public final static int ABX_FIELD_AMT = 0x40;

	/**
	 * 成交筆數
	 */
	final static int ABGW_FIELD_REC = 0x80;

	/**
	 * 成交筆數
	 */
	public final static int ABX_FIELD_REC = 0x80;

	/**
	 * 成交量換手率
	 */
	final static int ABGW_FIELD_VOLCRATE = 0x100;

	/**
	 * 成交量換手率
	 */
	public final static int ABX_FIELD_VOLCRATE = 0x100;

	/**
	 * 未平倉量(限期貨)
	 */
	final static int ABGW_FIELD_OPENVOL = 0x1000;

	/**
	 * 未平倉量(限期貨)
	 */
	public final static int ABX_FIELD_OPENVOL = 0x1000;

	/**
	 * 融資餘額
	 */
	final static int ABGW_FIELD_ACMBALAMT = 0x10000;

	/**
	 * 融資餘額
	 */
	public final static int ABX_FIELD_ACMBALAMT = 0x10000;

	/**
	 * 融券餘量
	 */
	final static int ABGW_FIELD_BONBALQTY = 0x20000;

	/**
	 * 融券餘量
	 */
	public final static int ABX_FIELD_BONBALQTY = 0x20000;

	/**
	 * 融資買入額
	 */
	final static int ABGW_FIELD_ACMBUYAMT = 0x40000;

	/**
	 * 融資買入額
	 */
	public final static int ABX_FIELD_ACMBUYAMT = 0x40000;

	/**
	 * 融券賣出量
	 */
	final static int ABGW_FIELD_BONSELLQTY = 0x80000;

	/**
	 * 融券賣出量
	 */
	public final static int ABX_FIELD_BONSELLQTY = 0x80000;

	/**
	 * 當沖、資券相抵
	 */
	final static int ABGW_FIELD_OFFSETAMT = 0x100000;

	/**
	 * 當沖、資券相抵
	 */
	public final static int ABX_FIELD_OFFSETAMT = 0x100000;

	//*******************
	/**
	 * SecurityKey之初始值(API內部使用)
	 */
	public final static byte INITIAL_SECURITYKEY = -1;
	
	/**
	 * AbyKey中之保留欄位長度(API內部使用)
	 */
	final static int RESERVE_LENGTH = 8;

	/**
	 * 封包之Control Header長度(API內部使用)
	 */
	final static byte CTRL_HEADER_SIZE = 41;

	/**
	 * 當封包壓縮格式不為ABUS_COMPRESS_NULL時之Compress Header之長度
	 */
	public final static int COMPRESS_HEADER_LENGTH = 32;

	/**
	 * 主機類型0-報價主機
	 */
	public static final byte SERVER_TYPE_0 = 0;

	/**
	 * 主機類型1-交易主機
	 */
	public static final byte SERVER_TYPE_1 = 1;

	/**
	 * 主機類型2-回報主機
	 */
	public static final byte SERVER_TYPE_2 = 2;

	/**
	 * 主機類型3-ABUSGW主機
	 */
	public static final byte SERVER_TYPE_3 = 3;

	/**
	 * 連線功能之FunID
	 */
	public final static int ABXFUN_SESSION = 1;

	/**
	 * 訂閱即時報價之FunID
	 */
	public final static int ABXFUN_SUBSCRIBE_QUOTE = 2;

	/**
	 * 訂閱即時短線精靈訊息之FunID
	 */
	public final static int ABXFUN_SUBSCRIBE_SMARTSHORT = 3;

	/**
	 * 訂閱即時排名訊息之FunID
	 */
	public final static int ABXFUN_SUBSCRIBE_SMARTRANK = 4;

	/**
	 * 訂閱即時主力大單訊息之FunID
	 */
	public final static int ABXFUN_SUBSCRIBE_SMARTMASTER = 5;

	/**
	 * 訂閱即時ABus公告訊息之FunID
	 */
	public final static int ABXFUN_SUBSCRIBE_SYSTEMBULLETIN = 6;

	/**
	 * 訂閱即時交易所公告訊息之FunID
	 */
	public final static int ABXFUN_SUBSCRIBE_EXCHANGEBULLETIN = 7;

	/**
	 * 訂閱即時新聞訊息之FunID
	 */
	public final static int ABXFUN_SUBSCRIBE_NEWS = 8;

	/**
	 * 訂閱即時產品公告訊息之FunID
	 */
	public final static int ABXFUN_SUBSCRIBE_PRODUCTBULLETIN = 9;

	/**
	 * 訂閱即時廣告訊息之FunID
	 */
	public final static int ABXFUN_SUBSCRIBE_ADVERTISEMENT = 10;

	/**
	 * 訂閱即時行情報導訊息之FunID
	 */
	public final static int ABXFUN_SUBSCRIBE_MARKETREPORT = 11;

	/**
	 * 訂閱即時交易回報訊息之FunID
	 */
	public final static int ABXFUN_SUBSCRIBE_TRADEREPORT = 12;

	/**
	 * 訂閱即時指標之FunID
	 */
	public final static int ABXFUN_SUBSCRIBE_REALTIMETECHNICAL = 13;

	/**
	 * 訂閱指標連線心跳
	 */
	public final static int ABXFUN_SENDTECHNICAL_KEEPALIVE = 14;

	/**
	 * 訂閱警示訊息之FunID
	 */
	public final static int ABXFUN_SUBSCRIBE_SMARTWARN = 15;

	/**
	 * 報價系統檔案查詢
	 */
	public final static int ABXFUN_DOWNLOAD_FILE = 101;

	/**
	 * 回補FunID之基常數
	 */
	final static int ABXFUN_REBUILD_BASE = 1000;

	/**
	 * 回補即時之FunID
	 */
	public final static int ABXFUN_REBUILD_QUOTE = ABXFUN_REBUILD_BASE + 1;

	/**
	 * 回補多筆成交資訊之FunID
	 */
	public final static int ABXFUN_REBUILD_TRADE = ABXFUN_REBUILD_BASE + 6;

	/**
	 * 回補成交分鐘明細(多筆)之FunID
	 */
	public final static int ABXFUN_REBUILD_MINUTETRADE = ABXFUN_REBUILD_BASE + 7;

	/**
	 * 回補多筆總委買賣量筆及成交筆訊息之FunID
	 */
	public final static int ABXFUN_REBUILD_TOTREFINFO = ABXFUN_REBUILD_BASE + 8;

	/**
	 * 回補某一分鐘之成交明細訊息之FunID
	 */
	public final static int ABXFUN_REBUILD_ONEMINUTETRADE = ABXFUN_REBUILD_BASE + 9;

	/**
	 * 回補多筆交易所統計資料訊息之FunID
	 */
	public final static int ABXFUN_REBUILD_STATISTIC = ABXFUN_REBUILD_BASE + 10;

	/**
	 * 回補逐筆成交上海,深圳資料之FunID
	 */
	public final static int ABXFUN_REBUILD_DETAILTRADE = ABXFUN_REBUILD_BASE + 13;

	/**
	 * 回補逐筆委託-深圳資料之FunID
	 */
	public final static int ABXFUN_REBUILD_DETAILORDER = ABXFUN_REBUILD_BASE + 14;

	/**
	 * 回補零股買賣成交-台灣資料之FunID
	 */
	public final static int ABXFUN_REBUILD_OLDLOT = ABXFUN_REBUILD_BASE + 15;

	/**
	 * 回補盤前虛擬撮合-上海,深圳資料之FunID
	 */
	public final static int ABXFUN_REBUILD_VIRTUALTRADE = ABXFUN_REBUILD_BASE + 16;

	/**
	 * 個股成交分價查詢之FunID
	 */
	public final static int ABXFUN_REBUILD_PRICETRADE = ABXFUN_REBUILD_BASE + 17;

	/**
	 * 回補短線精靈資料之FunID
	 */
	public final static int ABXFUN_REBUILD_SMARTSHORT = ABXFUN_REBUILD_BASE + 50;

	/**
	 * 回補即時排名資料之FunID
	 */
	public final static int ABXFUN_REBUILD_SMARTRANK = ABXFUN_REBUILD_BASE + 51;

	/**
	 * 回補主力大單資料之FunID
	 */
	public final static int ABXFUN_REBUILD_SMARTMASTER = ABXFUN_REBUILD_BASE + 52;

	/**
	 * 回補當日交易所公告標題之FunID
	 */
	public final static int ABXFUN_REBUILD_EXCHANGEBULLETIN = ABXFUN_REBUILD_BASE + 101;

	/**
	 * 回補個股歷史交易所公告標題之FunID
	 */
	public final static int ABXFUN_REBUILD_HISTORYEXCHANGEBULLETIN = ABXFUN_REBUILD_BASE + 102;

	/**
	 * 回補當日新聞標題之FunID
	 */
	public final static int ABXFUN_REBUILD_NEWS = ABXFUN_REBUILD_BASE + 103;

	/**
	 * 回補個股歷史新聞標題之FunID
	 */
	public final static int ABXFUN_REBUILD_HISTORYENEWS = ABXFUN_REBUILD_BASE + 104;

	/**
	 * 回補產品公告標題之FunID
	 */
	public final static int ABXFUN_REBUILD_PRODUCTBULLETIN = ABXFUN_REBUILD_BASE + 201;

	/**
	 * 回補行情報導標題之FunID
	 */
	public final static int ABXFUN_REBUILD_MARKETREPORT = ABXFUN_REBUILD_BASE + 202;

	/**
	 * 回補廣告訊息之FunID
	 */
	public final static int ABXFUN_REBUILD_ADVERTISEMENT = ABXFUN_REBUILD_BASE + 203;

	/**
	 * 下載檔案之基常數
	 */
	final static int ABXFUN_DOWNLOAD_BASE = 2000;
	
	/**
	 * 下載自定(板塊)分類資料之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_BLOCKCLASS = ABXFUN_DOWNLOAD_BASE + 1;

	/**
	 * 下載分類關連股票列表之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_CLASSRELATIONSTOCKLIST = ABXFUN_DOWNLOAD_BASE + 2;

	/**
	 * 下載ABUS錯誤代碼對照檔之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_ERRORCODE = ABXFUN_DOWNLOAD_BASE + 3;

	/**
	 * 下載交易所名稱資料檔之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_EXCHANGELIST = ABXFUN_DOWNLOAD_BASE + 4;

	/**
	 * 下載公式分類資料檔之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_FORMULACLASS = ABXFUN_DOWNLOAD_BASE + 5;

	/**
	 * 下載公式名稱資料檔之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_FORMULALIST = ABXFUN_DOWNLOAD_BASE + 6;

	/**
	 * 下載期權股名檔之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_FUTUREOPTION = ABXFUN_DOWNLOAD_BASE + 7;

	/**
	 * 下載其他分類設定檔之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_OTHERCLASS = ABXFUN_DOWNLOAD_BASE + 8;

	/**
	 * 下載產品功能分類資料檔之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_PRODUCTCLASS = ABXFUN_DOWNLOAD_BASE + 9;

	/**
	 * 下載即時排名分類資料之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_SMARTRANKCLASS = ABXFUN_DOWNLOAD_BASE + 10;

	/**
	 * 下載智慧選股(短線精靈)分類資料之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_SMARTSHORTCLASS = ABXFUN_DOWNLOAD_BASE + 11;

	/**
	 * 下載價差交易分類資料之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_SPREADCLASS = ABXFUN_DOWNLOAD_BASE + 12;

	/**
	 * 下載分類樹之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_STKTYPETREE = ABXFUN_DOWNLOAD_BASE + 13;

	/**
	 * 下載商品分類資料之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_STOCKCLASS = ABXFUN_DOWNLOAD_BASE + 14;

	/**
	 * 下載股票關連分類列表之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_STOCKRELATIONCLASSLIST = ABXFUN_DOWNLOAD_BASE + 15;

	/**
	 * 下載交易所1股票關連交易所2股票資料檔之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_STOCKRELATIONSTOCKLIST = ABXFUN_DOWNLOAD_BASE + 16;

	/**
	 * 下載一般股票關連權證股票(港股)資料檔之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_STOCKRELATIONWARRANTSLIST = ABXFUN_DOWNLOAD_BASE + 17;

	/**
	 * 下載股票名稱(拼音)資料檔之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_STOCKTABLE = ABXFUN_DOWNLOAD_BASE + 18;

	/**
	 * 下載技術指標分類資料檔之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_TECHNICALINDEXCLASS = ABXFUN_DOWNLOAD_BASE + 19;

	/**
	 * 下載技術指標名稱資料檔之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_TECHNICALINDEXLIST = ABXFUN_DOWNLOAD_BASE + 20;

	/**
	 * 下載產業分類資料之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_TRADECLASS = ABXFUN_DOWNLOAD_BASE + 21;

	/**
	 * 下載產業分類資料2之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_TRADECLASS2 = ABXFUN_DOWNLOAD_BASE + 22;

	/**
	 * 下載警示分類資料之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_WARNCLASS = ABXFUN_DOWNLOAD_BASE + 23;

	/**
	 * 查詢開盤後新增股票之FunID
	 */
	public final static int ABXFUN_QUERY_NEWSTOCKDATA = ABXFUN_DOWNLOAD_BASE + 101;

	/**
	 * 查詢ABUS錯誤代碼對照檔之FunID
	 */
	public final static int ABXFUN_QUERY_BASE = 2500;

	/**
	 * 查詢(一個)股票關連(多個)分類之FunID
	 */
	public final static int ABXFUN_QUERY_STOCKRELATIONCLASS = ABXFUN_QUERY_BASE + 1;

	/**
	 * 查詢(一個)分類關連(多個)股票之FunID
	 */
	public final static int ABXFUN_QUERY_CLASSRELATIONSTOCK = ABXFUN_QUERY_BASE + 2;

	/**
	 * 查詢股票除權息資料之FunID
	 */
	public final static int ABXFUN_QUERY_STOCKDIVIDE = ABXFUN_QUERY_BASE + 3;

	/**
	 * 查詢股票歷史報價數據(K線)之FunID
	 */
	public final static int ABXFUN_QUERY_HISTORYQUOTEDATA = ABXFUN_QUERY_BASE + 4;

	/**
	 * 查詢技術指標公式定義之FunID
	 */
	public final static int ABXFUN_QUERY_TECHNICALINDEXDEFINE = ABXFUN_QUERY_BASE + 5;

	/**
	 * 查詢技術指標數據之FunID
	 */
	public final static int ABXFUN_QUERY_TECHNICALINDEXDATA = ABXFUN_QUERY_BASE + 6;

	/**
	 * 股票報價數據下載之FunID
	 */
	public final static int ABXFUN_DOWNLOAD_QUOTEDATA = ABXFUN_QUERY_BASE + 7;

	/**
	 * 查詢交易所公告內容之FunID
	 */
	public final static int ABXFUN_QUERY_EXCHANGEBULLETINCONTENT = ABXFUN_QUERY_BASE + 8;

	/**
	 * 查詢新聞內容之FunID
	 */
	public final static int ABXFUN_QUERY_NEWSCONTENT = ABXFUN_QUERY_BASE + 9;

	/**
	 * 查詢產品公告內容之FunID
	 */
	public final static int ABXFUN_QUERY_PRODUCTBULLETINCONTENT = ABXFUN_QUERY_BASE + 10;

	/**
	 * 查詢行情報導內容之FunID
	 */
	public final static int ABXFUN_QUERY_MARKETREPORTCONTENT = ABXFUN_QUERY_BASE + 11;

	/**
	 * 查詢專家選股訊息之FunID
	 */
	public final static int ABXFUN_QUERY_EXPERTSELECT = ABXFUN_QUERY_BASE + 12;

	/**
	 * 檔差表查詢之FunID
	 */
	public final static int ABXFUN_QUERY_TICKDIFF = ABXFUN_QUERY_BASE + 13;

	/**
	 * 權證相關資料查詢之FunID
	 */
	public final static int ABXFUN_QUERY_WARRANT_RELATIVE = ABXFUN_QUERY_BASE + 14;

	/**
	 * 即時報價數據下載之FunID
	 */
	public final static int ABXFUN_QUERY_SORTQUOTEDATA = ABXFUN_QUERY_BASE + 15;

	/**
	 * 查詢最新報價數據之FunID
	 */
	public final static int ABXFUN_QUERY_LASTQUOTEDATA = ABXFUN_QUERY_BASE + 16;

	/**
	 * 技術指標再查詢之FunID
	 */
	public final static int ABXFUN_REQUERY_TECHNICALINDEXDATA = ABXFUN_QUERY_BASE + 17;

	/**
	 * 查詢交易所資訊之FunID
	 */
	final static int ABXFUN_QUERY_EXCHANGEINFO = ABXFUN_QUERY_BASE + 18;

	/**
	 * 查詢用戶常用技術指標之FunID
	 */
	public final static int ABXFUN_GET_USERTECHNICALINDEXCOMMONSETUP = ABXFUN_QUERY_BASE + 501;

	/**
	 * 查詢用戶技術指標參數預設值之FunID
	 */
	public final static int ABXFUN_GET_USERTECHNICALINDEXSETUP = ABXFUN_QUERY_BASE + 502;

	/**
	 * 查詢用戶系統環境設定列表之FunID
	 */
	public final static int ABXFUN_GET_USERENVIRONMENTLIST = ABXFUN_QUERY_BASE + 503;

	/**
	 * 查詢用戶系統環境設定群組之FunID
	 */
	public final static int ABXFUN_GET_USERENVIRONMENTGROUP = ABXFUN_QUERY_BASE + 504;

	/**
	 * 查詢用戶系統環境設定內容之FunID
	 */
	public final static int ABXFUN_GET_USERENVIRONMENTCONTENT = ABXFUN_QUERY_BASE + 505;

	/**
	 * 查詢用戶意見反饋標題之FunID
	 */
	public final static int ABXFUN_GET_USERFEEDBACK = ABXFUN_QUERY_BASE + 506;

	/**
	 * 查詢用戶意見反饋內容之FunID
	 */
	public final static int ABXFUN_GET_USERFEEDBACKCONTENT = ABXFUN_QUERY_BASE + 507;

	/**
	 * 取得用戶資訊總覧之FunID
	 */
	public final static int ABXFUN_GET_USERINFO = ABXFUN_QUERY_BASE + 508;

	/**
	 * 取得用戶密碼變更之FunID
	 */
	public final static int ABXFUN_CHANGE_USERPASSWORD = ABXFUN_QUERY_BASE + 509;

	/**
	 * 取得用戶密碼檢核之FunID
	 */
	public final static int ABXFUN_CHECK_USERPASSWORD = ABXFUN_QUERY_BASE + 510;

	/**
	 * 用戶警示設定查詢之FunID
	 */
	public final static int ABXFUN_WARNING_QUERY = ABXFUN_QUERY_BASE + 511;

	/**
	 * 用戶警示設定異動之FunID
	 */
	public final static int ABXFUN_WARNING_UPDATE = ABXFUN_QUERY_BASE + 512;

	/**
	 * 用戶警示設定刪除之FunID
	 */
	public final static int ABXFUN_WARNING_DELETE = ABXFUN_QUERY_BASE + 513;

	/**
	 * 是否啟用用戶警示設定之FunID
	 */
	public final static int ABXFUN_WARNING_ONOFF = ABXFUN_QUERY_BASE + 514;

	/**
	 * 簽署契約申請之FunID
	 */
	public final static int ABXFUN_APPLY_NOTICESIGN = ABXFUN_QUERY_BASE + 515;

	/**
	 * 交易及帳務之FunID
	 */
	public final static int ABXFUN_TRADE_BILLS = ABXFUN_QUERY_BASE + 600;

	/**
	 * 交易委託資訊查詢之FunID
	 */
	public final static int ABXFUN_QUERY_TRADEDATA = ABXFUN_QUERY_BASE + 601;

	/**
	 * 傳送訊息至Server之FunID
	 */
	public final static int ABXFUN_SENDMESSAGE_TO_SERVER = ABXFUN_QUERY_BASE + 602;

	/**
	 * 設定類之基本FunID
	 */
	public final static int ABXFUN_SET_BASE = 2600;

	/**
	 * 設定用戶常用技術指標之FunID
	 */
	public final static int ABXFUN_SET_USERTECHNICALINDEXCOMMONSETUP = ABXFUN_SET_BASE + 1;

	/**
	 * 更新用戶技術指標參數預設值之FunID
	 */
	public final static int ABXFUN_SET_USERTECHNICALINDEXSETUP = ABXFUN_SET_BASE + 2;

	/**
	 * 更新用戶系統環境設定群組之FunID
	 */
	public final static int ABXFUN_SET_USERENVIRONMENTGROUP = ABXFUN_SET_BASE + 3;

	/**
	 * 更新用戶系統環境設定內容之FunID
	 */
	public final static int ABXFUN_SET_USERENVIRONMENTCONTENT = ABXFUN_SET_BASE + 4;

	/**
	 * 刪除用戶系統環境設定之FunID
	 */
	public final static int ABXFUN_DELETE_USERENVIRONMENT = ABXFUN_SET_BASE + 5;

	/**
	 * 即時訊息之FunID
	 */
	public final static int ABXFUN_REALTIME = 3000;

	/**
	 * StatusID之Heartbit
	 */
	public final static int ABXMSG_HEARTBIT = 0x00000001;

	/**
	 * StatusID之登入
	 */
	public final static int ABXMSG_LOGIN = 0x00000002;

	/**
	 * StatusID之登入Response
	 */
	//public final static int ABXMSG_LOGONRESP = 0x00000003;

	/**
	 * StatusID之登出
	 */
	public final static int ABXMSG_LOGOUT = 0x00000004;

	/**
	 * StatusID之已連線
	 */
	public final static int ABXMSG_ONCONNECTION = 0x00000005;
	
	/**
	 * StatusID之Socket斷線
	 */
	public final static int ABXMSG_DISCONNECTION = 0x00000006;

	/**
	 * StatusID之相同帳號登入
	 */
	public final static int ABXMSG_ANOTHERLOGIN = 0x00000007;

	/**
	 * StatusID之Socket數據處理
	 */
	public final static int ABXMSG_SOCKET_PROCESS = 0x00000008;

	/**
	 * StatusID之股票清盤之處理
	 */
	public final static int ABXMSG_CLEAR_TRADE = 0x00000009;

	/**
	 * StatusID之基常數
	 */
	public final static int ABXMSG_BASE = 0xf000;

	/**
	 * StatusID之股票基本資料訊息
	 */
	public final static int ABXMSG_STKBASEINFO = ABXMSG_BASE + 1;

	/**
	 * StatusID之股票基本資料訊息
	 */
	public final static int ABXMSG_STKREFINFO = ABXMSG_BASE + 2;

	/**
	 * StatusID之第一檔委買賣
	 */
	public final static int ABXMSG_ORDER_1 = ABXMSG_BASE + 3;

	/**
	 * StatusID之第2至5檔委買資訊
	 */
	public final static int ABXMSG_ORDER_2_5 = ABXMSG_BASE + 4;

	/**
	 * StatusID之第6至10檔委買資訊
	 */
	public final static int ABXMSG_ORDER_6_10 = ABXMSG_BASE + 5;

	/**
	 * StatusID之成交資訊
	 */
	public final static int ABXMSG_TRADE = ABXMSG_BASE + 6;

	/**
	 * StatusID之總委買賣量筆及成交筆資訊
	 */
	public final static int ABXMSG_TOTREFINFO = ABXMSG_BASE + 7;

	/**
	 * StatusID之其他一分鐘資訊
	 */
	public final static int ABXMSG_1MININFO = ABXMSG_BASE + 8;

	/**
	 * StatusID之其他報價資訊
	 */
	public final static int ABXMSG_OTHERS = ABXMSG_BASE + 9;

	/**
	 * StatusID之交易所統計訊息
	 */
	public final static int ABXMSG_STATISTIC = ABXMSG_BASE + 10;

	/**
	 * StatusID之交易所交易狀態訊息
	 */
	public final static int ABXMSG_EXCHANGESTATUS = ABXMSG_BASE + 11;

	/**
	 * StatusID之BrokerQueue資料結構(香港)
	 */
	public final static int ABXMSG_BROKERQUEUE = ABXMSG_BASE + 12;

	/**
	 * StatusID之逐筆成交訊息(目前只support SSE,SZSE)
	 */
	public final static int ABXMSG_DETAILTRADE = ABXMSG_BASE + 13;

	/**
	 * StatusID之逐筆委託訊息(目前只support SZSE)
	 */
	public final static int ABXMSG_DETAILORDER = ABXMSG_BASE + 14;

	/**
	 * StatusID之零股買賣成交訊息(Just for TW)
	 */
	public final static int ABXMSG_OLDLOT = ABXMSG_BASE + 15;

	/**
	 * StatusID之盤前虛擬撮合訊息(Just for SSE,SZSE,HK)
	 */
	public final static int ABXMSG_VIRTUALTRADE = ABXMSG_BASE + 16;

	/**
	 * StatusID之委買賣每檔明細訊息(CHINA Level-2)
	 */
	public final static int ABXMSG_ORDERLIST = ABXMSG_BASE + 17;

	/**
	 * StatusID之一秒快照訊息
	 */
	public final static int ABXMSG_1SECSNAPSHOT = ABXMSG_BASE + 18;

	/**
	 * StatusID之總覧成交明細資訊
	 */
	public final static int ABXMSG_TRADE_OVERVIEW = ABXMSG_BASE + 101;

	/**
	 * StatusID之總覧總委買賣量筆及成交筆資訊
	 */
	public final static int ABXMSG_TOTREFINF_OVERVIEW = ABXMSG_BASE + 102;

	/**
	 * StatusID之總覧交易所統計訊息
	 */
	public final static int ABXMSG_STATISTIC_OVERVIEW = ABXMSG_BASE + 103;

	/**
	 * StatusID之總覧逐筆成交訊息(目前只support SSE, SZSE)
	 */
	public final static int ABXMSG_DETAILTRADE_OVERVIEW = ABXMSG_BASE + 105;

	/**
	 * StatusID之總覧逐筆委託訊息(目前只support SZSE)
	 */
	public final static int ABXMSG_DETAILORDER_OVERVIEW = ABXMSG_BASE + 106;

	/**
	 * StatusID之總覧股票分鐘明細
	 */
	public final static int ABXMSG_MINUTETRADE_OVERVIEW = ABXMSG_BASE + 107;

	/**
	 * StatusID之總覧股票分鐘明細
	 */
	public final static int ABXMSG_PRICETRADE_OVERVIEW = ABXMSG_BASE + 108;

	/**
	 * StatusID之即時K數據
	 */
	public final static int ABXMSG_REALTIME_TECHNICAL = ABXMSG_BASE + 109;

	/**
	 * StatusID之短線精靈資料訊息
	 */
	public final static int ABXMSG_SMARTSHORT = ABXMSG_BASE + 301;

	/**
	 * StatusID之即時排名訊息
	 */
	public final static int ABXMSG_SMARTRANK = ABXMSG_BASE + 302;

	/**
	 * StatusID之主力大單訊息
	 */
	public final static int ABXMSG_SMARTMASTER = ABXMSG_BASE + 303;

	/**
	 * StatusID之用戶警示訊息
	 */
	public final static int ABXMSG_SMARTWARN = ABXMSG_BASE + 304;

	/**
	 * StatusID之廣播訊息-FreeFormat訊息
	 */
	public final static int ABXMSG_SYSTEMBULLETIN = ABXMSG_BASE + 501;

	/**
	 * StatusID之廣播訊息-交易所訊息
	 */
	public final static int ABXMSG_EXCHANGEBULLETIN = ABXMSG_BASE + 502;

	/**
	 * StatusID之廣播訊息-新聞訊息
	 */
	public final static int ABXMSG_NEWS = ABXMSG_BASE + 503;

	/**
	 * StatusID之廣播訊息-產品訊息
	 */
	public final static int ABXMSG_PRODUCT = ABXMSG_BASE + 504;

	/**
	 * StatusID之廣播訊息-交易回報訊息
	 */
	public final static int ABXMSG_TRADEREPORT = ABXMSG_BASE + 505;

	/**
	 * StatusID之廣播訊息-即時指標訊息
	 */
	public final static int ABXMSG_TECHNICAL = ABXMSG_BASE + 506;

	/**
	 * 下載檔案名稱定義: 錯誤代碼對照檔
	 */
	final static String FILENAME_ERRORCODE = "errcode1";

	/**
	 * 下載檔案名稱定義: 交易所名檔
	 */
	public final static String FILENAME_EXCHANGE = "exchange1";

	/**
	 * 下載檔案名稱定義: 公式編輯樹檔
	 */
	public final static String FILENAME_FORMTREE = "formtree";

	/**
	 * 下載檔案名稱定義: 公式列表檔
	 */
	public final static String FILENAME_FORMLIST = "formlist";

	/**
	 * 下載檔案名稱定義: 產品功能類別檔
	 */
	public final static String FILENAME_PCLASS = "pclass";

	/**
	 * 下載檔案名稱定義: 產品收費功能項設定檔
	 */
	public final static String FILENAME_PRODFUN = "prodfun";

	/**
	 * 下載檔案名稱定義: 股名檔
	 */
	public final static String FILENAME_STKTBL = "stktbl";

	/**
	 * 下載檔案名稱定義: 交易所1股票關連交易所2股票檔
	 */
	public final static String FILENAME_STKLINK = "stklink";

	/**
	 * 下載檔案名稱定義: 一般股票關連權證股票對應檔(目前只有港股)
	 */
	public final static String FILENAME_STKWAR = "stkwar";

	/**
	 * 下載檔案名稱定義: 分類關連股票索引檔
	 */
	public final static String FILENAME_STKIDXC = "stkidxc";

	/**
	 * 下載檔案名稱定義: 股票關連分類索引檔
	 */
	public final static String FILENAME_STKIDXS = "stkidxs";

	/**
	 * 下載檔案名稱定義: 商品大分類設定檔
	 */
	public final static String FILENAME_STKTYPEC = "stktypec1";

	/**
	 * 下載檔案名稱定義: 產業分類設定檔
	 */
	public final static String FILENAME_STKTYPET = "stktypet1";

	/**
	 * 下載檔案名稱定義: 產業分類設定檔2
	 */
	public final static String FILENAME_STKTYPET2 = "stktypet2";

	/**
	 * 下載檔案名稱定義: 自定(板塊)分類設定檔
	 */
	public final static String FILENAME_STKTYPEB = "stktypeb1";

	/**
	 * 下載檔案名稱定義: 短線精靈分類設定檔
	 */
	public final static String FILENAME_STKTYPES = "stktypes1";

	/**
	 * 下載檔案名稱定義: 排名分類設定檔
	 */
	public final static String FILENAME_STKTYPER = "stktyper1";

	/**
	 * 下載檔案名稱定義: 期貨價差交易分類設定檔
	 */
	public final static String FILENAME_STKTYPEP = "stktypep1";

	/**
	 * 下載檔案名稱定義: 其他分類設定檔
	 */
	public final static String FILENAME_STKTYPEO = "stktypeo1";

	/**
	 * 下載檔案名稱定義: 分類樹
	 */
	public final static String FILENAME_STKTYPETREE = "stktypetree";

	/**
	 * 下載檔案名稱定義: 警示檔
	 */
	public final static String FILENAME_WARN = "warn";

}
