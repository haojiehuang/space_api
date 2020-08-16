package com.syt.jabx.watch;

/**
 * 一秒快照訊息之介面
 * @author Jason
 *
 */
public interface IJABXSecondSnapshot {

	/**
	 * 取得第一買價
	 * @return int
	 */
	public int getBidPrice1();

	/**
	 * 取得第一買量
	 * @return int
	 */
	public int getBidVolume1();

	/**
	 * 取得第一賣價
	 * @return int
	 */
	public int getAskPrice1();

	/**
	 * 取得第一賣量
	 * @return int
	 */
	public int getAskVolume1();

	/**
	 * 取得成交時間(HHmmsssss,後3sss 為 mini sec)
	 * @return int
	 */
	public int getTradeTime();

	/**
	 * 取得成交類別(目前只有香港1byte)
	 * @return String
	 */
	public String getTradeType();

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
	 * 取得內盤總量
	 * @return long
	 */
	public long getTotInVolume();

	/**
	 * 取得外盤總量
	 * @return long
	 */
	public long getTotOutVolume();

	/**
	 * 取得今開價
	 * @return int
	 */
	public int getOpenPrice();

	/**
	 * 取得今高價
	 * @return int
	 */
	public int getHighPrice();

	/**
	 * 取得今低價
	 * @return int
	 */
	public int getLowPrice();
}
