package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析個股成交分價查詢之類別
 * @author Jason
 *
 */
public final class JABXParseP10 extends IJABXParseBase implements IJABXParseBody {

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
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 * @param quoteTool - JABXQuoteTool
	 */
	public JABXParseP10(JABXKernel jabxKernel, IJABXLog jabxLog, JABXQuoteTool quoteTool) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
		this.quoteTool = quoteTool;
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

		String stockID = (String)jabxKernel.getInfoMapValue(String.valueOf(requestID));
		if (stockID == null || stockID.equals("")) {
			return;
		}

		ZJABXQuoteItem item = null;
		ZJABXQuoteOverview stkRegQuo = (ZJABXQuoteOverview)quoteTool.getQuoteOverview();
		item = (ZJABXQuoteItem)stkRegQuo.atID(stockID);
		if (item == null) {
			item = new ZJABXQuoteItem();
			item.setStkID(stockID);
			if (jabxKernel.isReservedData()) {
				stkRegQuo.addItem(item);
			}
		}
		ZJABXPriceTradeOverview ptObj = item.getPriceTradeOverview();
		if (ptObj == null) {// 若ZJABXPriceTradeOverview物件不存在，則new一個物件，設定至ZJABXQuoteItem中
			ptObj = new ZJABXPriceTradeOverview();
			item.setPriceTradeOverview(ptObj);
		}else {// 若ZJABXPriceTradeOverview物件存在，則清除物件中之數據
			ptObj.clear();
		}

		try {
			JABXTagValue tagValue;
			int nRead = 0;// 已讀取byte數
			int tag;
			int errorCode = 0;
			String errorDesc = "";
			ZJABXPriceTradeItem tradeItem = null;
			while(nRead < dataAry.length) {
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
				case 3:// 最新成交序號
					ptObj.setSeqNo(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 4:// 分價筆數
					break;
				case 5:// 成交價
					tradeItem = new ZJABXPriceTradeItem();
					ptObj.addItem(tradeItem);
					tradeItem.setTradePrice(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 6:// 成交量
					tradeItem.setTradeVolume(fixProc.getLongValue(tagValue.getValue()));
					break;
				case 7:// 內盤量
					tradeItem.setInVolume(fixProc.getLongValue(tagValue.getValue()));
					break;
				case 8:// 外盤量
					tradeItem.setOutVolume(fixProc.getLongValue(tagValue.getValue()));
					break;
				}
			}
			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REBUILD_PRICETRADE);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, item);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseP10.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}