package com.syt.jabx.query;

import java.util.List;

/**
 * 交易所公告內容的介面
 * @author Jason
 *
 */
public interface IJABXExchangeBulletinContent {

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
	 * 取得交易所公告日期時間(格式為："YYYY/MM/DD hh:mm:ss")
	 * @return String
	 */
	public String getDataTime();

	/**
	 * 取得交易所公告標題
	 * @return String
	 */
	public String getTitle();

	/**
	 * 取得交易所公告相關股票全代碼列表
	 * @return List&lt;String&gt;
	 */
	public List<String> getListOfStockID();

	/**
	 * 取得交易所公告內容 
	 * @return String
	 */
	public String getData();

}
