package com.syt.jabx.message;

import java.util.List;

/**
 * 交易所公告標題項目的介面
 * @author Jason
 *
 */
public interface IJABXExchangeBulletinItem {

	/**
	 * 取得交易所公告序號
	 * @return int
	 */
	public int getSerialNo();

	/**
	 * 取得交易所代碼
	 * @return String
	 */
	public String getExchangeID();

	/**
	 * 取得交易所公告分類代碼
	 * @return String
	 */
	public String getClassID();

	/**
	 * 取得交易所公告類型
	 * @return String
	 */
	public String getBulletinType();

	/**
	 * 取得交易所公告內容資料格式
	 * @return String
	 */
	public String getDataType();

	/**
	 * 取得交易所公告相關股票全代碼列表
	 * @return List&lt;String&gt;
	 */
	public List<String> getListOfStockID();

	/**
	 * 取得交易所公告日期時間
	 * @return String
	 */
	public String getDataTime();

	/**
	 * 取得交易所公告標題 
	 * @return String
	 */
	public String getTitle();

}
