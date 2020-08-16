package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXSmartRankClass;
import com.syt.jabx.query.IJABXSmartRankClassItem;

/**
 * 即時排名分類索引之類別
 * @author Jason
 *
 */
final class ZJABXSmartRankClass implements IJABXSmartRankClass {

	/**
	 * 以即時排名分類群組代碼為Key，存放IJABXSmartRankClassItem之Map容器
	 */
	private Map<Integer, IJABXSmartRankClassItem> idMap;

	/**
	 * 存放所有即時排名分類項目之List容器
	 */
	private List<IJABXSmartRankClassItem> list;

	/**
	 * Constructor
	 */
	public ZJABXSmartRankClass() {
		idMap = new HashMap<Integer, IJABXSmartRankClassItem>();
		list = new ArrayList<IJABXSmartRankClassItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartRankClass#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartRankClass#atIndex(int)
	 */
	@Override
	public synchronized IJABXSmartRankClassItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartRankClass#atClassID(int)
	 */
	@Override
	public synchronized IJABXSmartRankClassItem atClassID(int classID) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(classID)) {
			return (IJABXSmartRankClassItem)idMap.get(classID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXSmartRankClassItem物件
	 * @param item - IJABXSmartRankClassItem
	 */
	public synchronized void addItem(IJABXSmartRankClassItem item) {
		if (idMap.containsKey(item.getClassID())) {
			IJABXSmartRankClassItem tmpItem;
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
