package com.syt.jabx.bean;

/**
 * 標籤參數之Bean物件(Gateway返回訊息使用)
 * @author jason
 *
 */
public final class JABXTagValue {

	/**
	 * 標籤代碼
	 */
	private int tag;
	
	/**
	 * 內容
	 */
	private byte[] value;

	/**
	 * Constructor
	 */
	public JABXTagValue() {
		this.tag = 0;
		this.value = null;
	}
	
	/**
	 * 取得標籤代碼
	 * @return int
	 */
	public int getTag() {
		return this.tag;
	}		
	
	/**
	 * 設定標籤代碼
	 * @param tag - int
	 */
	public void setTag(int tag) {
		this.tag = tag;
	}
	
	/**
	 * 取得內容
	 * @return byte[]
	 */
	public byte[] getValue() {
		return this.value;
	}
	
	/**
	 * 設定內容
	 * @param value - String
	 */
	public void setValue(byte[] value) {
		this.value = value;
	}

}
