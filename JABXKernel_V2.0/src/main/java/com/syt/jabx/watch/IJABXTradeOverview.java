package com.syt.jabx.watch;

/**
 * 總覽股票成交分筆的介面
 * @author Jason
 *
 */
public interface IJABXTradeOverview {

	/**
	 * 取得總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXTradeItem
	 * @param index - int((索引值，從 0 開始))
	 * @return IJABXTradeItem
	 */
	public IJABXTradeItem atIndex(int index);
}
