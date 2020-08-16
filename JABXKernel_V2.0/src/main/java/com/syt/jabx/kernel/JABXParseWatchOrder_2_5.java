package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析股票第2至5檔委買資訊之類別
 * @author Jason
 *
 */
public final class JABXParseWatchOrder_2_5 extends IJABXParseBase implements IJABXParseBody {

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
	public JABXParseWatchOrder_2_5(JABXKernel jabxKernel, IJABXLog jabxLog, JABXQuoteTool quoteTool, Object srcObj) {
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

			ZJABXWatchOrder_2_5 order_2_5 = item.getOrder_2_5();
			if (order_2_5 == null) {
				order_2_5 = new ZJABXWatchOrder_2_5();
				item.setOrder_2_5(order_2_5);
			}
			StringBuffer parameterSb = new StringBuffer("0000000000000000");

			ZJABXBestOrder bestOrder = order_2_5.getBestOrder();
			ZJABXBestOrderItem orderItem;
			JABXTagValue tagValue;
			int nRead = offset;// 已讀取byte數
			int tag;
			while (nRead < dataAry.length) {
				tagValue = new JABXTagValue();
				nRead += fixProc.readOneFixField(dataAry, nRead, 0, tagValue);
				tag = tagValue.getTag();
				if (tagValue.getValue() == null) {
					continue;
				}
				switch (tag) {
				case 1:// 第2買價
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(0);
					orderItem.setBidPrice(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(0, 1, "1");
					break;
				case 2:// 第2買量
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(0);
					orderItem.setBidVolume(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(1, 2, "1");
					break;
				case 3:// 第3買價
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(1);
					orderItem.setBidPrice(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(2, 3, "1");
					break;
				case 4:// 第3買量
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(1);
					orderItem.setBidVolume(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(3, 4, "1");
					break;
				case 5:// 第4買價
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(2);
					orderItem.setBidPrice(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(4, 5, "1");
					break;
				case 6:// 第4買量
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(2);
					orderItem.setBidVolume(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(5, 6, "1");
					break;
				case 7:// 第5買價
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(3);
					orderItem.setBidPrice(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(6, 7, "1");
					break;
				case 8:// 第5買量
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(3);
					orderItem.setBidVolume(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(7, 8, "1");
					break;
				case 9:// 第2賣價
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(0);
					orderItem.setAskPrice(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(8, 9, "1");
					break;
				case 10:// 第2賣量
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(0);
					orderItem.setAskVolume(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(9, 10, "1");
					break;
				case 11:// 第3賣價
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(1);
					orderItem.setAskPrice(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(10, 11, "1");
					break;
				case 12:// 第3賣量
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(1);
					orderItem.setAskVolume(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(11, 12, "1");
					break;
				case 13:// 第4賣價
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(2);
					orderItem.setAskPrice(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(12, 13, "1");
					break;
				case 14:// 第4賣量
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(2);
					orderItem.setAskVolume(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(13, 14, "1");
					break;
				case 15:// 第5賣價
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(3);
					orderItem.setAskPrice(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(14, 15, "1");
					break;
				case 16:// 第5賣量
					orderItem = (ZJABXBestOrderItem)bestOrder.atIndex(3);
					orderItem.setAskVolume(fixProc.getIntValue(tagValue.getValue()));
					parameterSb.replace(15, 16, "1");
					break;
				}
			}
			if (offset == 0) {
				result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REALTIME);
				result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_ORDER_2_5);
				result.put(JS_Result.PARAM, parameterSb.toString());
				result.put(JS_Result.DATA, item);
				result.put(JS_Result.NOTIFIED, true);
			}

			abyKeyStk = null;
			quoteOverview = null;
			order_2_5 = null;
			orderItem = null;
			tagValue = null;
			parameterSb = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			if (offset == 0) {
				result.put(JS_Result.NOTIFIED, true);
			}
			jabxLog.outputErrorAndLog("JABXParseWatchOrder_2_5.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}