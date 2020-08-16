package com.syt.jabx.watch;

/**
 * 總覽交易所統計訊息的介面
 * @author Jason
 *
 */
public interface IJABXStatisticOverview {

	/**
	 * 取得總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXStatisticItem
	 * @param index - int(索引值，從 0 開始)
	 * @return IJABXStatisticItem
	 */
	public IJABXStatisticItem atIndex(int index);
}
