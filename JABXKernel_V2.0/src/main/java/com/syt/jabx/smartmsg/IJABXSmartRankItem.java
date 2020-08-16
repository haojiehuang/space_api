package com.syt.jabx.smartmsg;

/**
 * 即時排名資訊項目的介面
 * @author Jason
 *
 */
public interface IJABXSmartRankItem {

	/**
	 * 取得即時排名分類代碼
	 * @return String
	 */
	public String getClassID();

	/**
	 * 取得交易所代碼或區域代碼
	 * @return String
	 */
	public String getExchangeID();

	/**
	 * 取得證券代碼
	 * @return String
	 */
	public String getID();

	/**
	 * 取得排名
	 * @return int
	 */
	public int getRank();

	/**
	 * 取得成交價
	 * @return int
	 */
	public int getTradePrice();

	/**
	 * 取得成交價漲趺
	 * @return int
	 */
	public int getPriceType();

	/**
	 * 取得排名值
	 * @return int
	 */
	public int getRankValue();
}
