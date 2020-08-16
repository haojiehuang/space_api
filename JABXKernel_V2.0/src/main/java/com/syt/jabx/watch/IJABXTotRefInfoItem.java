package com.syt.jabx.watch;

/**
 * 總委買賣量筆及成交筆資訊的介面
 * @author Jason
 *
 */
public interface IJABXTotRefInfoItem extends IJABXTotRefInfo {

	/**
	 * 取得成交總筆(rebuild多筆才有)
	 * @return int 
	 */
	public int getTotTradeCount();

	/**
	 * 取得成交總量(rebuild多筆才有)
	 * @return long
	 */
	public long getTotTradeVolume();

	/**
	 * 取得成交總金額(rebuild多筆才有)
	 * @return long
	 */
	public long getTotTradeAmount();

	/**
	 * 取得成交價(rebuild多筆才有)
	 * @return int
	 */
	public int getTradePrice();
}
