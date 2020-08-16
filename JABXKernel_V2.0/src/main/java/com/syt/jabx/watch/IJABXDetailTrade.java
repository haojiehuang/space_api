package com.syt.jabx.watch;

/**
 * 即時逐筆成交訊息(目前只support SSE,SZSE)之介面
 * @author Jason
 *
 */
public interface IJABXDetailTrade {

	/**
	 * 取得交易所成交序號
	 * @return int
	 */
	public int getMarketSeqNo();

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
	 * 取得成交單量(SSE,SZSE單位為股)
	 * @return int
	 */
	public int getTradeVolume();

	/**
	 * 取得成交類別(SZSE才有)
	 * @return String
	 */
	public String getTradeType();

	/**
	 * 取得逐筆交易所委買序號(SZSE才有,Server自行統計)
	 * @return int
	 */
	public int getBidNo();

	/**
	 * 取得逐筆交易所委賣序號(SZSE才有,Server自行統計)
	 * @return int
	 */
	public int getAskNo();
}
