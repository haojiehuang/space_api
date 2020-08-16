package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析用戶警示設定查詢之類別
 * @author Jason
 *
 */
public final class JABXParseU08 extends IJABXParseBase implements IJABXParseBody {

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * 應用程序核心管理物件
	 */
	private JABXKernel jabxKernel;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 */
	public JABXParseU08(JABXKernel jabxKernel, IJABXLog jabxLog) {
		this.jabxKernel = jabxKernel;
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

		byte mainType = ctrlHeader.getByMsgMainType();
		int requestID = 0;// API回覆碼
		IJABXAbyKey iabyKey = ctrlHeader.getAbyKey();// 取得AbyKey
		if (mainType == JABXConst.ABUS_MAINTYPE_CONTROL) {
			JABXAbyKeyCtrl abyKey = (JABXAbyKeyCtrl)iabyKey; 
			requestID = toUsignShort(abyKey.getByAPCode());
		}

		try{
			ZJABXWarningOverview woObj = new ZJABXWarningOverview();
			ZJABXWarningItem item = null;
			
			JABXTagValue tagValue;
			int nRead = 0;// 已讀取byte數
			int tag;
			int errorCode = 0;
			String errorDesc = "";
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
					errorDesc = fixProc.getValue(tagValue.getValue(), "");
					break;
				case 3:// 資料筆數
					break;
				case 4:// 設定股票代碼
					item = new ZJABXWarningItem();
					item.setStkID(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 5:// 設定群組代碼
					item.setGroupID(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 6:// 設定警示值
					item.setWarnValue(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 7:// 設定警示時間
					item.setWarnTime(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 8:// 設定警示次數
					item.setWarnCount(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 9:// 設定個人化設定旗標
					item.setSetupFlag(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 19:
					woObj.addItem(item);
					break;
				}
			}

			Integer funcID = (Integer)jabxKernel.getInfoMapValue(String.valueOf(requestID));

			result.put(JS_Result.FUNC_ID, funcID);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, woObj);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
			woObj = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseU08.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}