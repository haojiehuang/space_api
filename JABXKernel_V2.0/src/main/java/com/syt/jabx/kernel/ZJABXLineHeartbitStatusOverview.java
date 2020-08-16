package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

/**
 * 總覧訂閱連線心跳狀態之類別
 * @author jason
 *
 */
final class ZJABXLineHeartbitStatusOverview {

	/**
	 * 存放ZJABXLineHeartbitStatus之List物件
	 */
	private List<ZJABXLineHeartbitStatus> lineList;

	/**
	 * Constructor
	 */
	public ZJABXLineHeartbitStatusOverview() {
		lineList = new ArrayList<ZJABXLineHeartbitStatus>();
	}

	/**
	 * 取得訂閱指標連線心跳之狀態項目之筆數
	 * @return int
	 */
	public int getCount() {
		return lineList.size();
	}

	/**
	 * 取得索引為index之ZJABXLineHeartbitStatus物件
	 * @param index - int
	 * @return ZJABXLineHeartbitStatus
	 */
	public ZJABXLineHeartbitStatus atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < lineList.size()) {
			return lineList.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 添加一訂閱指標連線心跳之狀態項目
	 * @param item - ZJABXLineHeartbitStatus
	 */
	public void addItem(ZJABXLineHeartbitStatus item) {
		if (item == null) {
			return;
		}
		lineList.add(item);
	}
}