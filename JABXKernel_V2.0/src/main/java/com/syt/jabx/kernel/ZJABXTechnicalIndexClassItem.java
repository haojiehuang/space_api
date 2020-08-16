package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXTechnicalIndexClassItem;

/**
 * 技術指標分類子項目之類別
 * @author Jason
 *
 */
final class ZJABXTechnicalIndexClassItem implements IJABXTechnicalIndexClassItem {

	/**
	 * 技術指標分類序號
	 */
	private int classID;

	/**
	 * 技術指標分類名稱
	 */
	private String name;

	/**
	 * 父節點技術指標分類序號
	 */
	private int parentClassID;

	/**
	 * 指標類型代碼
	 */
	private int type;

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexClassItem#getClassID()
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
	 * @see com.syt.jabx.query.IJABXTechnicalIndexClassItem#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * 設定技術指標分類名稱
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexClassItem#getParentClassID()
	 */
	@Override
	public int getParentClassID() {
		// TODO Auto-generated method stub
		return parentClassID;
	}

	/**
	 * 設定父節點技術指標分類序號
	 * @param parentClassID - int
	 */
	public void setParentClassID(int parentClassID) {
		this.parentClassID = parentClassID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexClassItem#getType()
	 */
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return type;
	}

	/**
	 * 設定指標類型代碼
	 * @param type - int
	 */
	public void setType(int type) {
		this.type = type;
	}

}
