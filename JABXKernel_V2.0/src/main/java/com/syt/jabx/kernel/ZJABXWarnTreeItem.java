package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXWarnTreeItem;

/**
 * 警示樹子項目類別
 * @author jason
 *
 */
final class ZJABXWarnTreeItem implements IJABXWarnTreeItem {

	/**
	 * 群組代碼
	 */
	private String groupID;

	/**
	 * 群組名稱
	 */
	private String groupName;

	/**
	 * 分類種類
	 */
	private String classType;

	/**
	 * 警示預設值
	 */
	private String defaultValue;

	/**
	 * 警示預設時間(hhmm:預設時間，-1:盤中即時)
	 */
	private String defaultTime;

	/**
	 * 特定股票類型的可使用旗標X(4)
	 */
	private String specialStockFlag;

	/**
	 * 個人化設定旗標X(4)
	 */
	private String specialUserFlag;
	/**
	 * 警示備註
	 */
	private String mark;

	/**
	 * @see com.syt.jabx.query.IJABXWarnTreeItem#getGroupID()
	 */
	@Override
	public String getGroupID() {
		// TODO Auto-generated method stub
		return groupID;
	}

	/**
	 * 設定群組代碼
	 * @param groupID - String
	 */
	void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXWarnTreeItem#getGroupName()
	 */
	@Override
	public String getGroupName() {
		// TODO Auto-generated method stub
		return groupName;
	}

	/**
	 * 設定群組名稱
	 * @param groupName - String
	 */
	void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @see com.syt.jabx.query.IJABXWarnTreeItem#getClassType()
	 */
	@Override
	public String getClassType() {
		// TODO Auto-generated method stub
		return classType;
	}

	/**
	 * 設定分類種類
	 * @param classType - String
	 */
	void setClassType(String classType) {
		this.classType = classType;
	}

	/**
	 * @see com.syt.jabx.query.IJABXWarnTreeItem#getDefaultValue()
	 */
	public String getDefaultValue() {
		// TODO Auto-generated method stub
		return defaultValue;
	}

	/**
	 * 設定警示預設值
	 * @param defaultValue - String
	 */
	void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @see com.syt.jabx.query.IJABXWarnTreeItem#getDefaultTime()
	 */
	public String getDefaultTime() {
		// TODO Auto-generated method stub
		return defaultTime;
	}

	/**
	 * 設定警示預設時間(hhmm:預設時間，-1:盤中即時)
	 * @param defaultTime - String
	 */
	void setDefaultTime(String defaultTime) {
		this.defaultTime = defaultTime;
	}

	/**
	 * @see com.syt.jabx.query.IJABXWarnTreeItem#getSpecialStockFlag()
	 */
	public String getSpecialStockFlag() {
		// TODO Auto-generated method stub
		return specialStockFlag;
	}

	/**
	 * 設定特定股票類型的可使用旗標X(4)
	 * @param specialStockFlag - String
	 */
	void setSpecialStockFlag(String specialStockFlag) {
		this.specialStockFlag = specialStockFlag;
	}

	/**
	 * @see com.syt.jabx.query.IJABXWarnTreeItem#getSpecialUserFlag()
	 */
	public String getSpecialUserFlag() {
		// TODO Auto-generated method stub
		return specialUserFlag;
	}

	/**
	 * 設定個人化設定旗標X(4)
	 * @param specialUserFlag - String
	 */
	void setSpecialUserFlag(String specialUserFlag) {
		this.specialUserFlag = specialUserFlag;
	}

	/**
	 * @see com.syt.jabx.query.IJABXWarnTreeItem#getMark()
	 */
	@Override
	public String getMark() {
		// TODO Auto-generated method stub
		return mark;
	}

	/**
	 * 設定警示註釋
	 * @param mark - String
	 */
	void setMark(String mark) {
		this.mark = mark;
	}
}