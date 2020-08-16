package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXTotRefInfoItem;

/**
 * 總委買賣量筆及成交筆資訊的類別
 * @author Jason
 *
 */
final class ZJABXWatchTotRefInfoItem implements IJABXTotRefInfoItem {

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
	 * 成交總筆(rebuild多筆才有)
	 */
	private int totTradeCount;

	/**
	 * 成交總量(rebuild多筆才有)
	 */
	private long totTradeVolume;

	/**
	 * 成交總金額(rebuild多筆才有)
	 */
	private long totTradeAmount;

	/**
	 * 成交價(rebuild多筆才有)
	 */
	private int tradePrice;
 
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
	 * @see com.syt.jabx.watch.IJABXTotRefInfoItem#getTotTradeCount()
	 */
	@Override
	public int getTotTradeCount() {
		// TODO Auto-generated method stub
		return totTradeCount;
	}

	/**
	 * 設定成交總筆(rebuild多筆才有)
	 * @param totTradeCount - int
	 */
	public void setTotTradeCount(int totTradeCount) {
		this.totTradeCount = totTradeCount;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTotRefInfoItem#getTotTradeVolume()
	 */
	@Override
	public long getTotTradeVolume() {
		// TODO Auto-generated method stub
		return totTradeVolume;
	}

	/**
	 * 設定成交總量(rebuild多筆才有)
	 * @param totTradeVolume - long
	 */
	public void setTotTradeVolume(long totTradeVolume) {
		this.totTradeVolume = totTradeVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTotRefInfoItem#getTotTradeAmount()
	 */
	@Override
	public long getTotTradeAmount() {
		// TODO Auto-generated method stub
		return totTradeAmount;
	}

	/**
	 * 設定成交總金額(rebuild多筆才有)
	 * @param totTradeAmount - long
	 */
	public void setTotTradeAmount(long totTradeAmount) {
		this.totTradeAmount = totTradeAmount;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTotRefInfoItem#getTradePrice()
	 */
	@Override
	public int getTradePrice() {
		// TODO Auto-generated method stub
		return tradePrice;
	}

	/**
	 * 設定成交價(rebuild多筆才有)
	 * @param tradePrice - int
	 */
	public void setTradePrice(int tradePrice) {
		this.tradePrice = tradePrice;
	}

	/**
	 * 複製數據
	 * @return ZJABXTotRefInfo
	 */
	public ZJABXWatchTotRefInfoItem copyData() {
		ZJABXWatchTotRefInfoItem item = new ZJABXWatchTotRefInfoItem();		
		item.tradeTime = tradeTime;
		item.totAskVolume = totAskVolume;
		item.totAskCount = totAskCount;
		item.totBidVolume = totBidVolume;
		item.totBidCount = totBidCount;
		item.totBidTradeCount = totBidTradeCount;
		item.totAskTradeCount = totAskTradeCount;
		item.totTradeCount = totTradeCount;
		item.totTradeVolume = totTradeVolume;
		item.totTradeAmount = totTradeAmount;
		item.tradePrice = tradePrice;
		return item;
	}
}
