package com.syt.jabx.message;

/**
 * 廣告訊息項目的介面
 * @author Jason
 *
 */
public interface IJABXAdvertisementItem {

	/**
	 * 取得廣告序號
	 * @return int
	 */
	public int getSerialNo();

	/**
	 * 取得廣告分類代碼
	 * @return String
	 */
	public String getClassID();

	/**
	 * 取得廣告訊息日期時間
	 * @return String
	 */
	public String getDataTime();

	/**
	 * 取得廣告訊息網址
	 * @return String
	 */
	public String getUrl();

	/**
	 * 取得廣告訊息
	 * @return String
	 */
	public String getData();

}
