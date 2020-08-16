package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXStockRelationStockList;
import com.syt.jabx.query.IJABXStockRelationStockListItem;

/**
 * 交易所1關連交易所2股票檔之類別
 * @author Jason
 *
 */
final class ZJABXStockRelationStockList implements IJABXStockRelationStockList {

	/**
	 * 以交易所1證券全代碼為Key，儲存IJABXStockRelationStockListItem之Map容器
	 */
	private Map<String, IJABXStockRelationStockListItem> idMap;

	/**
	 * 存放所有關連數據之List容器
	 */
	private List<IJABXStockRelationStockListItem> list;

	/**
	 * Constructor
	 */
	public ZJABXStockRelationStockList() {
		idMap = new HashMap<String, IJABXStockRelationStockListItem>();
		list = new ArrayList<IJABXStockRelationStockListItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationStockList#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationStockList#atIndex(int)
	 */
	@Override
	public synchronized IJABXStockRelationStockListItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationStockList#atID(String)
	 */
	@Override
	public synchronized IJABXStockRelationStockListItem atID(String stkID1) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(stkID1)) {
			return (IJABXStockRelationStockListItem)idMap.get(stkID1);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXTechnicalIndexNameItem物件
	 * @param item - IJABXTechnicalIndexNameItem
	 */
	public synchronized void addItem(IJABXStockRelationStockListItem item) {
		idMap.put(item.getID(), item);
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
