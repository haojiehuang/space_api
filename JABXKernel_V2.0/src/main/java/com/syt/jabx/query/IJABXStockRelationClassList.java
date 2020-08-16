package com.syt.jabx.query;

/**
 * 股票關連分類列表索引之介面
 * @author Jason
 *
 */
public interface IJABXStockRelationClassList {

	/**
	 * 取得股票關連分類列表項目之筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXStockRelationClassListItem物件
	 * @param index - int(索引值，從0開始)
	 * @return IJABXStockRelationClassListItem
	 */
	public IJABXStockRelationClassListItem atIndex(int index);

	/**
	 * 以證券全代碼取得股票關連分類列表項目的介面(IJABXStockRelationClassListItem)
	 * @param id - String(證券全代碼)
	 * @return IJABXStockRelationClassListItem
	 */
	public IJABXStockRelationClassListItem atID(String id);

}
