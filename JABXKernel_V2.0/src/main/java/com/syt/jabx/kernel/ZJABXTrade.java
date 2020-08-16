package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXTrade;

/**
 * 股票成交分筆項目的類別
 * @author Jason
 *
 */
final class ZJABXTrade implements IJABXTrade {

	/**
	 * 成交序號
	 */
	private int seqNo;

	/**
	 * 成交時間(HHmmsssss,後3sss 為 minisec)
	 */
	private int tradeTime;

	/**
	 * 成交註記
	 */
	private String tradeNote;

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
	 * 成交總筆
	 */
	private int totTradeCount;

	/**
	 * 內盤總量
	 */
	private long totInVolume;

	/**
	 * 外盤總量
	 */
	private long totOutVolume;

	/**
	 * @see com.syt.jabx.watch.IJABXTrade#getSeqNo()
	 */
	@Override
	public int getSeqNo() {
		return seqNo;
	}

	/**
	 * 設定成交序號
	 * @param seqNo - int
	 */
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTrade#getTradeTime()
	 */
	@Override
	public int getTradeTime() {
		// TODO Auto-generated method stub
		return tradeTime;
	}

	/**
	 * 設定成交時間(HHmmsssss,後3sss 為 minisec)
	 * @param tradeTime - int
	 */
	public void setTradeTime(int tradeTime) {
		this.tradeTime = tradeTime;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTrade#getTradeNote()
	 */
	@Override
	public String getTradeNote() {
		// TODO Auto-generated method stub
		return tradeNote;
	}

	/**
	 * 設定成交註記
	 * @param tradeNote - String
	 */
	public void setTradeNote(String tradeNote) {
		this.tradeNote = tradeNote;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTrade#getTradePrice()
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
	 * @see com.syt.jabx.watch.IJABXTrade#getTradeVolume()
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
	 * @see com.syt.jabx.watch.IJABXTrade#getVolumeInOutFlag()
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
	 * @see com.syt.jabx.watch.IJABXTrade#getTotTradeVolume()
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
	 * @see com.syt.jabx.watch.IJABXTrade#getTotTradeAmount()
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
	 * @see com.syt.jabx.watch.IJABXTrade#getTotTradeCount()
	 */
	@Override
	public int getTotTradeCount() {
		// TODO Auto-generated method stub
		return totTradeCount;
	}

	/**
	 * 設定成交總筆
	 * @param totTradeCount - int
	 */
	public void setTotTradeCount(int totTradeCount) {
		this.totTradeCount = totTradeCount;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXTrade#getTotInVolume()
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
	 * @see com.syt.jabx.watch.IJABXTrade#getTotOutVolume()
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
	 * 複製數據
	 * @return ZJABXTrade
	 */
	public ZJABXTrade copyData() {
		ZJABXTrade trade = new ZJABXTrade();
		trade.tradeTime = tradeTime;
		trade.tradeNote = tradeNote;
		trade.tradePrice = tradePrice;
		trade.tradeVolume = tradeVolume;
		trade.volumeInOutFlag = volumeInOutFlag;
		trade.totTradeVolume = totTradeVolume;
		trade.totTradeAmount = totTradeAmount;
		trade.totTradeCount = totTradeCount;
		trade.totInVolume = totInVolume;
		trade.totOutVolume = totOutVolume;
		return trade;
	}

	/**
	 * 將類別中所用到的欄位設定為零或空白
	 */
	public void setDataToZeroOrSpace() {
		tradeTime = 0;
		tradeNote = "";
		tradePrice = 0;
		tradeVolume = 0;
		volumeInOutFlag = 0;
		totTradeVolume = 0;
		totTradeAmount = 0;
		totTradeCount = 0;
		totInVolume = 0;
		totOutVolume = 0;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("SeqNo: ").append(seqNo).append(" ");
		sb.append("TradeTime: ").append(tradeTime).append(" ");
		sb.append("TradeNote: ").append(tradeNote).append(" ");
		sb.append("TradePrice: ").append(tradePrice).append(" ");
		sb.append("TradeVolume: ").append(tradeVolume).append(" ");
		sb.append("VolumeInOutFlag: ").append(volumeInOutFlag).append(" ");
		sb.append("TotTradeVolume: ").append(totTradeVolume).append(" ");
		sb.append("TotTradeAmount: ").append(totTradeAmount).append(" ");
		sb.append("TotTradeCount: ").append(totTradeCount).append(" ");
		sb.append("TotInVolume: ").append(totInVolume).append(" ");
		sb.append("TotOutVolume: ").append(totOutVolume).append(" ");
		return sb.toString();
	}
}
