package com.syt.jabx.query;

/**
 * 公式分類項目之介面
 * @author Jason
 *
 */
public interface IJABXFormulaClassItem {

	/**
	 * 取得分類序號
	 * @return int
	 */
	public int getClassID();

	/**
	 * 取得分類名稱
	 * @return String
	 */
	public String getClassName();

	/**
	 * 取得父節點分類序號
	 * @return int
	 */
	public int getParentClassID();

	/**
	 * 取得類型代碼
	 * @return int
	 */
	public int getType();
}
