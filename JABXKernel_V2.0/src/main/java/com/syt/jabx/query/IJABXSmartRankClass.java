package com.syt.jabx.query;

/**
 * 即時排名分類索引之介面
 * @author Jason
 *
 */
public interface IJABXSmartRankClass {

	/**
	 * 取得即時排名分類項目之介面筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXSmartRankClassItem物件
	 * @param index - int(索引值，從0開始)
	 * @return IJABXSmartRankClassItem
	 */
	public IJABXSmartRankClassItem atIndex(int index);

	/**
	 * 以即時排名分類群組代碼取得即時排名分類項目的介面(IJABXSmartRankClassItem)
	 * @param classID - int(即時排名分類群組代碼)
	 * @return IJABXSmartRankClassItem
	 */
	public IJABXSmartRankClassItem atClassID(int classID);
}
