package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;


/**
 * 解析用戶警示訊息之類別
 * @author Jason
 *
 */
public final class JABXParseSmartMsgWarn extends IJABXParseBase implements IJABXParseBody {

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * Constructor
	 * @param jabxLog - IJABXLog
	 */
	public JABXParseSmartMsgWarn(IJABXLog jabxLog) {
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
			if (offset != 0) {
				jabxLog.outputInfoAndLog(logSb.toString(), new String(dataAry, JABXConst.ABX_CHARSET));
			}else {
				jabxLog.outputRealtimeMsg(logSb.toString(), new String(dataAry, JABXConst.ABX_CHARSET));
			}
		}catch(Exception e) {
		}

		ZJABXSmartWarnItem item = new ZJABXSmartWarnItem();

		try{
			ZJABXAbyKeyReal abyKeyReal = (ZJABXAbyKeyReal)ctrlHeader.getAbyKey();
			String abyKeyStk = abyKeyReal.getAbyCode();
			if (abyKeyStk != null) {				
				item.setUserID(Integer.parseInt(abyKeyStk));
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
				case 1:// 股票全代碼
					item.setStkID(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 2:// 警示ID
					item.setWarnID(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 3:// 警示名稱
					item.setWarnName(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 4:// 警示說明
					item.setWarnDesc(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 5:// 用戶曾登錄的產品ID列表
					item.setProductIDs(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				}
			}

			if (offset == 0) {
				result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REALTIME);
				result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_SMARTWARN);
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
			jabxLog.outputErrorAndLog("JABXParseSmartMsgWarn.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}