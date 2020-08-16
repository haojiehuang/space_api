package com.syt.jabx.kernel;

import java.util.List;

import com.syt.jabx.kmodel.IJABXUserAccountInfoItem;
import com.syt.jabx.kmodel.IJABXWDAccount;

/**
 * 用戶下單帳號資訊項目之類別
 * @author jason
 *
 */
final class ZJABXUserAccountInfoItem implements IJABXUserAccountInfoItem {

	/**
	 * 下單帳號
	 */
	private String tradeAccount;

	/**
	 * IB代號
	 */
	private String ibID;

	/**
	 * 帳號狀態
	 */
	private String accountState;

	/**
	 * 交易盤別
	 */
	private String tradeType;

	/**
	 * 結算幣別
	 */
	private String settleCurrency;

	/**
	 * 出金銀行帳號列表
	 */
	private List<IJABXWDAccount> withdrawAccountList;

	/**
	 * 入金銀行帳號列表
	 */
	private List<IJABXWDAccount> depositAccountList;

	/**
	 * 開戶日期
	 */
	private int openDate;

	/**
	 * 銷戶日期
	 */
	private int cancelDate;

	/**
	 * 營業員
	 */
	private String salesID;

	/**
	 * 市價委託控管
	 */
	private String priceControl;

	/**
	 * 當沖交易同意書
	 */
	private String offsetConsent;

	/**
	 * 是否為電子戶
	 */
	private String networkUser;

	/**
	 * 帳單傳送方式
	 */
	private String billType;

	/**
	 * 電子對帳單申請日期
	 */
	private int bankStatementDate;

	/**
	 * 電子郵件地址
	 */
	private String email;

	/**
	 * 電子對帳單密碼
	 */
	private String bankStatementPwd;

	/**
	 * 客戶約定書簽署旗標
	 */
	private String noticeSign;

	/**
	 * 電子平台使用權限
	 */
	private String elePlatform;

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getTradeAccount()
	 */
	@Override
	public String getTradeAccount() {
		// TODO Auto-generated method stub
		return tradeAccount;
	}

