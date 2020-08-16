package com.syt.jabx.watch;

/**
 * 即時總委買賣量筆及成交筆資訊的介面
 * @author Jason
 *
 */
public interface IJABXTotRefInfo {

	/**
	 * 取得統計時間(HHmm)
	 * @return int
	 */
	public int getTradeTime();

	/**
	 * 取得委賣總量
	 * @return long
	 */
	public long getTotAskVolume();

	/**
	 * 取得委賣總筆
	 * @return int
	 */
	public int getTotAskCount();

	/**
	 * 取得委買總量
	 * @return long
	 */
	public long getTotBidVolume();

	/**
	 * 取得委買總筆
	 * @return int
	 */
	public int getTotBidCount();

	/**
	 * 取得買進成交總筆(Just for TAIFEX)
	 * @return int
	 */
	public int getTotBidTradeCount();

	/**
	 * 取得賣出成交總筆(Just for TAIFEX)
	 * @return int
	 */
	public int getTotAskTradeCount();
}
