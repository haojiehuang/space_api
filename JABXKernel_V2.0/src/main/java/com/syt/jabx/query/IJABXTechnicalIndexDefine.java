package com.syt.jabx.query;

/**
 * 技術指標定義項目的介面
 * @author Jason
 *
 */
public interface IJABXTechnicalIndexDefine {

	/**
	 * 取得技術指標代碼
	 * @return int
	 */
	public int getTechnicalIndexID();

	/**
	 * 取得公式英文名稱
	 * @return String
	 */
	public String getName();

	/**
	 * 取得公式中文名稱
	 * @return String
	 */
	public String getChineseName();

	/**
	 * 取得參數設定
	 * @return String
	 */
	public String getParameterSetup();

	/**
	 * 取得公式註釋
	 * @return String
	 */
	public String getFormulaDescription();

	/**
	 * 取得參數描述
	 * @return String
	 */
	public String getParameterDescription();

	/**
	 * 取得預設週期
	 * @return String
	 */
	public String getDefaultPeriod();

	/**
	 * 取得可使用週期列表
	 * @return String
	 */
	public String getUsedPeriod();

	/**
	 * 取得限用(不可用)交易所代碼列表
	 * @return String
	 */
	public String getUnExchange();

	/**
	 * 取得坐標線位置
	 * @return String
	 */
	public String getScale();

	/**
	 * 取得展示位置
	 * @return String
	 */
	public String getPlacementsType();

	/**
	 * 取得指標線設定 
	 * @return String
	 */
	public String getLineSetup();

}
