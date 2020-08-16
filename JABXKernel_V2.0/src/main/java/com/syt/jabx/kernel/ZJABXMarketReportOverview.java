package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.message.IJABXMarketReportItem;
import com.syt.jabx.message.IJABXMarketReportOverview;

/**
 * 實作行情報導標題索引的介面之類別
 * @author Jason
 *
 */
final class ZJABXMarketReportOverview implements IJABXMarketReportOverview {

	/**
	 * 以行情報導序號為Key，儲存IJABXMarketReportItem之Map容器
	 */
	private Map<Integer, IJABXMarketReportItem> idMap;

	/**
	 * 以行情報導分類代碼為Key，儲存List&lt;IJABXMarketReportItem&gt;之Map容器
	 */
	private Map<String, List<IJABXMarketReportItem>> listMap;

	/**
	 * 存放所有行情報導標題項目的介面之List容器
	 */
	private List<IJABXMarketReportItem> itemList;

	/**
	 * Constructor
	 */
	public ZJABXMarketReportOverview() {
		idMap = new HashMap<Integer, IJABXMarketReportItem>();
		listMap = new HashMap<String, List<IJABXMarketReportItem>>();
		itemList = new ArrayList<IJABXMarketReportItem>();
	}

	public synchronized void addItem(IJABXMarketReportItem ebItem) {
		// TODO Auto-generated method stub
		List<IJABXMarketReportItem> tmpList = null;
		if (listMap.containsKey(ebItem.getClassID())) {
			tmpList = listMap.get(ebItem.getClassID());
		}else {
			tmpList = new ArrayList<IJABXMarketReportItem>();
		}
		tmpList.add(ebItem);
		listMap.put(ebItem.getClassID(), tmpList);
		idMap.put(ebItem.getSerialNo(), ebItem);
		itemList.add(ebItem);
	}

	/**
	 * @see com.syt.jabx.message.IJABXMarketReportOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	/**
	 * @see com.syt.jabx.message.IJABXMarketReportOverview#atIndex(int)
	 */
	@Override
	public synchronized IJABXMarketReportItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < itemList.size()) {
			return (IJABXMarketReportItem)itemList.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.message.IJABXMarketReportOverview#atSerialNo(int)
	 */
	@Override
	public synchronized IJABXMarketReportItem atSerialNo(int serialNo) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(serialNo)) {
			return (IJABXMarketReportItem)idMap.get(serialNo);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.message.IJABXMarketReportOverview#getListByExchangeID(String)
	 */
	@Override
	public synchronized List<IJABXMarketReportItem> getListByClassID(String classID) {
		// TODO Auto-generated method stub
		if (listMap.containsKey(classID)) {
			return (List<IJABXMarketReportItem>)listMap.get(classID);
		}else {
			return null;
		}
	}

	/**
	 * 清除idMap, listMap及itemList中之物件
	 */
	public synchronized void clear() {
		idMap.clear();
		listMap.clear();
		itemList.clear();
	}
}