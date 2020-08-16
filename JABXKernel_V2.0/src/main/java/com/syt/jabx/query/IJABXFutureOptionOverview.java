package com.syt.jabx.query;

/**
 * 總覧期權股名項目介面
 * @author jason
 *
 */
public interface IJABXFutureOptionOverview {

	/**
	 * 取得自訂索引項目之筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXFutureOptionItem物件
	 * @param index - int(索引值，從0開始)
	 * @return IJABXFutureOptionItem
	 */
	public IJABXFutureOptionItem atIndex(int index);

	/**
	 * 取得Key為stkID之IJABXFutureOptionItem物件
	 * @param stkID - String(股票全代碼)
	 * @return IJABXFutureOptionItem
	 */
	public IJABXFutureOptionItem atStkID(String stkID);
}
