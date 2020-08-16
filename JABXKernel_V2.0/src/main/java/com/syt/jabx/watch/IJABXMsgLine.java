package com.syt.jabx.watch;

/**
 * 即時指標訊息之介面
 * @author jason
 *
 */
public interface IJABXMsgLine {

	/**
	 * 取得指標記錄序號
	 * @return int
	 */
	public int getLineID();

	/**
	 * 取得指標總筆數
	 * @return int
	 */
	public int getTotalCount();

	/**
	 * 取得即時指標內容(格式依所訂閱的指標而定)
	 * @return String
	 */
	public String getData();
}
