package com.syt.jabx.query;

/**
 * 分類關連股票索引之介面
 * @author Jason
 *
 */
public interface IJABXClassRelationStock {

	/**
	 * 取得分類群組代碼
	 * @return int
	 */
	public int getClassID();

	/**
	 * 取得分類關連股票項目之總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXClassRelationStockItem物件
	 * @param index - int(索引值，從0開始)
	 * @return IJABXClassRelationStockItem
	 */
	public IJABXClassRelationStockItem atIndex(int index);
}
