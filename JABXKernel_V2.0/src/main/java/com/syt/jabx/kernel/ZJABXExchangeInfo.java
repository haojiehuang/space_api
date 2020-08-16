package com.syt.jabx.kernel;

import com.syt.jabx.kmodel.IJABXExchangeInfo;

/**
 * 交易所列表項目之類別
 * @author Jason
 *
 */
final class ZJABXExchangeInfo implements IJABXExchangeInfo {

	/**
	 * 交易所代碼
	 */
	private String exchange;

	/**
	 * 交易所名稱
	 */
	private String exchangeName;

	/**
	 * 交易所屬性
	 */
	private String exchangeNote;

	/**
	 * 時差(已經過日光節約時間處理後的時差)
	 */
	private short timeZone;

	/**
	 * 交易所盤前時間(交易所當地時間HHmm，可能有買賣價,假開盤資料)
	 */
	private short preOpenTime;

	/**
	 * 交易所開盤時間(交易所當地時間HHmm)
	 */
	private short openTime;

	/**
	 * 交易所收盤時間(交易所當地時間HHmm)
	 */
	private short closeTime;

	/**
	 * 交易所休息時間1(交易所當地時間HHmm，-1:沒有休息)
	 */
	private short restTime1;

	/**
	 * 交易所再開盤時間1(交易所當地時間HHmm，-1:沒有再開盤)
	 */
	private short reOpenTime1;

	/**
	 * 交易所休息時間2(交易所當地時間HHmm，-1:没有休息)
	 */
	private short restTime2;

	/**
	 * 交易所再開盤時間2(交易所當地時間HHmm，-1:没有再開盤)
	 */
	private short reOpenTime2;

	/**
	 * 交易所盤後交易開始時間(交易所當地時間HHmm，-1:没有盤後交易)<br />
	 * 台股TSE(T1), OTC(T2)：定價,零股<br />
	 * 美股：電子交易
	 */
	private short afterTradeStartTime;

	/**
	 * 交易所盤後交易結束時間(交易所當地時間HHmm，-1:没有盤後交易)
	 */
	private short afterTradeEndTime;

	/**
	 * 系統強制收盤時間(交易所當地時間HHmm)
	 */
	private short sysCloseTime;

	/**
	 * 代表指數證券全代碼(長度22，交易所代碼+證券代碼)
	 */
	private String refIdxCode;

	/**
	 * 小數位數(限非多時期交所有效)
	 */
	private short decimalShow;

	/**
	 * 交易所交易狀態<br />
	 * 0-非盤中時段(ABUS_XSTATUS_NOTTRADE)，一般為00:00->Clear<br />
	 * 1-清盤時段(ABUS_XSTATUS_CLEAR)<br />
	 * 2-盤前時段,可下單(ABUS_XSTATUS_PREOPEN)<br />
	 * 3-盤中時段(ABUS_XSTATUS_TRADE)<br />
	 * 6-盤後交易時段(ABUS_XSTATUS_AFTERTRADE)(TW,US)
	 */
	private short tradeStatus;

	/**
	 * 市場狀態<br />
	 * 0-正常狀態(ABUS_HALTSTATUS_NORMAL)<br />
	 * 1-交易所Halt(ABUS_HALTSTATUS_MARKETHALT)<br />
	 * C-收盤前競價(ABUS_MARKETSTATUS_CAS)(HK)
	 */
	private String haltStatus;

	/**
	 * 系統狀態旗標(X(32)，每個字元代表一個系統狀態旗標)<br />
	 * 系統狀態位置見ABus常數定義SYSTEM FLAG<br />
	 * [0]-ABUS清盤(ABUS_SYSFLAG_ABRESET)<br />
	 * [1]-ABUS交易所參考價(ABUS_SYSFLAG_ABREFPRC)<br />
	 * [2]-ABUS收盤(ABUS_SYSFLAG_ABCLOSE)<br />
	 * [30]-ABGW收盤(ABUS_SYSFLAG_AGCLOSE)<br />
	 * [31]-ABUS清盤(ABUS_SYSFLAG_AGRESET)
	 */
	private String systemFlag;

	/**
	 * 最新交易日期(yyyyMMdd)
	 */
	private int tradeDate;

