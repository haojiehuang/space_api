package com.syt.jabx.query;

/**
 * 股票報價數據下載的索引介面
 * @author Jason
 *
 */
public interface IJABXQuoteData {

	/**
	 * 取得股票報價數據下載的項目介面數量
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXQuoteDataItem物件
	 * @param index - int(索引值，從0開始)
	 * @return IJABXQuoteDataItem
	 */
	public IJABXQuoteDataItem atIndex(int index);

	/**
	 * 以證券全代碼取得股票報價數據下載的項目介(IJABXQuoteDataItem)
	 * @param id - String(證券全代碼)
	 * @return IJABXQuoteDataItem
	 */
	public IJABXQuoteDataItem atID(String id);
}
