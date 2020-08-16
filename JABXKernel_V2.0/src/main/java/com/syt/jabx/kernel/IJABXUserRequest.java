package com.syt.jabx.kernel;

import java.util.List;

import com.syt.jabx.bean.JABXTagValueParam;

/**
 * 用戶資訊請求介面
 * @author jason
 *
 */
public interface IJABXUserRequest {

	/**
	 * 簽署契約申請(U20)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param param - List&lt;JABXTagValueParam&gt;<br>
	 * 目前可填的TagValue如下所示:<br>
	 * 1.交易所代碼<br>
	 * 2.期貨商代碼<br>
	 * 3.投資人帳號<br>
	 * 4.簽署類別<br>
	 * 5.申請日期<br>
	 * 6.申請人IP<br>
	 * 7.用戶ID(中菲：(309-ID) 由登入回覆中取得，N-不用驗章)<br>
	 * 90.簽章內容長度<br>
	 * 91.簽章內容(中菲：簽署類別+公司別+客戶帳號)<br>
	 */
	public void applyNoticeSign(int requestID, List<JABXTagValueParam> param);

	/**
	 * 用戶變更密碼(U02)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param execType - String(0-網路(電子)，1-語音下單，2-語音查詢，8-出金密碼，9-全部)
	 * @param loginType - int(登入方式)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;0-用戶ID<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;1-用戶名(預設)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;2-用戶昵稱<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;3-歸戶帳號(大昌)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;4-證期券商帳號(大昌))<br>
	 * @param account - String(登入資料)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;內容依登入方式而定<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當LoginType=4：＂交易所代碼|券商代碼｜下單帳號＂<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;如：”T3｜F904000｜123467”<br>
	 * @param orgPwd - String(原始登入密碼)
	 * @param newPwd - String(新登入密碼)
	 * @param loginSource - String(登入來源)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;Web_Java:30000<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;Java 手機:30002<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;Web_HTML5:30004<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;紳洋AP(暫稱):30005
	 * @param loginIP - String(登入IP)
	 */
	public void changeUserPassword(int requestID, String execType, int loginType, String account, String orgPwd, String newPwd, String loginSource, String loginIP);

	/**
	 * 用戶密碼檢核(U04)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param checkType - String(0-網路(電子)，1-語音下單，2-語音查詢，8-出金密碼，9-全部)
	 * @param loginType - int(登入方式)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;0-用戶ID<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;1-用戶名(預設)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;2-用戶昵稱<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;3-歸戶帳號(大昌)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;4-證期券商帳號(大昌))<br>
	 * @param account - String(登入資料)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;內容依登入方式而定<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當LoginType=4：＂交易所代碼|券商代碼｜下單帳號＂<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;如：”T3｜F904000｜123467”<br>
	 * @param password - String(密碼)
	 * @param pwdEncoding - String(密碼編碼方式)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;無: JABXConst.ABGW_ENCODE_NONE<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;MD5: JABXConst.ABGW_ENCODE_MD5<br>
	 * @param loginSource - String(登入來源)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;依後端交易系統而定，中菲:無。
	 * @param loginIP - String(登入IP)
	 */
	public void checkUserPassword(int requestID, String checkType, int loginType, String account, String password, int pwdEncoding, String loginSource, String loginIP);

	/**
	 * 刪除用戶系統環境設定(U07)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param sGroupID - String(用戶系統環境設定群組代碼)
	 * @param iGroupNo - int(用戶系統環境設定群組序號)
	 * @param bProductSetupType - byte(產品設定方式。0-系統預設值，1-產品id)
	 */
	public void deleteUserEnvironment(int requestID, String sGroupID, int iGroupNo, byte bProductSetupType);

	/**
	 * 查詢用戶系統環境設定內容(U05)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param sGroupID - String(用戶系統環境設定群組代碼)
	 * @param iGroupNo - int(用戶系統環境設定群組序號)
	 * @param bUserSetupType - byte(用戶設定方式。0-系統預設值，1-用戶id)
	 * @param bProductSetupType - byte(產品設定方式。0-系統預設值，1-產品id)
	 */
	public void getUserEnvironmentContent(int requestID, String sGroupID, int iGroupNo, byte bUserSetupType, byte bProductSetupType);

	/**
	 * 查詢用戶系統環境設定群組(U05)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param sGroupID - String(用戶系統環境設定群組代碼)
	 * @param bUserSetupType - byte(用戶設定方式。0-系統預設值，1-用戶id)
	 * @param bProductSetupType - byte(產品設定方式。0-系統預設值，1-產品id)
	 */
	public void getUserEnvironmentGroup(int requestID, String sGroupID, byte bUserSetupType, byte bProductSetupType);

