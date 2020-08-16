package com.syt.jabx.notify;

import java.io.File;

/**
 * 輸出訊息之介面(JABX應用程序使用)
 * @author Jason
 *
 */
public interface IJABXOutputMsg {

	/**
	 * 合併func及info訊息(只有時間)
	 * @param account - String(用戶登入資料)
	 * @param func - String(程式執行之Function名稱)
	 * @param msg - String(訊息)
	 * @return String(合併後之訊息)
	 */
	public String mergeTimeInfo(String account, String func, String msg);

	/**
	 * 輸出訊息至控制台
	 * @param account - String(用戶登入資料)
	 * @param func - String(程式執行之Function名稱)
	 * @param msg - String(訊息)
	 */
	public void outputInfo(String account, String func, String msg);
	
	/**
	 * 取得檔案路徑
	 * @return File
	 */
	public File getFilePath();

	/**
	 * 是否輸出訊息
	 * @return boolean
	 */
	public boolean isOutputInfo();

	/**
	 * 是否寫入Log
	 * @return boolean
	 */
	public boolean isWriteLog();

	/**
	 * 是否輸出即時訊息
	 * @return boolean
	 */
	public boolean isOutputRealtime();

	/**
	 * 取得需產生Log之帳號字段(多筆以;分隔)
	 * @return String
	 */
	public String getLogCheckIDS();
}
