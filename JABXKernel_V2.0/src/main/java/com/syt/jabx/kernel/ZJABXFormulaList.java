package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXFormulaList;
import com.syt.jabx.query.IJABXFormulaListItem;

/**
 * 公式列表索引之類別
 * @author Jason
 *
 */
final class ZJABXFormulaList implements IJABXFormulaList {

	/**
	 * 存放List&lt;IJABXFormulaListItem&gt;物件之Map容器
	 */
	private Map<Integer, List<IJABXFormulaListItem>> listMap;

	/**
	 * 以分類序號為Key，存放IJABXFormulaListItem之Map容器
	 */
	private Map<Integer, IJABXFormulaListItem> idMap;

	/**
	 * 存放所有公式分類項目之List容器
	 */
	private List<IJABXFormulaListItem> itemList;

	/**
	 * Constructor
	 */
	public ZJABXFormulaList() {
		listMap = new HashMap<Integer, List<IJABXFormulaListItem>>();
		idMap = new HashMap<Integer, IJABXFormulaListItem>();
		itemList = new ArrayList<IJABXFormulaListItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXFormulaList#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXFormulaList#atIndex(int)
	 */
	@Override
	public synchronized IJABXFormulaListItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < itemList.size()) {
			return itemList.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXFormulaList#atFormulaID(int)
	 */
	@Override
	public synchronized IJABXFormulaListItem atFormulaID(int formulaID) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(formulaID)) {
			return (IJABXFormulaListItem)idMap.get(formulaID);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXFormulaList#getListByClassID(Integer)
	 */
	@Override
	public synchronized List<IJABXFormulaListItem> getListByClassID(Integer classID) {
		// TODO Auto-generated method stub
		if (listMap.containsKey(classID)) {
			return (List<IJABXFormulaListItem>)listMap.get(classID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXFormulaListItem物件
	 * @param item - IJABXFormulaListItem
	 */
	public synchronized void addItem(IJABXFormulaListItem item) {
		List<IJABXFormulaListItem> tmpItemList;
		if (listMap.containsKey(item.getClassID())) {
			tmpItemList = listMap.get(item.getClassID());
		}else {
			tmpItemList = new ArrayList<IJABXFormulaListItem>();
		}
		tmpItemList.add(item);
		listMap.put(item.getClassID(), tmpItemList);

		if (idMap.containsKey(item.getFormulaID())) {
			IJABXFormulaListItem tmpItem;
			for (int i = 0, size = itemList.size();i < size;i++) {
				tmpItem = itemList.get(i);
				if (tmpItem.getFormulaID() == item.getFormulaID()) {
					itemList.set(i, item);
					break;
				}
			}
		}else {
			itemList.add(item);
		}
		idMap.put(item.getFormulaID(), item);
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
