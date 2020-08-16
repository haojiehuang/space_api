package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXOtherClassItem;

/**
 * 其他分類項目之類別
 * @author Jason
 *
 */
final class ZJABXOtherClassItem implements IJABXOtherClassItem {

	/**
	 * 其他分類群組代碼
	 */
	private int classID;

	/**
	 * 其他分類群組名稱(UTF-8)
	 */
	private String name;

	/**
	 * 其他分類的交易所代碼
	 */
	private String exchangeID;

	/**
	 * 其他分類
	 */
	private String code;

	/**
	 * 其他分類屬性
	 */
	private String note;

	/**
	 * @see com.syt.jabx.query.IJABXOtherClassItem#getClassID()
	 */
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return classID;
	}

	/**
	 * 設定其他分類群組代碼
	 * @param classID - int
	 */
	public void setClassID(int classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXOtherClassItem#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * 設定其他分類群組名稱(UTF-8)
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see com.syt.jabx.query.IJABXOtherClassItem#getExchangeID()
	 */
	@Override
	public String getExchangeID() {
		// TODO Auto-generated method stub
		return exchangeID;
	}

	/**
	 * 設定其他分類的交易所代碼
	 * @param exchangeID - int
	 */
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXOtherClassItem#getCode()
	 */
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	/**
	 * 設定其他分類代碼
	 * @param code - String
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @see com.syt.jabx.query.IJABXOtherClassItem#getNote()
	 */
	@Override
	public String getNote() {
		// TODO Auto-generated method stub
		return note;
	}

	/**
	 * 設定其他分類屬性
	 * @param note - String
	 */
	public void setNote(String note) {
		this.note = note;
	}	
}
