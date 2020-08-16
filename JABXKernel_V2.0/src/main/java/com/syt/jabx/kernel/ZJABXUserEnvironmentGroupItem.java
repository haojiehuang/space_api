package com.syt.jabx.kernel;

import com.syt.jabx.kmodel.IJABXUserEnvironmentGroupItem;

/**
 * 用戶系統環境設定群組項目的類別
 * @author Jason
 *
 */
final class ZJABXUserEnvironmentGroupItem implements IJABXUserEnvironmentGroupItem {

	/**
	 * 用戶系統環境設定群組序號
	 */
	private int groupNo;

	/**
	 * 用戶系統環境設定群組名稱
	 */
	private String groupName;

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentGroupItem#getGroupNo()
	 */
	@Override
	public int getGroupNo() {
		// TODO Auto-generated method stub
		return groupNo;
	}

	/**
	 * 設定用戶系統環境設定群組序號
	 * @param groupNo - int
	 */
	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentGroupItem#getGroupName()
	 */
	@Override
	public String getGroupName() {
		// TODO Auto-generated method stub
		return groupName;
	}

	/**
	 * 設定用戶系統環境設定群組名稱
	 * @param groupName - String
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
