package com.syt.jabx.kmodel;

import java.util.List;

/**
 * 總覧警示設定項目之介面
 * @author jason
 *
 */
public interface IJABXWarningOverview {

	/**
	 * 取得設定警示之股票筆數
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得在索引值(index)之股票代碼
	 * @param index - int(索引值)
	 * @return - String
	 */
	public String atIndex(int index);

	/**
	 * 依股票代碼取得警示設定項目之List&lt;IJABXWarningItem&gt;
	 * @param stkID - String(股票代碼)
	 * @return List&lt;IJABXWarningItem&gt;
	 */
	public List<IJABXWarningItem> getList(String stkID);
}
