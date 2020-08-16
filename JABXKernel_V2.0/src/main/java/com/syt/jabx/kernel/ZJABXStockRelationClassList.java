package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXStockRelationClassList;
import com.syt.jabx.query.IJABXStockRelationClassListItem;

/**
 * 股票關連分類列表索引之類別
 * @author Jason
 *
 */
final class ZJABXStockRelationClassList implements IJABXStockRelationClassList {

	/**
	 * 以證券全代碼為Key，存放IJABXStockRelationClassListItem之Map容器
	 */
	private Map<String, IJABXStockRelationClassListItem> idMap;

	/**
	 * 存放所有股票關連分類列表項目之List容器
	 */
	private List<IJABXStockRelationClassListItem> list;

	/**
	 * Constructor
	 */
	public ZJABXStockRelationClassList() {
		idMap = new HashMap<String, IJABXStockRelationClassListItem>();
		list = new ArrayList<IJABXStockRelationClassListItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassRelationStockList#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassRelationStockList#atIndex(int)
	 */
	@Override
	public synchronized IJABXStockRelationClassListItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationClassList#atID(String)
	 */
	@Override
	public synchronized IJABXStockRelationClassListItem atID(String id) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(id)) {
			return (IJABXStockRelationClassListItem)idMap.get(id);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXStockRelationClassListItem物件
	 * @param item - IJABXStockRelationClassListItem
	 */
	public synchronized void addItem(IJABXStockRelationClassListItem item) {
		if (idMap.containsKey(item.getID())) {
			IJABXStockRelationClassListItem tmpItem;
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
