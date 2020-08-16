package com.syt.jabx.query;

/**
 * 產業分類項目的介面
 * @author Jason
 *
 */
public interface IJABXTradeClassItem {

	/**
	 * 取得產業分類群組代碼
	 * @return int
	 */
	public int getClassID();

	/**
	 * 取得產業分類群組名稱(UTF-8)
	 * @return String
	 */
	public String getName();

	/**
	 * 取得產業分類的交易所代碼
	 * @return String
	 */
	public String getExchangeID();

	/**
	 * 取得產業分類代碼
	 * @return String
	 */
	public String getCode();

	/**
	 * 取得分類屬性
	 * @return String
	 */
	public String getAttribute();
}
