package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.kmodel.IJABXGroupAccountItem;
import com.syt.jabx.kmodel.IJABXGroupAccountOverview;

/**
 * 群組交易帳號索引類別
 * @author Jason
 *
 */
final class ZJABXGroupAccountOverview implements IJABXGroupAccountOverview {

	/**
	 * 存放群組交易帳號項目介面之List容器
	 */
	private List<IJABXGroupAccountItem> itemList;

	/**
	 * itemList所使用的Lock
	 */
	private byte[] itemListLock = new byte[0];
	
	/**
	 * Constructor
	 */
	public ZJABXGroupAccountOverview() {
		itemList = new ArrayList<IJABXGroupAccountItem>();
	}

	/**
	 * @see com.syt.jabx.kmodel.IJABXGroupAccountOverview#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		synchronized(itemListLock) {
			return itemList.size();
		}
	}

	/**
	 * @see com.syt.jabx.kmodel.IJABXGroupAccountOverview#atIndex(int)
	 */
	@Override
	public IJABXGroupAccountItem atIndex(int index) {
		// TODO Auto-generated method stub
		synchronized(itemListLock) {
			if (index < itemList.size()) {
				return itemList.get(index);
			}else {
				return null;
			}
		}
	}

	/**
	 * 添加ZJABXGroupAccountItem至itemList
	 * @param item - ZJABXGroupAccountItem
	 */
	public void addItem(ZJABXGroupAccountItem item) {
		synchronized(itemListLock) {
			ZJABXGroupAccountItem tmpObj;
			boolean isInList = false;
			for (int i = 0, size = itemList.size();i < size;i++) {
				tmpObj = (ZJABXGroupAccountItem)itemList.get(i);
				if (tmpObj.getAccountData().equals(item.getAccountData())) {
					isInList = true;
					break;
				}
			}
			if (!isInList) {
				itemList.add(item);
			}
		}
	}

	/**
	 * 清除itemList中之物件
	 */
	public void clear() {
		synchronized(itemListLock) {
			itemList.clear();
		}
	}
}