	/**
	 * 設定下單帳號
	 * @param tradeAccount - String
	 */
	public void setTradeAccount(String tradeAccount) {
		this.tradeAccount = tradeAccount;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getIBID()
	 */
	@Override
	public String getIBID() {
		// TODO Auto-generated method stub
		return ibID;
	}

	/**
	 * 設定IB代號
	 * @param ibID - String
	 */
	public void setIBID(String ibID) {
		this.ibID = ibID;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getAccountState()
	 */
	@Override
	public String getAccountState() {
		// TODO Auto-generated method stub
		return accountState;
	}

	/**
	 * 設定帳號狀態
	 * @param accountState - String
	 */
	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getTradeType()
	 */
	@Override
	public String getTradeType() {
		// TODO Auto-generated method stub
		return tradeType;
	}

	/**
	 * 設定交易盤別
	 * @param tradeType - String
	 */
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getSettleCurrency()
	 */
	@Override
	public String getSettleCurrency() {
		// TODO Auto-generated method stub
		return settleCurrency;
	}

	/**
	 * 設定結算幣別
	 * @param settleCurrency - String
	 */
	public void setSettleCurrency(String settleCurrency) {
		this.settleCurrency = settleCurrency;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getWithdrawAccount()
	 */
	@Override
	public List<IJABXWDAccount> getWithdrawAccountList() {
		// TODO Auto-generated method stub
		return withdrawAccountList;
	}

	/**
	 * 設定出金銀行帳號列表
	 * @param withdrawAccountList - List&ltIJABXWDAccount&gt;
	 */
	public void setWithdrawAccountList(List<IJABXWDAccount> withdrawAccountList) {
		this.withdrawAccountList = withdrawAccountList;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getDepositAccountList()
	 */
	@Override
	public List<IJABXWDAccount> getDepositAccountList() {
		// TODO Auto-generated method stub
		return depositAccountList;
	}

	/**
	 * 設定入金銀行帳號列表
	 * @param depositAccountList - List&lt;IJABXWDAccount&gt;
	 */
	public void setDepositAccountList(List<IJABXWDAccount> depositAccountList) {
		this.depositAccountList = depositAccountList;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getOpenDate()
	 */
	@Override
	public int getOpenDate() {
		// TODO Auto-generated method stub
		return openDate;
	}

	/**
	 * 設定開戶日期
	 * @param openDate - int
	 */
	public void setOpenDate(int openDate) {
		this.openDate = openDate;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getCancelDate()
	 */
	@Override
	public int getCancelDate() {
		// TODO Auto-generated method stub
		return cancelDate;
	}

	/**
	 * 設定銷戶日期
	 * @param cancelDate - int
	 */
	public void setCancelDate(int cancelDate) {
		this.cancelDate = cancelDate;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getSalesID()
	 */
	@Override
	public String getSalesID() {
		// TODO Auto-generated method stub
		return salesID;
	}

	/**
	 * 設定營業員
	 * @param salesID - String
	 */
	public void setSalesID(String salesID) {
		this.salesID = salesID;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getPriceControl()
	 */
	@Override
	public String getPriceControl() {
		// TODO Auto-generated method stub
		return priceControl;
	}

	/**
	 * 設定市價委託控管
	 * @param priceControl - String
	 */
	public void setPriceControl(String priceControl) {
		this.priceControl = priceControl;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getOffsetConsent()
	 */
	@Override
	public String getOffsetConsent() {
		// TODO Auto-generated method stub
		return offsetConsent;
	}

	/**
	 * 設定當沖交易同意書
	 * @param offsetConsent - String
	 */
	public void setOffsetConsent(String offsetConsent) {
		this.offsetConsent = offsetConsent;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getNetworkUser()
	 */
	@Override
	public String getNetworkUser() {
		// TODO Auto-generated method stub
		return networkUser;
	}

	/**
	 * 設定是否為電子戶
	 * @param networkUser - String
	 */
	public void setNetworkUser(String networkUser) {
		this.networkUser = networkUser;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getBillType()
	 */
	@Override
	public String getBillType() {
		// TODO Auto-generated method stub
		return billType;
	}

	/**
	 * 設定帳單傳送方式
	 * @param billType - String
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getBankStatementDate()
	 */
	@Override
	public int getBankStatementDate() {
		// TODO Auto-generated method stub
		return bankStatementDate;
	}

	/**
	 * 設定電子對帳單申請日期
	 * @param bankStatementDate - int
	 */
	public void setBankStatementDate(int bankStatementDate) {
		this.bankStatementDate = bankStatementDate;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getEmail()
	 */
	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return email;
	}

	/**
	 * 設定電子郵件地址
	 * @param email - String
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getBankStatementPwd()
	 */
	@Override
	public String getBankStatementPwd() {
		// TODO Auto-generated method stub
		return bankStatementPwd;
	}

	/**
	 * 設定電子對帳單密碼
	 * @param bankStatementPwd - String
	 */
	public void setBankStatementPwd(String bankStatementPwd) {
		this.bankStatementPwd = bankStatementPwd;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getNoticeSign()
	 */
	@Override
	public String getNoticeSign() {
		// TODO Auto-generated method stub
		return noticeSign;
	}

	/**
	 * 設定客戶約定書簽署旗標
	 * @param noticeSign - String
	 */
	public void setNoticeSign(String noticeSign) {
		this.noticeSign = noticeSign;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserAccountInfoItem#getElePlatform()
	 */
	@Override
	public String getElePlatform() {
		// TODO Auto-generated method stub
		return elePlatform;
	}

	/**
	 * 設定電子平台使用權限
	 * @param elePlatform - String
	 */
	public void setElePlatform(String elePlatform) {
		this.elePlatform = elePlatform;
	}
}
