package com.syt.jabx.watch;

/**
 * 經紀排位項目介面
 * @author jason
 *
 */
public interface IJABXBrokerQueueItem {

	/**
	 * 取得經紀商代碼
	 * @return String
	 */
	public String getItem();

	/**
	 * 取得資料別
	 * @return String
	 */
	public String getType();
}
