package com.syt.jabx.smartmsg;

import java.util.List;

/**
 * 即時排名資訊索引的介面
 * @author Jason
 *
 */
public interface IJABXSmartRankOverview {

	/**
	 * 取得短線精靈資訊項目的介面總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得指向某個index的即時排名資訊項目的物件
	 * @param index - int(某個短線精靈資訊項目的索引值，從 0 開始)
	 * @return IJABXSmartRankItem
	 */
	public IJABXSmartRankItem atIndex(int index);

	/**
	 * 取得某即時排名分類代碼之即時排名資訊項目的介面之List
	 * @param classID - String(即時排名分類代碼)
	 * @return List&lt;IJABXSmartRankItem&gt;
	 */
	public List<IJABXSmartRankItem> getListByClassID(String classID);
}
