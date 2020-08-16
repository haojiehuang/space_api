package com.syt.jabx.watch;

/**
 * 股票成交分筆項目的介面
 * @author Jason
 *
 */
public interface IJABXTradeItem extends IJABXTrade {
	
	/**
	 * 取得委買價
	 * @return int
	 */
	public int getBidPrice();

	/**
	 * 取得委賣價
	 * @return int
	 */
	public int getAskPrice();
}
