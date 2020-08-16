package com.syt.jabx.kernel;


/**
 * 股票最佳買賣委託項目的類別
 * @author Jason
 *
 */
final class ZJABXBestOrderItem {

	/**
	 * 最佳買價
	 */
	private int bidPrice;

	/**
	 * 最佳買量
	 */
	private int bidVolume;

	/**
	 * 最佳賣價
	 */
	private int askPrice;

	/**
	 * 最佳賣量
	 */
	private int askVolume;

	/**
	 * 取得最佳買價
	 * @return int
	 */
	public synchronized int getBidPrice() {
		return bidPrice;
	}

	/**
	 * 設定最佳買價
	 * @param bidPrice - int
	 */
	public synchronized void setBidPrice(int bidPrice) {
		this.bidPrice = bidPrice;
	}

	/**
	 * 取得最佳買量
	 * @return int
	 */
	public synchronized int getBidVolume() {
		return bidVolume;
	}

	/**
	 * 設定最佳買量
	 * @param bidVolume - int
	 */
	public synchronized void setBidVolume(int bidVolume) {
		this.bidVolume = bidVolume;
	}

	/**
	 * 取得最佳賣價
	 * @return int
	 */
	public synchronized int getAskPrice() {
		return askPrice;
	}

	/**
	 * 設定最佳賣價
	 * @param askPrice - int
	 */
	public synchronized void setAskPrice(int askPrice) {
		this.askPrice = askPrice;
	}

	/**
	 * 取得最佳賣量
	 * @return int
	 */
	public synchronized int getAskVolume() {
		return askVolume;
	}

	/**
	 * 設定最佳賣量
	 * @param askVolume - int
	 */
	public synchronized void setAskVolume(int askVolume) {
		this.askVolume = askVolume;
	}

	/**
	 * 將所有數據設為0
	 */
	public synchronized void setDataToZeroOrSpace() {
		bidPrice = 0;
		bidVolume = 0;
		askPrice = 0;
		askVolume = 0;
	}
}
