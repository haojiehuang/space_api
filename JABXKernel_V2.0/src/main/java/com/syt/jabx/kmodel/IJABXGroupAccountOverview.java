package com.syt.jabx.kmodel;

/**
 * 群組交易帳號索引介面
 * @author Jason
 *
 */
public interface IJABXGroupAccountOverview {

	/**
	 * 取得群組交易帳號項目介面的筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得群組交易帳號項目介面(IJABXGroupAccountItem)
	 * @param index - int(索引值，從0開始)
	 * @return IJABXGroupAccountItem
	 */
	public IJABXGroupAccountItem atIndex(int index);
}
