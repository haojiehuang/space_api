package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXStockClass;
import com.syt.jabx.query.IJABXStockClassItem;

/**
 * 商品分類索引的類別
 * @author Jason
 *
 */
final class ZJABXStockClass implements IJABXStockClass {

	/**
	 * 以商品分類群組代碼為Key，儲存IJABXStockClassItem之Map容器
	 */
	private Map<Integer, IJABXStockClassItem> idMap;

	/**
	 * 存放所有商品分類項目之List容器
	 */
	private List<IJABXStockClassItem> list;

	/**
	 * Constructor
	 */
	public ZJABXStockClass() {
		idMap = new HashMap<Integer, IJABXStockClassItem>();
		list = new ArrayList<IJABXStockClassItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockClass#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockClass#atIndex(int)
	 */
	@Override
	public synchronized IJABXStockClassItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockClass#atClassID(int)
	 */
	@Override
	public synchronized IJABXStockClassItem atClassID(int classID) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(classID)) {
			return (IJABXStockClassItem)idMap.get(classID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXStockClassItem物件
	 * @param item - IJABXStockClassItem
	 */
	public synchronized void addItem(IJABXStockClassItem item) {
		if (idMap.containsKey(item.getClassID())) {
			IJABXStockClassItem tmpItem;
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
