package com.syt.jabx.query;

/**
 * 總覧股名檔資料之介面
 * @author Jason
 *
 */
public interface IJABXStockOverview {

	/**
	 * 取得總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXStockItem
	 * @param index - int(索引值，從 0 開始)
	 * @return IJABXStockItem
	 */
	public IJABXStockItem atIndex(int index);

	/**
	 * 取得證券全代碼為 strID 的IJABXStockItem介面的物件 
	 * @param strID - String(證券全代碼)
	 * @return IJABXStockItem
	 */
	public IJABXStockItem atID(String strID);
}
