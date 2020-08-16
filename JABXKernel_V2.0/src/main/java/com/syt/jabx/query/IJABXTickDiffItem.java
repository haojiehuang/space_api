package com.syt.jabx.query;

/**
 * 檔差項目介面
 * @author jason
 *
 */
public interface IJABXTickDiffItem {

	/**
	 * 取得檔差類別碼
	 * @return String
	 */
	public String getSpreadCode();

	/**
	 * 取得檔差階級
	 * @return String
	 */
	public String getPriceRank();

	/**
	 * 取得升降單位
	 * @return String
	 */
	public String getPriceStep();

}
