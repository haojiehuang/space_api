package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.message.IJABXExchangeBulletinItem;
import com.syt.jabx.message.IJABXExchangeBulletinOverview;

/**
 * 交易所公告標題索引的類別
 * @author Jason
 *
 */
final class ZJABXExchangeBulletinOverview implements IJABXExchangeBulletinOverview {

	/**
	 * 以交易所公告序號為Key，儲存IJABXExchangeBulletinItem之Map容器
	 */
	private Map<Integer, IJABXExchangeBulletinItem> idMap;

	/**
	 * 以交易所代碼為Key，儲存List&lt;IJABXExchangeBulletinItem&gt;之Map容器
	 */
	private Map<String, List<IJABXExchangeBulletinItem>> listMap;

	/**
	 * 存放所有交易所公告標題項目的介面之List容器
	 */
	private List<IJABXExchangeBulletinItem> itemList;

	/**
	 * Constructor
	 */
	public ZJABXExchangeBulletinOverview() {
		idMap = new HashMap<Integer, IJABXExchangeBulletinItem>();
		listMap = new HashMap<String, List<IJABXExchangeBulletinItem>>();
		itemList = new ArrayList<IJABXExchangeBulletinItem>();
	}

	synchronized void addItem(IJABXExchangeBulletinItem ebItem) {
		// TODO Auto-generated method stub
		List<IJABXExchangeBulletinItem> tmpList = null;
		if (listMap.containsKey(ebItem.getExchangeID())) {
			tmpList = listMap.get(ebItem.getExchangeID());
		}else {
			tmpList = new ArrayList<IJABXExchangeBulletinItem>();
		}
		tmpList.add(ebItem);
		listMap.put(ebItem.getExchangeID(), tmpList);
		idMap.put(ebItem.getSerialNo(), ebItem);
		itemList.add(ebItem);
	}

	/**
	 * @see com.syt.jabx.message.IJABXExchangeBulletinOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	/**
	 * @see com.syt.jabx.message.IJABXExchangeBulletinOverview#atIndex(int)
	 */
	@Override
	public synchronized IJABXExchangeBulletinItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < itemList.size()) {
			return (IJABXExchangeBulletinItem)itemList.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.message.IJABXExchangeBulletinOverview#atSerialNo(int)
	 */
	@Override
	public synchronized IJABXExchangeBulletinItem atSerialNo(int serialNo) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(serialNo)) {
			return (IJABXExchangeBulletinItem)idMap.get(serialNo);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.message.IJABXExchangeBulletinOverview#getListByExchangeID(String)
	 */
	@Override
	public synchronized List<IJABXExchangeBulletinItem> getListByExchangeID(String exchangeID) {
		// TODO Auto-generated method stub
		if (listMap.containsKey(exchangeID)) {
			return (List<IJABXExchangeBulletinItem>)listMap.get(exchangeID);
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
