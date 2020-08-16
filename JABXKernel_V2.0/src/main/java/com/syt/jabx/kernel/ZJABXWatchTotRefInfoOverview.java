package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.watch.IJABXTotRefInfoOverview;

/**
 * 總覽總委買賣量筆及成交筆資訊的類別
 * @author Jason
 *
 */
final class ZJABXWatchTotRefInfoOverview implements IJABXTotRefInfoOverview {

	/**
	 * 儲存ZJABXTotRefInfoItem物件之List容器
	 */
	private List<ZJABXWatchTotRefInfoItem> list;

	/**
	 * Constructor
	 */
	public ZJABXWatchTotRefInfoOverview() {
		list = new ArrayList<ZJABXWatchTotRefInfoItem>();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTotRefInfoOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTotRefInfoOverview#atIndex(int)
	 */
	@Override
	public synchronized ZJABXWatchTotRefInfoItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 添加一ZJABXTotRefInfo至itemList中
	 * @param item - ZJABXTotRefInfo
	 */
	synchronized void addItem(ZJABXWatchTotRefInfoItem item) {
		list.add(item);
	}

	/**
	 * 清除list中之物件
	 */
	synchronized void clear() {
		list.clear();
	}
}