	/**
	 * 查詢用戶系統環境設定列表(U05)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param sGroupID - String(用戶系統環境設定群組代碼)
	 * @param bUserSetupType - byte(用戶設定方式。0-系統預設值，1-用戶id)
	 * @param bProductSetupType - byte(產品設定方式。0-系統預設值，1-產品id)
	 */
	public void getUserEnvironmentList(int requestID, String sGroupID, byte bUserSetupType, byte bProductSetupType);

	/**
	 * 查詢用戶意見反饋標題(U11)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param iStart - int(要查詢的起始日期，格式為：YYYYMMDD，0表第一筆)
	 * @param iEnd - int(要查詢的結束日期，格式為：YYYYMMDD，0表最新一筆)
	 * @param bUserFlag - byte(意見反饋的用戶旗標。0-本用戶(預設)，1-全部用戶)
	 * @param bProductFlag - byte(意見反饋的產品旗標。0-本產品(預設)，1-全部產品)
	 * @param bPlatformflag - byte(意見反饋的平台旗標。0-本平台(預設)，1-全部平台)
	 */
	public void getUserFeedback(int requestID, int iStart, int iEnd, byte bUserFlag, byte bProductFlag, byte bPlatformflag);

	/**
	 * 查詢用戶意見反饋內容(U12)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param iFeedbackDate - int(意見反饋日期，格式為：YYYYMMDD)
	 * @param iFeedbackNo - int(意見反饋序號)
	 */
	public void getUserFeedbackContent(int requestID, int iFeedbackDate, int iFeedbackNo);

	/**
	 * 取得用戶資訊總覧(U03)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param loginType - int(登入方式)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;0-用戶ID<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;1-用戶名(預設)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;2-用戶昵稱<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;3-歸戶帳號(大昌)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;4-證期券商帳號(大昌)
	 * @param account - String(登入資料)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;內容依登入方式而定<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當LoginType=4：＂交易所代碼|券商代碼｜下單帳號＂<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;如：”T3｜F904000｜123467”
	 */
	public void getUserInfo(int requestID, int loginType, String account);

	/**
	 * 更新用戶系統環境設定內容(U06)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param sGroupID - String(用戶系統環境設定群組代碼)
	 * @param iGroupNo - int(用戶系統環境設定群組序號)
	 * @param sContent - String(用戶系統環境設定內容)
	 * @param bProductSetupType - byte(產品設定方式。0-系統預設值，1-產品id)
	 */
	public void setUserEnvironmentContent(int requestID, String sGroupID, int iGroupNo, String sContent, byte bProductSetupType);

	/**
	 * 更新用戶系統環境設定群組(U06)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param sGroupID - String(用戶系統環境設定群組代碼)
	 * @param iGroupNo - int(用戶系統環境設定群組序號)
	 * @param sGroupName - String(用戶系統環境設定群組名稱)
	 * @param bProductSetupType - byte(產品設定方式。0-系統預設值，1-產品id)
	 */
	public void setUserEnvironmentGroup(int requestID, String sGroupID, int iGroupNo, String sGroupName, byte bProductSetupType);

	/**
	 * 用戶警示設定刪除(U09)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param stkID - String(證券全代碼)
	 * @param groupID - String(群組代碼)
	 */
	public void warningDelete(int requestID, String stkID, String groupID);

	/**
	 * 是否啟用用戶警示設定(U09)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param onOff - String(M.啟用,D.停用)
	 */
	public void warningOnOff(int requestID, String onOff);

	/**
	 * 用戶警示設定查詢(U08)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param stkID - String(股票全代碼, 空白代表查詢全部)
	 */
	public void warningQuery(int requestID, String stkID);

	/**
	 * 用戶警示設定異動(U09)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param stkID - String(證券全代碼)
	 * @param groupID - String(群組代碼)
	 * @param warnValue - String(警示值)
	 * @param warnTime - int(警示時間，hhmm:定時警示, -1:盤中即時警示)
	 * @param warnCount - int(警示次數, 0-不限定次數,&gt;0-限定次數(每次警示過後次數會被減1, 直到最後一次警示後此筆記錄會被刪除))
	 */
	public void warningUpdate(int requestID, String stkID, String groupID, String warnValue, int warnTime, int warnCount);
}
