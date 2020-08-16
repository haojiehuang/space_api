package com.syt.jabx.message;

import java.util.List;

/**
 * 行情報導標題索引的介面
 * @author Jason
 *
 */
public interface IJABXMarketReportOverview {

	/**
	 * 取得行情報導標題項目的介面總筆數 
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得指向某個index的行情報導標題項目的物件
	 * @param index - int(某個行情報導標題項目的索引值，從 0 開始)
	 * @return IJABXMarketReportItem
	 */
	public IJABXMarketReportItem atIndex(int index);

	/**
	 * 以行情報導序號取得行情報導標題項目的物件
	 * @param serialNo - int(行情報導序號)
	 * @return IJABXMarketReportItem
	 */
	public IJABXMarketReportItem atSerialNo(int serialNo);

	/**
	 * 依行情報導代碼取得行情報導標題項目的列表
	 * @param classID - String(行情報導代碼)
	 * @return List&lt;IJABXMarketReportItem&gt;
	 */
	public List<IJABXMarketReportItem> getListByClassID(String classID);

}
