package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.query.IJABXTickDiff;
import com.syt.jabx.query.IJABXTickDiffItem;

/**
 * 檔差項目索引之類別
 * @author Jason
 *
 */
final class ZJABXTickDiff implements IJABXTickDiff {

	/**
	 * 存放所有檔差項目之List容器
	 */
	private List<IJABXTickDiffItem> list;

	/**
	 * Constructor
	 */
	public ZJABXTickDiff() {
		list = new ArrayList<IJABXTickDiffItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXTickDiff#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXTickDiff#atIndex(int)
	 */
	@Override
	public synchronized IJABXTickDiffItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXStkDiffItem物件
	 * @param item - IJABXStkDiffItem
	 */
	public synchronized void addItem(IJABXTickDiffItem item) {
		list.add(item);
	}

	/**
	 * 清除list中之物件
	 */
	public synchronized void clear() {
		list.clear();
	}
}