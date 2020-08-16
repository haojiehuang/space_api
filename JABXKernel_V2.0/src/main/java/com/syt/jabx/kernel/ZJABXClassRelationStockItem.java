package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXClassRelationStockItem;

/**
 * 分類關連股票項目之類別
 * @author jason
 *
 */
final class ZJABXClassRelationStockItem implements IJABXClassRelationStockItem {

	/**
	 * 證券全代碼
	 */
	private String stockID;

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
	private int price;

	/**
	 * 買賣權(C:CALL, P:PUT)
	 */
	private String callPut;

	/**
	 * 證券名稱
	 */
	private String name;

	/**
	 * @see com.syt.jabx.query.IJABXClassRelationStockItem#getStockID()
	 */
	@Override
	public String getStockID() {
		// TODO Auto-generated method stub
		return stockID;
	}

	/**
	 * 設定股票全代碼
	 * @param stockID - String
	 */
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassRelationStockItem#getTradeID()
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
	 * @see com.syt.jabx.query.IJABXClassRelationStockItem#quoteID()
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
	 * @see com.syt.jabx.query.IJABXClassRelationStockItem#getSettleMonth()
	 */
	@Override
	public String getSettleMonth() {
		// TODO Auto-generated method stub
		return settleMonth;
	}

	/**
	 * 設定契約年月
	 * @param settleMonth - String
	 */
	public void setSettleMonth(String settleMonth) {
		this.settleMonth = settleMonth;
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassRelationStockItem#getPrice()
	 */
	@Override
	public int getPrice() {
		// TODO Auto-generated method stub
		return price;
	}

	/**
	 * 設定履約價
	 * @param price
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassRelationStockItem#getCallPut()
	 */
	@Override
	public String getCallPut() {
		// TODO Auto-generated method stub
		return callPut;
	}

	/**
	 * 設定買賣權(C:CALL, P:PUT)
	 * @param callPut - sTRING
	 */
	public void setCallPut(String callPut) {
		this.callPut = callPut;
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassRelationStockItem#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * 設定證券名稱
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}
}
