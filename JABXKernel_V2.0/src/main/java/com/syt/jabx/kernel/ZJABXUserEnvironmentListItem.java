package com.syt.jabx.kernel;

import com.syt.jabx.kmodel.IJABXUserEnvironmentListItem;

/**
 * 用戶系統環境設定列表項目的類別
 * @author Jason
 *
 */
final class ZJABXUserEnvironmentListItem implements IJABXUserEnvironmentListItem {

	/**
	 * 用戶系統環境設定群組序號
	 */
	private int groupNo;

	/**
	 * 用戶系統環境設定群組名稱
	 */
	private String groupName;

	/**
	 * 用戶系統環境設定內容
	 */
	private String content;

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentListItem#getGroupNo()
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
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentListItem#getGroupName()
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

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentListItem#getContent()
	 */
	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return content;
	}

	/**
	 * 設定用戶系統環境設定內容
	 * @param content - String
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
