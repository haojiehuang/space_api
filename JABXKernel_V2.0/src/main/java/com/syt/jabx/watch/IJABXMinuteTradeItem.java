package com.syt.jabx.watch;

/**
 * 股票成交分鐘明細項目的介面
 * @author Jason
 *
 */
public interface IJABXMinuteTradeItem {

	/**
	 * 取得成交日期(格式為yyyyMMdd)
	 * @return String
	 */
	public String getTradeDate();

	/**
	 * 取得成交時間(格式為HHmm)
	 * @return String
	 */
	public String getTradeTime();

	/**
	 * 取得開盤價
	 * @return int
	 */
	public int getOpenPrice();

	/**
	 * 取得最高價
	 * @return int
	 */
	public int getHighPrice();

	/**
	 * 取得最低價
	 * @return int
	 */
	public int getLowPrice();

	/**
	 * 取得成交價
	 * @return int
	 */
	public int getTradePrice();

	/**
	 * 取得成交量
	 * @return long
	 */
	public long getTradeVolume();

	/**
	 * 取得成交金額
	 * @return long
	 */
	public long getTradeAmount();

	/**
	 * 取得換手率
	 * @return String
	 */
	public String getChangeRate();

	/**
	 * 取得總量
	 * @return long
	 */
	public long getTotalVolume();

	/**
	 * 取得總金額
	 * @return long
	 */
	public long getAmount();
}
