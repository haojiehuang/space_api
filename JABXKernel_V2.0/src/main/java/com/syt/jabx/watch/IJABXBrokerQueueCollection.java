package com.syt.jabx.watch;

/**
 * 經紀排位集合介面
 * @author jason
 *
 */
public interface IJABXBrokerQueueCollection {

	/**
	 * 取得經紀排位索引介面數量
	 * @return int
	 */
	public int getCount();

	/**
	 * 經由索引值取得經紀排位索引介面
	 * @param index - int
	 * @return IJABXBrokerQueueOverview
	 */
	public IJABXBrokerQueueOverview atIndex(int index);

	/**
	 * 經由買賣方取得經紀排位索引介面
	 * @param stdOrderSide - String(JABXConst.ABX_ORDERSIDE_BID(買方),JABXConst.ABX_ORDERSIDE_BID(賣方))
	 * @return IJABXBrokerQueueOverview
	 */
	public IJABXBrokerQueueOverview atOrderSide(String stdOrderSide);
}
