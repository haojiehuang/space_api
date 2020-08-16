package com.syt.jabx.kernel;

import java.util.List;

import com.syt.jabx.query.IJABXExchangeBulletinContent;

/**
 * 交易所公告內容的類別
 * @author Jason
 *
 */
final class ZJABXExchangeBulletinContent implements IJABXExchangeBulletinContent {

	/**
	 * 交易所公告序號
	 */
	private int serialNo;

	/**
	 * 交易所代碼
	 */
	private String exchangeID;

	/**
	 * 公告訊息日期時間(格式為："YYYY/MM/DD hh:mm:ss")
	 */
	private String dataTime;

	/**
	 * 交易所公告標題
	 */
	private String title;

	/**
	 * 交易所公告相關股票全代碼列表
	 */
	private List<String> listOfStockID;

	/**
	 * 交易所公告內容 
	 */
	private String data;

	/**
	 * @see com.syt.jabx.query.IJABXExchangeBulletinContent#getSerialNo()
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
	 * @see com.syt.jabx.query.IJABXExchangeBulletinContent#getExchangeID()
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
	 * @see com.syt.jabx.query.IJABXExchangeBulletinContent#getDataTime()
	 */
	@Override
	public String getDataTime() {
		// TODO Auto-generated method stub
		return dataTime;
	}

	/**
	 * 設定公告訊息日期時間(格式為："YYYY/MM/DD hh:mm:ss")
	 * @param dataTime - String
	 */
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeBulletinContent#getTitle()
	 */
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	/**
	 * 設定交易所公告標題
	 * @param title - String
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeBulletinContent#getListOfStockID()
	 */
	@Override
	public List<String> getListOfStockID() {
		// TODO Auto-generated method stub
		return listOfStockID;
	}

	/**
	 * 設定交易所公告相關股票全代碼列表
	 * @param listOfStockID - List&lt;String&gt;
	 */
	public void setListOfStockID(List<String> listOfStockID) {
		this.listOfStockID = listOfStockID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExchangeBulletinContent#getData()
	 */
	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return data;
	}

	/**
	 * 設定交易所公告內容 
	 * @param data - String
	 */
	public void setData(String data) {
		this.data = data;
	}

}
