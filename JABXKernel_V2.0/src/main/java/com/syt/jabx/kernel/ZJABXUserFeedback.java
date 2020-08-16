package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.kmodel.IJABXUserFeedback;
import com.syt.jabx.kmodel.IJABXUserFeedbackItem;

/**
 * 用戶意見反饋標題索引的類別
 * @author Jason
 *
 */
final class ZJABXUserFeedback implements IJABXUserFeedback {

	/**
	 * 以意見反饋序號為Key，儲存IJABXUserFeedbackItem之Map容器
	 */
	private Map<Integer, IJABXUserFeedbackItem> idMap;

	/**
	 * 存放所有用戶意見反饋標題項目的介面之List容器
	 */
	private List<IJABXUserFeedbackItem> list;

	/**
	 * Constructor
	 */
	public ZJABXUserFeedback() {
		idMap = new HashMap<Integer, IJABXUserFeedbackItem>();
		list = new ArrayList<IJABXUserFeedbackItem>();
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserFeedback#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserFeedback#atIndex(int)
	 */
	@Override
	public synchronized IJABXUserFeedbackItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserFeedback#atFeedbackNo(int)
	 */
	@Override
	public synchronized IJABXUserFeedbackItem atFeedbackNo(int groupNo) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(groupNo)) {
			return (IJABXUserFeedbackItem)idMap.get(groupNo);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXUserFeedbackItem物件
	 * @param item - IJABXUserFeedbackItem
	 */
	public synchronized void addItem(IJABXUserFeedbackItem item) {
		if (idMap.containsKey(item.getFeedbackNo())) {
			IJABXUserFeedbackItem tmpItem;
			for (int i = 0, size = list.size();i < size;i++) {
				tmpItem = list.get(i);
				if (tmpItem.getFeedbackNo() == item.getFeedbackNo()) {
					list.set(i, item);
					break;
				}
			}
		}else {
			list.add(item);
		}
		idMap.put(item.getFeedbackNo(), item);
	}

	/**
	 * 清除list及idMap中之物件
	 */
	public synchronized void clear() {
		idMap.clear();
		list.clear();
	}
}
