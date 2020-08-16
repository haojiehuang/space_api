package com.syt.jabx.message;

/**
 * 新聞標題索引的介面
 * @author Jason
 *
 */
public interface IJABXNewsOverview {

	/**
	 * 取得新聞標題項目的總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得指向某個index的新聞標題項目的物件
	 * @param index - int(某個新聞標題項目的索引值，從 0 開始)
	 * @return IJABXNewsItem
	 */
	public IJABXNewsItem atIndex(int index);

	/**
	 * 以新聞序號取得新聞標題項目的物件
	 * @param serialNo - int(新聞序號)
	 * @return IJABXExchangeBulletinItem
	 */
	public IJABXNewsItem atSerialNo(int serialNo);

}
