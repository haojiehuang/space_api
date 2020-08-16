package com.syt.jabx.kernel;

import com.syt.jabx.kmodel.IJABXUserFeedbackItem;

/**
 * 用戶意見反饋標題項目的類別
 * @author Jason
 *
 */
final class ZJABXUserFeedbackItem implements IJABXUserFeedbackItem {

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
	 * @see com.syt.jabx.kmodel.user.IJABXUserFeedbackItem#getTime()
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
	 * @see com.syt.jabx.kmodel.user.IJABXUserFeedbackItem#getFeedbackNo()
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
	 * @see com.syt.jabx.kmodel.user.IJABXUserFeedbackItem#getTitle()
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
}
