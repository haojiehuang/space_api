package com.syt.jabx.kernel;

import java.util.List;

import com.syt.jabx.query.IJABXStockRelationWarrantsListItem;

/**
 * 股票關連權證股票的項目類別
 * @author Jason
 *
 */
final class ZJABXStockRelationWarrantsListItem implements IJABXStockRelationWarrantsListItem {

	/**
	 * 證券全代碼
	 */
	private String stkID;

	/**
	 * 交易所2之證券全代碼
	 */
	private List<String> listOfWarrantsID;

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationWarrantsListItem#getID()
	 */
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return stkID;
	}

	/**
	 * 設定證券全代碼
	 * @param stkID - String
	 */
	public void setID(String stkID) {
		this.stkID = stkID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationWarrantsListItem#getListOfWarrantsID()
	 */
	@Override
	public List<String> getListOfWarrantsID() {
		// TODO Auto-generated method stub
		return listOfWarrantsID;
	}

	/**
	 * 設定權證證券全代碼列表
	 * @param list - List&lt;String&gt;
	 */
	public void setListOfWarrantsID(List<String> list) {
		this.listOfWarrantsID = list;
	}
}
