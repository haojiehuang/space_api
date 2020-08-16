package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析證券除權息記錄查詢之類別
 * @author Jason
 *
 */
public final class JABXParseP03 extends IJABXParseBase implements IJABXParseBody {

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
	public JABXParseP03(JABXKernel jabxKernel, IJABXLog jabxLog) {
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

		ZJABXStockDivide stockDivide = new ZJABXStockDivide();
		try{
			JABXTagValue tagValue;
			int nRead = 0;// 已讀取byte數
			int tag;
			int errorCode = 0;
			String errorDesc = "";
			stockDivide.setID((String)jabxKernel.getInfoMapValue(String.valueOf(requestID)));
			ZJABXStockDivideItem item = null;
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
				case 3:// 筆數
					break;
				case 4:// 除權息日期
					item = new ZJABXStockDivideItem();
					stockDivide.addItem(item);
					item.setDivideDate(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 5:// 派息
					if (item != null) {
						item.setDivideCash(fixProc.getValue(tagValue.getValue(), ""));
					}
					break;
				case 6:// 送股比例
					if (item != null) {
						item.setBonusRate(fixProc.getValue(tagValue.getValue(), ""));
					}
					break;
				case 7:// 轉增比例
					if (item != null) {
						item.setTransferRate(fixProc.getValue(tagValue.getValue(), ""));
					}
					break;
				case 8:// 配股比例
					if (item != null) {
						item.setExpandRate(fixProc.getValue(tagValue.getValue(), ""));
					}
					break;
				case 9:// 增發比例
					if (item != null) {
						item.setIncreaseRate(fixProc.getValue(tagValue.getValue(), ""));
					}
					break;
				case 10:// 配股價格
					if (item != null) {
						item.setExpandPrice(fixProc.getValue(tagValue.getValue(), ""));
					}
					break;
				case 11:// 增發價格
					if (item != null) {
						item.setIncreasePrice(fixProc.getValue(tagValue.getValue(), ""));
					}
					break;
				case 12:// 配股證券全代碼
					if (item != null) {
						item.setExpandID(fixProc.getValue(tagValue.getValue(), ""));
					}
					break;
				case 13:// 送股證券全代碼
					if (item != null) {
						item.setIncreaseID(fixProc.getValue(tagValue.getValue(), ""));
					}
					break;
				}
			}
			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_QUERY_STOCKDIVIDE);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, stockDivide);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseP03.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}