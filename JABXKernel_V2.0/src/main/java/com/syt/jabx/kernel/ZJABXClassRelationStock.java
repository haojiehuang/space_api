package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.query.IJABXClassRelationStock;
import com.syt.jabx.query.IJABXClassRelationStockItem;

/**
 * 分類關連股票索引之類別
 * @author Jason
 *
 */
final class ZJABXClassRelationStock implements IJABXClassRelationStock {

	/**
	 * 分類群組代碼
	 */
	private int classID;

	/**
	 * 證券全代碼列表
	 */
	private List<IJABXClassRelationStockItem> list;

	/**
	 * Constructor
	 */
	public ZJABXClassRelationStock() {
		list = new ArrayList<IJABXClassRelationStockItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassRelationStock#getClassID()
	 */
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return classID;
	}

	/**
	 * 設定分類群組代碼
	 * @param classID - int
	 */
	public void setClassID(int classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassRelationStock#getCount()
	 */
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassRelationStock#atIndex(int)
	 */
	@Override
	public synchronized IJABXClassRelationStockItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return (IJABXClassRelationStockItem)list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 設定證券全代碼列表
	 * @param listOfID - List&lt;String&gt;
	 */
	public synchronized void addItem(IJABXClassRelationStockItem item) {
		list.add(item);
	}

	/**
	 * 清除list中之物件
	 */
	public synchronized void clear() {
		list.clear();
	}
}
