package com.syt.jabx.kernel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析多筆交易所統計資料訊息之類別
 * @author Jason
 *
 */
public final class JABXParseR10 extends IJABXParseBase implements IJABXParseBody {

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
	 * Socket解碼之工廠物件
	 */
	private IJABXParseFactory parseFactory;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 * @param quoteTool - JABXQuoteTool
	 * @param parseFactory - IJABXParseFactory
	 */
	public JABXParseR10(JABXKernel jabxKernel, IJABXLog jabxLog, JABXQuoteTool quoteTool, IJABXParseFactory parseFactory) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
		this.quoteTool = quoteTool;
		this.parseFactory = parseFactory;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXParseBody#parse(com.syt.jabx.kernel.ZJABXFixProc, JSONObject, byte[], com.syt.jabx.kernel.JABXCtrlHeader, int)
	 */
	@Override
	public void parse(final ZJABXFixProc fixProc, final JSONObject result,
			byte[] dataAry, JABXCtrlHeader ctrlHeader, int offset) {
		// TODO Auto-generated method stub
		/*logSb.delete(0, logSb.length());
		logSb.append(this.getClass().getSimpleName()).append(".parse()");
		try {
			jabxLog.outputInfoAndLog(logSb.toString(), new String(dataAry, JABXConst.ABX_CHARSET));
		}catch(Exception e) {
		}*/

		byte mainType = ctrlHeader.getByMsgMainType();
		int requestID = 0;// API回覆碼
		IJABXAbyKey iabyKey = ctrlHeader.getAbyKey();// 取得AbyKey
		if (mainType == JABXConst.ABUS_MAINTYPE_CONTROL) {
			JABXAbyKeyCtrl abyKey = (JABXAbyKeyCtrl)iabyKey; 
			requestID = toUsignShort(abyKey.getByAPCode());
		}

		ZJABXQuoteItem item = null;
		ZJABXWatchStatisticOverview allStatistic = null;

		String stockID = (String)jabxKernel.getInfoMapValue(String.valueOf(requestID));
		if (stockID != null && !stockID.equals("")) {
			if (jabxKernel.isReservedData()) {
				ZJABXQuoteOverview quoteOverview = (ZJABXQuoteOverview)quoteTool.getQuoteOverview();
				item = (ZJABXQuoteItem)quoteOverview.atID(stockID);
				if (item == null) {
					item = new ZJABXQuoteItem();
					item.setStkID(stockID);
				}
			}else {
				item = new ZJABXQuoteItem();
				item.setStkID(stockID);
			}

			allStatistic = item.getStatisticOverview();
			if (allStatistic == null) {// 若ZJABXAllStatistic物件不存在，則new一個物件，設定至ZJABXQuoteItem中
				allStatistic = new ZJABXWatchStatisticOverview();
				item.setStatisticOverview(allStatistic);
			}else {// 若ZJABXAllStatistic物件存在，則清除物件中之數據
				allStatistic.clear();
			}
		}else {
			return;
		}

		try{
			JABXTagValue tagValue;
			int nRead = 0;// 已讀取byte數
			int tag;
			int errorCode = 0;
			String errorDesc = "";
			int dataLength = 0;
			int fixDataLength = 0;// Fix資料讀取長度(若為0，則為正常讀法，不為0，則讀取固定長度之數據)
			// 放置ZIJABXParseBody物件之Map，若有相同之物件，可自map中取得，不用再重新new物件
			Map<String, IJABXParseBody> map = null;
			while(nRead < dataAry.length) {
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
					errorDesc = fixProc.getValue(tagValue.getValue(), "");
					break;
				case 3:// 回補資料筆數
					break;
				case 4:// 回補訊息長度
					dataLength = fixProc.getIntValue(tagValue.getValue());//訊息長度
					if (dataLength != 0) {
						fixDataLength = dataLength + 3;// 讀取回補訊息之Tag(總長度length + 3長度(5=加最後一分隔符號(0x01)，共3bytes))
					}
					break;
				case 5:// 回補訊息內容
					fixDataLength = 0;// 訊息內容讀完，要將fixDataLength重置為0
					if (map == null) {
						map = new HashMap<String, IJABXParseBody>();
					}
					byte[] rebuildAry = tagValue.getValue();//訊息內容
					parseRebuildCountData(jabxKernel, jabxLog, parseFactory, fixProc, result, rebuildAry, map, item);

					rebuildAry = null;
					break;
				}
			}

			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REBUILD_STATISTIC);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, item);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
			map = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseR10.parse()", e.getMessage());
			e.printStackTrace();
		}catch(IOException e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.IO_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseR10.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}