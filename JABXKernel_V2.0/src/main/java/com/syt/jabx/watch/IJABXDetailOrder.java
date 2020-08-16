package com.syt.jabx.watch;

/**
 * 逐筆委託訊息(目前只support SZSE)之介面
 * @author Jason
 *
 */
public interface IJABXDetailOrder {

	/**
	 * 取得交易所委託序號
	 * @return int
	 */
	public int getMarketSeqNo();

	/**
	 * 取得委託時間(HHmmsssss,後3sss 為 mini sec)
	 * @return int
	 */
	public int getTradeTime();

	/**
	 * 取得委託價
	 * @return int
	 */
	public int getTradePrice();

	/**
	 * 取得委託量(SZSE單位為股)
	 * @return int
	 */
	public int getTradeVolume();

	/**
	 * 取得委託類別(SZSE才有)
	 * @return String
	 */
	public String getOrderType();
}
