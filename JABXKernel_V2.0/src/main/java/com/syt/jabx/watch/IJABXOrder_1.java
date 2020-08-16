package com.syt.jabx.watch;

/**
 * 取得第一檔委買賣之介面
 * @author Jason
 *
 */
public interface IJABXOrder_1 {

	/**
	 * 取得第1買價
	 * @return int
	 */
	public int getBidPrice1();

	/**
	 * 取得第1買量
	 * @return int
	 */
	public int getBidVolume1();

	/**
	 * 取得第1賣價
	 * @return int
	 */
	public int getAskPrice1();

	/**
	 * 取得第1賣量
	 * @return int
	 */
	public int getAskVolume1();
}
