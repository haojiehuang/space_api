package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXProductBulletinContent;

/**
 * 產品公告內容的類別
 * @author Jason
 *
 */
final class ZJABXProductBulletinContent implements IJABXProductBulletinContent {

	/**
	 * 產品公告序號
	 */
	private int serialNo;

	/**
	 * 產品公告日期時間(格式為："YYYY/MM/DD hh:mm:ss")
	 */
	private String dataTime;

	/**
	 * 產品公告類型
	 */
	private int bulletinType;

	/**
	 * 產品公告標題
	 */
	private String title;

	/**
	 * 產品公告內容
	 */
	private String data;

	/**
	 * @see com.syt.jabx.query.IJABXProductBulletinContent#getSerialNo()
	 */
	@Override
	public int getSerialNo() {
		// TODO Auto-generated method stub
		return serialNo;
	}

	/**
	 * 設定產品公告序號
	 * @param serialNo - int
	 */
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @see com.syt.jabx.query.IJABXProductBulletinContent#getDataTime()
	 */
	@Override
	public String getDataTime() {
		// TODO Auto-generated method stub
		return dataTime;
	}

	/**
	 * 設定產品公告日期時間(格式為："YYYY/MM/DD hh:mm:ss")
	 * @param dataTime - String
	 */
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	/**
	 * @see com.syt.jabx.query.IJABXProductBulletinContent#getBulletinType()
	 */
	@Override
	public int getBulletinType() {
		// TODO Auto-generated method stub
		return bulletinType;
	}

	/**
	 * 設定產品公告類型
	 * @param bulletinType - int
	 */
	public void setBulletinType(int bulletinType) {
		this.bulletinType = bulletinType;
	}

	/**
	 * @see com.syt.jabx.query.IJABXProductBulletinContent#getTitle()
	 */
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	/**
	 * 設定產品公告標題
	 * @param title - String
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @see com.syt.jabx.query.IJABXProductBulletinContent#getData()
	 */
	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return data;
	}

	/**
	 * 設定產品公告內容
	 * @param data - String
	 */
	public void setData(String data) {
		this.data = data;
	}
}
