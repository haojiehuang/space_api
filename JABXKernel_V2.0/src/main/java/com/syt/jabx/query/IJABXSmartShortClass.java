package com.syt.jabx.query;

/**
 * 短線精靈分類索引之介面
 * @author Jason
 *
 */
public interface IJABXSmartShortClass {

	/**
	 * 取得短線精靈分類項目之介面筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXSmartShortClassItem物件
	 * @param index - int(索引值，從0開始)
	 * @return IJABXSmartShortClassItem
	 */
	public IJABXSmartShortClassItem atIndex(int index);

	/**
	 * 以分類群組代碼取得短線精靈分類項目的介面(IJABXSmartShortClassItem)
	 * @param classID - int(分類群組代碼)
	 * @return IJABXSmartShortClassItem
	 */
	public IJABXSmartShortClassItem atClassID(int classID);
}
