package com.syt.jabx.query;

/**
 * 股票除權息索引的介面
 * @author Jason
 *
 */
public interface IJABXStockDivide {

	/**
	 * 取得股票除權息索引的證券全代碼
	 * @return String
	 */
	public String getID();

	/**
	 * 取得此股票除權息項目的筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXStockDivideItem物件
	 * @param index - int(索引值，從0開始)
	 * @return IJABXStockDivideItem
	 */
	public IJABXStockDivideItem atIndex(int index);
}
