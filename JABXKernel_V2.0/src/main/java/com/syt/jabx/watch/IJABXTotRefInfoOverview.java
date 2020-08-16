package com.syt.jabx.watch;

/**
 * 總覽總委買賣量筆及成交筆資訊的介面
 * @author Jason
 *
 */
public interface IJABXTotRefInfoOverview {

	/**
	 * 取得總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXTotRefInfoItem
	 * @param index - int(索引值，從 0 開始)
	 * @return IJABXTotRefInfoItem
	 */
	public IJABXTotRefInfoItem atIndex(int index);
}
