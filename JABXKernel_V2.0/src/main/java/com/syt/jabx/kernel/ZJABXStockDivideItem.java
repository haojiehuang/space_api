package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXStockDivideItem;

/**
 * 股票除權息項目的類別
 * @author Jason
 *
 */
final class ZJABXStockDivideItem implements IJABXStockDivideItem {

	/**
	 * 除權息日期(yyyyMMdd)
	 */
	private String divideDate;

	/**
	 * 派息
	 */
	private String divideCash;

	/**
	 * 送股比例
	 */
	private String bonusRate;

	/**
	 * 轉增比例
	 */
	private String transferRate;

	/**
	 * 配股比例
	 */
	private String expandRate;

	/**
	 * 增發比例
	 */
	private String increaseRate;

	/**
	 * 配股價格
	 */
	private String expandPrice;

	/**
	 * 增發價格
	 */
	private String increasePrice;

	/**
	 * 配股證券全代碼
	 */
	private String expandID;

	/**
	 * 增發證券全代碼
	 */
	private String increaseID;

	/**
	 * @see com.syt.jabx.query.IJABXStockDivideItem#getDivideDate()
	 */
	@Override
	public String getDivideDate() {
		// TODO Auto-generated method stub
		return divideDate;
	}

	/**
	 * 設定除權息日期(yyyyMMdd)
	 * @param divideDate - String
	 */
	public void setDivideDate(String divideDate) {
		this.divideDate = divideDate;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockDivideItem#getDivideCash()
	 */
	@Override
	public String getDivideCash() {
		// TODO Auto-generated method stub
		return divideCash;
	}

	/**
	 * 設定派息
	 * @param divideCash - String
	 */
	public void setDivideCash(String divideCash) {
		this.divideCash = divideCash;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockDivideItem#getBonusRate()
	 */
	@Override
	public String getBonusRate() {
		// TODO Auto-generated method stub
		return bonusRate;
	}

	/**
	 * 設定送股比例
	 * @param bonusRate - String
	 */
	public void setBonusRate(String bonusRate) {
		this.bonusRate = bonusRate;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockDivideItem#getTransferRate()
	 */
	@Override
	public String getTransferRate() {
		// TODO Auto-generated method stub
		return transferRate;
	}

	/**
	 * 設定轉增比例
	 * @param transferRate - String
	 */
	public void setTransferRate(String transferRate) {
		this.transferRate = transferRate;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockDivideItem#getExpandRate()
	 */
	@Override
	public String getExpandRate() {
		// TODO Auto-generated method stub
		return expandRate;
	}

	/**
	 * 設定配股比例
	 * @param expandRate - String
	 */
	public void setExpandRate(String expandRate) {
		this.expandRate = expandRate;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockDivideItem#getIncreaseRate()
	 */
	@Override
	public String getIncreaseRate() {
		// TODO Auto-generated method stub
		return increaseRate;
	}

	/**
	 * 設定增發比例
	 * @param increaseRate - String
	 */
	public void setIncreaseRate(String increaseRate) {
		this.increaseRate = increaseRate;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockDivideItem#getExpandPrice()
	 */
	@Override
	public String getExpandPrice() {
		// TODO Auto-generated method stub
		return expandPrice;
	}

	/**
	 * 設定配股價格
	 * @param expandPrice - String
	 */
	public void setExpandPrice(String expandPrice) {
		this.expandPrice = expandPrice;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockDivideItem#getIncreasePrice()
	 */
	@Override
	public String getIncreasePrice() {
		// TODO Auto-generated method stub
		return increasePrice;
	}

	/**
	 * 設定增發價格
	 * @param increasePrice - String
	 */
	public void setIncreasePrice(String increasePrice) {
		this.increasePrice = increasePrice;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockDivideItem#getExpandID()
	 */
	@Override
	public String getExpandID() {
		// TODO Auto-generated method stub
		return expandID;
	}

	/**
	 * 設定配股證券全代碼
	 * @param expandID - String
	 */
	public void setExpandID(String expandID) {
		this.expandID = expandID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockDivideItem#getIncreaseID()
	 */
	@Override
	public String getIncreaseID() {
		// TODO Auto-generated method stub
		return increaseID;
	}

	/**
	 * 設定增發證券全代碼
	 * @param increaseID - String
	 */
	public void setIncreaseID(String increaseID) {
		this.increaseID = increaseID;
	}

}
