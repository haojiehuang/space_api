package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.message.IJABXNewsItem;
import com.syt.jabx.message.IJABXNewsOverview;

/**
 * 實作新聞標題索引的介面之類別
 * @author Jason
 *
 */
final class ZJABXNewsOverview implements IJABXNewsOverview {

	/**
	 * 以新聞序號為Key，儲存IJABXNewsItem之Map容器
	 */
	private Map<Integer, IJABXNewsItem> idMap;

	/**
	 * 存放所有新聞標題項目的介面之List容器
	 */
	private List<IJABXNewsItem> list;

	/**
	 * Constructor
	 */
	public ZJABXNewsOverview() {
		idMap = new HashMap<Integer, IJABXNewsItem>();
		list = new ArrayList<IJABXNewsItem>();
	}

	synchronized void addItem(IJABXNewsItem newsItem) {
		// TODO Auto-generated method stub
		idMap.put(newsItem.getSerialNo(), newsItem);
		list.add(newsItem);
	}

	/**
	 * @see com.syt.jabx.message.IJABXNewsOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.message.IJABXNewsOverview#atIndex(int)
	 */
	@Override
	public synchronized IJABXNewsItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return (IJABXNewsItem)list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.message.IJABXNewsOverview#atSerialNo(int)
	 */
	@Override
	public synchronized IJABXNewsItem atSerialNo(int serialNo) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(serialNo)) {
			return (IJABXNewsItem)idMap.get(serialNo);
		}else {
			return null;
		}
	}

	/**
	 * 清除list及idMap中之物件
	 */
	public synchronized void clear() {
		idMap.clear();
		list.clear();
	}
}
