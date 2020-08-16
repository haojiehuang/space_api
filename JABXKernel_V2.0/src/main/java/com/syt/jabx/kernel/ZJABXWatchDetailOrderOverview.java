package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.watch.IJABXDetailOrderOverview;

/**
 * 總覽逐筆委託訊息(目前只support SZSE)的類別
 * @author Jason
 *
 */
final class ZJABXWatchDetailOrderOverview implements IJABXDetailOrderOverview {

	/**
	 * 儲存ZJABXDetailOrder物件之List容器
	 */
	private List<ZJABXWatchDetailOrderItem> list;

	/**
	 * Constructor
	 */
	public ZJABXWatchDetailOrderOverview() {
		list = new ArrayList<ZJABXWatchDetailOrderItem>();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXDetailTradeOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXDetailTradeOverview#atIndex(int)
	 */
	@Override
	public synchronized ZJABXWatchDetailOrderItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 添加一ZJABXDetailOrder至itemList中
	 * @param item - ZJABXDetailOrder
	 */
	public synchronized void addItem(ZJABXWatchDetailOrderItem item) {
		list.add(item);
	}

	/**
	 * 清除list中之物件
	 */
	public synchronized void clear() {
		list.clear();
	}
}