package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXExchangeList;
import com.syt.jabx.query.IJABXExchangeListItem;

/**
 * 交易所列表索引之類別
 * @author Jason
 *
 */
final class ZJABXExchangeList implements IJABXExchangeList {

	/**
	 * 以交易所代碼為Key，儲存ZJABXExchangeListItem之Map容器
	 */
	private Map<String, ZJABXExchangeListItem> idMap;

	/**
	 * 存放ZJABXExchangeListItem物件之List容器
	 */
	private List<ZJABXExchangeListItem> itemList;

	/**
	 * Constructor
	 */
	public ZJABXExchangeList() {
		idMap = new HashMap<String, ZJABXExchangeListItem>();
		itemList = new ArrayList<ZJABXExchangeListItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeList#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeList#atIndex(int)
	 */
	@Override
	public synchronized IJABXExchangeListItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < itemList.size()) {
			return itemList.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeList#atID(String)
	 */
	@Override
	public synchronized IJABXExchangeListItem atID(String id) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(id)) {
			return (IJABXExchangeListItem)idMap.get(id);
		}else {
			return null;
		}
	}

	/**
	 * 添加一ZJABXExchangeListItem至itemList中
	 * @param item - ZJABXExchangeListItem
	 */
	public synchronized void addItem(ZJABXExchangeListItem item) {
		if (idMap.containsKey(item.getID())) {
			ZJABXExchangeListItem tmpItem;
			for (int i = 0, size = itemList.size();i < size;i++) {
				tmpItem = itemList.get(i);
				if (tmpItem.getID().equals(item.getID())) {
					itemList.set(i, item);
					break;
				}
			}
		}else {
			itemList.add(item);
		}
		idMap.put(item.getID(), item);
	}

	/**
	 * 清除itemList及idMap中之物件
	 */
	public synchronized void clear() {
		itemList.clear();
		idMap.clear();
	}
}
