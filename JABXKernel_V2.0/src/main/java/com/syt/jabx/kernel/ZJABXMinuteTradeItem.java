package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXMinuteTradeItem;

/**
 * 股票成交分鐘明細項目的類別
 * @author Jason
 *
 */
final class ZJABXMinuteTradeItem implements IJABXMinuteTradeItem {

	/**
	 * 成交日期(格式為yyyyMMdd)
	 */
	private String tradeDate;

	/**
	 * 成交時間(格式為HHmm)
	 */
	private String tradeTime;

	/**
	 * 開盤價
	 */
	private int openPrice;

	/**
	 * 最高價
	 */
	private int highPrice;

	/**
	 * 最低價
	 */
	private int lowPrice;

	/**
	 * 成交價
	 */
	private int tradePrice;

	/**
	 * 成交量
	 */
	private long tradeVolume;

	/**
	 * 成交金額
	 */
	private long tradeAmount;

	/**
	 * 換手率
	 */
	private String changeRate;

	/**
	 * 總量
	 */
	private long totalVolume;

	/**
	 * 總金額
	 */
	private long amount;

	/**
	 * Constructor
	 */
	public ZJABXMinuteTradeItem() {
		tradeDate = "";
		tradeTime = "";
		changeRate = "0.000";	
	}

	/**
	 * @see com.syt.jabx.watch.IJABXMinuteTradeItem#getTradeDate()
	 */
	@Override
	public String getTradeDate() {
		// TODO Auto-generated method stub
		return tradeDate;
	}

	/**
	 * 設定成交日期(格式為yyyyMMdd)
	 * @param tradeDate - String
	 */
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXMinuteTradeItem#getTradeTime()
	 */
	@Override
	public String getTradeTime() {
		// TODO Auto-generated method stub
		return tradeTime;
	}

	/**
	 * 設定成交時間(格式為HHmm)
	 * @param tradeTime - String
	 */
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXMinuteTradeItem#getOpenPrice()
	 */
	@Override
	public int getOpenPrice() {
		// TODO Auto-generated method stub
		return openPrice;
	}

	/**
	 * 設定開盤價
	 * @param openPrice - int
	 */
	public void setOpenPrice(int openPrice) {
		this.openPrice = openPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXMinuteTradeItem#getHighPrice()
	 */
	@Override
	public int getHighPrice() {
		// TODO Auto-generated method stub
		return highPrice;
	}

	/**
	 * 設定最高價
	 * @param highPrice - int
	 */
	public void setHighPrice(int highPrice) {
		this.highPrice = highPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXMinuteTradeItem#getLowPrice()
	 */
	@Override
	public int getLowPrice() {
		// TODO Auto-generated method stub
		return lowPrice;
	}

	/**
	 * 設定最低價
	 * @param lowPrice - int
	 */
	public void setLowPrice(int lowPrice) {
		this.lowPrice = lowPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXMinuteTradeItem#getTradePrice()
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
	 * @see com.syt.jabx.watch.IJABXMinuteTradeItem#getTradeVolume()
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
	public void setTradeVolume(long tradeVolume) {
		this.tradeVolume = tradeVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXMinuteTradeItem#getTradeAmount()
	 */
	@Override
	public long getTradeAmount() {
		// TODO Auto-generated method stub
		return tradeAmount;
	}

	/**
	 * 設定成交金額
	 * @param tradeAmount - long
	 */
	public void setTradeAmount(long tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXMinuteTradeItem#getChangeRate()
	 */
	@Override
	public String getChangeRate() {
		// TODO Auto-generated method stub
		return changeRate;
	}

	/**
	 * 設定換手率
	 * @param changeRate - String
	 */
	public void setChangeRate(String changeRate) {
		this.changeRate = changeRate;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXMinuteTradeItem#getTotalVolume()
	 */
	@Override
	public long getTotalVolume() {
		// TODO Auto-generated method stub
		return totalVolume;
	}

	/**
	 * 設定總量
	 * @param totalVolume - long
	 */
	public void setTotalVolume(long totalVolume) {
		this.totalVolume = totalVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXMinuteTradeItem#getAmount()
	 */
	@Override
	public long getAmount() {
		// TODO Auto-generated method stub
		return amount;
	}

	/**
	 * 設定總金額
	 * @param amount - long
	 */
	public void setAmount(long amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TradeDate: ").append(tradeDate).append(" ");
		sb.append("TradeTime: ").append(tradeTime).append(" ");
		sb.append("OpenPrice: ").append(openPrice).append(" ");
		sb.append("HighPrice: ").append(highPrice).append(" ");
		sb.append("LowPrice: ").append(lowPrice).append(" ");
		sb.append("TradePrice: ").append(tradePrice).append(" ");
		sb.append("TradeVolume: ").append(tradeVolume).append(" ");
		sb.append("TradeAmount: ").append(tradeAmount).append(" ");
		sb.append("ChangeRate: ").append(changeRate).append(" ");
		sb.append("TotalVolume: ").append(totalVolume).append(" ");
		sb.append("Amount: ").append(amount).append(" ");
		return sb.toString();
	}
}
