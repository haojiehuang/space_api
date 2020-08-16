package com.syt.jabx.query;

import java.util.List;

/**
 * 股名檔子項目之介面
 * @author Jason
 *
 */
public interface IJABXStockItem {

	/**
	 * 取得證券全代碼
	 * @return String
	 */
	public String getID();

	/**
	 * 取得股票名稱
	 * @return String
	 */
	public String getName();

	/**
	 * 取得交易所代碼
	 * @return String
	 */
	public String getExchangeID();

	/**
	 * 取得股名拼音之List
	 * @return List&lt;String&gt;
	 */
	public List<String> getListOfPinying();

}
