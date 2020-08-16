package com.syt.jabx.kernel;

/**
 * 實作IJABXAbyKey之Bean類別(Control Header使用之AbyKey)，API內部使用
 * @author Jason
 *
 */
public final class JABXAbyKeyCtrl implements IJABXAbyKey {

	/**
	 * 功能代碼欄位之長度
	 */
	private static int ABY_FUNC_CODE_LENGTH = 4;
	
	/**
	 * 功能代碼
	 */
	private String abyFuncCode;

	/**
	 * 用戶GW帳號
	 */
	private int dwUserGWID;
	
	/**
	 * 產品代碼
	 */
	private byte byProductID;
	  
	/**
	 * 產品平台代碼
	 */
	private byte byPlatformID;

	/**
	 * GW連線序號
	 */
	private byte byChannelNo;
	
	/**
	 * 安全碼
	 */
	private byte bySecurityKey;
	
	/**
	 * 保留
	 */
	private byte[] byReserve;

	/**
	 * AP識別碼
	 */
	private short byAPCode;
	
	public JABXAbyKeyCtrl() {
		byReserve = new byte[JABXConst.RESERVE_LENGTH];
	}
	
	/**
	 * 取得功能代碼
	 * @return String
	 */
	public String getAbyFuncCode() {
		return abyFuncCode;
	}

	/**
	 * 設定功能代碼
	 * @param abyFuncCode - String
	 */
	public void setAbyFuncCode(String abyFuncCode) {
		this.abyFuncCode = abyFuncCode;
	}

	/**
	 * 取得功能代碼之byte[]
	 * @return byte[]
	 */
	public byte[] getAbyFuncCodeBytes() {
		byte[] tmpAry = new byte[ABY_FUNC_CODE_LENGTH];
		
	    byte[] abyValAry = abyFuncCode.getBytes();
	    int nCopy = Math.min(abyValAry.length, ABY_FUNC_CODE_LENGTH);
	    System.arraycopy(abyValAry, 0, tmpAry, 0, nCopy);		
		
		return tmpAry;		
	}
	
	/**
	 * 取得用戶GW帳號
	 * @return int
	 */
	public int getDwUserGWID() {
		return dwUserGWID;
	}

	/**
	 * 設定用戶GW帳號
	 * @param dwUserGWID - int
	 */
	public void setDwUserGWID(int dwUserGWID) {
		this.dwUserGWID = dwUserGWID;
	}

	/**
	 * 取得產品代碼
	 * @return byte
	 */
	public byte getByProductID() {
		return byProductID;
	}

	/**
	 * 設定產品代碼
	 * @param byProductID - byte
	 */
	public void setByProductID(byte byProductID) {
		this.byProductID = byProductID;
	}

	/**
	 * 取得產品平台代碼
	 * @return byte
	 */
	public byte getByPlatformID() {
		return byPlatformID;
	}

	/**
	 * 設定產品平台代碼
	 * @param byPlatformID - byte
	 */
	public void setByPlatformID(byte byPlatformID) {
		this.byPlatformID = byPlatformID;
	}

	/**
	 * 取得GW連線序號
	 * @return byte
	 */
	public byte getByChannelNo() {
		return byChannelNo;
	}

	/**
	 * 設定GW連線序號
	 * @param byChannelNo - byte
	 */
	public void setByChannelNo(byte byChannelNo) {
		this.byChannelNo = byChannelNo;
	}

	/**
	 * 取得安全碼
	 * @return byte
	 */
	public byte getBySecurityKey() {
		return bySecurityKey;
	}

	/**
	 * 設定安全碼
	 * @param bySecurityKey - byte
	 */
	public void setBySecurityKey(byte bySecurityKey) {
		this.bySecurityKey = bySecurityKey;
	}

	/**
	 * 取得保留
	 * @return String
	 */
	public byte[] getByReserve() {
		return byReserve;
	}

	/**
	 * 取得AP識別碼
	 * @return short
	 */
	public short getByAPCode() {
		return byAPCode;
	}

	/**
	 * 設定AP識別碼
	 * @param byAPCode - short
	 */
	public void setByAPCode(short byAPCode) {
		this.byAPCode = byAPCode;
	}

	/**
	 * 計算並取得CheckSum之值
	 * @return byte
	 */
	public byte calcCheckSum() {

		byte checkSum = byProductID;//byProductID CheckSum計算
		
		byte[] tmpAry = this.getAbyFuncCodeBytes();//abyFuncCode CheckSum計算
		for (int i = 0;i < tmpAry.length;i++) {
			checkSum ^= tmpAry[i];
		}
		
		for (int i = 0;i < 4;i++) {//dwUserGWID CheckSum計算
			int shift = i * 8;
			byte tmp = (byte)((dwUserGWID >> shift) & 255);
			checkSum ^= tmp;
		}
			
		checkSum ^= byPlatformID;//byPlatformID CheckSum計算
		
		checkSum ^= byChannelNo;//byChannelNO CheckSum計算
		
		checkSum ^= bySecurityKey;//dwSecurityKey CheckSum計算
				
		for (int i = 0;i < byReserve.length;i++) {//byReserve CheckSum計算
			checkSum ^= byReserve[i];
		}
		
		for (int i = 0;i < 2;i++) {//byAPCode CheckSum計算
			int shift = i * 8;
			byte tmp = (byte)((byAPCode >> shift) & 255);
			checkSum ^= tmp;
		}		
		
		tmpAry = null;
		
		return checkSum;		
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXAbyKey#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		int securityKey = bySecurityKey > 0 ? bySecurityKey : bySecurityKey + 256;
		sb.append("abyFuncCode=").append(abyFuncCode).append("|");
		sb.append("dwUserGWID=").append(dwUserGWID).append("|");
		sb.append("byProductID=").append(byProductID).append("|");
		sb.append("byPlatformID=").append(byPlatformID).append("|");
		sb.append("byChannelNo=").append(byChannelNo).append("|");
		sb.append("bySecurityKey=").append(securityKey).append("|");
		sb.append("byReserve=").append(new String(byReserve)).append("|");
		sb.append("byAPCode=").append(byAPCode);
		
		return sb.toString();
	}
}
