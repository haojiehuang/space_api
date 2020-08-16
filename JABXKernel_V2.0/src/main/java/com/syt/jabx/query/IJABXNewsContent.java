package com.syt.jabx.query;

import java.util.List;

/**
 * 新聞內容的介面
 * @author Jason
 *
 */
public interface IJABXNewsContent {

	/**
	 * 取得新聞序號
	 * @return int
	 */
	public int getSerialNo();

	/**
	 * 取得新聞來源代碼
	 * @return int
	 */
	public int getSourceID();

	/**
	 * 取得新聞日期時間
	 * @return String
	 */
	public String getDataTime();

	/**
	 * 取得新聞標題
	 * @return String
	 */
	public String getTitle();

	/**
	 * 取得新聞相關股票全代碼列表
	 * @return List&lt;String&gt;
	 */
	public List<String> getListOfStockID();

	/**
	 * 取得新聞內容
	 * @return String
	 */
	public String getData();

}
