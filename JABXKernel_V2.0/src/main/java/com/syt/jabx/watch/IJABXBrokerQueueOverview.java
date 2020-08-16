package com.syt.jabx.watch;

/**
 * 經紀排位索引介面
 * @author jason
 *
 */
public interface IJABXBrokerQueueOverview {

	/**
	 * 取得經紀排位項目介面數量
	 * @return int
	 */
	public int getCount();

	/**
	 * 經由索引值取得經紀排位項目介面
	 * @param index - int
	 * @return IJABXBrokerQueueItem
	 */
	public IJABXBrokerQueueItem atIndex(int index);
}
