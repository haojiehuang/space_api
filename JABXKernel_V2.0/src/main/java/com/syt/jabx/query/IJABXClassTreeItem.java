package com.syt.jabx.query;

/**
 * 分類樹子項目介面
 * @author jason
 *
 */
public interface IJABXClassTreeItem {

	/**
	 * 取得分類代碼<br>
	 * 此分類代碼可當作函式IJABXClassTreeOverview.getListByClassID(String classID)中<br>
	 * classID的參數，取得此分類代碼下之子項目列表；以此方式不斷將 分類樹 展開。
	 * @return String
	 */
	public String getClassID();

	/**
	 * 取得分類名稱
	 * @return String
	 */ 
	public String getClassName();
	
	/**
	 * 取得分類種類
	 * @return String
	 */
	public String getClassType();

	/**
	 * 取得分類描述
	 * @return String
	 */
	public String getClassDescription();
}
