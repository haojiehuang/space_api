package com.syt.jabx.query;

import java.util.List;

/**
 * 自訂分類索引的介面
 * @author Jason
 *
 */
public interface IJABXBlockClass {

	/**
	 * 取得自訂索引項目之筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXBlockClassItem物件
	 * @param index - int(索引值，從0開始)
	 * @return IJABXBlockClassItem
	 */
	public IJABXBlockClassItem atIndex(int index);

	/**
	 * 以分類群組代碼取得自訂索引項目的介面(IJABXBlockClassItem)
	 * @param classID - int(分類群組代碼)
	 * @return IJABXBlockClassItem
	 */
	public IJABXBlockClassItem atClassID(int classID);

	/**
	 * 取得某一節點之公式分類項目列表
	 * @param parentClassID - int(父節點分類群組代碼)
	 * @return List&lt;IJABXBlockClassItem&gt;
	 */
	public List<IJABXBlockClassItem> getListByParentClassID(int parentClassID);
	
}
