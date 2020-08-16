package com.syt.jabx.kernel;

import java.util.List;

import com.syt.jabx.kmodel.IJABXUserAccountInfoItem;
import com.syt.jabx.kmodel.IJABXUserInfoOverview;

/**
 * 用戶資訊總覧之類別
 * @author jason
 *
 */
final class ZJABXUserInfoOverview implements IJABXUserInfoOverview {

	/**
	 * 身分證號
	 */
	private String idNo;

	/**
	 * 客戶名稱
	 */
	private String userName;

	/**
	 * 身份別(戶別)
	 */
	private String userType;

	/**
	 * 生日(yyyyMMdd)
	 */
	private int birthday;

	/**
	 * 性別
	 */
	private String sex;

	/**
	 * 用戶下單帳號資訊項目列表
	 */
	private List<IJABXUserAccountInfoItem> accountInfoList;

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserInfoOverview#getIdNo()
	 */
	@Override
	public String getIdNo() {
		// TODO Auto-generated method stub
		return idNo;
	}

	/**
	 * 設定身分證號
	 * @param idNo - String
	 */
	void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserInfoOverview#getUserName()
	 */
	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return userName;
	}

	/**
	 * 設定客戶名稱
	 * @param userName - String
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserInfoOverview#getUserType()
	 */
	@Override
	public String getUserType() {
		// TODO Auto-generated method stub
		return userType;
	}

	/**
	 * 設定身份別(戶別)
	 * @param userType - String
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserInfoOverview#getBirthday()
	 */
	@Override
	public int getBirthday() {
		// TODO Auto-generated method stub
		return birthday;
	}

	/**
	 * 設定生日
	 * @param birthday - int
	 */
	public void setBirthday(int birthday) {
		this.birthday = birthday;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserInfoOverview#getSex()
	 */
	@Override
	public String getSex() {
		// TODO Auto-generated method stub
		return sex;
	}

	/**
	 * 設定性別
	 * @param sex - String
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserInfoOverview#getAccountInfoList()
	 */
	@Override
	public List<IJABXUserAccountInfoItem> getAccountInfoList() {
		// TODO Auto-generated method stub
		return accountInfoList;
	}

	/**
	 * 設定用戶下單帳號資訊項目列表
	 * @param accountInfoList - List&lt;IJABXUserAccountInfoItem&gt;
	 */
	public void setAccountInfoList(List<IJABXUserAccountInfoItem> accountInfoList) {
		this.accountInfoList = accountInfoList;
	}
}
