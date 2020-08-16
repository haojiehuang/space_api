package com.syt.jabx.kernel;

/**
 * 股票訊息(用於保留資料)之類別
 * @author jason
 *
 */
final class ZJABXReservedStkInfo {

	/**
	 * 最大之成交序號
	 */
	private int maxSeqNo;

	/**
	 * 開盤時間(HHmm)
	 */
	private short openTime;

	/**
	 * 收盤時間(HHmm)
	 */
	private short closeTime;

	/**
	 * 開盤參考價
	 */
	private int openRefPrice;

	/**
	 * 小數位數或分數之分母
	 */
	private int decimal;

	/**
	 * 總金額
	 */
	private long totalAmount;

	/**
	 * 總量
	 */
	private long totalVolumn;

	/**
	 * 總筆數
	 */
	private int totalCount;

	/**
	 * 內盤總量
	 */
	private long totalInVolume;

	/**
	 * 外盤總量
	 */
	private long totalOutVolume;
	
	/**
	 * 委買價
	 */
	private int bidPrice;

	/**
	 * 委賣價
	 */
	private int askPrice;

	/**
	 * 最後交易日
	 */
	private int lastTradeDate;

	/**
	 * Constructor
	 */
	public ZJABXReservedStkInfo() {
		lastTradeDate = -1;
	}

	/**
	 * 取得最大成交序號
	 * @return int
	 */
	public int getMaxSeqNo() {
		return maxSeqNo;
	}

	/**
	 * 設定最大成交序號
	 * @param maxSeqNo - int
	 */
	public void setMaxSeqNo(int maxSeqNo) {
		this.maxSeqNo = maxSeqNo;
	}

	/**
	 * 取得開盤時間
	 * @return short
	 */
	public short getOpenTime() {
		return openTime;
	}

	/**
	 * 設定開盤時間
	 * @param openTime - short
	 */
	public void setOpenTime(short openTime) {
		this.openTime = openTime;
	}

	/**
	 * 取得收盤時間
	 * @return short
	 */
	public short getCloseTime() {
		return closeTime;
	}

	/**
	 * 設定收盤時間
	 * @param closeTime - short
	 */
	public void setCloseTime(short closeTime) {
		this.closeTime = closeTime;
	}

	/**
	 * 取得開盤參考價
	 * @return int
	 */
	public int getOpenRefPrice() {
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
	 * 取得小數位數或分數之分母
	 * @return int
	 */
	public int getDecimal() {
		return decimal;
	}

	/**
	 * 設定小數位數或分數之分母
	 * @param decimal - int
	 */
	public void setDecimal(int decimal) {
		this.decimal = decimal;
	}
	/**
	 * 取得總金額
	 * @return long
	 */
	public long getTotalAmount() {
		return totalAmount;
	}

	/**
	 * 設定總金額
	 * @param totalAmount - long
	 */
	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * 取得總量
	 * @return long
	 */
	public long getTotalVolumn() {
		return totalVolumn;
	}

	/**
	 * 設定總量
	 * @param totalVolumn - long
	 */
	public void setTotalVolumn(long totalVolumn) {
		this.totalVolumn = totalVolumn;
	}

	/**
	 * 取得總筆數
	 * @return int
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 設定總筆數
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 取得內盤總量
	 * @return long
	 */
	public long getTotalInVolume() {
		return totalInVolume;
	}

	/**
	 * 設定內盤總量
	 * @param totalInVolume - long
	 */
	public void setTotalInVolume(long totalInVolume) {
		this.totalInVolume = totalInVolume;
	}

	/**
	 * 取得外盤總量
	 * @return long
	 */
	public long getTotalOutVolume() {
		return totalOutVolume;
	}

	/**
	 * 設定外盤總量
	 * @param totalOutVolume - long
	 */
	public void setTotalOutVolume(long totalOutVolume) {
		this.totalOutVolume = totalOutVolume;
	}

	/**
	 * 取得委買價
	 * @return int
	 */
	public int getBidPrice() {
		// TODO Auto-generated method stub
		return bidPrice;
	}

	/**
	 * 設定委買價
	 * @param bidPrice - int
	 */
	public void setBidPrice(int bidPrice) {
		this.bidPrice = bidPrice;
	}

	/**
	 * 取得委賣價
	 * @return int
	 */
	public int getAskPrice() {
		// TODO Auto-generated method stub
		return askPrice;
	}

	/**
	 * 設定委賣價
	 * @param askPrice - int
	 */
	public void setAskPrice(int askPrice) {
		this.askPrice = askPrice;
	}

	/**
	 * 取得最後交易日(yyyyMMdd)
	 * @return int
	 */
	public int getLastTradeDate() {
		return lastTradeDate;
	}

	/**
	 * 設定最後交易日(yyyyMMdd)
	 * @param lastTradeDate - int
	 */
	public void setLastTradeDate(int lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
	}

	/**
	 * 清除所有資料或歸零
	 */
	public void clear() {
		maxSeqNo = 0;
		openTime = 0;
		closeTime = 0;
		openRefPrice = 0;
		decimal = 0; 
		totalAmount = 0;
		totalVolumn = 0;
		totalCount = 0;
		totalInVolume = 0;
		totalOutVolume = 0;
		bidPrice = 0;
		askPrice = 0;
		lastTradeDate = 0;
	}
}
