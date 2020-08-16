package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.query.IJABXFutureOptionItem;
import com.syt.jabx.query.IJABXFutureOptionOverview;

/**
 * 實作總覧期權股名項目介面之類別
 * @author Jason
 *
 */
final class ZJABXFutureOptionOverview implements IJABXFutureOptionOverview {

	/**
	 * 存放所有期權股名檔項目的Map物件
	 */
	private Map<String, ZJABXFutureOptionItem> itemMap;

	/**
	 * 存放所有股票全代碼之List物件
	 */
	private List<String> stkIDList;
	
	/**
	 * itemMap所使用之Lock
	 */
	private byte[] itemMapLock = new byte[0];

	/**
	 * stkIDList所使用之Lock
	 */
	private byte[] stkIDListLock = new byte[0];

	/**
	 * Constructor
	 */
	public ZJABXFutureOptionOverview() {
		itemMap = new HashMap<String, ZJABXFutureOptionItem>();
		stkIDList = new ArrayList<String>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXFutureOptionOverview#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		synchronized(stkIDListLock) {
			return stkIDList.size();
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXFutureOptionOverview#atIndex(int)
	 */
	@Override
	public IJABXFutureOptionItem atIndex(int index) {
		// TODO Auto-generated method stub
		synchronized(stkIDListLock) {
			if (index < stkIDList.size()) {
				return atStkID(stkIDList.get(index));
			}else {
				return null;
			}
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXFutureOptionOverview#atStkID(String)
	 */
	@Override
	public IJABXFutureOptionItem atStkID(String stkID) {
		synchronized(itemMapLock) {
			return itemMap.get(stkID);
		}
	}

	/**
	 * 添加一期權股名項目
	 * @param item - ZJABXFutureOptionItem
	 */
	public void addItem(ZJABXFutureOptionItem item) {
		String stkID = item.getStkID();
		synchronized(itemMapLock) {
			itemMap.put(stkID, item);
		}
		synchronized(stkIDListLock) {
			stkIDList.add(stkID);
		}
	}

	/**
	 * 清除itemMap, stkIDList中之物件
	 */
	public void clear() {
		synchronized(itemMapLock) {
			itemMap.clear();
		}
		synchronized(stkIDListLock) {
			stkIDList.clear();
		}
	}
}