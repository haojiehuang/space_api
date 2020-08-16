package com.syt.jabx.kernel;

import com.syt.jabx.kmodel.IJABXWDAccount;

/**
 * 出入金帳戶之類別
 * @author jason
 *
 */
final class ZJABXWDAccount implements IJABXWDAccount {

	/**
	 * 銀行代碼
	 */
	private String bankNo;

	/**
	 * 銀行帳號
	 */
	private String bankAccount;

	/**
	 * 幣別
	 */
	private String currency;

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXWDAccount#getBankNo()
	 */
	@Override
	public String getBankNo() {
		// TODO Auto-generated method stub
		return bankNo;
	}

	/**
	 * 設定銀行代碼
	 * @param bankNo - String
	 */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXWDAccount#getBankAccount()
	 */
	@Override
	public String getBankAccount() {
		// TODO Auto-generated method stub
		return bankAccount;
	}

	/**
	 * 設定銀行帳號
	 * @param bankAccount - String
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXWDAccount#getCurrency()
	 */
	@Override
	public String getCurrency() {
		// TODO Auto-generated method stub
		return currency;
	}

	/**
	 * 設定幣別
	 * @param currency - String
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
