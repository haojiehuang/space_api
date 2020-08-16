package com.syt.jabx.kmodel;

/**
 * 出入金帳戶之介面
 * @author jason
 *
 */
public interface IJABXWDAccount {

	/**
	 * 取得銀行代碼
	 * @return String
	 */
	public String getBankNo();

	/**
	 * 取得銀行帳號
	 * @return String
	 */
	public String getBankAccount();

	/**
	 * 取得幣別
	 * @return String
	 */
	public String getCurrency();

}