package com.syt.jabx.watch;

/**
 * 股票相關資料訊息之介面
 * @author Jason
 *
 */
public interface IJABXStockReference {
	
	/**
	 * 取得昨收價
	 * @return int
	 */
	public int getYestPrice();

	/**
	 * 取得開盤參考價
	 * @return int
	 */
	public int getOpenRefPrice();

	/**
	 * 取得漲停價
	 * @return int
	 */
	public int getUpStopPrice();

	/**
	 * 取得跌停價
	 * @return int
	 */
	public int getDownStopPrice();

	/**
	 * 取得昨收量
	 * @return long
	 */
	public long getYestVolume();

	/**
	 * 取得昨未平倉
	 * @return int
	 */
	public int getYestOI();

	/**
	 * 取得交易狀態
	 * @return String
	 */
	public String getTradeStatus();

	/**
	 * 取得下市/重新上市
	 * @return String
	 */
	public String getListing();

	/**
	 * 設定最後收盤日
	 * @return int
	 */
	public int getLastCloseDate();

	/**
	 * 取得最後交易日
	 * @return int
	 */
	public int getLastTradeDate();

	/**
	 * 取得最後結算日/下市日
	 * @return int
	 */
	public int getDeListingDate();

	/**
	 * 取得檔差碼
	 * @return String
	 */
	public String getSpreadTableCode();

	/**
	 * 取得所屬股票全碼
	 * @return String
	 */
	public String getBelongFullStockID();

	/**
	 * 取得合約年月(一般為期貨商品)
	 * @return String(yyyyMM)
	 */
	public String getContractDate();
}
