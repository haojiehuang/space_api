package com.syt.jabx.query;

/**
 * 公式列表項目之介面
 * @author Jason
 *
 */
public interface IJABXFormulaListItem {

	/**
	 * 取得公式序號
	 * @return int
	 */
	public int getFormulaID();

	/**
	 * 取得分類序號
	 * @return int
	 */
	public int getClassID();

	/**
	 * 取得公式英文名
	 * @return String
	 */
	public String getName();

	/**
	 * 取得公式中文名
	 * @return String
	 */
	public String getChineseName();

	/**
	 * 取得展示位置<br>
	 * 0.非線圖公式(選股)<br>
	 * 1.主圖疊加<br>
	 * 2.輔圖<br>
	 * 3.線圖公式不展示(選圖)<br>
	 * @return int
	 */
	public int getPosType();
}
