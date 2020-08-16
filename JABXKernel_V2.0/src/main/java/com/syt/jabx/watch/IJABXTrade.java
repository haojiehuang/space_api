package com.syt.jabx.watch;

/**
 * 股票成交分筆項目的介面
 * @author Jason
 *
 */
public interface IJABXTrade {

	/**
	 * 取得成交序號
	 * @return int
	 */
	public int getSeqNo();

	/**
	 * 取得成交時間(HHmmsssss,後3sss 為 minisec)
	 * @return int
	 */
	public int getTradeTime();

	/**
	 * 取得成交註記
	 * @return String
	 */
	public String getTradeNote();

	/**
	 * 取得成交價
	 * @return int
	 */
	public int getTradePrice();

	/**
	 * 取得成交單量
	 * @return int
	 */
	public int getTradeVolume();

	/**
	 * 取得單量內外盤
	 * @return int
	 */
	public int getVolumeInOutFlag();

	/**
	 * 取得成交總量
	 * @return long
	 */
	public long getTotTradeVolume();

	/**
	 * 取得成交總金額
	 * @return long
	 */
	public long getTotTradeAmount();

	/**
	 * 取得成交總筆
	 * @return int
	 */
	public int getTotTradeCount();

	/**
	 * 取得內盤總量
	 * @return long
	 */
	public long getTotInVolume();

	/**
	 * 取得外盤總量
	 * @return long
	 */
	public long getTotOutVolume();

}
