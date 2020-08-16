package com.syt.jabx.kernel;

import org.json.JSONArray;
import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析交易及帳務回覆之類別
 * @author Jason
 *
 */
public final class JABXParseTradeBills extends IJABXParseBase implements IJABXParseBody {

	/**
	 * 管理股票即時報價及歷史數據下載之主物件
	 */
	private IJABXLog jabxLog;

	/**
	 * Constructor
	 * @param jabxLog - IJABXLog
	 */
	public JABXParseTradeBills(IJABXLog jabxLog) {
		this.jabxLog = jabxLog;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXParseBody#parse(com.syt.jabx.kernel.ZJABXFixProc, JSONObject, byte[], com.syt.jabx.kernel.JABXCtrlHeader, int)
	 */
	@Override
	public void parse(final ZJABXFixProc fixProc, final JSONObject result,
			byte[] dataAry, JABXCtrlHeader ctrlHeader, int offset) {
		// TODO Auto-generated method stub
		outputInfoAndLog(jabxLog, dataAry);

		JSONArray list = new JSONArray();
		try {
			JABXTagValue tagValue;
			JSONArray jsonItem;
			int nRead = 0;// 已讀取byte數
			int tag;
			int errorCode = 0;
			int dataLength = 0;
			int fixDataLength = 0;// Fix資料讀取長度(若為0，則為正常讀法，不為0，則讀取固定長度之數據)
			String errorDesc = "";
			while (nRead < dataAry.length) {
				tagValue = new JABXTagValue();
				nRead += fixProc.readOneFixField(dataAry, nRead, fixDataLength, tagValue);
				tag = tagValue.getTag();
				if (tagValue.getValue() == null) {
					continue;
				}
				switch(tag) {
				case 1:// 回覆代碼
					errorCode = fixProc.getIntValue(tagValue.getValue());
					break;
				case 2:// 回覆錯誤訊息
					errorDesc = fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET);
					break;
				case JABXConst.FIX_DATA_LENGTH_NUM:// 資料長度
					dataLength = fixProc.getIntValue(tagValue.getValue());//訊息長度
					if (dataLength != 0) {
						fixDataLength = dataLength + String.valueOf(JABXConst.FIX_DATA_CONTENT).length() + 2;// 讀取回補訊息之Tag(總長度length + 長度(JABXConst.FIX_DATA_CONTENT=加最後一分隔符號(0x01)))
					}
					break;
				default:
					fixDataLength = 0;// 訊息內容讀完，要將fixDataLength重置為0
					jsonItem = new JSONArray();
					jsonItem.put(tagValue.getTag());
					jsonItem.put(tagValue.getValue());
					list.put(jsonItem);
					break;
				}
			}

			byte mainType = ctrlHeader.getByMsgMainType();
			int requestID = 0;// API回覆碼
			String funcCode = "";
			IJABXAbyKey iabyKey = ctrlHeader.getAbyKey();// 取得AbyKey
			if (mainType == JABXConst.ABUS_MAINTYPE_CONTROL) {
				JABXAbyKeyCtrl abyKey = (JABXAbyKeyCtrl)iabyKey; 
				requestID = toUsignShort(abyKey.getByAPCode());
				funcCode = abyKey.getAbyFuncCode();
			}

			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_TRADE_BILLS);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.PARAM, funcCode);
			result.put(JS_Result.DATA, list);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseTradeData.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}