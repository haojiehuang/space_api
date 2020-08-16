package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.query.IJABXStockDivide;
import com.syt.jabx.query.IJABXStockDivideItem;

/**
 * 股票除權息索引的類別
 * @author Jason
 *
 */
final class ZJABXStockDivide implements IJABXStockDivide {

	/**
	 * 股票除權息索引的證券全代碼
	 */
	private String id;

	/**
	 * 存放所有股票除權息項目之List容器
	 */
	private List<IJABXStockDivideItem> list;

	/**
	 * Constructor
	 */
	public ZJABXStockDivide() {
		list = new ArrayList<IJABXStockDivideItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockDivide#getID()
	 */
	@Override
	public String getID() {
		return id;
	}

	/**
	 * 設定股票除權息索引的證券全代碼
	 * @param id - String
	 */
	public void setID(String id) {
		this.id = id;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockDivide#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockDivide#atIndex(int)
	 */
	@Override
	public synchronized IJABXStockDivideItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXStockDivideItem物件
	 * @param item - IJABXStockDivideItem
	 */
	public synchronized void addItem(IJABXStockDivideItem item) {
		list.add(item);
	}

	/**
	 * 清除list中的物件
	 */
	public synchronized void clear() {
		list.clear();
	}
}
