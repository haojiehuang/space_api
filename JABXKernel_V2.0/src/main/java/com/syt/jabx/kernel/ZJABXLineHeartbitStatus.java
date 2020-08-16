package com.syt.jabx.kernel;

/**
 * 訂閱指標連線心跳之狀態類別
 */
final class ZJABXLineHeartbitStatus {

	/**
	 * 訊息主代碼
	 */
	private int mainType;

	/**
	 * 訊息次代碼
	 */
	private String subType;

	/**
	 * 訂閱鍵值
	 */
	private String keyCode;

	/**
	 * 訂閱結果(0-成功,1-訂閱項目不存在,2-無法訂閱或無權限訂閱)
	 */
	private int result;

	/**
	 * 取得訊息主代碼
	 * @return int
	 */
	public int getMainType() {
		return mainType;
	}

	/**
	 * 設定訊息主代碼
	 * @param mainType - int
	 */
	public void setMainType(int mainType) {
		this.mainType = mainType;
	}

	/**
	 * 取得訊息次代碼
	 * @return String
	 */
	public String getSubType() {
		return subType;
	}

	/**
	 * 設定訊息次代碼
	 * @param subType - String
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}

	/**
	 * 取得訂閱鍵值
	 * @return String
	 */
	public String getKeyCode() {
		return keyCode;
	}

	/**
	 * 設定訂閱鍵值
	 * @param keyCode - String
	 */
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	/**
	 * 取得訂閱結果
	 * @return int
	 */
	public int getResult() {
		return result;
	}

	/**
	 * 設定訂閱結果
	 * @param result - int
	 */
	public void setResult(int result) {
		this.result = result;
	}

}
