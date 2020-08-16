package com.syt.jabx.query;

/**
 * 股票報價數據下載的項目介面
 * @author Jason
 *
 */
public interface IJABXQuoteDataItem {

	/**
	 * 證券全代碼
	 * @return String
	 */
	public String getID();

	/**
	 * 取得股票報價數據<br>
	 * 格式為："資料日期(YYYYMMDD)|欄位1|欄位2|...;資料日期(YYYYMMDD)|欄位1|欄位2|...;"，<br>
	 * 各筆間以分號分隔，各欄位以｜分隔。
	 * @return String
	 */
	public String getData();

}
