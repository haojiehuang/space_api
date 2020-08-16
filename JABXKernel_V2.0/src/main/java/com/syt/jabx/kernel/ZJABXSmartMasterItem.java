package com.syt.jabx.kernel;

import com.syt.jabx.smartmsg.IJABXSmartMasterItem;

/**
 * 主力大單資訊項目的類別
 * @author Jason
 *
 */
final class ZJABXSmartMasterItem implements IJABXSmartMasterItem {

	/**
	 * 大單類別
	 */
	private String masterType;

	/**
	 * 交易所代碼或區域代碼
	 */
	private String exchangeID;

	/**
	 * 證券全代碼
	 */
	private String id;

	/**
	 * 委託或成交時間(HHmmsssss,後3sss 為 minisec)
	 */
	private int dataTime;

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
	 * 單量內外盤標誌
	 */
	private int volumeInOutFlag;

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartMasterItem#getMasterType()
	 */
	@Override
	public String getMasterType() {
		// TODO Auto-generated method stub
		return masterType;
	}

	/**
	 * 設定大單類別
	 * @param masterType - String
	 */
	public void setMasterType(String masterType) {
		this.masterType = masterType;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartMasterItem#getExchangeID()
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
	 * @see com.syt.jabx.smartmsg.IJABXSmartMasterItem#getID()
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
	 * @see com.syt.jabx.smartmsg.IJABXSmartMasterItem#getDataTime()
	 */
	@Override
	public int getDataTime() {
		// TODO Auto-generated method stub
		return dataTime;
	}

	/**
	 * 設定委託或成交時間(HHmmsssss,後3sss 為 minisec)
	 * @param dataTime - int
	 */
	public void setDataTime(int dataTime) {
		this.dataTime = dataTime;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartMasterItem#getTradePrice()
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
	 * @see com.syt.jabx.smartmsg.IJABXSmartMasterItem#getPriceType()
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
	 * @see com.syt.jabx.smartmsg.IJABXSmartMasterItem#getTradeVolume()
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

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartMasterItem#getInOutFlag()
	 */
	@Override
	public int getInOutFlag() {
		// TODO Auto-generated method stub
		return volumeInOutFlag;
	}

	/**
	 * 設定單量內外盤標誌
	 * @param volumeInOutFlag - int
	 */
	public void setVolumeInOutFlag(int volumeInOutFlag) {
		this.volumeInOutFlag = volumeInOutFlag;
	}

}
