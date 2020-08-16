package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXTradeClass2Item;

/**
 * 產業分類2項目的類別
 * @author Jason
 *
 */
final class ZJABXTradeClass2Item implements IJABXTradeClass2Item {

	/**
	 * 分類ID
	 */
	private int classID;

	/**
	 * 產業名稱(UTF-8)
	 */
	private String name;

	/**
	 * 分類代碼
	 */
	private String code;

	/**
	 * 分類屬性
	 */
	private String attribute;

	/**
	 * 報價代碼
	 */
	private String quoteID;

	/**
	 * 對應的指數或商品的全代碼
	 */
	private String stkID;

	/**
	 * 交易代碼
	 */
	private String tradeID;

	/**
	 * @see com.syt.jabx.query.IJABXTradeClass2Item#getClassID()
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
	 * @see com.syt.jabx.query.IJABXTradeClass2Item#getName()
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
	 * @see com.syt.jabx.query.IJABXTradeClass2Item#getCode()
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
	 * @see com.syt.jabx.query.IJABXTradeClass2Item#getAttribute()
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
	 * @see com.syt.jabx.query.IJABXTradeClass2Item#getQuoteID()
	 */
	@Override
	public String getQuoteID() {
		// TODO Auto-generated method stub
		return quoteID;
	}

	/**
	 * 設定報價代碼
	 * @param quoteID - String
	 */
	public void setQuoteID(String quoteID) {
		this.quoteID = quoteID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTradeClass2Item#getStkID()
	 */
	@Override
	public String getStkID() {
		// TODO Auto-generated method stub
		return stkID;
	}

	/**
	 * 設定對應的指數或商品的全代碼
	 * @param stkID - String
	 */
	public void setStkID(String stkID) {
		this.stkID = stkID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTradeClass2Item#getTradeID()
	 */
	@Override
	public String getTradeID() {
		// TODO Auto-generated method stub
		return tradeID;
	}

	/**
	 * 設定交易代碼
	 * @param tradeID - String
	 */
	public void setTradeID(String tradeID) {
		this.tradeID = tradeID;
	}

}
