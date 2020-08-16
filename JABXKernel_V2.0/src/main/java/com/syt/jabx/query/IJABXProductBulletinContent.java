package com.syt.jabx.query;

/**
 * 產品公告內容的介面
 * @author Jason
 *
 */
public interface IJABXProductBulletinContent {

	/**
	 * 取得產品公告序號
	 * @return int
	 */
	public int getSerialNo();

	/**
	 * 取得產品公告日期時間(格式為："YYYY/MM/DD hh:mm:ss")
	 * @return String
	 */
	public String getDataTime();

	/**
	 * 取得產品公告類型<br>
	 * ABX_NOTICE_NORMAL: 一般公告<br>
	 * ABX_NOTICE_URGENT: 緊急公告<br>
	 * ABX_NOTICE_PRODUCT: 版本更新
	 * @return int
	 */
	public int getBulletinType();

	/**
	 * 取得產品公告標題 
	 * @return String
	 */
	public String getTitle();

	/**
	 * 取得產品公告內容
	 * @return String
	 */
	public String getData();

}
