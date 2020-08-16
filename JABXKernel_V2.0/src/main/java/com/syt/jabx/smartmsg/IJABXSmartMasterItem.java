package com.syt.jabx.smartmsg;

/**
 * 主力大單資訊項目的介面
 * @author Jason
 *
 */
public interface IJABXSmartMasterItem {

	/**
	 * 取得大單類別
	 * @return String
	 */
	public String getMasterType();

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
	 * 取得委託或成交時間(HHmmsssss,後3sss 為 minisec)
	 * @return int
	 */
	public int getDataTime();

	/**
	 * 取得成交價
	 * @return int
	 */
	public int getTradePrice();

	/**
	 * 取得成交價格型態
	 * @return int
	 */
	public int getPriceType();

	/**
	 * 取得成交單量
	 * @return int
	 */
	public int getTradeVolume();

	/**
	 * 取得單量內外盤
	 * @return int
	 */
	public int getInOutFlag();
}