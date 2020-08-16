package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXOthers;

/**
 * 股票其他報價資訊之類別
 * @author Jason
 *
 */
final class ZJABXWatchOthers implements IJABXOthers {

	/**
	 * 今開價
	 */
	private int openPrice;

	/**
	 * 今高價
	 */
	private int highPrice;

	/**
	 * 今低價
	 */
	private int lowPrice;

	/**
	 * 今未平倉量
	 */
	private int todayOI;

	/**
	 * 結算價
	 */
	private int settlementPrice;

	/**
	 * 均買價
	 */
	private int avgBidPrice;

	/**
	 * 均賣價
	 */
	private int avgAskPrice;

	/**
	 * 合約高
	 */
	private int contractHigh;

	/**
	 * 合約低
	 */
	private int contractLow;

	/**
	 * 定價成交量
	 */
	private int fixPriceVolume;

	/**
	 * 衍生最佳買價(for TAIFEX)
	 */
	private int derivedBidPrice;

	/**
	 * 衍生最佳買量(for TAIFEX)
	 */
	private int derivedBidVolume;

	/**
	 * 衍生最佳賣價(for TAIFEX)
	 */
	private int derivedAskPrice;

	/**
	 * 衍生最佳賣量(for TAIFEX)
	 */
	private int derivedAskVolume;

	/**
	 * 按盤價(for HK)
	 */
	private int normalPrice;
	
	/**
	 * @see com.syt.jabx.watch.IJABXOthers#getOpenPrice()
	 */
	@Override
	public int getOpenPrice() {
		// TODO Auto-generated method stub
		return openPrice;
	}

	/**
	 * 設定今開價
	 * @param openPrice - int
	 */
	public void setOpenPrice(int openPrice) {
		this.openPrice = openPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOthers#getHighPrice()
	 */
	@Override
	public int getHighPrice() {
		// TODO Auto-generated method stub
		return highPrice;
	}

	/**
	 * 設定今高價
	 * @param highPrice - int
	 */
	public void setHighPrice(int highPrice) {
		this.highPrice = highPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOthers#getLowPrice()
	 */
	@Override
	public int getLowPrice() {
		// TODO Auto-generated method stub
		return lowPrice;
	}

	/**
	 * 設定今低價
	 * @param lowPrice - int
	 */
	public void setLowPrice(int lowPrice) {
		this.lowPrice = lowPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOthers#getTodayOI()
	 */
	@Override
	public int getTodayOI() {
		// TODO Auto-generated method stub
		return todayOI;
	}

	/**
	 * 設定今未平倉量
	 * @param todayOI - int
	 */
	public void setTodayOI(int todayOI) {
		this.todayOI = todayOI;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOthers#getSettlementPrice()
	 */
	@Override
	public int getSettlementPrice() {
		// TODO Auto-generated method stub
		return settlementPrice;
	}

	/**
	 * 設定結算價
	 * @param settlementPrice - int
	 */
	public void setSettlementPrice(int settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOthers#getAvgBidPrice()
	 */
	@Override
	public int getAvgBidPrice() {
		// TODO Auto-generated method stub
		return avgBidPrice;
	}

	/**
	 * 設定均買價
	 * @param avgBidPrice - int
	 */
	public void setAvgBidPrice(int avgBidPrice) {
		this.avgBidPrice = avgBidPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOthers#getOpenPrice()
	 */
	@Override
	public int getAvgAskPrice() {
		// TODO Auto-generated method stub
		return avgAskPrice;
	}

	/**
	 * 設定均賣價
	 * @param avgAskPrice - int
	 */
	public void setAvgAskPrice(int avgAskPrice) {
		this.avgAskPrice = avgAskPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOthers#getContractHigh()
	 */
	@Override
	public int getContractHigh() {
		// TODO Auto-generated method stub
		return contractHigh;
	}

	/**
	 * 設定合約高
	 * @param contractHigh - int
	 */
	public void setContractHigh(int contractHigh) {
		this.contractHigh = contractHigh;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOthers#getContractLow()
	 */
	@Override
	public int getContractLow() {
		// TODO Auto-generated method stub
		return contractLow;
	}

	/**
	 * 設定合約低
	 * @param contractLow - int
	 */
	public void setContractLow(int contractLow) {
		this.contractLow = contractLow;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOthers#getFixPriceVolume()
	 */
	@Override
	public int getFixPriceVolume() {
		// TODO Auto-generated method stub
		return fixPriceVolume;
	}

	/**
	 * 設定定價成交量
	 * @param fixPriceVolume - int
	 */
	public void setFixPriceVolume(int fixPriceVolume) {
		this.fixPriceVolume = fixPriceVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOthers#getDerivedBidPrice()
	 */
	@Override
	public int getDerivedBidPrice() {
		// TODO Auto-generated method stub
		return derivedBidPrice;
	}

	/**
	 * 設定衍生最佳買價(for TAIFEX)
	 * @param derivedBidPrice - int
	 */
	public void setDerivedBidPrice(int derivedBidPrice) {
		this.derivedBidPrice = derivedBidPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOthers#getDerivedBidVolume()
	 */
	@Override
	public int getDerivedBidVolume() {
		// TODO Auto-generated method stub
		return derivedBidVolume;
	}

	/**
	 * 設定取得衍生最佳買量(for TAIFEX)
	 * @param derivedBidVolume - int
	 */
	public void setDerivedBidVolume(int derivedBidVolume) {
		this.derivedBidVolume = derivedBidVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOthers#getDerivedAskPrice()
	 */
	@Override
	public int getDerivedAskPrice() {
		// TODO Auto-generated method stub
		return derivedAskPrice;
	}

	/**
	 * 設定衍生最佳賣價(for TAIFEX)
	 * @param derivedAskPrice - int
	 */
	public void setDerivedAskPrice(int derivedAskPrice) {
		this.derivedAskPrice = derivedAskPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOthers#getDerivedAskVolume()
	 */
	@Override
	public int getDerivedAskVolume() {
		// TODO Auto-generated method stub
		return derivedAskVolume;
	}

	/**
	 * 設定衍生最佳賣量(for TAIFEX)
	 * @param derivedAskVolume - int
	 */
	public void setDerivedAskVolume(int derivedAskVolume) {
		this.derivedAskVolume = derivedAskVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOthers#getNormalPrice()
	 */
	@Override
	public int getNormalPrice() {
		// TODO Auto-generated method stub
		return normalPrice;
	}

	/**
	 * 設定按盤價(for HK)
	 * @param normalPrice - int
	 */
	public void setNormalPrice(int normalPrice) {
		this.normalPrice = normalPrice;
	}

	/**
	 * 將類別中所用到的欄位設定為零或空白
	 */
	public void setDataToZeroOrSpace() {
		openPrice = 0;
		highPrice = 0;
		lowPrice = 0;
		todayOI = 0;
		settlementPrice = 0;
		avgBidPrice = 0;
		avgAskPrice =  0;
		contractHigh = 0;
		contractLow = 0;
		fixPriceVolume = 0;
		derivedBidPrice = 0;
		derivedBidVolume = 0;
		derivedAskPrice = 0;
		derivedAskVolume = 0;
		normalPrice = 0;
	}
}
