package com.syt.jabx.query;

/**
 * 產業分類2索引的介面
 * @author Jason
 *
 */
public interface IJABXTradeClass2 {

	/**
	 * 取得產業分類2項目的介面筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得產業分類2項目的介面(IJABXTradeClass2Item)
	 * @param index - int(索引值，從0開始)
	 * @return IJABXTradeClass2Item
	 */
	public IJABXTradeClass2Item atIndex(int index);

	/**
	 * 以產業分類2分類代碼取得商品分類項目的介面(IJABXTradeClass2Item)
	 * @param classID - int(分類代碼)
	 * @return IJABXTradeClass2Item
	 */
	public IJABXTradeClass2Item atClassID(int classID);
}