package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.kmodel.IJABXUserEnvironmentGroup;
import com.syt.jabx.kmodel.IJABXUserEnvironmentGroupItem;

/**
 * 用戶系統環境設定群組索引的類別
 * @author Jason
 *
 */
final class ZJABXUserEnvironmentGroup implements IJABXUserEnvironmentGroup {
	
	/**
	 * 用戶系統環境設定群組代碼
	 */
	private String groupID;

	/**
	 * 以用戶系統環境設定群組序號為Key，儲存IJABXUserEnvironmentGroupItem之Map容器
	 */
	private Map<Integer, IJABXUserEnvironmentGroupItem> idMap;

	/**
	 * 存放所有用戶系統環境設定列表項目的介面之List容器
	 */
	private List<IJABXUserEnvironmentGroupItem> list;

	/**
	 * Constructor
	 */
	public ZJABXUserEnvironmentGroup() {
		idMap = new HashMap<Integer, IJABXUserEnvironmentGroupItem>();
		list = new ArrayList<IJABXUserEnvironmentGroupItem>();
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentGroup#getGroupID()
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
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentGroup#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentGroup#atIndex(int)
	 */
	@Override
	public synchronized IJABXUserEnvironmentGroupItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentGroup#atGroupNo(int)
	 */
	@Override
	public synchronized IJABXUserEnvironmentGroupItem atGroupNo(int groupNo) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(groupNo)) {
			return (IJABXUserEnvironmentGroupItem)idMap.get(groupNo);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXUserEnvironmentGroupItem物件
	 * @param item - IJABXUserEnvironmentGroupItem
	 */
	public synchronized void addItem(IJABXUserEnvironmentGroupItem item) {
		if (idMap.containsKey(item.getGroupNo())) {
			IJABXUserEnvironmentGroupItem tmpItem;
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
