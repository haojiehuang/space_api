package com.syt.jabx.kernel;

import java.util.List;

import com.syt.jabx.query.IJABXStockRelationClassListItem;

/**
 * 股票關連分類列表項目之類別
 * @author Jason
 *
 */
final class ZJABXStockRelationClassListItem implements IJABXStockRelationClassListItem {

	/**
	 * 證券全代碼
	 */
	private String id;

	/**
	 * 產業或自定(板塊)分類群組代碼列表
	 */
	private List<Integer> classIDList;

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationClassListItem#getID()
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
	public void setClassID(String id) {
		this.id = id;
	}

	/**
	 * @see com.syt.jabx.query.IJABXStockRelationClassListItem#getListOfClassID()
	 */
	@Override
	public List<Integer> getListOfClassID() {
		// TODO Auto-generated method stub
		return classIDList;
	}

	/**
	 * 設定產業或自定(板塊)分類群組代碼列表
	 * @param classIDList - List&lt;Integer&gt;
	 */
	public void setListOfID(List<Integer> classIDList) {
		this.classIDList = classIDList;
	}
}
