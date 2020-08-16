package com.syt.jabx.query;

/**
 * 檔差項目索引介面
 * @author jason
 *
 */
public interface IJABXTickDiff {

	/**
	 * 取得檔差項目介面的筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得檔差項目介面(IJABXStkDiffItem)
	 * @param index - int(索引值，從0開始)
	 * @return IJABXStkDiffItem
	 */
	public IJABXTickDiffItem atIndex(int index);
}
