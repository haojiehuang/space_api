package com.syt.jabx.query;

import java.util.List;

/**
 * 總覧分類樹之介面
 * @author jason
 *
 */
public interface IJABXClassTreeOverview {

	/**
	 * 以分類代碼取得分類項目之List
	 * @param classID - String(分類代碼)
	 * @return List&lt;IJABXClassTreeItem&gt;
	 */
	public List<IJABXClassTreeItem> getListByClassID(String classID);
}
