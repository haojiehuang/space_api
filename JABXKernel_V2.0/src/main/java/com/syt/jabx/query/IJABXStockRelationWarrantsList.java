package com.syt.jabx.query;

/**
 * 股票關連權證股票索引介面
 * @author Jason
 *
 */
public interface IJABXStockRelationWarrantsList {

	/**
	 * 取得總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXStockRelationWarrantsListItem
	 * @param index - int(索引值，從 0 開始)
	 * @return IJABXStockRelationWarrantsListItem
	 */
	public IJABXStockRelationWarrantsListItem atIndex(int index);

	/**
	 * 以證券全代碼取得IJABXStockRelationWarrantsListItem介面
	 * @param stkID - String
	 * @return IJABXStockRelationWarrantsListItem
	 */
	public IJABXStockRelationWarrantsListItem atID(String stkID);

}
