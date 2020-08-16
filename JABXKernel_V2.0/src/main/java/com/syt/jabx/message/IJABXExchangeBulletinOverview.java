package com.syt.jabx.message;

import java.util.List;

/**
 * 交易所公告標題索引的介面
 * @author Jason
 *
 */
public interface IJABXExchangeBulletinOverview {

	/**
	 * 取得交易所公告標題項目的介面總筆數 
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得指向某個index的交易所公告標題項目的物件
	 * @param index - int(某個交易所公告標題項目的索引值，從 0 開始)
	 * @return IJABXExchangeBulletinItem
	 */
	public IJABXExchangeBulletinItem atIndex(int index);

	/**
	 * 以交易所公告序號取得交易所公告標題項目的物件
	 * @param serialNo - int(交易所公告序號)
	 * @return IJABXExchangeBulletinItem
	 */
	public IJABXExchangeBulletinItem atSerialNo(int serialNo);

	/**
	 * 依交易所代碼取得交易所公告標題項目的列表
	 * @param exchangeID - String(交易所代碼)
	 * @return List&lt;IJABXExchangeBulletinItem&gt;
	 */
	public List<IJABXExchangeBulletinItem> getListByExchangeID(String exchangeID);

}
