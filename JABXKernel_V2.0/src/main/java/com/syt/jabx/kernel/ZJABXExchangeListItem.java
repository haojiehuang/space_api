package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXExchangeListItem;

/**
 * 交易所列表項目之類別
 * @author Jason
 *
 */
final class ZJABXExchangeListItem implements IJABXExchangeListItem {

	/**
	 * 交易所代碼
	 */
	private String id;

	/**
	 * 交易所名稱
	 */
	private String name;

	/**
	 * 交易所屬性
	 */
	private String attribute;

	/**
	 * @see com.syt.jabx.query.IJABXExchangeListItem#getID()
	 */
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return id;
	}

	/**
	 * 設定交易所代碼
	 * @param id - String
	 */
	public void setID(String id) {
		this.id = id;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeListItem#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * 設定交易所名稱
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeListItem#getAttribute()
	 */
	@Override
	public String getAttribute() {
		// TODO Auto-generated method stub
		return attribute;
	}

	/**
	 * 設定交易所屬性
	 * @param attribute - String
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
