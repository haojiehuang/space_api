package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXHistoryQuoteData;

/**
 * 股票歷史報價數據(K線)的類別
 * @author Jason
 *
 */
final class ZJABXHistoryQuoteData implements IJABXHistoryQuoteData {

	/**
	 * 證券全代碼
	 */
	public String id;

	/**
	 * 股票歷史報價的周期代碼
	 */
	public int period;

	/**
	 * 股票歷史報價數據總筆數
	 */
	public int dataTotalCount;

	/**
	 * 股票歷史報價數據
	 */
	public String data;

	/**
	 * @see com.syt.jabx.query.IJABXHistoryQuoteData#getID()
	 */
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return id;
	}

	/**
	 * 設定證券全代碼
	 * @param id - String
	 */
	public void setID(String id) {
		this.id = id;
	}

	/**
	 * @see com.syt.jabx.query.IJABXHistoryQuoteData#getPeriod()
	 */
	@Override
	public int getPeriod() {
		// TODO Auto-generated method stub
		return period;
	}

	/**
	 * 設定股票歷史報價的周期代碼
	 * @param period - int
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * @see com.syt.jabx.query.IJABXHistoryQuoteData#getDataTotalCount()
	 */
	@Override
	public int getDataTotalCount() {
		// TODO Auto-generated method stub
		return dataTotalCount;
	}

	/**
	 * 設定股票歷史報價數據總筆數
	 * @param dataTotalCount
	 */
	public void setDataTotalCount(int dataTotalCount) {
		this.dataTotalCount = dataTotalCount;
	}

	/**
	 * @see com.syt.jabx.query.IJABXHistoryQuoteData#getData()
	 */
	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return data;
	}

	/**
	 * 設定股票歷史報價數據
	 * @param data - String
	 */
	public void setData(String data) {
		this.data = data;
	}
}
