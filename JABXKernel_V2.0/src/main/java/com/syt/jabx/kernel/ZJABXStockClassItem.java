package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXStockClassItem;

/**
 * 商品分類項目的類別
 * @author Jason
 *
 */
final class ZJABXStockClassItem implements IJABXStockClassItem {

	/**
	 * 商品分類群組代碼
	 */
	private int classID;

	/**
	 * 商品分類群組名稱(UTF-8)
	 */
	private String name;

	/**
	 * 商品分類的交易所代碼
	 */
	private String exchangeID;

	/**
	 * 商品分類代碼
	 */
	private String code;

	/**
	 * 商品分類屬性
	 */
	private String note;

	/**
	 * @see com.syt.jabx.query.IJABXStockClassItem#getClassID()
	 */
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return classID;
	}

	/**
	 * 設定商品分類群組代碼
	 * @param classID - int
	 */
	public void setClassID(int classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockClassItem#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * 設定商品分類群組名稱(UTF-8)
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockClassItem#getExchangeID()
	 */
	@Override
	public String getExchangeID() {
		// TODO Auto-generated method stub
		return exchangeID;
	}

	/**
	 * 設定商品分類的交易所代碼
	 * @param exchangeID - String
	 */
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockClassItem#getCode()
	 */
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	/**
	 * 設定商品分類代碼
	 * @param code - String
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockClassItem#getNote()
	 */
	@Override
	public String getNote() {
		// TODO Auto-generated method stub
		return note;
	}

	/**
	 * 設定商品分類屬性
	 * @param note - String
	 */
	public void setNote(String note) {
		this.note = note;
	}

}
