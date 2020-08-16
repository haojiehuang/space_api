package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXClassTreeItem;

/**
 * 分類樹子項目類別
 * @author jason
 *
 */
final class ZJABXClassTreeItem implements IJABXClassTreeItem {

	/**
	 * 分類代碼
	 */
	private String classID;

	/**
	 * 分類名稱
	 */
	private String className;

	/**
	 * 分類種類
	 */
	private String classType;

	/**
	 * 分類描述
	 */
	private String classDescription;

	/**
	 * @see com.syt.jabx.query.IJABXClassTreeItem#getClassID()
	 */
	@Override
	public String getClassID() {
		// TODO Auto-generated method stub
		return classID;
	}

	/**
	 * 設定分類代碼
	 * @param classID - String
	 */
	void setClassID(String classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassTreeItem#getClassName()
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
	void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassTreeItem#getClassType()
	 */
	@Override
	public String getClassType() {
		// TODO Auto-generated method stub
		return classType;
	}

	/**
	 * 設定分類種類
	 * @param classType - String
	 */
	void setClassType(String classType) {
		this.classType = classType;
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassTreeItem#getClassDescription()
	 */
	@Override
	public String getClassDescription() {
		// TODO Auto-generated method stub
		return classDescription;
	}

	/**
	 * 設定分類描述
	 * @param classDescription - String
	 */
	void setClassDescription(String classDescription) {
		this.classDescription = classDescription;
	}
}
