package com.syt.jabx.watch;

/**
 * 成交價量明細索引介面
 * @author jason
 *
 */
public interface IJABXPriceTradeOverview {

	/**
	 * 取得成交價量明細項目介面數量
	 * @return int
	 */
	public int getCount();

	/**
	 * 經由索引値(index)取得成交價量明細項目介面
	 * @param index - int
	 * @return IJABXPriceTradeItem
	 */
	public IJABXPriceTradeItem atIndex(int index);
}
