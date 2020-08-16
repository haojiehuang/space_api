package com.syt.jabx.kernel;

/**
 * 當壓縮格式不為ABUS_COMPRESS_NULL時，記錄Compress Header用之類別
 * @author Jason
 *
 */
final class ZJABXCompressHeader {

	/**
	 * 錯誤碼
	 */
	private int errorCode;

	/**
	 * 未壓縮前之內容長度
	 */
	private int unCompressLength;

	/**
	 * 保留
	 */
	private String reserve;

	/**
	 * 取得錯誤碼
	 * @return int
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * 設定錯誤碼
	 * @param errorCode - int
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 取得未壓縮前之內容長度
	 * @return int
	 */
	public int getUnCompressLength() {
		return unCompressLength;
	}

	/**
	 * 設定未壓縮前之內容長度
	 * @param unCompressLength - int
	 */
	public void setUnCompressLength(int unCompressLength) {
		this.unCompressLength = unCompressLength;
	}

	/**
	 * 取得保留
	 * @return String
	 */
	public String getReserve() {
		return reserve;
	}

	/**
	 * 設定保留
	 * @param reserve - String
	 */
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
}
