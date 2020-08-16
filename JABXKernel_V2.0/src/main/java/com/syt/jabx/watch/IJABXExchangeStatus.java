package com.syt.jabx.watch;

/**
 * 交易所交易狀態訊息的介面
 * @author Jason
 *
 */
public interface IJABXExchangeStatus {

	/**
	 * 取得當地日期時間(yyyyMMddHHmmss)
	 * @return long
	 */
	public long getLocalTime();

	/**
	 * 取得交易狀態
	 * @return String
	 */
	public String getTradeStatus();

	/**
	 * 取得最後收盤日(yyyyMMdd)
	 * @return int
	 */
	public int getLastCloseDate();

	/**
	 * 取得最後交易日(yyyyMMdd)
	 * @return int
	 */
	public int getLastTradeDate();

	/**
	 * 取得市場特殊狀態
	 * @return String
	 */
	public String getMarketStatus();
}
