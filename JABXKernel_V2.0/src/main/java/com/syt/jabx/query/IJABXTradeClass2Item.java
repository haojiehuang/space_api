package com.syt.jabx.query;

/**
 * 產業分類2項目的介面
 * @author Jason
 *
 */
public interface IJABXTradeClass2Item {

	/**
	 * 取得分類ID
	 * @return int
	 */
	public int getClassID();

	/**
	 * 取得分類名稱(UTF-8)
	 * @return String
	 */
	public String getName();

	/**
	 * 取得分類代碼
	 * @return String
	 */
	public String getCode();

	/**
	 * 取得分類屬性
	 * @return String
	 */
	public String getAttribute();

	/**
	 * 取得報價代碼
	 * @return String
	 */
	public String getQuoteID();

	/**
	 * 取得對應的指數或商品的全代碼
	 * @return String
	 */
	public String getStkID();

	/**
	 * 取得交易代碼
	 * @return String
	 */
	public String getTradeID();
}