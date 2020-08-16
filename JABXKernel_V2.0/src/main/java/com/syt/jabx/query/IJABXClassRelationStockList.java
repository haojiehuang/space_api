package com.syt.jabx.query;

/**
 * 分類關連股票列表索引之介面
 * @author Jason
 *
 */
public interface IJABXClassRelationStockList {

	/**
	 * 取得分類關連股票列表項目之筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXClassRelationStockListItem物件
	 * @param index - int(索引值，從0開始)
	 * @return IJABXClassRelationStockListItem
	 */
	public IJABXClassRelationStockListItem atIndex(int index);

	/**
	 * 以產業或自定(板塊)分類群組代碼取得分類關連股票列表項目的介面(IJABXClassRelationStockListItem)
	 * @param classID - int(產業或自定(板塊)分類群組代碼)
	 * @return IJABXClassRelationStockListItem
	 */
	public IJABXClassRelationStockListItem atClassID(int classID);

}
