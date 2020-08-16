package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXSmartRankClassItem;

/**
 * 即時排名分類項目之類別
 * @author Jason
 *
 */
final class ZJABXSmartRankClassItem implements IJABXSmartRankClassItem {

	/**
	 * 市場別屬性
	 */
	private String marketType;

	/**
	 * 即時排名分類群組代碼
	 */
	private int classID;

	/**
	 * 即時排名分類群組名稱(UTF-8)
	 */
	private String name;

	/**
	 * 即時排名分類的交易所代碼
	 */
	private String exchangeID;

	/**
	 * @see com.syt.jabx.query.IJABXSmartRankClassItem#getMarketType()
	 */
	@Override
	public String getMarketType() {
		// TODO Auto-generated method stub
		return marketType;
	}

	/**
	 * 設定市場別屬性
	 * @param marketType - String
	 */
	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartRankClassItem#getClassID()
	 */
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return classID;
	}

	/**
	 * 設定即時排名分類群組代碼
	 * @param classID - int
	 */
	public void setClassID(int classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartRankClassItem#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * 設定即時排名分類群組名稱(UTF-8)
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see com.syt.jabx.query.IJABXSmartRankClassItem#getExchangeID()
	 */
	@Override
	public String getExchangeID() {
		// TODO Auto-generated method stub
		return exchangeID;
	}

	/**
	 * 設定即時排名分類的交易所代碼
	 * @param exchangeID - int
	 */
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}
}
