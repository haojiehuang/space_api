package com.syt.jabx.kernel;

import org.json.JSONObject;

/**
 * 交易及帳務資訊請求介面
 * @author jason
 *
 */
public interface IJABXTradeBillsRequest {

	/**
	 * 交易及帳務(B或T開頭的功能)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param funcCode - String(功能代碼)
	 * @param param - JSONObject
	 */
	public void tradeBills(int requestID, String funcCode, JSONObject param);
}
