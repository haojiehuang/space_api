package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXStatisticItem;

/**
 * 交易所統計訊息的類別
 * @author Jason
 *
 */
final class ZJABXWatchStatisticItem implements IJABXStatisticItem {

	/**
	 * 統計時間
	 */
	private int tradeTime;

	/**
	 * 漲停家數
	 */
	private int upStopNum;

	/**
	 * 跌停家數
	 */
	private int downStopNum;

	/**
	 * 上漲家數
	 */
	private int upNum;

	/**
	 * 下跌家數
	 */
	private int downNum;

	/**
	 * 平盤家數
	 */
	private int equalNum;

	/**
	 * 未成交家數
	 */
	private int noTradeNum;
	
	/**
	 * @see com.syt.jabx.watch.IJABXStatisticItem#getTradeTime()
	 */
	@Override
	public int getTradeTime() {
		// TODO Auto-generated method stub
		return tradeTime;
	}

	/**
	 * 設定統計時間
	 * @param tradeTime - int
	 */
	public void setTradeTime(int tradeTime) {
		this.tradeTime = tradeTime;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStatisticItem#getTradeTime()
	 */
	@Override
	public int getUpStopNum() {
		// TODO Auto-generated method stub
		return upStopNum;
	}

	/**
	 * 設定漲停家數
	 * @param upStopNum - int
	 */
	public void setUpStopNum(int upStopNum) {
		this.upStopNum = upStopNum;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStatisticItem#getTradeTime()
	 */
	@Override
	public int getDownStopNum() {
		// TODO Auto-generated method stub
		return downStopNum;
	}

	/**
	 * 設定跌停家數
	 * @param downStopNum - int
	 */
	public void setDownStopNum(int downStopNum) {
		this.downStopNum = downStopNum;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStatisticItem#getTradeTime()
	 */
	@Override
	public int getUpNum() {
		// TODO Auto-generated method stub
		return upNum;
	}

	/**
	 * 設定上漲家數
	 * @param upNum - int
	 */
	public void setUpNum(int upNum) {
		this.upNum = upNum;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStatisticItem#getTradeTime()
	 */
	@Override
	public int getDownNum() {
		// TODO Auto-generated method stub
		return downNum;
	}

	/**
	 * 設定下跌家數
	 * @param downNum - int
	 */
	public void setDownNum(int downNum) {
		this.downNum = downNum;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStatisticItem#getTradeTime()
	 */
	@Override
	public int getEqualNum() {
		// TODO Auto-generated method stub
		return equalNum;
	}

	/**
	 * 設定平盤家數
	 * @param equalNum - int
	 */
	public void setEqualNum(int equalNum) {
		this.equalNum = equalNum;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStatisticItem#getTradeTime()
	 */
	@Override
	public int getNoTradeNum() {
		// TODO Auto-generated method stub
		return noTradeNum;
	}

	/**
	 * 設定未成交家數
	 * @param noTradeNum - int
	 */
	public void setNoTradeNum(int noTradeNum) {
		this.noTradeNum = noTradeNum;
	}

	/**
	 * 複製數據
	 * @return ZJABXStatistic
	 */
	public ZJABXWatchStatisticItem copyData() {
		ZJABXWatchStatisticItem item = new ZJABXWatchStatisticItem();		
		item.tradeTime = tradeTime;
		item.upStopNum = upStopNum;
		item.downStopNum = downStopNum;
		item.upNum = upNum;
		item.downNum = downNum;
		item.equalNum = equalNum;
		item.noTradeNum = noTradeNum;
		return item;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TradeTime: ").append(tradeTime).append(" ");
		sb.append("UpStopNum: ").append(upStopNum).append(" ");
		sb.append("DownStopNum: ").append(downStopNum).append(" ");
		sb.append("UpNum: ").append(upNum).append(" ");
		sb.append("DownNum: ").append(downNum).append(" ");
		sb.append("EqualNum: ").append(equalNum).append(" ");
		sb.append("NoTradeNum: ").append(noTradeNum).append(" ");
		return sb.toString();
	}
}
