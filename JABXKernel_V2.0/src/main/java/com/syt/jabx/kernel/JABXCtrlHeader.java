package com.syt.jabx.kernel;

/**
 * 記錄Socket Header之類別
 * @author Jason
 *
 */
public final class JABXCtrlHeader {

	/**
	 * 起始碼
	 */
	private byte byLeadCode;
	
	/**
	 * 序號
	 */
	private long llSeqNo;
	
	/**
	 * 訊息主代碼
	 */
	private byte byMsgMainType;
	
	/**
	 * 訊息次代碼
	 */
	private int byMsgSubType;
	
	/**
	 * 記錄AbyKey之物件
	 */
	private IJABXAbyKey abyKey;
	
	/**
	 * 資料壓縮方式
	 */
	private byte byCompressType;
	
	/**
	 * 資料長度
	 */
	private int dwDataLen;

	/**
	 * 建構式
	 */
	public JABXCtrlHeader() {
		byLeadCode = 0x02;
	}
	
	/**
	 * 取得起始碼
	 * @return byte
	 */
	public byte getByLeadCode() {
		return byLeadCode;
	}
	
	public void setByLeadCode(byte byLeadCode) {
		this.byLeadCode = byLeadCode;
	}

	/**
	 * 取得序號
	 * @return long
	 */
	public long getLlSeqNo() {
		return llSeqNo;
	}

	/**
	 * 設定序號
	 * @param llSeqNo - long
	 */
	public void setLlSeqNo(long llSeqNo) {
		this.llSeqNo = llSeqNo;
	}

	/**
	 * 取得訊息主代碼
	 * @return byte
	 */
	public byte getByMsgMainType() {
		return byMsgMainType;
	}

	/**
	 * 設定訊息主代碼
	 * @param byMsgMainType - byte
	 */
	public void setByMsgMainType(byte byMsgMainType) {
		this.byMsgMainType = byMsgMainType;
	}

	/**
	 * 取得訊息次代碼
	 * @return int
	 */
	public int getByMsgSubType() {
		return byMsgSubType;
	}

	/**
	 * 設定訊息次代碼
	 * @param byMsgSubType - int
	 */
	public void setByMsgSubType(int byMsgSubType) {
		this.byMsgSubType = byMsgSubType;
	}

	/**
	 * 取得IJABXAbyKey物件
	 * @return ZABusAbyKey
	 */
	public IJABXAbyKey getAbyKey() {
		return abyKey;
	}

	/**
	 * 設定IJABXAbyKey物件
	 * @param abyKey - String
	 */
	public void setAbyKey(IJABXAbyKey abyKey) {
		this.abyKey = abyKey;
	}

	/**
	 * 取得資料壓縮方式
	 * @return byte
	 */
	public byte getByCompressType() {
		return byCompressType;
	}

	/**
	 * 設定資料壓縮方式
	 * @param byCompressType - byte
	 */
	public void setByCompressType(byte byCompressType) {
		this.byCompressType = byCompressType;
	}

	/**
	 * 取得資料長度
	 * @return int
	 */
	public int getDwDataLen() {
		return dwDataLen;
	}

	/**
	 * 設定資料長度
	 * @param dwDataLen - int
	 */
	public void setDwDataLen(int dwDataLen) {
		this.dwDataLen = dwDataLen;
	}
		
	/**
	 * 計算並取得CheckSum之值
	 * @return byte
	 */
	public byte calcCheckSum() {
		
		byte checkSum = byLeadCode;
		
		for (int i = 0;i < 8;i++) {//llSeqNo CheckSum計算
			int shift = i * 8;
			byte tmp = (byte)((llSeqNo >> shift) & 255);
			checkSum ^= tmp;
		}
		
		checkSum ^= byMsgMainType;//byMsgMainType CheckSum計算
		
		for (int i = 0;i < 4;i++) {//byMsgSubType CheckSum計算
			int shift = i * 8;
			byte tmp = (byte)((byMsgSubType >> shift) & 255);
			checkSum ^= tmp;			
		}

		checkSum ^= abyKey.calcCheckSum();//abyKey CheckSum計算

		checkSum ^= byCompressType;//byCompressType計算
		
		for (int i = 0;i < 4;i++) {//dwDataLen CheckSum計算
			int shift = i * 8;
			byte tmp = (byte)((dwDataLen >> shift) & 255);
			checkSum ^= tmp;			
		}				

		return checkSum;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("byLeadCode:").append(byLeadCode).append(",");
		sb.append("llSeqNo:").append(llSeqNo).append(",");
		sb.append("byMsgMainType:").append(byMsgMainType).append(",");
		sb.append("byMsgSubType:").append(byMsgSubType).append(",");
		sb.append("abyKey:").append(abyKey.toString()).append(",");
		sb.append("byCompressType:").append(byCompressType).append(",");
		sb.append("dwDataLen:").append(dwDataLen);
		return sb.toString();
	}
}