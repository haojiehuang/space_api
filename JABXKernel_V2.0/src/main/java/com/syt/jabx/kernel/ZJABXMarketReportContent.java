package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXMarketReportContent;

/**
 * 行情報導內容的類別
 * @author Jason
 *
 */
final class ZJABXMarketReportContent implements IJABXMarketReportContent {

	/**
	 * 行情報導序號
	 */
	private int serialNo;

	/**
	 * 行情報導日期時間(格式為："YYYY/MM/DD hh:mm:ss")
	 */
	private String dataTime;

	/**
	 * 行情報導分類代碼 
	 */
	private String classID;

	/**
	 * 行情報導標題
	 */
	private String title;

	/**
	 * 行情報導摘要
	 */
	private String summary;

	/**
	 * 行情報導內容 
	 */
	private String data;

	/**
	 * @see com.syt.jabx.query.IJABXMarketReportContent#getSerialNo()
	 */
	@Override
	public int getSerialNo() {
		// TODO Auto-generated method stub
		return serialNo;
	}

	/**
	 * 設定行情報導序號
	 * @param serialNo - int
	 */
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @see com.syt.jabx.query.IJABXMarketReportContent#getDataTime()
	 */
	@Override
	public String getDataTime() {
		// TODO Auto-generated method stub
		return dataTime;
	}

	/**
	 * 設定行情報導日期時間(格式為："YYYY/MM/DD hh:mm:ss")
	 * @param dataTime - String
	 */
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	/**
	 * @see com.syt.jabx.query.IJABXMarketReportContent#getClassID()
	 */
	@Override
	public String getClassID() {
		// TODO Auto-generated method stub
		return classID;
	}

	/**
	 * 設定行情報導分類代碼 
	 * @param classID - String
	 */
	public void setClassID(String classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXMarketReportContent#getTitle()
	 */
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	/**
	 * 設定行情報導標題
	 * @param title - String
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @see com.syt.jabx.query.IJABXMarketReportContent#getSummary()
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return summary;
	}

	/**
	 * 設定行情報導摘要
	 * @param summary - String
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @see com.syt.jabx.query.IJABXMarketReportContent#getData()
	 */
	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return data;
	}

	/**
	 * 設定行情報導內容
	 * @param data - String
	 */
	public void setData(String data) {
		this.data = data;
	}
}
