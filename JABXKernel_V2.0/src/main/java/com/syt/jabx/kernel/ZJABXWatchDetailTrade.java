package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXDetailTrade;

/**
 * 即時逐筆成交訊息(目前只support SSE,SZSE)之類別
 * @author Jason
 *
 */
final class ZJABXWatchDetailTrade implements IJABXDetailTrade {

	/**
	 * 成交序號
	 */
	private int seqNo;

	/**
	 * 交易所成交序號
	 */
	private int marketSeqNo;

	/**
	 * 成交時間(HHmmsssss,後3sss 為 mini sec)
	 */
	private int tradeTime;

	/**
	 * 成交價
	 */
	private int tradePrice;

	/**
	 * 成交單量(SSE,SZSE單位為股)
	 */
	private int tradeVolume;

	/**
	 * 成交類別(SZSE才有)
	 */
	private String tradeType;

	/**
	 * 逐筆交易所委買序號(SZSE才有,Server自行統計)
	 */
	private int bidNo;

	/**
	 * 逐筆交易所委賣序號(SZSE才有,Server自行統計)
	 */
	private int askNo;

	/**
	 * 取得成交序號
	 * @return int
	 */
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
	 * @see com.syt.jabx.watch.IJABXDetailTradeItem#getMarketSeqNo()
	 */
	@Override
	public int getMarketSeqNo() {
		// TODO Auto-generated method stub
		return marketSeqNo;
	}

	/**
	 * 設定交易所成交序號
	 * @param marketSeqNo - int
	 */
	public void setMarketSeqNo(int marketSeqNo) {
		this.marketSeqNo = marketSeqNo;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXDetailTradeItem#getTradeTime()
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
	 * @see com.syt.jabx.watch.IJABXDetailTradeItem#getTradePrice()
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
	 * @see com.syt.jabx.watch.IJABXDetailTradeItem#getTradeVolume()
	 */
	@Override
	public int getTradeVolume() {
		// TODO Auto-generated method stub
		return tradeVolume;
	}

	/**
	 * 設定成交單量(SSE,SZSE單位為股)
	 * @param tradeVolume - int
	 */
	public void setTradeVolume(int tradeVolume) {
		this.tradeVolume = tradeVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXDetailTradeItem#getTradeType()
	 */
	@Override
	public String getTradeType() {
		// TODO Auto-generated method stub
		return tradeType;
	}

	/**
	 * 設定成交類別(SZSE才有)
	 * @param tradeType - String
	 */
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXDetailTradeItem#getBidNo()
	 */
	@Override
	public int getBidNo() {
		// TODO Auto-generated method stub
		return bidNo;
	}

	/**
	 * 設定逐筆交易所委買序號(SZSE才有,Server自行統計)
	 * @param bidNo - int
	 */
	public void setBidNo(int bidNo) {
		this.bidNo = bidNo;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXDetailTradeItem#getAskNo()
	 */
	@Override
	public int getAskNo() {
		// TODO Auto-generated method stub
		return askNo;
	}

	/**
	 * 設定逐筆交易所委賣序號(SZSE才有,Server自行統計)
	 * @param askNo - int
	 */
	public void setAskNo(int askNo) {
		this.askNo = askNo;
	}

	/**
	 * 複製數據
	 * @return ZJABXDetailTrade
	 */
	public ZJABXWatchDetailTrade copyData() {
		ZJABXWatchDetailTrade detailTrade = new ZJABXWatchDetailTrade();
		detailTrade.seqNo = seqNo;
		detailTrade.marketSeqNo = marketSeqNo;
		detailTrade.tradeTime = tradeTime;
		detailTrade.tradePrice = tradePrice;
		detailTrade.tradeVolume = tradeVolume;
		detailTrade.tradeType = tradeType;
		detailTrade.bidNo = bidNo;
		detailTrade.askNo = askNo;
		return detailTrade;
	}

	/**
	 * 將類別中所用到的欄位設定為零或空白
	 */
	public void setDataToZeroOrSpace() {
		seqNo = 0;
		marketSeqNo = 0;
		tradeTime = 0;
		tradePrice = 0;
		tradeVolume = 0;
		tradeType = "";
		bidNo = 0;
		askNo = 0;
	}
}
