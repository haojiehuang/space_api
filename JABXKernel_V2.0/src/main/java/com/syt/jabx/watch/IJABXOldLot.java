package com.syt.jabx.watch;

/**
 * 零股買賣成交訊息(Just for TW)之介面
 * @author Jason
 *
 */
public interface IJABXOldLot {

	/**
	 * 取得委買價
	 * @return int
	 */
	public int getBidPrice();

	/**
	 * 取得委買量
	 * @return int
	 */
	public int getBidVolume();

	/**
	 * 取得委賣價
	 * @return int
	 */
	public int getAskPrice();

	/**
	 * 取得委賣量
	 * @return int
	 */
	public int getAskVolume();

	/**
	 * 取得成交時間(HHmmsssss,後3sss 為 mini sec)
	 * @return int
	 */
	public int getTradeTime();

	/**
	 * 取得成交價
	 * @return int
	 */
	public int getTradePrice();

	/**
	 * 取得成交單量(單位:股)
	 * @return int
	 */
	public int getTradeVolume();

	/**
	 * 取得內外盤
	 * @return int
	 */
	public int getInOutFlag();
}
