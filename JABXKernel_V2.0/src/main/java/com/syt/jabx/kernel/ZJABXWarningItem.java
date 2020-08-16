package com.syt.jabx.kernel;

import com.syt.jabx.kmodel.IJABXWarningItem;

/**
 * 警示設定項目之類別
 * @author jason
 *
 */
final class ZJABXWarningItem implements IJABXWarningItem {

	/**
	 * 股票代碼
	 */
	private String stkID;

	/**
	 * 群組代碼
	 */
	private int groupID;

	/**
	 * 警示值
	 */
	private String warnValue;

	/**
	 * 警示時間(hhmm:定時警示, -1:盤中即時警示)
	 */
	private int warnTime;

	/**
	 * 警示次數(0-不限定次數,>0-限定次數(每次警示過後次數會被減1, 直到最後一次警示後此筆記錄會被刪除))
	 */
	private int warnCount;

	/**
	 * 個人化設定旗標
	 */
	private String setupFlag;

	/**
	 * @see com.syt.jabx.kmodel.query.IJABXWarningItem#getStkID()
	 */
	@Override
	public String getStkID() {
		// TODO Auto-generated method stub
		return stkID;
	}

	/**
	 * 設定股票代碼
	 * @param stkID - String
	 */
	public void setStkID(String stkID) {
		this.stkID = stkID;
	}

	/**
	 * @see com.syt.jabx.kmodel.query.IJABXWarningItem#getGroupID()
	 */
	@Override
	public int getGroupID() {
		// TODO Auto-generated method stub
		return groupID;
	}

	/**
	 * 設定群組代碼
	 * @param groupID - int
	 */
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	/**
	 * @see com.syt.jabx.kmodel.query.IJABXWarningItem#getWarnValue()
	 */
	@Override
	public String getWarnValue() {
		// TODO Auto-generated method stub
		return warnValue;
	}

	/**
	 * 設定警示值
	 * @param warnValue - String
	 */
	public void setWarnValue(String warnValue) {
		this.warnValue = warnValue;
	}

	/**
	 * @see com.syt.jabx.kmodel.query.IJABXWarningItem#getWarnTime()
	 */
	@Override
	public int getWarnTime() {
		// TODO Auto-generated method stub
		return warnTime;
	}

	/**
	 * 設定警示時間(hhmm:定時警示, -1:盤中即時警示)
	 * @param warnTime - int(HHmm)
	 */
	public void setWarnTime(int warnTime) {
		this.warnTime = warnTime;
	}

	/**
	 * @see com.syt.jabx.kmodel.query.IJABXWarningItem#getWarnCount()
	 */
	@Override
	public int getWarnCount() {
		// TODO Auto-generated method stub
		return warnCount;
	}

	/**
	 * 設定警示次數(0-不限定次數,>0-限定次數(每次警示過後次數會被減1, 直到最後一次警示後此筆記錄會被刪除))
	 * @param warnCount - int
	 */
	public void setWarnCount(int warnCount) {
		this.warnCount = warnCount;
	}

	/**
	 * @see com.syt.jabx.kmodel.query.IJABXWarningItem#getSetupFlag()
	 */
	@Override
	public String getSetupFlag() {
		// TODO Auto-generated method stub
		return setupFlag;
	}

	/**
	 * 設定個人化設定旗標
	 * @param setupFlag - String
	 */
	public void setSetupFlag(String setupFlag) {
		this.setupFlag = setupFlag;
	}
}
