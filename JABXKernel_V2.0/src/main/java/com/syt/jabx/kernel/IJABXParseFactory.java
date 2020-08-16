package com.syt.jabx.kernel;

import com.syt.jabx.notify.IJABXLog;

/**
 * Socket解碼之工廠介面(API內部使用)
 * @author jason
 *
 */
public interface IJABXParseFactory {

	/**
	 * 依條件取得IJABXParseBody物件
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 * @param ctrlHeader - ZJABXCtrlHeader
	 * @param srcObj - Object
	 * @return IJABXParseBody
	 */
	public IJABXParseBody getParseBody(JABXKernel jabxKernel, IJABXLog jabxLog, JABXCtrlHeader ctrlHeader, Object srcObj);
}
