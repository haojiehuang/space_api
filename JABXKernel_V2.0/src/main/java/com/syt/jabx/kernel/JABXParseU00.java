package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.consts.JS_UserInfo;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析登入回傳訊息之類別
 * @author Jason
 *
 */
public final class JABXParseU00 extends IJABXParseBase implements IJABXParseBody {

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * 應用程序核心管理物件
	 */
	private JABXKernel jabxKernel;

	/**
	 * 公用工具物件
	 */
	private JABXUserTool userTool;
	
	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 * @param userTool - JABXUserTool
	 */
	public JABXParseU00(JABXKernel jabxKernel, IJABXLog jabxLog, JABXUserTool userTool) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
		this.userTool = userTool;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXParseBody#parse(com.syt.jabx.kernel.ZJABXFixProc, JSONObject, byte[], com.syt.jabx.kernel.JABXCtrlHeader, int)
	 */
	@Override
	public void parse(final ZJABXFixProc fixProc, final JSONObject result,
			byte[] dataAry, JABXCtrlHeader ctrlHeader, int offset) {
		// TODO Auto-generated method stub
		outputInfoAndLog(jabxLog, dataAry);

		String ipAndPort = (String)result.get(JS_Result.IP_PORT);
		if (ipAndPort.equals(jabxKernel.getFirstLoginServerIPAndPort())) {
			process_0(fixProc, result, dataAry, ctrlHeader, offset);// 第一次登入之處理
		}else {// 其他登入之處理
			process_1(fixProc, result, dataAry, ctrlHeader, offset);
		}
	}

