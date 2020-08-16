package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.smartmsg.IJABXSmartMasterItem;
import com.syt.jabx.smartmsg.IJABXSmartMasterOverview;

/**
 * 主力大單資訊索引的類別
 * @author Jason
 *
 */
final class ZJABXSmartMasterOverview implements IJABXSmartMasterOverview {

	/**
	 * 存放所有主力大單資訊項目之List容器
	 */
	private List<IJABXSmartMasterItem> list;

	/**
	 * Constructor
	 */
	public ZJABXSmartMasterOverview() {
		list = new ArrayList<IJABXSmartMasterItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartMasterOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartMasterOverview#atIndex(int)
	 */
	@Override
	public synchronized IJABXSmartMasterItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXSmartMasterItem物件
	 * @param item - IJABXSmartMasterItem
	 */
	public synchronized void addItem(IJABXSmartMasterItem item) {
		list.add(item);
	}

	/**
	 * 清除list中之物件
	 */
	public synchronized void clear() {
		list.clear();
	}
}