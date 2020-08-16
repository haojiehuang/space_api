package com.syt.jabx.kernel;

import java.io.IOException;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析用戶系統環境設定查詢之類別
 * @author Jason
 *
 */
public final class JABXParseU05 extends IJABXParseBase implements IJABXParseBody {

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * 應用程序核心管理物件
	 */
	private JABXKernel jabxKernel;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 */
	public JABXParseU05(JABXKernel jabxKernel, IJABXLog jabxLog) {
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
		outputInfoAndLog(jabxLog, dataAry);

		byte mainType = ctrlHeader.getByMsgMainType();
		int requestID = 0;// API回覆碼
		IJABXAbyKey iabyKey = ctrlHeader.getAbyKey();// 取得AbyKey
		if (mainType == JABXConst.ABUS_MAINTYPE_CONTROL) {
			JABXAbyKeyCtrl abyKey = (JABXAbyKeyCtrl)iabyKey; 
			requestID = toUsignShort(abyKey.getByAPCode());
		}

		try{
			Integer funcID = (Integer)jabxKernel.getInfoMapValue(String.valueOf(requestID));
			ZJABXUserEnvironmentList uelObj = null;
			ZJABXUserEnvironmentListItem uelItem = null;
			ZJABXUserEnvironmentGroup uegObj = null;
			ZJABXUserEnvironmentGroupItem uegItem = null;
			ZJABXUserEnvironmentContent uecObj = null;
			switch (funcID) {
			case JABXConst.ABXFUN_GET_USERENVIRONMENTLIST:
				uelObj = new ZJABXUserEnvironmentList();
				break;
			case JABXConst.ABXFUN_GET_USERENVIRONMENTGROUP:
				uegObj = new ZJABXUserEnvironmentGroup();
				break;
			case JABXConst.ABXFUN_GET_USERENVIRONMENTCONTENT:
				uecObj = new ZJABXUserEnvironmentContent();
				break;
			}
			
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
					errorDesc = fixProc.getValue(tagValue.getValue(), "");
					break;
				case 3:// 資料筆數
					break;
				case 4:// 設定群組代碼
					switch (funcID) {
					case JABXConst.ABXFUN_GET_USERENVIRONMENTLIST:
						uelObj.setGroupID(fixProc.getValue(tagValue.getValue(), ""));
						uelItem = new ZJABXUserEnvironmentListItem();
						break;
					case JABXConst.ABXFUN_GET_USERENVIRONMENTGROUP:
						uegObj.setGroupID(fixProc.getValue(tagValue.getValue(), ""));
						uegItem = new ZJABXUserEnvironmentGroupItem();
						break;
					case JABXConst.ABXFUN_GET_USERENVIRONMENTCONTENT:
						uecObj.setGroupID(fixProc.getValue(tagValue.getValue(), ""));
						break;
					}
					break;
				case 5:// 設定群組明細序號
					if (uelItem != null) {
						uelItem.setGroupNo(fixProc.getIntValue(tagValue.getValue()));
					}
					if (uegItem != null) {
						uegItem.setGroupNo(fixProc.getIntValue(tagValue.getValue()));
					}
					if (uecObj != null) {
						uecObj.setGroupNo(fixProc.getIntValue(tagValue.getValue()));
					}
					break;
				case 6:// 設定群組明細名稱
					if (uelItem != null) {
						uelItem.setGroupName(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					}
					if (uegItem != null) {
						uegItem.setGroupName(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					}
					if (uecObj != null) {
						uecObj.setGroupName(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					}
					break;
				case 7:// 設定群組明細內容長度
					dataLength = fixProc.getIntValue(tagValue.getValue());// 訊息長度
					if (dataLength != 0) {
						fixDataLength = dataLength + 3;// 讀取回補訊息之Tag(總長度length + 3長度(8=加最後一分隔符號(0x01)，共3bytes))
					}
					break;
				case 8:// 設定群組明細內容
					fixDataLength = 0;// 群組明細內容讀完，要將fixDataLength重置為0
					byte[] rebuildAry = tagValue.getValue();// 訊息內容
					switch (funcID) {
					case JABXConst.ABXFUN_GET_USERENVIRONMENTLIST:
						if (uelItem != null) {
							uelItem.setContent(new String(rebuildAry, JABXConst.ABX_CHARSET));
							uelObj.addItem(uelItem);
						}
						break;
					case JABXConst.ABXFUN_GET_USERENVIRONMENTGROUP:
						if (uegItem != null) {
							uegObj.addItem(uegItem);
						}
						break;
					case JABXConst.ABXFUN_GET_USERENVIRONMENTCONTENT:
						if (uecObj != null) {
							uecObj.setContent(new String(rebuildAry, JABXConst.ABX_CHARSET));
						}
						break;
					}

					rebuildAry = null;
					break;
				}
			}
			
			result.put(JS_Result.FUNC_ID, funcID);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			switch (funcID) {
			case JABXConst.ABXFUN_GET_USERENVIRONMENTLIST:
				result.put(JS_Result.DATA, uelObj);
				break;
			case JABXConst.ABXFUN_GET_USERENVIRONMENTGROUP:
				result.put(JS_Result.DATA, uegObj);
				break;
			case JABXConst.ABXFUN_GET_USERENVIRONMENTCONTENT:
				result.put(JS_Result.DATA, uecObj);
				break;
			}
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
			uelObj = null;
			uelItem = null;
			uegObj = null;
			uegItem = null;
			uecObj = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseU05.parse()", e.getMessage());
			e.printStackTrace();
		}catch(IOException e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.IO_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseU05.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}