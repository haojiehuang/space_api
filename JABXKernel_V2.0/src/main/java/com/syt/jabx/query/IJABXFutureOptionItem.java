package com.syt.jabx.query;

/**
 * 期權股名項目介面
 * @author jason
 *
 */
public interface IJABXFutureOptionItem {

	/**
	 * 取得證券全代碼
	 * @return String
	 */
	public String getStkID();

	/**
	 * 取得交易所代碼
	 * @return String
	 */
	public String getExchangeID();

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
	 * 取得履約價(原始履約價*10000)
	 * @return int
	 */
	public String getPrice();

	/**
	 * 取得買賣權(C:CALL, P:PUT)
	 * @return String
	 */
	public String getCallPut();

	/**
	 * 取得股票名稱(Example: 台指期1607, 電子指選 1607 265 C)
	 * @return String
	 */
	public String getName();
}
