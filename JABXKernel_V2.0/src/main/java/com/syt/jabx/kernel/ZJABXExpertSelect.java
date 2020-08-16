package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.query.IJABXExpertSelect;
import com.syt.jabx.query.IJABXExpertSelectItem;

/**
 * 專家選股索引的類別
 * @author Jason
 *
 */
final class ZJABXExpertSelect implements IJABXExpertSelect {

	/**
	 * 存放所有專家選股項目之List容器
	 */
	private List<IJABXExpertSelectItem> itemList;

	/**
	 * Constructor
	 */
	public ZJABXExpertSelect() {
		itemList = new ArrayList<IJABXExpertSelectItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXExpertSelect#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXExpertSelect#atIndex(int)
	 */
	@Override
	public synchronized IJABXExpertSelectItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < itemList.size()) {
			return itemList.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXFormulaClassItem物件
	 * @param item - IJABXFormulaClassItem
	 */
	public synchronized void addItem(ZJABXExpertSelectItem item) {
		itemList.add(item);
	}

	/**
	 * 清除itemList中之物件
	 */
	public synchronized void clear() {
		itemList.clear();
	}
}
