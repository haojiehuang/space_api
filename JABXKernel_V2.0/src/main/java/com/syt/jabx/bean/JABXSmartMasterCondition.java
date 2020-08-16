package com.syt.jabx.bean;

/**
 * 提供主力大單訂閱或回補條件之Bean
 * @author Jason
 *
 */
public class JABXSmartMasterCondition {

	/**
	 * 交易所代碼
	 */
	private String exchangeID;

	/**
	 * 主力大單最小成交量
	 */
	private int minimumVolume;

	/**
	 * 主力大單最小成交金額
	 */
	private int minimumAmount;

	/**
	 * 取得交易所代碼
	 * @return String 
	 */
	public String getExchangeID() {
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
	 * 取得主力大單最小成交量
	 * @return int
	 */
	public int getMinimumVolume() {
		return minimumVolume;
	}

	/**
	 * 設定主力大單最小成交量
	 * @param minimumVolume - int
	 */
	public void setMinimumVolume(int minimumVolume) {
		this.minimumVolume = minimumVolume;
	}

	/**
	 * 取得主力大單最小成交金額
	 * @return int
	 */
	public int getMinimumAmount() {
		return minimumAmount;
	}

	/**
	 * 設定主力大單最小成交金額
	 * @param minimumAmount - int
	 */
	public void setMinimumAmount(int minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

}
