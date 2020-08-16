package com.syt.jabx.query;

/**
 * 其他分類項目之介面
 * @author Jason
 *
 */
public interface IJABXOtherClassItem {

	/**
	 * 取得其他分類群組代碼
	 * @return int
	 */
	public int getClassID();

	/**
	 * 取得其他分類群組名稱(UTF-8)
	 * @return String
	 */
	public String getName();

	/**
	 * 取得其他分類的交易所代碼
	 * @return String
	 */
	public String getExchangeID();

	/**
	 * 取得其他分類代碼
	 * @return String
	 */
	public String getCode();

	/**
	 * 取得其他分類屬性
	 * @return String
	 */
	public String getNote();
}
