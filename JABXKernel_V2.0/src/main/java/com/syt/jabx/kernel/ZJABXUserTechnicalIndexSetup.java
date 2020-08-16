package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXUserTechnicalIndexSetup;

/**
 * 用戶技術指標參數預設值的類別
 * @author Jason
 *
 */
final class ZJABXUserTechnicalIndexSetup implements IJABXUserTechnicalIndexSetup {

	/**
	 * 技術指標代碼
	 */
	private int technicalIndexID;

	/**
	 * 週期代碼
	 */
	private int period;

	/**
	 * 預設值旗標
	 */
	private byte defaultFlag;

	/**
	 * 參數預設值
	 */
	private String parameter;

	/**
	 * 指標線設定 
	 */
	private String lineSetup;

	/**
	 * @see com.syt.jabx.user.IJABXUserTechnicalIndexSetup#getTechnicalIndexID()
	 */
	@Override
	public int getTechnicalIndexID() {
		// TODO Auto-generated method stub
		return technicalIndexID;
	}

	/**
	 * 設定技術指標代碼
	 * @param technicalIndexID - int
	 */
	public void setTechnicalIndexID(int technicalIndexID) {
		this.technicalIndexID = technicalIndexID;
	}

	/**
	 * @see com.syt.jabx.user.IJABXUserTechnicalIndexSetup#getPeriod()
	 */
	@Override
	public int getPeriod() {
		// TODO Auto-generated method stub
		return period;
	}

	/**
	 * 設定週期代碼
	 * @param period - int
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * @see com.syt.jabx.user.IJABXUserTechnicalIndexSetup#getDefaultFlag()
	 */
	@Override
	public byte getDefaultFlag() {
		// TODO Auto-generated method stub
		return defaultFlag;
	}

	/**
	 * 設定預設值旗標
	 * @param defaultFlag - byte
	 */
	public void setDefaultFlag(byte defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	/**
	 * @see com.syt.jabx.user.IJABXUserTechnicalIndexSetup#getParameter()
	 */
	@Override
	public String getParameter() {
		// TODO Auto-generated method stub
		return parameter;
	}

	/**
	 * 設定參數預設值
	 * @param parameter - String
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	/**
	 * @see com.syt.jabx.user.IJABXUserTechnicalIndexSetup#getLineSetup()
	 */
	@Override
	public String getLineSetup() {
		// TODO Auto-generated method stub
		return lineSetup;
	}

	/**
	 * 設定指標線設定 
	 * @param lineSetup - String
	 */
	public void setLineSetup(String lineSetup) {
		this.lineSetup = lineSetup;
	}
}
