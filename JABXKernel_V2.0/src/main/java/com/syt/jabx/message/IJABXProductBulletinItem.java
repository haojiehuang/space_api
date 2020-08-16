package com.syt.jabx.message;

/**
 * 產品公告標題項目的介面
 * @author Jason
 *
 */
public interface IJABXProductBulletinItem {

	/**
	 * 取得產品公告序號
	 * @return int
	 */
	public int getSerialNo();

	/**
	 * 取得產品代碼
	 * @return String
	 */
	public String getProductID();

	/**
	 * 取得產品公告類型
	 * @return String
	 */
	public String getBulletinType();

	/**
	 * 取得產品公告內容資料格式
	 * @return String
	 */
	public String getDataType();

	/**
	 * 取得產品公告日期時間
	 * @return String
	 */
	public String getDataTime();

	/**
	 * 取得產品公告標題 
	 * @return String
	 */
	public String getTitle();

}
