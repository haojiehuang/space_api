package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.watch.IJABXDetailTradeOverview;

/**
 * 總覽逐筆成交訊息(目前只support SSE,SZSE)的類別
 * @author Jason
 *
 */
final class ZJABXWatchDetailTradeOverview implements IJABXDetailTradeOverview {

	/**
	 * 儲存ZJABXDetailTrade物件之List容器
	 */
	private List<ZJABXWatchDetailTradeItem> list;

	/**
	 * Constructor
	 */
	public ZJABXWatchDetailTradeOverview() {
		list = new ArrayList<ZJABXWatchDetailTradeItem>();
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
	public synchronized ZJABXWatchDetailTradeItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 添加一ZJABXDetailTrade至itemList中
	 * @param item - ZJABXDetailTrade
	 */
	public synchronized void addItem(ZJABXWatchDetailTradeItem item) {
		list.add(item);
	}

	/**
	 * 清除list中之物件
	 */
	public synchronized void clear() {
		list.clear();
	}
}