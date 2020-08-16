package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.message.IJABXProductBulletinItem;
import com.syt.jabx.message.IJABXProductBulletinOverview;

/**
 * 實作產品公告標題索引的介面之類別
 * @author Jason
 *
 */
final class ZJABXProductBulletinOverview implements IJABXProductBulletinOverview {

	/**
	 * 以產品公告序號為Key，儲存IJABXProductBulletinItem之Map容器
	 */
	private Map<Integer, IJABXProductBulletinItem> idMap;

	/**
	 * 存放所有產品公告標題項目的介面之List容器
	 */
	private List<IJABXProductBulletinItem> list;

	/**
	 * Constructor
	 */
	public ZJABXProductBulletinOverview() {
		idMap = new HashMap<Integer, IJABXProductBulletinItem>();
		list = new ArrayList<IJABXProductBulletinItem>();
	}

	synchronized void addItem(IJABXProductBulletinItem newsItem) {
		// TODO Auto-generated method stub
		idMap.put(newsItem.getSerialNo(), newsItem);
		list.add(newsItem);
	}

	/**
	 * @see com.syt.jabx.message.IJABXProductBulletinOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.message.IJABXProductBulletinOverview#atIndex(int)
	 */
	@Override
	public synchronized IJABXProductBulletinItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return (IJABXProductBulletinItem)list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.message.IJABXProductBulletinOverview#atSerialNo(int)
	 */
	@Override
	public synchronized IJABXProductBulletinItem atSerialNo(int serialNo) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(serialNo)) {
			return (IJABXProductBulletinItem)idMap.get(serialNo);
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
