package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXSmartShortClassItem;

/**
 * 短線精靈分類項目之類別
 * @author Jason
 *
 */
final class ZJABXSmartShortClassItem implements IJABXSmartShortClassItem {

	/**
	 * 分類群組代碼
	 */
	private int classID;

	/**
	 * 短線精靈分類群組名稱(UTF-8)
	 */
	private String name;

	/**
	 * 短線精靈分類的交易所代碼
	 */
	private String exchangeID;

	/**
	 * @see com.syt.jabx.query.IJABXSmartShortClassItem#getClassID()
	 */
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return classID;
	}

	/**
	 * 設定分類群組代碼
	 * @param classID - int
	 */
	public void setClassID(int classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartShortClassItem#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * 設定短線精靈分類群組名稱(UTF-8)
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartShortClassItem#getExchangeID()
	 */
	@Override
	public String getExchangeID() {
		// TODO Auto-generated method stub
		return exchangeID;
	}

	/**
	 * 設定短線精靈分類的交易所代碼
	 * @param exchangeID - int
	 */
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}
}
