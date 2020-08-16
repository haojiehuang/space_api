package com.syt.jabx.query;

/**
 * 即時排名分類項目之介面
 * @author Jason
 *
 */
public interface IJABXSmartRankClassItem {

	/**
	 * 取得市場別屬性
	 * @return String
	 */
	public String getMarketType();

	/**
	 * 取得即時排名分類群組代碼
	 * @return int
	 */
	public int getClassID();

	/**
	 * 取得即時排名分類群組名稱(UTF-8)
	 * @return String
	 */
	public String getName();

	/**
	 * 取得即時排名分類的交易所代碼
	 * @return String
	 */
	public String getExchangeID();
}
