package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.bean.JABXFixData;
import com.syt.jabx.bean.JABXQuoteDataInfo;
import com.syt.jabx.bean.JABXSocketParam;
import com.syt.jabx.notify.IJABXLog;

/**
 * 實作交易回報資訊請求介面之類別
 * @author jason
 *
 */
final class ZJABXTradeReportRequest extends ZJABXQuoteBase implements IJABXTradeReportRequest {

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
	 * 記錄訂閱交易回報參數之List物件
	 */
	private List<String> exchangeAndAccountIDParamList;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 */
	public ZJABXTradeReportRequest(JABXKernel jabxKernel, IJABXLog jabxLog) {
		super(jabxKernel, jabxLog);
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXTradeReportRequest#sendMessageToServer(int, int, String, String)
	 */
	@Override
	public void sendMessageToServer(int requestID, int msgSubType, String msgKey, String msg) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_2);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SENDMESSAGE_TO_SERVER, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SENDMESSAGE_TO_SERVER, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢用戶系統環境設定資料
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "S10", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定廣播訊息次代碼
			fixData.setValue(Integer.toHexString(msgSubType));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定訊息鍵值
			fixData.setValue(msgKey);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 設定數據長度
			fixData.setValue(String.valueOf(msg.getBytes(JABXConst.ABX_CHARSET).length));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 設定數據內容
			fixData.setValue(new String(msg.getBytes(JABXConst.ABX_CHARSET)));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("S10", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXTradeReportRequest.sendMessageToServer()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXTradeReportRequest.sendMessageToServer()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXTradeReportRequest.sendMessageToServer()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXTradeReportRequest.sendMessageToServer()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SENDMESSAGE_TO_SERVER, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXTradeReportRequest#subscribeTradeReport(int, List)
	 */
	@Override
	public void subscribeTradeReport(int requestID, List<String> listOfExchangeAndAccountID) {
		// TODO Auto-generated method stub
		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			/* 500-01-Begin: 檢查有那些Server需要上傳資料，以清除不在此次訂閱之數據，主要是針對有多台ABus Server時所要做的處理。
			 * 例如：上次訂閱T1及H1開頭的數據，此次只訂閱T1開頭的數據，因此要將H1開頭的數據清除。*/
			Map<String, String> mustSendMap = new HashMap<String, String>();
			if (exchangeAndAccountIDParamList != null && exchangeAndAccountIDParamList.size() != 0) {
				String eaacID, exchangeID, idAndPort;
				for (int i = 0, size = exchangeAndAccountIDParamList.size();i < size;i++) {
					eaacID = exchangeAndAccountIDParamList.get(i);
					exchangeID = eaacID.substring(0, 2);
					idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_2);
					mustSendMap.put(idAndPort, "");
				}
				eaacID = null;
				exchangeID = null;
				idAndPort = null;
			}
			// 500-01-End.

			// 500-02-Begin: 設定訂閱即時交易回報訊息查詢參數
			if (listOfExchangeAndAccountID != null && listOfExchangeAndAccountID.size() != 0) {
				// 記錄查詢參數之List
				List<String> bakIDParamList = new ArrayList<String>();
				String sourceID;
				for (int i = 0, size = listOfExchangeAndAccountID.size();i < size;i++) {
					sourceID = listOfExchangeAndAccountID.get(i);

					bakIDParamList.add(sourceID);
				}
				exchangeAndAccountIDParamList = bakIDParamList;
				bakIDParamList = null;
			}else {
				if (exchangeAndAccountIDParamList != null) {
					exchangeAndAccountIDParamList.clear();
				}
			}
			// 500-02-End.

			if (exchangeAndAccountIDParamList != null && exchangeAndAccountIDParamList.size() != 0) {
				Map<String, JABXQuoteDataInfo> mergeDataMap = new HashMap<String, JABXQuoteDataInfo>();
				JABXFixData fixData;
				String exchangeAndAccountID, exchangeID, idAndPort;
				JABXQuoteDataInfo mdInfo;
				List<JABXFixData> fdList;
				for (int i = 0, size = exchangeAndAccountIDParamList.size();i < size;i++) {
					exchangeAndAccountID = exchangeAndAccountIDParamList.get(i);
					exchangeID = exchangeAndAccountID.substring(0, 2);
					idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_2);
					mdInfo = mergeDataMap.get(idAndPort);
					if (mdInfo == null) {
						mdInfo = new JABXQuoteDataInfo();
					}
					mergeDataMap.put(idAndPort, mdInfo);

					fdList = new ArrayList<JABXFixData>();
			
					fixData = new JABXFixData();
					fixData.setTag("3");// 訊息主代碼
					fixData.setValue(String.valueOf(JABXConst.ABUS_MAINTYPE_FREEFORMAT));
					fdList.add(fixData);
	
					fixData = new JABXFixData();
					fixData.setTag("4");// 訊息次代碼
					fixData.setValue(Integer.toHexString(JABXConst.ABUS_FREEMSG_TRADE));
					fdList.add(fixData);
	
					fixData = new JABXFixData();
					fixData.setTag("5");// 交易所代碼+下單帳號
					fixData.setValue(exchangeAndAccountID);
					fdList.add(fixData);

					mdInfo.addCount(1);
					mdInfo.addFixDataList(fdList);
					
					fdList = null;
				}
				
				jabxKernel.getReservedQuoteData().putSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_TRADEREPORT), mergeDataMap);

				fixData = null;
				exchangeAndAccountID = null;
				exchangeID = null;
				idAndPort = null;
				mdInfo = null;
				fdList = null;
			}else {
				jabxKernel.getReservedQuoteData().removeSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_TRADEREPORT));
			}

			// 上傳Socket數據
			uploadSocketData(requestID, JABXConst.ABXFUN_SUBSCRIBE_TRADEREPORT, mustSendMap);
			mustSendMap = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXTradeReportRequest.subscribeTradeReport()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXTradeReportRequest.subscribeTradeReport()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXTradeReportRequest.subscribeTradeReport()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_TRADEREPORT, requestID, "Execution Error!", nErrCode);
		}		
	}

}
