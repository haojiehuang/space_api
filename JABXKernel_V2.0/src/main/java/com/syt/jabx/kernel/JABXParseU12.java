package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析用戶意見反饋內容之類別
 * @author Jason
 *
 */
public final class JABXParseU12 extends IJABXParseBase implements IJABXParseBody {

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
	public JABXParseU12(JABXKernel jabxKernel, IJABXLog jabxLog) {
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

		ZJABXUserFeedbackContent ufcObj = new ZJABXUserFeedbackContent();
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
				case 3:// 意見反饋日期時間
					ufcObj.setTime(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 4:// 反饋的產品代碼
				case 5:// 反饋的產品平台代碼
					break;
				case 6:// 意見反饋標題
					ufcObj.setTitle(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 7:// 意見反饋內容長度
					dataLength = fixProc.getIntValue(tagValue.getValue());// 訊息長度
					if (dataLength != 0) {
						fixDataLength = dataLength + 3;// 讀取回補訊息之Tag(總長度length + 3長度(8=加最後一分隔符號(0x01)，共3bytes))
					}
					break;
				case 8:// 意見反饋內容
					fixDataLength = 0;
					ufcObj.setContent(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 9:// 回覆日期時間
					ufcObj.setReplyTime(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 10:// 回覆內容長度
					dataLength = fixProc.getIntValue(tagValue.getValue());// 訊息長度
					if (dataLength != 0) {
						fixDataLength = dataLength + 3;// 讀取回補訊息之Tag(總長度length + 3長度(11=加最後一分隔符號(0x01)，共3bytes))
					}
					break;
				case 11:// 回覆內容
					fixDataLength = 0;
					ufcObj.setReplyContent(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
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

			// 取出所使用之功能代碼
			Integer feedbackNo = (Integer)jabxKernel.getInfoMapValue(String.valueOf(requestID));
			ufcObj.setFeedbackNo(feedbackNo);
						
			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_GET_USERFEEDBACKCONTENT);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, ufcObj);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseU12.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}