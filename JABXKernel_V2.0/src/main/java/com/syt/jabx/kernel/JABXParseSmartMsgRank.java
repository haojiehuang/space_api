package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析即時排名訊息之類別
 * @author Jason
 *
 */
public final class JABXParseSmartMsgRank extends IJABXParseBase implements IJABXParseBody {

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * 儲存資料源
	 */
	private Object srcObj;

	/**
	 * Constructor
	 * @param jabxLog - IJABXLog
	 * @param srcObj - Object
	 */
	public JABXParseSmartMsgRank(IJABXLog jabxLog, Object srcObj) {
		this.jabxLog = jabxLog;
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

		ZJABXSmartRankItem item = new ZJABXSmartRankItem();
		if (srcObj instanceof ZJABXSmartRankOverview) {// R51
			ZJABXSmartRankOverview ssoObj = (ZJABXSmartRankOverview)srcObj;
			ssoObj.addItem(item);
		}
		try{
			ZJABXAbyKeyReal abyKeyReal = (ZJABXAbyKeyReal)ctrlHeader.getAbyKey();
			String abyKeyStk = abyKeyReal.getAbyCode();
			if (abyKeyStk != null && abyKeyStk.length() > 2) {				
				String exchangeID = abyKeyStk.substring(0, 2);
				String classID = abyKeyStk.substring(2);
				item.setClassID(classID);
				item.setExchangeID(exchangeID);
			}
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
				case 1:// 證券代碼
					item.setStockCode(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 2:// 排名
					item.setRank(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 3:// 成交價
					item.setTradePrice(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 4:// 成交價漲趺
					item.setPriceType(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 5:// 排名值
					item.setRankValue(fixProc.getIntValue(tagValue.getValue()));
					break;
				}
			}

			if (offset == 0) {
				result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REALTIME);
				result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_SMARTRANK);
				result.put(JS_Result.DATA, item);
				result.put(JS_Result.NOTIFIED, true);
			}

			abyKeyReal = null;
			abyKeyStk = null;
			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			if (offset == 0) {
				result.put(JS_Result.NOTIFIED, true);
			}
			jabxLog.outputErrorAndLog("JABXParseSmartMsgRank.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}