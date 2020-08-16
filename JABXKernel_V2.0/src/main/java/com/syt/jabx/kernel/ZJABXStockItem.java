package com.syt.jabx.kernel;

import java.util.List;

import com.syt.jabx.query.IJABXStockItem;

/**
 * 股名檔子項目之類別
 * @author Jason
 *
 */
final class ZJABXStockItem implements IJABXStockItem {

	/**
	 * 證券全代碼
	 */
	private String stkID;

	/**
	 * 股票名稱
	 */
	private String name;

	/**
	 * 股名拼音之List
	 */
	private List<String> listOfPinying;

	/**
	 * @see com.syt.jabx.query.IJABXStockItem#getID()
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
	 * @see com.syt.jabx.query.IJABXStockItem#getStkName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * 設定股票名稱
	 * @param stkName - String
	 */
	public void setName(String stkName) {
		this.name = stkName;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockItem#getExchangeID()
	 */
	@Override
	public String getExchangeID() {
		// TODO Auto-generated method stub
		if (stkID.length() >= 2) {
			return stkID.substring(0, 2);
		}else {
			return "";
		}
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockItem#getListOfPinying()
	 */
	@Override
	public List<String> getListOfPinying() {
		// TODO Auto-generated method stub
		return listOfPinying;
	}

	/**
	 * 設定股名拼音之List
	 * @param listOfPinying - List&lt;String&gt;
	 */
	public void setListOfPinying(List<String> listOfPinying) {
		this.listOfPinying = listOfPinying;
	}
}
