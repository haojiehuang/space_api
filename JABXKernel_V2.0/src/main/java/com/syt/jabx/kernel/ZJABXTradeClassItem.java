package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXTradeClassItem;

/**
 * 產業分類項目的類別
 * @author Jason
 *
 */
final class ZJABXTradeClassItem implements IJABXTradeClassItem {

	/**
	 * 產業分類群組代碼
	 */
	private int classID;

	/**
	 * 產業分類群組名稱(UTF-8)
	 */
	private String name;

	/**
	 * 產業分類的交易所代碼
	 */
	private String exchangeID;

	/**
	 * 產業分類代碼
	 */
	private String code;

	/**
	 * 分類屬性
	 */
	private String attribute;

	/**
	 * @see com.syt.jabx.query.IJABXTradeClassItem#getClassID()
	 */
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return classID;
	}

	/**
	 * 設定產業分類群組代碼
	 * @param classID - int
	 */
	public void setClassID(int classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTradeClassItem#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * 設定產業分類群組名稱(UTF-8)
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTradeClassItem#getExchangeID()
	 */
	@Override
	public String getExchangeID() {
		// TODO Auto-generated method stub
		return exchangeID;
	}

	/**
	 * 設定產業分類的交易所代碼
	 * @param exchangeID - String
	 */
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTradeClassItem#getCode()
	 */
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	/**
	 * 設定產業分類代碼
	 * @param code - String
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTradeClassItem#getAttribute()
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
}
