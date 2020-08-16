package com.syt.jabx.kernel;

import com.syt.jabx.message.IJABXProductBulletinItem;

/**
 * 實作產品公告標題項目的介面之類別
 * @author Jason
 *
 */
final class ZJABXProductBulletinItem implements IJABXProductBulletinItem {

	/**
	 * 產品公告序號
	 */
	private int serialNo;

	/**
	 * 產品代碼
	 */
	private String productID;

	/**
	 * 產品公告類型
	 */
	private String bulletinType;

	/**
	 * 產品公告內容資料格式
	 */
	private String dataType;

	/**
	 * 產品公告日期時間
	 */
	private String dataTime;

	/**
	 * 產品公告標題 
	 */
	private String title;

	/**
	 * @see com.syt.jabx.message.IJABXProductBulletinItem#getSerialNo()
	 */
	@Override
	public int getSerialNo() {
		// TODO Auto-generated method stub
		return this.serialNo;
	}

	/**
	 * 設定產品公告序號
	 * @param serialNo - int
	 */
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @see com.syt.jabx.message.IJABXProductBulletinItem#getProductID()
	 */
	@Override
	public String getProductID() {
		// TODO Auto-generated method stub
		return this.productID;
	}

	/**
	 * 設定產品代碼
	 * @param productID - String
	 */
	public void setProductID(String productID) {
		this.productID = productID;
	}

	/**
	 * @see com.syt.jabx.message.IJABXProductBulletinItem#getBulletinType()
	 */
	@Override
	public String getBulletinType() {
		// TODO Auto-generated method stub
		return this.bulletinType;
	}

	/**
	 * 設定產品公告類型
	 * @param bulletinType - String
	 */
	public void setBulletinType(String bulletinType) {
		this.bulletinType = bulletinType;
	}

	/**
	 * @see com.syt.jabx.message.IJABXProductBulletinItem#getDataType()
	 */
	@Override
	public String getDataType() {
		// TODO Auto-generated method stub
		return this.dataType;
	}

	/**
	 * 設定產品公告內容資料格式
	 * @param dataType - String
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @see com.syt.jabx.message.IJABXProductBulletinItem#getDataTime()
	 */
	@Override
	public String getDataTime() {
		// TODO Auto-generated method stub
		return this.dataTime;
	}

	/**
	 * 設定產品公告日期時間
	 * @param dataTime - String
	 */
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	/**
	 * @see com.syt.jabx.message.IJABXProductBulletinItem#getTitle()
	 */
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return this.title;
	}

	/**
	 * 設定產品公告標題
	 * @param title - String
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
