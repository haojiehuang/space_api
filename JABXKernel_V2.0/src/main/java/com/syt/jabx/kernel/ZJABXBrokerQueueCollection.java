package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.watch.IJABXBrokerQueueCollection;
import com.syt.jabx.watch.IJABXBrokerQueueOverview;

/**
 * 實作經紀排位集合介面之類別
 * @author jason
 *
 */
final class ZJABXBrokerQueueCollection implements IJABXBrokerQueueCollection {

	/**
	 * 儲存經紀排位索引介面之Map物件
	 */
	private Map<String, IJABXBrokerQueueOverview> map;

	/**
	 * 儲存買賣別之List物件
	 */
	private List<String> orderSideList;

	/**
	 * Constructor
	 */
	public ZJABXBrokerQueueCollection() {
		map = new HashMap<String, IJABXBrokerQueueOverview>();
		orderSideList = new ArrayList<String>();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXBrokerQueueCollection#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return map.size();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXBrokerQueueCollection#atIndex(int)
	 */
	@Override
	public synchronized IJABXBrokerQueueOverview atIndex(int index) {
		// TODO Auto-generated method stub
		String orderSide;
		if (index < orderSideList.size()) {
			orderSide = orderSideList.get(index);
		}else {
			orderSide = "";
		}
		return map.get(orderSide);
	}

	/**
	 * @see com.syt.jabx.watch.IJABXBrokerQueueCollection#atOrderSide(String)
	 */
	@Override
	public synchronized IJABXBrokerQueueOverview atOrderSide(String stdOrderSide) {
		// TODO Auto-generated method stub
		return map.get(stdOrderSide);
	}

	/**
	 * 添加一ZJABXBrokerQueueOverview物件
	 * @param item - ZJABXBrokerQueueOverview
	 */
	public synchronized void addItem(ZJABXBrokerQueueOverview item) {
		IJABXBrokerQueueOverview qoObj = map.get(item.getOrderSide());
		if (qoObj == null) {
			orderSideList.add(item.getOrderSide());
		}
		map.put(item.getOrderSide(), item);
	}

	/**
	 * 清除orderSideList及map中的物件
	 */
	public synchronized void clear() {
		orderSideList.clear();
		map.clear();
	}
}
