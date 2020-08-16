package com.syt.jabx.query;

import java.util.List;

/**
 * 查詢技術指標分類之介面
 * @author Jason
 *
 */
public interface IJABXTechnicalIndexClass {

	/**
	 * 取得技術指標分類的項目介面筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之技術指標分類的項目介面(IJABXTechnicalIndexClassItem)
	 * @param index - int(索引值，從 0 開始)
	 * @return IJABXTechnicalIndexClassItem
	 */
	public IJABXTechnicalIndexClassItem atIndex(int index);

	/**
	 * 取得某一節點之技術指標分類列表
	 * @param parentClassID - Integer(父節點分類序號)
	 * @return List&lt;IJABXTechnicalIndexClassItem&gt;
	 */
	public List<IJABXTechnicalIndexClassItem> getListByParentClassID(Integer parentClassID);
}
