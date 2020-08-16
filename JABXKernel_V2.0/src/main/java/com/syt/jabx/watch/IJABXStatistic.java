package com.syt.jabx.watch;

/**
 * 即時交易所統計訊息的介面
 * @author Jason
 *
 */
public interface IJABXStatistic {

	/**
	 * 取得總計時間(HHmm)
	 * @return int
	 */
	public int getTradeTime();

	/**
	 * 取得漲停家數
	 * @return int
	 */
	public int getUpStopNum();

	/**
	 * 取得跌停家數
	 * @return int
	 */
	public int getDownStopNum();

	/**
	 * 取得上漲家數
	 * @return int
	 */
	public int getUpNum();

	/**
	 * 取得下漲家數
	 * @return int
	 */
	public int getDownNum();

	/**
	 * 取得平盤家數
	 * @return int
	 */
	public int getEqualNum();

	/**
	 * 取得未成交家數
	 * @return int
	 */
	public int getNoTradeNum();
}
