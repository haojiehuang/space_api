package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXMsgLine;

/**
 * 實作即時指標訊息之類別
 * @author jason
 *
 */
final class ZJABXMsgLine implements IJABXMsgLine {

	/**
	 * 指標記錄序號
	 */
	private int lineID;

	/**
	 * 指標總筆數
	 */
	private int totalCount;

	/**
	 * 即時指標內容(格式依所訂閱的指標而定)
	 */
	private String data;

	/**
	 * Constructor
	 */
	public ZJABXMsgLine() {
		this.data = "";
	}

	/**
	 * @see com.syt.jabx.watch.IJABXMsgLine#getLineID()
	 */
	@Override
	public int getLineID() {
		// TODO Auto-generated method stub
		return this.lineID;
	}

	/**
	 * 設定指標記錄序號
	 * @param lineID - int
	 */
	public void setLineID(int lineID) {
		this.lineID = lineID;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXMsgLine#getTotalCount()
	 */
	@Override
	public int getTotalCount() {
		// TODO Auto-generated method stub
		return this.totalCount;
	}

	/**
	 * 設定指標總筆數
	 * @param totalCount - int
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXMsgLine#getData()
	 */
	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return this.data;
	}

	/**
	 * 設定即時指標內容(格式依所訂閱的指標而定)
	 * @param data - String
	 */
	public void setData(String data) {
		this.data = data;
	}
}
