package com.syt.jabx.query;

/**
 * 股票歷史報價數據(K線)的介面
 * @author Jason
 *
 */
public interface IJABXHistoryQuoteData {

	/**
	 * 取得證券全代碼
	 * @return String
	 */
	public String getID();

	/**
	 * 取得股票歷史報價的周期代碼<br>
	 * 周期代碼如下所示:<br>
	 * ABX_PRICEDATA_DAY: 日線<br>
	 * ABX_PRICEDATA_WEEK: 周線<br>
	 * ABX_PRICEDATA_MONTH: 月線<br>
	 * ABX_PRICEDATA_QUARTER: 季線<br>
	 * ABX_PRICEDATA_HYEAR: 半年線<br>
	 * ABX_PRICEDATA_YEAR: 年線<br>
	 * ABX_PRICEDATA_MIN: 1分線<br>
	 * ABX_PRICEDATA_MIN5: 5分線<br>
	 * ABX_PRICEDATA_MIN10: 10分線<br>
	 * ABX_PRICEDATA_MIN15: 15分線<br>
	 * ABX_PRICEDATA_MIN20: 20分線<br>
	 * ABX_PRICEDATA_MIN30: 30分線<br>
	 * ABX_PRICEDATA_MIN60: 60分線<br>
	 * ABX_PRICEDATA_FRBASE: 往前復權基準值<br>
	 * ABX_PRICEDATA_BRBASE: 往後復權基準值<br>
	 * <br>
	 * 復權需加上復權基準值，限日線至60分線使用。<br>
	 * ex: 往前復權日線: ABX_PRICEDATA_DAY | ABX_PRICEDATA_FRBASE。<br>
	 * ex: 往後復權日線: ABX_PRICEDATA_DAY | ABX_PRICEDATA_BRBASE。
	 * @return int
	 */
	public int getPeriod();

	/**
	 * 取得股票歷史報價數據總筆數
	 * @return int
	 */
	public int getDataTotalCount();

	/**
	 * 取得股票歷史報價數據<br>
	 * 格式為："資料時間(YYYYMMDD[hhmm])|開盤價|最高價|最低價|收盤價|開盤參考價|成交量|成交金額|漲停跌符號|換手率;..."<br>
	 * ，各筆間以分號分隔，各欄位以｜分隔。
	 * @return String
	 */
	public String getData();

}
