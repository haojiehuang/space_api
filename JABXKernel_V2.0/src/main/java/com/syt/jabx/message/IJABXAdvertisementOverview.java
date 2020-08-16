package com.syt.jabx.message;

/**
 * 廣告訊息索引的介面
 * @author Jason
 *
 */
public interface IJABXAdvertisementOverview {

	/**
	 * 取得廣告訊息項目的總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得指向某個index的廣告訊息項目的物件
	 * @param index - int(某個廣告訊息項目的索引值，從 0 開始)
	 * @return IJABXAdvertisementItem
	 */
	public IJABXAdvertisementItem atIndex(int index);

	/**
	 * 以廣告序號取得廣告訊息項目的物件
	 * @param serialNo - int(廣告序號)
	 * @return IJABXAdvertisementItem
	 */
	public IJABXAdvertisementItem atSerialNo(int serialNo);

}
