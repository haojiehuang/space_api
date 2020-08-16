package com.syt.jabx.bean;

/**
 * 交易時間之類別
 * @author jason
 *
 */
public class JABXTradeTime {

	/**
	 * 開盤時間(HHmm)
	 */
	private int openHHmm;

	/**
	 * 休息時間1(HHmm)
	 */
	private int restHHmm1;

	/**
	 * 再盤時間1(HHmm)
	 */
	private int reOpenHHmm1;

	/**
	 * 收盤時間(HHmm)
	 */
	private int closeHHmm;

	/**
	 * 取得開盤時間(HHmm)
	 * @return int
	 */
	public int getOpenHHmm() {
		return openHHmm;
	}

	/**
	 * 設定開盤時間(HHmm)
	 * @param openHHmm - int
	 */
	public void setOpenHHmm(int openHHmm) {
		this.openHHmm = openHHmm;
	}

	/**
	 * 取得休息時間1(HHmm)
	 * @return int
	 */
	public int getRestHHmm1() {
		return restHHmm1;
	}

	/**
	 * 設定休息時間1(HHmm)
	 * @param restHHmm1 - int
	 */
	public void setRestHHmm1(int restHHmm1) {
		this.restHHmm1 = restHHmm1;
	}

	/**
	 * 取得再開盤時間1(HHmm)
	 * @return int
	 */
	public int getReOpenHHmm1() {
		return reOpenHHmm1;
	}

	/**
	 * 設定再開盤時間1(HHmm)
	 * @param reOpenHHmm1 - int
	 */
	public void setReOpenHHmm1(int reOpenHHmm1) {
		this.reOpenHHmm1 = reOpenHHmm1;
	}

	/**
	 * 取得收盤時間(HHmm)
	 * @return int
	 */
	public int getCloseHHmm() {
		return closeHHmm;
	}

	/**
	 * 設定收盤時間(HHmm)
	 * @param closeHHmm - int
	 */
	public void setCloseHHmm(int closeHHmm) {
		this.closeHHmm = closeHHmm;
	}
}
