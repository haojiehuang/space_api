package com.syt.jabx.query;

import java.util.List;

/**
 * 總覧警示定義樹之介面
 * @author jason
 *
 */
public interface IJABXWarnTreeOverview {

	/**
	 * 以群組代碼取得分類項目之List
	 * @param groupID - String(群組代碼)
	 * @return List&lt;IJABXWarnTreeItem&gt;
	 */
	public List<IJABXWarnTreeItem> getListByGroupID(String groupID);
}
