package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXFutureOptionItem;

/**
 * 期權股名項目類別
 * @author jason
 *
 */
final class ZJABXFutureOptionItem implements IJABXFutureOptionItem {

	/**
	 * 股票全代碼
	 */
	private String stkID;

	/**
	 * 下單交易代碼
	 */
	private String tradeID;

	/**
	 * 報價代碼
	 */
	private String quoteID;

	/**
	 * 契約年月
	 */
	private String settleMonth;

	/**
	 * 履約價
	 */
	private String price;

	/**
	 * 買賣權(C:CALL, P:PUT)
	 */
	private String callPut;

	/**
	 * 股票名稱
	 */
	private String name;

	/**
	 * @see com.syt.jabx.query.IJABXFutureOptionItem#getStkID()
	 */
	@Override
	public String getStkID() {
		// TODO Auto-generated method stub
		return stkID;
	}

	/**
	 * 設定股票全代碼
	 * @param stkID - String
	 */
	public void setStkID(String stkID) {
		this.stkID = stkID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXFutureOptionItem#getExchangeID()
	 */
	@Override
	public String getExchangeID() {
		// TODO Auto-generated method stub
		return stkID.substring(0, 2);
	}

	/**
	 * @see com.syt.jabx.query.IJABXFutureOptionItem#getTradeID()
	 */
	@Override
	public String getTradeID() {
		// TODO Auto-generated method stub
		return tradeID;
	}

	/**
	 * 設定下單交易代碼
	 * @param tradeID - String
	 */
	public void setTradeID(String tradeID) {
		this.tradeID = tradeID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXFutureOptionItem#getQuoteID()
	 */
	@Override
	public String getQuoteID() {
		// TODO Auto-generated method stub
		return quoteID;
	}

	/**
	 * 設定報價代碼
	 * @param quoteID - String
	 */
	public void setQuoteID(String quoteID) {
		this.quoteID = quoteID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXFutureOptionItem#getSettleMonth()
	 */
	@Override
	public String getSettleMonth() {
		// TODO Auto-generated method stub
		return settleMonth;
	}

	/**
	 * 設定契約年月
	 * @param settleMonth - int
	 */
	public void setSettleMonth(String settleMonth) {
		this.settleMonth = settleMonth;
	}

	/**
	 * @see com.syt.jabx.query.IJABXFutureOptionItem#getPrice()
	 */
	@Override
	public String getPrice() {
		// TODO Auto-generated method stub
		return price;
	}

	/**
	 * 設定履約價
	 * @param price - String
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @see com.syt.jabx.query.IJABXFutureOptionItem#getCallPut()
	 */
	@Override
	public String getCallPut() {
		// TODO Auto-generated method stub
		return callPut;
	}

	/**
	 * 設定買賣權(C:CALL, P:PUT)
	 * @param callPut - String
	 */
	public void setCallPut(String callPut) {
		this.callPut = callPut;
	}

	/**
	 * @see com.syt.jabx.query.IJABXServerItem#getExchangeIDCollection()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * 設定股票名稱
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}
}
