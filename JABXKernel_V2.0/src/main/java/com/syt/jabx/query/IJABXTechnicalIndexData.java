package com.syt.jabx.query;

/**
 * 技術指標數據的介面
 * @author Jason
 *
 */
public interface IJABXTechnicalIndexData {

	/**
	 * 取得證券全代碼
	 * @return String
	 */
	public String getID();

	/**
	 * 取得指標記錄序號
	 * @return int
	 */
	public int getLineID();

	/**
	 * 取得技術指標代碼
	 * @return int
	 */
	public int getTechnicalIndexID();

	/**
	 * 取得周期代碼
	 * @return int
	 */
	public int getPeriod();

	/**
	 * 取得指標線設定
	 * @return String
	 */
	public String getLineSetup();

	/**
	 * 取得技術指標數據總筆數
	 * @return int
	 */
	public int getDataTotalCount();

	/**
	 * 取得技術指標數據
	 * @return String
	 */
	public String getData();

}
