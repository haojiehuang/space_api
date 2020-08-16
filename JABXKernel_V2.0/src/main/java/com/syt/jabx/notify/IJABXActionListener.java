package com.syt.jabx.notify;

import org.json.JSONObject;

/**
 * 訊息監聽介面<br>
 * 當某個UI的介面，需要做即時之查詢時，需實作此介面。<br>
 * 當JABXToolkit API接收到訊息後，會執行resultPerformed函式，通知UI有資料進來。<br>
 * 而UI可透過JSONObject物件，得知變更的資料，而作相應的改變。<br>  
 * @author jason
 * 
 */
public interface IJABXActionListener {

	/**
	 * 訊息監聽的Function
	 * @param result - JSONObject
	 * @return boolean
	 */
    public boolean resultPerformed(JSONObject result);
}
