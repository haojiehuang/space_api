package com.syt.jabx.kmodel;

/**
 * 用戶系統環境設定內容的介面
 * @author Jason
 *
 */
public interface IJABXUserEnvironmentContent {

	/**
	 * 取得用戶系統環境設定群組代碼
	 * @return String
	 */
	public String getGroupID();

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
