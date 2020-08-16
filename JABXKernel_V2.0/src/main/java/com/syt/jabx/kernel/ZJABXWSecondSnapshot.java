package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXSecondSnapshot;

/**
 * 一秒快照訊息之類別
 * @author Jason
 *
 */
final class ZJABXWSecondSnapshot implements IJABXSecondSnapshot {

	/**
	 * 第一買價
	 */
	private int bidPrice1;

	/**
	 * 第一買量
	 */
	private int bidVolume1;

	/**
	 * 第一賣價
	 */
	private int askPrice1;

	/**
	 * 第一賣量
	 */
	private int askVolume1;

	/**
	 * 成交時間(HHmmsssss,後3sss 為 mini sec)
	 */
	private int tradeTime;

	/**
	 * 成交類別(目前只有香港1byte)
	 */
	private String tradeType;

	/**
	 * 成交價
	 */
	private int tradePrice;

	/**
	 * 成交單量
	 */
	private int tradeVolume;

	/**
	 * 單量內外盤
	 */
	private int volumeInOutFlag;

	/**
	 * 成交總量
	 */
	private long totTradeVolume;

	/**
	 * 成交總金額
	 */
	private long totTradeAmount;

	/**
	 * 內盤總量
	 */
	private long totInVolume;

	/**
	 * 外盤總量
	 */
	private long totOutVolume;

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
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getBidPrice1()
	 */
	@Override
	public int getBidPrice1() {
		// TODO Auto-generated method stub
		return bidPrice1;
	}

	/**
	 * 設定第一買價
	 * @param bidPrice1 - int
	 */
	public void setBidPrice1(int bidPrice1) {
		this.bidPrice1 = bidPrice1;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getBidVolume1()
	 */
	@Override
	public int getBidVolume1() {
		// TODO Auto-generated method stub
		return bidVolume1;
	}

	/**
	 * 設定第一買量
	 * @param bidVolume1 - int
	 */
	public void setBidVolume1(int bidVolume1) {
		this.bidVolume1 = bidVolume1;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getAskPrice1()
	 */
	@Override
	public int getAskPrice1() {
		// TODO Auto-generated method stub
		return askPrice1;
	}

	/**
	 * 設定第一賣價
	 * @param askPrice1 - int
	 */
	public void setAskPrice1(int askPrice1) {
		this.askPrice1 = askPrice1;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getAskVolume1()
	 */
	@Override
	public int getAskVolume1() {
		// TODO Auto-generated method stub
		return askVolume1;
	}

	/**
	 * 設定第一賣量
	 * @param askVolume1 - int
	 */
	public void setAskVolume1(int askVolume1) {
		this.askVolume1 = askVolume1;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getTradeTime()
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
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getTradeType()
	 */
	@Override
	public String getTradeType() {
		// TODO Auto-generated method stub
		return tradeType;
	}

	/**
	 * 設定成交類別(目前只有香港1byte)
	 * @param tradeType - String
	 */
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getTradePrice()
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
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getTradeVolume()
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
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getVolumeInOutFlag()
	 */
	@Override
	public int getVolumeInOutFlag() {
		// TODO Auto-generated method stub
		return volumeInOutFlag;
	}

	/**
	 * 設定單量內外盤
	 * @param volumeInOutFlag - int
	 */
	public void setVolumeInOutFlag(int volumeInOutFlag) {
		this.volumeInOutFlag = volumeInOutFlag;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getTotTradeVolume()
	 */
	@Override
	public long getTotTradeVolume() {
		// TODO Auto-generated method stub
		return totTradeVolume;
	}

	/**
	 * 設定成交總量
	 * @param totTradeVolume - long
	 */
	public void setTotTradeVolume(long totTradeVolume) {
		this.totTradeVolume = totTradeVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getTotTradeAmount()
	 */
	@Override
	public long getTotTradeAmount() {
		// TODO Auto-generated method stub
		return totTradeAmount;
	}

	/**
	 * 設定成交總金額
	 * @param totTradeAmount - long
	 */
	public void setTotTradeAmount(long totTradeAmount) {
		this.totTradeAmount = totTradeAmount;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getTotInVolume()
	 */
	@Override
	public long getTotInVolume() {
		// TODO Auto-generated method stub
		return totInVolume;
	}

	/**
	 * 設定內盤總量
	 * @param totInVolume - long
	 */
	public void setTotInVolume(long totInVolume) {
		this.totInVolume = totInVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getTotOutVolume()
	 */
	@Override
	public long getTotOutVolume() {
		// TODO Auto-generated method stub
		return totOutVolume;
	}

	/**
	 * 設定外盤總量
	 * @param totOutVolume - long
	 */
	public void setTotOutVolume(long totOutVolume) {
		this.totOutVolume = totOutVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getOpenPrice()
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
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getHighPrice()
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
	 * @see com.syt.jabx.watch.IJABXSecondSnapshot#getLowPrice()
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
	 * 將類別中所用到的欄位設定為零或空白
	 */
	public void setDataToZeroOrSpace() {
		bidPrice1 = 0;
		bidVolume1 = 0;
		askPrice1 = 0;
		askVolume1 = 0;
		tradeTime = 0;
		tradeType = "";
		tradePrice = 0;
		tradeVolume = 0;
		volumeInOutFlag = 0;
		totTradeVolume = 0;
		totTradeAmount = 0;
		totInVolume = 0;
		totOutVolume = 0;
		openPrice = 0;
		highPrice = 0;
		lowPrice = 0;
	}
}
