package com.syt.jabx.query;

import java.util.List;

/**
 * 股票關連分類項目之介面
 * @author Jason
 *
 */
public interface IJABXStockRelationClass {

	/**
	 * 取得證券全代碼
	 * @return String
	 */
	public String getID();

	/**
	 * 取得商品分類群組代碼
	 * @return int
	 */
	public int getStockClassID();

	/**
	 * 取得產業分類群組代碼
	 * @return int
	 */
	public int getTradeClassID();

	/**
	 * 取得自定(板塊)分類群組代碼列表
	 * @return List&lt;Integer&gt;
	 */
	public List<Integer> getListOfBlockClassID();
}
