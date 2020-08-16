package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXSpreadClass;
import com.syt.jabx.query.IJABXSpreadClassItem;

/**
 * 價差交易分類索引之類別
 * @author Jason
 *
 */
final class ZJABXSpreadClass implements IJABXSpreadClass {

	/**
	 * 以價差交易分類群組代碼為Key，存放IJABXSpreadClassItem之Map容器
	 */
	private Map<Integer, IJABXSpreadClassItem> idMap;

	/**
	 * 存放所有價差交易分類項目之List容器
	 */
	private List<IJABXSpreadClassItem> list;

	/**
	 * Constructor
	 */
	public ZJABXSpreadClass() {
		idMap = new HashMap<Integer, IJABXSpreadClassItem>();
		list = new ArrayList<IJABXSpreadClassItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXSpreadClassClass#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXSpreadClassClass#atIndex(int)
	 */
	@Override
	public synchronized IJABXSpreadClassItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXSpreadClassClass#atClassID(int)
	 */
	@Override
	public synchronized IJABXSpreadClassItem atClassID(int classID) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(classID)) {
			return (IJABXSpreadClassItem)idMap.get(classID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXSpreadClassItem物件
	 * @param item - IJABXSpreadClassItem
	 */
	public synchronized void addItem(IJABXSpreadClassItem item) {
		if (idMap.containsKey(item.getClassID())) {
			IJABXSpreadClassItem tmpItem;
			for (int i = 0, size = list.size();i < size;i++) {
				tmpItem = list.get(i);
				if (tmpItem.getClassID() == item.getClassID()) {
					list.set(i, item);
					break;
				}
			}
		}else {
			list.add(item);
		}
		idMap.put(item.getClassID(), item);
	}

	/**
	 * 清除list及idMap中之物件
	 */
	public synchronized void clear() {
		idMap.clear();
		list.clear();
	}
}
