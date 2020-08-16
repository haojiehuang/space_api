package com.syt.jabx.kernel;

import java.util.List;

/**
 * 即時交易回報資訊請求介面
 * @author jason
 *
 */
public interface IJABXTradeReportRequest {

	/**
	 * 傳送訊息至Server
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param msgSubType - int(廣播訊息次代碼)<br>
	 * 1.系統公告:JABXConst.ABX_FREEMSG_BROADCAST<br>
	 * 2.交易所訊息:JABXConst.ABX_FREEMSG_EXCHANGE<br>
	 * 3.新聞訊息:JABXConst.ABX_FREEMSG_NEWS<br>
	 * 4.產品訊息:JABXConst.ABX_FREEMSG_PRODUCT<br>
	 * 5.交易訊息:JABXConst.ABX_FREEMSG_TRADE
	 * @param msgKey - String(訊息鍵值)<br>
	 * 依msgSubType而異。<br>
	 * 1.JABXConst.ABX_FREEMSG_BROADCAST:空白，非必填。
	 * 2.JABXConst.ABX_FREEMSG_EXCHANGE:交易所代碼列表，可多筆以分號分隔。
	 * 3.JABXConst.ABX_FREEMSG_NEWS:新聞來源代碼，依系統定義。
	 * 4.JABXConst.ABX_FREEMSG_PRODUCT:產品代碼，依系統定義。
	 * 5.JABXConst.ABX_FREEMSG_TRADE:身分辨識碼，依系統定義。
	 * @param msg - String(傳送訊息)
	 */
	public void sendMessageToServer(int requestID, int msgSubType, String msgKey, String msg);

	/**
	 * 訂閱即時交易回報
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param listOfExchangeAndAccountID - List&lt;String&gt;<br>
	 * List中之String說明:<br>
	 * 用戶訊息鍵值格式“類別_身分證號“<br>
	 * I1：一般訊息<br>
	 * I2：緊急訊息<br>
	 * 例：I1_A123456789<br>
	 * 回報鍵值格式“類別_券商代碼_帳號“<br>
	 * SO：證券交易委託回報<br>
	 * SR：證券交易成交回報<br>
	 * FO：期權交易委託回報<br>
	 * FR：期權交易成交回報<br>
	 * GO：複委託交易委託回報<br>
	 * GR：複委託交易交回報<br>
	 * 例：SO_7790_123456789
	 */
	public void subscribeTradeReport(int requestID, List<String> listOfExchangeAndAccountID);	
}
