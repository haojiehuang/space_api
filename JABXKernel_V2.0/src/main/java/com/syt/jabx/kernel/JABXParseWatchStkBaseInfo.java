package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析股票基本資料訊息之類別
 * @author Jason
 *
 */
public final class JABXParseWatchStkBaseInfo extends IJABXParseBase implements IJABXParseBody {

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
	public JABXParseWatchStkBaseInfo(JABXKernel jabxKernel, IJABXLog jabxLog, JABXQuoteTool quoteTool, Object srcObj) {
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

			JSONObject baseInfo = (JSONObject)quoteTool.getSbMapItem(item.getStkID());
			if (baseInfo == null) {
				baseInfo = new JSONObject();
			}
			item.setStkBaseInfo(baseInfo);
			quoteTool.putSbMapItem(item.getStkID(), baseInfo);

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
				case 1:// 股票繁中名稱
					baseInfo.put(JSConst.WATCH_STK_NAMET, fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 2:// 股票簡中名稱
					baseInfo.put(JSConst.WATCH_STK_NAMES, fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 3:// 股票英文名稱
					baseInfo.put(JSConst.WATCH_STK_NAMEE, fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 4:// 履約價
					baseInfo.put(JSConst.WATCH_STRIKE_PRICE, fixProc.getIntValue(tagValue.getValue()));
					break;
				case 5:// 商品代碼
					baseInfo.put(JSConst.WATCH_CLASS_TYPE, fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 6:// 商品群組代碼
					baseInfo.put(JSConst.WATCH_CLASS_GROUPID, fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 7:// 產業代碼
					baseInfo.put(JSConst.WATCH_TRADE_TYPE, fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 8:// 產業群組代碼
					baseInfo.put(JSConst.WATCH_TRADE_GROUPID, fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 9:// 交易代號
					baseInfo.put(JSConst.WATCH_TRADE_CODE, fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 10:// 小數位數或分數之分母
					baseInfo.put(JSConst.WATCH_DECIMAL, fixProc.getIntValue(tagValue.getValue()));
					break;
				case 11:// 證券屬性
					baseInfo.put(JSConst.WATCH_STK_NOTE, fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 12:// 交易單位
					baseInfo.put(JSConst.WATCH_LOT_SIZE, fixProc.getIntValue(tagValue.getValue()));
					break;
				case 13:// 時區
					baseInfo.put(JSConst.WATCH_TIME_ZONE, fixProc.getByteValue(tagValue.getValue()));
					break;
				case 14:// 開盤時間
					baseInfo.put(JSConst.WATCH_OPEN_HHMM, fixProc.getShortValue(tagValue.getValue()));
					break;
				case 15:// 收盤時間
					baseInfo.put(JSConst.WATCH_CLOSE_HHMM, fixProc.getShortValue(tagValue.getValue()));
					break;
				case 16:// 休息時間1
					baseInfo.put(JSConst.WATCH_REST_HHMM1, fixProc.getShortValue(tagValue.getValue()));
					break;
				case 17:// 再開盤時間1
					baseInfo.put(JSConst.WATCH_REOPEN_HHMM1, fixProc.getShortValue(tagValue.getValue()));
					break;
				case 18:// 休息時間2
					baseInfo.put(JSConst.WATCH_REST_HHMM2, fixProc.getShortValue(tagValue.getValue()));
					break;
				case 19:// 再開盤時間2
					baseInfo.put(JSConst.WATCH_REOPEN_HHMM2, fixProc.getShortValue(tagValue.getValue()));
					break;
				case 20:// 假開盤時間
					baseInfo.put(JSConst.WATCH_PREOPEN_HHMM, fixProc.getShortValue(tagValue.getValue()));
					break;
				case 21:// 檔差
					baseInfo.put(JSConst.WATCH_TICK_DIFF, fixProc.getIntValue(tagValue.getValue()));
					break;
				case 22:// 交易幣別
					baseInfo.put(JSConst.WATCH_CURRENCY_CODE, fixProc.getValue(tagValue.getValue(), ""));
					break;
				}
			}

			ZJABXReservedStkInfo stkInfo = quoteTool.getStkInfo(item.getStkID());
			if (stkInfo == null) {
				stkInfo = new ZJABXReservedStkInfo();
			}
			if (!baseInfo.isNull(JSConst.WATCH_OPEN_HHMM)) {
				stkInfo.setOpenTime((short)baseInfo.getInt(JSConst.WATCH_OPEN_HHMM));
			}
			if (!baseInfo.isNull(JSConst.WATCH_CLOSE_HHMM)) {
				stkInfo.setCloseTime((short)baseInfo.getInt(JSConst.WATCH_CLOSE_HHMM));
			}
			if (!baseInfo.isNull(JSConst.WATCH_DECIMAL)) {
				stkInfo.setDecimal(baseInfo.getInt(JSConst.WATCH_DECIMAL));
			}
			quoteTool.putStkInfoItem(item.getStkID(), stkInfo);
			
			if (offset == 0) {
				result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REALTIME);
				result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_STKBASEINFO);
				result.put(JS_Result.PARAM, item.getStkID());
				result.put(JS_Result.DATA, baseInfo);
				result.put(JS_Result.NOTIFIED, true);
			}

			abyKeyStk = null;
			quoteOverview = null;
			baseInfo = null;
			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			if (offset == 0) {
				result.put(JS_Result.NOTIFIED, true);
			}
			jabxLog.outputErrorAndLog("JABXParseWatchStkBaseInfo.parse()", e.getMessage());
			e.printStackTrace();
		}
	}

}
