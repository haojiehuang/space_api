package com.syt.jabx.query;

/**
 * 交易所1關連交易所2股票子項目之介面
 * @author Jason
 *
 */
public interface IJABXStockRelationStockListItem {

	/**
	 * 取得證券全代碼1
	 * @return String
	 */
	public String getID();

	/**
	 * 取得關連分類代碼
	 * @return int
	 */
	public int getRelationClassID();

	/**
	 * 取得證券全代碼2
	 * @return String
	 */
	public String getID2();
}
