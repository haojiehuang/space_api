package com.syt.jabx.kmodel;

import java.util.List;

/**
 * 用戶資訊總覧之介面
 * @author jason
 *
 */
public interface IJABXUserInfoOverview {

	/**
	 * 取得身分證號
	 * @return String
	 */
	public String getIdNo();

	/**
	 * 取得客戶名稱
	 * @return String
	 */
	public String getUserName(); 

	/**
	 * 取得身份別(戶別)
	 * @return String
	 */
	public String getUserType();

	/**
	 * 取得出生日期(yyyyMMdd)
	 * @return int
	 */
	public int getBirthday();

	/**
	 * 取得性別
	 * @return String
	 */
	public String getSex();

	/**
	 * 取得用戶下單帳號資訊項目之列表
	 * @return List&lt;IJABXUserAccountInfoItem&gt;
	 */
	public List<IJABXUserAccountInfoItem> getAccountInfoList();
}
