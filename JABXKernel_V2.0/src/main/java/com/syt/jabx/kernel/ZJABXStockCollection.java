package com.syt.jabx.kernel;

import java.util.HashMap;
import java.util.Map;

import com.syt.jabx.query.IJABXStockCollection;
import com.syt.jabx.query.IJABXStockOverview;

/**
 * 股名檔之Collection類別
 * @author Jason
 *
 */
final class ZJABXStockCollection implements IJABXStockCollection {

	/**
	 * 依檔名存放IJABXStockOverview之Map物件
	 */
	private Map<String, IJABXStockOverview> itemMap;

	/**
	 * Constructor
	 */
	public ZJABXStockCollection() {
		itemMap = new HashMap<String, IJABXStockOverview>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockCollection#get(String)
	 */
	@Override
	public synchronized IJABXStockOverview get(String fileName) {
		// TODO Auto-generated method stub
		return itemMap.get(fileName);
	}

	/**
	 * 依fileName將IJABXStockOverview放入itemMap中
	 * @param fileName - String
	 * @param soObj - IJABXStockOverview
	 */
	public synchronized void put(String fileName, IJABXStockOverview soObj) {
		itemMap.put(fileName, soObj);
	}

	/**
	 * 清除itemMap中之物件
	 */
	public synchronized void clear() {
		itemMap.clear();
	}

}
