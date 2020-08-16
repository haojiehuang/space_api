package com.syt.jabx.kernel;

import java.util.List;

import com.syt.jabx.message.IJABXNewsItem;

/**
 * 實作新聞標題項目的介面之類別
 * @author Jason
 *
 */
final class ZJABXNewsItem implements IJABXNewsItem {

	/**
	 * 新聞序號
	 */
	private int serialNo;

	/**
	 * 新聞來源代碼
	 */
	private String sourceID;

	/**
	 * 新聞分類代碼
	 */
	private String classID;

	/**
	 * 新聞證券類型
	 */
	private String stockType;

	/**
	 * 新聞內容資料格式
	 */
	private String dataType;

	/**
	 * 新聞相關股票全代碼列表
	 */
	private List<String> listOfStockID;

	/**
	 * 新聞日期時間
	 */
	private String dataTime;

	/**
	 * 新聞標題 
	 */
	private String title;

	/**
	 * @see com.syt.jabx.message.IJABXNewsItem#getSerialNo()
	 */
	@Override
	public int getSerialNo() {
		// TODO Auto-generated method stub
		return this.serialNo;
	}

	/**
	 * 設定新聞序號
	 * @param serialNo - int
	 */
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @see com.syt.jabx.message.IJABXNewsItem#getSourceID()
	 */
	@Override
	public String getSourceID() {
		// TODO Auto-generated method stub
		return this.sourceID;
	}

	/**
	 * 設定新聞來源代碼
	 * @param sourceID - String
	 */
	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}

	/**
	 * @see com.syt.jabx.message.IJABXNewsItem#getClassID()
	 */
	@Override
	public String getClassID() {
		// TODO Auto-generated method stub
		return this.classID;
	}

	/**
	 * 設定新聞分類代碼
	 * @param classID - String
	 */
	public void setClassID(String classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.message.IJABXNewsItem#getStockType()
	 */
	@Override
	public String getStockType() {
		// TODO Auto-generated method stub
		return this.stockType;
	}

	/**
	 * 設定新聞證券類型
	 * @param stockType - String
	 */
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	/**
	 * @see com.syt.jabx.message.IJABXNewsItem#getDataType()
	 */
	@Override
	public String getDataType() {
		// TODO Auto-generated method stub
		return this.dataType;
	}

	/**
	 * 設定新聞內容資料格式
	 * @param dataType - String
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @see com.syt.jabx.message.IJABXNewsItem#getListOfStockID()
	 */
	@Override
	public List<String> getListOfStockID() {
		// TODO Auto-generated method stub
		return this.listOfStockID;
	}

	/**
	 * 設定新聞相關股票全代碼列表
	 * @param listOfStockID - List&lt;String&gt;
	 */
	public void setListOfStockID(List<String> listOfStockID) {
		this.listOfStockID = listOfStockID;
	}

	/**
	 * @see com.syt.jabx.message.IJABXNewsItem#getDataTime()
	 */
	@Override
	public String getDataTime() {
		// TODO Auto-generated method stub
		return this.dataTime;
	}

	/**
	 * 設定新聞日期時間
	 * @param dataTime - String
	 */
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	/**
	 * @see com.syt.jabx.message.IJABXNewsItem#getTitle()
	 */
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return this.title;
	}

	/**
	 * 設定新聞標題
	 * @param title - String
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
