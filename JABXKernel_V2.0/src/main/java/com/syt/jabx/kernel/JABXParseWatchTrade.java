package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析股票之成交資訊之類別
 * @author Jason
 *
 */
public final class JABXParseWatchTrade extends IJABXParseBase implements IJABXParseBody {

	/**
	 * 應用程序核心管理物件
	 */
	private JABXKernel jabxKernel;

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * 行情資訊工具物件
	 */
	private JABXQuoteTool quoteTool;

	/**
	 * 儲存資料源
	 */
	private Object srcObj;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 * @param quoteTool - JABXQuoteTool
	 * @param srcObj - Object
	 */
	public JABXParseWatchTrade(JABXKernel jabxKernel, IJABXLog jabxLog, JABXQuoteTool quoteTool, Object srcObj) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
		this.quoteTool = quoteTool;
		this.srcObj = srcObj;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXParseBody#parse(com.syt.jabx.kernel.ZJABXFixProc, JSONObject, byte[], com.syt.jabx.kernel.JABXCtrlHeader, int)
	 */
	@Override
	public void parse(final ZJABXFixProc fixProc, final JSONObject result,
			byte[] dataAry, JABXCtrlHeader ctrlHeader, int offset) {
		// TODO Auto-generated method stub

		ZJABXAbyKeyReal abyKey = (ZJABXAbyKeyReal)ctrlHeader.getAbyKey();
		String packNo = String.valueOf(ctrlHeader.getLlSeqNo());
		logSb.delete(0, logSb.length());
		logSb.append(this.getClass().getSimpleName()).append(".parse()");
		try {
			if (offset != 0) {
				jabxLog.outputInfoAndLog(logSb.toString(), new String(dataAry, JABXConst.ABX_CHARSET));
				parse_1(fixProc, result, dataAry, abyKey, offset);
			}else {
				jabxLog.outputRealtimeMsg(logSb.toString(), new String(dataAry, JABXConst.ABX_CHARSET));
				parse_2(fixProc, result, dataAry, abyKey, packNo);
			}
		}catch(Exception e) {
		}
	}

