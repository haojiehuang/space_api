package com.syt.jabx.query;

/**
 * 價差交易分類索引之介面
 * @author Jason
 *
 */
public interface IJABXSpreadClass {

	/**
	 * 取得價差交易分類項目之介面筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXSpreadClassItem物件
	 * @param index - int(索引值，從0開始)
	 * @return IJABXSpreadClassItem
	 */
	public IJABXSpreadClassItem atIndex(int index);

	/**
	 * 以價差交易分類群組代碼取得價差交易分類項目的介面(IJABXSpreadClassItem)
	 * @param classID - int(價差交易分類群組代碼)
	 * @return IJABXSpreadClassItem
	 */
	public IJABXSpreadClassItem atClassID(int classID);
}
