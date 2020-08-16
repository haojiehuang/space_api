package com.syt.jabx.kmodel;

/**
 * 警示設定項目之介面
 * @author jason
 *
 */
public interface IJABXWarningItem {

	/**
	 * 取得股票代碼
	 * @return String
	 */
	public String getStkID();

	/**
	 * 取得群組代碼
	 * @return int
	 */
	public int getGroupID();

	/**
	 * 取得警示值
	 * @return String
	 */
	public String getWarnValue();

	/**
	 * 取得警示時間(hhmm:定時警示, -1:盤中即時警示)
	 * @return int
	 */
	public int getWarnTime();

	/**
	 * 取得警示次數(0-不限定次數,&gt;0-限定次數(每次警示過後次數會被減1, 直到最後一次警示後此筆記錄會被刪除))
	 * @return int
	 */
	public int getWarnCount();

	/**
	 * 取得個人化設定旗標
	 * @return String
	 */
	public String getSetupFlag();

}
