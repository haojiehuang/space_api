package com.syt.jabx.watch;

/**
 * 股票成交分鐘明細索引的介面
 * @author Jason
 *
 */
public interface IJABXMinuteTradeOverview {

	/**
	 * 取得總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXMinuteTradeItem
	 * @param index - int(索引值，從 0 開始)
	 * @return IJABXMinuteTradeItem
	 */
	public IJABXMinuteTradeItem atIndex(int index);
}
