package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXStockReference;

/**
 * 股票相關資料之類別
 * @author Jason
 *
 */
final class ZJABXStockReference implements IJABXStockReference {

	/**
	 * 昨收價
	 */
	private int yestPrice;

	/**
	 * 開盤參考價
	 */
	private int openRefPrice;

	/**
	 * 漲停價
	 */
	private int upStopPrice;

	/**
	 * 跌停價
	 */
	private int downStopPrice;

	/**
	 * 昨收量
	 */
	private long yestVolume;

	/**
	 * 
	 */
	private int yestOI;

	/**
	 * 交易狀態
	 */
	private String tradeStatus;

	/**
	 * 下市/重新上市
	 */
	private String listing;

	/**
	 * 最後收盤日
	 */
	private int lastCloseDate;

	/**
	 * 最後交易日
	 */
	private int lastTradeDate;

	/**
	 * 最後結算日/下市日
	 */
	private int deListingDate;

	/**
	 * 檔差碼 
	 */
	private String spreadTableCode;

	/**
	 * 所屬股票全碼
	 */
	private String belongFullStockID;

	/**
	 * 合約年月(一般為期貨商品)
	 */
	private String contractDate;

	/**
	 * @see com.syt.jabx.watch.IJABXStockReference#getYestPrice()
	 */
	@Override
	public int getYestPrice() {
		// TODO Auto-generated method stub
		return yestPrice;
	}

	/**
	 * 設定昨收價
	 * @param yestPrice - int
	 */
	public void setYestPrice(int yestPrice) {
		this.yestPrice = yestPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockReference#getOpenRefPrice()
	 */
	@Override
	public int getOpenRefPrice() {
		// TODO Auto-generated method stub
		return openRefPrice;
	}

	/**
	 * 設定開盤參考價
	 * @param openRefPrice - int
	 */
	public void setOpenRefPrice(int openRefPrice) {
		this.openRefPrice = openRefPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockReference#getUpStopPrice()
	 */
	@Override
	public int getUpStopPrice() {
		// TODO Auto-generated method stub
		return upStopPrice;
	}

	/**
	 * 設定漲停價
	 * @param upStopPrice - int
	 */
	public void setUpStopPrice(int upStopPrice) {
		this.upStopPrice = upStopPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockReference#getDownStopPrice()
	 */
	@Override
	public int getDownStopPrice() {
		// TODO Auto-generated method stub
		return downStopPrice;
	}

	/**
	 * 設定跌停價
	 * @param downStopPrice - int
	 */
	public void setDownStopPrice(int downStopPrice) {
		this.downStopPrice = downStopPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockReference#getYestVolume()
	 */
	@Override
	public long getYestVolume() {
		// TODO Auto-generated method stub
		return yestVolume;
	}

	/**
	 * 設定昨收量
	 * @param yestVolume - long
	 */
	public void setYestVolume(long yestVolume) {
		this.yestVolume = yestVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockReference#getYestOI()
	 */
	@Override
	public int getYestOI() {
		// TODO Auto-generated method stub
		return yestOI;
	}

	/**
	 * 設定昨未平倉
	 * @param yestOI - int
	 */
	public void setYestOI(int yestOI) {
		this.yestOI = yestOI;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockReference#getTradeStatus()
	 */
	@Override
	public String getTradeStatus() {
		// TODO Auto-generated method stub
		return tradeStatus;
	}

	/**
	 * 設定交易狀態
	 * @param tradeStatus - String
	 */
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockReference#getListing()
	 */
	@Override
	public String getListing() {
		// TODO Auto-generated method stub
		return listing;
	}

	/**
	 * 設定下市/重新上市
	 * @param listing - String
	 */
	public void setListing(String listing) {
		this.listing = listing;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockReference#getLastCloseDate()
	 */
	@Override
	public int getLastCloseDate() {
		// TODO Auto-generated method stub
		return lastCloseDate;
	}

	/**
	 * 設定最後收盤日
	 * @param lastCloseDate - int(yyyyMMdd)
	 */
	public void setLastCloseDate(int lastCloseDate) {
		this.lastCloseDate = lastCloseDate;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockReference#getLastTradeDate()
	 */
	@Override
	public int getLastTradeDate() {
		// TODO Auto-generated method stub
		return lastTradeDate;
	}

	/**
	 * 設定最後交易日
	 * @param lastTradeDate - int(yyyyMMdd)
	 */
	public void setLastTradeDate(int lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockReference#getDeListingDate()
	 */
	@Override
	public int getDeListingDate() {
		// TODO Auto-generated method stub
		return deListingDate;
	}

	/**
	 * 設定最後結算日/下市日
	 * @param deListingDate - int(yyyyMMdd)
	 */
	public void setDeListingDate(int deListingDate) {
		this.deListingDate = deListingDate;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockReference#getDeListingDate()
	 */
	@Override
	public String getSpreadTableCode() {
		// TODO Auto-generated method stub
		return spreadTableCode;
	}

	/**
	 * 設定檔差碼
	 * @param spreadTableCode - String
	 */
	public void setSpreadTableCode(String spreadTableCode) {
		this.spreadTableCode = spreadTableCode;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockReference#getDeListingDate()
	 */
	@Override
	public String getBelongFullStockID() {
		// TODO Auto-generated method stub
		return belongFullStockID;
	}

	/**
	 * 設定所屬股票全碼
	 * @param belongFullStockID - String
	 */
	public void setBelongFullStockID(String belongFullStockID) {
		this.belongFullStockID = belongFullStockID;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockReference#getDeListingDate()
	 */
	@Override
	public String getContractDate() {
		// TODO Auto-generated method stub
		return contractDate;
	}

	/**
	 * 設定合約年月(一般為期貨商品)
	 * @param contractDate - String
	 */
	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	/**
	 * 將類別中所用到的欄位設定為零或空白
	 */
	public void setDataToZeroOrSpace() {
		yestPrice = 0;
		openRefPrice = 0;
		upStopPrice = 0;
		downStopPrice = 0;
		yestVolume = 0;
		yestOI = 0;
		tradeStatus = "";
		listing = "";
		lastCloseDate = 0;
		lastTradeDate = 0;
		deListingDate = 0;
		spreadTableCode = "";
		belongFullStockID = "";
		contractDate = "";
	}
}
