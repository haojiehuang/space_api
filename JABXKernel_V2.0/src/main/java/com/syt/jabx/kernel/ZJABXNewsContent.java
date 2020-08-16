package com.syt.jabx.kernel;

import java.util.List;

import com.syt.jabx.query.IJABXNewsContent;

/**
 * 新聞內容的類別
 * @author Jason
 *
 */
final class ZJABXNewsContent implements IJABXNewsContent {

	/**
	 * 新聞序號
	 */
	private int serialNo;

	/**
	 * 新聞來源代碼
	 */
	private int sourceID;

	/**
	 * 新聞日期時間(格式為："YYYY/MM/DD hh:mm:ss")
	 */
	private String dataTime;

	/**
	 * 新聞標題
	 */
	private String title;

	/**
	 * 新聞相關股票全代碼列表
	 */
	private List<String> listOfStockID;

	/**
	 * 新聞內容
	 */
	private String data;

	/**
	 * @see com.syt.jabx.query.IJABXNewsContent#getSerialNo()
	 */
	@Override
	public int getSerialNo() {
		// TODO Auto-generated method stub
		return serialNo;
	}

	/**
	 * 設定新聞序號
	 * @param serialNo - int
	 */
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @see com.syt.jabx.query.IJABXNewsContent#getSourceID()
	 */
	@Override
	public int getSourceID() {
		// TODO Auto-generated method stub
		return sourceID;
	}

	/**
	 * 設定新聞來源代碼
	 * @param sourceID - int
	 */
	public void setSourceID(int sourceID) {
		this.sourceID = sourceID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXNewsContent#getDataTime()
	 */
	@Override
	public String getDataTime() {
		// TODO Auto-generated method stub
		return dataTime;
	}

	/**
	 * 設定新聞日期時間(格式為："YYYY/MM/DD hh:mm:ss")
	 * @param dataTime - String
	 */
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	/**
	 * @see com.syt.jabx.query.IJABXNewsContent#getTitle()
	 */
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	/**
	 * 設定新聞標題
	 * @param title - String
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @see com.syt.jabx.query.IJABXNewsContent#getListOfStockID()
	 */
	@Override
	public List<String> getListOfStockID() {
		// TODO Auto-generated method stub
		return listOfStockID;
	}

	/**
	 * 設定新聞相關股票全代碼列表
	 * @param listOfStockID - List&lt;String&gt;
	 */
	public void setListOfStockID(List<String> listOfStockID) {
		this.listOfStockID = listOfStockID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXNewsContent#getData()
	 */
	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return data;
	}

	/**
	 * 設定新聞內容
	 * @param data - String
	 */
	public void setData(String data) {
		this.data = data;
	}
}
