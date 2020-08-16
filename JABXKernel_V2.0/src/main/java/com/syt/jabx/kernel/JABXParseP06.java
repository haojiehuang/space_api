package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析個股所屬分類查詢之類別
 * @author Jason
 *
 */
public final class JABXParseP06 extends IJABXParseBase implements IJABXParseBody {

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
	public JABXParseP06(JABXKernel jabxKernel, IJABXLog jabxLog) {
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

		ZJABXStockRelationClass srcObj = new ZJABXStockRelationClass();
		try{
			JABXTagValue tagValue;
			int nRead = 0;// 已讀取byte數
			int tag;
			int errorCode = 0;
			String errorDesc = "";
			srcObj.setID((String)jabxKernel.getInfoMapValue(String.valueOf(requestID)));
			List<Integer> listOfBlockClassID = new ArrayList<Integer>();
			srcObj.setListOfBlockClassID(listOfBlockClassID);
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
				case 3:// 商品分類ID(大分類)
					srcObj.setStockClassID(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 4:// 產業分類ID(小分類)
					srcObj.setTradeClassID(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 5:// 筆數
					break;
				case 6:// 自定分類ID
					listOfBlockClassID.add(fixProc.getIntValue(tagValue.getValue()));
					break;
				}
			}
			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_QUERY_STOCKRELATIONCLASS);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, srcObj);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseP06.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}