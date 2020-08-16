package com.syt.jabx.notify;

import org.json.JSONObject;

/**
 * 訊息通知介面(JABX API專用)
 * @author Jason
 *
 */
public interface IJABXNotifier extends Runnable {

	/**
	 * 添加一IABusActionListener物件
	 * @param listener - IABusActionListener
	 */
	public void addListener(IJABXActionListener listener);
	
	/**
	 * 移除一IABusActionListener物件
	 * @param listener - IABusActionListener
	 */
	public void removeListener(IJABXActionListener listener);
	
	/**
	 * 移除所有IABusActionListener物件
	 */
	public void removeAllListener();

	/**
	 * 訊息通知
	 * @param result - JSONObject
	 */
	public void putResultQueue(JSONObject result);

	/**
	 * 移除所有Queue中之資料
	 * @throws InterruptedException -
	 */
	public void removeAllResultQueue() throws InterruptedException;

	/**
	 * 取得是否執行Thread
	 * @return boolean
	 */
	public boolean isRunFlag();

	/**
	 * 設定是否執行Thread
	 * @param isRunFlag - boolean
	 */
	public void setRunFlag(boolean isRunFlag);

}
