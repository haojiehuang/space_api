package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXTotRefInfo;

/**
 * 即時總委買賣量筆及成交筆資訊的類別
 * @author Jason
 *
 */
final class ZJABXWatchTotRefInfo implements IJABXTotRefInfo {

	/**
	 * 統計時間(HHmm)
	 */
	private int tradeTime;

	/**
	 * 委賣總量
	 */
	private long totAskVolume;

	/**
	 * 委賣總筆
	 */
	private int totAskCount;

	/**
	 * 委買總量
	 */
	private long totBidVolume;

	/**
	 * 委買總筆
	 */
	private int totBidCount;

	/**
	 * 買進成交總筆(Just for TAIFEX)
	 */
	private int totBidTradeCount;

	/**
	 * 賣出成交總筆(Just for TAIFEX)
	 */
	private int totAskTradeCount;
 
	/**
	 * @see com.syt.jabx.watch.IJABXTotRefInfoItem#getTradeTime()
	 */
	@Override
	public int getTradeTime() {
		// TODO Auto-generated method stub
		return tradeTime;
	}

	/**
	 * 設定統計時間(HHmm)
	 * @param tradeTime - int(HHmm)
	 */
	public void setTradeTime(int tradeTime) {
		this.tradeTime = tradeTime;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTotRefInfoItem#getTotAskVolume()
	 */
	@Override
	public long getTotAskVolume() {
		// TODO Auto-generated method stub
		return totAskVolume;
	}

	/**
	 * 設定委賣總量
	 * @param totAskVolume - long
	 */
	public void setTotAskVolume(long totAskVolume) {
		this.totAskVolume = totAskVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTotRefInfoItem#getTotAskCount()
	 */
	@Override
	public int getTotAskCount() {
		// TODO Auto-generated method stub
		return totAskCount;
	}

	/**
	 * 設定委賣總筆
	 * @param totAskCount - int
	 */
	public void setTotAskCount(int totAskCount) {
		this.totAskCount = totAskCount;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTotRefInfoItem#getTotBidVolume()
	 */
	@Override
	public long getTotBidVolume() {
		// TODO Auto-generated method stub
		return totBidVolume;
	}

	/**
	 * 設定委買總量
	 * @param totBidVolume - long
	 */
	public void setTotBidVolume(long totBidVolume) {
		this.totBidVolume = totBidVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTotRefInfoItem#getTotBidCount()
	 */
	@Override
	public int getTotBidCount() {
		// TODO Auto-generated method stub
		return totBidCount;
	}

	/**
	 * 設定委買總筆
	 * @param totBidCount - int
	 */
	public void setTotBidCount(int totBidCount) {
		this.totBidCount = totBidCount;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTotRefInfoItem#getTotBidTradeCount()
	 */
	@Override
	public int getTotBidTradeCount() {
		// TODO Auto-generated method stub
		return totBidTradeCount;
	}

	/**
	 * 設定買進成交總筆(Just for TAIFEX)
	 * @param totBidTradeCount - int
	 */
	public void setTotBidTradeCount(int totBidTradeCount) {
		this.totBidTradeCount = totBidTradeCount;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTotRefInfoItem#getTotAskTradeCount()
	 */
	@Override
	public int getTotAskTradeCount() {
		// TODO Auto-generated method stub
		return totAskTradeCount;
	}

	/**
	 * 設定賣出成交總筆(Just for TAIFEX)
	 * @param totAskTradeCount- int
	 */
	public void setTotAskTradeCount(int totAskTradeCount) {
		this.totAskTradeCount = totAskTradeCount;
	}

	/**
	 * 複製數據
	 * @return ZJABXWatchTotRefInfo
	 */
	public ZJABXWatchTotRefInfo copyData() {
		ZJABXWatchTotRefInfo item = new ZJABXWatchTotRefInfo();		
		item.tradeTime = tradeTime;
		item.totAskVolume = totAskVolume;
		item.totAskCount = totAskCount;
		item.totBidVolume = totBidVolume;
		item.totBidCount = totBidCount;
		item.totBidTradeCount = totBidTradeCount;
		item.totAskTradeCount = totAskTradeCount;
		return item;
	}

	/**
	 * 將類別中所用到的欄位設定為零或空白
	 */
	public void setDataToZeroOrSpace() {
		tradeTime = 0;
		totAskVolume = 0;
		totAskCount = 0;
		totBidVolume = 0;
		totBidCount = 0;
		totBidTradeCount = 0;
		totAskTradeCount = 0;
	}
}