	/**
	 * 最新收盤日期(yyyyMMdd)
	 */
	private int closeDate;

	/**
	 * 下一個休假日期(yyyyMMdd)
	 */
	private int nonTradeDate;

	/**
	 * Constructor
	 */
	public ZJABXExchangeInfo() {
		exchange = "";
		exchangeName = "";
		exchangeNote = "";
		refIdxCode = "";
		haltStatus = "";
		systemFlag = "";
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getExchange()
	 */
	@Override
	public String getExchange() {
		// TODO Auto-generated method stub
		return exchange;
	}

	/**
	 * 設定交易所代碼
	 * @param exchange - String
	 */
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getExchangeName()
	 */
	@Override
	public String getExchangeName() {
		// TODO Auto-generated method stub
		return exchangeName;
	}

	/**
	 * 設定交易所名稱
	 * @param exchangeName - String
	 */
	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getExchangeNote()
	 */
	@Override
	public String getExchangeNote() {
		// TODO Auto-generated method stub
		return exchangeNote;
	}

	/**
	 * 設定交易所屬性
	 * @param exchangeNote - String
	 */
	public void setExchangeNote(String exchangeNote) {
		this.exchangeNote = exchangeNote;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getTimeZone()
	 */
	@Override
	public short getTimeZone() {
		// TODO Auto-generated method stub
		return timeZone;
	}

	/**
	 * 設定時差(已經過日光節約時間處理後的時差)
	 * @param timeZone - short
	 */
	public void setTimeZone(short timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getPreOpenTime()
	 */
	@Override
	public short getPreOpenTime() {
		// TODO Auto-generated method stub
		return preOpenTime;
	}
 
	/**
	 * 設定交易所盤前時間(交易所當地時間HHmm，可能有買賣價,假開盤資料)
	 * @param preOpenTime - short
	 */
	public void setPreOpenTime(short preOpenTime) {
		this.preOpenTime = preOpenTime;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getOpenTime()
	 */
	@Override
	public short getOpenTime() {
		// TODO Auto-generated method stub
		return openTime;
	}

	/**
	 * 設定交易所開盤時間(交易所當地時間HHmm)
	 * @param openTime - short
	 */
	public void setOpenTime(short openTime) {
		this.openTime = openTime;
	}
	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getCloseTime()
	 */
	@Override
	public short getCloseTime() {
		// TODO Auto-generated method stub
		return closeTime;
	}

	/**
	 * 設定交易所收盤時間(交易所當地時間HHmm)
	 * @param closeTime - short
	 */
	public void setCloseTime(short closeTime) {
		this.closeTime = closeTime;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getRestTime1()
	 */
	@Override
	public short getRestTime1() {
		// TODO Auto-generated method stub
		return restTime1;
	}

	/**
	 * 設定交易所休息時間1(交易所當地時間HHmm，-1:沒有休息)
	 * @param restTime1 - short
	 */
	public void setRestTime1(short restTime1) {
		this.restTime1 = restTime1;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getReOpenTime1()
	 */
	@Override
	public short getReOpenTime1() {
		// TODO Auto-generated method stub
		return reOpenTime1;
	}

	/**
	 * 設定交易所再開盤時間1(交易所當地時間HHmm，-1:沒有再開盤)
	 * @param reOpenTime1 - short
	 */
	public void setReOpenTime1(short reOpenTime1) {
		this.reOpenTime1 = reOpenTime1;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getRestTime2()
	 */
	@Override
	public short getRestTime2() {
		// TODO Auto-generated method stub
		return restTime2;
	}

	/**
	 * 設定交易所休息時間2(交易所當地時間HHmm，-1:没有休息)
	 * @param restTime2 - short
	 */
	public void setRestTime2(short restTime2) {
		this.restTime2 = restTime2;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getReOpenTime2()
	 */
	@Override
	public short getReOpenTime2() {
		// TODO Auto-generated method stub
		return reOpenTime2;
	}

	/**
	 * 設定交易所再開盤時間2(交易所當地時間HHmm，-1:没有再開盤)
	 * @param reOpenTime2 - short
	 */
	public void setReOpenTime2(short reOpenTime2) {
		this.reOpenTime2 = reOpenTime2;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getAfterTradeStartTime()
	 */
	@Override
	public short getAfterTradeStartTime() {
		// TODO Auto-generated method stub
		return afterTradeStartTime;
	}

	/**
	 * 設定交易所盤後交易開始時間(交易所當地時間HHmm，-1:没有盤後交易)
	 * @param afterTradeStartTime - short
	 */
	public void setAfterTradeStartTime(short afterTradeStartTime) {
		this.afterTradeStartTime = afterTradeStartTime;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getAfterTradeEndTime()
	 */
	@Override
	public short getAfterTradeEndTime() {
		// TODO Auto-generated method stub
		return afterTradeEndTime;
	}

	/**
	 * 設定交易所盤後交易結束時間(交易所當地時間HHmm，-1:没有盤後交易)
	 * @param afterTradeEndTime - short
	 */
	public void setAfterTradeEndTime(short afterTradeEndTime) {
		this.afterTradeEndTime = afterTradeEndTime;
	}
	
	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getSysCloseTime()
	 */
	@Override
	public short getSysCloseTime() {
		// TODO Auto-generated method stub
		return sysCloseTime;
	}

	/**
	 * 設定系統強制收盤時間(交易所當地時間HHmm)
	 * @param sysCloseTime - short
	 */
	public void setSysCloseTime(short sysCloseTime) {
		this.sysCloseTime = sysCloseTime;
	}
	
	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getRefIdxCode()
	 */
	@Override
	public String getRefIdxCode() {
		// TODO Auto-generated method stub
		return refIdxCode;
	}

	/**
	 * 設定代表指數證券全代碼(長度22，交易所代碼+證券代碼)
	 * @param refIdxCode - String
	 */
	public void setRefIdxCode(String refIdxCode) {
		this.refIdxCode = refIdxCode;
	}
	
	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getDecimalShow()
	 */
	@Override
	public short getDecimalShow() {
		// TODO Auto-generated method stub
		return decimalShow;
	}

	/**
	 * 設定小數位數(限非多時期交所有效)
	 * @param decimalShow - short
	 */
	public void setDecimalShow(short decimalShow) {
		this.decimalShow = decimalShow;
	}
	
	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getTradeStatus()
	 */
	@Override
	public short getTradeStatus() {
		// TODO Auto-generated method stub
		return tradeStatus;
	}

	/**
	 * 設定交易所交易狀態
	 * @param tradeStatus - short
	 */
	public void setTradeStatus(short tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getHaltStatus()
	 */
	@Override
	public String getHaltStatus() {
		// TODO Auto-generated method stub
		return haltStatus;
	}

	/**
	 * 設定市場狀態
	 * @param haltStatus - String
	 */
	public void setHaltStatus(String haltStatus) {
		this.haltStatus = haltStatus;
	}
	
	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getSystemFlag()
	 */
	@Override
	public String getSystemFlag() {
		// TODO Auto-generated method stub
		return systemFlag;
	}

	/**
	 * 設定系統狀態旗標(X(32)，每個字元代表一個系統狀態旗標)
	 * @param systemFlag - String
	 */
	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}
	
	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getTradeDate()
	 */
	@Override
	public int getTradeDate() {
		// TODO Auto-generated method stub
		return tradeDate;
	}

	/**
	 * 設定最新交易日期(yyyyMMdd)
	 * @param tradeDate - int
	 */
	public void setTradeDate(int tradeDate) {
		this.tradeDate = tradeDate;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getCloseDate()
	 */
	@Override
	public int getCloseDate() {
		// TODO Auto-generated method stub
		return closeDate;
	}

	/**
	 * 設定最新收盤日期(yyyyMMdd)
	 * @param closeDate - int
	 */
	public void setCloseDate(int closeDate) {
		this.closeDate = closeDate;
	}
	
	/**
	 * @see com.syt.jabx.query.IJABXExchangeInfo#getNonTradeDate()
	 */
	@Override
	public int getNonTradeDate() {
		// TODO Auto-generated method stub
		return nonTradeDate;
	}

	/**
	 * 設定下一個休假日期(yyyyMMdd)
	 * @param nonTradeDate - int
	 */
	public void setNonTradeDate(int nonTradeDate) {
		this.nonTradeDate = nonTradeDate;
	}
}