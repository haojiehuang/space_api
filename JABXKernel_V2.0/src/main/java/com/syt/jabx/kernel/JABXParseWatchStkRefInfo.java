package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析股票相關資料訊息之類別
 * @author Jason
 *
 */
public final class JABXParseWatchStkRefInfo extends IJABXParseBase implements IJABXParseBody {

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
	public JABXParseWatchStkRefInfo(JABXKernel jabxKernel, IJABXLog jabxLog, JABXQuoteTool quoteTool, Object srcObj) {
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

			JSONObject refInfo = item.getStkRefInfo();
			if (refInfo == null) {
				refInfo = new JSONObject();
				item.setStkRefInfo(refInfo);
			}

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
				case 1:// 昨收價
					refInfo.put(JSConst.WATCH_YEST_PRICE, fixProc.getIntValue(tagValue.getValue()));
					break;
				case 2:// 開盤參考價
					refInfo.put(JSConst.WATCH_OPEN_REFPRICE, fixProc.getIntValue(tagValue.getValue()));
					break;
				case 3:// 漲停價
					refInfo.put(JSConst.WATCH_UPSTOP_PRICE, fixProc.getIntValue(tagValue.getValue()));
					break;
				case 4:// 跌停價
					refInfo.put(JSConst.WATCH_DOWNSTOP_PRICE, fixProc.getIntValue(tagValue.getValue()));
					break;
				case 5:// 昨收量
					refInfo.put(JSConst.WATCH_YEST_VOLUME, fixProc.getLongValue(tagValue.getValue()));
					break;
				case 6:// 昨未平倉
					refInfo.put(JSConst.WATCH_YEST_OI, fixProc.getIntValue(tagValue.getValue()));
					break;
				case 7:// 交易狀態
					refInfo.put(JSConst.WATCH_TRADE_STATUS, fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 8:// 下市/重新上市
					refInfo.put(JSConst.WATCH_LISTING, fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 9:// 最後收盤日
					refInfo.put(JSConst.WATCH_LAST_CLOSEDATE, fixProc.getIntValue(tagValue.getValue()));
					break;
				case 10:// 最後交易日
					refInfo.put(JSConst.WATCH_LAST_TRADEDATE, fixProc.getIntValue(tagValue.getValue()));
					break;
				case 11:// 最後結算日/下市日
					refInfo.put(JSConst.WATCH_DELLISTING_DATE, fixProc.getIntValue(tagValue.getValue()));
					break;
				case 12:// 檔差碼
					refInfo.put(JSConst.WATCH_SPREADTABLE_CODE, fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 13:// 所屬股票全碼
					refInfo.put(JSConst.WATCH_BELONG_FULLSTKID, fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 14:// 合約年月(一般為期貨商品)
					refInfo.put(JSConst.WATCH_CONTRACT_DATE, fixProc.getValue(tagValue.getValue(), ""));
					break;
				}
			}

			if (offset == 0) {
				result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REALTIME);
				result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_STKREFINFO);
				result.put(JS_Result.PARAM, item.getStkID());
				result.put(JS_Result.DATA, refInfo);
				result.put(JS_Result.NOTIFIED, true);

				
				if (!refInfo.isNull(JSConst.WATCH_LAST_TRADEDATE)) {
					int lastTradeDate = refInfo.getInt(JSConst.WATCH_LAST_TRADEDATE);
					ZJABXReservedStkInfo rdStkInfo = quoteTool.getStkInfo(item.getStkID());
					if (rdStkInfo == null) {
						rdStkInfo = new ZJABXReservedStkInfo();
					}
					if (rdStkInfo.getLastTradeDate() != -1) {
						if (rdStkInfo.getLastTradeDate() != lastTradeDate && jabxKernel.getApiEdition() == '0') {
							item.clear();// 因清盤，清除item(股票基本資料,股票相關資料及交易所交易狀態資料)外之所有資料
							quoteTool.clearReservedData(item.getStkID());// 清除保留資料
							JSONObject stkBase = quoteTool.getSbMapItem(item.getStkID());
							item.setStkBaseInfo(stkBase);

							result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_SESSION);
							result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_CLEAR_TRADE);
						}
					}
					rdStkInfo.setLastTradeDate(lastTradeDate);
					quoteTool.putStkInfoItem(item.getStkID(), rdStkInfo);
				}
			}else {
				if (!refInfo.isNull(JSConst.WATCH_LAST_TRADEDATE)) {
					ZJABXReservedStkInfo rdStkInfo = quoteTool.getStkInfo(item.getStkID());
					if (rdStkInfo == null) {
						rdStkInfo = new ZJABXReservedStkInfo();
					}
					rdStkInfo.setLastTradeDate(refInfo.getInt(JSConst.WATCH_LAST_TRADEDATE));
					quoteTool.putStkInfoItem(item.getStkID(), rdStkInfo);					
				}				
			}

			if (!refInfo.isNull(JSConst.WATCH_OPEN_REFPRICE)) {
				ZJABXReservedStkInfo rdStkInfo = quoteTool.getStkInfo(item.getStkID());
				if (rdStkInfo == null) {
					rdStkInfo = new ZJABXReservedStkInfo();
				}
				rdStkInfo.setOpenRefPrice(refInfo.getInt(JSConst.WATCH_OPEN_REFPRICE));
				quoteTool.putStkInfoItem(item.getStkID(), rdStkInfo);
			}

			abyKeyStk = null;
			quoteOverview = null;
			refInfo = null;
			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			if (offset == 0) {
				result.put(JS_Result.NOTIFIED, true);
			}
			jabxLog.outputErrorAndLog("JABXParseWatchStkRefInfo.parse()", e.getMessage());
			e.printStackTrace();
		}
	}

}