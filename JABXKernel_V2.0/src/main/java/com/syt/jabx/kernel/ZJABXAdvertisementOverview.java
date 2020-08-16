package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.message.IJABXAdvertisementItem;
import com.syt.jabx.message.IJABXAdvertisementOverview;

/**
 * 實作廣告訊息索引的介面之類別
 * @author Jason
 *
 */
final class ZJABXAdvertisementOverview implements IJABXAdvertisementOverview {

	/**
	 * 以廣告序號為Key，儲存IJABXAdvertisementItem之Map容器
	 */
	private Map<Integer, IJABXAdvertisementItem> idMap;

	/**
	 * 存放所有廣告訊息項目的介面之List容器
	 */
	private List<IJABXAdvertisementItem> itemList;

	/**
	 * Constructor
	 */
	public ZJABXAdvertisementOverview() {
		idMap = new HashMap<Integer, IJABXAdvertisementItem>();
		itemList = new ArrayList<IJABXAdvertisementItem>();
	}

	public synchronized void addItem(IJABXAdvertisementItem item) {
		// TODO Auto-generated method stub
		idMap.put(item.getSerialNo(), item);
		itemList.add(item);
	}

	/**
	 * @see com.syt.jabx.message.IJABXAdvertisementOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	/**
	 * @see com.syt.jabx.message.IJABXAdvertisementOverview#atIndex(int)
	 */
	@Override
	public synchronized IJABXAdvertisementItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < itemList.size()) {
			return (IJABXAdvertisementItem)itemList.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.message.IJABXAdvertisementOverview#atSerialNo(int)
	 */
	@Override
	public synchronized IJABXAdvertisementItem atSerialNo(int serialNo) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(serialNo)) {
			return (IJABXAdvertisementItem)idMap.get(serialNo);
		}else {
			return null;
		}
	}

	/**
	 * 清除itemList及idMap中的物件
	 */
	public synchronized void clear() {
		idMap.clear();
		itemList.clear();
	}
}
