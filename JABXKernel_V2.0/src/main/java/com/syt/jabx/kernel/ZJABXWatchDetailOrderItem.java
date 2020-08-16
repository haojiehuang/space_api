package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXDetailOrderItem;

/**
 * 逐筆委託訊息(目前只support SZSE)之類別
 * @author Jason
 *
 */
final class ZJABXWatchDetailOrderItem implements IJABXDetailOrderItem {

	/**
	 * 成交序號
	 */
	private int seqNo;

	/**
	 * 交易所委託序號
	 */
	private int marketSeqNo;

	/**
	 * 委託時間(HHmmsssss,後3sss 為 mini sec)
	 */
	private int tradeTime;

	/**
	 * 委託價
	 */
	private int tradePrice;

	/**
	 * 委託量(SZSE單位為股)
	 */
	private int tradeVolume;

	/**
	 * 委託類別(SZSE才有)
	 */
	private String orderType;

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
	 * @see com.syt.jabx.watch.IJABXDetailOrderItem#getMarketSeqNo()
	 */
	@Override
	public int getMarketSeqNo() {
		// TODO Auto-generated method stub
		return marketSeqNo;
	}

	/**
	 * 設定交易所委託序號
	 * @param marketSeqNo - int
	 */
	public void setMarketSeqNo(int marketSeqNo) {
		this.marketSeqNo = marketSeqNo;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXDetailOrderItem#getTradeTime()
	 */
	@Override
	public int getTradeTime() {
		// TODO Auto-generated method stub
		return tradeTime;
	}

	/**
	 * 設定委託時間(HHmmsssss,後3sss 為 mini sec)
	 * @param tradeTime - int
	 */
	public void setTradeTime(int tradeTime) {
		this.tradeTime = tradeTime;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXDetailOrderItem#getTradePrice()
	 */
	@Override
	public int getTradePrice() {
		// TODO Auto-generated method stub
		return tradePrice;
	}

	/**
	 * 設定委託價
	 * @param tradePrice - int
	 */
	public void setTradePrice(int tradePrice) {
		this.tradePrice = tradePrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXDetailOrderItem#getTradeVolume()
	 */
	@Override
	public int getTradeVolume() {
		// TODO Auto-generated method stub
		return tradeVolume;
	}

	/**
	 * 設定委託量(SZSE單位為股)
	 * @param tradeVolume - int
	 */
	public void setTradeVolume(int tradeVolume) {
		this.tradeVolume = tradeVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXDetailOrderItem#getOrderType()
	 */
	@Override
	public String getOrderType() {
		// TODO Auto-generated method stub
		return orderType;
	}

	/**
	 * 設定委託類別(SZSE才有)
	 * @param orderType - String
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * 複製數據
	 * @return ZJABXDetailOrder
	 */
	public ZJABXWatchDetailOrderItem copyData() {
		ZJABXWatchDetailOrderItem detailOrder = new ZJABXWatchDetailOrderItem();
		detailOrder.seqNo = seqNo;
		detailOrder.marketSeqNo = marketSeqNo;
		detailOrder.tradeTime = tradeTime;
		detailOrder.tradePrice = tradePrice;
		detailOrder.tradeVolume = tradeVolume;
		detailOrder.orderType = orderType;
		return detailOrder;
	}

}
