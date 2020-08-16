package com.syt.jabx.kernel;

/**
 * 廣播訊息項目的介面
 * @author Jason
 *
 */
final class ZJABXRealtimeFormatItem {

	/**
	 * 訊息識別鍵值
	 */
	private String abyKey;

	/**
	 * 訊息序號
	 */
	private int serialNo;

	/**
	 * 來源別
	 */
	private String sourceNo;

	/**
	 * 訊息型態
	 */
	private String catalogNo;

	/**
	 * 訊息類別
	 */
	private String messageType;

	/**
	 * 訊息類別2
	 */
	private String messageType2;

	/**
	 * 內容型態
	 */
	private String dataType;

	/**
	 * 相關連股票
	 */
	private String relationStock;

	/**
	 * 訊息日期時間
	 */
	private String dataTime;

	/**
	 * URL連結地址
	 */
	private String url;

	/**
	 * 訊息標題
	 */
	private String title;

	/**
	 * 取得訊息識別鍵值
	 * @return String
	 */
	public String getAbyKey() {
		return abyKey;
	}

	/**
	 * 設定訊息識別鍵值
	 * @param abyKey
	 */
	public void setAbyKey(String abyKey) {
		this.abyKey = abyKey;
	}

	/**
	 * 取得訊息序號
	 * @return int
	 */
	public int getSerialNo() {
		return serialNo;
	}

	/**
	 * 設定訊息序號
	 * @param serialNo - int
	 */
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 取得來源別
	 * @return String
	 */
	public String getSourceNo() {
		return sourceNo;
	}

	/**
	 * 設定來源別
	 * @param sourceNo - String
	 */
	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}

	/**
	 * 取得訊息型態
	 * @return String
	 */
	public String getCatalogNo() {
		return catalogNo;
	}

	/**
	 * 設定訊息型態
	 * @param catalogNo - String
	 */
	public void setCatalogNo(String catalogNo) {
		this.catalogNo = catalogNo;
	}

	/**
	 * 取得訊息類別
	 * @return String
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * 設定訊息類別
	 * @param messageType - String
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * 取得訊息類別2
	 * @return String
	 */
	public String getMessageType2() {
		return messageType2;
	}

	/**
	 * 設定訊息類別2
	 * @param messageType2 - String
	 */
	public void setMessageType2(String messageType2) {
		this.messageType2 = messageType2;
	}

	/**
	 * 取得內容型態
	 * @return String
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * 設定內容型態
	 * @param dataType - String
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * 取得相關連股票
	 * @return String
	 */
	public String getRelationStock() {
		return relationStock;
	}

	/**
	 * 設定相關連股票
	 * @param relationStock - String
	 */
	public void setRelationStock(String relationStock) {
		this.relationStock = relationStock;
	}

	/**
	 * 取得訊息日期時間
	 * @return String
	 */
	public String getDataTime() {
		return dataTime;
	}

	/**
	 * 設定訊息日期時間
	 * @param dataTime - String
	 */
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	/**
	 * 取得URL連結地址
	 * @return String
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 設定URL連結地址
	 * @param url - String
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 取得訊息標題
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 設定訊息標題
	 * @param title - String
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
