package com.syt.jabx.consts;

/**
 * 交易所訊息所使用之常數類別
 * @author jason
 *
 */
public class JS_ExchangeInfo {

	/**
	 * 交易所盤後交易結束時間(交易所當地時間hhmm，-1:没有盤後交易)(short)
	 */
	public static String AFTER_TRADE_END_TIME = "ate";

	/**
	 * 交易所盤後交易開始時間(交易所當地時間hhmm，-1:没有盤後交易)(short)<br>
	 * 台股TSE(T1), OTC(T2)：定價,零股<br>
	 * 美股：電子交易
	 */
	public static String AFTER_TRADE_START_TIME = "ats";

	/**
	 * 最新收盤日期(yyyyMMdd)(int)
	 */
	public static String CLOSE_DATE = "ced";

	/**
	 * 交易所收盤時間(交易所當地時間hhmm)(short)
	 */
	public static String CLOSE_TIME = "cet";

	/**
	 * 小數位數(限非多時期交所有效)(short)
	 */
	public static String DECIMAL_SHOW = "dls";
	
	/**
	 * 交易所代碼(String)
	 */
	public static String EXCHANGE = "exc";

	/**
	 * 交易所名稱(String)
	 */
	public static String EXCHANGE_NAME = "een";

	/**
	 * 交易所屬性(String)
	 */
	public static String EXCHANGE_NOTE = "eee";

	/**
	 * 市場狀態(String)<br>
	 * 0-正常狀態(ABUS_HALTSTATUS_NORMAL)<br>
	 * 1-交易所Halt(ABUS_HALTSTATUS_MARKETHALT)<br>
	 * C-收盤前競價(ABUS_MARKETSTATUS_CAS)(HK)
	 */
	public static String HALT_STATUS = "hts";

	/**
	 * 下一個休假日期(yyyyMMdd)(int)
	 */
	public static String NON_TRADE_DATE = "ntd";
	
	/**
	 * 交易所開盤時間(交易所當地時間hhmm)(short)
	 */
	public static String OPEN_TIME = "ont";

	/**
	 * 交易所盤前時間(交易所當地時間hhmm，可能有買賣價,假開盤資料)(short)
	 */
	public static String PRE_OPEN_TIME = "pot";

	/**
	 * 交易所再開盤時間1(交易所當地時間hhmm，-1:沒有再開盤)(short)
	 */
	public static String RE_OPEN_TIME1 = "ro1";

	/**
	 * 交易所再開盤時間2(交易所當地時間hhmm，-1:没有再開盤)(short)
	 */
	public static String RE_OPEN_TIME2 = "ro2";

	/**
	 * 代表指數證券全代碼(長度22，交易所代碼+證券代碼)(String)
	 */
	public static String REF_IDX_CODE = "ric";

	/**
	 * 交易所休息時間1(交易所當地時間hhmm，-1:沒有休息)(short)
	 */
	public static String REST_TIME1 = "rt1";

	/**
	 * 交易所休息時間2(交易所當地時間hhmm，-1:没有休息)(short)
	 */
	public static String REST_TIME2 = "rt2";

	/**
	 * 系統強制收盤時間(交易所當地時間hhmm)(short)
	 */
	public static String SYS_CLOSE_TIME = "sct";

	/**
	 * 系統狀態旗標(X(32)，每個字元代表一個系統狀態旗標)(String)<br>
	 * 系統狀態位置見ABus常數定義SYSTEM FLAG<br>
	 * [0]-ABUS清盤(ABUS_SYSFLAG_ABRESET)<br>
	 * [1]-ABUS交易所參考價(ABUS_SYSFLAG_ABREFPRC)<br>
	 * [2]-ABUS收盤(ABUS_SYSFLAG_ABCLOSE)<br>
	 * [30]-ABGW收盤(ABUS_SYSFLAG_AGCLOSE)<br>
	 * [31]-ABUS清盤(ABUS_SYSFLAG_AGRESET)
	 */
	public static String SYSTEM_FLAG = "smf";
	
	/**
	 * 時差(已經過日光節約時間處理後的時差)(short)
	 */
	public static String TIME_ZONE = "tez";

	/**
	 * 最新交易日期(yyyyMMdd)(int)
	 */
	public static String TRADE_DATE = "ted";

	/**
	 * 交易所交易狀態(short)<br>
	 * 0-非盤中時段(ABUS_XSTATUS_NOTTRADE)，一般為00:00-&lt;Clear<br>
	 * 1-清盤時段(ABUS_XSTATUS_CLEAR)<br>
	 * 2-盤前時段,可下單(ABUS_XSTATUS_PREOPEN)<br>
	 * 3-盤中時段(ABUS_XSTATUS_TRADE)<br>
	 * 6-盤後交易時段(ABUS_XSTATUS_AFTERTRADE)(TW,US)
	 */
	public static String TRADE_STATUS = "tes";
}
