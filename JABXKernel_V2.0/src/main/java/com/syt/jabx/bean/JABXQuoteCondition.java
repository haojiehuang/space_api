package com.syt.jabx.bean;

/**
 * 提供即時報價訂閱或回補條件之Bean
 * @author Jason
 *
 */
public class JABXQuoteCondition {

	/**
	 * 證券全代碼
	 */
	private String stkID;
	
	/**
	 * 即時報價欄位
	 */
	private int quoteID;

	/**
	 * 取得證券全代碼
	 * @return String
	 */
	public String getStkID() {
		return stkID;
	}

	/**
	 * 設定證券全代碼
	 * @param stkID - String
	 */
	public void setStkID(String stkID) {
		this.stkID = stkID;
	}

	/**
	 * 取得即時報價欄位
	 * @return int
	 */
	public int getQuoteID() {
		return quoteID;
	}

	/**
	 * 設定即時報價欄位
	 * @param quoteID - int
	 */
	public void setQuoteID(int quoteID) {
		this.quoteID = quoteID;
	}
}
