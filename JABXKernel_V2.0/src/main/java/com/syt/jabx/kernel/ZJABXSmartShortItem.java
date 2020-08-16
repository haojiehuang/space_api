package com.syt.jabx.kernel;

import com.syt.jabx.smartmsg.IJABXSmartShortItem;

/**
 * 短線精靈資訊項目的類別
 * @author Jason
 *
 */
final class ZJABXSmartShortItem implements IJABXSmartShortItem {

	/**
	 * 短線精靈分類代碼
	 */
	private String classID;

	/**
	 * 交易所代碼或區域代碼
	 */
	private String exchangeID;

	/**
	 * 證券代碼
	 */
	private String id;

	/**
	 * 成交時間
	 */
	private int tradeTime;

	/**
	 * 成交價
	 */
	private int tradePrice;

	/**
	 * 成交價漲趺
	 */
	private int priceType;

	/**
	 * 成交單量
	 */
	private int tradeVolume;

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartShortItem#getClassID()
	 */
	@Override
	public String getClassID() {
		// TODO Auto-generated method stub
		return classID;
	}

	/**
	 * 設定短線精靈分類代碼
	 * @param classID - String
	 */
	public void setClassID(String classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartShortItem#getExchangeID()
	 */
	@Override
	public String getExchangeID() {
		// TODO Auto-generated method stub
		return exchangeID;
	}

	/**
	 * 設定交易所代碼或區域代碼
	 * @param exchangeID - String
	 */
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartShortItem#getID()
	 */
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return id;
	}

	/**
	 * 設定證券全代碼
	 * @param id - String
	 */
	public void setID(String id) {
		this.id = id;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartShortItem#getTradeTime()
	 */
	@Override
	public int getTradeTime() {
		// TODO Auto-generated method stub
		return tradeTime;
	}

	/**
	 * 設定成交時間
	 * @param tradeTime - int
	 */
	public void setTradeTime(int tradeTime) {
		this.tradeTime = tradeTime;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartShortItem#getTradePrice()
	 */
	@Override
	public int getTradePrice() {
		// TODO Auto-generated method stub
		return tradePrice;
	}

	/**
	 * 設定成交價
	 * @param tradePrice - int
	 */
	public void setTradePrice(int tradePrice) {
		this.tradePrice = tradePrice;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartShortItem#getPriceType()
	 */
	@Override
	public int getPriceType() {
		// TODO Auto-generated method stub
		return priceType;
	}

	/**
	 * 設定成交價漲趺
	 * @param priceType - int
	 */
	public void setPriceType(int priceType) {
		this.priceType = priceType;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartShortItem#getTradeVolume()
	 */
	@Override
	public int getTradeVolume() {
		// TODO Auto-generated method stub
		return tradeVolume;
	}

	/**
	 * 設定成交單量
	 * @param tradeVolume - int
	 */
	public void setTradeVolume(int tradeVolume) {
		this.tradeVolume = tradeVolume;
	}

}