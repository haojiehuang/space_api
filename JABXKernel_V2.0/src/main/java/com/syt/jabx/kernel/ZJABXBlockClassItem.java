package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXBlockClassItem;

/**
 * 自訂分類項目的類別
 * @author Jason
 *
 */
final class ZJABXBlockClassItem implements IJABXBlockClassItem {

	/**
	 * 自訂(板塊)分類群組代碼
	 */
	private int classID;

	/**
	 * 自訂(板塊)分類群組名稱(UTF-8)
	 */
	private String name;

	/**
	 * 自訂(板塊)分類的交易所代碼
	 */
	private String exchangeID;

	/**
	 * 分類屬性
	 */
	private String attribute;

	/**
	 * 父節點分類群組代碼
	 */
	private int parentClassID;

	/**
	 * @see com.syt.jabx.query.IJABXBlockClassItem#getClassID()
	 */
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return classID;
	}

	/**
	 * 設定自訂(板塊)分類群組代碼
	 * @param classID - int
	 */
	public void setClassID(int classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXBlockClassItem#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * 設定自訂(板塊)分類群組名稱(UTF-8)
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see com.syt.jabx.query.IJABXBlockClassItem#getExchangeID()
	 */
	@Override
	public String getExchangeID() {
		// TODO Auto-generated method stub
		return exchangeID;
	}

	/**
	 * 設定自訂(板塊)分類的交易所代碼
	 * @param exchangeID - String
	 */
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXBlockClassItem#getAttribute()
	 */
	@Override
	public String getAttribute() {
		// TODO Auto-generated method stub
		return attribute;
	}

	/**
	 * 設定分類屬性
	 * @param attribute - String
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	/**
	 * @see com.syt.jabx.query.IJABXBlockClassItem#getParentClassID()
	 */
	@Override
	public int getParentClassID() {
		// TODO Auto-generated method stub
		return parentClassID;
	}

	/**
	 * 設定父節點分類群組代碼
	 * @param parentClassID - int
	 */
	public void setParentClassID(int parentClassID) {
		this.parentClassID = parentClassID;
	}

}
