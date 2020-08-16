package com.syt.jabx.smartmsg;

/**
 * 短線精靈資訊項目的介面
 * @author Jason
 *
 */
public interface IJABXSmartShortItem {

	/**
	 * 取得短線精靈分類代碼
	 * @return String
	 */
	public String getClassID();

	/**
	 * 取得交易所代碼或區域代碼
	 * @return String
	 */
	public String getExchangeID();

	/**
	 * 取得證券全代碼
	 * @return String
	 */
	public String getID();

	/**
	 * 取得成交時間(HHmmss)
	 * @return int
	 */
	public int getTradeTime();

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
	 * 取得成交單量
	 * @return int
	 */
	public int getTradeVolume();
}
