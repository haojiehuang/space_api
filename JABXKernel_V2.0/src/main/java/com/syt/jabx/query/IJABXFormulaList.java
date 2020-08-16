package com.syt.jabx.query;

import java.util.List;

/**
 * 公式列表索引之介面
 * @author Jason
 *
 */
public interface IJABXFormulaList {

	/**
	 * 取得公式列表項目之筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得公式列表項目的介面(IJABXFormulaListItem)
	 * @param index - int(索引值，從0開始)
	 * @return IJABXFormulaListItem
	 */
	public IJABXFormulaListItem atIndex(int index);

	/**
	 * 以公式序號取得公式列表項目的介面(IJABXFormulaListItem)
	 * @param formulaID - int(公式序號)
	 * @return IJABXFormulaListItem
	 */
	public IJABXFormulaListItem atFormulaID(int formulaID);

	/**
	 * 取得某分類序號之公式列表項目之List
	 * @param classID - Integer(分類序號)
	 * @return List&lt;IJABXFormulaNameItem&gt;
	 */
	public List<IJABXFormulaListItem> getListByClassID(Integer classID);

}
