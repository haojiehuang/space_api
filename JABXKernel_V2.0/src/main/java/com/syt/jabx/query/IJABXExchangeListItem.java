package com.syt.jabx.query;

/**
 * 交易所列表項目之介面
 * @author Jason
 *
 */
public interface IJABXExchangeListItem {

	/**
	 * 取得交易所代碼
	 * @return String
	 */
	public String getID();

	/**
	 * 取得交易所名稱
	 * @return String
	 */
	public String getName();

	/**
	 * 取得交易所屬性
	 * @return String
	 */
	public String getAttribute();
}
