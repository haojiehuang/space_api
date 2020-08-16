package com.syt.jabx.watch;

/**
 * 取得第2至5檔委買資訊之介面
 * @author Jason
 *
 */
public interface IJABXOrder_2_5 {

	/**
	 * 取得第2買價
	 * @return int
	 */
	public int getBidPrice2();

	/**
	 * 取得第2買量
	 * @return int
	 */
	public int getBidVolume2();

	/**
	 * 取得第3買價
	 * @return int
	 */
	public int getBidPrice3();

	/**
	 * 取得第3買量
	 * @return int
	 */
	public int getBidVolume3();

	/**
	 * 取得第4買價
	 * @return int
	 */
	public int getBidPrice4();

	/**
	 * 取得第4買量
	 * @return int
	 */
	public int getBidVolume4();

	/**
	 * 取得第5買價
	 * @return int
	 */
	public int getBidPrice5();

	/**
	 * 取得第5買量
	 * @return int
	 */
	public int getBidVolume5();

	/**
	 * 取得第2賣價
	 * @return int
	 */
	public int getAskPrice2();

	/**
	 * 取得第2賣量
	 * @return int
	 */
	public int getAskVolume2();

	/**
	 * 取得第3賣價
	 * @return int
	 */
	public int getAskPrice3();

	/**
	 * 取得第3賣量
	 * @return int
	 */
	public int getAskVolume3();

	/**
	 * 取得第4賣價
	 * @return int
	 */
	public int getAskPrice4();

	/**
	 * 取得第4賣量
	 * @return int
	 */
	public int getAskVolume4();

	/**
	 * 取得第5賣價
	 * @return int
	 */
	public int getAskPrice5();

	/**
	 * 取得第5賣量
	 * @return int
	 */
	public int getAskVolume5();
}