package com.syt.jabx.kernel;

import com.syt.jabx.notify.IJABXLog;

/**
 * 交易及帳務工具類別
 * @author jason
 *
 */
public class JABXTradeBillsTool {

	/**
	 * 交易及帳務資訊請求物件
	 */
	private IJABXTradeBillsRequest tradeBillsRequest;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 */
	public JABXTradeBillsTool(JABXKernel jabxKernel, IJABXLog jabxLog) {
		tradeBillsRequest = new ZJABXTradeBillsRequest(jabxKernel, jabxLog);
	}

	/**
	 * 取得交易及帳務資訊請求物件
	 * @return IJABXTradeBillsRequest
	 */
	public IJABXTradeBillsRequest getRequest() {
		return this.tradeBillsRequest;
	}
}
