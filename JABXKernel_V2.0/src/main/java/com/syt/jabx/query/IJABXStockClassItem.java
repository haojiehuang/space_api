package com.syt.jabx.query;

/**
 * 商品分類項目的介面
 * @author Jason
 *
 */
public interface IJABXStockClassItem {

	/**
	 * 取得商品分類群組代碼
	 * @return int
	 */
	public int getClassID();

	/**
	 * 取得商品分類群組名稱(UTF-8)
	 * @return String
	 */
	public String getName();

	/**
	 * 商品分類的交易所代碼
	 * @return String
	 */
	public String getExchangeID();

	/**
	 * 取得商品分類代碼
	 * @return String
	 */
	public String getCode();

	/**
	 * 取得商品分類屬性
	 * @return String
	 */
	public String getNote();
}
