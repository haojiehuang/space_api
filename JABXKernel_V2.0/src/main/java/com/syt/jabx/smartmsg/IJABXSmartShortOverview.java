package com.syt.jabx.smartmsg;

import java.util.List;

/**
 * 短線精靈資訊索引的介面
 * @author Jason
 *
 */
public interface IJABXSmartShortOverview {

	/**
	 * 取得短線精靈資訊項目的介面總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得指向某個index的短線精靈資訊項目的物件
	 * @param index - int(某個短線精靈資訊項目的索引值，從 0 開始)
	 * @return IJABXSmartShortItem
	 */
	public IJABXSmartShortItem atIndex(int index);

	/**
	 * 取得某短線精靈分類代碼之短線精靈資訊項目的介面之List
	 * @param classID - String(短線精靈分類代碼)
	 * @return List&lt;IJABXSmartShortItem&gt;
	 */
	public List<IJABXSmartShortItem> getListByClassID(String classID);
}
