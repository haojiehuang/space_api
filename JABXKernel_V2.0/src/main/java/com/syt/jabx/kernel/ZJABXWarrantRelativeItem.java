package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXWarrantRelativeItem;

/**
 * 權證標的項目類別
 * @author jason
 *
 */
final class ZJABXWarrantRelativeItem implements IJABXWarrantRelativeItem {

	/**
	 * 權證標的全代碼
	 */
	private String stockFullID;

	/**
	 * 權重
	 */
	private int weights;

	/**
	 * @see com.syt.jabx.query.IJABXWarrantRelativeItem#getStockFullID()
	 */
	@Override
	public String getStockFullID() {
		// TODO Auto-generated method stub
		return stockFullID;
	}

	/**
	 * 設定權證標的全代碼
	 * @param stockFullID - String
	 */
	public void setStockFullID(String stockFullID) {
		this.stockFullID = stockFullID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXWarrantRelativeItem#getWeights()
	 */
	@Override
	public int getWeights() {
		// TODO Auto-generated method stub
		return weights;
	}

	/**
	 * 設定權證
	 * @param weights - int
	 */
	public void setWeights(int weights) {
		this.weights = weights;
	}

}
