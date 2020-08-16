package com.syt.jabx.kmodel;

/**
 * 用戶技術指標參數預設值的介面
 * @author Jason
 *
 */
public interface IJABXUserTechnicalIndexSetup {

	/**
	 * 取得技術指標代碼
	 * @return int
	 */
	public int getTechnicalIndexID();

	/**
	 * 取得週期代碼
	 * @return int
	 */
	public int getPeriod();

	/**
	 * 取得預設值旗標
	 * @return byte
	 */
	public byte getDefaultFlag();

	/**
	 * 取得參數預設值
	 * 各參數間以分號(;)分隔，"參數1預設值;參數2預設值;…"
	 * @return String
	 */
	public String getParameter();

	/**
	 * 取得指標線設定 <br>
	 * 格式為 ”指標線名稱1|指標線顏色1|指標線形狀1|…;”。多筆以分號分隔。<br>
	 * 例：MACD指標線設定 ”DIFF|COLORWHITE;DEA|COLORYELLOW;MACD|COLORSTICK;”。
	 * @return String
	 */
	public String getLineSetup();

}
