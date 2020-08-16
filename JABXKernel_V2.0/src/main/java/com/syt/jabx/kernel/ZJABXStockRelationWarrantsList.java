package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXStockRelationWarrantsList;

/**
 * 股票關連權證股票索引類別
 * @author Jason
 *
 */
final class ZJABXStockRelationWarrantsList implements IJABXStockRelationWarrantsList {

	/**
	 * 以證券全代碼為Key，儲存ZJABXStockRelationWarrantsListItem之Map容器
	 */
	private Map<String, ZJABXStockRelationWarrantsListItem> idMap;

	/**
	 * 儲放所有股票關連權證股票的項目介面之List容器
	 */
	private List<ZJABXStockRelationWarrantsListItem> list;

	/**
	 * Constructor
	 */
	public ZJABXStockRelationWarrantsList() {
		idMap = new HashMap<String, ZJABXStockRelationWarrantsListItem>();
		list = new ArrayList<ZJABXStockRelationWarrantsListItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationWarrantsList#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationWarrantsList#atIndex(int)
	 */
	@Override
	public synchronized ZJABXStockRelationWarrantsListItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationWarrantsList#atID(String)
	 */
	@Override
	public synchronized ZJABXStockRelationWarrantsListItem atID(String stkID) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(stkID)) {
			return (ZJABXStockRelationWarrantsListItem)idMap.get(stkID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一ZJABXStockRelationWarrantsListItem物件
	 * @param item - ZJABXStockRelationWarrantsListItem
	 */
	public synchronized void addItem(ZJABXStockRelationWarrantsListItem item) {
		if (idMap.containsKey(item.getID())) {
			ZJABXStockRelationWarrantsListItem tmpItem;
			for (int i = 0, size = list.size();i < size;i++) {
				tmpItem = list.get(i);
				if (tmpItem.getID().equals(item.getID())) {
					list.set(i, item);
					break;
				}
			}
		}else {
			list.add(item);
		}
		idMap.put(item.getID(), item);
	}

	/**
	 * 清除list及idMap中之物件
	 */
	public synchronized void clear() {
		idMap.clear();
		list.clear();
	}
}
