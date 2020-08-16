package com.syt.jabx.kernel;

import com.syt.jabx.message.IJABXSystemBulletin;

/**
 * Abus系統公告訊息的類別
 * @author Jason
 *
 */
final class ZJABXSystemBulletin implements IJABXSystemBulletin {

	/**
	 * Abus系統公告訊息
	 */
	private String systemBulletinData;

	@Override
	public String getSystemBulletinData() {
		// TODO Auto-generated method stub
		return systemBulletinData;
	}

	/**
	 * 設定Abus系統公告訊息
	 * @param systemBulletinData - String
	 */
	public void setSystemBulletinData(String systemBulletinData) {
		this.systemBulletinData = systemBulletinData;
	}
}
