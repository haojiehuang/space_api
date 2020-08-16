package com.syt.jabx.kernel;

import java.util.ArrayList;

/**
 * 總覧股票最佳買賣委託的類別
 * @author Jason
 *
 */
final class ZJABXBestOrder {

	/**
	 * 記錄IJABXBestOrderItem之List物件
	 */
	private ArrayList<ZJABXBestOrderItem> itemList;

	/**
	 * Constructor
	 */
	public ZJABXBestOrder(int size) {
		itemList = new ArrayList<ZJABXBestOrderItem>();
		ZJABXBestOrderItem item;
		for (int i = 0;i < size;i++) {
			item = new ZJABXBestOrderItem();
			itemList.add(item);
		}
	}

	/**
	 * 取得股票最佳買賣委託的筆數
	 * @return int
	 */
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	/**
	 * 取得第index索引之股票最佳買賣委託物件
	 * @param index - int(索引值)
	 * @return ZJABXBestOrderItem
	 */
	public synchronized ZJABXBestOrderItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < itemList.size()) {
			return itemList.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 設定索引值(index)之物件
	 * @param item - IJABXBestOrderItem
	 * @param index - int
	 */
	public synchronized void setItem(ZJABXBestOrderItem item, int index) {
		if (index < itemList.size()) {
			itemList.set(index, item);
		}
	}

	/**
	 * 將類別中所用到的欄位設定為零或空白
	 */
	public synchronized void setDataToZeroOrSpace() {
		ZJABXBestOrderItem item;
		for (int i = 0, size = itemList.size();i < size;i++) {
			item = (ZJABXBestOrderItem)itemList.get(i);
			item.setDataToZeroOrSpace();
		}
	}

	/**
	 * 清除itemList中的物件
	 */
	public synchronized void clear() {
		itemList.clear();
	}

}
