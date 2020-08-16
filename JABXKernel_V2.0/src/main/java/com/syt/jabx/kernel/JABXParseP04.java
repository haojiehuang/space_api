package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析開盤後新增股票查詢之類別
 * @author Jason
 *
 */
public final class JABXParseP04 extends IJABXParseBase implements IJABXParseBody {

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
	public JABXParseP04(JABXKernel jabxKernel, IJABXLog jabxLog, JABXQuoteTool quoteTool) {
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

		ZJABXStockOverview stockOverview = null;
		try {
			JABXTagValue tagValue;
			String fileName = (String)jabxKernel.getInfoMapValue(String.valueOf(requestID));
			stockOverview = (ZJABXStockOverview)jabxKernel.getInfoMapValue(requestID + "-" + fileName);
			if (stockOverview == null) {
				stockOverview = new ZJABXStockOverview();
			}
			if (jabxKernel.isReservedData()) {
				ZJABXStockCollection stockCollection = (ZJABXStockCollection)quoteTool.getStockCollection();
				stockCollection.put(fileName, stockOverview);
				stockCollection = null;
			}

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
				case 4:// 新股之股名數據
					String stockData = fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET);
					String[] strAry = stockData.split("\\|");
					if (strAry.length >= 2) {
						ZJABXStockItem item = new ZJABXStockItem();
						item.setID(strAry[0]);
						item.setName(strAry[1]);
						if (strAry.length > 2) {// 大於2代表資料中有股名拼音數據
							List<String> listOfPinying = new ArrayList<String>();
							for (int i = 2;i < strAry.length;i++) {
								listOfPinying.add(strAry[i]);
							}
							item.setListOfPinying(listOfPinying);
						}
						if (stockOverview != null) {
							stockOverview.addItem(item);
						}
					}
					break;
				}
			}
			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_DOWNLOAD_STOCKTABLE);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, stockOverview);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseP04.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}