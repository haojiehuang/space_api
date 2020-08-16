package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 回覆查詢交易所資訊之類別
 * @author Jason
 *
 */
public final class JABXParseP90 extends IJABXParseBase implements IJABXParseBody {

	/**
	 * 應用程序核心管理物件
	 */
	private JABXKernel jabxKernel;

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 */
	public JABXParseP90(JABXKernel jabxKernel, IJABXLog jabxLog) {
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

		ZJABXExchangeInfoOverview eiObj = (ZJABXExchangeInfoOverview)jabxKernel.getExchangeInfoOverview();
		if (eiObj == null) {
			eiObj = new ZJABXExchangeInfoOverview();
			jabxKernel.setExchangeInfoOverview(eiObj);
		}

		ZJABXExchangeInfo eiItem = null;
		try{
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
					errorDesc = fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET);
					break;
				case 3:// 資料筆數
					break;
				case 4:// 交易所代碼
					eiItem = new ZJABXExchangeInfo();
					eiItem.setExchange(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 5:// 交易所名稱
					eiItem.setExchangeName(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 6:// 交易所屬性
					eiItem.setExchangeNote(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 7:// 時差
					eiItem.setTimeZone(fixProc.getShortValue(tagValue.getValue()));
					break;
				case 8:// 交易所盤前時間
					eiItem.setPreOpenTime(fixProc.getShortValue(tagValue.getValue()));
					break;
				case 9:// 交易所開盤時間
					eiItem.setOpenTime(fixProc.getShortValue(tagValue.getValue()));
					break;
				case 10:// 交易所收盤時間
					eiItem.setCloseTime(fixProc.getShortValue(tagValue.getValue()));
					break;
				case 11:// 交易所休息時間1
					eiItem.setRestTime1(fixProc.getShortValue(tagValue.getValue()));
					break;
				case 12:// 交易所再開盤時間1
					eiItem.setReOpenTime1(fixProc.getShortValue(tagValue.getValue()));
					break;
				case 13:// 交易所休息時間2
					eiItem.setRestTime2(fixProc.getShortValue(tagValue.getValue()));
					break;
				case 14:// 交易所再開盤時間2
					eiItem.setReOpenTime2(fixProc.getShortValue(tagValue.getValue()));
					break;
				case 15:// 交易所盤後交易開始時間
					eiItem.setAfterTradeStartTime(fixProc.getShortValue(tagValue.getValue()));
					break;
				case 16:// 交易所盤後交易結束時間
					eiItem.setAfterTradeEndTime(fixProc.getShortValue(tagValue.getValue()));
					break;
				case 17:// 系統強制收盤時間
					eiItem.setSysCloseTime(fixProc.getShortValue(tagValue.getValue()));
					break;
				case 18:// 代表指數證券全代碼
					eiItem.setRefIdxCode(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 19:// 小數位數
					eiItem.setDecimalShow(fixProc.getShortValue(tagValue.getValue()));
					break;
				case 20:// 交易所交易狀態
					eiItem.setTradeStatus(fixProc.getShortValue(tagValue.getValue()));
					break;
				case 21:// 市場狀態
					eiItem.setHaltStatus(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 22:// 系統狀態旗標
					eiItem.setSystemFlag(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 23:// 最新交易日期
					eiItem.setTradeDate(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 24:// 最新收盤日期
					eiItem.setCloseDate(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 25:// 下一個休假日期
					eiItem.setNonTradeDate(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 39:// 單筆結束旗標
					eiObj.addItem(eiItem);
					break;
				}
			}

			byte mainType = ctrlHeader.getByMsgMainType();
			int requestID = 0;// API回覆碼
			IJABXAbyKey iabyKey = ctrlHeader.getAbyKey();// 取得AbyKey
			if (mainType == JABXConst.ABUS_MAINTYPE_CONTROL) {
				JABXAbyKeyCtrl abyKey = (JABXAbyKeyCtrl)iabyKey; 
				requestID = toUsignShort(abyKey.getByAPCode());
			}

			// 取得查詢時所放入之物件
			Object obj = jabxKernel.getInfoMapValue(String.valueOf(requestID));
			
			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_SESSION);
			result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_LOGIN);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, obj);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseP90.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}