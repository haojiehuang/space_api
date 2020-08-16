package com.syt.jabx.kernel;

import org.json.JSONObject;



/**
 * 記錄封包及錯誤訊息之類別
 * @author Jason
 *
 */
final class ZJABXPacket {

	/**
	 * 資料類型(0.data, 1.result)
	 */
	private byte type;

	/**
	 * 封包標頭
	 */
	private JABXCtrlHeader ctrlHeader;

	/**
	 * 壓縮內容之Header
	 */
	private byte[] compressHeader;
	
	/**
	 * 封包內容
	 */
	private byte[] data;
	
	/**
	 * 封包之CheckSum
	 */
	private byte checkSum;

	/**
	 * 訊息結果物件
	 */
	private JSONObject result;

	/**
	 * 取得資料類型(0.data, 1.result)
	 * @return byte
	 */
	public byte getType() {
		return type;
	}

	/**
	 * 設定資料類型(0.data, 1.result)
	 * @param type - byte
	 */
	public void setType(byte type) {
		this.type = type;
	}

	/**
	 * 取得ZJABXCtrlHeader
	 * @return ZJABXCtrlHeader
	 */
	public JABXCtrlHeader getCtrlHeader() {
		return ctrlHeader;
	}
	
	/**
	 * 設定封包標頭
	 * @param ctrlHeader - ZJABXCtrlHeader
	 */
	public void setCtrlHeader(JABXCtrlHeader ctrlHeader) {
		this.ctrlHeader = ctrlHeader;
	}

	/**
	 * 取得壓縮內容之Header
	 * @return byte[]
	 */
	public byte[] getCompressHeader() {
		return compressHeader;
	}

	/**
	 * 設定壓縮內容之Header
	 * @param compressHeader - byte[]
	 */
	public void setCompressHeader(byte[] compressHeader) {
		this.compressHeader = compressHeader;
	}

	/**
	 * 取得封包資料
	 * @return byte[]
	 */
	public byte[] getData() {
		return data;
	}
	
	/**
	 * 設定封包內容
	 * @param data - byte[]
	 */
	public void setData(byte[] data) {
		this.data = data;
	}
	
	/**
	 * 取得封包回傳之CheckSum
	 * @return byte
	 */
	public byte getCheckSum() {
		return checkSum;
	}
	
	/**
	 * 設定封包之CheckSum
	 * @param checkSum - byte
	 */
	public void setCheckSum(byte checkSum) {
		this.checkSum = checkSum;
	}

	/**
	 * 取得訊息結果物件
	 * @return IJABXJson
	 */
	public JSONObject getResult() {
		return result;
	}

	/**
	 * 設定訊息結果物件
	 * @param result - JSONObject
	 */
	public void setResult(JSONObject result) {
		this.result = result;
	}

	/**
	 * 計算並取得CheckSum之值
	 * @return byte
	 */
	public byte calcCheckSum() {
		
		byte checkSum = 0x00;
		
		if (ctrlHeader != null) {//controlHeader CheckSum計算
			checkSum = ctrlHeader.calcCheckSum();
			if (data != null) {//data CheckSum計算
				for (int i = 0;i < data.length;i++) {
					checkSum ^= data[i];
				}
			}
		}else {
			if (data != null) {//data CheckSum計算
				for (int i = 0;i < data.length;i++) {
					if (i == 0) {
						checkSum = data[i];
					}else {
						checkSum ^= data[i];
					}
				}
			}
		}
		
		return checkSum;
	}
}