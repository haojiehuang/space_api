package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXFormulaListItem;

/**
 * 公式列表項目之類別
 * @author Jason
 *
 */
final class ZJABXFormulaListItem implements IJABXFormulaListItem {

	/**
	 * 公式序號
	 */
	private int formulaID;

	/**
	 * 分類序號
	 */
	private int classID;

	/**
	 * 公式英文名
	 */
	private String name;

	/**
	 * 公式中文名
	 */
	private String chineseName;

	/**
	 * 展示位置<br />
	 * 0.非線圖公式(選股)<br />
	 * 1.主圖疊加<br />
	 * 2.輔圖<br />
	 * 3.線圖公式不展示(選圖)<br />
	 */
	private int posType;

	/**
	 * @see com.syt.jabx.query.IJABXFormulaListItem#getFormulaID()
	 */
	@Override
	public int getFormulaID() {
		// TODO Auto-generated method stub
		return formulaID;
	}

	/**
	 * 設定公式序號
	 * @param formulaID - int
	 */
	public void setFormulaID(int formulaID) {
		this.formulaID = formulaID;
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
	 * 設定分類序號
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
	 * 設定公式英文名
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
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
	 * 設定公式中文名
	 * @param chineseName - String
	 */
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	/**
	 * @see com.syt.jabx.query.IJABXFormulaListItem#getPosType()
	 */
	@Override
	public int getPosType() {
		// TODO Auto-generated method stub
		return posType;
	}

	/**
	 * 設定展示位置
	 * @param posType - int
	 */
	public void setPosType(int posType) {
		this.posType = posType;
	}
}
