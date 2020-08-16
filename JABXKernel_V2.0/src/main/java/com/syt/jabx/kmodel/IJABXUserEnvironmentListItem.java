package com.syt.jabx.kmodel;

/**
 * 用戶系統環境設定列表項目的介面
 * @author Jason
 *
 */
public interface IJABXUserEnvironmentListItem {

	/**
	 * 取得用戶系統環境設定群組序號
	 * @return int
	 */
	public int getGroupNo();

	/**
	 * 取得用戶系統環境設定群組名稱
	 * @return String
	 */
	public String getGroupName();

	/**
	 * 取得用戶系統環境設定內容
	 * @return String
	 */
	public String getContent();

}
