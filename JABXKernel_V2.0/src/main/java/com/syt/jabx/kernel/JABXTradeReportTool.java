package com.syt.jabx.kernel;

import com.syt.jabx.notify.IJABXLog;

/**
 * 委託及成交即時回報工具類別
 * @author jason
 *
 */
public class JABXTradeReportTool {

	/**
	 * 即時交易回報資訊請求物件
	 */
	private IJABXTradeReportRequest tradeReportRequest;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 */
	public JABXTradeReportTool(JABXKernel jabxKernel, IJABXLog jabxLog) {
		tradeReportRequest = new ZJABXTradeReportRequest(jabxKernel, jabxLog);
	}

	/**
	 * 取得即時交易回報資訊請求物件
	 * @return IJABXTradeReportRequest
	 */
	public IJABXTradeReportRequest getRequest() {
		return this.tradeReportRequest;
	}
}
