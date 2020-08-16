package com.syt.jabx.kmodel;

import java.util.List;

/**
 * 用戶可使用產品功能介面
 * @author Jason
 *
 */
public interface IJABXUserAvailableProductClass {

	/**
	 * queryInterface中取得IJABXAccountOverview介面所使用之常數
	 */
	public final static int ACCOUNT_OVERVIEW = 1; 

	/**
	 * queryInterface中取得IJABXGroupAccountOverview介面所使用之常數
	 */
	public final static int GROUP_ACCOUNT_OVERVIEW = 2; 
	
	/**
	 * 取得系統日期(格式為: yyyyMMdd)
	 * @return int(yyyyMMdd)
	 */
	public int getSystemDate();

	/**
	 * 取得系統時間(格式為: HHmmss)
	 * @return int(HHmmss)
	 */
	public int getSystemTime();

	/**
	 * 取得用戶到期日(格式為: yyyyMMdd)
	 * @return int(yyyyMMdd)
	 */
	public int getExpDate();

	/**
	 * 取得產品功能集合
	 * @return List&lt;String&gt;
	 */
	public List<String> getProductClassCollection();

	/**
	 * 取得交易所代碼集合
	 * @return List&lt;String&gt;
	 */
	public List<String> getExchangeIDCollection();

	/**
	 * 取得後端登入主機原始回覆內容
	 * @return String
	 */
	public String getOriginalResponse();

	/**
	 * 查詢可使用帳號介面
	 * @param i - int<br>
	 * 常數對應之物件如下所：<br>
	 * IJABXUserAvailableProductClass.ACCOUNT_OVERVIEW: IJABXAccountOverview<br>
	 * IJABXUserAvailableProductClass.GROUP_ACCOUNT_OVERVIEW: IJABXGroupAccountOverview
	 * @return Object
	 */
	public Object queryInterface(int i);
}
