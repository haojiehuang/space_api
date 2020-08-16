package com.syt.jabx.watch;

/**
 * 總覽所有即時報價資訊的介面
 * @author jason
 *
 */
public interface IJABXQuoteOverview {

	/**
	 * 取得註冊交易個股總數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得指向某個index的個股報價資訊介面的物件
	 * @param index - int(某個股票報價資訊介面的索引值，從 0 開始)
	 * @return IJABXQuoteItem
	 */
	public IJABXQuoteItem atIndex(int index);

	/**
	 * 取得個股代碼為 strID 的個股報價資訊介面的物件 
	 * @param strID - String(個股代碼)
	 * @return IJABXQuoteItem
	 */
	public IJABXQuoteItem atID(String strID);
}
