package com.syt.jabx.kmodel;

/**
 * 用戶意見反饋內容的介面
 * @author Jason
 *
 */
public interface IJABXUserFeedbackContent {

	/**
	 * 取得意見反饋日期時間
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

	/**
	 * 取得意見反饋內容
	 * @return String
	 */
	public String getContent();

	/**
	 * 取得回覆日期時間
	 * @return String
	 */
	public String getReplyTime();

	/**
	 * 取得回覆內容 
	 * @return String
	 */
	public String getReplyContent();

}
