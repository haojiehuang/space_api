package com.syt.jabx.query;

/**
 * 產品功能類別之介面
 * @author Jason
 *
 */
public interface IJABXProductClass {

	/**
	 * 取得總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXProductClassItem物件
	 * @param index - int(索引值，從0開始)
	 * @return IJABXProductClassItem
	 */
	public IJABXProductClassItem atIndex(int index);
}
