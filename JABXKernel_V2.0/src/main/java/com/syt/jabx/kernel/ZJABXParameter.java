package com.syt.jabx.kernel;

/**
 * 記錄Request中各函式啟動時所使用之參數
 * @author Jason
 *
 */
final class ZJABXParameter {

	/**
	 * 函式之功能ID
	 */
	private int funcID;

	/**
	 * 函式啟動時之參數(依不同功能，參數內容也不同)
	 */
	private Object parameter;

	/**
	 * 取得函式之功能ID
	 * @return int
	 */
	public int getFuncID() {
		return funcID;
	}

	/**
	 * 設定函式之功能ID
	 * @param funcID - int
	 */
	public void setFuncID(int funcID) {
		this.funcID = funcID;
	}

	/**
	 * 取得函式啟動時之參數(依不同功能，參數內容也不同)
	 * @return Object
	 */
	public Object getParameter() {
		return parameter;
	}

	/**
	 * 設定函式啟動時之參數(依不同功能，參數內容也不同)
	 * @param parameter - Object
	 */
	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}

	
}
