package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXSmartShortClass;
import com.syt.jabx.query.IJABXSmartShortClassItem;

/**
 * 短線精靈分類索引之類別
 * @author Jason
 *
 */
final class ZJABXSmartShortClass implements IJABXSmartShortClass {

	/**
	 * 以分類群組代碼為Key，存放IJABXSmartShortClassItem之Map容器
	 */
	private Map<Integer, IJABXSmartShortClassItem> idMap;

	/**
	 * 存放所有短線精靈分類項目之List容器
	 */
	private List<IJABXSmartShortClassItem> list;

	/**
	 * Constructor
	 */
	public ZJABXSmartShortClass() {
		idMap = new HashMap<Integer, IJABXSmartShortClassItem>();
		list = new ArrayList<IJABXSmartShortClassItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartShortClass#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartShortClass#atIndex(int)
	 */
	@Override
	public synchronized IJABXSmartShortClassItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartShortClass#atClassID(int)
	 */
	@Override
	public synchronized IJABXSmartShortClassItem atClassID(int classID) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(classID)) {
			return (IJABXSmartShortClassItem)idMap.get(classID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXSmartShortClassItem物件
	 * @param item - IJABXSmartShortClassItem
	 */
	public synchronized void addItem(IJABXSmartShortClassItem item) {
		if (idMap.containsKey(item.getClassID())) {
			IJABXSmartShortClassItem tmpItem;
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
