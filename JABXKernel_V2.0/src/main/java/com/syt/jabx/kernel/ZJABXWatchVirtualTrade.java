package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXVirtualTrade;

/**
 * 盤前虛擬撮合訊息(Just for SSE,SZSE,HK)之類別
 * @author Jason
 *
 */
final class ZJABXWatchVirtualTrade implements IJABXVirtualTrade {

	/**
	 * 委買價
	 */
	private int bidPrice;

	/**
	 * 委買量
	 */
	private int bidVolume;

	/**
	 * 委賣價
	 */
	private int askPrice;

	/**
	 * 委賣量
	 */
	private int askVolume;

	/**
	 * 成交時間(HHmmsssss,後3sss 為 mini sec)
	 */
	private int tradeTime;

	/**
	 * 成交價,if HK->IEP(盤前平衡價)
	 */
	private int tradePrice;

	/**
	 * 成交單量(單位:股 ,if HK->IEV)
	 */
	private int tradeVolume;

	/**
	 * 內外盤
	 */
	private int inOutFlag;

	/**
	 * @see com.syt.jabx.watch.IJABXVirtualTrade#getBidPrice()
	 */
	@Override
	public int getBidPrice() {
		// TODO Auto-generated method stub
		return bidPrice;
	}

	/**
	 * 設定委買價
	 * @param bidPrice - int
	 */
	public void setBidPrice(int bidPrice) {
		this.bidPrice = bidPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXVirtualTrade#getBidVolume()
	 */
	@Override
	public int getBidVolume() {
		// TODO Auto-generated method stub
		return bidVolume;
	}

	/**
	 * 設定委買量
	 * @param bidVolume - int
	 */
	public void setBidVolume(int bidVolume) {
		this.bidVolume = bidVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXVirtualTrade#getAskPrice()
	 */
	@Override
	public int getAskPrice() {
		// TODO Auto-generated method stub
		return askPrice;
	}

	/**
	 * 設定委賣價
	 * @param askPrice - int
	 */
	public void setAskPrice(int askPrice) {
		this.askPrice = askPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXVirtualTrade#getAskVolume()
	 */
	@Override
	public int getAskVolume() {
		// TODO Auto-generated method stub
		return askVolume;
	}

	/**
	 * 設定委賣量
	 * @param askVolume - int
	 */
	public void setAskVolume(int askVolume) {
		this.askVolume = askVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXVirtualTrade#getTradeTime()
	 */
	@Override
	public int getTradeTime() {
		// TODO Auto-generated method stub
		return tradeTime;
	}

	/**
	 * 設定成交時間(HHmmsssss,後3sss 為 mini sec)
	 * @param tradeTime - int
	 */
	public void setTradeTime(int tradeTime) {
		this.tradeTime = tradeTime;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXVirtualTrade#getTradePrice()
	 */
	@Override
	public int getTradePrice() {
		// TODO Auto-generated method stub
		return tradePrice;
	}

	/**
	 * 設定成交價,if HK->IEP(盤前平衡價)
	 * @param tradePrice - int
	 */
	public void setTradePrice(int tradePrice) {
		this.tradePrice = tradePrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXVirtualTrade#getTradeVolume()
	 */
	@Override
	public int getTradeVolume() {
		// TODO Auto-generated method stub
		return tradeVolume;
	}

	/**
	 * 設定成交單量(單位:股 ,if HK->IEV)
	 * @param tradeVolume - int
	 */
	public void setTradeVolume(int tradeVolume) {
		this.tradeVolume = tradeVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXVirtualTrade#getInOutFlag()
	 */
	@Override
	public int getInOutFlag() {
		// TODO Auto-generated method stub
		return inOutFlag;
	}

	/**
	 * 設定內外盤
	 * @param inOutFlag - int
	 */
	public void setInOutFlag(int inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

	/**
	 * 將類別中所用到的欄位設定為零或空白
	 */
	public void setDataToZeroOrSpace() {
		bidPrice = 0;
		bidVolume = 0;
		askPrice = 0;
		askVolume = 0;
		tradeTime = 0;
		tradePrice = 0;
		tradeVolume = 0;
		inOutFlag = 0;
	}
}