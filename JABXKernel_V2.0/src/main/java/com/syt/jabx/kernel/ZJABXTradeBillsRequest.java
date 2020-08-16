package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXFixData;
import com.syt.jabx.bean.JABXSocketParam;
import com.syt.jabx.notify.IJABXLog;

/**
 * 實作交易及帳務請求資訊介面之類別
 * @author jason
 *
 */
final class ZJABXTradeBillsRequest extends IJABXParseBase implements IJABXTradeBillsRequest {

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
	public ZJABXTradeBillsRequest(JABXKernel jabxKernel, IJABXLog jabxLog) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXTradeBillsRequest#tradeBills(int, String, JSONObject)
	 */
	@Override
	public void tradeBills(int requestID, String funcCode, JSONObject param) {
		// TODO Auto-generated method stub
		if (param == null) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_TRADE_BILLS, requestID, "", JABXErrCode.NULL_POINTER);
			return;
		}
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfoByServer_1();
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_TRADE_BILLS, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非交易主機或GW主機，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_TRADE_BILLS, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
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
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), funcCode, requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData;
			String key, value;
			Iterator<String> it = param.keys();
			while (it.hasNext()) {
				key = it.next();
				value = param.getString(key);
				fixData = new JABXFixData();
				fixData.setTag(key);
				fixData.setValue(value);
				queryList.add(fixData);
			}
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam(funcCode, ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXTradeBillsRequest.tradeBills()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXTradeBillsRequest.tradeBills()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXTradeBillsRequest.tradeBills()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXTradeBillsRequest.tradeBills()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_TRADE_BILLS, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}
}
