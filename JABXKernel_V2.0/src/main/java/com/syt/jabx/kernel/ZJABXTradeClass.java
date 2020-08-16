package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXTradeClass;
import com.syt.jabx.query.IJABXTradeClassItem;

/**
 * 產業分類索引的類別
 * @author Jason
 *
 */
final class ZJABXTradeClass implements IJABXTradeClass {

	/**
	 * 以產業分類群組代碼為Key，儲存IJABXTradeClassItem之Map容器
	 */
	private Map<Integer, IJABXTradeClassItem> idMap;

	/**
	 * 存放所有產業分類項目之List容器
	 */
	private List<IJABXTradeClassItem> list;

	/**
	 * Constructor
	 */
	public ZJABXTradeClass() {
		idMap = new HashMap<Integer, IJABXTradeClassItem>();
		list = new ArrayList<IJABXTradeClassItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXTradeClass#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXTradeClass#atIndex(int)
	 */
	@Override
	public synchronized IJABXTradeClassItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXTradeClass#atClassID(int)
	 */
	@Override
	public synchronized IJABXTradeClassItem atClassID(int classID) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(classID)) {
			return (IJABXTradeClassItem)idMap.get(classID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXTradeClassItem物件
	 * @param item - IJABXTradeClassItem
	 */
	public synchronized void addItem(IJABXTradeClassItem item) {
		idMap.put(item.getClassID(), item);
		list.add(item);
	}

	/**
	 * 清除list及idMap中之物件
	 */
	public synchronized void clear() {
		idMap.clear();
		list.clear();
	}
}
