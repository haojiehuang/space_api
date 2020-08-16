package com.syt.jabx.notify;

/**
 * 輸出訊息及Log之介面
 * @author jason
 *
 */
public interface IJABXLog {

	/**
	 * 輸出訊息及記錄Log
	 * @param funcName - String(程式執行之Function名稱)
	 * @param msg - String(訊息)
	 */
	public void outputInfoAndLog(String funcName, String msg);

	/**
	 * 輸出即時訊息及記錄Log
	 * @param funcName - String(程式執行之Function名稱)
	 * @param msg - String(訊息)
	 */
	public void outputRealtimeMsg(String funcName, String msg);

	/**
	 * 輸出錯誤訊息Log
	 * @param funcName - String(程式執行之Function名稱)
	 * @param msg - String(訊息)
	 */
	public void outputErrorAndLog(String funcName, String msg);
}
