package com.syt.jabx.watch;

/**
 * 成交價量明細項目介面
 * @author jason
 *
 */
public interface IJABXPriceTradeItem {

	/**
	 * 取得成交價
	 * @return int
	 */
	public int getTradePrice();

	/**
	 * 取得成交量
	 * @return long
	 */
	public long getTradeVolume();

	/**
	 * 取得內盤量
	 * @return long
	 */
	public long getInVolume();

	/**
	 * 取得外盤量
	 * @return long
	 */
	public long getOutVolume();
}
