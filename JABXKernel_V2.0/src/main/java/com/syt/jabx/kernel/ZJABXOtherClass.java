package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXOtherClass;
import com.syt.jabx.query.IJABXOtherClassItem;

/**
 * 其他分類索引之類別
 * @author Jason
 *
 */
final class ZJABXOtherClass implements IJABXOtherClass {

	/**
	 * 以其他分類群組代碼為Key，存放IJABXOtherClassItem之Map容器
	 */
	private Map<Integer, IJABXOtherClassItem> idMap;

	/**
	 * 存放所有其他分類項目之List容器
	 */
	private List<IJABXOtherClassItem> list;

	/**
	 * Constructor
	 */
	public ZJABXOtherClass() {
		idMap = new HashMap<Integer, IJABXOtherClassItem>();
		list = new ArrayList<IJABXOtherClassItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXOtherClass#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXOtherClass#atIndex(int)
	 */
	@Override
	public synchronized IJABXOtherClassItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXOtherClass#atClassID(int)
	 */
	@Override
	public synchronized IJABXOtherClassItem atClassID(int classID) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(classID)) {
			return (IJABXOtherClassItem)idMap.get(classID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXOtherClassItem物件
	 * @param item - IJABXOtherClassItem
	 */
	public synchronized void addItem(IJABXOtherClassItem item) {
		if (idMap.containsKey(item.getClassID())) {
			IJABXOtherClassItem tmpItem;
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
