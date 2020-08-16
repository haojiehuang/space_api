package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析Heartbit之類別(API內部使用)
 * @author Jason
 *
 */
public final class JABXParseHeartbit extends ZIJABXInfoBase implements IJABXParseBody {

	/**
	 * 管理股票即時報價及歷史數據下載之主物件
	 */
	private IJABXLog jabxLog;

	/**
	 * Constructor
	 * @param jabxLog - IJABXLog
	 */
	public JABXParseHeartbit(IJABXLog jabxLog) {
		this.jabxLog = jabxLog;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXParseBody#parse(com.syt.jabx.kernel.ZJABXFixProc, JSONObject, byte[], com.syt.jabx.kernel.JABXCtrlHeader, int)
	 */
	@Override
	public void parse(final ZJABXFixProc fixProc, final JSONObject result,
			byte[] dataAry, JABXCtrlHeader ctrlHeader, int offset) {
		// TODO Auto-generated method stub
		outputInfoAndLog(jabxLog, dataAry);// Output Information

		String dateTime = "";
		try{
			JABXTagValue tagValue;
			int errorCode = 0;
			String errorDesc = "";
			int tag;
			int nRead = 0;// 已讀取byte數			
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
					errorDesc = fixProc.getValue(tagValue.getValue(), "");
					break;
				case 3:// 日期時間
					dateTime = fixProc.getValue(tagValue.getValue(), "");
					break;
				}
			}
			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_SESSION);
			result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_HEARTBIT);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, dateTime);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseHeartbit.parse()", e.getMessage());
			e.printStackTrace();
		}
	}

}
