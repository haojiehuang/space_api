package com.syt.jabx.watch;

/**
 * 即時K類別
 * @author Jason
 *
 */
public interface IJABXRealtimeTechnical {

	/**
	 * 取得週期代碼
	 * @return int
	 */
	public int getPeriod();

	/**
	 * 取得週期之日期(格式為yyyyMMdd)
	 * @return int
	 */
	public int getPeriodDate();

	/**
	 * 取得週期之時間(格式為HHmm)
	 * @return int
	 */
	public int getPeriodTime();

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
	 * 取得開盤參考價
	 * @return int
	 */
	public int getRefOpenPrice();

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
}
