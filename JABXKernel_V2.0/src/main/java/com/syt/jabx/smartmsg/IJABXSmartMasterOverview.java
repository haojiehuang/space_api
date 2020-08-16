package com.syt.jabx.smartmsg;

/**
 * 主力大單資訊索引的介面
 * @author Jason
 *
 */
public interface IJABXSmartMasterOverview {

	/**
	 * 取得主力大單資訊項目的介面總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得指向某個index的主力大單資訊項目的物件
	 * @param index - int(某個主力大單資訊項目的索引值，從 0 開始)
	 * @return IJABXSmartMasterItem
	 */
	public IJABXSmartMasterItem atIndex(int index);
}
