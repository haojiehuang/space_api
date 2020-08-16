package com.syt.jabx.kernel;

import com.syt.jabx.kmodel.IJABXUserEnvironmentContent;

/**
 * 用戶系統環境設定內容的類別
 * @author Jason
 *
 */
final class ZJABXUserEnvironmentContent implements IJABXUserEnvironmentContent {

	/**
	 * 用戶系統環境設定群組代碼
	 */
	private String groupID;

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
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentContent#getGroupID()
	 */
	@Override
	public String getGroupID() {
		// TODO Auto-generated method stub
		return groupID;
	}

	/**
	 * 設定用戶系統環境設定群組代碼
	 * @param groupID - String
	 */
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentContent#getGroupNo()
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
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentContent#getGroupName()
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
	 * @see com.syt.jabx.kmodel.user.IJABXUserEnvironmentContent#getContent()
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
