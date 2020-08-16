package com.syt.jabx.kernel;

import com.syt.jabx.message.IJABXAdvertisementItem;

/**
 * 實作廣告訊息項目的介面之類別
 * @author Jason
 *
 */
final class ZJABXAdvertisementItem implements IJABXAdvertisementItem {

	/**
	 * 廣告序號
	 */
	private int serialNo;

	/**
	 * 廣告分類代碼
	 */
	private String classID;

	/**
	 * 廣告訊息日期時間
	 */
	private String dataTime;

	/**
	 * 廣告訊息網址
	 */
	private String url;

	/**
	 * 廣告訊息
	 */
	private String data;

	/**
	 * Constructor
	 */
	public ZJABXAdvertisementItem() {
		classID = "";
		dataTime = "";
		url = "";
		data = "";
	}

	/**
	 * @see com.syt.jabx.message.IJABXAdvertisementItem#getSerialNo()
	 */
	@Override
	public int getSerialNo() {
		// TODO Auto-generated method stub
		return this.serialNo;
	}

	/**
	 * 設定廣告序號
	 * @param serialNo - int
	 */
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @see com.syt.jabx.message.IJABXAdvertisementItem#getClassID()
	 */
	@Override
	public String getClassID() {
		// TODO Auto-generated method stub
		return this.classID;
	}

	/**
	 * 設定廣告分類代碼
	 * @param classID - String
	 */
	public void setClassID(String classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.message.IJABXAdvertisementItem#getDataTime()
	 */
	@Override
	public String getDataTime() {
		// TODO Auto-generated method stub
		return this.dataTime;
	}

	/**
	 * 設定廣告訊息日期時間
	 * @param dataTime - String
	 */
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	/**
	 * @see com.syt.jabx.message.IJABXAdvertisementItem#getUrl()
	 */
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return this.url;
	}

	/**
	 * 設定廣告訊息網址
	 * @param url - String
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @see com.syt.jabx.message.IJABXAdvertisementItem#getData()
	 */
	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return this.data;
	}

	/**
	 * 設定廣告訊息
	 * @param data - String
	 */
	public void setData(String data) {
		this.data = data;
	}
}
