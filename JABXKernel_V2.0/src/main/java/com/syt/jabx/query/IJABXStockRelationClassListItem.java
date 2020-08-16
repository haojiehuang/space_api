package com.syt.jabx.query;

import java.util.List;

/**
 * 股票關連分類列表項目之介面
 * @author Jason
 *
 */
public interface IJABXStockRelationClassListItem {

	/**
	 * 取得證券全代碼
	 * @return String
	 */
	public String getID();

	/**
	 * 取得產業或自定(板塊)分類群組代碼列表
	 * @return List&lt;String&gt;
	 */
	public List<Integer> getListOfClassID();
}
