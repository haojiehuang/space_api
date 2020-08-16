package com.syt.jabx.kernel;

import com.syt.jabx.message.IJABXMarketReportItem;

/**
 * 實作行情報導標題項目的介面之類別
 * @author Jason
 *
 */
final class ZJABXMarketReportItem implements IJABXMarketReportItem {

	/**
	 * 行情報導序號
	 */
	private int serialNo;

	/**
	 * 行情報導分類代碼
	 */
	private String classID;

	/**
	 * 行情報導標題資料格式
	 */
	private String dataType;

	/**
	 * 行情報導標題日期時間
	 */
	private String dataTime;

	/**
	 * 行情報導標題
	 */
	private String title;
	
	/**
	 * @see com.syt.jabx.message.IJABXMarketReportItem#getSerialNo()
	 */
	@Override
	public int getSerialNo() {
		// TODO Auto-generated method stub
		return this.serialNo;
	}

	/**
	 * 設定行情報導序號
	 * @param serialNo - int
	 */
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @see com.syt.jabx.message.IJABXMarketReportItem#getClassID()
	 */
	@Override
	public String getClassID() {
		// TODO Auto-generated method stub
		return this.classID;
	}

	/**
	 * 設定行情報導分類代碼
	 * @param classID - String
	 */
	public void setClassID(String classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.message.IJABXMarketReportItem#getDataType()
	 */
	@Override
	public String getDataType() {
		// TODO Auto-generated method stub
		return this.dataType;
	}

	/**
	 * 設定行情報導標題資料格式
	 * @param dataType - String
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @see com.syt.jabx.message.IJABXMarketReportItem#getDataTime()
	 */
	@Override
	public String getDataTime() {
		// TODO Auto-generated method stub
		return this.dataTime;
	}

	/**
	 * 設定行情報導標題日期時間
	 * @param dataTime - String
	 */
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	/**
	 * @see com.syt.jabx.message.IJABXMarketReportItem#getTitle()
	 */
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return this.title;
	}

	/**
	 * 設定行情報導標題
	 * @param title - String
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
