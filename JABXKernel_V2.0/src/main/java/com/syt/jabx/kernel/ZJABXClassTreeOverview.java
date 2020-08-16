package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.syt.jabx.query.IJABXClassTreeItem;
import com.syt.jabx.query.IJABXClassTreeOverview;

/**
 * 總覧分類樹之類別
 * @author jason
 *
 */
final class ZJABXClassTreeOverview implements IJABXClassTreeOverview {

	/**
	 * 以父節點之classID為key，儲放分類樹子項目List之Map物件
	 */
	private Map<String, List<IJABXClassTreeItem>> classTreeMap;

	/**
	 * Constructor
	 */
	public ZJABXClassTreeOverview() {
		classTreeMap = new HashMap<String, List<IJABXClassTreeItem>>();
	}

	@Override
	public synchronized List<IJABXClassTreeItem> getListByClassID(String classID) {
		// TODO Auto-generated method stub
		List<IJABXClassTreeItem> list = classTreeMap.get(classID);
		if (list != null) {
			return list;
		}
		return null;
	}

	/**
	 * 添加一分類樹子項目
	 * @param item - IJABXClassTreeItem
	 * @param parentClassID - String(父節點之分類代碼)
	 */
	public synchronized void addItem(IJABXClassTreeItem item, String parentClassID) {
		if (item == null || parentClassID == null || parentClassID.equals("")) {
			return;
		}
		List<IJABXClassTreeItem> list = classTreeMap.get(parentClassID);
		if (list == null) {
			list = new ArrayList<IJABXClassTreeItem>();
		}
		list.add(item);
		classTreeMap.put(parentClassID, list);
	}

	/**
	 * 清除classTreeMap中之物件
	 */
	public synchronized void clear() {
		Set<String> keySet = classTreeMap.keySet();
		Iterator<String> it = keySet.iterator();
		String key;
		List<IJABXClassTreeItem> list;
		while (it.hasNext()) {
			key = it.next();
			list = classTreeMap.get(key);
			list.clear();
		}
		classTreeMap.clear();
	}
}
