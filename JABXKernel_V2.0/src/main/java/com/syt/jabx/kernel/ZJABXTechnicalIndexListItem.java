package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXTechnicalIndexListItem;

/**
 * 技術指標列表子項目之類別
 * @author Jason
 *
 */
final class ZJABXTechnicalIndexListItem implements IJABXTechnicalIndexListItem {

	/**
	 * 技術指標公式序號
	 */
	private int technicalIndexID;

	/**
	 * 技術指標分類序號
	 */
	private int classID;

	/**
	 * 技術指標英文名
	 */
	private String name;

	/**
	 * 技術指標中文名
	 */
	private String chineseName;

	/**
	 * @see com.syt.jabx.query.IJABXFormulaListItem#getFormulaID()
	 */
	@Override
	public int getTechnicalIndexID() {
		// TODO Auto-generated method stub
		return technicalIndexID;
	}

	/**
	 * 設定技術指標公式序號
	 * @param technicalIndexID - int
	 */
	public void setTechnicalIndexID(int technicalIndexID) {
		this.technicalIndexID = technicalIndexID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXFormulaListItem#getClassID()
	 */
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return classID;
	}

	/**
	 * 設定技術指標分類序號
	 * @param classID - int
	 */
	public void setClassID(int classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXFormulaListItem#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * 設定技術指標英文名
	 * @param englishName - String
	 */
	public void setName(String englishName) {
		this.name = englishName;
	}

	/**
	 * @see com.syt.jabx.query.IJABXFormulaListItem#getChineseName()
	 */
	@Override
	public String getChineseName() {
		// TODO Auto-generated method stub
		return chineseName;
	}

	/**
	 * 設定技術指標中文名
	 * @param chineseName - String
	 */
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

}
