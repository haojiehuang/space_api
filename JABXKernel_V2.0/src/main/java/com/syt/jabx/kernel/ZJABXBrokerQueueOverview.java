package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.watch.IJABXBrokerQueueItem;
import com.syt.jabx.watch.IJABXBrokerQueueOverview;

/**
 * 實作經紀排位索引介面之類別
 * @author jason
 *
 */
final class ZJABXBrokerQueueOverview implements IJABXBrokerQueueOverview {

	/**
	 * 買賣別
	 */
	private String orderSide;

	/**
	 * 儲存經紀排位項目介面之List物件
	 */
	private List<IJABXBrokerQueueItem> list;

	/**
	 * Constructor
	 */
	public ZJABXBrokerQueueOverview(String orderSide) {
		this.orderSide = orderSide;
		list = new ArrayList<IJABXBrokerQueueItem>();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXBrokerQueueOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXBrokerQueueOverview#atIndex(int)
	 */
	@Override
	public synchronized IJABXBrokerQueueItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 添加一ZJABXBrokerQueueItem至list中
	 * @param item - ZJABXBrokerQueueItem
	 */
	public synchronized void addItem(ZJABXBrokerQueueItem item) {
		list.add(item);
	}

	/**
	 * 取得買賣別
	 * @return String
	 */
	public String getOrderSide() {
		return orderSide;
	}

	/**
	 * 清除list中之物件
	 */
	public synchronized void clear() {
		list.clear();
	}
}
