package com.syt.jabx.kernel;

import java.util.List;

import com.syt.jabx.query.IJABXClassRelationStockListItem;

/**
 * 分類關連股票列表項目之類別
 * @author Jason
 *
 */
final class ZJABXClassRelationStockListItem implements IJABXClassRelationStockListItem {

	/**
	 * 產業或自定(板塊)分類群組代碼
	 */
	private int classID;

	/**
	 * 證券全代碼列表
	 */
	private List<String> listOfStockID;

	/**
	 * @see com.syt.jabx.query.IJABXClassRelationStockListItem#getClassID()
	 */
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return classID;
	}

	/**
	 * 設定產業或自定(板塊)分類群組代碼
	 * @param classID - int
	 */
	public void setClassID(int classID) {
		this.classID = classID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXClassRelationStockListItem#getListOfStockID()
	 */
	@Override
	public List<String> getListOfStockID() {
		// TODO Auto-generated method stub
		return listOfStockID;
	}

	/**
	 * 設定證券全代碼列表
	 * @param listOfStockID - List&lt;String&gt;
	 */
	public void setListOfStockID(List<String> listOfStockID) {
		this.listOfStockID = listOfStockID;
	}
}
