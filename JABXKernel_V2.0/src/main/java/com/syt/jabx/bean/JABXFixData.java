package com.syt.jabx.bean;

/**
 * 記錄Fix格式之Tag及Value之類別
 * @author Jason
 *
 */
public final class JABXFixData {

	/**
	 * 標籤
	 */
	private String tag;
	
	/**
	 * 內容
	 */
	private String value;
	
	/**
	 * 編碼字集
	 */
	private String charSet;

	/**
	 * 取得標籤
	 * @return String
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * 設定標籤
	 * @param tag - String
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * 取得內容
	 * @return String
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 設定內容
	 * @param value - String
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 取得編碼字集
	 * @return String
	 */
	public String getCharSet() {
		return charSet;
	}

	/**
	 * 設定編碼字集
	 * @param charSet - String
	 */
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}
}
