package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABX1Minute;

/**
 * 其他一分鐘資訊之類別
 * @author Jason
 *
 */
final class ZJABX1Minute implements IJABX1Minute {

	/**
	 * 5日均量
	 */
	private int day5AvgVolume;

	/**
	 * @see com.syt.jabx.watch.IJABX1Minute#getDay5AvgVolume()
	 */
	@Override
	public int getDay5AvgVolume() {
		// TODO Auto-generated method stub
		return day5AvgVolume;
	}

	/**
	 * 設定5日均量
	 * @param day5AvgVolume - int
	 */
	public void setDay5AvgVolume(int day5AvgVolume) {
		this.day5AvgVolume = day5AvgVolume;
	}

	/**
	 * 將類別中所用到的欄位設定為零或空白
	 */
	public void setDataToZeroOrSpace() {
		day5AvgVolume = 0;
	}
}
