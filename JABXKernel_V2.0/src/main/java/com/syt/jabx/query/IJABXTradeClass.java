package com.syt.jabx.query;

/**
 * 產業分類索引的介面
 * @author Jason
 *
 */
public interface IJABXTradeClass {

	/**
	 * 取得產業分類項目的介面筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得產業分類項目的介面(IJABXTradeClassItem)
	 * @param index - int(索引值，從0開始)
	 * @return IJABXTradeClassItem
	 */
	public IJABXTradeClassItem atIndex(int index);

	/**
	 * 以產業分類群組代碼取得商品分類項目的介面(IJABXTradeClassItem)
	 * @param classID - int(商品分類群組代碼)
	 * @return IJABXTradeClassItem
	 */
	public IJABXTradeClassItem atClassID(int classID);
}
