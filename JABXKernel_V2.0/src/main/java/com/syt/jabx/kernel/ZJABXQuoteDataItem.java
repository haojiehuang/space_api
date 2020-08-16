package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXQuoteDataItem;

/**
 * 股票報價數據下載的項目類別
 * @author Jason
 *
 */
final class ZJABXQuoteDataItem implements IJABXQuoteDataItem {

	/**
	 * 證券全代碼
	 */
	private String id;

	/**
	 * 股票報價數據
	 */
	private String data;

	/**
	 * @see com.syt.jabx.query.IJABXQuoteDataItem#getID()
	 */
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return id;
	}

	/**
	 * 設定證券全代碼
	 * @param id - String
	 */
	public void setID(String id) {
		this.id = id;
	}

	/**
	 * @see com.syt.jabx.query.IJABXQuoteDataItem#getData()
	 */
	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return data;
	}

	/**
	 * 設定股票報價數據
	 * @param data - String
	 */
	public void setData(String data) {
		this.data = data;
	}
}
