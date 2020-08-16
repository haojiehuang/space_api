package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析逐筆委託訊息(目前只support SZSE)之類別
 * @author Jason
 *
 */
public final class JABXParseWatchDetailOrder extends IJABXParseBase implements IJABXParseBody {

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
	 * 儲存資料源
	 */
	private Object srcObj;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 * @param quoteTool - JABXQuoteTool
	 * @param srcObj - Object
	 */
	public JABXParseWatchDetailOrder(JABXKernel jabxKernel, IJABXLog jabxLog, JABXQuoteTool quoteTool, Object srcObj) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
		this.quoteTool = quoteTool;
		this.srcObj = srcObj;
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
			if (offset != 0) {
				jabxLog.outputInfoAndLog(logSb.toString(), new String(dataAry, JABXConst.ABX_CHARSET));
			}else {
				jabxLog.outputRealtimeMsg(logSb.toString(), new String(dataAry, JABXConst.ABX_CHARSET));
			}
		}catch(Exception e) {
		}

		ZJABXQuoteItem item = null;
		ZJABXQuoteOverview quoteOverview;
		try{
			ZJABXAbyKeyReal abyKeyStk = (ZJABXAbyKeyReal)ctrlHeader.getAbyKey();
			if (srcObj instanceof ZJABXQuoteOverview) {// R00或R01
				quoteOverview = (ZJABXQuoteOverview)srcObj;
				item = (ZJABXQuoteItem)quoteOverview.atID(abyKeyStk.getAbyCode());
				if (item == null) {
					item = new ZJABXQuoteItem();
					item.setStkID(abyKeyStk.getAbyCode());
					quoteOverview.addItem(item);
				}
			}else if(srcObj instanceof ZJABXQuoteItem) {// R14
				item = (ZJABXQuoteItem)srcObj;
			}else {// 即時資料
				if (jabxKernel.isReservedData()) {
					quoteOverview = (ZJABXQuoteOverview)quoteTool.getQuoteOverview();
					item = (ZJABXQuoteItem)quoteOverview.atID(abyKeyStk.getAbyCode());
					if (item == null) {
						return;
					}
				}else {
					item = new ZJABXQuoteItem();
					item.setStkID(abyKeyStk.getAbyCode());
				}
			}
			
			ZJABXWatchDetailOrder detailOrder = item.getDetailOrder();
			if (detailOrder == null) {
				detailOrder = new ZJABXWatchDetailOrder();
				item.setDetailOrder(detailOrder);
			}

			ZJABXWatchDetailOrderOverview detailOrderDir = item.getDetailOrderOverview();
			ZJABXWatchDetailOrderItem detailOrderItem = null;
			if (detailOrderDir != null) {
				detailOrderItem = new ZJABXWatchDetailOrderItem();
			}
			StringBuffer parameterSb = new StringBuffer("000000");
			
			JABXTagValue tagValue;
			int nRead = offset;// 已讀取byte數
			int tag, seqNo = 0;
			while (nRead < dataAry.length) {
				tagValue = new JABXTagValue();
				nRead += fixProc.readOneFixField(dataAry, nRead, 0, tagValue);
				tag = tagValue.getTag();
				if (tagValue.getValue() == null) {
					continue;
				}
				switch (tag) {
				case 1:// 序號
					seqNo = fixProc.getIntValue(tagValue.getValue());
					detailOrder.setSeqNo(seqNo);
					parameterSb.replace(0, 1, "1");
					if (detailOrderItem != null) {
						detailOrderItem.setSeqNo(seqNo);
					}
					break;
				case 2:// 交易所委託序號
					detailOrder.setMarketSeqNo(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(1, 2, "1");
					if (detailOrderItem != null) {
						detailOrderItem.setMarketSeqNo(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 3:// 委託時間
					detailOrder.setTradeTime(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(2, 3, "1");
					if (detailOrderItem != null) {
						detailOrderItem.setTradeTime(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 4:// 委託價
					detailOrder.setTradePrice(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(3, 4, "1");
					if (detailOrderItem != null) {
						detailOrderItem.setTradePrice(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 5:// 委託量
					detailOrder.setTradeVolume(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(4, 5, "1");
					if (detailOrderItem != null) {
						detailOrderItem.setTradeVolume(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 6:// 委託類別
					detailOrder.setOrderType(fixProc.getValue(tagValue.getValue(), ""));
					parameterSb.replace(5, 6, "1");
					if (detailOrderItem != null) {
						detailOrderItem.setOrderType(fixProc.getValue(tagValue.getValue(), ""));
					}
					break;
				}
			}

			if (seqNo != 0) {// 代表啟動下載逐筆委託訊息，因此要將數據添加到ZJABXDetailOrderDir中
				if (detailOrderDir != null) {
					detailOrderDir.addItem(detailOrderItem);// 添加一筆ZJABXDetailOrderItem
				}
			}

			if (offset == 0) {// offset為0，代表接收即時訊息
				result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REALTIME);
				result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_DETAILORDER);
				result.put(JS_Result.PARAM, parameterSb.toString());
				result.put(JS_Result.DATA, item);
				result.put(JS_Result.NOTIFIED, true);
				// 若detailOrderDir不為null，代表收到逐筆委託訊息(目前只support SZSE)，要再另作通知。
				if (detailOrderDir != null) {
					JSONObject anotherResult = cloneJSON(result);
					anotherResult.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_DETAILORDER_OVERVIEW);
					anotherResult.put(JS_Result.DATA, item.getDetailOrderOverview());
					anotherResult.put(JS_Result.NOTIFIED, true);
					this.notifyChange(jabxKernel.getNotifier(), anotherResult);
					anotherResult = null;
				}
			}

			abyKeyStk = null;
			quoteOverview = null;
			detailOrder = null;
			detailOrderDir = null;
			detailOrderItem = null;
			tagValue = null;
			parameterSb = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			if (offset == 0) {
				result.put(JS_Result.NOTIFIED, true);
			}
			jabxLog.outputErrorAndLog("JABXParseWatchDetailOrder.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}