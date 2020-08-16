package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXStockRelationStockListItem;

/**
 * 交易所1關連交易所2股票子項目之類別
 * @author Jason
 *
 */
final class ZJABXStockRelationStockListItem implements IJABXStockRelationStockListItem {

	/**
	 * 交易所1之證券全代碼
	 */
	private String stkID1;

	/**
	 * 關連分類
	 */
	private int relationClassID;

	/**
	 * 交易所2之證券全代碼
	 */
	private String stkID2;

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationStockListItem#getID()
	 */
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return stkID1;
	}

	/**
	 * 設定交易所1之證券全代碼
	 * @param stkID1 - String
	 */
	public void setID(String stkID1) {
		this.stkID1 = stkID1;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationStockListItem#getRelationClassID()
	 */
	@Override
	public int getRelationClassID() {
		// TODO Auto-generated method stub
		return relationClassID;
	}

	/**
	 * 設定關連分類
	 * @param relationClassID - int
	 */
	public void setRelationClassID(int relationClassID) {
		this.relationClassID = relationClassID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationStockListItem#getID2()
	 */
	@Override
	public String getID2() {
		// TODO Auto-generated method stub
		return stkID2;
	}

	/**
	 * 設定交易所2之證券全代碼
	 * @param stkID2 - String
	 */
	public void setID2(String stkID2) {
		this.stkID2 = stkID2;
	}

}
