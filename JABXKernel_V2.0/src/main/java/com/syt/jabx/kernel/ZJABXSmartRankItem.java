package com.syt.jabx.kernel;

import com.syt.jabx.smartmsg.IJABXSmartRankItem;

/**
 * 即時排名資訊項目的類別
 * @author Jason
 *
 */
final class ZJABXSmartRankItem implements IJABXSmartRankItem {

	/**
	 * 即時排名分類代碼
	 */
	private String classID;

	/**
	 * 交易所代碼或區域代碼
	 */
	private String exchangeID;

	/**
	 * 證券代碼
	 */
	private String stockCode;

	/**
	 * 排名
	 */
	private int rank;

	/**
	 * 成交價
	 */
	private int tradePrice;

	/**
	 * 成交價漲趺
	 */
	private int priceType;

	/**
	 * 排名值
	 */
	private int rankValue;

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartRankItem#getClassID()
	 */
	@Override
	public String getClassID() {
		// TODO Auto-generated method stub
		return classID;
	}

	/**
	 * 設定即時排名分類代碼
	 * @param classID - String
	 */
	public void setClassID(String classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartRankItem#getExchangeID()
	 */
	@Override
	public String getExchangeID() {
		// TODO Auto-generated method stub
		return exchangeID;
	}

	/**
	 * 設定交易所代碼或區域代碼
	 * @param exchangeID - String
	 */
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartRankItem#getID()
	 */
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return stockCode;
	}

	/**
	 * 設定證券代碼
	 * @param stockCode - String
	 */
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartRankItem#getRank()
	 */
	@Override
	public int getRank() {
		// TODO Auto-generated method stub
		return rank;
	}

	/**
	 * 設定排名
	 * @param rank - int
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartRankItem#getTradePrice()
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
	 * @see com.syt.jabx.smartmsg.IJABXSmartRankItem#getPriceType()
	 */
	@Override
	public int getPriceType() {
		// TODO Auto-generated method stub
		return priceType;
	}

	/**
	 * 設定成交價漲趺
	 * @param priceType - int
	 */
	public void setPriceType(int priceType) {
		this.priceType = priceType;
	}

	/**
	 * @see com.syt.jabx.smartmsg.IJABXSmartRankItem#getRankValue()
	 */
	@Override
	public int getRankValue() {
		// TODO Auto-generated method stub
		return rankValue;
	}

	/**
	 * 設定排名值
	 * @param rankValue - int
	 */
	public void setRankValue(int rankValue) {
		this.rankValue = rankValue;
	}

}