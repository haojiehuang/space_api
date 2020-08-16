package com.syt.jabx.query;

/**
 * 技術指標列表子項目之介面
 * @author Jason
 *
 */
public interface IJABXTechnicalIndexListItem {

	/**
	 * 取得技術指標公式序號
	 * @return int
	 */
	public int getTechnicalIndexID();

	/**
	 * 取得技術指標分類序號
	 * @return int
	 */
	public int getClassID();

	/**
	 * 取得技術指標英文名
	 * @return String
	 */
	public String getName();

	/**
	 * 取得技術指標中文名
	 * @return String
	 */
	public String getChineseName();
}
