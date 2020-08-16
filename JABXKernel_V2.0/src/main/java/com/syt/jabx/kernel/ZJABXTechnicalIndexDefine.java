package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXTechnicalIndexDefine;

/**
 * 技術指標定義項目的類別
 * @author Jason
 *
 */
final class ZJABXTechnicalIndexDefine implements IJABXTechnicalIndexDefine {

	/**
	 * 技術指標代碼
	 */
	private int technicalIndexID;

	/**
	 * 公式英文名稱
	 */
	private String name;

	/**
	 * 公式中文名稱
	 */
	private String chineseName;

	/**
	 * 參數設定
	 */
	private String parameterSetup;

	/**
	 * 公式註釋
	 */
	private String formulaDescription;

	/**
	 * 參數描述
	 */
	private String parameterDescription;

	/**
	 * 預設週期
	 */
	private String defaultPeriod;

	/**
	 * 可使用週期列表
	 */
	private String usedPeriod;

	/**
	 * 限用(不可用)交易所代碼列表
	 */
	private String unExchange;

	/**
	 * 坐標線位置
	 */
	private String scale;

	/**
	 * 展示位置
	 */
	private String placementsType;

	/**
	 * 指標線設定 
	 */
	private String lineSetup;

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexDefine#getTechnicalIndexID()
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
	 * @see com.syt.jabx.query.IJABXTechnicalIndexDefine#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * 設定公式英文名稱
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexDefine#getChineseName()
	 */
	@Override
	public String getChineseName() {
		// TODO Auto-generated method stub
		return chineseName;
	}

	/**
	 * 設定公式中文名稱
	 * @param chineseName - String
	 */
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexDefine#getParameterSetup()
	 */
	@Override
	public String getParameterSetup() {
		// TODO Auto-generated method stub
		return parameterSetup;
	}

	/**
	 * 設定參數設定
	 * @param parameterSetup - String
	 */
	public void setParameterSetup(String parameterSetup) {
		this.parameterSetup = parameterSetup;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexDefine#getFormulaDescription()
	 */
	@Override
	public String getFormulaDescription() {
		// TODO Auto-generated method stub
		return formulaDescription;
	}

	/**
	 * 設定公式註釋
	 * @param formulaDescription - String
	 */
	public void setFormulaDescription(String formulaDescription) {
		this.formulaDescription = formulaDescription;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexDefine#getParameterDescription()
	 */
	@Override
	public String getParameterDescription() {
		// TODO Auto-generated method stub
		return parameterDescription;
	}

	/**
	 * 設定參數描述
	 * @param parameterDescription - String
	 */
	public void setParameterDescription(String parameterDescription) {
		this.parameterDescription = parameterDescription;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexDefine#getDefaultPeriod()
	 */
	@Override
	public String getDefaultPeriod() {
		// TODO Auto-generated method stub
		return defaultPeriod;
	}

	/**
	 * 設定預設週期
	 * @param defaultPeriod - String
	 */
	public void setDefaultPeriod(String defaultPeriod) {
		this.defaultPeriod = defaultPeriod;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexDefine#getUsedPeriod()
	 */
	@Override
	public String getUsedPeriod() {
		// TODO Auto-generated method stub
		return usedPeriod;
	}

	/**
	 * 設定可使用週期列表
	 * @param usedPeriod - String
	 */
	public void setUsedPeriod(String usedPeriod) {
		this.usedPeriod = usedPeriod;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexDefine#getUnExchange()
	 */
	@Override
	public String getUnExchange() {
		// TODO Auto-generated method stub
		return unExchange;
	}

	/**
	 * 設定限用(不可用)交易所代碼列表
	 * @param unExchange - String
	 */
	public void setUnExchange(String unExchange) {
		this.unExchange = unExchange;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexDefine#getScale()
	 */
	@Override
	public String getScale() {
		// TODO Auto-generated method stub
		return scale;
	}

	/**
	 * 設定坐標線位置
	 * @param scale - String
	 */
	public void setScale(String scale) {
		this.scale = scale;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexDefine#getPlacementsType()
	 */
	@Override
	public String getPlacementsType() {
		// TODO Auto-generated method stub
		return placementsType;
	}

	/**
	 * 設定展示位置
	 * @param placementsType - String
	 */
	public void setPlacementsType(String placementsType) {
		this.placementsType = placementsType;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexDefine#getLineSetup()
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
