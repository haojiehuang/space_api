package com.syt.jabx.query;

/**
 * 商品分類索引的介面
 * @author Jason
 *
 */
public interface IJABXStockClass {

	/**
	 * 取得商品分類項目的介面筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得商品分類項目的介面(IJABXStockClassItem)
	 * @param index - int(索引值，從0開始)
	 * @return IJABXStockClassItem
	 */
	public IJABXStockClassItem atIndex(int index);

	/**
	 * 以商品分類群組代碼取得商品分類項目的介面(IJABXStockClassItem)
	 * @param classID - int(商品分類群組代碼)
	 * @return IJABXStockClassItem
	 */
	public IJABXStockClassItem atClassID(int classID);
}
