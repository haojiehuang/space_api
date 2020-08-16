package com.syt.jabx.query;

import java.util.List;

/**
 * 公式分類索引之介面
 * @author Jason
 *
 */
public interface IJABXFormulaClass {

	/**
	 * 取得公式分類項目之筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得公式分類項目的介面(IJABXFormulaClassItem)
	 * @param index - int(索引值，從0開始)
	 * @return IJABXFormulaClassItem
	 */
	public IJABXFormulaClassItem atIndex(int index);

	/**
	 * 以分類序號取得公式分類項目的介面(IJABXFormulaClassItem)
	 * @param classID - int(商品分類群組代碼)
	 * @return IJABXFormulaClassItem
	 */
	public IJABXFormulaClassItem atClassID(int classID);

	/**
	 * 取得某一節點之公式分類項目列表
	 * @param parentClassID - Integer(父節點分類序號)
	 * @return List&lt;IJABXFormulaClassItem&gt;
	 */
	public List<IJABXFormulaClassItem> getListByParentClassID(int parentClassID);
}
