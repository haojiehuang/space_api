package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXPriceTradeItem;

/**
 * 成交價量明細項目類別
 * @author jason
 *
 */
final class ZJABXPriceTradeItem implements IJABXPriceTradeItem, Comparable<ZJABXPriceTradeItem> {

	/**
	 * 成交價
	 */
	private int tradePrice;

	/**
	 * 成交量
	 */
	private long tradeVolume;

	/**
	 * 內盤量
	 */
	private long inVolume;

	/**
	 * 外盤量
	 */
	private long outVolume;

	/**
	 * @see com.syt.jabx.watch.IJABXPriceTradeItem#getTradePrice()
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
	void setTradePrice(int tradePrice) {
		this.tradePrice = tradePrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXPriceTradeItem#getTradeVolume()
	 */
	@Override
	public long getTradeVolume() {
		// TODO Auto-generated method stub
		return tradeVolume;
	}

	/**
	 * 設定成交量
	 * @param tradeVolume - long
	 */
	void setTradeVolume(long tradeVolume) {
		this.tradeVolume = tradeVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXPriceTradeItem#getInVolume()
	 */
	@Override
	public long getInVolume() {
		// TODO Auto-generated method stub
		return inVolume;
	}

	/**
	 * 設定內盤量
	 * @param inVolume - long
	 */
	void setInVolume(long inVolume) {
		this.inVolume = inVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXPriceTradeItem#getOutVolume()
	 */
	@Override
	public long getOutVolume() {
		// TODO Auto-generated method stub
		return outVolume;
	}

	/**
	 * 設定外盤量
	 * @param outVolume - long
	 */
	void setOutVolume(long outVolume) {
		this.outVolume = outVolume;
	}

	@Override
	public int compareTo(ZJABXPriceTradeItem o) {
		// TODO Auto-generated method stub
		if (tradePrice < o.getTradePrice()) {
			return -1;
		}else if (tradePrice > o.getTradePrice()) {
			return 1;
		}else {
			return 0;
		}
	}

}
