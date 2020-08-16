package com.syt.jabx.query;

/**
 * 行情報導內容的介面
 * @author Jason
 *
 */
public interface IJABXMarketReportContent {

	/**
	 * 取得行情報導序號
	 * @return int
	 */
	public int getSerialNo();

	/**
	 * 取得行情報導日期時間(格式為："YYYY/MM/DD hh:mm:ss")
	 * @return String
	 */
	public String getDataTime();

	/**
	 * 取得行情報導分類代碼 
	 * @return String
	 */
	public String getClassID();

	/**
	 * 取得行情報導標題
	 * @return String
	 */
	public String getTitle();

	/**
	 * 取得行情報導摘要
	 * @return String
	 */
	public String getSummary();

	/**
	 * 取得行情報導內容 
	 * @return String
	 */
	public String getData();

}
