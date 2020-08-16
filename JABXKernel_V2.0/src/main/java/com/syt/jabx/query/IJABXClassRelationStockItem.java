package com.syt.jabx.query;

/**
 * 分類關連股票項目之介面
 * @author jason
 *
 */
public interface IJABXClassRelationStockItem {

	/**
	 * 取得股票全代碼
	 * @return String
	 */
	public String getStockID();

	/**
	 * 取得下單交易代碼
	 * @return String
	 */
	public String getTradeID();

	/**
	 * 取得報價代碼
	 * @return String
	 */
	public String getQuoteID();

	/**
	 * 取得契約年月
	 * @return String(yyMM)
	 */
	public String getSettleMonth();

	/**
	 * 取得履約價(取得的值為實際的履約價*10000的值，因此取得後要除以10000，才是實際的履約價)
	 * @return int
	 */
	public int getPrice();

	/**
	 * 取得買賣權(C:CALL, P:PUT)
	 * @return String
	 */
	public String getCallPut();

	/**
	 * 取得證券名稱
	 * @return String
	 */
	public String getName();

}
