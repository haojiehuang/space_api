package com.syt.jabx.query;

import java.util.List;

/**
 * 股票關連權證股票的項目介面
 * @author Jason
 *
 */
public interface IJABXStockRelationWarrantsListItem {

	/**
	 * 取得證券全代碼
	 * @return String
	 */
	public String getID();

	/**
	 * 取得權證證券全代碼列表
	 * @return List&lt;String&gt;
	 */
	public List<String> getListOfWarrantsID();
}
