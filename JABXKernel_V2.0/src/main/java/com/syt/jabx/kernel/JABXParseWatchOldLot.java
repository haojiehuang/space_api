package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析零股買賣成交訊息(Just for TW)之類別
 * @author Jason
 *
 */
public final class JABXParseWatchOldLot extends IJABXParseBase implements IJABXParseBody {

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
	public JABXParseWatchOldLot(JABXKernel jabxKernel, IJABXLog jabxLog, JABXQuoteTool quoteTool, Object srcObj) {
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
			}else if (srcObj instanceof ZJABXQuoteItem) {// R15
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

			ZJABXWatchOldLot oldLot = item.getOldLot();
			if (oldLot == null) {
				oldLot = new ZJABXWatchOldLot();
				item.setOldLot(oldLot);
			}
			StringBuffer parameterSb = new StringBuffer("00000000");

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
				case 1:// 委買價
					oldLot.setBidPrice(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(0, 1, "1");
					break;
				case 2:// 委買量
					oldLot.setBidVolume(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(1, 2, "1");
					break;
				case 3:// 委賣價
					oldLot.setAskPrice(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(2, 3, "1");
					break;
				case 4:// 委賣量
					oldLot.setAskVolume(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(3, 4, "1");
					break;
				case 5:// 成交時間
					oldLot.setTradeTime(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(4, 5, "1");
					break;
				case 6:// 成交價
					oldLot.setTradePrice(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(5, 6, "1");
					break;
				case 7:// 成交單量
					oldLot.setTradeVolume(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(6, 7, "1");
					break;
				case 8:// 內外盤
					oldLot.setInOutFlag(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(7, 8, "1");
					break;
				}
			}
			if (offset == 0) {
				result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REALTIME);
				result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_OLDLOT);
				result.put(JS_Result.PARAM, parameterSb.toString());
				result.put(JS_Result.DATA, item);
				result.put(JS_Result.NOTIFIED, true);
			}

			abyKeyStk = null;
			quoteOverview = null;
			oldLot = null;
			tagValue = null;
			parameterSb = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			if (offset == 0) {
				result.put(JS_Result.NOTIFIED, true);
			}
			jabxLog.outputErrorAndLog("JABXParseWatchOldLot.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}