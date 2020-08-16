package com.syt.jabx.query;

/**
 * 專家選股索引的介面
 * @author Jason
 *
 */
public interface IJABXExpertSelect {

	/**
	 * 取得專家選股項目總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXExpertSelectItem物件
	 * @param index - int(索引值，從0開始)
	 * @return IJABXExpertSelectItem
	 */
	public IJABXExpertSelectItem atIndex(int index);

}
