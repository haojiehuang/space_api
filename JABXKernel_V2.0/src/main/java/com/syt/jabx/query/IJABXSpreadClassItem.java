package com.syt.jabx.query;

/**
 * 價差交易分類項目之介面
 * @author Jason
 *
 */
public interface IJABXSpreadClassItem {

	/**
	 * 取得價差交易分類群組代碼
	 * @return int
	 */
	public int getClassID();

	/**
	 * 取得價差交易分類群組名稱(UTF-8)
	 * @return String
	 */
	public String getName();

	/**
	 * 取得價差交易分類的交易所代碼
	 * @return String
	 */
	public String getExchangeID();

	/**
	 * 取得價差交易分類代碼
	 * @return String
	 */
	public String getCode();

	/**
	 * 取得價差交易分類屬性
	 * @return String
	 */
	public String getNote();
}
