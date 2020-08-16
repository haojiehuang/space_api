package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.bean.JABXFixData;
import com.syt.jabx.bean.JABXSocketParam;
import com.syt.jabx.bean.JABXTagValueParam;
import com.syt.jabx.notify.IJABXLog;

/**
 * 用戶資訊請求類別
 * @author jason
 *
 */
final class ZJABXUserRequest extends IJABXParseBase implements IJABXUserRequest {

	/**
	 * 應用程序核心管理類別
	 */
	private JABXKernel jabxKernel;

	/**
	 * 輸出訊息及Log之介面
	 */
	private IJABXLog jabxLog;

	/**
	 * 處理Gateway Session之物件
	 */
	private ZJABXChannelSocket channelSocket = null;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 */
	public ZJABXUserRequest(JABXKernel jabxKernel, IJABXLog jabxLog) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
	}

	/**
	 * @see com.syt.jabx.notify.IJABXRequest#applyNoticeSign(int, List)
	 */
	@Override
	public void applyNoticeSign(int requestID, List<JABXTagValueParam> param) {
		// TODO Auto-generated method stub
		if (param == null) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_APPLY_NOTICESIGN, requestID, "", JABXErrCode.NULL_POINTER);
			return;
		}
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfoByServer_1();
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_APPLY_NOTICESIGN, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非交易主機或GW主機，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_APPLY_NOTICESIGN, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 證券-交易委託
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U20", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXTagValueParam tagValue;
			JABXFixData fixData;
			for (int i = 0, size = param.size();i < size;i++) {
				tagValue = param.get(i);
				fixData = new JABXFixData();
				fixData.setTag(String.valueOf(tagValue.getTag()));
				fixData.setValue(tagValue.getValue());
				queryList.add(fixData);
			}
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U20", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.applyNoticeSign()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.applyNoticeSign()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.applyNoticeSign()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.applyNoticeSign()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_APPLY_NOTICESIGN, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXUserRequest#changeUserPassword(int, String, int, String, String, String, String, String)
	 */
	@Override
	public void changeUserPassword(int requestID, String execType, int loginType, String account, String orgPwd, String newPwd, String loginSource, String loginIP) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_CHANGE_USERPASSWORD, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_CHANGE_USERPASSWORD, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_CHANGE_USERPASSWORD);// 儲存所使用之功能代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 用戶密碼變更
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U02", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 變更類別
			fixData.setValue(execType);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 登入方式
			fixData.setValue(String.valueOf(loginType));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 登入資料
			fixData.setValue(account);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 原始登入密碼
			fixData.setValue(orgPwd);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("5");// 新登入密碼
			fixData.setValue(newPwd);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("6");// 密碼編碼方式
			fixData.setValue(String.valueOf(JABXConst.ABGW_ENCODE_NONE));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("7");// 登入來源
			fixData.setValue(loginSource);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("8");// 登入IP
			fixData.setValue(loginIP);
			queryList.add(fixData);
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U02", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.changeUserPassword()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.changeUserPassword()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.changeUserPassword()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.changeUserPassword()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_CHANGE_USERPASSWORD, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXUserRequest#checkUserPassword(int, String, int, String, String, int, String, String)
	 */
	@Override
	public void checkUserPassword(int requestID, String execType, int loginType, String account, String password, int pwdEncoding, String loginSource, String loginIP) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_CHECK_USERPASSWORD, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_CHECK_USERPASSWORD, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_CHECK_USERPASSWORD);// 儲存所使用之功能代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 用戶密碼變更
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U04", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 變更類別
			fixData.setValue(execType);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 登入方式
			fixData.setValue(String.valueOf(loginType));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 登入資料
			fixData.setValue(account);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 密碼
			fixData.setValue(password);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("5");// 密碼編碼方式
			if (pwdEncoding == JABXConst.ABGW_ENCODE_MD5) {
				fixData.setValue("MD5");
			}else {
				fixData.setValue("");
			}
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("6");// 登入來源
			fixData.setValue(loginSource);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("7");// 登入IP
			fixData.setValue(loginIP);
			queryList.add(fixData);
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U04", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.checkUserPassword()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.checkUserPassword()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.checkUserPassword()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.checkUserPassword()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_CHECK_USERPASSWORD, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXUserRequest#deleteUserEnvironment(int, String, int, byte)
	 */
	@Override
	public void deleteUserEnvironment(int requestID, String sGroupID, int iGroupNo, byte bProductSetupType) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_DELETE_USERENVIRONMENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_DELETE_USERENVIRONMENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_DELETE_USERENVIRONMENT);// 儲存所使用之功能代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 用戶系統環境設定刪除
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U07", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定群組代碼
			fixData.setValue(sGroupID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定群組明細序號
			fixData.setValue(String.valueOf(iGroupNo));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 產品代碼
			if (bProductSetupType == 0) {// 0為系統預設
				fixData.setValue("0");
			}else {// 1為產品代碼
				fixData.setValue(String.valueOf(jabxKernel.getLoginInfo().get(JSConst.LN_PRODUCTID)));
			}
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U07", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.deleteUserEnvironment()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.deleteUserEnvironment()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.deleteUserEnvironment()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.deleteUserEnvironment()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_DELETE_USERENVIRONMENT, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXUserRequest#getUserEnvironmentContent(int, String, int, byte, byte)
	 */
	@Override
	public void getUserEnvironmentContent(int requestID, String sGroupID, int iGroupNo, byte bUserSetupType, byte bProductSetupType) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERENVIRONMENTCONTENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERENVIRONMENTCONTENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_GET_USERENVIRONMENTCONTENT);// 儲存所使用之功能代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢用戶系統環境設定資料
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U05", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定群組代碼
			fixData.setValue(sGroupID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定群組明細序號
			fixData.setValue(String.valueOf(iGroupNo));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 回覆欄位Tag列表
			fixData.setValue("");
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("5");// 用戶GW帳號
			if (bUserSetupType == 0) {// 0為系統預設
				fixData.setValue("0");
			}else {// 1為用戶GW Id
				fixData.setValue(String.valueOf(jabxKernel.getLoginInfo().get(JSConst.LN_USERGWID)));
			}
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("6");// 產品代碼
			if (bProductSetupType == 0) {// 0為系統預設
				fixData.setValue("0");
			}else {// 1為產品代碼
				fixData.setValue(String.valueOf(jabxKernel.getLoginInfo().get(JSConst.LN_PRODUCTID)));
			}
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U05", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.getUserEnvironmentContent()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserEnvironmentContent()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserEnvironmentContent()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserEnvironmentContent()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERENVIRONMENTCONTENT, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXUserRequest#getUserEnvironmentGroup(int, String, byte, byte)
	 */
	@Override
	public void getUserEnvironmentGroup(int requestID, String sGroupID, byte bUserSetupType, byte bProductSetupType) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERENVIRONMENTGROUP, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERENVIRONMENTGROUP, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_GET_USERENVIRONMENTGROUP);// 儲存所使用之功能代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢用戶系統環境設定資料
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U05", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定群組代碼
			fixData.setValue(sGroupID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定群組明細序號
			fixData.setValue("0");
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 回覆欄位Tag列表
			fixData.setValue("");
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("5");// 用戶GW帳號
			if (bUserSetupType == 0) {// 0為系統預設
				fixData.setValue("0");
			}else {// 1為用戶GW Id
				fixData.setValue(String.valueOf(jabxKernel.getLoginInfo().get(JSConst.LN_USERGWID)));
			}
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("6");// 產品代碼
			if (bProductSetupType == 0) {// 0為系統預設
				fixData.setValue("0");
			}else {// 1為產品代碼
				fixData.setValue(String.valueOf(jabxKernel.getLoginInfo().get(JSConst.LN_PRODUCTID)));
			}
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U05", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.getUserEnvironmentGroup()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserEnvironmentGroup()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserEnvironmentGroup()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserEnvironmentGroup()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERENVIRONMENTGROUP, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXUserRequest#getUserEnvironmentList(int, String, byte, byte)
	 */
	@Override
	public void getUserEnvironmentList(int requestID, String sGroupID, byte bUserSetupType, byte bProductSetupType) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERENVIRONMENTLIST, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERENVIRONMENTLIST, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_GET_USERENVIRONMENTLIST);// 儲存所使用之功能代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢用戶系統環境設定資料
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U05", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定群組代碼
			fixData.setValue(sGroupID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定群組明細序號
			fixData.setValue("0");
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 回覆欄位Tag列表
			fixData.setValue("");
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("5");// 用戶GW帳號
			if (bUserSetupType == 0) {// 0為系統預設
				fixData.setValue("0");
			}else {// 1為用戶GW Id
				fixData.setValue(String.valueOf(jabxKernel.getLoginInfo().get(JSConst.LN_USERGWID)));
			}
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("6");// 產品代碼
			if (bProductSetupType == 0) {// 0為系統預設
				fixData.setValue("0");
			}else {// 1為產品代碼
				fixData.setValue(String.valueOf(jabxKernel.getLoginInfo().get(JSConst.LN_PRODUCTID)));
			}
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U05", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.getUserEnvironmentList()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserEnvironmentList()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserEnvironmentList()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserEnvironmentList()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERENVIRONMENTLIST, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXUserRequest#getUserFeedback(int, int, int, byte, byte, byte)
	 */
	@Override
	public void getUserFeedback(int requestID, int iStart, int iEnd, byte bUserFlag, byte bProductFlag, byte bPlatformflag) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERFEEDBACK, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERFEEDBACK, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_GET_USERFEEDBACK);// 儲存所使用之功能代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢用戶意見反饋列表
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U11", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定查詢起始日期
			fixData.setValue(String.valueOf(iStart));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定查詢結止日期
			fixData.setValue(String.valueOf(iEnd));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 意見反饋之用戶旗標
			fixData.setValue(String.valueOf(bUserFlag));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 反饋的產品代碼旗標
			fixData.setValue(String.valueOf(bProductFlag));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("5");// 反饋的產品平台代碼旗標
			fixData.setValue(String.valueOf(bPlatformflag));
			queryList.add(fixData);
			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U11", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.getUserFeedback()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserFeedback()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserFeedback()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserFeedback()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERFEEDBACK, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXUserRequest#getUserFeedbackContent(int, int, int)
	 */
	@Override
	public void getUserFeedbackContent(int requestID, int iFeedbackDate, int iFeedbackNo) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERFEEDBACKCONTENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERFEEDBACKCONTENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), iFeedbackNo);// 儲存所使用之意見反饋序號

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢用戶意見反饋內容
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U12", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 意見反饋日期
			fixData.setValue(String.valueOf(iFeedbackDate));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 意見反饋序號
			fixData.setValue(String.valueOf(iFeedbackNo));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 回覆欄位Tag列表
			fixData.setValue("");
			queryList.add(fixData);
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U12", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.getUserFeedbackContent()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserFeedbackContent()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserFeedbackContent()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserFeedbackContent()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERFEEDBACKCONTENT, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXUserRequest#getUserInfo(int, int, String)
	 */
	@Override
	public void getUserInfo(int requestID, int loginType, String account) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERINFO, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERINFO, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 用戶資料查詢
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U03", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 登入方式
			fixData.setValue(String.valueOf(loginType));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 登入資料
			fixData.setValue(account);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("99");// 回覆欄位Tag列表
			fixData.setValue("");
			queryList.add(fixData);
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U03", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.getUserInfo()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserInfo()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserInfo()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.getUserInfo()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERINFO, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXUserRequest#setUserEnvironmentContent(int, String, int, String, byte)
	 */
	@Override
	public void setUserEnvironmentContent(int requestID, String sGroupID, int iGroupNo, String sContent, byte bProductSetupType) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SET_USERENVIRONMENTCONTENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SET_USERENVIRONMENTCONTENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_SET_USERENVIRONMENTCONTENT);// 儲存所使用之功能代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢用戶系統環境設定資料
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U06", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定群組代碼
			fixData.setValue(sGroupID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定群組明細序號
			fixData.setValue(String.valueOf(iGroupNo));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 設定群組明細內容長度
			fixData.setValue(String.valueOf(sContent.length()));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("5");// 設定群組明細內容
			fixData.setValue(sContent);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("6");// 產品代碼
			if (bProductSetupType == 0) {// 0為系統預設
				fixData.setValue("0");
			}else {// 1為產品代碼
				fixData.setValue(String.valueOf(jabxKernel.getLoginInfo().get(JSConst.LN_PRODUCTID)));
			}
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U06", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.setUserEnvironmentContent()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.setUserEnvironmentContent()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.setUserEnvironmentContent()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.setUserEnvironmentContent()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SET_USERENVIRONMENTCONTENT, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXUserRequest#setUserEnvironmentGroup(int, String, int, String, byte)
	 */
	@Override
	public void setUserEnvironmentGroup(int requestID, String sGroupID, int iGroupNo, String sGroupName, byte bProductSetupType) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SET_USERENVIRONMENTGROUP, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SET_USERENVIRONMENTGROUP, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_SET_USERENVIRONMENTGROUP);// 儲存所使用之功能代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢用戶系統環境設定資料
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U06", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定群組代碼
			fixData.setValue(sGroupID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定群組明細序號
			fixData.setValue(String.valueOf(iGroupNo));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 設定群組明細名稱
			fixData.setValue(sGroupName);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("6");// 產品代碼
			if (bProductSetupType == 0) {// 0為系統預設
				fixData.setValue("0");
			}else {// 1為產品代碼
				fixData.setValue(String.valueOf(jabxKernel.getLoginInfo().get(JSConst.LN_PRODUCTID)));
			}
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U06", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.setUserEnvironmentGroup()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.setUserEnvironmentGroup()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.setUserEnvironmentGroup()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.setUserEnvironmentGroup()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SET_USERENVIRONMENTGROUP, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXUserRequest#warningDelete(int, String, String)
	 */
	@Override
	public void warningDelete(int requestID, String stkID, String groupID) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_WARNING_DELETE, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_WARNING_DELETE, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_WARNING_DELETE);// 儲存所使用之功能代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢用戶系統環境設定資料
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U09", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定股票代碼
			fixData.setValue(stkID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定群組代碼
			fixData.setValue(groupID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 類別
			fixData.setValue("D");
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U09", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.warningDelete()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.warningDelete()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.warningDelete()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.warningDelete()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_WARNING_DELETE, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXUserRequest#warningOnOff(int, String)
	 */
	@Override
	public void warningOnOff(int requestID, String onOff) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_WARNING_ONOFF, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_WARNING_ONOFF, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_WARNING_ONOFF);// 儲存所使用之功能代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢用戶系統環境設定資料
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U09", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定股票代碼
			fixData.setValue("");
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 類別
			fixData.setValue(onOff);
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U09", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.warningOnOff()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.warningOnOff()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.warningOnOff()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.warningOnOff()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_WARNING_ONOFF, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXUserRequest#warningQuery(int, String)
	 */
	@Override
	public void warningQuery(int requestID, String stkID) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_WARNING_QUERY, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_WARNING_QUERY, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_WARNING_QUERY);// 儲存所使用之功能代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢用戶系統環境設定資料
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U08", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定股票代碼
			fixData.setValue(stkID);
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U08", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.warningQuery()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.warningQuery()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.warningQuery()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.warningQuery()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_WARNING_QUERY, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXUserRequest#warningUpdate(int, String, String, String, int, int)
	 */
	@Override
	public void warningUpdate(int requestID, String stkID, String groupID, String warnValue, int warnTime, int warnCount) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_WARNING_UPDATE, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_WARNING_UPDATE, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_WARNING_UPDATE);// 儲存所使用之功能代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢用戶系統環境設定資料
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "U09", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定股票代碼
			fixData.setValue(stkID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定群組代碼
			fixData.setValue(groupID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 類別
			fixData.setValue("M");
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 設定值
			fixData.setValue(warnValue);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("5");// 警示時間
			fixData.setValue(String.valueOf(warnTime));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("6");// 警示次數
			fixData.setValue(String.valueOf(warnCount));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("U09", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXUserRequest.warningUpdate()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {// 檢查連線狀態
					ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(ipiObj.getIdAndPort());
					if (socket == null) {
						nErrCode = JABXErrCode.UNCONNECTING;
						siObj.setConnectStatus(false);
					}else {
						socket.outputDataByParam(sparam);
					}
				}else {
					nErrCode = JABXErrCode.UNCONNECTING;
				}
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.warningUpdate()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.warningUpdate()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXUserRequest.warningUpdate()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_WARNING_UPDATE, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}
}
