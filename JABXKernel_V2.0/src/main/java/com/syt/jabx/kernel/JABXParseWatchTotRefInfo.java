package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析總委買賣量筆及成交筆資訊之類別
 * @author Jason
 *
 */
public final class JABXParseWatchTotRefInfo extends IJABXParseBase implements IJABXParseBody {

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
	public JABXParseWatchTotRefInfo(JABXKernel jabxKernel, IJABXLog jabxLog, JABXQuoteTool quoteTool, Object srcObj) {
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
			}else if (srcObj instanceof ZJABXQuoteItem) {// R07
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

			ZJABXWatchTotRefInfo totRefInfo = item.getTotRefInfo();
			if (totRefInfo == null) {
				totRefInfo = new ZJABXWatchTotRefInfo();
				item.setTotRefInfo(totRefInfo);
			}

			ZJABXWatchTotRefInfoOverview totRefInfoDir = item.getTotRefInfoOverview();
			ZJABXWatchTotRefInfoItem totRefInfoItem = null;
			if (totRefInfoDir != null) {
				totRefInfoItem = new ZJABXWatchTotRefInfoItem();
			}
			StringBuffer parameterSb = new StringBuffer("0000000");

			JABXTagValue tagValue;
			int nRead = offset;// 已讀取byte數
			int tag;
			while (nRead < dataAry.length) {
				tagValue = new JABXTagValue();
				nRead += fixProc.readOneFixField(dataAry, nRead, 0, tagValue);
				tag = tagValue.getTag();
				if (tagValue.getValue() == null) {
					continue;
				}
				switch (tag) {
				case 1:// 統計時間
					totRefInfo.setTradeTime(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(0, 1, "1");
					if (totRefInfoItem != null) {
						totRefInfoItem.setTradeTime(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 2:// 委賣總量
					totRefInfo.setTotAskVolume(fixProc.getLongValue(tagValue.getValue()));
					parameterSb.replace(1, 2, "1");
					if (totRefInfoItem != null) {
						totRefInfoItem.setTotAskVolume(fixProc.getLongValue(tagValue.getValue()));
					}
					break;
				case 3:// 委賣總筆
					totRefInfo.setTotAskCount(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(2, 3, "1");
					if (totRefInfoItem != null) {
						totRefInfoItem.setTotAskCount(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 4:// 委買總量
					totRefInfo.setTotBidVolume(fixProc.getLongValue(tagValue.getValue()));
					parameterSb.replace(3, 4, "1");
					if (totRefInfoItem != null) {
						totRefInfoItem.setTotBidVolume(fixProc.getLongValue(tagValue.getValue()));
					}
					break;
				case 5:// 委買總筆
					totRefInfo.setTotBidCount(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(4, 5, "1");
					if (totRefInfoItem != null) {
						totRefInfoItem.setTotBidCount(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 6:// 買進成交總筆
					totRefInfo.setTotBidTradeCount(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(5, 6, "1");
					if (totRefInfoItem != null) {
						totRefInfoItem.setTotBidTradeCount(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 7:// 賣出成交總筆
					totRefInfo.setTotAskTradeCount(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(6, 7, "1");
					if (totRefInfoItem != null) {
						totRefInfoItem.setTotAskTradeCount(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 8:// 成交總筆
					if (totRefInfoItem != null) {
						totRefInfoItem.setTotTradeCount(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 9:// 成交總量
					if (totRefInfoItem != null) {
						totRefInfoItem.setTotTradeVolume(fixProc.getLongValue(tagValue.getValue()));
					}
					break;
				case 10:// 成交總金額
					if (totRefInfoItem != null) {
						totRefInfoItem.setTotTradeAmount(fixProc.getLongValue(tagValue.getValue()));
					}
					break;
				case 11:// 成交價
					if (totRefInfoItem != null) {
						totRefInfoItem.setTradePrice(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				}
			}

			/*
			 * 代表啟動下載回補多筆總委買賣量筆及成交筆資訊數據，
			 * 因此要將數據添加到ZJABXTotRefInfoDir中
			 */
			if (totRefInfoDir != null) {
				totRefInfoDir.addItem(totRefInfoItem);//添加一筆ZJABXTotRefInfoItem
			}

			if (offset == 0) {// offset為0，代表接收即時訊息
				result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REALTIME);
				result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_TOTREFINFO);
				result.put(JS_Result.PARAM, parameterSb.toString());
				result.put(JS_Result.DATA, item);
				result.put(JS_Result.NOTIFIED, true);
				// 若totRefInfoDir不為null，代表收到即時多筆總委買賣量筆及成交筆資訊，要再另作通知。
				if (totRefInfoDir != null) {
					JSONObject anotherResult = cloneJSON(result);
					anotherResult.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_TOTREFINF_OVERVIEW);
					anotherResult.put(JS_Result.DATA, item.getTotRefInfoOverview());
					anotherResult.put(JS_Result.NOTIFIED, true);
					this.notifyChange(jabxKernel.getNotifier(), anotherResult);
					anotherResult = null;
				}
			}

			abyKeyStk = null;
			quoteOverview = null;
			totRefInfo = null;
			totRefInfoDir = null;
			totRefInfoItem = null;
			tagValue = null;
			parameterSb = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			if (offset == 0) {
				result.put(JS_Result.NOTIFIED, true);
			}
			jabxLog.outputErrorAndLog("JABXParseWatchTotRefInfo.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}