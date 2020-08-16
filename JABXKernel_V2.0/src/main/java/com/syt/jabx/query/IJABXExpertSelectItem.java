package com.syt.jabx.query;

/**
 * 專家選股項目的介面
 * @author Jason
 *
 */
public interface IJABXExpertSelectItem {

	/**
	 * 取得專家選股代碼
	 * @return int
	 */
	public int getExpertSelectID();

	/**
	 * 取得推薦選股日期(格式為："YYYYMMDD")
	 * @return String
	 */
	public String getSelectDate();

	/**
	 * 取得推薦證券全代碼
	 * @return String
	 */
	public String getID();

	/**
	 * 取得股票簡稱
	 * @return String
	 */
	public String getName();

	/**
	 * 取得收盤價
	 * @return String
	 */
	public String getClosePrice();

	/**
	 * 取得壓力價
	 * @return String
	 */
	public String getHighPrice();

	/**
	 * 取得支撐價
	 * @return String
	 */
	public String getLowPrice();

	/**
	 * 取得選股說明
	 * @return String
	 */
	public String getContent();

}
