package com.syt.jabx.kmodel;

/**
 * 交易所基本資料介面
 * @author jason
 *
 */
public interface IJABXExchangeInfo {

	/**
	 * 取得交易所代碼
	 * @return String
	 */
	public String getExchange();

	/**
	 * 取得交易所名稱(Utf-8編碼)
	 * @return String
	 */
	public String getExchangeName();

	/**
	 * 取得交易所屬性<br>
	 * S:證券交易<br>
	 * F:期貨交易<br>
	 * M:多時期貨交易<br>
	 * @return String
	 */
	public String getExchangeNote();

	/**
	 * 取得時差(已經過日光節約時間處理後的時差)
	 * @return short
	 */
	public short getTimeZone();

	/**
	 * 取得交易所盤前時間(交易所當地時間HHmm，可能有買賣價,假開盤資料)
	 * @return short
	 */
	public short getPreOpenTime();

	/**
	 * 取得交易所開盤時間(交易所當地時間HHmm)
	 * @return short
	 */
	public short getOpenTime();

	/**
	 * 取得交易所收盤時間(交易所當地時間HHmm)
	 * @return short
	 */
	public short getCloseTime();

	/**
	 * 取得交易所休息時間1(交易所當地時間HHmm，-1:沒有休息)
	 * @return short
	 */
	public short getRestTime1();

	/**
	 * 取得交易所再開盤時間1(交易所當地時間HHmm，-1:沒有再開盤)
	 * @return short
	 */
	public short getReOpenTime1();

	/**
	 * 取得交易所休息時間2(交易所當地時間HHmm，-1:没有休息)
	 * @return short
	 */
	public short getRestTime2();

	/**
	 * 取得交易所再開盤時間2(交易所當地時間HHmm，-1:没有再開盤)
	 * @return short
	 */
	public short getReOpenTime2();

	/**
	 * 取得交易所盤後交易開始時間(交易所當地時間HHmm，-1:没有盤後交易)<br>
	 * 台股TSE(T1), OTC(T2)：定價,零股<br>
	 * 美股：電子交易
	 * @return short
	 */
	public short getAfterTradeStartTime();

	/**
	 * 取得交易所盤後交易結束時間(交易所當地時間HHmm，-1:没有盤後交易)
	 * @return short
	 */
	public short getAfterTradeEndTime();

	/**
	 * 取得系統強制收盤時間(交易所當地時間HHmm)
	 * @return short
	 */
	public short getSysCloseTime();

	/**
	 * 取得代表指數證券全代碼(長度22，交易所代碼+證券代碼)
	 * @return String
	 */
	public String getRefIdxCode();

	/**
	 * 取得小數位數(限非多時期交所有效)
	 * @return short
	 */
	public short getDecimalShow();

	/**
	 * 取得交易所交易狀態<br>
	 * 0-非盤中時段(ABUS_XSTATUS_NOTTRADE)，一般為00:00-&gt;Clear<br>
	 * 1-清盤時段(ABUS_XSTATUS_CLEAR)<br>
	 * 2-盤前時段,可下單(ABUS_XSTATUS_PREOPEN)<br>
	 * 3-盤中時段(ABUS_XSTATUS_TRADE)<br>
	 * 6-盤後交易時段(ABUS_XSTATUS_AFTERTRADE)(TW,US)
	 * @return short
	 */
	public short getTradeStatus();

	/**
	 * 取得市場狀態<br>
	 * 0-正常狀態(ABUS_HALTSTATUS_NORMAL)<br>
	 * 1-交易所Halt(ABUS_HALTSTATUS_MARKETHALT)<br>
	 * C-收盤前競價(ABUS_MARKETSTATUS_CAS)(HK)
	 * @return String
	 */
	public String getHaltStatus();

	/**
	 * 取得系統狀態旗標(X(32)，每個字元代表一個系統狀態旗標)<br>
	 * 系統狀態位置見ABus常數定義SYSTEM FLAG<br>
	 * [0]-ABUS清盤(ABUS_SYSFLAG_ABRESET)<br>
	 * [1]-ABUS交易所參考價(ABUS_SYSFLAG_ABREFPRC)<br>
	 * [2]-ABUS收盤(ABUS_SYSFLAG_ABCLOSE)<br>
	 * [30]-ABGW收盤(ABUS_SYSFLAG_AGCLOSE)<br>
	 * [31]-ABUS清盤(ABUS_SYSFLAG_AGRESET)
	 * @return String
	 */
	public String getSystemFlag();

	/**
	 * 取得最新交易日期(yyyyMMdd)
	 * @return int
	 */
	public int getTradeDate();

	/**
	 * 取得收盤日期(yyyyMMdd)
	 * @return int
	 */
	public int getCloseDate();

	/**
	 * 取得下一個休假日期(yyyyMMdd)
	 * @return int
	 */
	public int getNonTradeDate();

}
