package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXTickDiffItem;

/**
 * 價差交易分類項目之類別
 * @author Jason
 *
 */
final class ZJABXTickDiffItem implements IJABXTickDiffItem {

	/**
	 * 檔差類別碼
	 */
	private String spreadCode;

	/**
	 * 檔差階級
	 */
	private String priceRank;

	/**
	 * 升降單位
	 */
	private String priceStep;

	/**
	 * @see com.syt.jabx.query.IJABXTickDiffItem#getSpreadCode()
	 */
	@Override
	public String getSpreadCode() {
		// TODO Auto-generated method stub
		return spreadCode;
	}

	/**
	 * 設定檔差類別碼
	 * @param spreadCode - String
	 */
	void setSpreadCode(String spreadCode) {
		this.spreadCode = spreadCode;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTickDiffItem#getPriceRank()
	 */
	@Override
	public String getPriceRank() {
		// TODO Auto-generated method stub
		return priceRank;
	}

	/**
	 * 設定檔差階級
	 * @param priceRank - String
	 */
	void setPriceRank(String priceRank) {
		this.priceRank = priceRank;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTickDiffItem#getPriceStep()
	 */
	@Override
	public String getPriceStep() {
		// TODO Auto-generated method stub
		return priceStep;
	}

	/**
	 * 設定升降單位
	 * @param priceStep - String
	 */
	void setPriceStep(String priceStep) {
		this.priceStep = priceStep;
	}
}