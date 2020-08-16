package com.syt.jabx.query;

/**
 * 自訂分類項目的介面
 * @author Jason
 *
 */
public interface IJABXBlockClassItem {

	/**
	 * 取得自訂(板塊)分類群組代碼
	 * @return int
	 */
	public int getClassID();

	/**
	 * 取得自訂(板塊)分類群組名稱(UTF-8)
	 * @return String
	 */
	public String getName();

	/**
	 * 取得自訂(板塊)分類的交易所代碼
	 * @return String
	 */
	public String getExchangeID();

	/**
	 * 取得分類屬性
	 * @return String
	 */
	public String getAttribute();

	/**
	 * 取得父節點分類群組代碼
	 * @return int
	 */
	public int getParentClassID();

}
