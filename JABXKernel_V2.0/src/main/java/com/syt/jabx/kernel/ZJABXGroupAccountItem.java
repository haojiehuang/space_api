package com.syt.jabx.kernel;

import com.syt.jabx.kmodel.IJABXGroupAccountItem;

/**
 * 群組交易帳號項目類別
 * @author Jason
 *
 */
final class ZJABXGroupAccountItem implements IJABXGroupAccountItem {

	/**
	 * 下單帳號(資料間以  | 分隔)
	 */
	private String accountData;

	/**
	 * 其他資料(資料間以 | 分隔)
	 */
	private String memo;

	/**
	 * @see com.syt.jabx.kmodel.IJABXGroupAccountItem#getAccountData()
	 */
	@Override
	public String getAccountData() {
		// TODO Auto-generated method stub
		return this.accountData;
	}

	/**
	 * 設定下單帳號(資料間以  | 分隔)
	 * @param accountData - String
	 */
	public void setAccountData(String accountData) {
		this.accountData = accountData;
	}

	/**
	 * @see com.syt.jabx.kmodel.IJABXGroupAccountItem#getMemo()
	 */
	@Override
	public String getMemo() {
		// TODO Auto-generated method stub
		return this.memo;
	}

	/**
	 * 設定其他資料(資料間以 | 分隔)
	 * @param memo - String
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
}