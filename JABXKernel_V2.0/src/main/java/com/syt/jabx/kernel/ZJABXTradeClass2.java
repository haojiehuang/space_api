package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXTradeClass2;
import com.syt.jabx.query.IJABXTradeClass2Item;

/**
 * 產業分類2索引的類別
 * @author Jason
 *
 */
final class ZJABXTradeClass2 implements IJABXTradeClass2 {

	/**
	 * 以產業分類群組代碼為Key，儲存IJABXTradeClass2Item之Map容器
	 */
	private Map<Integer, IJABXTradeClass2Item> idMap;

	/**
	 * 存放所有產業分類項目之List容器
	 */
	private List<IJABXTradeClass2Item> list;

	/**
	 * Constructor
	 */
	public ZJABXTradeClass2() {
		idMap = new HashMap<Integer, IJABXTradeClass2Item>();
		list = new ArrayList<IJABXTradeClass2Item>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXTradeClass2#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXTradeClass2#atIndex(int)
	 */
	@Override
	public synchronized IJABXTradeClass2Item atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXTradeClass2#atClassID(int)
	 */
	@Override
	public synchronized IJABXTradeClass2Item atClassID(int classID) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(classID)) {
			return (IJABXTradeClass2Item)idMap.get(classID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXTradeClass2Item物件
	 * @param item - IJABXTradeClass2Item
	 */
	public synchronized void addItem(IJABXTradeClass2Item item) {
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