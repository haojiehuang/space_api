package com.syt.jabx.query;

import java.util.List;

/**
 * 用戶常用技術指標查詢的介面
 * @author Jason
 *
 */
public interface IJABXUserTechnicalIndexCommonSetup {

	/**
	 * 取得用戶常用技術指標代碼列表
	 * @return List&lt;String&gt;
	 */
	public List<String> getListOfTechnicalIndexID();

}
