package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析證券分類個股列表查詢之類別
 * @author Jason
 *
 */
public final class JABXParseP07 extends IJABXParseBase implements IJABXParseBody {

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
	public JABXParseP07(JABXKernel jabxKernel, IJABXLog jabxLog) {
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

		ZJABXClassRelationStock crsObj = new ZJABXClassRelationStock();
		try{
			JABXTagValue tagValue;
			int nRead = 0;// 已讀取byte數
			int tag;
			int errorCode = 0;
			String errorDesc = "";
			crsObj.setClassID((Integer)jabxKernel.getInfoMapValue(String.valueOf(requestID)));
			ZJABXClassRelationStockItem item = null;
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
				case 4:// 證券全代碼
					item = new ZJABXClassRelationStockItem();
					item.setStockID(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 5:// 下單交易代碼
					item.setTradeID(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 6:// 履約價(取得的值為實際的履約價乘以10000的值)
					item.setPrice(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 7:// 報價代碼
					item.setQuoteID(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 8:// 契約年月
					item.setSettleMonth(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 10:// 買賣權
					item.setCallPut(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 11:// 證券名稱
					item.setName(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 9:// 單筆結束旗標
					if (item != null) {
						crsObj.addItem(item);
					}
					break;
				}
			}
			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_QUERY_CLASSRELATIONSTOCK);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, crsObj);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseP07.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}