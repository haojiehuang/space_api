package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析專家選股查詢之類別
 * @author Jason
 *
 */
public final class JABXParseI10 extends IJABXParseBase implements IJABXParseBody {

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * Constructor
	 * @param jabxLog - IJABXLog
	 */
	public JABXParseI10(IJABXLog jabxLog) {
		this.jabxLog = jabxLog;
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

		ZJABXExpertSelect esObj = new ZJABXExpertSelect();
		try{
			JABXTagValue tagValue;
			int nRead = 0;// 已讀取byte數
			int tag;
			int errorCode = 0;
			String errorDesc = "";
			
			ZJABXExpertSelectItem esItem = null;
			while (nRead < dataAry.length) {
				tagValue = new JABXTagValue();
				nRead += fixProc.readOneFixField(dataAry, nRead, 0, tagValue);
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
				case 3:// 資料筆數
					break;
				case 4:// 選股推薦日期
					esItem = new ZJABXExpertSelectItem();
					esObj.addItem(esItem);
					esItem.setSelectDate(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 5:// 股票全代碼
					if (esItem != null) {
						esItem.setID(fixProc.getValue(tagValue.getValue(), ""));
					}
					break;
				case 6:// 股票簡稱
					if (esItem != null) {
						esItem.setName(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					}
					break;
				case 7:// 收盤價
					if (esItem != null) {
						esItem.setClosePrice(fixProc.getValue(tagValue.getValue(), ""));
					}
					break;
				case 8:// 壓力價
					if (esItem != null) {
						esItem.setHighPrice(fixProc.getValue(tagValue.getValue(), ""));
					}
					break;
				case 9:// 支撐價
					if (esItem != null) {
						esItem.setLowPrice(fixProc.getValue(tagValue.getValue(), ""));
					}
					break;
				case 10:// 選股說明
					if (esItem != null) {
						esItem.setName(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					}
					break;
				}
			}
			esItem = null;

			byte mainType = ctrlHeader.getByMsgMainType();
			int requestID = 0;// API回覆碼
			IJABXAbyKey iabyKey = ctrlHeader.getAbyKey();// 取得AbyKey
			if (mainType == JABXConst.ABUS_MAINTYPE_CONTROL) {
				JABXAbyKeyCtrl abyKey = (JABXAbyKeyCtrl)iabyKey; 
				requestID = toUsignShort(abyKey.getByAPCode());
			}

			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_QUERY_EXPERTSELECT);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, esObj);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseI10.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}