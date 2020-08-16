package com.syt.jabx.kernel;

/**
 * AbyKey之介面
 * @author Jason
 *
 */
public interface IJABXAbyKey {

	/**
	 * 計算CheckSum
	 * @return byte
	 */
	public byte calcCheckSum();

	/**
	 * 將數據轉換為String
	 * @return String
	 */
	public String toString();
}
