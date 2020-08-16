package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.syt.jabx.query.IJABXQuoteData;
import com.syt.jabx.query.IJABXQuoteDataItem;

/**
 * 股票報價數據下載的索引類別
 * @author Jason
 *
 */
final class ZJABXQuoteData implements IJABXQuoteData {

	/**
	 * 記錄IJABXQuoteDataItem之List物件
	 */
	private ArrayList<IJABXQuoteDataItem> list = new ArrayList<IJABXQuoteDataItem>();

	/**
	 * 記錄IJABXQuoteDataItem之Map物件(Key為證券全代碼)
	 */
	private Map<String, IJABXQuoteDataItem> idMap = new HashMap<String, IJABXQuoteDataItem>();

	/**
	 * @see com.syt.jabx.query.IJABXQuoteData#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXQuoteData#atIndex(int)
	 */
	@Override
	public synchronized IJABXQuoteDataItem atIndex(int index) {
		// TODO Auto-generated method stub		
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXQuoteData#atID(String)
	 */
	@Override
	public synchronized IJABXQuoteDataItem atID(String strID) {
		// TODO Auto-generated method stub
		return idMap.get(strID);
	}

	/**
	 * 加入一個IJABXStkRegQuoItem物件
	 * @param item - IJABXStkRegQuoItem
	 */
	public synchronized void addItem(IJABXQuoteDataItem item) {
		if (idMap.containsKey(item.getID())) {
			IJABXQuoteDataItem tmpItem;
			for (int i = 0, size = list.size();i < size;i++) {
				tmpItem = list.get(i);
				if (tmpItem.getID().equals(item.getID())) {
					list.set(i, item);
					break;
				}
			}
		}else {
			list.add(item);
		}
		idMap.put(item.getID(), item);
	}

	/**
	 * 清除list及idMap中之物件
	 */
	public synchronized void clear() {
		list.clear();
		idMap.clear();
	}
}