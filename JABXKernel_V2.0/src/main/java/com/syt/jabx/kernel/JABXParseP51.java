package com.syt.jabx.kernel;

import java.util.List;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析個股歷史報價數據之類別
 * @author Jason
 *
 */
public final class JABXParseP51 extends IJABXParseBase implements IJABXParseBody {

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
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 * @param quoteTool - JABXQuoteTool
	 */
	public JABXParseP51(JABXKernel jabxKernel, IJABXLog jabxLog, JABXQuoteTool quoteTool) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
		this.quoteTool = quoteTool;
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
			jabxLog.outputInfoAndLog(logSb.toString(), new String(dataAry, JABXConst.ABX_CHARSET));
		}catch(Exception e) {
		}

		byte mainType = ctrlHeader.getByMsgMainType();
		int requestID = 0;// API回覆碼
		IJABXAbyKey iabyKey = ctrlHeader.getAbyKey();// 取得AbyKey
		if (mainType == JABXConst.ABUS_MAINTYPE_CONTROL) {
			JABXAbyKeyCtrl abyKey = (JABXAbyKeyCtrl)iabyKey; 
			requestID = toUsignShort(abyKey.getByAPCode());
		}

		// 取得查詢時所使用之參數
		@SuppressWarnings("unchecked")
		List<String> paramList = (List<String>)jabxKernel.getInfoMapValue(String.valueOf(requestID));
		int funcID = Integer.parseInt(paramList.get(0));
		String stockID = paramList.get(1);
		switch (funcID) {
		case JABXConst.ABXFUN_QUERY_HISTORYQUOTEDATA:
			int period = Integer.parseInt(paramList.get(2));
			parse_1(fixProc, result, dataAry, requestID, iabyKey, offset, stockID, period);
			break;
		case JABXConst.ABXFUN_REBUILD_MINUTETRADE:
			parse_2(fixProc, result, dataAry, requestID, iabyKey, offset, stockID);
			break;
		}
	}

	/**
	 * 依週期代碼取得交易日期
	 * @param source - long(yyyyMMddHHmm)
	 * @param sPeriod - int
	 * @return int
	 */
	private int getDate(long source, int sPeriod) {
		int target = 0;

		int period = 0;
		if (sPeriod > 200) {
			period = sPeriod - 200;
		}else if (sPeriod > 100) {
			period = sPeriod - 100;
		}else {
			period = sPeriod;
		}
		
		switch (period) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			target = (int)source;
			break;
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
			target = (int)(source / 10000);
			break;
		}

		return target;
	}

	/**
	 * 依週期代碼取得交易時間
	 * @param source - long(yyyyMMddHHmm)
	 * @param sPeriod - int
	 * @return int
	 */
	private int getTime(long source, int sPeriod) {
		int target = 0;

		int period = 0;
		if (sPeriod > 200) {
			period = sPeriod - 200;
		}else if (sPeriod > 100) {
			period = sPeriod - 100;
		}else {
			period = sPeriod;
		}
		
		switch (period) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			target = 0;
			break;
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
			target = (int)(source % 10000);
			break;
		}

		return target;
	}

	/**
	 * 依period及itemAry取得ZJABXRealtimeTechnical物件
	 * @param period - int
	 * @param itemAry - String[]
	 * @return ZJABXRealtimeTechnical
	 */
	private ZJABXRealtimeTechnical getRTObj(int period, String[] itemAry) {
		ZJABXRealtimeTechnical target = new ZJABXRealtimeTechnical();
		target.setPeriod(period);
		//"資料時間(YYYYMMDD[hhmm])|開盤價|最高價|最低價|收盤價|開盤參考價|成交量|成交金額|漲停跌符號|換手率;"
		target.setTradeDate(getDate(Long.parseLong(itemAry[0]), period));
		target.setPeriodTime(getTime(Long.parseLong(itemAry[0]), period));
		target.setOpenPrice(Integer.parseInt(itemAry[1]));
		target.setHighPrice(Integer.parseInt(itemAry[2]));
		target.setLowPrice(Integer.parseInt(itemAry[3]));
		target.setTradePrice(Integer.parseInt(itemAry[4]));
		target.setRefOpenPrice(Integer.parseInt(itemAry[5]));
		target.setTradeVolume(Long.parseLong(itemAry[6]));
		target.setTradeAmount(Long.parseLong(itemAry[7]));
		target.setChangeRate(itemAry[9]);
		return target;
	}

	/**
	 * 依股票代碼及週期代碼設定最新一筆資料
	 * @param stockID - String(股票代碼)
	 * @param period - int(週期代碼)
	 * @param data - String(資料來源)
	 */
	private void processLastData(String stockID, int period, String data) {
		ZJABXRealtimeTechnical nrtObj = new ZJABXRealtimeTechnical();
		nrtObj.setPeriod(period);
		if (data == null || data.equals("")) {
			quoteTool.putRtMapItem(stockID, nrtObj);
			return;
		}

		boolean isSetFirstData = false;
		String[] dataAry = data.split("\\;");
		String[] itemAry;
		ZJABXRealtimeTechnical nrtObj2 = null;
		for (int i = 0;i < dataAry.length;i++) {
			itemAry = dataAry[i].split("\\|");
			if (!isSetFirstData) {
				isSetFirstData = true;
				nrtObj = getRTObj(period, itemAry);
				continue;
			}
			nrtObj2 = getRTObj(period, itemAry);
			if (i == 1) {
				if (nrtObj2.getPeriodDate() > nrtObj.getPeriodDate()) {
					nrtObj = nrtObj2;
					i = dataAry.length - 2;
					continue;
				}else if (nrtObj2.getPeriodDate() == nrtObj.getPeriodDate()) {
					if (nrtObj2.getPeriodTime() > nrtObj.getPeriodTime()) {
						nrtObj = nrtObj2;
						i = dataAry.length - 2;
						continue;
					}
				}else {
					break;
				}
			}else {
				nrtObj = nrtObj2;
			}
		}

		ZJABXRealtimeTechnical rtObj = (ZJABXRealtimeTechnical)quoteTool.getRtMapItem(stockID);
		if (rtObj == null) {
			quoteTool.putRtMapItem(stockID, nrtObj);
		}else {
			if (rtObj.getPeriodDate() < nrtObj.getPeriodDate()) {
				quoteTool.putRtMapItem(stockID, nrtObj);
			}else if (rtObj.getPeriodDate() == nrtObj.getPeriodDate()) {
				if (rtObj.getPeriodTime() < nrtObj.getPeriodTime()) {
					quoteTool.putRtMapItem(stockID, nrtObj);
				}else {
					quoteTool.putRtMapItem(stockID, rtObj);
				}
			}else {
				quoteTool.putRtMapItem(stockID, rtObj);
			}
		}
	}

	/**
	 * 解析技術分析資料
	 * @param fixProc - JABXFixProc
	 * @param result - JSONObject
	 * @param dataAry - byte[]
	 * @param requestID - int
	 * @param iabyKey - IJABXAbyKey
	 * @param offset - int
	 * @param stockID - String
	 * @param period - int
	 */
	private void parse_1(final ZJABXFixProc fixProc, final JSONObject result,
			byte[] dataAry, int requestID, IJABXAbyKey iabyKey, int offset, String stockID, int period) {

		ZJABXHistoryQuoteData hqData = new ZJABXHistoryQuoteData();
		try{
			JABXTagValue tagValue;
			int nRead = 0;// 已讀取byte數
			int tag;
			int errorCode = 0;
			String errorDesc = "";
			int dataLength = 0;
			int fixDataLength = 0;// Fix資料讀取長度(若為0，則為正常讀法，不為0，則讀取固定長度之數據)
			while (nRead < dataAry.length) {
				tagValue = new JABXTagValue();
				nRead += fixProc.readOneFixField(dataAry, nRead, fixDataLength, tagValue);
				tag = tagValue.getTag();
				if (tagValue.getValue() == null) {
					continue;
				}
				switch (tag) {
				case 1:// 錯誤代碼
					errorCode = fixProc.getIntValue(tagValue.getValue());
					break;
				case 2:// 錯誤訊息
					errorDesc = fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET);
					break;
				case 3:// 資料總筆數
					hqData.setDataTotalCount(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 4:// 數據內容長度
					dataLength = fixProc.getIntValue(tagValue.getValue());// 訊息長度
					if (dataLength != 0) {
						fixDataLength = dataLength + 3;// 讀取回補訊息之Tag(總長度length + 3長度(5=加最後一分隔符號(0x01)，共3bytes))
					}
					break;
				case 5:// 數據內容
					fixDataLength = 0;// 數據內容讀完，要將fixDataLength重置為0
					byte[] rebuildAry = tagValue.getValue();// 訊息內容
					hqData.setData(new String(rebuildAry));

					processLastData(stockID, period, new String(rebuildAry));
					
					rebuildAry = null;
					break;
				}
			}
			hqData.setID(stockID);
			hqData.setPeriod(period);
			if (hqData.getData() == null || hqData.getData().equals("")) {// 解決因無歷史數據時，接收即時數據，不會更新即時K數據的問題
				ZJABXRealtimeTechnical nrtObj = new ZJABXRealtimeTechnical();
				nrtObj.setPeriod(period);
				quoteTool.putRtMapItem(stockID, nrtObj);
			}

			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_QUERY_HISTORYQUOTEDATA);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.PARAM, stockID);
			result.put(JS_Result.DATA, hqData);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseP51.parse_1()", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 解析分鐘資料
	 * @param fixProc - JABXFixProc
	 * @param result - JSONObject
	 * @param dataAry - byte[]
	 * @param requestID - int
	 * @param iabyKey - IJABXAbyKey
	 * @param offset - int
	 * @param stockID - String
	 */
	private void parse_2(final ZJABXFixProc fixProc, final JSONObject result,
			byte[] dataAry, int requestID, IJABXAbyKey iabyKey, int offset, String stockID) {

		ZJABXQuoteOverview quoteOverview = (ZJABXQuoteOverview)quoteTool.getQuoteOverview();
		ZJABXQuoteItem quoteItem = (ZJABXQuoteItem)quoteOverview.atID(stockID);
		if (quoteItem == null) {
			quoteItem = new ZJABXQuoteItem();
			quoteItem.setStkID(stockID);
			if (jabxKernel.isReservedData()) {
				quoteOverview.addItem(quoteItem);
			}
		}
		ZJABXMinuteTradeOverview mtOverview;
		if (jabxKernel.isReservedData()) {
			mtOverview = quoteItem.getMinuteTradeOverview();
			if (mtOverview == null) {
				mtOverview = new ZJABXMinuteTradeOverview(quoteTool);
			}else {
				mtOverview.clear();
			}
			quoteItem.setMinuteTradeOverview(mtOverview);
		}else {
			mtOverview = (ZJABXMinuteTradeOverview)quoteTool.getMtoMapItem(stockID);
			if (mtOverview == null) {
				mtOverview = new ZJABXMinuteTradeOverview(quoteTool);
			}else {
				mtOverview.clear();
			}
			quoteItem.setMinuteTradeOverview(mtOverview);
			quoteTool.putMtoMapItem(stockID, mtOverview);
		}

		try{
			JABXTagValue tagValue;
			int nRead = 0;// 已讀取byte數
			int tag;
			int errorCode = 0;
			String errorDesc = "";
			int dataLength = 0;
			int fixDataLength = 0;// Fix資料讀取長度(若為0，則為正常讀法，不為0，則讀取固定長度之數據)
			while (nRead < dataAry.length) {
				tagValue = new JABXTagValue();
				nRead += fixProc.readOneFixField(dataAry, nRead, fixDataLength, tagValue);
				tag = tagValue.getTag();
				if (tagValue.getValue() == null) {
					continue;
				}
				switch (tag) {
				case 1:// 錯誤代碼
					errorCode = fixProc.getIntValue(tagValue.getValue());
					break;
				case 2:// 錯誤訊息
					errorDesc = fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET);
					break;
				case 3:// 資料總筆數
					break;
				case 4:// 數據內容長度
					dataLength = fixProc.getIntValue(tagValue.getValue());// 訊息長度
					if (dataLength != 0) {
						fixDataLength = dataLength + 3;// 讀取回補訊息之Tag(總長度length + 3長度(5=加最後一分隔符號(0x01)，共3bytes))
					}
					break;
				case 5:// 數據內容
					fixDataLength = 0;// 數據內容讀完，要將fixDataLength重置為0
					setData(quoteItem, mtOverview, tagValue.getValue());
					break;
				}
			}
			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REBUILD_MINUTETRADE);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.PARAM, stockID);
			result.put(JS_Result.DATA, mtOverview);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseP51.parse_2()", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 設定數據
	 * @param quoteItem - ZJABXQuoteItem
	 * @param mtOverview - ZJABXMinuteTradeOverview
	 * @param dataAry - byte[]
	 */
	private void setData(ZJABXQuoteItem quoteItem, ZJABXMinuteTradeOverview mtOverview, byte[] dataAry) {
		ZJABXMinuteTradeItem item = null;
		String dataStr = new String(dataAry);
		String[] rowDataAry = dataStr.split("\\;");
		String[] itemDataAry;
		for (int i = 0;i < rowDataAry.length;i++) {
			itemDataAry = rowDataAry[i].split("\\|");
			if (itemDataAry.length >= 10) {
				item = new ZJABXMinuteTradeItem();
				item.setTradeDate(itemDataAry[0].substring(0, 8));
				item.setTradeTime(itemDataAry[0].substring(8));
				item.setOpenPrice(Integer.parseInt(itemDataAry[1]));
				item.setHighPrice(Integer.parseInt(itemDataAry[2]));
				item.setLowPrice(Integer.parseInt(itemDataAry[3]));
				item.setTradePrice(Integer.parseInt(itemDataAry[4]));
				item.setTradeVolume(Long.parseLong(itemDataAry[6]));
				item.setTradeAmount(Long.parseLong(itemDataAry[7]));
				item.setChangeRate(itemDataAry[9]);
				mtOverview.addRebuildItem(quoteItem, item);
			}
		}
	}
}