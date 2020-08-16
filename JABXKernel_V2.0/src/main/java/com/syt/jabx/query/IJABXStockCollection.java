package com.syt.jabx.query;

/**
 * 股名檔之Collection介面
 * @author Jason
 *
 */
public interface IJABXStockCollection {

	/**
	 * 依股名檔檔名稱取得IJABXStockOverview介面
	 * @param fileName - String(股名檔檔名)
	 * @return IJABXStockOverview
	 */
	public IJABXStockOverview get(String fileName);
}
