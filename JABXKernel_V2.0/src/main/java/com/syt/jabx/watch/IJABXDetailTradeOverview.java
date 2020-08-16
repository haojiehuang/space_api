package com.syt.jabx.watch;

/**
 * 總覽逐筆成交訊息(目前只support SSE,SZSE)的介面
 * @author Jason
 *
 */
public interface IJABXDetailTradeOverview {

	/**
	 * 取得總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXDetailTradeItem
	 * @param index - int(索引值，從 0 開始)
	 * @return IJABXDetailTradeItem
	 */
	public IJABXDetailTradeItem atIndex(int index);
}
