package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.kmodel.IJABXUserEnvironmentList;
import com.syt.jabx.kmodel.IJABXUserEnvironmentListItem;

/**
 * 用戶系統環境設定列表索引的類別
 * @author Jason
 *
 */
final class ZJABXUserEnvironmentList implements IJABXUserEnvironmentList {

	/**
	 * 用戶系統環境設定群組代碼
	 */
	private String groupID;

	/**
	 * 以用戶系統環境設定群組序號為Key，儲存IJABXUserEnvironmentListItem之Map容器
	 */
	private Map<Integer, IJABXUserEnvironmentListItem> idMap;

	/**
	 * 存放所有用戶系統環境設定列表項目的介面之List容器
	 */
	private List<IJABXUserEnvironmentListItem> list;

	/**
	 * Constructor
	 */
	public ZJABXUserEnvironmentList() {
		idMap = new HashMap<Integer, IJABXUserEnvironmentListItem>();
		list = new ArrayList<IJABXUserEnvironmentListItem>();
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentList#getGroupID()
	 */
	@Override
	public String getGroupID() {
		// TODO Auto-generated method stub
		return groupID;
	}

	/**
	 * 設定用戶系統環境設定群組代碼
	 * @param groupID - String
	 */
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentList#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentList#atIndex(int)
	 */
	@Override
	public synchronized IJABXUserEnvironmentListItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentList#atGroupNo(int)
	 */
	@Override
	public synchronized IJABXUserEnvironmentListItem atGroupNo(int groupNo) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(groupNo)) {
			return (IJABXUserEnvironmentListItem)idMap.get(groupNo);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXUserEnvironmentListItem物件
	 * @param item - IJABXUserEnvironmentListItem
	 */
	public synchronized void addItem(IJABXUserEnvironmentListItem item) {
		if (idMap.containsKey(item.getGroupNo())) {
			IJABXUserEnvironmentListItem tmpItem;
			for (int i = 0, size = list.size();i < size;i++) {
				tmpItem = list.get(i);
				if (tmpItem.getGroupNo() == item.getGroupNo()) {
					list.set(i, item);
					break;
				}
			}
		}else {
			list.add(item);
		}
		idMap.put(item.getGroupNo(), item);
	}

	/**
	 * 清除list及idMap中之物件
	 */
	public synchronized void clear() {
		idMap.clear();
		list.clear();
	}
}
