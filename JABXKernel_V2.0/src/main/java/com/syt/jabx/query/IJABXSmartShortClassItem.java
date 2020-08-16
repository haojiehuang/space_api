package com.syt.jabx.query;

/**
 * 短線精靈分類項目之介面
 * @author Jason
 *
 */
public interface IJABXSmartShortClassItem {

	/**
	 * 取得分類群組代碼
	 * @return int
	 */
	public int getClassID();

	/**
	 * 取得短線精靈分類群組名稱(UTF-8)
	 * @return String
	 */
	public String getName();

	/**
	 * 取得短線精靈分類的交易所代碼
	 * @return String
	 */
	public String getExchangeID();
}
