package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXFormulaClass;
import com.syt.jabx.query.IJABXFormulaClassItem;

/**
 * 公式分類索引之類別
 * @author Jason
 *
 */
final class ZJABXFormulaClass implements IJABXFormulaClass {

	/**
	 * 存放List&lt;IJABXFormulaClassItem&gt;物件之Map容器
	 */
	private Map<Integer, List<IJABXFormulaClassItem>> listMap;

	/**
	 * 以分類序號為Key，存放IJABXFormulaClassItem之Map容器
	 */
	private Map<Integer, IJABXFormulaClassItem> idMap;

	/**
	 * 存放所有公式分類項目之List容器
	 */
	private List<IJABXFormulaClassItem> itemList;

	/**
	 * Constructor
	 */
	public ZJABXFormulaClass() {
		listMap = new HashMap<Integer, List<IJABXFormulaClassItem>>();
		idMap = new HashMap<Integer, IJABXFormulaClassItem>();
		itemList = new ArrayList<IJABXFormulaClassItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXFormulaClass#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXFormulaClass#atIndex(int)
	 */
	@Override
	public synchronized IJABXFormulaClassItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < itemList.size()) {
			return itemList.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXFormulaClass#atClassID(int)
	 */
	@Override
	public synchronized IJABXFormulaClassItem atClassID(int classID) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(classID)) {
			return (IJABXFormulaClassItem)idMap.get(classID);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXFormulaClass#getListByParentClassID(Integer)
	 */
	@Override
	public synchronized List<IJABXFormulaClassItem> getListByParentClassID(int parentClassID) {
		// TODO Auto-generated method stub
		if (listMap.containsKey(parentClassID)) {
			return (List<IJABXFormulaClassItem>)listMap.get(parentClassID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXFormulaClassItem物件
	 * @param item - IJABXFormulaClassItem
	 */
	public synchronized void addItem(IJABXFormulaClassItem item) {
		List<IJABXFormulaClassItem> tmpItemList;
		if (listMap.containsKey(item.getParentClassID())) {
			tmpItemList = listMap.get(item.getParentClassID());
		}else {
			tmpItemList = new ArrayList<IJABXFormulaClassItem>();
		}
		tmpItemList.add(item);
		listMap.put(item.getParentClassID(), tmpItemList);

		if (idMap.containsKey(item.getClassID())) {
			IJABXFormulaClassItem tmpItem;
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
	 * 清除listMap, idMap及itemList中之物件
	 */
	public synchronized void clear() {
		listMap.clear();
		idMap.clear();
		itemList.clear();
	}
}
