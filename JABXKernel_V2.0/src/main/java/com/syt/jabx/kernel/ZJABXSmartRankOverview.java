package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.smartmsg.IJABXSmartRankItem;
import com.syt.jabx.smartmsg.IJABXSmartRankOverview;

/**
 * 即時排名資訊索引的類別
 * @author Jason
 *
 */
final class ZJABXSmartRankOverview implements IJABXSmartRankOverview {

	/**
	 * 以即時排名分類代碼為Key，儲存IJABXSmartRankItem之Map容器
	 */
	private Map<String, List<IJABXSmartRankItem>> idMap;

	/**
	 * 存放所有即時排名資訊項目之List容器
	 */
	private List<IJABXSmartRankItem> list;

	/**
	 * Constructor
	 */
	public ZJABXSmartRankOverview() {
		idMap = new HashMap<String, List<IJABXSmartRankItem>>();
		list = new ArrayList<IJABXSmartRankItem>();
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
	public synchronized IJABXSmartRankItem atIndex(int index) {
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
	public synchronized List<IJABXSmartRankItem> getListByClassID(String classID) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(classID)) {
			return (List<IJABXSmartRankItem>)idMap.get(classID);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXSmartShortItem物件
	 * @param item - IJABXSmartShortItem
	 */
	public synchronized void addItem(IJABXSmartRankItem item) {
		List<IJABXSmartRankItem> tmpList = null;
		if (idMap.containsKey(item.getClassID())) {
			tmpList = idMap.get(item.getClassID());
		}else {
			tmpList = new ArrayList<IJABXSmartRankItem>();
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