package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析技術指標定義之類別
 * @author Jason
 *
 */
public final class JABXParseP56 extends IJABXParseBase implements IJABXParseBody {

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
	public JABXParseP56(JABXKernel jabxKernel, IJABXLog jabxLog) {
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

		ZJABXTechnicalIndexDefine tidData = new ZJABXTechnicalIndexDefine();
		try{
			JABXTagValue tagValue;
			int nRead = 0;// 已讀取byte數
			int tag;
			int errorCode = 0;
			String errorDesc = "";
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
					errorDesc = fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET);
					break;
				case 3:// 公式英文名稱
					tidData.setName(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 4:// 公式中文名稱
					tidData.setChineseName(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 5:// 參數設定
					tidData.setParameterSetup(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 6:// 公式註釋
					tidData.setFormulaDescription(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 7:// 參數描述
					tidData.setParameterDescription(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 8:// 預設週期
					tidData.setDefaultPeriod(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 9:// 可使用週期
					tidData.setUsedPeriod(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 10:// 限用(不可用)交易所列表
					tidData.setUnExchange(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 11:// 坐標線位置
					tidData.setScale(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 12:// 展示位置
					tidData.setPlacementsType(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 13:// 指標線設定
					tidData.setLineSetup(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
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

			// 取得查詢時所使用之參數
			Integer technicalIndexID = (Integer)jabxKernel.getInfoMapValue(String.valueOf(requestID));
			tidData.setTechnicalIndexID(technicalIndexID);

			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_QUERY_TECHNICALINDEXDEFINE);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, tidData);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseP56.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}