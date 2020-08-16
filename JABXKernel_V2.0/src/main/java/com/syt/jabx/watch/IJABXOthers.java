package com.syt.jabx.watch;

/**
 * 股票其他報價資訊之介面
 * @author Jason
 *
 */
public interface IJABXOthers {

	/**
	 * 取得今開價
	 * @return int
	 */
	public int getOpenPrice();

	/**
	 * 取得今高價
	 * @return int
	 */
	public int getHighPrice();

	/**
	 * 取得今低價
	 * @return int
	 */
	public int getLowPrice();

	/**
	 * 取得今未平倉量
	 * @return int
	 */
	public int getTodayOI();

	/**
	 * 取得結算價
	 * @return int
	 */
	public int getSettlementPrice();

	/**
	 * 取得均買價
	 * @return int
	 */
	public int getAvgBidPrice();

	/**
	 * 取得均賣價
	 * @return int
	 */
	public int getAvgAskPrice();

	/**
	 * 取得合約高
	 * @return int
	 */
	public int getContractHigh();

	/**
	 * 取得合約低
	 * @return int
	 */
	public int getContractLow();

	/**
	 * 取得定價成交量
	 * @return int
	 */
	public int getFixPriceVolume();

	/**
	 * 取得衍生最佳買價(for TAIFEX)
	 * @return int
	 */
	public int getDerivedBidPrice();

	/**
	 * 取得衍生最佳買量(for TAIFEX)
	 * @return int
	 */
	public int getDerivedBidVolume();

	/**
	 * 取得衍生最佳賣價(for TAIFEX)
	 * @return int
	 */
	public int getDerivedAskPrice();

	/**
	 * 取得衍生最佳賣量(for TAIFEX)
	 * @return int
	 */
	public int getDerivedAskVolume();

	/**
	 * 取得按盤價(for HK)
	 * @return int
	 */
	public int getNormalPrice();
}
