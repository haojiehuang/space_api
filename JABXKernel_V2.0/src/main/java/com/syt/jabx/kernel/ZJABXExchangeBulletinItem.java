package com.syt.jabx.kernel;

import java.util.List;

import com.syt.jabx.message.IJABXExchangeBulletinItem;

/**
 * 交易所公告標題項目的類別 
 * @author Jason
 *
 */
final class ZJABXExchangeBulletinItem implements IJABXExchangeBulletinItem {

	/**
	 * 交易所公告序號
	 */
	private int serialNo;

	/**
	 * 交易所代碼
	 */
	private String exchangeID;

	/**
	 * 交易所公告分類代碼
	 */
	private String classID;

	/**
	 * 交易所公告類型
	 */
	private String bulletinType;

	/**
	 * 交易所公告內容資料格式
	 */
	private String dataType;

	/**
	 * 相關連股票列表
	 */
	private List<String> listOfStockID;

	/**
	 * 日期及時間
	 */
	private String dataTime;

	/**
	 * 訊息標題
	 */
	private String title;

	/**
	 * @see com.syt.jabx.message.IJABXExchangeBulletinItem#getSerialNo()
	 */
	@Override
	public int getSerialNo() {
		// TODO Auto-generated method stub
		return serialNo;
	}

	/**
	 * 設定交易所公告序號
	 * @param serialNo - int
	 */
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @see com.syt.jabx.message.IJABXExchangeBulletinItem#getExchangeID()
	 */
	@Override
	public String getExchangeID() {
		// TODO Auto-generated method stub
		return exchangeID;
	}

	/**
	 * 設定交易所代碼
	 * @param exchangeID - String
	 */
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}

	/**
	 * @see com.syt.jabx.message.IJABXExchangeBulletinItem#getClassID()
	 */
	@Override
	public String getClassID() {
		// TODO Auto-generated method stub
		return classID;
	}

	/**
	 * 設定交易所公告分類代碼
	 * @param classID - String
	 */
	public void setClassID(String classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.message.IJABXExchangeBulletinItem#getBulletinType()
	 */
	@Override
	public String getBulletinType() {
		// TODO Auto-generated method stub
		return bulletinType;
	}

	/**
	 * 設定交易所公告類型
	 * @param bulletinType - String
	 */
	public void setBulletinType(String bulletinType) {
		this.bulletinType = bulletinType;
	}

	/**
	 * @see com.syt.jabx.message.IJABXExchangeBulletinItem#getDataType()
	 */
	@Override
	public String getDataType() {
		// TODO Auto-generated method stub
		return dataType;
	}

	/**
	 * 設定交易所公告內容資料格式
	 * @param dataType - String
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @see com.syt.jabx.message.IJABXExchangeBulletinItem#getListOfStockID()
	 */
	@Override
	public List<String> getListOfStockID() {
		// TODO Auto-generated method stub
		return listOfStockID;
	}

	/**
	 * 設定相關連股票列表
	 * @param listOfStockID - List&lt;String&gt;
	 */
	public void setListOfStockID(List<String> listOfStockID) {
		this.listOfStockID = listOfStockID;
	}

	/**
	 * @see com.syt.jabx.message.IJABXExchangeBulletinItem#getDataTime()
	 */
	@Override
	public String getDataTime() {
		// TODO Auto-generated method stub
		return dataTime;
	}

	/**
	 * 設定日期及時間
	 * @param dataTime - String
	 */
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	/**
	 * @see com.syt.jabx.message.IJABXExchangeBulletinItem#getTitle()
	 */
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	/**
	 * 設定訊息標題
	 * @param title - String
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
