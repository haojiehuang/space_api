package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.smartmsg.IJABXSmartShortItem;
import com.syt.jabx.smartmsg.IJABXSmartShortOverview;

/**
 * 短線精靈資訊索引的類別
 * @author Jason
 *
 */
final class ZJABXSmartShortOverview implements IJABXSmartShortOverview {

	/**
	 * 以短線精靈分類代碼為Key，儲存IJABXSmartShortItem之Map容器
	 */
	private Map<String, List<IJABXSmartShortItem>> idMap;

	/**
	 * 存放所有短線精靈資訊項目之List容器
	 */
	private List<IJABXSmartShortItem> list;

	/**
	 * Constructor
	 */
	public ZJABXSmartShortOverview() {
		idMap = new HashMap<String, List<IJABXSmartShortItem>>();
		list = new ArrayList<IJABXSmartShortItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartShortOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartShortOverview#atIndex(int)
	 */
	@Override
	public synchronized IJABXSmartShortItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartShortOverview#getListByClassID(String)
	 */
	@Override
	public synchronized List<IJABXSmartShortItem> getListByClassID(String classID) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(classID)) {
			return (List<IJABXSmartShortItem>)idMap.get(classID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXSmartShortItem物件
	 * @param item - IJABXSmartShortItem
	 */
	public synchronized void addItem(IJABXSmartShortItem item) {
		List<IJABXSmartShortItem> tmpList = null;
		if (idMap.containsKey(item.getClassID())) {
			tmpList = idMap.get(item.getClassID());
		}else {
			tmpList = new ArrayList<IJABXSmartShortItem>();
		}
		tmpList.add(item);
		idMap.put(item.getClassID(), tmpList);
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
