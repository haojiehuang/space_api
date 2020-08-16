package com.syt.jabx.message;

import java.util.List;

/**
 * 新聞標題項目的介面
 * @author Jason
 *
 */
public interface IJABXNewsItem {

	/**
	 * 取得新聞序號
	 * @return int
	 */
	public int getSerialNo();

	/**
	 * 取得新聞來源代碼
	 * @return String
	 */
	public String getSourceID();

	/**
	 * 取得新聞分類代碼
	 * @return String
	 */
	public String getClassID();

	/**
	 * 取得新聞證券類型
	 * @return String
	 */
	public String getStockType();

	/**
	 * 取得新聞內容資料格式
	 * @return String
	 */
	public String getDataType();

	/**
	 * 取得新聞相關股票全代碼列表
	 * @return List&lt;String&gt;
	 */
	public List<String> getListOfStockID();

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

}
