package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXBrokerQueueItem;

/**
 * 實作經紀排位項目介面之類別
 * @author jason
 *
 */
final class ZJABXBrokerQueueItem implements IJABXBrokerQueueItem {

	/**
	 * 經紀商代碼
	 */
	private String item;

	/**
	 * 資料別
	 */
	private String type;

	/**
	 * @see com.syt.jabx.watch.IJABXBrokerQueueItem#getItem()
	 */
	@Override
	public String getItem() {
		// TODO Auto-generated method stub
		return item;
	}

	/**
	 * 設定經紀商代碼
	 * @param item - String
	 */
	void setItem(String item) {
		this.item = item;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXBrokerQueueItem#getType()
	 */
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	/**
	 * 設定資料別
	 * @param type - String
	 */
	void setType(String type) {
		this.type = type;
	}
}
