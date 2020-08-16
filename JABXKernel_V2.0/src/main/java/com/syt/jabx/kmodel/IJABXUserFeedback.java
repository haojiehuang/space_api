package com.syt.jabx.kmodel;

/**
 * 用戶意見反饋標題索引的介面
 * @author Jason
 *
 */
public interface IJABXUserFeedback {

	/**
	 * 取得用戶意見反饋標題的項目介面數量
	 * @return int
	 */
	public int getCount();

	/**
	 * 取得用戶意見反饋標題項目的介面(IJABXUserFeedbackItem)
	 * @param index - int(索引值，從0開始)
	 * @return IJABXUserFeedbackItem
	 */
	public IJABXUserFeedbackItem atIndex(int index);

	/**
	 * 以意見反饋序號取得用戶意見反饋標題項目的介面(IJABXUserFeedbackItem)
	 * @param feedbackNo - int(意見反饋序號)
	 * @return IJABXUserFeedbackItem
	 */
	public IJABXUserFeedbackItem atFeedbackNo(int feedbackNo);

}
