package com.syt.jabx.kernel;

import org.json.JSONObject;

/**
 * 解析Socket內容之介面(API內部使用)
 * @author Jason
 *
 */
public interface IJABXParseBody {

	/**
	 * 解析數據
	 * @param fixProc - JABXFixProc(處理Fix數據之類別)
	 * @param result - JSONObject(訊息結果物件)
	 * @param dataAry - byte[](封包內容)
	 * @param ctrlHeader - JABXCtrlHeader(記錄Socket Header之物件)
	 * @param offset - int(偏移量)
	 */
	public void parse(ZJABXFixProc fixProc, JSONObject result, byte[] dataAry, 
			JABXCtrlHeader ctrlHeader, int offset);
}
