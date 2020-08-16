package com.syt.jabx.query;

/**
 * 交易所1關連交易所2股票檔之介面
 * @author Jason
 *
 */
public interface IJABXStockRelationStockList {

	/**
	 * 取得總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXStockRelationStockListItem
	 * @param index - int(索引值，從 0 開始)
	 * @return IJABXStockRelationStockListItem
	 */
	public IJABXStockRelationStockListItem atIndex(int index);

	/**
	 * 以證券全代碼1取得IJABXStockRelationStockListItem介面
	 * @param stkID1 - String
	 * @return IJABXStockRelationStockListItem
	 */
	public IJABXStockRelationStockListItem atID(String stkID1);

}
