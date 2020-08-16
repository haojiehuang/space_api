package com.syt.jabx.message;

/**
 * 產品公告標題索引的介面
 * @author Jason
 *
 */
public interface IJABXProductBulletinOverview {

	/**
	 * 取得產品公告標題項目的總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得指向某個index的公告標題項目的物件
	 * @param index - int(某個公告標題項目的索引值，從 0 開始)
	 * @return IJABXProductBulletinItem
	 */
	public IJABXProductBulletinItem atIndex(int index);

	/**
	 * 以產品公告序號取得公告標題項目的物件
	 * @param serialNo - int(產品公告序號)
	 * @return IJABXProductBulletinItem
	 */
	public IJABXProductBulletinItem atSerialNo(int serialNo);

}