	/**
	 * 處理回補資料
	 * @param fixProc - JABXFixProc
	 * @param result - JSONObject
	 * @param dataAry - byte[]
	 * @param abyKey - IJABXAbyKey
	 * @param offset - int
	 */
	private void parse_1(final ZJABXFixProc fixProc, final JSONObject result,
			byte[] dataAry, IJABXAbyKey abyKey, int offset) {
		int funcID = (int)result.get(JS_Result.FUNC_ID);
		ZJABXQuoteItem item = null;
		ZJABXQuoteOverview quoteOverview;
		try{
			ZJABXAbyKeyReal abyKeyStk = (ZJABXAbyKeyReal)abyKey;
			if (srcObj instanceof ZJABXQuoteOverview) {// R00或R01
				quoteOverview = (ZJABXQuoteOverview)srcObj;
				item = (ZJABXQuoteItem)quoteOverview.atID(abyKeyStk.getAbyCode());
				if (item == null) {
					item = new ZJABXQuoteItem();
					item.setStkID(abyKeyStk.getAbyCode());
					quoteOverview.addItem(item);
				}
			}else if (srcObj instanceof ZJABXQuoteItem) {// R06或R96
				item = (ZJABXQuoteItem)srcObj;
			}

			ZJABXReservedStkInfo stkInfo = quoteTool.getStkInfo(item.getStkID());
			if (stkInfo == null) {
				stkInfo = new ZJABXReservedStkInfo();
			}
			quoteTool.putStkInfoItem(item.getStkID(), stkInfo);
			
			ZJABXTrade trade = (ZJABXTrade)item.getTrade();
			if (trade == null) {
				trade = new ZJABXTrade();
				item.setTrade(trade);
			}

			ZJABXWatchTradeItem tradeItem = null;
			JABXTagValue tagValue;
			int nRead = offset;// 已讀取byte數
			int tag, seqNo = 0, bidPrice = 0, askPrice = 0;
			while (nRead < dataAry.length) {
				tagValue = new JABXTagValue();
				nRead += fixProc.readOneFixField(dataAry, nRead, 0, tagValue);
				tag = tagValue.getTag();
				if (tagValue.getValue() == null) {
					continue;
				}
				switch (tag) {
				case 1:// 成交序號
					seqNo = fixProc.getIntValue(tagValue.getValue());
					trade.setSeqNo(seqNo);
					break;
				case 2:// 成交時間
					trade.setTradeTime(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 3:// 成交註記
					trade.setTradeNote(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 4:// 成交價
					trade.setTradePrice(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 5:// 成交單量
					trade.setTradeVolume(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 6:// 單量內外盤
					trade.setVolumeInOutFlag(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 7:// 成交總量
					trade.setTotTradeVolume(fixProc.getLongValue(tagValue.getValue()));
					break;
				case 8:// 成交總金額
					trade.setTotTradeAmount(fixProc.getLongValue(tagValue.getValue()));
					break;
				case 9:// 成交總筆
					trade.setTotTradeCount(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 10:// 內盤總量
					trade.setTotInVolume(fixProc.getLongValue(tagValue.getValue()));
					break;
				case 11:// 外盤總量
					trade.setTotOutVolume(fixProc.getLongValue(tagValue.getValue()));
					break;
				case 12:// 委買價
					bidPrice = fixProc.getIntValue(tagValue.getValue());
					break;
				case 13:// 委賣價
					askPrice = fixProc.getIntValue(tagValue.getValue());
					break;
				}
			}

			ZJABXWatchTradeOverview tradeOverview = item.getTradeOverview();
			if (seqNo != 0) {// 將數據添加到ZJABXTradeOverview
				// 500-01-Begin: 設定ZJABXWatchTradeItem資料
				if (funcID != JABXConst.ABXFUN_REBUILD_ONEMINUTETRADE) {
					if (seqNo > stkInfo.getMaxSeqNo()) {
						stkInfo.setMaxSeqNo(seqNo);
						stkInfo.setTotalVolumn(trade.getTotTradeVolume());
						stkInfo.setTotalAmount(trade.getTotTradeAmount());
						stkInfo.setTotalCount(trade.getTotTradeCount());
						stkInfo.setTotalInVolume(trade.getTotInVolume());
						stkInfo.setTotalOutVolume(trade.getTotOutVolume());
						stkInfo.setBidPrice(bidPrice);
						stkInfo.setAskPrice(askPrice);
					}
				}
				tradeItem = new ZJABXWatchTradeItem();
				tradeItem.setSeqNo(seqNo);
				tradeItem.setTradeTime(trade.getTradeTime());
				tradeItem.setTradeNote(trade.getTradeNote());
				tradeItem.setTradePrice(trade.getTradePrice());
				tradeItem.setTradeVolume(trade.getTradeVolume());
				tradeItem.setVolumeInOutFlag(trade.getVolumeInOutFlag());
				tradeItem.setTotTradeVolume(trade.getTotTradeVolume());
				tradeItem.setTotTradeAmount(trade.getTotTradeAmount());
				tradeItem.setTotTradeCount(trade.getTotTradeCount());
				tradeItem.setTotInVolume(trade.getTotInVolume());
				tradeItem.setTotOutVolume(trade.getTotOutVolume());
				tradeItem.setBidPrice(bidPrice);
				tradeItem.setAskPrice(askPrice);
				// 500-01-End.

				if (tradeOverview != null) {
					tradeOverview.addItem(tradeItem);// 添加一筆ZJABXWatchTradeItem
				}
			}

			abyKeyStk = null;
			quoteOverview = null;
			trade = null;
			tradeItem = null;
			tradeOverview = null;
			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			if (offset == 0) {
				result.put(JS_Result.NOTIFIED, true);
			}
			jabxLog.outputErrorAndLog("JABXParseWatchTrade.parse_1()", e.getMessage());
			e.printStackTrace();
		}		
	}

	/**
	 * 處理即時資料
	 * @param fixProc - JABXFixProc
	 * @param result - JSONObject
	 * @param dataAry - byte[]
	 * @param abyKey - IJABXAbyKey
	 * @param packNo - String
	 */
	private void parse_2(final ZJABXFixProc fixProc, final JSONObject result,
			byte[] dataAry, IJABXAbyKey abyKey, String packNo) {
		ZJABXQuoteItem item = null;
		ZJABXQuoteOverview quoteOverview;
		try{
			ZJABXAbyKeyReal abyKeyStk = (ZJABXAbyKeyReal)abyKey;
			if (jabxKernel.isReservedData()) {
				quoteOverview = (ZJABXQuoteOverview)quoteTool.getQuoteOverview();
				item = (ZJABXQuoteItem)quoteOverview.atID(abyKeyStk.getAbyCode());
				if (item == null) {
					return;
				}
			}else {
				item = new ZJABXQuoteItem();
				item.setStkID(abyKeyStk.getAbyCode());
			}

			ZJABXReservedStkInfo stkInfo = quoteTool.getStkInfo(item.getStkID());
			if (stkInfo == null) {
				stkInfo = new ZJABXReservedStkInfo();
			}
			quoteTool.putStkInfoItem(item.getStkID(), stkInfo);
			
			ZJABXTrade trade = (ZJABXTrade)item.getTrade();
			if (trade == null) {
				trade = new ZJABXTrade();
				item.setTrade(trade);
			}
			StringBuffer parameterSb = new StringBuffer("0000000000000");

			ZJABXWatchTradeItem tradeItem = null;
			JABXTagValue tagValue;
			int nRead = 0;// 已讀取byte數
			int tag, seqNo = 0, bidPrice = 0, askPrice = 0;
			while (nRead < dataAry.length) {
				tagValue = new JABXTagValue();
				nRead += fixProc.readOneFixField(dataAry, nRead, 0, tagValue);
				tag = tagValue.getTag();
				if (tagValue.getValue() == null) {
					continue;
				}
				switch (tag) {
				case 1:// 成交序號
					seqNo = fixProc.getIntValue(tagValue.getValue());
					trade.setSeqNo(seqNo);
					parameterSb.replace(0, 1, "1");
					break;
				case 2:// 成交時間
					trade.setTradeTime(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(1, 2, "1");
					break;
				case 3:// 成交註記
					trade.setTradeNote(fixProc.getValue(tagValue.getValue(), ""));
					parameterSb.replace(2, 3, "1");
					break;
				case 4:// 成交價
					trade.setTradePrice(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(3, 4, "1");
					break;
				case 5:// 成交單量
					trade.setTradeVolume(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(4, 5, "1");
					break;
				case 6:// 單量內外盤
					trade.setVolumeInOutFlag(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(5, 6, "1");
					break;
				case 7:// 成交總量
					trade.setTotTradeVolume(fixProc.getLongValue(tagValue.getValue()));
					parameterSb.replace(6, 7, "1");
					break;
				case 8:// 成交總金額
					trade.setTotTradeAmount(fixProc.getLongValue(tagValue.getValue()));
					parameterSb.replace(7, 8, "1");
					break;
				case 9:// 成交總筆
					trade.setTotTradeCount(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(8, 9, "1");
					break;
				case 10:// 內盤總量
					trade.setTotInVolume(fixProc.getLongValue(tagValue.getValue()));
					parameterSb.replace(9, 10, "1");
					break;
				case 11:// 外盤總量
					trade.setTotOutVolume(fixProc.getLongValue(tagValue.getValue()));
					parameterSb.replace(10, 11, "1");
					break;
				case 12:// 委買價
					bidPrice = fixProc.getIntValue(tagValue.getValue());
					parameterSb.replace(11, 12, "1");
					break;
				case 13:// 委賣價
					askPrice = fixProc.getIntValue(tagValue.getValue());
					parameterSb.replace(12, 13, "1");
					break;
				}
			}

			ZJABXWatchTradeOverview tradeOverview;
			ZJABXMinuteTradeOverview minuteTradeOverview;
			ZJABXPriceTradeOverview priceTradeOverview;

			if (jabxKernel.isReservedData()) {
				tradeOverview = item.getTradeOverview();
				minuteTradeOverview = item.getMinuteTradeOverview();
				priceTradeOverview = item.getPriceTradeOverview();
			}else {// 不保留資料時，將資料存在JABXCore中的Map物件中
				tradeOverview = (ZJABXWatchTradeOverview)quoteTool.getWtoMapItem(item.getStkID());
				minuteTradeOverview = (ZJABXMinuteTradeOverview)quoteTool.getMtoMapItem(item.getStkID());
				priceTradeOverview = (ZJABXPriceTradeOverview)quoteTool.getPtoMapItem(item.getStkID());
			}
			if (seqNo != 0) {// 要將數據添加到ZJABXTradeOverview及ZJABXMinuteTradeOverview中
				// 500-01-Begin: 設定ZJABXWatchTradeItem資料
				stkInfo.setMaxSeqNo(seqNo);
				
				tradeItem = new ZJABXWatchTradeItem();
				tradeItem.setSeqNo(seqNo);
				if (parameterSb.charAt(1) == '1') {
					tradeItem.setTradeTime(trade.getTradeTime());
				}
				if (parameterSb.charAt(2) == '1') {
					tradeItem.setTradeNote(trade.getTradeNote());
				}
				if (parameterSb.charAt(3) == '1') {
					tradeItem.setTradePrice(trade.getTradePrice());
				}
				if (parameterSb.charAt(4) == '1') {
					tradeItem.setTradeVolume(trade.getTradeVolume());
					if (parameterSb.charAt(5) == '1') {
						tradeItem.setVolumeInOutFlag(trade.getVolumeInOutFlag());
						if (trade.getVolumeInOutFlag() == 1) {
							if (parameterSb.charAt(9) == '1') {
								tradeItem.setTotInVolume(trade.getTotInVolume());
							}else {
								tradeItem.setTotInVolume(stkInfo.getTotalInVolume() + trade.getTradeVolume());
							}
							stkInfo.setTotalInVolume(tradeItem.getTotInVolume());
						}else if (trade.getVolumeInOutFlag() == 2) {
							tradeItem.setTotOutVolume(tradeItem.getTotOutVolume() + trade.getTradeVolume());
							if (parameterSb.charAt(10) == '1') {
								tradeItem.setTotOutVolume(trade.getTotOutVolume());
							}else {
								tradeItem.setTotOutVolume(stkInfo.getTotalOutVolume() + trade.getTradeVolume());
							}
							stkInfo.setTotalOutVolume(tradeItem.getTotOutVolume());
						}
						trade.setTotInVolume(stkInfo.getTotalInVolume());
						trade.setTotOutVolume(stkInfo.getTotalOutVolume());
					}

					if (parameterSb.charAt(6) == '1') {
						tradeItem.setTotTradeVolume(trade.getTotTradeVolume());
					}else {
						tradeItem.setTotTradeVolume(stkInfo.getTotalVolumn() + trade.getTradeVolume());
					}
					stkInfo.setTotalVolumn(tradeItem.getTotTradeVolume());
					trade.setTotTradeVolume(stkInfo.getTotalVolumn());
					
					if (parameterSb.charAt(7) == '1') {
						tradeItem.setTotTradeAmount(trade.getTotTradeAmount());
					}else {
						int decimal = stkInfo.getDecimal();
						double amount = 1000d * trade.getTradePrice() * trade.getTradeVolume() / Math.pow(10, decimal);
						tradeItem.setTotTradeAmount((long)(stkInfo.getTotalAmount() + amount));
					}
					stkInfo.setTotalAmount(tradeItem.getTotTradeAmount());
					trade.setTotTradeAmount(stkInfo.getTotalAmount());

					if (parameterSb.charAt(8) == '1') {
						tradeItem.setTotTradeCount(trade.getTotTradeCount());
					}else {
						tradeItem.setTotTradeCount(stkInfo.getTotalCount() + 1);
					}
					stkInfo.setTotalCount(tradeItem.getTotTradeCount());
					trade.setTotTradeCount(stkInfo.getTotalCount());

					parameterSb.replace(5, 6, "1");
					parameterSb.replace(6, 7, "1");
					parameterSb.replace(7, 8, "1");
					parameterSb.replace(8, 9, "1");
					parameterSb.replace(9, 10, "1");
					parameterSb.replace(10, 11, "1");
				}else {
					if (parameterSb.charAt(6) == '1') {
						tradeItem.setTotTradeVolume(trade.getTotTradeVolume());
						stkInfo.setTotalVolumn(tradeItem.getTotTradeVolume());
					}
					if (parameterSb.charAt(7) == '1') {
						tradeItem.setTotTradeAmount(trade.getTotTradeAmount());
						stkInfo.setTotalAmount(tradeItem.getTotTradeAmount());
					}
					if (parameterSb.charAt(8) == '1') {
						trade.setTotTradeCount(trade.getTotTradeCount());
						stkInfo.setTotalCount(tradeItem.getTotTradeCount());
					}
					if (parameterSb.charAt(9) == '1') {
						tradeItem.setTotInVolume(trade.getTotInVolume());
						stkInfo.setTotalInVolume(tradeItem.getTotInVolume());
					}		
					if (parameterSb.charAt(10) == '1') {
						tradeItem.setTotOutVolume(trade.getTotOutVolume());
						stkInfo.setTotalOutVolume(tradeItem.getTotOutVolume());
					}
				}

				if (parameterSb.charAt(11) == '1') {
					tradeItem.setBidPrice(bidPrice);
					stkInfo.setBidPrice(bidPrice);
				}else {
					tradeItem.setBidPrice(stkInfo.getBidPrice());
				}
				if (parameterSb.charAt(12) == '1') {
					tradeItem.setAskPrice(askPrice);
					stkInfo.setAskPrice(askPrice);
				}else {
					tradeItem.setAskPrice(stkInfo.getAskPrice());
				}
				// 500-01-End.
				if (tradeOverview != null) {
					ZJABXWatchOrder_1 order_1 = (ZJABXWatchOrder_1)quoteTool.getOrder1MapItem(item.getStkID());
					if (order_1 != null) {
						tradeItem.setBidPrice(order_1.getBidPrice1());
						tradeItem.setAskPrice(order_1.getAskPrice1());
					}
					tradeOverview.addItem(tradeItem);// 添加一筆ZJABXWatchTradeItem
				}
			}

			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REALTIME);
			result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_TRADE);
			result.put(JS_Result.PARAM, parameterSb.toString());
			result.put(JS_Result.DATA, item);
			result.put(JS_Result.NOTIFIED, true);
			result.put(JS_Result.PACK_NO, packNo);

			if (seqNo != 0) {// 若seqNo不為0，要再另作通知。
				if (tradeOverview != null) {
					JSONObject anotherResult = cloneJSON(result);
					anotherResult.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_TRADE_OVERVIEW);
					anotherResult.put(JS_Result.DATA, tradeOverview);
					anotherResult.put(JS_Result.PARAM, item.getStkID());
					anotherResult.put(JS_Result.NOTIFIED, true);
					this.notifyChange(jabxKernel.getNotifier(), anotherResult);
					anotherResult = null;
				}
				if (minuteTradeOverview != null) {
					minuteTradeOverview.addRealtimeItem(item, tradeItem);// 添加一筆ZJABXWatchTradeItem
					JSONObject anotherResult = cloneJSON(result);
					anotherResult.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_MINUTETRADE_OVERVIEW);
					anotherResult.put(JS_Result.DATA, minuteTradeOverview);
					anotherResult.put(JS_Result.PARAM, item.getStkID());
					anotherResult.put(JS_Result.NOTIFIED, true);
					this.notifyChange(jabxKernel.getNotifier(), anotherResult);
					anotherResult = null;
				}
				if (priceTradeOverview != null) {
					if (seqNo > priceTradeOverview.getSeqNo()) {
						priceTradeOverview.setSeqNo(seqNo);
						priceTradeOverview.addItem(trade);// 添加一筆ZJABXPriceTradeItem
					}
					JSONObject anotherResult = cloneJSON(result);
					anotherResult.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_PRICETRADE_OVERVIEW);
					anotherResult.put(JS_Result.DATA, priceTradeOverview);
					anotherResult.put(JS_Result.PARAM, item.getStkID());
					anotherResult.put(JS_Result.NOTIFIED, true);
					this.notifyChange(jabxKernel.getNotifier(), anotherResult);
					anotherResult = null;
				}
				
				ZJABXRealtimeTechnical rtObj = quoteTool.getRtMapItem(item.getStkID());
				if (rtObj != null) {
					rtObj.calcData(jabxKernel, quoteTool, item.getStkID(), trade);
					JSONObject anotherResult = cloneJSON(result);
					anotherResult.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_REALTIME_TECHNICAL);
					anotherResult.put(JS_Result.DATA, rtObj);
					anotherResult.put(JS_Result.PARAM, item.getStkID());
					anotherResult.put(JS_Result.NOTIFIED, true);
					this.notifyChange(jabxKernel.getNotifier(), anotherResult);
					anotherResult = null;
				}
			}

			abyKeyStk = null;
			quoteOverview = null;
			trade = null;
			tradeItem = null;
			tradeOverview = null;
			tagValue = null;
			parameterSb = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseWatchTrade.parse_2()", e.getMessage());
			e.printStackTrace();
		}
	}
}