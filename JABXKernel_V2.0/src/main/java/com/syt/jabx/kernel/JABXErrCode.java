package com.syt.jabx.kernel;

/**
 * 錯誤代碼定義之類別
 * @author Jason
 *
 */
public final class JABXErrCode {

	/**
	 * 基本錯誤常數
	 */
	private static final int BASE_ERROR = 4000;

	/**
	 * 無錯誤
	 */
	public static final int NO_ERROR = 0;

	/**
	 * ABus Socket錯誤
	 */
	public static final int ABUS_SOCKET_ERROR = BASE_ERROR + 1;

	/**
	 * Unknow host錯誤
	 */
	public static final int ABUS_UNKONWNHOST_ERROR = BASE_ERROR + 2;

	/**
	 * 記憶體不足錯誤
	 */
	public static final int OUTOFMEMORY_ERROR = BASE_ERROR + 3;

	/**
	 * IO錯誤
	 */
	public static final int IO_ERROR = BASE_ERROR + 4;

	/**
	 * 無數據錯誤
	 */
	public static final int NODATA_ERROR = BASE_ERROR + 5;

	/**
	 * 數據CheckSum錯誤
	 */
	public static final int CHECKSUM_ERROR = BASE_ERROR + 6;

	/**
	 * 尚未連線
	 */
	public static final int UNCONNECTING = BASE_ERROR + 7;
	
	/**
	 * 空指標
	 */
	public static final int NULL_POINTER = BASE_ERROR + 998;

	/**
	 * Unknown錯誤
	 */
	public static final int UNKNOWN_ERROR = BASE_ERROR + 999;
}
