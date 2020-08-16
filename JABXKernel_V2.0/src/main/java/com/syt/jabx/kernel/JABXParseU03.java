package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.kmodel.IJABXUserAccountInfoItem;
import com.syt.jabx.kmodel.IJABXWDAccount;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析用戶資料查詢之類別
 * @author Jason
 *
 */
public final class JABXParseU03 extends IJABXParseBase implements IJABXParseBody {

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * Constructor
	 * @param jabxLog - IJABXLog
	 */
	public JABXParseU03(IJABXLog jabxLog) {
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

		try{
			JABXTagValue tagValue;
			int nRead = 0;// 已讀取byte數
			int tag;
			int errorCode = 0;
			String errorDesc = "";
			ZJABXUserInfoOverview uiObj = new ZJABXUserInfoOverview();
			ZJABXUserAccountInfoItem uaiItem = new ZJABXUserAccountInfoItem();
			List<IJABXUserAccountInfoItem> aiList = null;
			ZJABXWDAccount wdAccount;
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
				case 3:// 身分證號
					uiObj.setIdNo(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 4:// 客戶名稱
					uiObj.setUserName(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 5:// 身份別(戶別)
					uiObj.setUserType(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 6:// 生日
					uiObj.setBirthday(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 7: //性別
					uiObj.setSex(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 20:// 下單帳號筆數
					aiList = new ArrayList<IJABXUserAccountInfoItem>();
					uiObj.setAccountInfoList(aiList);
					break;
				case 21:// 下單帳號
					uaiItem.setTradeAccount(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 22:// IB代號
					uaiItem.setIBID(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 23:// 帳號狀態
					uaiItem.setAccountState(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 24:// 交易盤別
					uaiItem.setTradeType(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 25:// 結算幣別
					uaiItem.setSettleCurrency(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 26:// 出金銀行帳號
					List<IJABXWDAccount> withdrawList = new ArrayList<IJABXWDAccount>();
					String withdrawStr = fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET);
					String[] withdrawAry = withdrawStr.split("\\;");
					String[] waccountAry;
					for (int i = 0;i < withdrawAry.length;i++) {
						waccountAry = withdrawAry[i].split("\\|");
						if (waccountAry.length >=3) {
							wdAccount = new ZJABXWDAccount();
							wdAccount.setBankNo(waccountAry[0]);
							wdAccount.setBankAccount(waccountAry[1]);
							wdAccount.setCurrency(waccountAry[2]);
							withdrawList.add(wdAccount);
						}
					}
					
					uaiItem.setWithdrawAccountList(withdrawList);
					break;
				case 27:// 入金銀行帳號
					List<IJABXWDAccount> depositList = new ArrayList<IJABXWDAccount>();
					String depositStr = fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET);
					String[] depositAry = depositStr.split("\\;");
					String[] daccountAry;
					for (int i = 0;i < depositAry.length;i++) {
						daccountAry = depositAry[i].split("\\|");
						if (daccountAry.length >=3) {
							wdAccount = new ZJABXWDAccount();
							wdAccount.setBankNo(daccountAry[0]);
							wdAccount.setBankAccount(daccountAry[1]);
							wdAccount.setCurrency(daccountAry[2]);
							depositList.add(wdAccount);
						}
					}

					uaiItem.setDepositAccountList(depositList);
					break;
				case 28:// 開戶日期
					uaiItem.setOpenDate(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 29:// 銷戶日期
					uaiItem.setCancelDate(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 30:// 營業員
					uaiItem.setSalesID(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 31:// 市價委託控管
					uaiItem.setPriceControl(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 32:// 當沖交易同意書
					uaiItem.setOffsetConsent(fixProc.getValue(tagValue.getValue(), JABXConst.ABX_CHARSET));
					break;
				case 33:// 是否為電子戶
					uaiItem.setNetworkUser(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 34:// 帳單傳送方式
					uaiItem.setBillType(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 35:// 電子對帳單申請日期
					uaiItem.setBankStatementDate(fixProc.getIntValue(tagValue.getValue()));
					break;
				case 36:// 電子郵件地址
					uaiItem.setEmail(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 37:// 電子對帳單密碼
					uaiItem.setBankStatementPwd(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 38:// 客戶約定書簽署旗標
					uaiItem.setNoticeSign(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 39:// 電子平台使用權限
					uaiItem.setElePlatform(fixProc.getValue(tagValue.getValue(), ""));
					break;
				case 49:// 單筆交易帳號結束旗標
					if (aiList != null) {
						aiList.add(uaiItem);
					}
					uaiItem = new ZJABXUserAccountInfoItem();
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

			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_GET_USERINFO);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			result.put(JS_Result.DATA, uiObj);
			result.put(JS_Result.NOTIFIED, true);

			tagValue = null;
			uiObj = null;
			uaiItem = null;
			aiList = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseU03.parse()", e.getMessage());
			e.printStackTrace();
		}
	}
}