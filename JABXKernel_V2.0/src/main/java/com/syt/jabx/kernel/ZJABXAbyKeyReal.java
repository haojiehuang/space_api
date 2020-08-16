package com.syt.jabx.kernel;

/**
 * 實作IJABXAbyKey之Bean類別(即時數據使用之AbyKey)，API內部使用
 * @author Jason
 *
 */
final class ZJABXAbyKeyReal implements IJABXAbyKey {

	/**
	 * 交易所代碼欄位之長度
	 */
	private static int EXCHANGE_ID_LENGTH = 2;

	/**
	 * 證券代碼欄位之長度
	 */
	private static int ABYCODE_LENGTH = 20;

	/**
	 * 證券代碼
	 */
	private String abyCode;
	
	public ZJABXAbyKeyReal() {
	}
	
	/**
	 * 取得證券代碼
	 * @return String
	 */
	public String getAbyCode() {
		return abyCode;
	}

	/**
	 * 設定證券代碼
	 * @param abyCode - String
	 */
	public void setAbyCode(String abyCode) {
		this.abyCode = abyCode;
	}

	/**
	 * 取得證券代碼之byte[]
	 * @return byte[]
	 */
	public byte[] getAbyCodeBytes() {
		byte[] tmpAry = new byte[EXCHANGE_ID_LENGTH + ABYCODE_LENGTH];
		
	    byte[] abyValAry = abyCode.getBytes();
	    int nCopy = Math.min(abyValAry.length, EXCHANGE_ID_LENGTH + ABYCODE_LENGTH);
	    System.arraycopy(abyValAry, 0, tmpAry, 0, nCopy);		
		
		return tmpAry;		
	}

	/**
	 * 計算並取得CheckSum之值
	 * @return byte
	 */
	public byte calcCheckSum() {

		byte[] tmpAry = this.getAbyCodeBytes();//abyCode CheckSum計算

		byte checkSum = tmpAry[0];

		for (int i = 1;i < tmpAry.length;i++) {
			checkSum ^= tmpAry[i];
		}

		tmpAry = null;
		
		return checkSum;		
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXAbyKey#toString()
	 */
	@Override
	public String toString() {
		return abyCode;
	}
}