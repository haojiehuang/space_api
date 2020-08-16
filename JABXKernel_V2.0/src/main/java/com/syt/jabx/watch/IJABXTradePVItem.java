package com.syt.jabx.watch;

/**
 * 個股價量關係明細的介面
 * @author Jason
 *
 */
public interface IJABXTradePVItem {

	/**
	 * 取得成交價
	 * @return int
	 */
	public int getTradePrice();
	
	/**
	 * 取得成交量
	 * @return int
	 */
	public int getTradeVolume();
	
	/**
	 * 取得內盤量
	 * @return int
	 */
	public int getInTradeVolume();
	
	/**
	 * 取得外盤量
	 * @return int
	 */
	public int getOutTradeVolume();
}
