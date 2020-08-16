package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.watch.IJABXStatisticOverview;

/**
 * 總覽總委買賣量筆及成交筆資訊的類別
 * @author Jason
 *
 */
final class ZJABXWatchStatisticOverview implements IJABXStatisticOverview {

	/**
	 * 儲存ZJABXTotRefInfoItem物件之List容器
	 */
	private List<ZJABXWatchStatisticItem> list;

	/**
	 * Constructor
	 */
	public ZJABXWatchStatisticOverview() {
		list = new ArrayList<ZJABXWatchStatisticItem>();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStatisticOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStatisticOverview#atIndex(int)
	 */
	@Override
	public synchronized ZJABXWatchStatisticItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 添加一ZJABXStatisticItem至itemList中
	 * @param item - ZJABXStatisticItem
	 */
	public synchronized void addItem(ZJABXWatchStatisticItem item) {
		list.add(item);
	}

	/**
	 * 清除list中之物件
	 */
	public synchronized void clear() {
		list.clear();
	}
}