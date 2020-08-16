package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.syt.jabx.query.IJABXWarnTreeItem;
import com.syt.jabx.query.IJABXWarnTreeOverview;

/**
 * 總覧警示定義樹之類別
 * @author jason
 *
 */
final class ZJABXWarnTreeOverview implements IJABXWarnTreeOverview {

	/**
	 * 以父節點之groupID為key，儲放警示樹子項目List之Map物件
	 */
	private Map<String, List<IJABXWarnTreeItem>> warnTreeMap;

	/**
	 * Constructor
	 */
	public ZJABXWarnTreeOverview() {
		warnTreeMap = new HashMap<String, List<IJABXWarnTreeItem>>();
	}

	@Override
	public synchronized List<IJABXWarnTreeItem> getListByGroupID(String groupID) {
		// TODO Auto-generated method stub
		List<IJABXWarnTreeItem> list = warnTreeMap.get(groupID);
		if (list != null) {
			return list;
		}
		return null;
	}

	/**
	 * 添加一警示樹子項目
	 * @param item - IJABXWarnTreeItem
	 * @param parentGroupID - String(父節點之群組代碼)
	 */
	public synchronized void addItem(IJABXWarnTreeItem item, String parentGroupID) {
		if (item == null || parentGroupID == null || parentGroupID.equals("")) {
			return;
		}
		List<IJABXWarnTreeItem> list = warnTreeMap.get(parentGroupID);
		if (list == null) {
			list = new ArrayList<IJABXWarnTreeItem>();
		}
		list.add(item);
		warnTreeMap.put(parentGroupID, list);
	}

	/**
	 * 清除warnTreeMap中之物件
	 */
	public synchronized void clear() {
		Set<String> keySet = warnTreeMap.keySet();
		Iterator<String> it = keySet.iterator();
		String key;
		List<IJABXWarnTreeItem> list;
		while (it.hasNext()) {
			key = it.next();
			list = warnTreeMap.get(key);
			list.clear();
		}
		warnTreeMap.clear();
	}
}