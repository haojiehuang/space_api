package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.syt.jabx.query.IJABXStockItem;
import com.syt.jabx.query.IJABXStockOverview;

/**
 * 總覧股名檔資料之類別
 * @author Jason
 *
 */
final class ZJABXStockOverview implements IJABXStockOverview {

	/**
	 * 記錄IJABXQuoteItem之List物件
	 */
	private ArrayList<IJABXStockItem> list = new ArrayList<IJABXStockItem>();

	/**
	 * 記錄IJABXStockItem之Map物件(Key為股票ID)
	 */
	private Map<String, IJABXStockItem> idMap = new HashMap<String, IJABXStockItem>();

	/**
	 * @see com.syt.jabx.query.IJABXStockOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockOverview#atIndex(int)
	 */
	@Override
	public synchronized IJABXStockItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockOverview#atID(String)
	 */
	@Override
	public synchronized IJABXStockItem atID(String strID) {
		// TODO Auto-generated method stub
		return idMap.get(strID);
	}

	/**
	 * 加入一個IJABXStockItem物件
	 * @param item - IJABXStockItem
	 */
	public synchronized void addItem(IJABXStockItem item) {
		if (idMap.containsKey(item.getID())) {
			ZJABXStockItem tmpItem = (ZJABXStockItem)idMap.get(item.getID());
			tmpItem.setName(item.getName());
			tmpItem.setListOfPinying(item.getListOfPinying());
		}else {
			list.add(item);
			idMap.put(item.getID(), item);
		}
	}

	/**
	 * 清除list及idMap中之物件
	 */
	public synchronized void clear() {
		list.clear();
		idMap.clear();
	}
}
