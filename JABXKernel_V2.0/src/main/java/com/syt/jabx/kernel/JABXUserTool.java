package com.syt.jabx.kernel;

import org.json.JSONArray;
import org.json.JSONObject;

import com.syt.jabx.consts.JS_UserInfo;
import com.syt.jabx.notify.IJABXLog;

/**
 * 用戶相關工具類別
 * @author jason
 *
 */
public class JABXUserTool {

	/**
	 * 用戶資訊請求物件
	 */
	private IJABXUserRequest userRequest;

	/**
	 * 用戶登入訊息的物件
	 */
	private volatile JSONObject userMsg;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 */
	public JABXUserTool(JABXKernel jabxKernel, IJABXLog jabxLog) {
		userRequest = new ZJABXUserRequest(jabxKernel, jabxLog);
		userMsg = new JSONObject();
	}

	/**
	 * 取得用戶資訊請求物件
	 * @return IJABXUserRequest
	 */
	public IJABXUserRequest getRequest() {
		return this.userRequest;
	}

	/**
	 * 取得用戶訊息之物件
	 * @return JSONObject
	 */
	public JSONObject getUserInfo() {
		if (userMsg == null || userMsg.isEmpty()) {
			return new JSONObject();
		}
		JSONObject userInfo = new JSONObject();
		if (!userMsg.isNull(JS_UserInfo.F_EXCHANGEID_LIST)) {
			JSONArray srcIDList = userMsg.getJSONArray(JS_UserInfo.F_EXCHANGEID_LIST);
			JSONArray exchangeIDList = new JSONArray();
			String exchangeID;
			for (int i = 0, length = srcIDList.length();i < length;i++) {
				exchangeID = (String)srcIDList.get(i);
				exchangeIDList.put(exchangeID);
			}
			userInfo.put(JS_UserInfo.F_EXCHANGEID_LIST, exchangeIDList);
		}
		if (!userMsg.isNull(JS_UserInfo.F_ACCOUNT_LIST)) {
			JSONArray srcList = userMsg.getJSONArray(JS_UserInfo.F_ACCOUNT_LIST);
			JSONArray accList = new JSONArray();
			JSONObject accItem, tarItem;
			String tmpStr;
			for (int i = 0, length = srcList.length();i < length;i++) {
				accItem = (JSONObject)srcList.get(i);
				tarItem = new JSONObject();
				if (!accItem.isNull(JS_UserInfo.I_ACCOUNT)) {
					tmpStr = accItem.getString(JS_UserInfo.I_ACCOUNT);
					tarItem.put(JS_UserInfo.I_ACCOUNT, tmpStr);
				}
				if (!accItem.isNull(JS_UserInfo.I_MEMO)) {
					tmpStr = accItem.getString(JS_UserInfo.I_MEMO);
					tarItem.put(JS_UserInfo.I_MEMO, tmpStr);
				}
				accList.put(tarItem);
			}
			userInfo.put(JS_UserInfo.F_ACCOUNT_LIST, accList);
		}
		if (!userMsg.isNull(JS_UserInfo.F_GROUP_ACCOUNT_LIST)) {
			JSONArray srcList = userMsg.getJSONArray(JS_UserInfo.F_GROUP_ACCOUNT_LIST);
			JSONArray accList = new JSONArray();
			JSONObject accItem, tarItem;
			String tmpStr;
			for (int i = 0, length = srcList.length();i < length;i++) {
				accItem = (JSONObject)srcList.get(i);
				tarItem = new JSONObject();
				if (!accItem.isNull(JS_UserInfo.I_ACCOUNT)) {
					tmpStr = accItem.getString(JS_UserInfo.I_ACCOUNT);
					tarItem.put(JS_UserInfo.I_ACCOUNT, tmpStr);
				}
				if (!accItem.isNull(JS_UserInfo.I_MEMO)) {
					tmpStr = accItem.getString(JS_UserInfo.I_MEMO);
					tarItem.put(JS_UserInfo.I_MEMO, tmpStr);
				}
				accList.put(tarItem);
			}
			userInfo.put(JS_UserInfo.F_GROUP_ACCOUNT_LIST, accList);
		}
		if (!userMsg.isNull(JS_UserInfo.F_SYSTEM_DATE)) {
			userInfo.put(JS_UserInfo.F_SYSTEM_DATE, userMsg.get(JS_UserInfo.F_SYSTEM_DATE));
		}
		if (!userMsg.isNull(JS_UserInfo.F_SYSTEM_TIME)) {
			userInfo.put(JS_UserInfo.F_SYSTEM_TIME, userMsg.get(JS_UserInfo.F_SYSTEM_TIME));
		}
		if (!userMsg.isNull(JS_UserInfo.F_EXPIRED_DATE)) {
			userInfo.put(JS_UserInfo.F_EXPIRED_DATE, userMsg.get(JS_UserInfo.F_EXPIRED_DATE));
		}
		if (!userMsg.isNull(JS_UserInfo.F_ORG_RESPONSE)) {
			userInfo.put(JS_UserInfo.F_ORG_RESPONSE, userMsg.get(JS_UserInfo.F_ORG_RESPONSE));
		}
		
		return userInfo;
	}

	/**
	 * 取得用戶登入訊息物件(API用)
	 * @return JSONObject
	 */
	JSONObject getUserMsg() {
		return this.userMsg;
	}
}
