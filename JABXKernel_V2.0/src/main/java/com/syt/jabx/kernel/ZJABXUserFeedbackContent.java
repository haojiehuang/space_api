package com.syt.jabx.kernel;

import com.syt.jabx.kmodel.IJABXUserFeedbackContent;

/**
 * 用戶意見反饋內容的類別
 * @author Jason
 *
 */
final class ZJABXUserFeedbackContent implements IJABXUserFeedbackContent {

	/**
	 * 意見反饋日期時間(格式為"YYYYMMDDHHMMSS")
	 */
	private String time;

	/**
	 * 意見反饋序號
	 */
	private int feedbackNo;

	/**
	 * 意見反饋標題
	 */
	private String title;

	/**
	 * 意見反饋內容
	 */
	private String content;

	/**
	 * 回覆日期時間(格式為"YYYYMMDDHHMMSS")
	 */
	private String replyTime;

	/**
	 * 回覆內容 
	 */
	private String replyContent;

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserFeedbackContent#getTime()
	 */
	@Override
	public String getTime() {
		// TODO Auto-generated method stub
		return time;
	}

	/**
	 * 設定意見反饋日期時間(格式為"YYYYMMDDHHMMSS")
	 * @param time - String
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserFeedbackContent#getFeedbackNo()
	 */
	@Override
	public int getFeedbackNo() {
		// TODO Auto-generated method stub
		return feedbackNo;
	}

	/**
	 * 設定意見反饋序號
	 * @param feedbackNo - int
	 */
	public void setFeedbackNo(int feedbackNo) {
		this.feedbackNo = feedbackNo;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserFeedbackContent#getTitle()
	 */
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	/**
	 * 設定意見反饋標題
	 * @param title - String
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserFeedbackContent#getContent()
	 */
	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return content;
	}

	/**
	 * 設定意見反饋內容
	 * @param content - String
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserFeedbackContent#getReplyTime()
	 */
	@Override
	public String getReplyTime() {
		// TODO Auto-generated method stub
		return replyTime;
	}

	/**
	 * 設定回覆日期時間(格式為"YYYYMMDDHHMMSS")
	 * @param replyTime - String
	 */
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	/**
	 * @see com.syt.jabx.kmodel.user.IJABXUserFeedbackContent#getReplyContent()
	 */
	@Override
	public String getReplyContent() {
		// TODO Auto-generated method stub
		return replyContent;
	}

	/**
	 * 設定回覆內容 
	 * @param replyContent - String
	 */
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

}
