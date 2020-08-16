package com.syt.jabx.kernel;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 總覧交易所基本資料之類別
 * @author Jason
 *
 */
final class ZJABXExchangeInfoOverview {

	/**
	 * 以交易所代碼為Key，儲存ZJABXExchangeInfo之Map容器
	 */
	private Map<String, ZJABXExchangeInfo> infoMap;

	/**
	 * Constructor
	 */
	public ZJABXExchangeInfoOverview() {
		infoMap = new TreeMap<String, ZJABXExchangeInfo>();
	}

	/**
	 * 依交易所代碼(exchange)取得交易所物件
	 * @param exchange - String
	 * @return ZJABXExchangeInfo
	 */
	public synchronized ZJABXExchangeInfo getExchangeInfo(String exchange) {
		// TODO Auto-generated method stub
		if (infoMap.containsKey(exchange)) {
			return infoMap.get(exchange);
		}else {
			return null;
		}
	}

	/**
	 * 添加一ZJABXExchangeInfo至infoMap中
	 * @param item - ZJABXExchangeInfo
	 */
	public synchronized void addItem(ZJABXExchangeInfo item) {
		infoMap.put(item.getExchange(), item);
	}

	/**
	 * 清除infoMap中之物件
	 */
	public synchronized void clear() {
		infoMap.clear();
	}

	/**
	 * 取得交易所之Set
	 * @return Set&lt;String&gt;
	 */
	public Set<String> keySet() {
		if (infoMap == null) {
			return null;
		}
		return infoMap.keySet();
	}
}