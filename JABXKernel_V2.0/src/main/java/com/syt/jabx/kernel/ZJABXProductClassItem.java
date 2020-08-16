package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXProductClassItem;

/**
 * 產品功能類別子項目之類別
 * @author Jason
 *
 */
final class ZJABXProductClassItem implements IJABXProductClassItem {

	/**
	 * 檔目代碼
	 */
	private String shelfID;

	/**
	 * 檔目名稱
	 */
	private String shelfName;

	/**
	 * @see com.syt.jabx.query.IJABXProductClassItem#getSehlfID()
	 */
	@Override
	public String getShelfID() {
		// TODO Auto-generated method stub
		return shelfID;
	}

	/**
	 * 設定檔目代碼
	 * @param shelfID - String
	 */
	public void setShelfID(String shelfID) {
		this.shelfID = shelfID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXProductClassItem#getShelfName()
	 */
	@Override
	public String getShelfName() {
		// TODO Auto-generated method stub
		return shelfName;
	}

	/**
	 * 設定檔目名稱
	 * @param shelfName - String
	 */
	public void setShelfName(String shelfName) {
		this.shelfName = shelfName;
	}

}
