package com.syt.jabx.kmodel;

import java.util.List;

/**
 * 用戶下單帳號資訊項目之介面
 * @author jason
 *
 */
public interface IJABXUserAccountInfoItem {

	/**
	 * 取得下單帳號(格式: 交易所碼｜券商代碼｜下單帳號｜帳號名稱｜)
	 * @return String
	 */
	public String getTradeAccount();

	/**
	 * 取得IB代碼(期權才有)
	 * @return String
	 */
	public String getIBID();

	/**
	 * 取得帳號狀態(中菲期權：空白-未上傳期交所、0-開戶或基本資料異動、1-解約(銷戶)、4-凍結戶、5-違約戶)
	 * @return String
	 */
	public String getAccountState();

	/**
	 * 取得交易盤別(中菲期權：I-只可交易國內盤、O-只可交易國外盤、A-國內+外盤都可交易)
	 * @return String
	 */
	public String getTradeType();

	/**
	 * 取得結算幣別(TWD-新台幣、USD-美元、JPY-日圓、CNY-人民幣)
	 * @return String
	 */
	public String getSettleCurrency();

	/**
	 * 取得出金銀行帳號列表
	 * @return List&lt;IJABXWDAccount&gt;
	 */
	public List<IJABXWDAccount> getWithdrawAccountList();

	/**
	 * 取得入金銀行帳號列表
	 * @return List&lt;IJABXWDAccount&gt;
	 */
	public List<IJABXWDAccount> getDepositAccountList();

	/**
	 * 取得開戶日期(yyyyMMdd)
	 * @return int
	 */
	public int getOpenDate();

	/**
	 * 取得銷戶日期(yyyyMMdd)
	 * @return int
	 */
	public int getCancelDate();

	/**
	 * 取得營業員
	 * @return String
	 */
	public String getSalesID();

	/**
	 * 取得市價委託控管(Y/N)
	 * @return String
	 */
	public String getPriceControl();

	/**
	 * 取得當沖交易同意書(N-未簽訂、Y-已簽訂)
	 * @return String
	 */
	public String getOffsetConsent();

	/**
	 * 取得是否為電子戶(Y/N)
	 * @return String
	 */
	public String getNetworkUser();

	/**
	 * 取得帳單傳送方式(中菲：Y-自取、M-通訊、R-戶籍、S-指定、E-EMAIL、N-不寄)
	 * @return String
	 */
	public String getBillType();

	/**
	 * 取得電子對帳單申請日期(yyyyMMdd)
	 * @return int
	 */
	public int getBankStatementDate();

	/**
	 * 取得電子郵件地址
	 * @return String
	 */
	public String getEmail();

	/**
	 * 取得電子對帳單密碼(中菲期權：身份證後4 碼+出生月日4碼)
	 * @return String
	 */
	public String getBankStatementPwd();

	/**
	 * 取得客戶約定書簽署旗標(中菲期權：Y/N，每個位元對應功能309的簽署類別。)<br>
	 * 例如簽署類別為「01」，對應到此欄位的第1位元
	 * @return String
	 */
	public String getNoticeSign();

	/**
	 * 取得電子平台使用權限(中菲期權：Y/N，每位元代表一個平台)
	 * @return String
	 */
	public String getElePlatform();

}
