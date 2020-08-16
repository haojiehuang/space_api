package com.syt.jabx.watch;

/**
 * 總覽逐筆委託訊息(目前只support SZSE)的介面
 * @author Jason
 *
 */
public interface IJABXDetailOrderOverview {
	
	/**
	 * 取得總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXDetailOrderItem
	 * @param index - int(索引值，從 0 開始)
	 * @return IJABXDetailOrderItem
	 */
	public IJABXDetailOrderItem atIndex(int index);
}
