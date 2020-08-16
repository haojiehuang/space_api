package com.syt.jabx.kmodel;

/**
 * 用戶系統環境設定群組索引的介面
 * @author Jason
 *
 */
public interface IJABXUserEnvironmentGroup {
	
	/**
	 * 取得用戶系統環境設定群組代碼
	 * @return String
	 */
	public String getGroupID();

	/**
	 * 取得用戶系統環境設定列表項目的介面總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得用戶系統環境設定群組項目的介面(IJABXUserEnvironmentGroupItem)
	 * @param index - int(索引值，從0開始)
	 * @return IJABXUserEnvironmentGroupItem
	 */
	public IJABXUserEnvironmentGroupItem atIndex(int index);

	/**
	 * 以用戶系統環境設定群組序號取得用戶系統環境設定群組項目的介面(IJABXUserEnvironmentGroupItem)
	 * @param groupNo - int(用戶系統環境設定群組序號)
	 * @return IJABXUserEnvironmentGroupItem
	 */
	public IJABXUserEnvironmentGroupItem atGroupNo(int groupNo);

}
