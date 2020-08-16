package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXExchangeStatus;

/**
 * 交易所交易狀態訊息的類別
 * @author Jason
 *
 */
final class ZJABXWatchExchangeStatus implements IJABXExchangeStatus {

	/**
	 * 當地日期時間(yyyyMMddHHmmss)
	 */
	private long localTime;

	/**
	 * 交易狀態
	 */
	private String tradeStatus;

	/**
	 * 最後收盤日(yyyyMMdd)
	 */
	private int lastCloseDate;

	/**
	 * 最後交易日(yyyyMMdd)
	 */
	private int lastTradeDate;

	/**
	 * 市場特殊狀態
	 */
	private String marketStatus;
	
	/**
	 * @see com.syt.jabx.watch.IJABXExchangeStatus#getLocalTime()
	 */
	@Override
	public long getLocalTime() {
		// TODO Auto-generated method stub
		return localTime;
	}

	/**
	 * 設定當地日期時間(yyyyMMddHHmmss)
	 * @param localTime - long
	 */
	public void setLocalTime(long localTime) {
		this.localTime = localTime;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXExchangeStatus#getTradeStatus()
	 */
	@Override
	public String getTradeStatus() {
		// TODO Auto-generated method stub
		return tradeStatus;
	}

	/**
	 * 設定交易狀態
	 * @param tradeStatus - String
	 */
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXExchangeStatus#getLastCloseDate()
	 */
	@Override
	public int getLastCloseDate() {
		// TODO Auto-generated method stub
		return lastCloseDate;
	}

	/**
	 * 設定最後收盤日(yyyyMMdd)
	 * @param lastCloseDate - int
	 */
	public void setLastCloseDate(int lastCloseDate) {
		this.lastCloseDate = lastCloseDate;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXExchangeStatus#getLastTradeDate()
	 */
	@Override
	public int getLastTradeDate() {
		// TODO Auto-generated method stub
		return lastTradeDate;
	}

	/**
	 * 設定最後交易日(yyyyMMdd)
	 * @param lastTradeDate - int
	 */
	public void setLastTradeDate(int lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXExchangeStatus#getMarketStatus()
	 */
	@Override
	public String getMarketStatus() {
		// TODO Auto-generated method stub
		return marketStatus;
	}

	/**
	 * 設定市場特殊狀態
	 * @param marketStatus - String
	 */
	public void setMarketStatus(String marketStatus) {
		this.marketStatus = marketStatus;
	}

	/**
	 * 將類別中所用到的欄位設定為零或空白
	 */
	public void setDataToZeroOrSpace() {
		localTime = 0;
		tradeStatus = "";
		lastCloseDate = 0;
		lastTradeDate = 0;
		marketStatus = "";
	}
}
