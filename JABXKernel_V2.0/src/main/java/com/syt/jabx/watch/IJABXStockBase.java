package com.syt.jabx.watch;

/**
 * 股票基本資料之介面
 * @author Jason
 *
 */
public interface IJABXStockBase {

	/**
	 * 取得股票繁中名稱
	 * @return String
	 */
	public String getStkNameT();

	/**
	 * 取得股票簡中名稱
	 * @return String
	 */
	public String getStkNameS();

	/**
	 * 取得股票英文名稱
	 * @return String
	 */
	public String getStkNameE();

	/**
	 * 取得履約價
	 * @return int
	 */
	public int getStrikePrice();

	/**
	 * 取得商品代碼
	 * @return String
	 */
	public String getClassType();

	/**
	 * 取得商品群組代碼 
	 * @return int
	 */
	public int getClassGroupID();

	/**
	 * 取得產業代碼
	 * @return String
	 */
	public String getTradeType();

	/**
	 * 取得產業群組代碼
	 * @return int
	 */
	public int getTradeGroupID();

	/**
	 * 取得交易代號
	 * @return String
	 */
	public String getTradeCode();

	/**
	 * 取得小數位數或分數之分母
	 * @return int
	 */
	public int getDecimal();

	/**
	 * 取得證券屬性
	 * @return String
	 */
	public String getStkNote();

	/**
	 * 取得交易單位
	 * @return int
	 */
	public int getLotSize();

	/**
	 * 取得時區
	 * @return byte
	 */
	public byte getTimeZone();

	/**
	 * 取得開盤時間 
	 * @return short
	 */
	public short getOpenHHMM();

	/**
	 * 取得收盤時間 
	 * @return short
	 */
	public short getCloseHHMM();

	/**
	 * 設定休息時間1
	 * @return short
	 */
	public short getRestHHMM1();

	/**
	 * 取得再開盤時間1
	 * @return short
	 */
	public short getReOpenHHMM1();

	/**
	 * 取得休息時間2
	 * @return short
	 */
	public short getRestHHMM2();

	/**
	 * 取得再開盤時間2
	 * @return short
	 */
	public short getReOpenHHMM2();

	/**
	 * 取得盤前時間
	 * @return short
	 */
	public short getPreOpenHHMM();

	/**
	 * 取得檔差
	 * @return int
	 */
	public int getTickDiff();

	/**
	 * 取得交易幣別
	 * @return String
	 */
	public String getCurrencyCode();
}
