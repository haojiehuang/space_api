package com.syt.jabx.query;

/**
 * 其他分類索引之介面
 * @author Jason
 *
 */
public interface IJABXOtherClass {

	/**
	 * 取得價差交易分類項目之介面筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXOtherClassItem物件
	 * @param index - int(索引值，從0開始)
	 * @return IJABXOtherClassItem
	 */
	public IJABXOtherClassItem atIndex(int index);

	/**
	 * 以其他分類群組代碼取得價差交易分類項目的介面(IJABXOtherClassItem)
	 * @param classID - int(其他分類群組代碼)
	 * @return IJABXOtherClassItem
	 */
	public IJABXOtherClassItem atClassID(int classID);
}
