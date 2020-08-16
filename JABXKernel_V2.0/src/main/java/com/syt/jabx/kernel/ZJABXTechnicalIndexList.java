package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXTechnicalIndexList;
import com.syt.jabx.query.IJABXTechnicalIndexListItem;

/**
 * 查詢技術指標列表之類別
 * @author Jason
 *
 */
final class ZJABXTechnicalIndexList implements IJABXTechnicalIndexList {

	/**
	 * 儲存List&lt;IJABXTechnicalIndexListItem&gt;物件之Map容器
	 */
	private Map<Integer, List<IJABXTechnicalIndexListItem>> listMap;

	/**
	 * 以公式序號為Key，儲存IJABXTechnicalIndexListItem之Map容器
	 */
	private Map<Integer, IJABXTechnicalIndexListItem> itemMap;

	/**
	 * 儲放所有公式數據之List容器
	 */
	private List<IJABXTechnicalIndexListItem> itemList;

	/**
	 * Constructor
	 */
	public ZJABXTechnicalIndexList() {
		listMap = new HashMap<Integer, List<IJABXTechnicalIndexListItem>>();
		itemMap = new HashMap<Integer, IJABXTechnicalIndexListItem>();
		itemList = new ArrayList<IJABXTechnicalIndexListItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexList#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexList#atIndex(int)
	 */
	@Override
	public synchronized IJABXTechnicalIndexListItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < itemList.size()) {
			return itemList.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexList#atIndex(int)
	 */
	@Override
	public synchronized IJABXTechnicalIndexListItem atFormulaID(int formulaID) {
		// TODO Auto-generated method stub
		if (itemMap.containsKey(formulaID)) {
			return (IJABXTechnicalIndexListItem)itemMap.get(formulaID);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexList#getListByClassID(int)
	 */
	@Override
	public synchronized List<IJABXTechnicalIndexListItem> getListByClassID(int classID) {
		// TODO Auto-generated method stub
		if (listMap.containsKey(classID)) {
			return (List<IJABXTechnicalIndexListItem>)listMap.get(classID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXTechnicalIndexNameItem物件
	 * @param item - IJABXTechnicalIndexNameItem
	 */
	public synchronized void addItem(IJABXTechnicalIndexListItem item) {
		List<IJABXTechnicalIndexListItem> itemList;
		if (listMap.containsKey(item.getClassID())) {
			itemList = listMap.get(item.getClassID());
		}else {
			itemList = new ArrayList<IJABXTechnicalIndexListItem>();
		}
		itemList.add(item);
		listMap.put(item.getClassID(), itemList);
		itemMap.put(item.getTechnicalIndexID(), item);
		itemList.add(item);
	}

	/**
	 * 清除listMap, itemMap及itemList中之物件
	 */
	public synchronized void clear() {
		listMap.clear();
		itemMap.clear();
		itemList.clear();
	}
}
