package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXClassRelationStockList;
import com.syt.jabx.query.IJABXClassRelationStockListItem;

/**
 * 分類關連股票列表索引之類別
 * @author Jason
 *
 */
final class ZJABXClassRelationStockList implements IJABXClassRelationStockList {

	/**
	 * 以產業或自定(板塊)分類群組代碼為Key，存放IJABXClassRelationStockListItem之Map容器
	 */
	private Map<Integer, IJABXClassRelationStockListItem> idMap;

	/**
	 * 存放所有分類關連股票列表項目之List容器
	 */
	private List<IJABXClassRelationStockListItem> itemList;

	/**
	 * Constructor
	 */
	public ZJABXClassRelationStockList() {
		idMap = new HashMap<Integer, IJABXClassRelationStockListItem>();
		itemList = new ArrayList<IJABXClassRelationStockListItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassRelationStockList#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassRelationStockList#atIndex(int)
	 */
	@Override
	public synchronized IJABXClassRelationStockListItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < itemList.size()) {
			return itemList.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassRelationStockList#atClassID(int)
	 */
	@Override
	public synchronized IJABXClassRelationStockListItem atClassID(int classID) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(classID)) {
			return (ZJABXClassRelationStockListItem)idMap.get(classID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一ZJABXClassRelationStockListItem物件
	 * @param item - ZJABXClassRelationStockListItem
	 */
	public synchronized void addItem(ZJABXClassRelationStockListItem item) {
		if (idMap.containsKey(item.getClassID())) {
			IJABXClassRelationStockListItem tmpItem;
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
	 * 清除idMap及itemList中之物件
	 */
	public synchronized void clear() {
		idMap.clear();
		itemList.clear();
	}
}
