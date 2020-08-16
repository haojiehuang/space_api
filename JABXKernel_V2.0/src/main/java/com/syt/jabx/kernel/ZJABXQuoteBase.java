package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.syt.jabx.bean.JABXFixData;
import com.syt.jabx.bean.JABXQuoteDataInfo;
import com.syt.jabx.bean.JABXSocketParam;
import com.syt.jabx.notify.IJABXLog;

/**
 * 行情資訊請求基本類別
 * @author jason
 *
 */
class ZJABXQuoteBase extends IJABXParseBase {

	/**
	 * 應用程序核心管理類別
	 */
	protected JABXKernel jabxKernel;

	/**
	 * 輸出訊息及Log之介面
	 */
	protected IJABXLog jabxLog;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 */
	public ZJABXQuoteBase(JABXKernel jabxKernel, IJABXLog jabxLog) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
	}

	/**
	 * 上傳Socket數據
	 * @param requestID - int(API回覆碼)
	 * @param funcID - int(功能代碼)
	 * @param bakParam - Object(上一次查詢的參數)
	 * @throws OutOfMemoryError
	 * @throws Exception
	 */
	protected void uploadSocketData(int requestID, int funcID, Map<String, String> mustSendMap) throws OutOfMemoryError, Exception {
		try {
			// 合併訂閱數據之Map
			Map<String, JABXQuoteDataInfo> mergeDataInfoMap = jabxKernel.getReservedQuoteData().getMergeDataInfo();			
			ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
			if (soObj != null) {// 取得伺服器索引物件
				String idAndPort;
				JABXQuoteDataInfo mdiObj;
				ZJABXSocket socket;
				ZJABXServerItem siObj;
				List<String> serverList = soObj.getServerListByHostType(JABXConst.SERVER_TYPE_0);// 取得主機屬性為0的Server
				if (serverList != null && serverList.size() != 0) {
					for(int i = 0, size = serverList.size();i < size;i++) {
						idAndPort = serverList.get(i);// 取得Server的hostID:hostPort
						mdiObj = mergeDataInfoMap.get(idAndPort);// 取得依idAndPort分類之訂閱數據
						if (mdiObj != null) {// 若有訂閱數據
							siObj = soObj.atIpAndPort(idAndPort);
							if (!siObj.getConnectStatus()) {
								addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_QUOTE, requestID, idAndPort, JABXErrCode.UNCONNECTING);
								continue;
							}
							jabxKernel.putInfoMapItem(idAndPort + "-" + requestID, funcID);// 儲存功能代碼
							JABXCtrlHeader ctrlHeader = getCtrlHeader(JABXConst.ABUS_MAINTYPE_CONTROL, JABXConst.ABUS_CONTROL_SUBSCRIBE, "R00", requestID, siObj, jabxKernel);

							JABXFixData fixData = new JABXFixData();
							// 記錄上傳之查詢參數之List
							List<JABXFixData> queryList = new ArrayList<JABXFixData>();
							fixData = new JABXFixData();
							fixData.setTag("1");// 連線ID
							fixData.setValue(String.valueOf(siObj.getSessionID()));
							queryList.add(fixData);

							fixData = new JABXFixData();
							fixData.setTag("2");// 筆數
							fixData.setValue(String.valueOf(mdiObj.getDataCount()));
							queryList.add(fixData);
							
							queryList.addAll(mdiObj.getFixDataList());
							
							byte[] queryAry = getBytesFromListFixData(queryList);
							
							// 輸出串流
							JABXSocketParam sparam = new JABXSocketParam("R00", ctrlHeader, queryAry);

							socket = jabxKernel.getRealtimeServerMap().get(idAndPort);
							if (socket == null) {
								addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_QUOTE, requestID, idAndPort, JABXErrCode.ABUS_UNKONWNHOST_ERROR);
								continue;								
							}else {
								socket.outputDataByParam(sparam);
							}
							
							ctrlHeader = null;
							fixData = null;
							queryList = null;
							queryAry = null;
							sparam = null;
						}else {// 無訂閱數據，則取消訂閱
							String isSend = mustSendMap.get(idAndPort);
							if (isSend != null) {
								siObj = soObj.atIpAndPort(idAndPort);
								if (!siObj.getConnectStatus()) {
									addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_QUOTE, requestID, idAndPort, JABXErrCode.UNCONNECTING);
									continue;
								}
								jabxKernel.putInfoMapItem(idAndPort + "-" + requestID, funcID);// 儲存功能代碼
								JABXCtrlHeader ctrlHeader = getCtrlHeader(JABXConst.ABUS_MAINTYPE_CONTROL, JABXConst.ABUS_CONTROL_SUBSCRIBE, "R00", requestID, siObj, jabxKernel);

								JABXFixData fixData = new JABXFixData();
								// 記錄上傳之查詢參數之List
								List<JABXFixData> queryList = new ArrayList<JABXFixData>();
								fixData = new JABXFixData();
								fixData.setTag("1");// 連線ID
								fixData.setValue(String.valueOf(siObj.getSessionID()));
								queryList.add(fixData);

								fixData = new JABXFixData();
								fixData.setTag("2");// 筆數
								fixData.setValue("0");
								queryList.add(fixData);

								byte[] queryAry = getBytesFromListFixData(queryList);

								// 輸出串流
								JABXSocketParam sparam = new JABXSocketParam("R00", ctrlHeader, queryAry);

								socket = jabxKernel.getRealtimeServerMap().get(idAndPort);
								if (socket == null) {
									addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_QUOTE, requestID, idAndPort, JABXErrCode.ABUS_UNKONWNHOST_ERROR);
									continue;								
								}else {
									socket.outputDataByParam(sparam);
								}
								siObj.setSessionID(0);// 因為取消訂閱，所以要將SessionID重置
								
								ctrlHeader = null;
								fixData = null;
								queryList = null;
								queryAry = null;
								sparam = null;
							}
						}
					}
				}

				idAndPort = null;
				mdiObj = null;
				socket = null;
				siObj = null;
				serverList = null;
			}
		}catch(OutOfMemoryError e) {
			jabxLog.outputErrorAndLog("JABXQuoteBase.uploadSocketData()", e.getMessage());
			throw e;
		}catch(Exception e) {
			jabxLog.outputErrorAndLog("JABXQuoteBase.uploadSocketData()", e.getMessage());
			throw e;
		}
	}
}
