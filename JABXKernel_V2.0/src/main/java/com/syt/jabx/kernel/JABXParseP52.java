package com.syt.jabx.kernel;

import java.util.List;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析技術指標數據首次查詢之類別
 * @author Jason
 *
 */
public final class JABXParseP52 extends IJABXParseBase implements IJABXParseBody {

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
	public JABXParseP52(JABXKernel jabxKernel, IJABXLog jabxLog) {
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

		ZJABXTechnicalIndexData tiData = new ZJABXTechnicalIndexData();
		try{
			JABXTagValue tagValue;
			int nRead = 0;// 已讀取byte數
			int tag;
			int errorCode = 0;
			String errorDesc = "";
			int dataLength = 0;
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
				case 3:// 指標記錄序號
					tiData.setLineID(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 4:// 指標線設定
					tiData.setLineSetup(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 5:// 資料總筆數
					tiData.setDataTotalCount(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 6:// 數據內容長度
					dataLength = fixProc.getIntValue(tagValue.getValue());// 訊息長度
					if (dataLength != 0) {
						fixDataLength = dataLength + 3;// 讀取回補訊息之Tag(總長度length + 3長度(7=加最後一分隔符號(0x01)，共3bytes))
					}
					break;
				case 7:// 數據內容
					fixDataLength = 0;// 訊息內容讀完，要將fixDataLength重置為0
					tiData.setData(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
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
			@SuppressWarnings("unchecked")
			List<String> paramList = (List<String>)jabxKernel.getInfoMapValue(String.valueOf(requestID));
			String stockID = paramList.get(0);
			int technicalIndexID = Integer.parseInt(paramList.get(1));
			int period = Integer.parseInt(paramList.get(2));
			tiData.setID(stockID);
			tiData.setTechnicalIndexID(technicalIndexID);
			tiData.setPeriod(period);
			paramList = null;

			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_QUERY_TECHNICALINDEXDATA);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, tiData);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseP52.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}