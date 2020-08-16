package com.syt.jabx.consts;

/**
 * 回覆訊息所使用之常數類別
 * @author jason
 *
 */
public class JS_Result {

	/**
	 * 返回物件(依不同的FUNC_ID及STATUS_ID而不同)
	 */
	public final static String DATA = "dat";

	/**
	 * 錯誤代碼(int)
	 */
	public final static String ERR_CODE = "erc";
	
	/**
	 * 錯誤訊息(String)
	 */
	public final static String ERR_DESC = "erd";
	
	/**
	 * 功能代碼(int)
	 */
	public final static String FUNC_ID = "fci";
	
	/**
	 * 主機之ip和埠號(String)
	 */
	public final static String IP_PORT = "ipp";

	/**
	 * 是否通知旗標(boolean)
	 */
	public final static String NOTIFIED = "nod";

	/**
	 * 封包序號(String)
	 */
	public final static String PACK_NO = "pkn";

	/**
	 * 狀態參數 (String)
	 */
	public final static String PARAM = "pam";

	/**
	 * 狀態代碼(int)
	 */
	public final static String STATUS_ID = "ssi";
}
