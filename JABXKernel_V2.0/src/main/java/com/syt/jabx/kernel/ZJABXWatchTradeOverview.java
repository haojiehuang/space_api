package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.watch.IJABXTradeOverview;

/**
 * 總覽股票成交分筆的類別
 * @author Jason
 *
 */
final class ZJABXWatchTradeOverview implements IJABXTradeOverview {

	/**
	 * 儲放seqNo之Map物件，據此判斷新增的item是否重覆
	 */
	private Map<Integer, String> seqNoMap;

	/**
	 * 儲存ZJABXWatchTradeItem物件之List容器
	 */
	private List<ZJABXWatchTradeItem> list;

	/**
	 * Constructor
	 */
	public ZJABXWatchTradeOverview() {
		list = new ArrayList<ZJABXWatchTradeItem>();
		seqNoMap = new HashMap<Integer, String>();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTradeOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTradeOverview#atIndex(int)
	 */
	@Override
	public synchronized ZJABXWatchTradeItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 添加一ZJABXTradeItem至itemList中
	 * @param item - ZJABXTradeItem
	 */
	public synchronized void addItem(ZJABXWatchTradeItem item) {
		String seqNo = seqNoMap.get(item.getSeqNo());
		if (seqNo == null) {
			list.add(item);
			seqNoMap.put(item.getSeqNo(), "");
		}
	}

	/**
	 * 清除list及seqNoMap中之物件
	 */
	public synchronized void clear() {
		list.clear();
		seqNoMap.clear();
	}

	/**
	 * 排序itemList數據
	 */
	public synchronized void sortData() {
		Collections.sort(list);
	}
}
