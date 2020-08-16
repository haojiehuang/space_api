package com.syt.jabx.kmodel;

/**
 * 群組交易帳號項目介面
 * @author Jason
 *
 */
public interface IJABXGroupAccountItem {

	/**
	 * 取得下單帳號(資料間以  | 分隔)
	 * @return String
	 */
	public String getAccountData();

	/**
	 * 取得其他資料(資料間以 | 分隔)
	 * @return String
	 */
	public String getMemo();
}
