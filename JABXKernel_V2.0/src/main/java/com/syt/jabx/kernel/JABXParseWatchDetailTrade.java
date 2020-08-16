package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析逐筆成交訊息(目前只support SSE,SZSE)之類別
 * @author Jason
 *
 */
public final class JABXParseWatchDetailTrade extends IJABXParseBase implements IJABXParseBody {

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
	public JABXParseWatchDetailTrade(JABXKernel jabxKernel, IJABXLog jabxLog, JABXQuoteTool quoteTool, Object srcObj) {
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
		logSb.delete(0, logSb.length());
		logSb.append(this.getClass().getSimpleName()).append(".parse()");
		try {
			if (offset != 0) {
				jabxLog.outputInfoAndLog(logSb.toString(), new String(dataAry, JABXConst.ABX_CHARSET));
			}else {
				jabxLog.outputRealtimeMsg(logSb.toString(), new String(dataAry, JABXConst.ABX_CHARSET));
			}
		}catch(Exception e) {
		}

		ZJABXQuoteItem item = null;
		ZJABXQuoteOverview quoteOverview;
		try{
			ZJABXAbyKeyReal abyKeyStk = (ZJABXAbyKeyReal)ctrlHeader.getAbyKey();
			if (srcObj instanceof ZJABXQuoteOverview) {// R00或R01
				quoteOverview = (ZJABXQuoteOverview)srcObj;
				item = (ZJABXQuoteItem)quoteOverview.atID(abyKeyStk.getAbyCode());
				if (item == null) {
					item = new ZJABXQuoteItem();
					item.setStkID(abyKeyStk.getAbyCode());
					quoteOverview.addItem(item);
				}
			}else if (srcObj instanceof ZJABXQuoteItem) {// R13
				item = (ZJABXQuoteItem)srcObj;
			}else {// 即時資料
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
			}

			ZJABXWatchDetailTrade detailTrade = item.getDetailTrade();
			if (detailTrade == null) {
				detailTrade = new ZJABXWatchDetailTrade();
				item.setDetailTrade(detailTrade);
			}

			ZJABXWatchDetailTradeOverview detailTradeDir = item.getDetailTradeOverview();
			ZJABXWatchDetailTradeItem detailTradeItem = null;
			if (detailTradeDir != null) {
				detailTradeItem = new ZJABXWatchDetailTradeItem();
			}
			StringBuffer parameterSb = new StringBuffer("00000000");
			
			JABXTagValue tagValue;
			int nRead = offset;// 已讀取byte數
			int tag, seqNo = 0;
			while (nRead < dataAry.length) {
				tagValue = new JABXTagValue();
				nRead += fixProc.readOneFixField(dataAry, nRead, 0, tagValue);
				tag = tagValue.getTag();
				if (tagValue.getValue() == null) {
					continue;
				}
				switch (tag) {
				case 1:// 序號
					seqNo = fixProc.getIntValue(tagValue.getValue());
					detailTrade.setSeqNo(seqNo);
					parameterSb.replace(0, 1, "1");
					if (detailTradeItem != null) {
						detailTradeItem.setSeqNo(seqNo);
					}
					break;
				case 2:// 交易所成交序號
					detailTrade.setMarketSeqNo(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(1, 2, "1");
					if (detailTradeItem != null) {
						detailTradeItem.setMarketSeqNo(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 3:// 成交時間
					detailTrade.setTradeTime(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(2, 3, "1");
					if (detailTradeItem != null) {
						detailTradeItem.setTradeTime(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 4:// 成交價
					detailTrade.setTradePrice(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(3, 4, "1");
					if (detailTradeItem != null) {
						detailTradeItem.setTradePrice(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 5:// 成交單量
					detailTrade.setTradeVolume(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(4, 5, "1");
					if (detailTradeItem != null) {
						detailTradeItem.setTradeVolume(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 6:// 成交類別
					detailTrade.setTradeType(fixProc.getValue(tagValue.getValue(), ""));
					parameterSb.replace(5, 6, "1");
					if (detailTradeItem != null) {
						detailTradeItem.setTradeType(fixProc.getValue(tagValue.getValue(), ""));
					}
					break;
				case 7:// 逐筆交易所委買序號
					detailTrade.setBidNo(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(6, 7, "1");
					if (detailTradeItem != null) {
						detailTradeItem.setBidNo(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 8:// 逐筆交易所委賣序號
					detailTrade.setAskNo(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(7, 8, "1");
					if (detailTradeItem != null) {
						detailTradeItem.setAskNo(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				}
			}

			if (seqNo != 0) {// 代表啟動下載逐筆成交訊息，因此要將數據添加到ZJABXDetailTradeDir中				
				if (detailTradeDir != null) {
					detailTradeDir.addItem(detailTradeItem);// 添加一筆ZJABXDetailTradeItem
				}
			}
			
			if (offset == 0) {// offset為0，代表接收即時訊息
				result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REALTIME);
				result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_DETAILTRADE);
				result.put(JS_Result.PARAM, parameterSb.toString());
				result.put(JS_Result.DATA, item);
				result.put(JS_Result.NOTIFIED, true);
				// 若detailTradeDir不為null，代表收到即時逐筆成交訊息(目前只support SSE, SZSE)，要再另作通知。
				if (detailTradeDir != null) {
					JSONObject anotherResult = cloneJSON(result);
					anotherResult.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_DETAILTRADE_OVERVIEW);
					anotherResult.put(JS_Result.DATA, item.getDetailTradeOverview());
					anotherResult.put(JS_Result.NOTIFIED, true);
					this.notifyChange(jabxKernel.getNotifier(), anotherResult);
					anotherResult = null;
				}
			}

			abyKeyStk = null;
			quoteOverview = null;
			detailTrade = null;
			detailTradeDir = null;
			detailTradeItem = null;
			tagValue = null;
			parameterSb = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			if (offset == 0) {
				result.put(JS_Result.NOTIFIED, true);
			}
			jabxLog.outputErrorAndLog("JABXParseWatchDetailTrade.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}