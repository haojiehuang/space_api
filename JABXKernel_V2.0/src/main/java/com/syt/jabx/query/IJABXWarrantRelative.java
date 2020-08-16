package com.syt.jabx.query;

/**
 * 權證相關介面
 * @author jason
 *
 */
public interface IJABXWarrantRelative {

	/**
	 * 取得履約價
	 * @return int
	 */
	public int getStrikePrice();

	/**
	 * 取得權證換股比率
	 * @return double
	 */
	public double getConverRatio();

	/**
	 * 取得權證到期日(yyyyMMdd)
	 * @return int
	 */
	public int getMaturityDate();

	/**
	 * 取得權證標的項目的數量
	 * @return int
	 */
	public int getCount();

	/**
	 * 依索引值(index)取得權證標的項目介面
	 * @param index - int
	 * @return IJABXWarrantRelativeItem
	 */
	public IJABXWarrantRelativeItem atIndex(int index);
}
