package com.syt.jabx.kmodel;

/**
 * 總覧交易所基本資料之介面
 * @author jason
 *
 */
public interface IJABXExchangeInfoOverview {

	/**
	 * 依交易所代碼取得交易所基本資料介面(IJABXExchangeInfo)
	 * @param exchangeID - String(交易所代碼)
	 * @return IJABXExchangeInfo
	 */
	public IJABXExchangeInfo getExchangeInfo(String exchangeID);
}
