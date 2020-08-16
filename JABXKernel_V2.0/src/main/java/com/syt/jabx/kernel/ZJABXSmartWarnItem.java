package com.syt.jabx.kernel;

import com.syt.jabx.smartmsg.IJABXSmartWarnItem;

/**
 * 用戶警示訊息之類別
 * @author jason
 *
 */
final class ZJABXSmartWarnItem implements IJABXSmartWarnItem {

	/**
	 * 用戶ID
	 */
	private int userID;

	/**
	 * 股票代碼
	 */
	private String stkID;

	/**
	 * 警示ID
	 */
	private int warnID;

	/**
	 * 警示名稱
	 */
	private String warnName;

	/**
	 * 警示說明
	 */
	private String warnDesc;

	/**
	 * 用戶曾登錄的產品ID列表( 每個ID間以”|”分隔。如：”1｜21｜31｜41｜”)
	 */
	private String productIDs;

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartWarnItem#getUserID()
	 */
	@Override
	public int getUserID() {
		return userID;
	}

	/**
	 * 設定用戶ID
	 * @param userID - int
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartWarnItem#getStkID()
	 */
	@Override
	public String getStkID() {
		// TODO Auto-generated method stub
		return stkID;
	}

	/**
	 * 設定股票代碼
	 * @param stkID - String
	 */
	public void setStkID(String stkID) {
		this.stkID = stkID;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartWarnItem#getWarnID()
	 */
	@Override
	public int getWarnID() {
		// TODO Auto-generated method stub
		return warnID;
	}

	/**
	 * 設定警示ID
	 * @param warnID - int
	 */
	public void setWarnID(int warnID) {
		this.warnID = warnID;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartWarnItem#getWarnName()
	 */
	@Override
	public String getWarnName() {
		// TODO Auto-generated method stub
		return warnName;
	}

	/**
	 * 設定警示名稱
	 * @param warnName - String
	 */
	public void setWarnName(String warnName) {
		this.warnName = warnName;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartWarnItem#getWarnDesc()
	 */
	@Override
	public String getWarnDesc() {
		// TODO Auto-generated method stub
		return warnDesc;
	}

	/**
	 * 設定警示說明
	 * @param warnDesc - String
	 */
	public void setWarnDesc(String warnDesc) {
		this.warnDesc = warnDesc;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartWarnItem#getProductIDs()
	 */
	@Override
	public String getProductIDs() {
		// TODO Auto-generated method stub
		return productIDs;
	}

	/**
	 * 設定用戶曾登錄的產品ID列表( 每個ID間以”|”分隔。如：”1｜21｜31｜41｜”)
	 * @param warnDesc - String
	 */
	public void setProductIDs(String productIDs) {
		this.productIDs = productIDs;
	}
}