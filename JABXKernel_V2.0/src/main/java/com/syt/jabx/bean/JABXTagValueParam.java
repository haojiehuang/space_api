package com.syt.jabx.bean;

/**
 * 標籤參數之Bean物件(Gateway傳送參數使用)
 * @author Jason
 *
 */
public class JABXTagValueParam {

	/**
	 * 標籤代碼
	 */
	private int tag;
	
	/**
	 * 內容
	 */
	private String value;

	/**
	 * Constructor
	 */
	public JABXTagValueParam() {
		this.tag = 0;
		this.value = "";
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
	public String getValue() {
		return this.value;
	}
	
	/**
	 * 設定內容
	 * @param value - String
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
