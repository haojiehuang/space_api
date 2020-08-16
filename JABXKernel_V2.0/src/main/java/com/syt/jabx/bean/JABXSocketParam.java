package com.syt.jabx.bean;

import com.syt.jabx.kernel.JABXCtrlHeader;

/**
 * 保存Socket傳送參數之類別
 * @author Jason
 *
 */
public final class JABXSocketParam {

	/**
	 * 功能名稱
	 */
	private String funcCode;

	/**
	 * 記錄Socket Header之物件
	 */
	private JABXCtrlHeader ctrlHeader;

	/**
	 * 查詢之參數byte[]
	 */
	private byte[] queryAry;

	/**
	 * Constructor
	 * @param funcCode - String
	 * @param ctrlHeader - JABXCtrlHeader
	 * @param queryAry - byte[]
	 */
	public JABXSocketParam(String funcCode, JABXCtrlHeader ctrlHeader, byte[] queryAry) {
		this.funcCode = funcCode;
		this.ctrlHeader = ctrlHeader;
		this.queryAry = queryAry;
	}

	/**
	 * 取得功能名稱
	 * @return String
	 */
	public String getFuncCode() {
		return funcCode;
	}

	/**
	 * 取得記錄Socket Header之物件
	 * @return JABXCtrlHeader
	 */
	public JABXCtrlHeader getCtrlHeader() {
		return ctrlHeader;
	}

	/**
	 * 取得查詢之參數byte[]
	 * @return byte[]
	 */
	public byte[] getQueryAry() {
		return queryAry;
	}

	/**
	 * 設定查詢之參數byte[]
	 * @param bAry - byte[]
	 */
	public void setQueryAry(byte[] bAry) {
		this.queryAry = bAry;
	}
}
