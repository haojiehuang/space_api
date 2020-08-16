package com.syt.jabx.kernel;

import java.util.List;

import com.syt.jabx.query.IJABXStockRelationClass;

/**
 * 股票關連分類項目之類別
 * @author Jason
 *
 */
final class ZJABXStockRelationClass implements IJABXStockRelationClass {

	/**
	 * 證券全代碼
	 */
	private String id;

	/**
	 * 商品分類群組代碼
	 */
	private int stockClassID;

	/**
	 * 產業分類群組代碼
	 */
	private int tradeClassID;

	/**
	 * 自定(板塊)分類群組代碼列表
	 */
	private List<Integer> listOfBlockClassID;

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationClass#getID()
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
	 * @see com.syt.jabx.query.IJABXStockRelationClass#getStockClassID()
	 */
	@Override
	public int getStockClassID() {
		// TODO Auto-generated method stub
		return stockClassID;
	}

	/**
	 * 設定商品分類群組代碼
	 * @param stockClassID - int
	 */
	public void setStockClassID(int stockClassID) {
		this.stockClassID = stockClassID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationClass#getTradeClassID()
	 */
	@Override
	public int getTradeClassID() {
		// TODO Auto-generated method stub
		return tradeClassID;
	}

	/**
	 * 設定產業分類群組代碼
	 * @param tradeClassID - int
	 */
	public void setTradeClassID(int tradeClassID) {
		this.tradeClassID = tradeClassID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationClass#getListOfBlockClassID()
	 */
	@Override
	public List<Integer> getListOfBlockClassID() {
		// TODO Auto-generated method stub
		return listOfBlockClassID;
	}

	/**
	 * 設定自定(板塊)分類群組代碼列表
	 * @param listOfBlockClassID - List&lt;Integer&gt;
	 */
	public void setListOfBlockClassID(List<Integer> listOfBlockClassID) {
		this.listOfBlockClassID = listOfBlockClassID;
	}

}
