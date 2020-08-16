package com.syt.jabx.query;

/**
 * 技術指標分類子項目之介面
 * @author Jason
 *
 */
public interface IJABXTechnicalIndexClassItem {

	/**
	 * 取得技術指標分類序號
	 * @return int
	 */
	public int getClassID();

	/**
	 * 取得技術指標分類名稱
	 * @return String
	 */
	public String getName();

	/**
	 * 取得父節點技術指標分類序號
	 * @return int
	 */
	public int getParentClassID();

	/**
	 * 取得指標類型代碼
	 * @return int
	 */
	public int getType();
}
