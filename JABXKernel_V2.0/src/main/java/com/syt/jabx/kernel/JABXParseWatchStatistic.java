package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析交易所統計訊息之類別
 * @author Jason
 *
 */
public final class JABXParseWatchStatistic extends IJABXParseBase implements IJABXParseBody {

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
	public JABXParseWatchStatistic(JABXKernel jabxKernel, IJABXLog jabxLog, JABXQuoteTool quoteTool, Object srcObj) {
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
			}else if (srcObj instanceof ZJABXQuoteItem) {// R10
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
			
			ZJABXWatchStatistic statistic = item.getStatistic();
			if (statistic == null) {
				statistic = new ZJABXWatchStatistic();
				item.setStatistic(statistic);
			}
			
			ZJABXWatchStatisticOverview statisticDir = item.getStatisticOverview();
			ZJABXWatchStatisticItem statisticItem = null;
			if (statisticDir != null) {
				statisticItem = new ZJABXWatchStatisticItem();
			}
			StringBuffer parameterSb = new StringBuffer("0000000");
			
			JABXTagValue tagValue;
			int nRead = offset;// 已讀取byte數
			int tag;
			while (nRead < dataAry.length) {// 每次所有欄位都更新
				tagValue = new JABXTagValue();
				nRead += fixProc.readOneFixField(dataAry, nRead, 0, tagValue);
				tag = tagValue.getTag();
				if (tagValue.getValue() == null) {
					continue;
				}
				switch (tag) {
				case 1:// 統計時間
					statistic.setTradeTime(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(0, 1, "1");
					if (statisticItem != null) {
						statisticItem.setTradeTime(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 2:// 漲停家數
					statistic.setUpStopNum(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(1, 2, "1");
					if (statisticItem != null) {
						statisticItem.setUpStopNum(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 3:// 跌停家數
					statistic.setDownStopNum(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(2, 3, "1");
					if (statisticItem != null) {
						statisticItem.setDownStopNum(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 4:// 上漲家數
					statistic.setUpNum(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(3, 4, "1");
					if (statisticItem != null) {
						statisticItem.setUpNum(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 5:// 下跌家數
					statistic.setDownNum(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(4, 5, "1");
					if (statisticItem != null) {
						statisticItem.setDownNum(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 6:// 平盤家數
					statistic.setEqualNum(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(5, 6, "1");
					if (statisticItem != null) {
						statisticItem.setEqualNum(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 7:// 未成交家數
					statistic.setNoTradeNum(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(6, 7, "1");
					if (statisticItem != null) {
						statisticItem.setNoTradeNum(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				}
			}

			// 代表啟動下載回補交易所統計訊息數據，因此要將數據添加到ZJABXStatisticDir中
			if (statisticDir != null) {
				statisticDir.addItem(statisticItem);// 添加一筆ZJABXStatisticItem
			}

			if (offset == 0) {// offset為0，代表接收即時訊息
				result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REALTIME);
				result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_STATISTIC);
				result.put(JS_Result.PARAM, parameterSb.toString());
				result.put(JS_Result.DATA, item);
				result.put(JS_Result.NOTIFIED, true);
				// 若statisticDir不為null，代表收到即時多筆交易所統計訊息，要再另作通知。
				if (statisticDir != null) {
					JSONObject anotherResult = cloneJSON(result);
					anotherResult.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_STATISTIC_OVERVIEW);
					anotherResult.put(JS_Result.DATA, item.getStatisticOverview());
					anotherResult.put(JS_Result.NOTIFIED, true);
					this.notifyChange(jabxKernel.getNotifier(), anotherResult);
					anotherResult = null;
				}
			}

			abyKeyStk = null;
			quoteOverview = null;
			statistic = null;
			statisticDir = null;
			statisticItem = null;
			tagValue = null;
			parameterSb = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			if (offset == 0) {
				result.put(JS_Result.NOTIFIED, true);
			}
			jabxLog.outputErrorAndLog("JABXParseWatchStatistic.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}