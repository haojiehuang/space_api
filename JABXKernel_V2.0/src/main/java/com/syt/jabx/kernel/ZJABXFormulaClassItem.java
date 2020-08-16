package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXFormulaClassItem;

/**
 * 公式分類列表子項目之類別
 * @author Jason
 *
 */
final class ZJABXFormulaClassItem implements IJABXFormulaClassItem {

	/**
	 * 分類序號
	 */
	private int classID;

	/**
	 * 分類名稱
	 */
	private String className;

	/**
	 * 父節點分類序號
	 */
	private int parentClassID;

	/**
	 * 類型代碼
	 */
	private int type;

	/**
	 * @see com.syt.jabx.query.IJABXFormulaClassItem#getClassID()
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
	 * @see com.syt.jabx.query.IJABXFormulaClassItem#getClassName()
	 */
	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return className;
	}

	/**
	 * 設定分類名稱
	 * @param className - String
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @see com.syt.jabx.query.IJABXFormulaClassItem#getParentClassID()
	 */
	@Override
	public int getParentClassID() {
		// TODO Auto-generated method stub
		return parentClassID;
	}

	/**
	 * 設定父節點分類序號
	 * @param parentClassID - int
	 */
	public void setParentClassID(int parentClassID) {
		this.parentClassID = parentClassID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXFormulaClassItem#getType()
	 */
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return type;
	}

	/**
	 * 設定類型代碼
	 * @param type - int
	 */
	public void setType(int type) {
		this.type = type;
	}

}
