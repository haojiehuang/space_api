package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXExpertSelectItem;

/**
 * 專家選股項目的類別
 * @author Jason
 *
 */
final class ZJABXExpertSelectItem implements IJABXExpertSelectItem {

	/**
	 * 專家選股項目總筆數
	 */
	private int expertSelectID;

	/**
	 * 推薦選股日期(格式為："YYYYMMDD")
	 */
	private String selectDate;

	/**
	 * 推薦證券全代碼
	 */
	private String id;

	/**
	 * 股票簡稱
	 */
	private String name;

	/**
	 * 收盤價
	 */
	private String closePrice;

	/**
	 * 壓力價
	 */
	private String highPrice;

	/**
	 * 支撐價
	 */
	private String lowPrice;

	/**
	 * 選股說明
	 */
	private String content;

	/**
	 * @see com.syt.jabx.query.IJABXExpertSelectItem#getExpertSelectID()
	 */
	@Override
	public int getExpertSelectID() {
		// TODO Auto-generated method stub
		return expertSelectID;
	}

	/**
	 * 設定專家選股項目總筆數
	 * @param expertSelectID - int
	 */
	public void setExpertSelectID(int expertSelectID) {
		this.expertSelectID = expertSelectID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExpertSelectItem#getSelectDate()
	 */
	@Override
	public String getSelectDate() {
		// TODO Auto-generated method stub
		return selectDate;
	}

	/**
	 * 設定推薦選股日期(格式為："YYYYMMDD")
	 * @param selectDate - String
	 */
	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExpertSelectItem#getID()
	 */
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return id;
	}

	/**
	 * 設定推薦證券全代碼
	 * @param id - String
	 */
	public void setID(String id) {
		this.id = id;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExpertSelectItem#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * 設定股票簡稱
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExpertSelectItem#getClosePrice()
	 */
	@Override
	public String getClosePrice() {
		// TODO Auto-generated method stub
		return closePrice;
	}

	/**
	 * 設定收盤價
	 * @param closePrice - String
	 */
	public void setClosePrice(String closePrice) {
		this.closePrice = closePrice;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExpertSelectItem#getHighPrice()
	 */
	@Override
	public String getHighPrice() {
		// TODO Auto-generated method stub
		return highPrice;
	}

	/**
	 * 設定壓力價
	 * @param highPrice - String
	 */
	public void setHighPrice(String highPrice) {
		this.highPrice = highPrice;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExpertSelectItem#getLowPrice()
	 */
	@Override
	public String getLowPrice() {
		// TODO Auto-generated method stub
		return lowPrice;
	}

	/**
	 * 設定支撐價
	 * @param lowPrice - String
	 */
	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}

	/**
	 * @see com.syt.jabx.query.IJABXExpertSelectItem#getContent()
	 */
	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return content;
	}

	/**
	 * 設定選股說明 
	 * @param content - String
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