	/**
	 * 第一次登入之處理
	 * @param fixProc - JABXFixProc(處理Fix數據之類別)
	 * @param resultInfo - JSONObject(訊息結果物件)
	 * @param dataAry - byte[](封包內容)
	 * @param ctrlHeader - JABXCtrlHeader(記錄Socket Header之類別)
	 * @param offset - int(偏移量)
	 */
	private void process_0(final ZJABXFixProc fixProc, final JSONObject result,
			byte[] dataAry, JABXCtrlHeader ctrlHeader, int offset) {

		try {
			JSONObject userMsg = (JSONObject)userTool.getUserMsg();
			JSONArray exchangeIDList = new JSONArray();
			userMsg.put(JS_UserInfo.F_EXCHANGEID_LIST, exchangeIDList);
			JSONArray accList = new JSONArray();
			userMsg.put(JS_UserInfo.F_ACCOUNT_LIST, accList);
			JSONArray grpAccList = new JSONArray();
			userMsg.put(JS_UserInfo.F_GROUP_ACCOUNT_LIST, grpAccList);
			ZJABXServerOverview soObj = jabxKernel.getServerOverview();
			JSONObject loginInfo = jabxKernel.getLoginInfo();
			ZJABXServerItem serverItem = null;
			JSONObject accountItem = null;
			JSONObject groupAccountItem = null;
			String str;
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
				case 3:// 交易所筆數
					break;
				case 4:// 交易所代碼
					exchangeIDList.put(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 5:// 系統日期
					userMsg.put(JS_UserInfo.F_SYSTEM_DATE, fixProc.getIntValue(tagValue.getValue()));
					break;
				case 6:// 系統時間
					userMsg.put(JS_UserInfo.F_SYSTEM_TIME, fixProc.getIntValue(tagValue.getValue()));
					break;
				case 7:// 用戶到期日
					userMsg.put(JS_UserInfo.F_EXPIRED_DATE, fixProc.getIntValue(tagValue.getValue()));
					break;
				case 8:// 本機ABusGW IP 棄用
					break;
				case 9:// 本機ABusGW Port 棄用
					break;
				case 10:// 產品功能列表
					str = fixProc.getValue(tagValue.getValue(), "");
					String[] productClassAry = str.split("\\;");
					List<String> productClassCollection = new ArrayList<String>();
					for (int i = 0;i < productClassAry.length;i++) {
						productClassCollection.add(productClassAry[i]);
					}
					jabxKernel.setListOfProductClass(productClassCollection);
					
					productClassAry = null;
					productClassCollection = null;
					break;
				case 30:// 特定主機筆數
					break;
				case 31:// ServerData(IP|PORT|主機屬性)
					serverItem = new ZJABXServerItem();
					str = fixProc.getValue(tagValue.getValue(), "");
					String[] strAry = str.trim().split("\\|");
					if (strAry.length >= 3) {
						serverItem.setHostIP(strAry[0]);
						serverItem.setHostPort(Short.parseShort(strAry[1]));
						serverItem.setHostType(Byte.parseByte(strAry[2]));
					}
					strAry = null;
					break;
				case 32:// 交易所代碼列表(兩個字位一組，無分隔符號)
					List<String> list = new ArrayList<String>();
					str = fixProc.getValue(tagValue.getValue(), "");
					StringBuffer strSb = new StringBuffer(str);
					String tmpStr;
					while (strSb.length() >= 2) {
						tmpStr = strSb.substring(0, 2);
						list.add(tmpStr);
						strSb.delete(0, 2);
					}
					serverItem.setExchangeIDCollection(list);
					break;
				case 35:// 特定主機結束旗標
					soObj.addItem(serverItem);
					serverItem = null;
					break;
				case 20:// 交易帳號筆數筆數
					break;
				case 21:// 下單帳號
					accountItem = new JSONObject();
					accountItem.put(JS_UserInfo.I_ACCOUNT, fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 22:// 其他資料
					accountItem.put(JS_UserInfo.I_MEMO, fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 24:// 單筆交易帳號結束旗標
					accList.put(accountItem);
					accountItem = null;
					break;
				case 25:// 群組交易帳號筆數
					break;
				case 26:// 下單帳號
					groupAccountItem = new JSONObject();
					groupAccountItem.put(JS_UserInfo.I_ACCOUNT, fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 27:// 其他資料
					groupAccountItem.put(JS_UserInfo.I_MEMO, fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 29:// 單筆群組交易帳號結束旗標
					grpAccList.put(groupAccountItem);
					groupAccountItem = null;
					break;
				case 40:// 數據內容長度
					dataLength = fixProc.getIntValue(tagValue.getValue());// 訊息長度
					if (dataLength != 0) {
						fixDataLength = dataLength + 3;// 讀取數據內容之Tag(總長度length + 4長度(41=加最後一分隔符號(0x01)，共4bytes))
					}
					break;
				case 41:// 數據內容
					fixDataLength = 0;// 訊息內容讀完，要將fixDataLength重置為0
					userMsg.put(JS_UserInfo.F_ORG_RESPONSE, fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				}
			}
	
			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_SESSION);
			result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_LOGIN);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, userTool.getUserInfo());
			result.put(JS_Result.NOTIFIED, true);
			if (errorCode == JABXErrCode.NO_ERROR) {
				result.put(JS_Result.NOTIFIED, false);// 此處不做通知，當下載完成P90之後再通知
				JABXAbyKeyCtrl abyKeyCtrl = (JABXAbyKeyCtrl)ctrlHeader.getAbyKey();
				// 設定default的securityKey
				loginInfo.put(JSConst.LN_SECURITYKEY, toUsignByte(abyKeyCtrl.getBySecurityKey()));
				// 設定某一伺服器的securityKey
				soObj.setSecurityKey((String)result.get(JS_Result.IP_PORT), (int)loginInfo.get(JSConst.LN_SECURITYKEY));
				
				// 600-01-Begin: 設定主機屬性為1及3的SecurityKey
				List<String> idAndPortList;
				String idAndPort;
				// 600-01-001-Begin: 設定主機屬性為1的SecurityKey
				idAndPortList = soObj.getServerListByHostType(JABXConst.SERVER_TYPE_1);
				if (idAndPortList != null && idAndPortList.size() != 0) {
					for (int i = 0, size = idAndPortList.size();i < size;i++) {
						idAndPort = idAndPortList.get(i);
						soObj.setSecurityKey(idAndPort, (int)loginInfo.get(JSConst.LN_SECURITYKEY));
					}
				}
				// 600-01-001-End.
				// 600-01-005-Begin: 設定主機屬性為3的SecurityKey
				idAndPortList = soObj.getServerListByHostType(JABXConst.SERVER_TYPE_3);
				if (idAndPortList != null && idAndPortList.size() != 0) {
					for (int i = 0, size = idAndPortList.size();i < size;i++) {
						idAndPort = idAndPortList.get(i);
						soObj.setSecurityKey(idAndPort, (int)loginInfo.get(JSConst.LN_SECURITYKEY));
					}
				}
				// 600-01-005-End.
				// 600-01-End.
				
				// 設定用戶GW帳號
				loginInfo.put(JSConst.LN_USERGWID, abyKeyCtrl.getDwUserGWID());
				JABXKernelRequest kRequest = jabxKernel.getKernelRequest();
				kRequest.downloadErrCodeFile(jabxKernel.getRequestID());// 下載ABUS錯誤代碼對照檔
				// 查詢交易所資訊
				JSONArray idList = userMsg.getJSONArray(JS_UserInfo.F_EXCHANGEID_LIST);
				if (idList != null && idList.length() != 0) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0, size = idList.length();i < size;i++) {
						sb.append(idList.get(i));
						if (i != (size - 1)) {
							sb.append(";");
						}
					}
					int requestID = jabxKernel.getRequestID();
					jabxKernel.putInfoMapItem(String.valueOf(requestID), userMsg);
					kRequest.queryExchangeInfo(requestID, sb.toString());
				}
				kRequest = null;
			}
			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseU00.process_0()", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 其他登入之處理
	 * @param fixProc - JABXFixProc(處理Fix數據之類別)
	 * @param resultInfo - JSONObject(訊息結果物件)
	 * @param dataAry - byte[](封包內容)
	 * @param ctrlHeader - JABXCtrlHeader(記錄Socket Header之類別)
	 * @param offset - int(偏移量)
	 */
	private void process_1(final ZJABXFixProc fixProc, final JSONObject result,
			byte[] dataAry, JABXCtrlHeader ctrlHeader, int offset) {

		JSONObject userMsg = (JSONObject)userTool.getUserMsg();
		try {
			ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
			JSONObject loginInfo = jabxKernel.getLoginInfo();
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
				case 3:// 交易所筆數
				case 4:// 交易所代碼
				case 5:// 系統日期
				case 6:// 系統時間
				case 7:// 用戶到期日
				case 8:// 本機ABusGW IP 棄用
				case 9:// 本機ABusGW Port 棄用
				case 10:// 產品功能列表
				case 30:// 特定主機筆數
				case 31:// ServerData(IP|PORT|主機屬性)
				case 32:// 交易所代碼列表(兩個字位一組，無分隔符號)
				case 35:// 特定主機結束旗標
				case 20:// 交易帳號筆數筆數
				case 21:// 下單帳號
				case 22:// 其他資料
				case 24:// 單筆交易帳號結束旗標
				case 25:// 群組交易帳號筆數
				case 26:// 下單帳號
				case 27:// 其他資料
				case 29:// 單筆群組交易帳號結束旗標
					break;
				case 40:// 數據內容長度
					dataLength = fixProc.getIntValue(tagValue.getValue());// 訊息長度
					if (dataLength != 0) {
						fixDataLength = dataLength + 3;// 讀取數據內容之Tag(總長度length + 4長度(41=加最後一分隔符號(0x01)，共4bytes))
					}
					break;
				case 41:// 數據內容
					fixDataLength = 0;// 訊息內容讀完，要將fixDataLength重置為0
					break;
				}
			}

			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_SESSION);
			result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_LOGIN);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, userMsg);
			result.put(JS_Result.NOTIFIED, true);
			if (errorCode == JABXErrCode.NO_ERROR) {
				JABXAbyKeyCtrl abyKeyCtrl = (JABXAbyKeyCtrl)ctrlHeader.getAbyKey();
				// 設定default的securityKey
				loginInfo.put(JSConst.LN_SECURITYKEY, toUsignByte(abyKeyCtrl.getBySecurityKey()));
				// 設定某一伺服器的securityKey
				soObj.setSecurityKey((String)result.get(JS_Result.IP_PORT), (int)loginInfo.get(JSConst.LN_SECURITYKEY));
			}
			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseU00.process_1()", e.getMessage());
			e.printStackTrace();
		}
	}
}
