package com.syt.jabx.kmodel;

/**
 * 用戶意見反饋標題項目的介面
 * @author Jason
 *
 */
public interface IJABXUserFeedbackItem {

	/**
	 * 取得意見反饋日期時間(格式為"YYYYMMDDHHMMSS")
	 * @return String
	 */
	public String getTime();

	/**
	 * 取得意見反饋序號
	 * @return int
	 */
	public int getFeedbackNo();

	/**
	 * 取得意見反饋標題
	 * @return String
	 */
	public String getTitle();

}
