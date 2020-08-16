package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXBlockClass;
import com.syt.jabx.query.IJABXBlockClassItem;

/**
 * 自訂分類索引的類別
 * @author Jason
 *
 */
final class ZJABXBlockClass implements IJABXBlockClass {

	/**
	 * 存放List&lt;IJABXBlockClassItem&gt;物件之Map容器
	 */
	private Map<Integer, List<IJABXBlockClassItem>> listMap;

	/**
	 * 以分類群組代碼為Key，存放ZJABXBlockClassItem之Map容器
	 */
	private Map<Integer, IJABXBlockClassItem> idMap;

	/**
	 * 存放所有自訂分類項目之List容器
	 */
	private List<IJABXBlockClassItem> itemList;

	/**
	 * Constructor
	 */
	public ZJABXBlockClass() {
		listMap = new HashMap<Integer, List<IJABXBlockClassItem>>();
		idMap = new HashMap<Integer, IJABXBlockClassItem>();
		itemList = new ArrayList<IJABXBlockClassItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXBlockClass#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXBlockClass#atIndex(int)
	 */
	@Override
	public synchronized IJABXBlockClassItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < itemList.size()) {
			return itemList.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXBlockClass#atClassID(int)
	 */
	@Override
	public synchronized IJABXBlockClassItem atClassID(int classID) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(classID)) {
			return (IJABXBlockClassItem)idMap.get(classID);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXBlockClass#getListByParentClassID(Integer)
	 */
	@Override
	public synchronized List<IJABXBlockClassItem> getListByParentClassID(int parentClassID) {
		// TODO Auto-generated method stub
		if (listMap.containsKey(parentClassID)) {
			return (List<IJABXBlockClassItem>)listMap.get(parentClassID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXFormulaClassItem物件
	 * @param item - IJABXFormulaClassItem
	 */
	public synchronized void addItem(ZJABXBlockClassItem item) {
		List<IJABXBlockClassItem> tmpItemList;
		if (listMap.containsKey(item.getParentClassID())) {
			tmpItemList = listMap.get(item.getParentClassID());
		}else {
			tmpItemList = new ArrayList<IJABXBlockClassItem>();
		}
		tmpItemList.add(item);
		listMap.put(item.getParentClassID(), tmpItemList);

		if (idMap.containsKey(item.getClassID())) {
			IJABXBlockClassItem tmpItem;
			for (int i = 0, size = itemList.size();i < size;i++) {
				tmpItem = itemList.get(i);
				if (tmpItem.getClassID() == item.getClassID()) {
					itemList.set(i, item);
					break;
				}
			}
		}else {
			itemList.add(item);
		}
		idMap.put(item.getClassID(), item);
	}

	/**
	 * 清除listMap, idMap及itemList中的物件
	 */
	public synchronized void clear() {
		listMap.clear();
		idMap.clear();
		itemList.clear();
	}
}
