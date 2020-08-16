package com.syt.jabx.kernel;

import java.util.Iterator;

import org.json.JSONObject;

import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;
import com.syt.jabx.notify.IJABXNotifier;

/**
 * 顯示訊息之基本類別
 * @author Jason
 *
 */
abstract class ZIJABXInfoBase {

	/**
	 * 記錄Log用之StringBuffer物件
	 */
	private StringBuffer logSb = new StringBuffer();

	/**
	 * 輸出訊息至Console及Log
	 * @param jabxLog - IJABXLog
	 * @param dataAry - byte[](Socket數據) 
	 */
	public void outputInfoAndLog(final IJABXLog jabxLog, final byte[] dataAry) {
		logSb.delete(0, logSb.length());
		logSb.append(this.getClass().getSimpleName()).append(".parse()");
		try {
			jabxLog.outputInfoAndLog(logSb.toString(), new String(dataAry, JABXConst.ABX_CHARSET));
		}catch(Exception e) {
			jabxLog.outputErrorAndLog("ZIJABXInfoBase.outputInfoAndLog()", e.getMessage());
		}
	}

	/**
	 * 輸出訊息至Log及Console
	 * @param jabxLog - IJABXLog
	 * @param dataAry - byte[](Socket數據) 
	 */
	public void outputRealtimeMsg(final IJABXLog jabxLog, final byte[] dataAry) {
		logSb.delete(0, logSb.length());
		logSb.append(this.getClass().getSimpleName()).append(".parse()");
		try {
			jabxLog.outputRealtimeMsg(logSb.toString(), new String(dataAry, JABXConst.ABX_CHARSET));
		}catch(Exception e) {
			jabxLog.outputErrorAndLog("ZIJABXInfoBase.outputRealtimeMsg()", e.getMessage());
		}
	}

	/**
	 * Clone JSONObject
	 * @param src - JSONObject
	 * @return JSONObject
	 */
	public JSONObject cloneJSON(JSONObject src) {
		JSONObject target = new JSONObject();
		Iterator<String> it = src.keys();
		String key;
		Object value;
		while (it.hasNext()) {
			key = it.next();
			value = src.get(key);
			target.put(key, value);
		}
		
		return target;
	}

	/**
	 * 訊息通知Function
	 * @param notify - IJABXNotifier
	 * @param result - JSONObject
	 */
	public void notifyChange(final IJABXNotifier notify, final JSONObject result) {

		if (notify == null) {
			return;
		}

		if (result != null && (boolean)result.get(JS_Result.NOTIFIED)) {
			if (notify != null) {
				notify.putResultQueue(cloneJSON(result));
			}
		}
	}
}
