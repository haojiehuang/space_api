package com.syt.jabx.query;

/**
 * 交易所列表索引之介面
 * @author Jason
 *
 */
public interface IJABXExchangeList {

	/**
	 * 取得總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXExchangeListItem物件
	 * @param index - int(索引值，從0開始)
	 * @return IJABXExchangeListItem
	 */
	public IJABXExchangeListItem atIndex(int index);

	/**
	 * 以交易所代碼取得交易所列表項目之介面(IJABXExchangeListItem)
	 * @param id - String(交易所代碼)
	 * @return IJABXExchangeListItem
	 */
	public IJABXExchangeListItem atID(String id);
}
