package com.syt.jabx.message;

/**
 * 行情報導標題項目的介面
 * @author Jason
 *
 */
public interface IJABXMarketReportItem {

	/**
	 * 取得行情報導序號
	 * @return int
	 */
	public int getSerialNo();

	/**
	 * 取得行情報導分類代碼
	 * @return String
	 */
	public String getClassID();

	/**
	 * 取得行情報導標題資料格式
	 * @return String
	 */
	public String getDataType();

	/**
	 * 取得行情報導標題日期時間
	 * @return String
	 */
	public String getDataTime();

	/**
	 * 取得行情報導標題
	 * @return String
	 */
	public String getTitle();

}
