package com.syt.jabx.smartmsg;

/**
 * 用戶警示訊息之介面
 * @author jason
 *
 */
public interface IJABXSmartWarnItem {

	/**
	 * 取得用戶ID
	 * @return int
	 */
	public int getUserID();

	/**
	 * 取得股票代碼
	 * @return String
	 */
	public String getStkID();

	/**
	 * 取得警示ID
	 * @return int
	 */
	public int getWarnID();

	/**
	 * 取得警示名稱
	 * @return String
	 */
	public String getWarnName();

	/**
	 * 取得警示說明
	 * @return int
	 */
	public String getWarnDesc();

	/**
	 * 取得曾登錄的產品ID列表( 每個ID間以”|”分隔。如：”1｜21｜31｜41｜”)
	 * @return String
	 */
	public String getProductIDs();
	
}