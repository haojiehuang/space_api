package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXTechnicalIndexClass;
import com.syt.jabx.query.IJABXTechnicalIndexClassItem;

/**
 * 查詢技術指標分類之類別
 * @author Jason
 *
 */
final class ZJABXTechnicalIndexClass implements IJABXTechnicalIndexClass {

	/**
	 * 儲存List&lt;ZJABXTechnicalIndexClass&gt;物件之Map容器
	 */
	private Map<Integer, List<IJABXTechnicalIndexClassItem>> listMap;

	/**
	 * 儲放所有關連數據之List容器
	 */
	private List<IJABXTechnicalIndexClassItem> list;

	/**
	 * Constructor
	 */
	public ZJABXTechnicalIndexClass() {
		listMap = new HashMap<Integer, List<IJABXTechnicalIndexClassItem>>();
		list = new ArrayList<IJABXTechnicalIndexClassItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexClass#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexClass#atIndex(int)
	 */
	@Override
	public synchronized IJABXTechnicalIndexClassItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexClass#getListByParentClassID(Integer)
	 */
	@Override
	public synchronized List<IJABXTechnicalIndexClassItem> getListByParentClassID(Integer parentClassID) {
		// TODO Auto-generated method stub
		if (listMap.containsKey(parentClassID)) {
			return (List<IJABXTechnicalIndexClassItem>)listMap.get(parentClassID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXTechnicalIndexClassItem物件
	 * @param item - IJABXTechnicalIndexClassItem
	 */
	public synchronized void addItem(IJABXTechnicalIndexClassItem item) {
		List<IJABXTechnicalIndexClassItem> tmpItemList;
		if (listMap.containsKey(item.getParentClassID())) {
			tmpItemList = listMap.get(item.getParentClassID());
		}else {
			tmpItemList = new ArrayList<IJABXTechnicalIndexClassItem>();
		}
		tmpItemList.add(item);
		listMap.put(item.getParentClassID(), tmpItemList);
		list.add(item);
	}

	/**
	 * 清除list及listMap中之物件
	 */
	public synchronized void clear() {
		listMap.clear();
		list.clear();
	}
}
