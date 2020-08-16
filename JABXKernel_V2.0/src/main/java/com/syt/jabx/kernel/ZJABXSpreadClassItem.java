package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXSpreadClassItem;

/**
 * 價差交易分類項目之類別
 * @author Jason
 *
 */
final class ZJABXSpreadClassItem implements IJABXSpreadClassItem {

	/**
	 * 價差交易分類群組代碼
	 */
	private int classID;

	/**
	 * 價差交易分類群組名稱(UTF-8)
	 */
	private String name;

	/**
	 * 價差交易分類的交易所代碼
	 */
	private String exchangeID;

	/**
	 * 價差交易分類代碼
	 */
	private String code;

	/**
	 * 價差交易分類屬性
	 */
	private String note;

	/**
	 * @see com.syt.jabx.query.IJABXSpreadClassItem#getClassID()
	 */
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return classID;
	}

	/**
	 * 設定價差交易分類群組代碼
	 * @param classID - int
	 */
	public void setClassID(int classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXSpreadClassItem#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * 設定價差交易分類群組名稱(UTF-8)
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see com.syt.jabx.query.IJABXSpreadClassItem#getExchangeID()
	 */
	@Override
	public String getExchangeID() {
		// TODO Auto-generated method stub
		return exchangeID;
	}

	/**
	 * 設定價差交易分類的交易所代碼
	 * @param exchangeID - int
	 */
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXSpreadClassItem#getCode()
	 */
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	/**
	 * 設定價差交易分類代碼
	 * @param code - String
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @see com.syt.jabx.query.IJABXSpreadClassItem#getNote()
	 */
	@Override
	public String getNote() {
		// TODO Auto-generated method stub
		return note;
	}

	/**
	 * 設定價差交易分類屬性
	 * @param note - String
	 */
	public void setNote(String note) {
		this.note = note;
	}	
}
