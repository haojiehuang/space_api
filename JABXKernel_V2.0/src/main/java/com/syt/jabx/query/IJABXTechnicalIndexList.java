package com.syt.jabx.query;

import java.util.List;

/**
 * 查詢技術指標列表之介面
 * @author Jason
 *
 */
public interface IJABXTechnicalIndexList {

	/**
	 * 取得總筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之IJABXTechnicalIndexListItem
	 * @param index - int(索引值，從 0 開始)
	 * @return IJABXTechnicalIndexListItem
	 */
	public IJABXTechnicalIndexListItem atIndex(int index);

	/**
	 * 以formulaID取得IJABXTechnicalIndexListItem
	 * @param formulaID - int
	 * @return IJABXTechnicalIndexListItem
	 */
	public IJABXTechnicalIndexListItem atFormulaID(int formulaID);

	/**
	 * 取得某技術分類之技術指標列表
	 * @param classID - int(技術指標分類序號)
	 * @return List&lt;IJABXFormulaNameItem&gt;
	 */
	public List<IJABXTechnicalIndexListItem> getListByClassID(int classID);
}
