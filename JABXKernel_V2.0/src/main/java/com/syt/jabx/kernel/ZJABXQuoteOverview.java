package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.syt.jabx.watch.IJABXQuoteItem;
import com.syt.jabx.watch.IJABXQuoteOverview;

/**
 * 總覽所有即時報價資訊的類別
 * @author Jason
 *
 */
final class ZJABXQuoteOverview implements IJABXQuoteOverview {

	/**
	 * 行情資訊工具物件
	 */
	private JABXQuoteTool quoteTool;

	/**
	 * 記錄IJABXQuoteItem之List物件
	 */
	private ArrayList<IJABXQuoteItem> itemList = new ArrayList<IJABXQuoteItem>();

	/**
	 * 記錄IJABXQuoteItem之Map物件(Key為股票ID)
	 */
	private Map<String, IJABXQuoteItem> idMap = new HashMap<String, IJABXQuoteItem>();

	/**
	 * Constructer
	 * @param quoteTool - JABXQuoteTool
	 */
	public ZJABXQuoteOverview(JABXQuoteTool quoteTool) {
		this.quoteTool = quoteTool;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXQuoteOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXQuoteOverview#atIndex(int)
	 */
	@Override
	public synchronized IJABXQuoteItem atIndex(int index) {
		// TODO Auto-generated method stub		
		if (index < itemList.size()) {
			return itemList.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.watch.IJABXQuoteOverview#atID(String)
	 */
	@Override
	public synchronized IJABXQuoteItem atID(String strID) {
		// TODO Auto-generated method stub
		return idMap.get(strID);
	}

	/**
	 * 加入一個IJABXStkRegQuoItem物件
	 * @param item - IJABXStkRegQuoItem
	 */
	public synchronized void addItem(IJABXQuoteItem item) {
		if (idMap.containsKey(item.getStkID())) {
			IJABXQuoteItem tmpItem;
			for (int i = 0, size = itemList.size();i < size;i++) {
				tmpItem = itemList.get(i);
				if (tmpItem.getStkID().equals(item.getStkID())) {
					itemList.set(i, item);
					break;
				}
			}
		}else {
			itemList.add(item);
		}
		idMap.put(item.getStkID(), item);
	}

	/**
	 * 清除itemList及idMap中之物件
	 */
	public synchronized void clear() {
		itemList.clear();
		idMap.clear();
	}

	/**
	 * 比對訂閱數據
	 * @param list - List&lt;JABXQuoteCondition&gt;
	 */
	public synchronized void compareData(Map<Integer, JSONArray> map) {

		if (map == null || map.size() == 0) {
			return;
		}
		// 1001-Begin: 組成map中訂閱之股票代碼集合的Set物件
		Set<String> idSet = new HashSet<String>();
		JSONArray list;
		JSONObject jobj;
		Set<Integer> keySet = map.keySet();
		Iterator<Integer> it = keySet.iterator();
		Integer key;
		while (it.hasNext()) {
			key = it.next();
			list = map.get(key);
			for (int i = 0, length = list.length();i < length;i++) {
				jobj = list.getJSONObject(i);
				idSet.add(jobj.getString(JSConst.CU_STKID));
			}
		}
		// 1001-End.
		
		boolean isInList;
		IJABXQuoteItem item;
		Iterator<String> idIt;
		String idKey;
		for (int i = 0;i < itemList.size();i++) {
			isInList = false;
			item = itemList.get(i);

			idIt = idSet.iterator();
			while (idIt.hasNext()) {
				idKey = idIt.next();
				if (item.getStkID().equals(idKey)) {
					isInList = true;
					break;
				}
			}
			if (!isInList) {// 若不在已訂閱數據中，則自itemList及idMap中移除
				itemList.remove(i);
				i--;
				idMap.remove(item.getStkID());
				quoteTool.clearReservedAllData(item.getStkID());
			}
		}
	}

}
