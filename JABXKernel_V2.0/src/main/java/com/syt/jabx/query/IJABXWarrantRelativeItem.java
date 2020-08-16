package com.syt.jabx.query;

/**
 * 權證標的項目介面
 * @author jason
 *
 */
public interface IJABXWarrantRelativeItem {

	/**
	 * 取得權證標的全代碼
	 * @return String
	 */
	public String getStockFullID();

	/**
	 * 取得權重
	 * @return int
	 */
	public int getWeights();

}
