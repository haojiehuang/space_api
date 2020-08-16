package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.query.IJABXProductClass;

/**
 * 產品功能類別之類別
 * @author Jason
 *
 */
final class ZJABXProductClass implements IJABXProductClass {

	/**
	 * 儲存ZJABXProductClassItem物件之List容器
	 */
	private List<ZJABXProductClassItem> list;

	/**
	 * Constructor
	 */
	public ZJABXProductClass() {
		list = new ArrayList<ZJABXProductClassItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXProductClass#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXProductClass#atIndex(int)
	 */
	@Override
	public synchronized ZJABXProductClassItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 添加一ZJABXProductClassItem至itemList中
	 * @param item - ZJABXProductClassItem
	 */
	public synchronized void addItem(ZJABXProductClassItem item) {
		list.add(item);
	}

	/**
	 * 清除list中之物件
	 */
	public synchronized void clear() {
		list.clear();
	}
}
