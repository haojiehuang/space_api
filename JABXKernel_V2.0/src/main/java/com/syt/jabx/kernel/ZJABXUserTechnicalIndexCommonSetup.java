package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.query.IJABXUserTechnicalIndexCommonSetup;

/**
 * 用戶常用技術指標查詢的類別
 * @author Jason
 *
 */
final class ZJABXUserTechnicalIndexCommonSetup implements IJABXUserTechnicalIndexCommonSetup {

	/**
	 * 用戶常用技術指標代碼列表
	 */
	private List<String> listOfTechnicalIndexID;

	/**
	 * Constructor
	 */
	public ZJABXUserTechnicalIndexCommonSetup() {
		listOfTechnicalIndexID = new ArrayList<String>();
	}

	/**
	 * @see com.syt.jabx.user.IJABXUserTechnicalIndexCommonSetup#getListOfTechnicalIndexID()
	 */
	@Override
	public synchronized List<String> getListOfTechnicalIndexID() {
		// TODO Auto-generated method stub
		return listOfTechnicalIndexID;
	}

	/**
	 * 設定用戶常用技術指標代碼列表
	 * @param list - List&lt;String&gt;
	 */
	public synchronized void addItem(String item) {
		listOfTechnicalIndexID.add(item);
	}

	/**
	 * 清除listOfTechnicalIndexID中之物件
	 */
	public synchronized void clear() {
		listOfTechnicalIndexID.clear();
	}
}
