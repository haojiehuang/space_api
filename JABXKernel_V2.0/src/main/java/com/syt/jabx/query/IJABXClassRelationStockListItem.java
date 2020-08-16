package com.syt.jabx.query;

import java.util.List;

/**
 * 分類關連股票列表項目之介面
 * @author Jason
 *
 */
public interface IJABXClassRelationStockListItem {

	/**
	 * 取得產業或自定(板塊)分類群組代碼
	 * @return int
	 */
	public int getClassID();

	/**
	 * 取得證券全代碼列表
	 * @return List&lt;String&gt;
	 */
	public List<String> getListOfStockID();
}
