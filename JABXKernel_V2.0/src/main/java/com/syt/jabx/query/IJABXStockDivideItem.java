package com.syt.jabx.query;

/**
 * 股票除權息項目的介面
 * @author Jason
 *
 */
public interface IJABXStockDivideItem {

	/**
	 * 取得除權息日期(yyyyMMdd)
	 * @return String
	 */
	public String getDivideDate();

	/**
	 * 取得派息
	 * @return String
	 */
	public String getDivideCash();

	/**
	 * 取得送股比例
	 * @return String
	 */
	public String getBonusRate();

	/**
	 * 取得轉增比例
	 * @return String
	 */
	public String getTransferRate();

	/**
	 * 取得配股比例
	 * @return String
	 */
	public String getExpandRate();

	/**
	 * 取得增發比例
	 * @return String
	 */
	public String getIncreaseRate();

	/**
	 * 取得配股價格
	 * @return String
	 */
	public String getExpandPrice();

	/**
	 * 取得增發價格
	 * @return String
	 */
	public String getIncreasePrice();

	/**
	 * 取得配股證券全代碼
	 * @return String
	 */
	public String getExpandID();

	/**
	 * 取得增發證券全代碼
	 * @return String
	 */
	public String getIncreaseID();

}
