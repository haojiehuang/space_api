package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.syt.jabx.bean.JABXAServer;
import com.syt.jabx.bean.JABXFixData;
import com.syt.jabx.bean.JABXQuoteCondition;
import com.syt.jabx.bean.JABXQuoteDataInfo;
import com.syt.jabx.bean.JABXSocketParam;
import com.syt.jabx.notify.IJABXLog;

/**
 * 實作行情資訊請求介面之類別
 * @author jason
 *
 */
final class ZJABXQuoteRequest extends ZJABXQuoteBase implements IJABXQuoteRequest {

	/**
	 * 行情資訊工具物件
	 */
	private JABXQuoteTool quoteTool;

	/**
	 * 處理Gateway Session之物件
	 */
	private ZJABXChannelSocket channelSocket = null;

	/**
	 * 是否訂閱警示訊息
	 */
	private boolean isSubscribeWarn;

	/**
	 * 是否訂閱系統公告
	 */
	private boolean isSubscribeSystemBulletin;

	/**
	 * 是否訂閱產品公告訊息
	 */
	private boolean isSubscribeProductBulletin;

	/**
	 * 是否訂閱即時廣告訊息
	 */
	private boolean isSubscribeAdvertisement;

	/**
	 * 記錄訂閱即時報價參數之Map物件，其中之Integer為SessionID
	 */
	private Map<Integer, JSONArray> quoteParamMap;

	/**
	 * 記錄訂閱即時行情報導參數之JSONArray物件
	 */
	private JSONArray classIDParamArray;

	/**
	 * 記錄訂閱即時交易所公告訊息參數之JSONArray物件
	 */
	private JSONArray exchangeIDParamArray;

	/**
	 * 記錄訂閱即時新聞訊息參數之JSONArray物件
	 */
	private JSONArray sourceIDParamArray;

	/**
	 * 記錄訂閱主力大單參數之JSONArray物件
	 */
	private JSONArray smartMasterParamArray;

	/**
	 * 記錄訂閱短線精靈參數之JSONArray物件
	 */
	private JSONArray smartShortParamArray;

	/**
	 * 記錄訂閱即時排名參數之JSONArray物件
	 */
	private JSONArray smartRankParamArray;

	/**
	 * 記錄訂閱LineID參數之JSONArray物件
	 */
	private JSONArray lineIDParamArray;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 * @param quoteTool - JABXQuoteTool;
	 */
	public ZJABXQuoteRequest(JABXKernel jabxKernel, IJABXLog jabxLog, JABXQuoteTool quoteTool) {
		super(jabxKernel, jabxLog);
		this.quoteTool = quoteTool;
		quoteParamMap = new HashMap<Integer, JSONArray>();
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#downloadFile(org.json.JSONObject)
	 */
	@Override
	public String downloadFile(JSONObject jsonObj) {
		// TODO Auto-generated method stub

		String errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD, jsonObj);
		if (!errMsg.equals("")) {
			return errMsg;
		}
		int requestID = jsonObj.getInt(JSConst.CU_REQUESTID);
		int fileType = jsonObj.getInt(JSConst.DN_FILETYPE);
		switch (fileType) {
		case JABXConst.ABXFUN_DOWNLOAD_BLOCKCLASS:// 下載自定(板塊)分類資料
			errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD_BLOCKCLASS, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				downloadBlockClass(requestID, jsonObj.getString(JSConst.DN_FILENO));
			}
			break;
		case JABXConst.ABXFUN_DOWNLOAD_CLASSRELATIONSTOCKLIST:// 下載分類關連股票索引資料
			errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD_CLASSRELATIONSTOCKLIST, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				downloadClassRelationStockList(requestID, jsonObj.getString(JSConst.DN_FILENO));
			}
			break;
		case JABXConst.ABXFUN_DOWNLOAD_EXCHANGELIST:// 下載交易所名稱資料檔
			downloadExchangeList(requestID);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_FORMULACLASS:// 下載公式分類資料檔
			errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD_FORMULACLASS, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				downloadFormulaClass(requestID, jsonObj.getString(JSConst.DN_FILENO));
			}
			break;
		case JABXConst.ABXFUN_DOWNLOAD_FORMULALIST:// 下載公式名稱資料檔
			errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD_FORMULALIST, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				downloadFormulaList(requestID, jsonObj.getString(JSConst.DN_FILENO));
			}
			break;
		case JABXConst.ABXFUN_DOWNLOAD_FUTUREOPTION:// 下載期權股名檔
			errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD_FUTUREOPTION, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				downloadFutureOptionTable(requestID, jsonObj.getString(JSConst.DN_FILENO), jsonObj.getString(JSConst.DN_ENCODEING));
			}
			break;
		case JABXConst.ABXFUN_DOWNLOAD_OTHERCLASS:// 下載其他分類設定檔
			downloadOtherClass(requestID);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_PRODUCTCLASS:// 下載產品功能分類資料檔
			downloadProductClass(requestID);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_SMARTRANKCLASS:// 下載即時排名分類資料
			downloadSmartRankClass(requestID);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_SMARTSHORTCLASS:// 下載智慧選股(短線精靈)分類資料
			downloadSmartShortClass(requestID);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_SPREADCLASS:// 下載價差交易分類資料
			downloadSpreadClass(requestID);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_STKTYPETREE:// 下載分類樹資料
			errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD_STKTYPETREE, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				downloadClassTree(requestID, jsonObj.getString(JSConst.DN_FILENO));
			}
			break;
		case JABXConst.ABXFUN_DOWNLOAD_STOCKCLASS:// 下載商品分類資料
			downloadStockClass(requestID);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_STOCKRELATIONCLASSLIST:// 下載股票關連分類列表
			errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD_STOCKRELATIONCLASSLIST, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				downloadStockRelationClassList(requestID, jsonObj.getString(JSConst.DN_FILENO));
			}
			break;
		case JABXConst.ABXFUN_DOWNLOAD_STOCKRELATIONSTOCKLIST:// 下載交易所1股票關連交易所2股票資料檔
			errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD_STOCKRELATIONSTOCKLIST, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				downloadStockRelationStockList(requestID, jsonObj.getString(JSConst.DN_FILENO), jsonObj.getString(JSConst.DN_FILENO2));
			}
			break;
		case JABXConst.ABXFUN_DOWNLOAD_STOCKRELATIONWARRANTSLIST:// 下載一般股票關連權證股票(港股)資料檔
			errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD_STOCKRELATIONWARRANTSLIST, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				downloadStockRelationWarrantsList(requestID, jsonObj.getString(JSConst.DN_FILENO));
			}
			break;
		case JABXConst.ABXFUN_DOWNLOAD_STOCKTABLE:// 下載股票名稱(拼音)資料檔
			errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD_STOCKTABLE, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				downloadStockTable(requestID, jsonObj.getString(JSConst.DN_FILENO), 
						jsonObj.getString(JSConst.DN_ENCODEING), jsonObj.getString(JSConst.DN_PINYING_TYPE));
			}
			break;
		case JABXConst.ABXFUN_DOWNLOAD_TECHNICALINDEXCLASS:// 下載技術指標分類資料檔
			errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD_TECHNICALINDEXCLASS, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				downloadTechnicalIndexClass(requestID, jsonObj.getString(JSConst.DN_FILENO));
			}
			break;
		case JABXConst.ABXFUN_DOWNLOAD_TECHNICALINDEXLIST:// 下載技術指標名稱資料檔
			errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD_TECHNICALINDEXLIST, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				downloadTechnicalIndexList(requestID, jsonObj.getString(JSConst.DN_FILENO));
			}
			break;
		case JABXConst.ABXFUN_DOWNLOAD_TRADECLASS:// 下載產業分類資料
			errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD_TRADECLASS, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				downloadTradeClass(requestID, jsonObj.getString(JSConst.DN_FILENO));
			}
			break;
		case JABXConst.ABXFUN_DOWNLOAD_TRADECLASS2:// 下載產業分類資料2
			errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD_TRADECLASS2, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				downloadTradeClass2(requestID, jsonObj.getString(JSConst.DN_FILENO));
			}
			break;
		case JABXConst.ABXFUN_DOWNLOAD_WARNCLASS:// 下載警示分類資料
			errMsg = ZJABXValidation.validate(JSV_Const.DOWNLOAD_WARNCLASS, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				downloadWarnClass(requestID, jsonObj.getString(JSConst.DN_FILENO));
			}
			break;
		}
		
		return "";
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#subscribe(org.json.JSONObject)
	 */
	@Override
	public String subscribe(JSONObject jsonObj) {
		// TODO Auto-generated method stub

		String errMsg = ZJABXValidation.validate(JSV_Const.SUBSCRIBE, jsonObj);
		if (!errMsg.equals("")) {
			return errMsg;
		}
		int requestID = jsonObj.getInt(JSConst.CU_REQUESTID);
		int funcID = jsonObj.getInt(JSConst.SE_FUNCID);
		switch (funcID) {
		case JABXConst.ABXFUN_SUBSCRIBE_QUOTE:// 訂閱即時報價
			errMsg = ZJABXValidation.validate(JSV_Const.SUBSCRIBE_QUOTE, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				subscribeQuote(requestID, jsonObj.getInt(JSConst.SE_SESSIONID), jsonObj.getJSONArray(JSConst.SE_QUOTE_INFO));
			}			
			break;
		case JABXConst.ABXFUN_SUBSCRIBE_SMARTMASTER:// 訂閱即時主力大單訊息
			errMsg = ZJABXValidation.validate(JSV_Const.SUBSCRIBE_SMARTMASTER, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				subscribeSmartMaster(requestID, jsonObj.getJSONArray(JSConst.SE_SMARTMASTER_INFO));
			}
			break;
		case JABXConst.ABXFUN_SUBSCRIBE_SMARTSHORT:// 訂閱即時短線精靈訊息
			errMsg = ZJABXValidation.validate(JSV_Const.SUBSCRIBE_SMARTSHORT, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				subscribeSmartShort(requestID, jsonObj.getJSONArray(JSConst.SE_SMARTSHORT_INFO));
			}
			break;
		case JABXConst.ABXFUN_SUBSCRIBE_SMARTRANK:// 訂閱即時排名訊息
			errMsg = ZJABXValidation.validate(JSV_Const.SUBSCRIBE_SMARTRANK, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				subscribeSmartRank(requestID, jsonObj.getJSONArray(JSConst.SE_SMARTRANK_INFO));
			}
			break;
		case JABXConst.ABXFUN_SUBSCRIBE_MARKETREPORT:// 訂閱即時行情報導訊息
			errMsg = ZJABXValidation.validate(JSV_Const.SUBSCRIBE_MARKETREPORT, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				subscribeMarketReport(requestID, jsonObj.getJSONArray(JSConst.SE_MARKETREPORT_INFO));
			}
			break;
		case JABXConst.ABXFUN_SUBSCRIBE_ADVERTISEMENT:// 訂閱即時廣告訊息
			errMsg = ZJABXValidation.validate(JSV_Const.SUBSCRIBE_ADVERTISEMENT, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				subscribeAdvertisement(requestID, jsonObj.getBoolean(JSConst.SE_FLAG_INFO));
			}
			break;
		case JABXConst.ABXFUN_SUBSCRIBE_EXCHANGEBULLETIN:// 訂閱即時交易所公告訊息
			errMsg = ZJABXValidation.validate(JSV_Const.SUBSCRIBE_EXCHANGEBULLETIN, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				subscribeExchangeBulletin(requestID, jsonObj.getJSONArray(JSConst.SE_EXCHANGEBULLETIN_INFO));
			}
			break;
		case JABXConst.ABXFUN_SUBSCRIBE_NEWS:// 訂閱即時新聞訊息
			errMsg = ZJABXValidation.validate(JSV_Const.SUBSCRIBE_NEWS, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				subscribeNews(requestID, jsonObj.getJSONArray(JSConst.SE_NEWS_INFO));
			}
			break;
		case JABXConst.ABXFUN_SUBSCRIBE_PRODUCTBULLETIN:// 訂閱即時產品公告訊息
			errMsg = ZJABXValidation.validate(JSV_Const.SUBSCRIBE_PRODUCTBULLETIN, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				subscribeProductBulletin(requestID, jsonObj.getBoolean(JSConst.SE_FLAG_INFO));
			}
			break;
		case JABXConst.ABXFUN_SUBSCRIBE_SYSTEMBULLETIN:// 訂閱即時ABus公告訊息
			errMsg = ZJABXValidation.validate(JSV_Const.SUBSCRIBE_SYSTEMBULLETIN, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				subscribeSystemBulletin(requestID, jsonObj.getBoolean(JSConst.SE_FLAG_INFO));
			}
			break;
		case JABXConst.ABXFUN_SUBSCRIBE_SMARTWARN:// 訂閱警示訊息
			errMsg = ZJABXValidation.validate(JSV_Const.SUBSCRIBE_SMARTWARN, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				subscribeSmartWarn(requestID, jsonObj.getBoolean(JSConst.SE_FLAG_INFO));
			}
			break;
		case JABXConst.ABXFUN_SUBSCRIBE_REALTIMETECHNICAL:// 訂閱即時指標
			errMsg = ZJABXValidation.validate(JSV_Const.SUBSCRIBE_REALTIMETECHNICAL, jsonObj);
			if (!errMsg.equals("")) {
				return errMsg;
			}else {
				subscribeRealtimeTechnical(requestID, jsonObj.getJSONArray(JSConst.SE_REALTIMETECHNICAL_INFO));
			}
			break;
		}
		
		return "";
	}

	/**
	 * 下載Patch檔
	 * @param abxfunCode - int(功能代碼)
	 * @param requestID - int(API回覆碼)
	 * @param fileName - String(檔案名稱)
	 * @param fileSeqNo - String(檔案日期)
	 */
	private synchronized void downloadPatchFile(int abxfunCode, int requestID, String fileName, String fileSeqNo) {
		
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, abxfunCode, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, abxfunCode, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}
		
		ZJABXParameter param = new ZJABXParameter();
		param.setFuncID(abxfunCode);
		param.setParameter(fileName);
		jabxKernel.putInfoMapItem(String.valueOf(requestID), param);// 儲存查詢之功能代碼及檔案名稱
		
		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢ABUS錯誤代碼對照檔
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P00", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 檔案名稱
			fixData.setValue(fileName);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 回覆欄位Tag列表
			fixData.setValue(fileSeqNo);
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P00", ctrlHeader, queryAry);			
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.downloadPatchFile()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.downloadPatchFile()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.downloadPatchFile()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.downloadPatchFile()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, abxfunCode, requestID, siObj.getIPAndPort(), nErrCode);
		}		
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#downloadQuoteData(int, List, int, int, int)
	 */
	@Override
	public void downloadQuoteData(int requestID, List<String> listID, int iQuoteField, int iStart, int iEnd) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_DOWNLOAD_QUOTEDATA, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_DOWNLOAD_QUOTEDATA, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 報價數據下載
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P50", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定下載起始日期
			fixData.setValue(String.valueOf(iStart));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定下載結止日期
			fixData.setValue(String.valueOf(iEnd));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 設定下載欄位代碼
			fixData.setValue(String.valueOf(iQuoteField));
			queryList.add(fixData);

			if (listID != null && listID.size() != 0) {
				StringBuffer sb = new StringBuffer();
				String stkID;
				for (int i = 0, size = listID.size();i < size;i++) {
					stkID = listID.get(i);
					sb.append(stkID);
					sb.append(";");
				}
				fixData = new JABXFixData();
				fixData.setTag("4");// 設定下載目標列表長度
				fixData.setValue(String.valueOf(sb.length()));
				queryList.add(fixData);

				fixData = new JABXFixData();
				fixData.setTag("5");// 設定下載目標列表
				fixData.setValue(sb.toString());
				queryList.add(fixData);
				
				sb = null;
				stkID = null;
			}

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P50", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.downloadQuoteData()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.downloadQuoteData()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.downloadQuoteData()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.downloadQuoteData()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_DOWNLOAD_QUOTEDATA, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#getUserTechnicalIndexCommonSetup(int)
	 */
	@Override
	public void getUserTechnicalIndexCommonSetup(int requestID) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERTECHNICALINDEXCOMMONSETUP, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERTECHNICALINDEXCOMMONSETUP, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 常用指標列表查詢
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P57", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式
			// 500-02-End.

			JABXSocketParam sparam = new JABXSocketParam("P57", ctrlHeader, new byte[0]);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.getUserTechnicalIndexCommonSetup()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.getUserTechnicalIndexCommonSetup()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.getUserTechnicalIndexCommonSetup()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.getUserTechnicalIndexCommonSetup()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERTECHNICALINDEXCOMMONSETUP, requestID, siObj.getIPAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#getUserTechnicalIndexSetup(int, int, int, byte)
	 */
	@Override
	public void getUserTechnicalIndexSetup(int requestID, int iTechnicalIndexID, int iPeriod, byte bDefaultFlag) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERTECHNICALINDEXSETUP, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERTECHNICALINDEXSETUP, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		List<String> paraList = new ArrayList<String>();
		paraList.add(String.valueOf(iTechnicalIndexID));
		paraList.add(String.valueOf(iPeriod));
		paraList.add(String.valueOf(bDefaultFlag));
		jabxKernel.putInfoMapItem(String.valueOf(requestID), paraList);// 儲存查詢之技術指標代碼，周期代碼及預設值旗標

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 技術指標個人化查詢
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P58", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定指標公式代碼
			fixData.setValue(String.valueOf(iTechnicalIndexID));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定資料週期
			fixData.setValue(String.valueOf(iPeriod));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 設定下載欄位代碼
			fixData.setValue(String.valueOf(bDefaultFlag));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 設定回覆欄位Tag列表
			fixData.setValue("");
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P58", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.getUserTechnicalIndexSetup()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.getUserTechnicalIndexSetup()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.getUserTechnicalIndexSetup()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.getUserTechnicalIndexSetup()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_GET_USERTECHNICALINDEXSETUP, requestID, siObj.getIPAndPort(), nErrCode);
		}
	}

	/**
	 * 下載自定(板塊)分類資料
	 * @param requestID - int(API回覆碼)
	 * @param zoneCode - String(交易所/區域 代碼)<br>
	 * &nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;T4:興櫃<br>
	 */
	void downloadBlockClass(int requestID, String zoneCode) {
		// TODO Auto-generated method stub

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_STKTYPEB);
		fileSb.append(zoneCode);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_BLOCKCLASS, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#queryClassRelationStock(int, int)
	 */
	@Override
	public void queryClassRelationStock(int requestID, int classID) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_CLASSRELATIONSTOCK, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_CLASSRELATIONSTOCK, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), classID);// 儲存查詢之分類群組代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 證券分類個股列表查詢	
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P07", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 分類群組代碼
			fixData.setValue(String.valueOf(classID));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P07", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.queryClassRelationStock()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryClassRelationStock()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryClassRelationStock()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryClassRelationStock()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_CLASSRELATIONSTOCK, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * 下載分類關連股票索引資料
	 * @param requestID - int(API回覆碼)
	 * @param zoneCode - String(交易所/區域 代碼)<br>
	 * &nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;T4:興櫃<br>
	 */
	void downloadClassRelationStockList(int requestID, String zoneCode) {
		// TODO Auto-generated method stub

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_STKIDXC);
		fileSb.append(zoneCode);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_CLASSRELATIONSTOCKLIST, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * 下載分類樹資料
	 * @param requestID - int(API回覆碼)
	 * @param classID - String(分類代碼)<br>
	 * &nbsp;&nbsp;0：可取得大分類<br>
	 * &nbsp;&nbsp;9000000：取得手機版分類<br>
	 * &nbsp;&nbsp;9010000：取得PC版分類<br>
	 */
	void downloadClassTree(int requestID, String classID) {
		// TODO Auto-generated method stub

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_STKTYPETREE);
		fileSb.append(classID);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_STKTYPETREE, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#queryExchangeBulletinContent(int, String, int)
	 */
	@Override
	public void queryExchangeBulletinContent(int requestID, String sExchangeID, int iSerialNo) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_EXCHANGEBULLETINCONTENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_EXCHANGEBULLETINCONTENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		List<String> paraList = new ArrayList<String>();
		paraList.add(sExchangeID);
		paraList.add(String.valueOf(iSerialNo));
		jabxKernel.putInfoMapItem(String.valueOf(requestID), paraList);// 儲存查詢之交易所代碼及公告訊息序號

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢交易所公告內容
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "N02", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 公告訊息來源
			fixData.setValue(sExchangeID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 公告訊息序號
			fixData.setValue(String.valueOf(iSerialNo));
			queryList.add(fixData);
			// 500-02-End.

			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("N02", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.queryExchangeBulletinContent()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryExchangeBulletinContent()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryExchangeBulletinContent()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryExchangeBulletinContent()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_EXCHANGEBULLETINCONTENT, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * 下載交易所名稱資料檔
	 * @param requestID - int(API回覆碼)
	 */
	void downloadExchangeList(int requestID) {
		// TODO Auto-generated method stub

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_EXCHANGE);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_EXCHANGELIST, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#queryExpertSelect(int, int, int, int)
	 */
	@Override
	public void queryExpertSelect(int requestID, int iExpertSelectID, int iStart, int iEnd) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_EXPERTSELECT, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_EXPERTSELECT, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢行情報導內容
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "I10", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 查詢起始日期
			fixData.setValue(String.valueOf(iStart));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 查詢結止日期
			fixData.setValue(String.valueOf(iEnd));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 專家選股代碼
			fixData.setValue(String.valueOf(iExpertSelectID));
			queryList.add(fixData);
			// 500-02-End.

			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("I10", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.queryExpertSelect()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryExpertSelect()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryExpertSelect()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryExpertSelect()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_EXPERTSELECT, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * 下載公式分類資料檔
	 * @param requestID - int(API回覆碼)
	 * @param catalogID - String(公式總分類代碼，由後台定義。)
	 */
	void downloadFormulaClass(int requestID, String catalogID) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_FORMTREE);
		fileSb.append(catalogID);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_FORMULACLASS, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * 下載公式名稱資料檔
	 * @param requestID - int(API回覆碼)
	 * @param catalogID - String(公式總分類代碼，由後台定義。)
	 */
	void downloadFormulaList(int requestID, String catalogID) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_FORMLIST);
		fileSb.append(catalogID);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_FORMULALIST, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * 下載期權股名檔
	 * @param requestID - int(API回覆碼)
	 * @param zoneCode - String(交易所代碼或區域代碼)
	 * @param nameType - String(股名檔類型<br>)
	 * &nbsp;&nbsp;T: 繁體<br>
	 * &nbsp;&nbsp;C: 簡體<br>
	 * &nbsp;&nbsp;E: 英文<br>
	 */
	void downloadFutureOptionTable(int requestID, String zoneCode, String nameType) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_STKTBL).append("4").append(nameType);
		fileSb.append(zoneCode);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_FUTUREOPTION, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#queryHistoryQuoteData(int, String, int, int, int, int, int)
	 */
	public void queryHistoryQuoteData(int requestID, String stockID, int period, int recoverDate, int startDay, int endDay, int nCount) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_HISTORYQUOTEDATA, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_HISTORYQUOTEDATA, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		List<String> paraList = new ArrayList<String>();
		paraList.add(String.valueOf(JABXConst.ABXFUN_QUERY_HISTORYQUOTEDATA));
		paraList.add(stockID);
		paraList.add(String.valueOf(period));
		jabxKernel.putInfoMapItem(String.valueOf(requestID), paraList);// 儲存查詢之證券全代碼及周期代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢股票歷史報價數據(K線)
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P51", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定證券全代碼
			fixData.setValue(stockID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定報價數據代碼
			fixData.setValue(String.valueOf(period));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 設定定點復權日期
			fixData.setValue(String.valueOf(recoverDate));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 設定查詢起始日期
			fixData.setValue(String.valueOf(startDay));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("5");// 設定查詢結止日期
			fixData.setValue(String.valueOf(endDay));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("6");// 設定查詢筆數
			fixData.setValue(String.valueOf(nCount));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P51", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.queryHistoryQuoteData()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryHistoryQuoteData()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryHistoryQuoteData()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryHistoryQuoteData()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_HISTORYQUOTEDATA, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#queryLastQuoteData(int, List, int)
	 */
	@Override
	public void queryLastQuoteData(int requestID, List<String> targetList, int quoteID) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_LASTQUOTEDATA, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_LASTQUOTEDATA, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 報價數據下載
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P49", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 下載目標列表
			if (targetList != null && targetList.size() != 0) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0, size = targetList.size();i < size;i++) {
					sb.append(targetList.get(i)).append(";");
				}
				fixData.setValue(sb.toString());
			}else {
				fixData.setValue("");
			}
			queryList.add(fixData);

			fixData.setTag("2");// 設定下載欄位代碼
			fixData.setValue(String.valueOf(quoteID));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P49", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.queryLastQuoteData()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryLastQuoteData()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryLastQuoteData()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryLastQuoteData()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_LASTQUOTEDATA, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#queryMarketReportContent(int, int, int, byte)
	 */
	@Override
	public void queryMarketReportContent(int requestID, int iMarketReportDate, int iMarketReportNo, byte bContentType) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_MARKETREPORTCONTENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_MARKETREPORTCONTENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), iMarketReportNo);// 儲存查詢之行情報導序號

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢行情報導內容
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "I03", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 報導日期
			fixData.setValue(String.valueOf(iMarketReportDate));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 報導序號
			fixData.setValue(String.valueOf(iMarketReportNo));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 回覆欄位Tag列表
			fixData.setValue("");
			queryList.add(fixData);
			// 500-02-End.

			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("I03", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.queryMarketReportContent()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryMarketReportContent()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryMarketReportContent()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryMarketReportContent()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_MARKETREPORTCONTENT, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * 查詢開盤後新增股票
	 * @param requestID - int(API回覆碼)
	 * @param fileName - String(檔案名稱)
	 */
	void queryNewStockData(int requestID, String fileName, Object obj) {

		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_NEWSTOCKDATA, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), fileName);// 儲存下載之檔名稱
		jabxKernel.putInfoMapItem(requestID + "-" + fileName, obj);// 儲存已下載之數據

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢開盤後新增股票
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P04", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// stktbl檔案名
			fixData.setValue(fileName);
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P04", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.queryNewStockData()", "Connecting another session");
				channelSocket = new ZJABXChannelSocket(jabxKernel, jabxLog, siObj, sparam);
				new Thread(channelSocket).start();
				channelSocket = null;
			}else {// 非GW Server
				if (siObj.getConnectStatus()) {
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryNewStockData()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryNewStockData()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryNewStockData()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_NEWSTOCKDATA, requestID, siObj.getIPAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#queryNewsContent(int, int, int)
	 */
	@Override
	public void queryNewsContent(int requestID, int iSourceID, int iSerialNo) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_NEWSCONTENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_NEWSCONTENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		List<String> paraList = new ArrayList<String>();
		paraList.add(String.valueOf(iSourceID));
		paraList.add(String.valueOf(iSerialNo));
		jabxKernel.putInfoMapItem(String.valueOf(requestID), paraList);// 儲存查詢之新聞來源代碼及新聞訊息序號

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢新聞內容
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "N12", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 新聞訊息來源
			fixData.setValue(String.valueOf(iSourceID));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 新聞訊息序號
			fixData.setValue(String.valueOf(iSerialNo));
			queryList.add(fixData);
			// 500-02-End.

			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("N12", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.queryNewsContent()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryNewsContent()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryNewsContent()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryNewsContent()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_NEWSCONTENT, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * 下載其他分類設定檔
	 * @param requestID - int(API回覆碼)
	 */
	void downloadOtherClass(int requestID) {
		// TODO Auto-generated method stub

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_STKTYPEO);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_OTHERCLASS, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#queryProductBulletinContent(int, int)
	 */
	@Override
	public void queryProductBulletinContent(int requestID, int iSerialNo) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_PRODUCTBULLETINCONTENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_PRODUCTBULLETINCONTENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), iSerialNo);// 儲存查詢之產品公告序號

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢產品公告內容
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "I01", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 公告序號
			fixData.setValue(String.valueOf(iSerialNo));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 回覆欄位Tag列表
			fixData.setValue("");
			queryList.add(fixData);
			// 500-02-End.

			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("I01", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.queryProductBulletinContent()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryProductBulletinContent()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryProductBulletinContent()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryProductBulletinContent()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_PRODUCTBULLETINCONTENT, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * 下載產品功能分類資料檔
	 * @param requestID - int(API回覆碼)
	 */
	void downloadProductClass(int requestID) {
		// TODO Auto-generated method stub

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_PCLASS);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_PRODUCTCLASS, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * 下載即時排名分類資料
	 * @param requestID - int(API回覆碼)
	 */
	void downloadSmartRankClass(int requestID) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_STKTYPER);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_SMARTRANKCLASS, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * 下載智慧選股(短線精靈)分類資料
	 * @param requestID - int(API回覆碼)
	 */
	void downloadSmartShortClass(int requestID) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_STKTYPES);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_SMARTSHORTCLASS, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#querySortQuoteData(int, int, List, int, int)
	 */
	@Override
	public void querySortQuoteData(int requestID, int groupID, List<String> targetList, int quoteID, int recordCount) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_SORTQUOTEDATA, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_SORTQUOTEDATA, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 報價數據下載
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P40", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 排行項目ID
			fixData.setValue(String.valueOf(groupID));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 排行目標列表
			if (targetList != null && targetList.size() != 0) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0, size = targetList.size();i < size;i++) {
					sb.append(targetList.get(i)).append(";");
				}
				fixData.setValue(sb.toString());
			}else {
				fixData.setValue("");
			}
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 設定下載欄位代碼
			fixData.setValue(String.valueOf(quoteID));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 設定查詢筆數
			fixData.setValue(String.valueOf(recordCount));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P40", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.querySortQuoteData()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.querySortQuoteData()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.querySortQuoteData()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.querySortQuoteData()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_SORTQUOTEDATA, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * 下載價差交易分類資料
	 * @param requestID - int(API回覆碼)
	 */
	void downloadSpreadClass(int requestID) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_STKTYPEP);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_SPREADCLASS, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * 下載商品分類資料
	 * @param requestID - int(API回覆碼)
	 */
	void downloadStockClass(int requestID) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_STKTYPEC);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_STOCKCLASS, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#queryStockDivide(int, java.lang.String, int, int)
	 */
	@Override
	public void queryStockDivide(int requestID, String stockID, int startIdx, int endIdx) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_STOCKDIVIDE, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_STOCKDIVIDE, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), stockID);// 儲存查詢之證券全代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢證券除權息資料	
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P03", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 證券全代碼
			fixData.setValue(stockID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 回覆欄位Tag列表
			fixData.setValue("");
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 起始索引值
			fixData.setValue(String.valueOf(startIdx));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 截止索引值
			fixData.setValue(String.valueOf(endIdx));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P03", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.queryStockDivide()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryStockDivide()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryStockDivide()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryStockDivide()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_STOCKDIVIDE, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#queryStockRelationClass(int, java.lang.String)
	 */
	@Override
	public void queryStockRelationClass(int requestID, String stkID) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_STOCKRELATIONCLASS, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_STOCKRELATIONCLASS, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), stkID);// 儲存查詢之證券全代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 個股所屬分類查詢	
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P06", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 證券全代碼
			fixData.setValue(stkID);
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P06", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.queryStockRelationClass()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryStockRelationClass()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryStockRelationClass()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryStockRelationClass()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_STOCKRELATIONCLASS, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * 下載股票關連分類列表
	 * @param requestID - int(API回覆碼)
	 * @param zoneCode - String(交易所/區域 代碼)
	 * &nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;T4:興櫃<br>
	 */
	void downloadStockRelationClassList(int requestID, String zoneCode) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_STKIDXS);
		fileSb.append(zoneCode);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_STOCKRELATIONCLASSLIST, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * 下載交易所1股票關連交易所2股票資料檔
	 * @param requestID - int(API回覆碼)
	 * @param zoneCode1 - String(交易所/區域 代碼)<br>
	 * &nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;T4:興櫃<br>
	 * @param zoneCode2 - String(交易所/區域 代碼)<br>
	 * &nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;T4:興櫃<br>
	 */
	void downloadStockRelationStockList(int requestID, String zoneCode1, String zoneCode2) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_STKLINK).append(zoneCode1).append(zoneCode2);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_STOCKRELATIONSTOCKLIST, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * 下載一般股票關連權證股票(港股)資料檔
	 * @param requestID - int(API回覆碼)
	 * @param zoneCode - String(交易所/區域 代碼)
	 * &nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;T4:興櫃<br>
	 */
	void downloadStockRelationWarrantsList(int requestID, String zoneCode) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_STKWAR).append(zoneCode);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_STOCKRELATIONWARRANTSLIST, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * 下載股票名稱(拼音)資料檔
	 * @param requestID - int(API回覆碼)
	 * @param exchangeID - String(交易所代碼)
	 * @param encoding - String(股名檔編碼)<br>
	 * &nbsp;&nbsp;T: 繁體<br>
	 * &nbsp;&nbsp;C: 簡體<br>
	 * &nbsp;&nbsp;E: 英文<br>
	 * @param pinyingType - String(股名檔類別)
	 * &nbsp;&nbsp;1-短股名檔<br>
	 * &nbsp;&nbsp;2-股名拼音股名檔<br>
	 * &nbsp;&nbsp;3-多股名拼音股名檔。股名拼音最多取 2 組。
	 */
	void downloadStockTable(int requestID, String exchangeID, String encoding, String pinyingType) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_STKTBL).append(pinyingType).append(encoding);
		fileSb.append(exchangeID);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_STOCKTABLE, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * 下載技術指標分類資料檔
	 * @param requestID - int(API回覆碼)
	 * @param catalogID - String(技術指標總分類代碼，由後台定義。)
	 */
	void downloadTechnicalIndexClass(int requestID, String catalogID) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_FORMTREE);
		fileSb.append(catalogID);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_TECHNICALINDEXCLASS, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#queryTechnicalIndexData(int, String, int, int, String, int, int, int, int, int)
	 */
	@Override
	public void queryTechnicalIndexData(int requestID, String sStockID,
			int iPeriod, int iTechnicalIndexID, String sParameter,
			int iDataDate, int iRecoverDate, int iStart, int iEnd, int iCount) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_TECHNICALINDEXDATA, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_TECHNICALINDEXDATA, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		List<String> paraList = new ArrayList<String>();
		paraList.add(sStockID);
		paraList.add(String.valueOf(iTechnicalIndexID));
		paraList.add(String.valueOf(iPeriod));
		jabxKernel.putInfoMapItem(String.valueOf(requestID), paraList);// 儲存查詢之證券全代碼,技術指標代碼及周期代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢股票歷史報價數據(K線)
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P52", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定證券全代碼
			fixData.setValue(sStockID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定報價數據代碼或周期代碼
			fixData.setValue(String.valueOf(iPeriod));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 設定指標公式代碼
			fixData.setValue(String.valueOf(iTechnicalIndexID));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 設定指標公式參數設定值
			fixData.setValue(sParameter);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("5");// 設定資料起始日期
			fixData.setValue(String.valueOf(iDataDate));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("6");// 設定定點復權日期
			fixData.setValue(String.valueOf(iRecoverDate));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("7");// 設定查詢起始日期
			fixData.setValue(String.valueOf(iStart));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("8");// 設定查詢結止日期
			fixData.setValue(String.valueOf(iEnd));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("9");// 設定查詢筆數
			fixData.setValue(String.valueOf(iCount));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P52", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.queryTechnicalIndexData()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryTechnicalIndexData()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryTechnicalIndexData()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryTechnicalIndexData()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_TECHNICALINDEXDATA, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#queryTechnicalIndexDefine(int, int)
	 */
	@Override
	public void queryTechnicalIndexDefine(int requestID, int technicalIndexID) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_TECHNICALINDEXDEFINE, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_TECHNICALINDEXDEFINE, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), technicalIndexID);// 儲存查詢之指標公式代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 查詢技術指標公式定義
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P56", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定指標公式代碼
			fixData.setValue(String.valueOf(technicalIndexID));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定回覆欄位Tag列
			fixData.setValue("");
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P56", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.queryTechnicalIndexDefine()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryTechnicalIndexDefine()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryTechnicalIndexDefine()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryTechnicalIndexDefine()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_TECHNICALINDEXDEFINE, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * 下載技術指標名稱資料檔
	 * @param requestID - int(API回覆碼)
	 * @param catalogID - String(技術指標總分類代碼，由後台定義。)
	 */
	void downloadTechnicalIndexList(int requestID, String catalogID) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_FORMLIST);
		fileSb.append(catalogID);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_TECHNICALINDEXLIST, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#queryTickDiff(int, java.lang.String)
	 */
	@Override
	public void queryTickDiff(int requestID, String exchange) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_TICKDIFF, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_TICKDIFF, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), exchange);// 儲存查詢之交易所代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 檔差表查詢	
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P01", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 交易所代碼
			fixData.setValue(exchange);
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P01", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.queryStkDiff()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryTickDiff()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryTickDiff()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryTickDiff()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_TICKDIFF, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * 下載產業分類資料
	 * @param requestID - int(API回覆碼)
	 * @param zoneCode - String(交易所/區域 代碼)
	 * &nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;T4:興櫃<br>
	 */
	void downloadTradeClass(int requestID, String zoneCode) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_STKTYPET);
		fileSb.append(zoneCode);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_TRADECLASS, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * 下載產業分類資料2
	 * @param requestID - int(API回覆碼)
	 * @param zoneCode - String(交易所/區域 代碼)
	 * &nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;T4:興櫃<br>
	 */
	void downloadTradeClass2(int requestID, String zoneCode) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_STKTYPET2);
		fileSb.append(zoneCode);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_TRADECLASS2, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * 下載警示分類資料
	 * @param requestID - int(API回覆碼)
	 * @param groupID - String(群組代碼)
	 */
	void downloadWarnClass(int requestID, String groupID) {

		StringBuffer fileSb = new StringBuffer();
		fileSb.append(JABXConst.FILENAME_WARN);
		fileSb.append(groupID);
		String fileName = fileSb.toString();
		fileSb.append(".dat");
		String datFileName = fileSb.toString();
		String fileSeqNo = jabxKernel.getFileSeqNo(datFileName);
		downloadPatchFile(JABXConst.ABXFUN_DOWNLOAD_WARNCLASS, requestID, fileName, fileSeqNo);
		
		fileSb = null;
		fileName = null;
		fileSeqNo = null;
		datFileName = null;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#queryWarrantRelative(int, java.lang.String)
	 */
	@Override
	public void queryWarrantRelative(int requestID, String stockID) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_WARRANT_RELATIVE, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_WARRANT_RELATIVE, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), stockID);// 儲存查詢之證券全代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 權證相關資料查詢
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P02", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 證券全代碼
			fixData.setValue(stockID);
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P02", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.queryWarrantRelative()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryWarrantRelative()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryWarrantRelative()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.queryWarrantRelative()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_QUERY_WARRANT_RELATIVE, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildAdvertisement(int)
	 */
	@Override
	public void rebuildAdvertisement(int requestID) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_ADVERTISEMENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_ADVERTISEMENT, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補廣告訊息
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "I06", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("I06", ctrlHeader, new byte[0]);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.rebuildAdvertisement()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildAdvertisement()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildAdvertisement()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildAdvertisement()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_ADVERTISEMENT, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildDetailOrder(int, java.lang.String, int, int)
	 */
	@Override
	public void rebuildDetailOrder(int requestID, String stockID, int startIdx, int endIdx) {
		// TODO Auto-generated method stub
		// 10000-Logic-0-Begin: 依交易所代碼取得主機屬性為0的Server之主機代碼及埠號(hostID:hostPort)
		String exchangeID = stockID.substring(0, 2);
		String idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXServerItem siObj = soObj.atIpAndPort(idAndPort);
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MINUTETRADE, requestID, idAndPort, JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			return;
		}
		// 判斷 主機是否已連線
		if (!siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MINUTETRADE, requestID, idAndPort, JABXErrCode.UNCONNECTING);
			return;
		}
		// 10000-Logic-0-End.

		jabxKernel.putInfoMapItem(String.valueOf(requestID), stockID);// 儲存查詢參數

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補逐筆成交上海,深圳資料
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "R14", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式
			
			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 證券全代碼
			fixData.setValue(stockID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 起始索引值
			fixData.setValue(String.valueOf(startIdx));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 截止索引值
			fixData.setValue(String.valueOf(endIdx));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 筆數
			fixData.setValue("0");
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("R14", ctrlHeader, queryAry);
			ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(idAndPort);
			if (socket == null) {
				nErrCode = JABXErrCode.UNCONNECTING;
				siObj.setConnectStatus(false);
			}else {
				socket.outputDataByParam(sparam);
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildDetailOrder()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildDetailOrder()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildDetailOrder()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_DETAILORDER, requestID, idAndPort, nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildDetailTrade(int, java.lang.String, int, int)
	 */
	@Override
	public void rebuildDetailTrade(int requestID, String stockID, int startIdx, int endIdx) {
		// TODO Auto-generated method stub
		// 10000-Logic-0-Begin: 依交易所代碼取得主機屬性為0的Server之主機代碼及埠號(hostID:hostPort)
		String exchangeID = stockID.substring(0, 2);
		String idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXServerItem siObj = soObj.atIpAndPort(idAndPort);
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MINUTETRADE, requestID, idAndPort, JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			return;
		}
		// 判斷 主機是否已連線
		if (!siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MINUTETRADE, requestID, idAndPort, JABXErrCode.UNCONNECTING);
			return;
		}
		// 10000-Logic-0-End.

		jabxKernel.putInfoMapItem(String.valueOf(requestID), stockID);// 儲存查詢參數

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補逐筆成交上海,深圳資料
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "R13", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式
			
			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 證券全代碼
			fixData.setValue(stockID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 起始索引值
			fixData.setValue(String.valueOf(startIdx));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 截止索引值
			fixData.setValue(String.valueOf(endIdx));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 筆數
			fixData.setValue("0");
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("R13", ctrlHeader, queryAry);
			ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(idAndPort);
			if (socket == null) {
				nErrCode = JABXErrCode.UNCONNECTING;
				siObj.setConnectStatus(false);
			}else {
				socket.outputDataByParam(sparam);
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildDetailTrade()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildDetailTrade()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildDetailTrade()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_DETAILTRADE, requestID, idAndPort, nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildExchangeBulletin(int, List, int, int, int)
	 */
	@Override
	public void rebuildExchangeBulletin(int requestID, List<String> listOfExchangeID, int iStart, int iEnd, int iCount) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_EXCHANGEBULLETIN, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_EXCHANGEBULLETIN, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補當日交易所公告標題
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "N00", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			JABXFixData fixData;
			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();
			// 500-02-Begin: 設定查詢參數
			fixData = new JABXFixData();
			fixData.setTag("1");// 回補起始時間
			fixData.setValue(String.valueOf(iStart));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 回補結止時間
			fixData.setValue(String.valueOf(iEnd));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 回補筆數
			fixData.setValue(String.valueOf(iCount));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 交易所代碼列表
			StringBuffer sb = new StringBuffer();
			if (listOfExchangeID != null) {
				for (int i = 0, size = listOfExchangeID.size();i < size;i++) {
					sb.append(listOfExchangeID.get(i));
					sb.append(';');
				}
			}
			fixData.setValue(sb.toString());
			queryList.add(fixData);
			sb = null;

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("N00", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.rebuildExchangeBulletin()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildExchangeBulletin()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildExchangeBulletin()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildExchangeBulletin()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_EXCHANGEBULLETIN, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildHistoryExchangeBulletin(int, String, int, int, int)
	 */
	@Override
	public void rebuildHistoryExchangeBulletin(int requestID, String stockID, int iStart, int iEnd, int iCount) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_HISTORYEXCHANGEBULLETIN, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_HISTORYEXCHANGEBULLETIN, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}


		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補個股歷史交易所公告標題
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "N01", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			//jabxKernel.putInfoMapItem(requestID, stockID);// 儲存查詢參數
			
			JABXFixData fixData;
			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			fixData = new JABXFixData();
			fixData.setTag("1");// 證券全代碼
			fixData.setValue(stockID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 回補起始時間
			fixData.setValue(String.valueOf(iStart));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 回補結止時間
			fixData.setValue(String.valueOf(iEnd));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 回補筆數
			fixData.setValue(String.valueOf(iCount));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("N01", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.rebuildHistoryExchangeBulletin()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildHistoryExchangeBulletin()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildHistoryExchangeBulletin()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildHistoryExchangeBulletin()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_HISTORYEXCHANGEBULLETIN, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildHistoryNews(int, String, int, int, int)
	 */
	@Override
	public void rebuildHistoryNews(int requestID, String stockID, int iStart, int iEnd, int iCount) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_HISTORYENEWS, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_HISTORYENEWS, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補個股歷史新聞標題
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "N11", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			//jabxKernel.putInfoMapItem(requestID, stockID);// 儲存查詢參數
			
			JABXFixData fixData;
			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			fixData = new JABXFixData();
			fixData.setTag("1");// 證券全代碼
			fixData.setValue(stockID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 回補起始時間
			fixData.setValue(String.valueOf(iStart));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 回補結止時間
			fixData.setValue(String.valueOf(iEnd));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 回補筆數
			fixData.setValue(String.valueOf(iCount));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("N11", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.rebuildStockHistoryNews()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildHistoryNews()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildHistoryNews()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildHistoryNews()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_HISTORYENEWS, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildMarketReport(int, List, int, int)
	 */
	@Override
	public void rebuildMarketReport(int requestID, List<String> listOfClassID, int iStart, int iEnd) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MARKETREPORT, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MARKETREPORT, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補行情報導標題
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "I02", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式
		
			JABXFixData fixData;
			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			fixData = new JABXFixData();
			fixData.setTag("1");// 查詢起始日期
			fixData.setValue(String.valueOf(iStart));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 查詢截止日期
			fixData.setValue(String.valueOf(iEnd));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 查詢報導分類代碼列表
			StringBuffer sb = new StringBuffer();
			if (listOfClassID != null) {
				for (int i = 0, size = listOfClassID.size();i < size;i++) {
					sb.append(listOfClassID.get(i));
					sb.append(';');
				}
			}
			fixData.setValue(sb.toString());
			queryList.add(fixData);
			sb = null;

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("I02", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.rebuildMarketReport()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildMarketReport()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildMarketReport()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildMarketReport()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MARKETREPORT, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildMinuteTrade(int, java.lang.String)
	 */
	@Override
	public void rebuildMinuteTrade(int requestID, String stockID) {
		// TODO Auto-generated method stub
		// 10000-Logic-0-Begin: 依交易所代碼取得主機屬性為0的Server之主機代碼及埠號(hostID:hostPort)
		String exchangeID = stockID.substring(0, 2);
		String idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXServerItem siObj = soObj.atIpAndPort(idAndPort);
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MINUTETRADE, requestID, idAndPort, JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			return;
		}
		// 判斷 主機是否已連線
		if (!siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MINUTETRADE, requestID, idAndPort, JABXErrCode.UNCONNECTING);
			return;
		}
		// 10000-Logic-0-End.

		List<String> paraList = new ArrayList<String>();
		paraList.add(String.valueOf(JABXConst.ABXFUN_REBUILD_MINUTETRADE));
		paraList.add(stockID);
		jabxKernel.putInfoMapItem(String.valueOf(requestID), paraList);// 儲存查詢之證券全代碼及周期代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補成交資訊明細
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P51", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式
			
			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定證券全代碼
			fixData.setValue(stockID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定報價數據代碼
			fixData.setValue(String.valueOf(JABXConst.ABX_PRICEDATA_MIN));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("6");// 設定查詢筆數
			fixData.setValue(String.valueOf(-1));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("P51", ctrlHeader, queryAry);
			ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(idAndPort);
			if (socket == null) {
				nErrCode = JABXErrCode.UNCONNECTING;
				siObj.setConnectStatus(false);
			}else {
				socket.outputDataByParam(sparam);
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildMinuteTrade()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildMinuteTrade()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildMinuteTrade()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MINUTETRADE, requestID, idAndPort, nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildNews(int, List, int, int, int)
	 */
	@Override
	public void rebuildNews(int requestID, List<String> listOfSourceID, int iStart, int iEnd, int iCount) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_NEWS, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_NEWS, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補當日新聞標題
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "N10", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式
	
			JABXFixData fixData;
			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();
			// 500-02-Begin: 設定查詢參數
			fixData = new JABXFixData();
			fixData.setTag("1");// 回補起始時間
			fixData.setValue(String.valueOf(iStart));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 回補結止時間
			fixData.setValue(String.valueOf(iEnd));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 回補筆數
			fixData.setValue(String.valueOf(iCount));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 查詢新聞來源代碼列表
			StringBuffer sb = new StringBuffer();
			if (listOfSourceID != null) {
				for (int i = 0, size = listOfSourceID.size();i < size;i++) {
					sb.append(listOfSourceID.get(i));
					sb.append(';');
				}
			}
			fixData.setValue(sb.toString());
			queryList.add(fixData);
			sb = null;

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("N10", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.rebuildNews()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildNews()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildNews()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildNews()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_NEWS, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildOldLotTrade(int, java.lang.String, int, int, int)
	 */
	@Override
	public void rebuildOldLotTrade(int requestID, String stockID, int startIdx, int endIdx, int nCount) {
		// TODO Auto-generated method stub
		// 10000-Logic-0-Begin: 依交易所代碼取得主機屬性為0的Server之主機代碼及埠號(hostID:hostPort)
		String exchangeID = stockID.substring(0, 2);
		String idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXServerItem siObj = soObj.atIpAndPort(idAndPort);
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MINUTETRADE, requestID, idAndPort, JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			return;
		}
		// 判斷 主機是否已連線
		if (!siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MINUTETRADE, requestID, idAndPort, JABXErrCode.UNCONNECTING);
			return;
		}
		// 10000-Logic-0-End.

		jabxKernel.putInfoMapItem(String.valueOf(requestID), stockID);// 儲存查詢參數

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補零股買賣成交-台灣資料
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "R15", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式
			
			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 證券全代碼
			fixData.setValue(stockID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 起始索引值
			fixData.setValue(String.valueOf(startIdx));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 截止索引值
			fixData.setValue(String.valueOf(endIdx));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 筆數
			fixData.setValue(String.valueOf(nCount));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("R15", ctrlHeader, queryAry);
			ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(idAndPort);
			if (socket == null) {
				nErrCode = JABXErrCode.UNCONNECTING;
				siObj.setConnectStatus(false);
			}else {
				socket.outputDataByParam(sparam);
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildOldLotTrade()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildOldLotTrade()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildOldLotTrade()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_OLDLOT, requestID, idAndPort, nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildOneMinuteTrade(int, java.lang.String, int)
	 */
	@Override
	public void rebuildOneMinuteTrade(int requestID, String stockID, int hhmm) {
		// TODO Auto-generated method stub
		// 10000-Logic-0-Begin: 依交易所代碼取得主機屬性為0的Server之主機代碼及埠號(hostID:hostPort)
		String exchangeID = stockID.substring(0, 2);
		String idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXServerItem siObj = soObj.atIpAndPort(idAndPort);
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_ONEMINUTETRADE, requestID, idAndPort, JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			return;
		}
		// 判斷 主機是否已連線
		if (!siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_ONEMINUTETRADE, requestID, idAndPort, JABXErrCode.UNCONNECTING);
			return;
		}
		// 10000-Logic-0-End.

		jabxKernel.putInfoMapItem(String.valueOf(requestID), stockID);// 儲存查詢參數

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補成交資訊明細
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "R96", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式
			
			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 證券全代碼
			fixData.setValue(stockID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 時間
			fixData.setValue(String.valueOf(hhmm));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("R96", ctrlHeader, queryAry);
			ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(idAndPort);
			if (socket == null) {
				nErrCode = JABXErrCode.UNCONNECTING;
				siObj.setConnectStatus(false);
			}else {
				socket.outputDataByParam(sparam);
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildOneMinuteTrade()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildOneMinuteTrade()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildOneMinuteTrade()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_ONEMINUTETRADE, requestID, idAndPort, nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildPriceTrade(int, String)
	 */
	@Override
	public void rebuildPriceTrade(int requestID, String stockID) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_PRICETRADE, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_PRICETRADE, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), stockID);// 儲存查詢之證券全代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 個股成交分價查詢
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P10", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 證券全代碼
			fixData.setValue(stockID);
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P10", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.rebuildPriceTrade()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildPriceTrade()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildPriceTrade()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildPriceTrade()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_PRICETRADE, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildProductBulletin(int, int)
	 */
	@Override
	public void rebuildProductBulletin(int requestID, int iBulletinType) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_PRODUCTBULLETIN, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_PRODUCTBULLETIN, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補產品公告標題
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "I00", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			//jabxKernel.putInfoMapItem(requestID, stockID);// 儲存查詢參數
			
			JABXFixData fixData;
			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			fixData = new JABXFixData();
			fixData.setTag("1");// 公告類型
			fixData.setValue(String.valueOf(iBulletinType));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("I00", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.rebuildProductBulletin()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildProductBulletin()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildProductBulletin()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildProductBulletin()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_PRODUCTBULLETIN, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildQuote(int, java.util.List)
	 */
	@Override
	public void rebuildQuote(int requestID, List<JABXQuoteCondition> list) {
		// TODO Auto-generated method stub
		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			Map<String, JABXQuoteDataInfo> fixDataMap = new HashMap<String, JABXQuoteDataInfo>();
			// 回補即時報價
			if (list != null && list.size() != 0) {
				JABXFixData fixData;
				JABXQuoteCondition cond;
				String stkID, exchangeID, idAndPort;
				JABXQuoteDataInfo fdInfo;
				List<JABXFixData> fdList;
				for (int i = 0, size = list.size();i < size;i++) {
					cond = list.get(i);
					stkID = cond.getStkID();
					exchangeID = stkID.substring(0, 2);

					idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
					fdInfo = fixDataMap.get(idAndPort);
					if (fdInfo == null) {
						fdInfo = new JABXQuoteDataInfo();
					}
					fixDataMap.put(idAndPort, fdInfo);
					
					fdList = new ArrayList<JABXFixData>();

					fixData = new JABXFixData();
					fixData.setTag("2");// 訊息主代碼
					fixData.setValue(String.valueOf(JABXConst.ABUS_MAINTYPE_WATCHLIST));
					fdList.add(fixData);

					fixData = new JABXFixData();
					fixData.setTag("3");// 訊息次代碼
					fixData.setValue(Integer.toHexString(cond.getQuoteID()));
					fdList.add(fixData);

					fixData = new JABXFixData();
					fixData.setTag("4");// 證券鍵值代碼
					fixData.setValue(cond.getStkID());			
					fdList.add(fixData);
					
					fdInfo.addCount(1);
					fdInfo.addFixDataList(fdList);
				}

				fixData = null;
				cond = null;
				stkID = null;
				exchangeID = null;
				idAndPort = null;
				fdInfo = null;
				fdList = null;
			}
			
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
						mdiObj = fixDataMap.get(idAndPort);// 取得依idAndPort分類之回補數據
						if (mdiObj != null) {// 若有回補數據
							siObj = soObj.atIpAndPort(idAndPort);
							if (!siObj.getConnectStatus()) {
								addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_QUOTE, requestID, idAndPort, JABXErrCode.UNCONNECTING);
								continue;
							}
							JABXCtrlHeader ctrlHeader = getCtrlHeader(JABXConst.ABUS_MAINTYPE_CONTROL, JABXConst.ABUS_CONTROL_REBUILD, "R01", requestID, siObj, jabxKernel);

							JABXFixData fixData = new JABXFixData();
							// 記錄上傳之查詢參數之List
							List<JABXFixData> queryList = new ArrayList<JABXFixData>();
							
							fixData.setTag("1");// 筆數
							fixData.setValue(String.valueOf(mdiObj.getDataCount()));
							queryList.add(fixData);

							queryList.addAll(mdiObj.getFixDataList());
							
							byte[] queryAry = getBytesFromListFixData(queryList);
							
							// 輸出串流
							JABXSocketParam sparam = new JABXSocketParam("R01", ctrlHeader, queryAry);

							socket = jabxKernel.getRealtimeServerMap().get(idAndPort);
							if (socket == null) {
								addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_QUOTE, requestID, idAndPort, JABXErrCode.ABUS_UNKONWNHOST_ERROR);
								continue;								
							}else {
								socket.outputDataByParam(sparam);
							}
							
							ctrlHeader = null;
							fixData = null;
							queryList = null;
							queryAry = null;
							sparam = null;
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
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildQuote()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildQuote()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildQuote()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_QUOTE, requestID, "Execution Error!", nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildSmartMaster(int, String, String, byte, int, int, int, int)
	 */
	@Override
	public void rebuildSmartMaster(int requestID, String marketID, String masterType, byte limitType, int limit, int startTime, int endTime, int rebuildCount) {
		// TODO Auto-generated method stub
		// 10000-Logic-0-Begin: 依交易所代碼取得主機屬性為0的Server之主機代碼及埠號(hostID:hostPort)
		String idAndPort = jabxKernel.getIPAndPortByExchangeID(marketID, JABXConst.SERVER_TYPE_0);
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXServerItem siObj = soObj.atIpAndPort(idAndPort);
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_SMARTMASTER, requestID, idAndPort, JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			return;
		}
		// 判斷 主機是否已連線
		if (!siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_SMARTMASTER, requestID, idAndPort, JABXErrCode.UNCONNECTING);
			return;
		}
		// 10000-Logic-0-End.

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補主力大單明細(多筆)
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "R52", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			//jabxKernel.putInfoMapItem(requestID, stockID);// 儲存查詢參數
			
			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();
			JABXFixData fixData;
			// 500-02-Begin: 設定查詢參數
			fixData = new JABXFixData();
			fixData.setTag("1");// 主力大單市場別
			fixData.setValue(marketID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 大單類別
			fixData.setValue(masterType);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 大單額度類別
			fixData.setValue(String.valueOf(limitType));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 大單額度
			fixData.setValue(String.valueOf(limit));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("5");// 起始時間
			fixData.setValue(String.valueOf(startTime));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("6");// 截止時間
			fixData.setValue(String.valueOf(endTime));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("7");// 回補筆數
			fixData.setValue(String.valueOf(rebuildCount));
			queryList.add(fixData);
			
			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("R52", ctrlHeader, queryAry);
			ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(idAndPort);
			if (socket == null) {
				nErrCode = JABXErrCode.UNCONNECTING;
				siObj.setConnectStatus(false);
			}else {
				socket.outputDataByParam(sparam);
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildSmartMaster()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildSmartMaster()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildSmartMaster()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_SMARTMASTER, requestID, idAndPort, nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildSmartRank(int, List, int)
	 */
	@Override
	public void rebuildSmartRank(int requestID, List<String> listOfGroupID, int iCount) {
		// TODO Auto-generated method stub
		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			Map<String, JABXQuoteDataInfo> fixDataMap = new HashMap<String, JABXQuoteDataInfo>();
			// 回補即時排名訊息
			if (listOfGroupID != null && listOfGroupID.size() != 0) {
				JABXFixData fixData;
				String groupID, exchangeID, idAndPort;
				JABXQuoteDataInfo fdInfo;
				List<JABXFixData> fdList;
				for (int i = 0, size = listOfGroupID.size();i < size;i++) {
					groupID = listOfGroupID.get(i);
					exchangeID = groupID.substring(0, 2);

					idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
					fdInfo = fixDataMap.get(idAndPort);
					if (fdInfo == null) {
						fdInfo = new JABXQuoteDataInfo();
					}
					fixDataMap.put(idAndPort, fdInfo);

					fdList = new ArrayList<JABXFixData>();

					fixData = new JABXFixData();
					fixData.setTag("2");// 排名市場別
					fixData.setValue(exchangeID);
					fdList.add(fixData);

					fixData = new JABXFixData();
					fixData.setTag("3");// 群組代碼
					fixData.setValue(groupID.substring(2));
					fdList.add(fixData);

					fdInfo.addCount(1);
					fdInfo.addFixDataList(fdList);
				}
				
				fixData = null;
				groupID = null;
				exchangeID = null;
				idAndPort = null;
				fdInfo = null;
				fdList = null;
			}

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
						mdiObj = fixDataMap.get(idAndPort);// 取得依idAndPort分類之回補數據
						if (mdiObj != null) {// 若有回補數據
							siObj = soObj.atIpAndPort(idAndPort);
							if (!siObj.getConnectStatus()) {
								addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_SMARTRANK, requestID, idAndPort, JABXErrCode.UNCONNECTING);
								continue;
							}
							//jabxKernel.putInfoMapItem(idAndPort + "-" + requestID, JABXConst.ABXFUN_REBUILD_SMARTSHORT);// 儲存功能代碼
							JABXCtrlHeader ctrlHeader = getCtrlHeader(JABXConst.ABUS_MAINTYPE_CONTROL, JABXConst.ABUS_CONTROL_REBUILD, "R51", requestID, siObj, jabxKernel);
							JABXFixData fixData;
							// 記錄上傳之查詢參數之List
							List<JABXFixData> queryList = new ArrayList<JABXFixData>();
							fixData = new JABXFixData();
							fixData.setTag("1");// 群組代碼組數
							fixData.setValue(String.valueOf(mdiObj.getDataCount()));
							queryList.add(fixData);

							queryList.addAll(mdiObj.getFixDataList());

							fixData = new JABXFixData();
							fixData.setTag("4");// 排名筆數
							fixData.setValue(String.valueOf(iCount));;
							queryList.add(fixData);
								
							byte[] queryAry = getBytesFromListFixData(queryList);
								
							// 輸出串流
							JABXSocketParam sparam = new JABXSocketParam("R51", ctrlHeader, queryAry);
							socket = jabxKernel.getRealtimeServerMap().get(idAndPort);
							if (socket == null) {
								addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_SMARTRANK, requestID, idAndPort, JABXErrCode.ABUS_UNKONWNHOST_ERROR);
								continue;								
							}else {
								socket.outputDataByParam(sparam);
							}
								
							ctrlHeader = null;
							fixData = null;
							queryList = null;
							queryAry = null;
							sparam = null;
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
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildSmartRank()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildSmartRank()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildSmartRank()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_SMARTRANK, requestID, "Execution Error!", nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildSmartShort(int, List, int, int, int)
	 */
	@Override
	public void rebuildSmartShort(int requestID, List<String> listOfGroupID, int iStart, int iEnd, int iCount) {
		// TODO Auto-generated method stub
		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			Map<String, JABXQuoteDataInfo> fixDataMap = new HashMap<String, JABXQuoteDataInfo>();
			// 回補短線精靈
			if (listOfGroupID != null && listOfGroupID.size() != 0) {
				JABXFixData fixData;
				String groupID, exchangeID, idAndPort;
				JABXQuoteDataInfo fdInfo;
				List<JABXFixData> fdList;
				for (int i = 0, size = listOfGroupID.size();i < size;i++) {
					groupID = listOfGroupID.get(i);
					exchangeID = groupID.substring(0, 2);

					idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
					fdInfo = fixDataMap.get(idAndPort);
					if (fdInfo == null) {
						fdInfo = new JABXQuoteDataInfo();
					}
					fixDataMap.put(idAndPort, fdInfo);

					fdList = new ArrayList<JABXFixData>();

					fixData = new JABXFixData();
					fixData.setTag("5");// 短線精靈市場別
					fixData.setValue(exchangeID);
					fdList.add(fixData);

					fixData = new JABXFixData();
					fixData.setTag("6");// 群組代碼
					fixData.setValue(groupID.substring(2));
					fdList.add(fixData);

					fdInfo.addCount(1);
					fdInfo.addFixDataList(fdList);
				}

				fixData = null;
				groupID = null;
				exchangeID = null;
				idAndPort = null;
				fdInfo = null;
				fdList = null;
			}

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
						mdiObj = fixDataMap.get(idAndPort);// 取得依idAndPort分類之回補數據
						if (mdiObj != null) {// 若有回補數據
							siObj = soObj.atIpAndPort(idAndPort);
							if (!siObj.getConnectStatus()) {
								addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_SMARTSHORT, requestID, idAndPort, JABXErrCode.UNCONNECTING);
								continue;
							}
							//jabxKernel.putInfoMapItem(idAndPort + "-" + requestID, JABXConst.ABXFUN_REBUILD_SMARTSHORT);// 儲存功能代碼
							JABXCtrlHeader ctrlHeader = getCtrlHeader(JABXConst.ABUS_MAINTYPE_CONTROL, JABXConst.ABUS_CONTROL_REBUILD, "R50", requestID, siObj, jabxKernel);

							JABXFixData fixData;
							// 記錄上傳之查詢參數之List
							List<JABXFixData> queryList = new ArrayList<JABXFixData>();

							fixData = new JABXFixData();
							fixData.setTag("1");// 回補起始時間
							fixData.setValue(String.valueOf(iStart));
							queryList.add(fixData);

							fixData = new JABXFixData();
							fixData.setTag("2");// 回補截止時間
							fixData.setValue(String.valueOf(iEnd));
							queryList.add(fixData);

							fixData = new JABXFixData();
							fixData.setTag("3");// 回補筆數
							fixData.setValue(String.valueOf(iCount));
							queryList.add(fixData);

							fixData = new JABXFixData();
							fixData.setTag("4");// 群組代碼組數
							fixData.setValue(String.valueOf(mdiObj.getDataCount()));
							queryList.add(fixData);

							queryList.addAll(mdiObj.getFixDataList());
							
							byte[] queryAry = getBytesFromListFixData(queryList);
							
							// 輸出串流
							JABXSocketParam sparam = new JABXSocketParam("R50", ctrlHeader, queryAry);

							socket = jabxKernel.getRealtimeServerMap().get(idAndPort);
							if (socket == null) {
								addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_SMARTSHORT, requestID, idAndPort, JABXErrCode.ABUS_UNKONWNHOST_ERROR);
								continue;								
							}else {
								socket.outputDataByParam(sparam);
							}
							
							ctrlHeader = null;
							fixData = null;
							queryList = null;
							queryAry = null;
							sparam = null;
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
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildSmartShort()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildSmartShort()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildSmartShort()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_SMARTSHORT, requestID, "Execution Error!", nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildStatistic(int, java.lang.String, int, int, int)
	 */
	@Override
	public void rebuildStatistic(int requestID, String stockID, int startIdx, int endIdx, int nCount) {
		// TODO Auto-generated method stub
		// 10000-Logic-0-Begin: 依交易所代碼取得主機屬性為0的Server之主機代碼及埠號(hostID:hostPort)
		String exchangeID = stockID.substring(0, 2);
		String idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXServerItem siObj = soObj.atIpAndPort(idAndPort);
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MINUTETRADE, requestID, idAndPort, JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			return;
		}
		// 判斷 主機是否已連線
		if (!siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MINUTETRADE, requestID, idAndPort, JABXErrCode.UNCONNECTING);
			return;
		}
		// 10000-Logic-0-End.

		jabxKernel.putInfoMapItem(String.valueOf(requestID), stockID);// 儲存查詢參數

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補多筆總委買賣量筆及成交筆訊息
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "R10", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 證券全代碼
			fixData.setValue(stockID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 起始索引值
			fixData.setValue(String.valueOf(startIdx));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 截止索引值
			fixData.setValue(String.valueOf(endIdx));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 筆數
			fixData.setValue(String.valueOf(nCount));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("R10", ctrlHeader, queryAry);
			ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(idAndPort);
			if (socket == null) {
				nErrCode = JABXErrCode.UNCONNECTING;
				siObj.setConnectStatus(false);
			}else {
				socket.outputDataByParam(sparam);
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildStatistic()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildStatistic()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildStatistic()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_STATISTIC, requestID, idAndPort, nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildTotRefInfo(int, java.lang.String, int, int, int)
	 */
	@Override
	public void rebuildTotRefInfo(int requestID, String stockID, int startIdx, int endIdx, int nCount) {
		// TODO Auto-generated method stub
		// 10000-Logic-0-Begin: 依交易所代碼取得主機屬性為0的Server之主機代碼及埠號(hostID:hostPort)
		String exchangeID = stockID.substring(0, 2);
		String idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXServerItem siObj = soObj.atIpAndPort(idAndPort);
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MINUTETRADE, requestID, idAndPort, JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			return;
		}
		// 判斷 主機是否已連線
		if (!siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MINUTETRADE, requestID, idAndPort, JABXErrCode.UNCONNECTING);
			return;
		}
		// 10000-Logic-0-End.

		jabxKernel.putInfoMapItem(String.valueOf(requestID), stockID);// 儲存查詢參數

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補多筆總委買賣量筆及成交筆訊息
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "R07", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式
		
			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 證券全代碼
			fixData.setValue(stockID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 起始索引值
			fixData.setValue(String.valueOf(startIdx));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 截止索引值
			fixData.setValue(String.valueOf(endIdx));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 筆數
			fixData.setValue(String.valueOf(nCount));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("R07", ctrlHeader, queryAry);
			ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(idAndPort);
			if (socket == null) {
				nErrCode = JABXErrCode.UNCONNECTING;
				siObj.setConnectStatus(false);
			}else {
				socket.outputDataByParam(sparam);
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildTotRefInfo()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildTotRefInfo()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildTotRefInfo()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_TOTREFINFO, requestID, idAndPort, nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildTrade(int, java.lang.String, int, int, int)
	 */
	@Override
	public void rebuildTrade(int requestID, String stockID, int startIdx, int endIdx, int nCount) {
		// TODO Auto-generated method stub
		// 10000-Logic-0-Begin: 依交易所代碼取得主機屬性為0的Server之主機代碼及埠號(hostID:hostPort)
		String exchangeID = stockID.substring(0, 2);
		String idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXServerItem siObj = soObj.atIpAndPort(idAndPort);
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_TRADE, requestID, idAndPort, JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			return;
		}
		// 判斷 主機是否已連線
		if (!siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_TRADE, requestID, idAndPort, JABXErrCode.UNCONNECTING);
			return;
		}
		// 10000-Logic-0-End.

		jabxKernel.putInfoMapItem(String.valueOf(requestID), stockID);// 儲存查詢參數

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補成交資訊明細
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "R06", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式
			
			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 證券全代碼
			fixData.setValue(stockID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 起始索引值
			fixData.setValue(String.valueOf(startIdx));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 截止索引值
			fixData.setValue(String.valueOf(endIdx));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 筆數
			fixData.setValue(String.valueOf(nCount));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("R06", ctrlHeader, queryAry);
			ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(idAndPort);
			if (socket == null) {
				nErrCode = JABXErrCode.UNCONNECTING;
				siObj.setConnectStatus(false);
			}else {
				socket.outputDataByParam(sparam);
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildTrade()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildTrade()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildTrade()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_TRADE, requestID, idAndPort, nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#rebuildVirtualTrade(int, java.lang.String, int, int, int)
	 */
	@Override
	public void rebuildVirtualTrade(int requestID, String stockID, int startIdx, int endIdx, int nCount) {
		// TODO Auto-generated method stub
		// 10000-Logic-0-Begin: 依交易所代碼取得主機屬性為0的Server之主機代碼及埠號(hostID:hostPort)
		String exchangeID = stockID.substring(0, 2);
		String idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXServerItem siObj = soObj.atIpAndPort(idAndPort);
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MINUTETRADE, requestID, idAndPort, JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			return;
		}
		// 判斷 主機是否已連線
		if (!siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_MINUTETRADE, requestID, idAndPort, JABXErrCode.UNCONNECTING);
			return;
		}
		// 10000-Logic-0-End.

		jabxKernel.putInfoMapItem(String.valueOf(requestID), stockID);// 儲存查詢參數

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 回補盤前虛擬撮合-上海,深圳資料
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_REBUILD);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "R16", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式
			
			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 證券全代碼
			fixData.setValue(stockID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 起始索引值
			fixData.setValue(String.valueOf(startIdx));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 截止索引值
			fixData.setValue(String.valueOf(endIdx));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 筆數
			fixData.setValue(String.valueOf(nCount));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			// 輸出串流
			JABXSocketParam sparam = new JABXSocketParam("R16", ctrlHeader, queryAry);
			ZJABXSocket socket = jabxKernel.getRealtimeServerMap().get(idAndPort);
			if (socket == null) {
				nErrCode = JABXErrCode.UNCONNECTING;
				siObj.setConnectStatus(false);
			}else {
				socket.outputDataByParam(sparam);
			}
			sparam = null;

			ctrlHeader = null;
			queryAry = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildVirtualTrade()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildVirtualTrade()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.rebuildVirtualTrade()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REBUILD_VIRTUALTRADE, requestID, idAndPort, nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#reQueryTechnicalIndexData(int, String, int, int, int, int)
	 */
	@Override
	public void reQueryTechnicalIndexData(int requestID, String sStockID, int lineID, int iStart, int iEnd, int iCount) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REQUERY_TECHNICALINDEXDATA, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REQUERY_TECHNICALINDEXDATA, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), sStockID);// 儲存查詢之證券全代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 技術指標數據再查詢
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P53", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定證券全代碼
			fixData.setValue(sStockID);
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定指標記錄序號
			fixData.setValue(String.valueOf(lineID));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 設定查詢起始日期
			fixData.setValue(String.valueOf(iStart));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("4");// 設定查詢結止日期
			fixData.setValue(String.valueOf(iEnd));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("5");// 設定查詢筆數
			fixData.setValue(String.valueOf(iCount));
			queryList.add(fixData);

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P53", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.reQueryTechnicalIndexData()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.reQueryTechnicalIndexData()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.reQueryTechnicalIndexData()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.reQueryTechnicalIndexData()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_REQUERY_TECHNICALINDEXDATA, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#serverSideSubscribe(int, List)
	 */
	@Override
	public void serverSideSubscribe(int requestID, List<JABXAServer> list) {
		// TODO Auto-generated method stub
		if (list != null && list.size() != 0) {
			ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
			String idAndPort;
			ZJABXSocket socket;
			ZJABXServerItem siObj;
			JABXAServer aserver;
			for (int i = 0, size = list.size();i < size;i++) {
				aserver = list.get(i);
				idAndPort = aserver.getHostAndPort();
				siObj = soObj.atIpAndPort(idAndPort);
				if (siObj == null) {
					continue;
				}
				if (!siObj.getConnectStatus()) {
					addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_QUOTE, requestID, idAndPort, JABXErrCode.UNCONNECTING);
					continue;
				}
				jabxKernel.putInfoMapItem(idAndPort + "-" + requestID, JABXConst.ABXFUN_SUBSCRIBE_QUOTE);// 儲存功能代碼
				// 訂閱所有股票訊息(Used for server side)之設定
				JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
				// 設定訊息主代碼
				ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
				// 設定訊息次代碼
				ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_SUBSCRIBE);
				// 設定abyKey
				ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "R00", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
				ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

				// 記錄查詢參數之List
				List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
				// 500-02-Begin: 設定查詢參數
				JABXFixData fixData = new JABXFixData();
				fixData.setTag("1");// 連線ID
				fixData.setValue(String.valueOf(siObj.getSessionID()));
				queryList.add(fixData);

				fixData = new JABXFixData();
				fixData.setTag("2");// 設定筆數
				fixData.setValue("1");
				queryList.add(fixData);

				fixData = new JABXFixData();
				fixData.setTag("3");// 訊息主代碼
				fixData.setValue("255");
				queryList.add(fixData);

				fixData = new JABXFixData();
				fixData.setTag("4");// 訊息次代碼
				fixData.setValue("0");
				queryList.add(fixData);

				fixData = new JABXFixData();
				fixData.setTag("5");// 回補封包序號
				fixData.setValue(aserver.getPackNo());
				queryList.add(fixData);
				// 500-02-End.

				// 將查詢參數轉換成byte[]
				byte[] queryAry = getBytesFromListFixData(queryList);
				queryList = null;
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
			}
		}else {
			ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
			String idAndPort;
			ZJABXSocket socket;
			ZJABXServerItem siObj;
			List<String> serverList = soObj.getServerListByHostType(JABXConst.SERVER_TYPE_0);// 取得主機屬性為0的Server
			for (int i = 0, size = serverList.size();i < size;i++) {
				idAndPort = serverList.get(i);
				siObj = soObj.atIpAndPort(idAndPort);
				if (siObj == null) {
					continue;
				}
				if (!siObj.getConnectStatus()) {
					addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_QUOTE, requestID, idAndPort, JABXErrCode.UNCONNECTING);
					continue;
				}
				// 訂閱所有股票訊息(Used for server side)之設定
				JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
				// 設定訊息主代碼
				ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
				// 設定訊息次代碼
				ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_SUBSCRIBE);
				// 設定abyKey
				ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "R00", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
				ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

				// 記錄查詢參數之List
				List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
				// 500-02-Begin: 設定查詢參數
				JABXFixData fixData = new JABXFixData();
				fixData.setTag("1");// 連線ID
				fixData.setValue(String.valueOf(siObj.getSessionID()));
				queryList.add(fixData);

				fixData = new JABXFixData();
				fixData.setTag("2");// 設定筆數
				fixData.setValue("0");
				queryList.add(fixData);
				// 500-02-End.

				// 將查詢參數轉換成byte[]
				byte[] queryAry = getBytesFromListFixData(queryList);
				queryList = null;
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
			}
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#setUserTechnicalIndexCommonSetup(int, int, byte)
	 */
	@Override
	public void setUserTechnicalIndexCommonSetup(int requestID, int iTechnicalIndexID, byte usedFlag) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SET_USERTECHNICALINDEXCOMMONSETUP, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SET_USERTECHNICALINDEXCOMMONSETUP, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_SET_USERTECHNICALINDEXCOMMONSETUP);// 儲存所使用之功能代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 技術指標個人化設定
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P59", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定指標公式代碼
			fixData.setValue(String.valueOf(iTechnicalIndexID));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("5");// 設定常用公式旗標
			fixData.setValue(String.valueOf(usedFlag));
			queryList.add(fixData);
			// 500-02-End.

			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P59", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.setUserTechnicalIndexCommonSetup()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.setUserTechnicalIndexCommonSetup()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.setUserTechnicalIndexCommonSetup()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.setUserTechnicalIndexCommonSetup()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SET_USERTECHNICALINDEXCOMMONSETUP, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXQuoteRequest#setUserTechnicalIndexSetup(int, int, int, String)
	 */
	@Override
	public void setUserTechnicalIndexSetup(int requestID, int iTechnicalIndexID, int period, String sParameter) {
		// TODO Auto-generated method stub
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SET_USERTECHNICALINDEXSETUP, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SET_USERTECHNICALINDEXSETUP, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_SET_USERTECHNICALINDEXSETUP);// 儲存所使用之功能代碼

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// 技術指標個人化設定
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "P59", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			fixData.setTag("1");// 設定指標公式代碼
			fixData.setValue(String.valueOf(iTechnicalIndexID));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("2");// 設定資料週期
			fixData.setValue(String.valueOf(period));
			queryList.add(fixData);

			fixData = new JABXFixData();
			fixData.setTag("3");// 設定參數預設值設定
			fixData.setValue(sParameter);
			queryList.add(fixData);
			// 500-02-End.

			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("P59", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.setUserTechnicalIndexSetup()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.setUserTechnicalIndexSetup()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.setUserTechnicalIndexSetup()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.setUserTechnicalIndexSetup()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SET_USERTECHNICALINDEXSETUP, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * 訂閱即時廣告訊息
	 * @param requestID - int(API回覆碼)
	 * @param isSubscribe - boolean(是否訂閱;true.訂閱,false.取消訂閱)
	 */
	void subscribeAdvertisement(int requestID, boolean isSubscribe) {
		// TODO Auto-generated method stub
		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			/* 500-01-Begin: 檢查有那些Server需要上傳資料，以清除不在此次訂閱之數據，主要是針對有多台ABus Server時所要做的處理。
			 * 例如：上次訂閱T1及H1開頭的數據，此次只訂閱T1開頭的數據，因此要將H1開頭的數據清除。*/
			Map<String, String> mustSendMap = new HashMap<String, String>();
			if (isSubscribeAdvertisement) {
				mustSendMap.put(jabxKernel.getFirstLoginServerIPAndPort(), "");
			}
			// 500-01-End.

			// 500-02-Begin: 設定訂閱即時廣告訊息查詢參數
			this.isSubscribeAdvertisement = isSubscribe;
			// 500-02-End.

			if (isSubscribeAdvertisement) {
				Map<String, JABXQuoteDataInfo> mergeDataMap = new HashMap<String, JABXQuoteDataInfo>();
				List<String> listOfProductClass = jabxKernel.getListOfProductClass();// 取得產品功能列表
				if (listOfProductClass != null && listOfProductClass.size() != 0) {
					String idAndPort = jabxKernel.getFirstLoginServerIPAndPort();
					List<JABXFixData> fdList;
					JABXQuoteDataInfo mdInfo = new JABXQuoteDataInfo();
					mergeDataMap.put(idAndPort, mdInfo);
					JABXFixData fixData;
					for (int i = 0, size = listOfProductClass.size();i < size;i++) {
						fdList = new ArrayList<JABXFixData>();
						
						fixData = new JABXFixData();
						fixData.setTag("3");// 訊息主代碼
						fixData.setValue(String.valueOf(JABXConst.ABUS_MAINTYPE_FREEFORMAT));
						fdList.add(fixData);

						fixData = new JABXFixData();
						fixData.setTag("4");// 訊息次代碼
						fixData.setValue(Integer.toHexString(JABXConst.ABUS_FREEMSG_PRODUCT));
						fdList.add(fixData);

						fixData = new JABXFixData();
						fixData.setTag("5");// 產品功能代碼
						fixData.setValue(listOfProductClass.get(i));
						fdList.add(fixData);

						mdInfo.addCount(1);
						mdInfo.addFixDataList(fdList);
					}

					idAndPort = null;
					fdList = null;
					mdInfo = null;
					fixData = null;
				}

				jabxKernel.getReservedQuoteData().putSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_ADVERTISEMENT), mergeDataMap);
				mergeDataMap = null;
			}else {
				jabxKernel.getReservedQuoteData().removeSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_ADVERTISEMENT));
			}

			// 上傳Socket數據
			uploadSocketData(requestID, JABXConst.ABXFUN_SUBSCRIBE_ADVERTISEMENT, mustSendMap);
			mustSendMap = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeAdvertisement()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeAdvertisement()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeAdvertisement()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_ADVERTISEMENT, requestID, "Execution Error!", nErrCode);
		}
	}

	/**
	 * 訂閱即時交易所公告訊息
	 * @param requestID - int(API回覆碼)
	 * @param jArray - JSONArray(訂閱即時交易所公告資訊)<br>
	 * &nbsp;&nbsp;JSONArray中每單一物件為String(交易所代碼)物件。<br>
	 */
	void subscribeExchangeBulletin(int requestID, JSONArray jArray) {

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			/* 500-01-Begin: 檢查有那些Server需要上傳資料，以清除不在此次訂閱之數據，主要是針對有多台ABus Server時所要做的處理。
			 * 例如：上次訂閱T1及H1開頭的數據，此次只訂閱T1開頭的數據，因此要將H1開頭的數據清除。*/
			Map<String, String> mustSendMap = new HashMap<String, String>();
			if (exchangeIDParamArray != null && exchangeIDParamArray.length() != 0) {
				String exchangeID, idAndPort;
				for (int i = 0, length = exchangeIDParamArray.length();i < length;i++) {
					exchangeID = exchangeIDParamArray.getString(i);
					idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
					mustSendMap.put(idAndPort, "");
				}
				exchangeID = null;
				idAndPort = null;
			}
			// 500-01-End.

			// 500-02-Begin: 設定訂閱即時交易所公告訊息查詢參數
			if (jArray != null && jArray.length() != 0) {
				// 記錄查詢參數之List
				JSONArray bakExchangeList = new JSONArray();
				String exchangeID;
				for (int i = 0, length = jArray.length();i < length;i++) {
					exchangeID = jArray.getString(i);

					bakExchangeList.put(exchangeID);
				}
				exchangeIDParamArray = bakExchangeList;
				bakExchangeList = null;
			}else {
				if (exchangeIDParamArray != null) {
					exchangeIDParamArray = null;
				}
			}
			// 500-02-End.

			if (exchangeIDParamArray != null && exchangeIDParamArray.length() != 0) {
				Map<String, JABXQuoteDataInfo> mergeDataMap = new HashMap<String, JABXQuoteDataInfo>();
				JABXFixData fixData;
				String exchangeID, idAndPort;
				JABXQuoteDataInfo mdInfo;
				List<JABXFixData> fdList;
				for (int i = 0, length = exchangeIDParamArray.length();i < length;i++) {
					exchangeID = exchangeIDParamArray.getString(i);
				
					idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
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
					fixData.setValue(Integer.toHexString(JABXConst.ABUS_FREEMSG_EXCHANGE));
					fdList.add(fixData);
	
					fixData = new JABXFixData();
					fixData.setTag("5");// 交易所代碼
					fixData.setValue(exchangeID);
					fdList.add(fixData);

					mdInfo.addCount(1);
					mdInfo.addFixDataList(fdList);
				}

				fixData = null;
				exchangeID = null;
				idAndPort = null;
				mdInfo = null;
				fdList = null;

				jabxKernel.getReservedQuoteData().putSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_EXCHANGEBULLETIN), mergeDataMap);
				mergeDataMap = null;
			}else {
				jabxKernel.getReservedQuoteData().removeSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_EXCHANGEBULLETIN));
			}
			
			// 上傳Socket數據
			uploadSocketData(requestID, JABXConst.ABXFUN_SUBSCRIBE_EXCHANGEBULLETIN, mustSendMap);
			mustSendMap = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeExchangeBulletin()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeExchangeBulletin()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeExchangeBulletin()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_EXCHANGEBULLETIN, requestID, "Execution Error!", nErrCode);
		}
	}

	/**
	 * 訂閱即時行情報導訊息
	 * @param requestID - int(API回覆碼)
	 * @param jArray - JSONArray(訂閱即時行情報導資訊)<br>
	 * &nbsp;&nbsp;JSONArray中每單一物件為String(行情報導分類代碼)物件。<br>
	 */
	void subscribeMarketReport(int requestID, JSONArray jArray) {
		// TODO Auto-generated method stub
		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			/* 500-01-Begin: 檢查有那些Server需要上傳資料，以清除不在此次訂閱之數據，主要是針對有多台ABus Server時所要做的處理。
			 * 例如：上次訂閱T1及H1開頭的數據，此次只訂閱T1開頭的數據，因此要將H1開頭的數據清除。*/
			Map<String, String> mustSendMap = new HashMap<String, String>();
			if (classIDParamArray != null && classIDParamArray.length() != 0) {
				mustSendMap.put(jabxKernel.getFirstLoginServerIPAndPort(), "");
			}
			// 500-01-End.
			
			// 500-02-Begin: 設定訂閱即時行情報導訊息查詢參數
			if (jArray != null && jArray.length() != 0) {
				// 記錄查詢參數之List
				JSONArray bakClassIDParamList = new JSONArray();
				String sourceID;
				for (int i = 0, length = jArray.length();i < length;i++) {
					sourceID = jArray.getString(i);

					bakClassIDParamList.put(sourceID);
				}
				classIDParamArray = bakClassIDParamList;
				bakClassIDParamList = null;
			}else {
				if (classIDParamArray != null) {
					classIDParamArray = null;
				}
			}
			// 500-02-End.

			if (classIDParamArray != null && classIDParamArray.length() != 0) {
				Map<String, JABXQuoteDataInfo> mergeDataMap = new HashMap<String, JABXQuoteDataInfo>();
				String idAndPort = jabxKernel.getFirstLoginServerIPAndPort();
				List<JABXFixData> fdList;
				JABXQuoteDataInfo mdInfo = new JABXQuoteDataInfo();
				mergeDataMap.put(idAndPort, mdInfo);
			
				JABXFixData fixData;
				String classID;
				for (int i = 0, length = classIDParamArray.length();i < length;i++) {
					classID = classIDParamArray.getString(i);
			
					fdList = new ArrayList<JABXFixData>();
					
					fixData = new JABXFixData();
					fixData.setTag("3");// 訊息主代碼
					fixData.setValue(String.valueOf(JABXConst.ABUS_MAINTYPE_FREEFORMAT));
					fdList.add(fixData);
	
					fixData = new JABXFixData();
					fixData.setTag("4");// 訊息次代碼
					fixData.setValue(Integer.toHexString(JABXConst.ABUS_FREEMSG_PRODUCT));
					fdList.add(fixData);
	
					fixData = new JABXFixData();
					fixData.setTag("5");// 行情報導分類代碼
					fixData.setValue(classID);
					fdList.add(fixData);
					
					mdInfo.addCount(1);
					mdInfo.addFixDataList(fdList);
				}

				idAndPort = null;
				fdList = null;
				mdInfo = null;
				fixData = null;
				classID = null;

				jabxKernel.getReservedQuoteData().putSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_MARKETREPORT), mergeDataMap);
				mergeDataMap = null;
			}else {
				jabxKernel.getReservedQuoteData().removeSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_MARKETREPORT));
			}
			
			// 上傳Socket數據
			uploadSocketData(requestID, JABXConst.ABXFUN_SUBSCRIBE_MARKETREPORT, mustSendMap);
			mustSendMap = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeMarketReport()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeMarketReport()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeMarketReport()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_MARKETREPORT, requestID, "Execution Error!", nErrCode);
		}		
	}

	/**
	 * 訂閱即時新聞訊息
	 * @param requestID - int(API回覆碼)
	 * @param jArray - JSONArray(訂閱即時新聞資訊)<br>
	 * &nbsp;&nbsp;JSONArray中每單一物件為String(新聞來源代碼)物件。<br>
	 */
	void subscribeNews(int requestID, JSONArray jArray) {

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			/* 500-01-Begin: 檢查有那些Server需要上傳資料，以清除不在此次訂閱之數據，主要是針對有多台ABus Server時所要做的處理。
			 * 例如：上次訂閱T1及H1開頭的數據，此次只訂閱T1開頭的數據，因此要將H1開頭的數據清除。*/
			Map<String, String> mustSendMap = new HashMap<String, String>();
			if (sourceIDParamArray != null && sourceIDParamArray.length() != 0) {
				mustSendMap.put(jabxKernel.getFirstLoginServerIPAndPort(), "");
			}
			// 500-01-End.

			// 500-02-Begin: 設定訂閱即時新聞訊息查詢參數
			if (jArray != null && jArray.length() != 0) {
				// 記錄查詢參數之List
				JSONArray bakSourceIDArray = new JSONArray();
				String sourceID;
				for (int i = 0, length = jArray.length();i < length;i++) {
					sourceID = jArray.getString(i);

					bakSourceIDArray.put(sourceID);
				}
				sourceIDParamArray = bakSourceIDArray;
				bakSourceIDArray = null;
			}else {
				if (sourceIDParamArray != null) {
					sourceIDParamArray = null;
				}
			}
			// 500-02-End.

			if (sourceIDParamArray != null && sourceIDParamArray.length() != 0) {
				Map<String, JABXQuoteDataInfo> mergeDataMap = new HashMap<String, JABXQuoteDataInfo>();
				String idAndPort = jabxKernel.getFirstLoginServerIPAndPort();
				List<JABXFixData> fdList;
				JABXQuoteDataInfo mdInfo = new JABXQuoteDataInfo();
				mergeDataMap.put(idAndPort, mdInfo);

				JABXFixData fixData;
				String sourceID;
				for (int i = 0, length = sourceIDParamArray.length();i < length;i++) {
					sourceID = sourceIDParamArray.getString(i);

					fdList = new ArrayList<JABXFixData>();

					fixData = new JABXFixData();
					fixData.setTag("3");// 訊息主代碼
					fixData.setValue(String.valueOf(JABXConst.ABUS_MAINTYPE_FREEFORMAT));
					fdList.add(fixData);
	
					fixData = new JABXFixData();
					fixData.setTag("4");// 訊息次代碼
					fixData.setValue(Integer.toHexString(JABXConst.ABUS_FREEMSG_NEWS));
					fdList.add(fixData);
	
					fixData = new JABXFixData();
					fixData.setTag("5");// 新聞來源代碼
					fixData.setValue(sourceID);
					fdList.add(fixData);

					mdInfo.addCount(1);
					mdInfo.addFixDataList(fdList);
				}

				idAndPort = null;
				fdList = null;
				fixData = null;
				sourceID = null;

				jabxKernel.getReservedQuoteData().putSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_NEWS), mergeDataMap);
				mergeDataMap = null;
			}else {
				jabxKernel.getReservedQuoteData().removeSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_NEWS));
			}

			// 上傳Socket數據
			uploadSocketData(requestID, JABXConst.ABXFUN_SUBSCRIBE_NEWS, mustSendMap);
			mustSendMap = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeNews()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeNews()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeNews()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_EXCHANGEBULLETIN, requestID, "Execution Error!", nErrCode);
		}
	}

	/**
	 * 訂閱即時產品公告訊息
	 * @param requestID - int(API回覆碼)
	 * @param isSubscribe - boolean(是否訂閱;true.訂閱,false.取消訂閱)
	 */
	void subscribeProductBulletin(int requestID, boolean isSubscribe) {
		// TODO Auto-generated method stub
		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			/* 500-01-Begin: 檢查有那些Server需要上傳資料，以清除不在此次訂閱之數據，主要是針對有多台ABus Server時所要做的處理。
			 * 例如：上次訂閱T1及H1開頭的數據，此次只訂閱T1開頭的數據，因此要將H1開頭的數據清除。*/
			Map<String, String> mustSendMap = new HashMap<String, String>();
			if (isSubscribeProductBulletin) {
				mustSendMap.put(jabxKernel.getFirstLoginServerIPAndPort(), "");
			}
			// 500-01-End.
			
			// 500-02-Begin: 設定訂閱即時產品公告訊息查詢參數
			this.isSubscribeProductBulletin = isSubscribe;
			// 500-02-End.

			if (isSubscribeProductBulletin) {
				Map<String, JABXQuoteDataInfo> mergeDataMap = new HashMap<String, JABXQuoteDataInfo>();
				String idAndPort = jabxKernel.getFirstLoginServerIPAndPort();
				List<JABXFixData> fdList = new ArrayList<JABXFixData>();
				JABXQuoteDataInfo mdInfo = new JABXQuoteDataInfo();
				mergeDataMap.put(idAndPort, mdInfo);
			
				JABXFixData fixData;

				fixData = new JABXFixData();
				fixData.setTag("3");// 訊息主代碼
				fixData.setValue(String.valueOf(JABXConst.ABUS_MAINTYPE_FREEFORMAT));
				fdList.add(fixData);

				fixData = new JABXFixData();
				fixData.setTag("4");// 訊息次代碼
				fixData.setValue(Integer.toHexString(JABXConst.ABUS_FREEMSG_PRODUCT));
				fdList.add(fixData);

				fixData = new JABXFixData();
				fixData.setTag("5");// P+產品代碼
				fixData.setValue(String.format("P%d", jabxKernel.getLoginInfo().get(JSConst.LN_PRODUCTID)));
				fdList.add(fixData);

				mdInfo.addCount(1);
				mdInfo.addFixDataList(fdList);

				idAndPort = null;
				fdList = null;
				mdInfo = null;
				fixData = null;
	
				jabxKernel.getReservedQuoteData().putSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_PRODUCTBULLETIN), mergeDataMap);
				mergeDataMap = null;
			}else {
				jabxKernel.getReservedQuoteData().removeSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_PRODUCTBULLETIN));
			}

			// 上傳Socket數據
			uploadSocketData(requestID, JABXConst.ABXFUN_SUBSCRIBE_PRODUCTBULLETIN, mustSendMap);
			mustSendMap = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeProductBulletin()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeProductBulletin()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeProductBulletin()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_PRODUCTBULLETIN, requestID, "Execution Error!", nErrCode);
		}
	}

	/**
	 * 訂閱即時報價
	 * @param requestID - int(API回覆碼)
	 * @param sessionID - int(不同視窗訂閱時所使用之SessionID，可經由JABXToolkit類別之getSessionID()取得)
	 * @param jArray - JSONArray(訂閱資訊)<br>
	 * &nbsp;&nbsp;JSONArray中每單一物件為JSONObject物件，格式如下所示：<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;[JSConst.CU_STKID:股票代碼;JSConst.CU_QUOTE_FIELD:訂閱欄位之int值]<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;訂閱欄位之int值，說明如下:<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(a).JABXConst.ABX_WATCH_STKBASEINFO:0x00000001(股票基本資料訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(b).JABXConst.ABX_WATCH_STKREFINFO:0x00000002(股票相關資料訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(c).JABXConst.ABX_WATCH_ORDER_1:0x00000004(第1檔委買賣訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(d).JABXConst.ABX_WATCH_ORDER_2_5:0x00000008(第2至5檔委買賣訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(e).JABXConst.ABX_WATCH_ORDER_6_10:0x00000010(第6至10檔委買賣訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(f).JABXConst.ABX_WATCH_TRADE:0x00000020(成交資訊訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(g).JABXConst.ABX_WATCH_TOTREFINFO:0x00000040(總委買賣量筆及成交筆訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(h).JABXConst.ABX_WATCH_MINUTE:0x00000080(其他一分鐘訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(i).JABXConst.ABX_WATCH_OTHERS:0x00000100(其他報價訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(j).JABXConst.ABX_WATCH_STATISTIC:0x00000200(交易所統計資料訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(k).JABXConst.ABX_WATCH_EXCHANGESTATUS:0x00000400(交易所交易狀態訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(l).JABXConst.ABX_WATCH_BROKERQUEUE:0x00000800(BrokerQueue-香港訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(m).JABXConst.ABX_WATCH_DETAILTRADE:0x00001000(逐筆成交上海,深圳訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(n).JABXConst.ABX_WATCH_DETAILORDER:0x00002000(逐筆委託-深圳訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(o).JABXConst.ABX_WATCH_OLDLOTTRADE:0x00004000(零股買賣成交-台灣訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(p).JABXConst.ABX_WATCH_VIRTUALTRADE:0x00008000(盤前虛擬撮合-上海,深圳訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(q).JABXConst.ABX_WATCH_1SECSNAPSHOT:0x00010000(一秒快照訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(r).JABXConst.ABX_WATCH_ORDERLIST:0x00020000(委買賣每檔明細-上海,深圳Level-2訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(s).JABXConst.ABX_WATCH_ALL_CLASS:0xFFFFFFFF(取得所有即時報價之class)<br>
	 */
	void subscribeQuote(int requestID, int sessionID, JSONArray jArray) {
		// TODO Auto-generated method stub
		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			/* 500-01-Begin: 檢查有那些Server需要上傳資料，以清除不在此次訂閱之數據，主要是針對有多台ABus Server時所要做的處理。
			 * 例如：上次訂閱T1及H1開頭的數據，此次只訂閱T1開頭的數據，因此要將H1開頭的數據清除。*/
			Map<String, String> mustSendMap = new HashMap<String, String>();
			if (quoteParamMap != null && quoteParamMap.size() != 0) {
				JSONArray quoteParamArray;
				Set<Integer> keySet = quoteParamMap.keySet();
				Iterator<Integer> it = keySet.iterator();
				Integer key;
				while (it.hasNext()) {
					key = it.next();
					if (key == sessionID) {
						continue;
					}
					quoteParamArray = quoteParamMap.get(key);
					JSONObject qcObj;
					String exchangeID, idAndPort, stkID = "";
					for (int i = 0, length = quoteParamArray.length();i < length;i++) {
						qcObj = quoteParamArray.getJSONObject(i);
						if (!qcObj.isNull(JSConst.CU_STKID)) {
							stkID = qcObj.getString(JSConst.CU_STKID);
							if (stkID.length() < 2) {
								continue;
							}
						}
						exchangeID = stkID.substring(0, 2);
						idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
						mustSendMap.put(idAndPort, "");
					}
					qcObj = null;
					exchangeID = null;
					idAndPort = null;
				}
			}
			// 500-01-End.

			// 500-02-Begin: 設定訂閱即時報價查詢參數
			if (jArray != null && jArray.length() != 0) {
				// 記錄查詢參數之List
				JSONArray bakQuoteArray = new JSONArray();
				JSONObject cond;
				JSONObject bakCond;
				for (int i = 0, length = jArray.length();i < length;i++) {
					cond = jArray.getJSONObject(i);
					
					bakCond = new JSONObject();

					bakCond.put(JSConst.CU_STKID, cond.getString(JSConst.CU_STKID));
					bakCond.put(JSConst.CU_QUOTE_FIELD, cond.getInt(JSConst.CU_QUOTE_FIELD));
					
					bakQuoteArray.put(bakCond);
				}
				quoteParamMap.put(sessionID, bakQuoteArray);
				bakQuoteArray = null;
			}else {
				quoteParamMap.remove(sessionID);
			}
			// 500-02-End.
			
			if (quoteParamMap != null && quoteParamMap.size() != 0) {
				Map<String, JSONObject> idQuoteMap = new HashMap<String, JSONObject>();
				JSONArray quoteParamList;
				JSONObject cond, quoteCond;
				Set<Integer> keySet = quoteParamMap.keySet();
				Iterator<Integer> keyIt = keySet.iterator();
				Integer key;
				while (keyIt.hasNext()) {
					key = keyIt.next();
					quoteParamList = (JSONArray)quoteParamMap.get(key);
					for (int i = 0, length = quoteParamList.length();i < length;i++) {
						cond = quoteParamList.getJSONObject(i);
						if (cond.isNull(JSConst.CU_STKID) || cond.isNull(JSConst.CU_QUOTE_FIELD)) {
							continue;
						}						
						quoteCond = idQuoteMap.get(cond.getString(JSConst.CU_STKID));
						if (quoteCond == null) {
							quoteCond = new JSONObject();
							quoteCond.put(JSConst.CU_STKID, cond.getString(JSConst.CU_STKID));
							quoteCond.put(JSConst.CU_QUOTE_FIELD, cond.getInt(JSConst.CU_QUOTE_FIELD));
						}else {
							quoteCond.put(JSConst.CU_QUOTE_FIELD, cond.getInt(JSConst.CU_QUOTE_FIELD) | quoteCond.getInt(JSConst.CU_QUOTE_FIELD));
						}
						idQuoteMap.put(quoteCond.getString(JSConst.CU_STKID), quoteCond);
					}
				}

				Map<String, JABXQuoteDataInfo> mergeDataMap = new HashMap<String, JABXQuoteDataInfo>();
				
				JABXFixData fixData;
				int quoteID;
				String stkID, exchangeID, idAndPort;
				JABXQuoteDataInfo mdInfo;
				List<JABXFixData> fdList;
				
				Set<String> idQuoteSet = idQuoteMap.keySet();
				Iterator<String> idQuoteIt = idQuoteSet.iterator();
				String idKey;
				while (idQuoteIt.hasNext()) {
					idKey = idQuoteIt.next();
					cond = idQuoteMap.get(idKey);
					stkID = cond.getString(JSConst.CU_STKID);
					if (stkID.length() < 2) {
						continue;
					}
					exchangeID = stkID.substring(0, 2);
						
					idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
					mdInfo = mergeDataMap.get(idAndPort);
					if (mdInfo == null) {
						mdInfo = new JABXQuoteDataInfo();
					}
					mergeDataMap.put(idAndPort, mdInfo);

					fdList = new ArrayList<JABXFixData>();

					fixData = new JABXFixData();
					fixData.setTag("3");// 訊息主代碼
					fixData.setValue(String.valueOf(JABXConst.ABUS_MAINTYPE_WATCHLIST));
					fdList.add(fixData);

					fixData = new JABXFixData();
					fixData.setTag("4");// 訊息次代碼
					quoteID = cond.getInt(JSConst.CU_QUOTE_FIELD) | JABXConst.ABUS_WATCH_STKBASEINFO; 
					quoteID |= JABXConst.ABUS_WATCH_STKREFINFO;
					quoteID |= JABXConst.ABUS_WATCH_EXCHANGESTATUS;
					fixData.setValue(Integer.toHexString(quoteID));
					fdList.add(fixData);

					fixData = new JABXFixData();
					fixData.setTag("5");// 證券鍵值代碼
					fixData.setValue(cond.getString(JSConst.CU_STKID));			
					fdList.add(fixData);

					mdInfo.addCount(1);
					mdInfo.addFixDataList(fdList);
				}

				fixData = null;
				cond = null;
				stkID = null;
				exchangeID = null;
				idAndPort = null;
				mdInfo = null;
				fdList = null;

				jabxKernel.getReservedQuoteData().putSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_QUOTE), mergeDataMap);
				mergeDataMap = null;
			}else {
				jabxKernel.getReservedQuoteData().removeSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_QUOTE));
			}
			
			// 上傳Socket數據
			uploadSocketData(requestID, JABXConst.ABXFUN_SUBSCRIBE_QUOTE, mustSendMap);
			mustSendMap = null;

			ZJABXQuoteOverview qoObj = (ZJABXQuoteOverview)quoteTool.getQuoteOverview();
			qoObj.compareData(quoteParamMap);
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeQuote()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeQuote()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeQuote()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_QUOTE, requestID, "Execution Error!", nErrCode);
		}
	}

	/**
	 * 訂閱即時指標
	 * @param requestID - int(API回覆碼)
	 * @param jArray - JSONArray(訂閱即時指標資訊)<br>
	 * &nbsp;&nbsp;JSONArray中每單一物件為String(指標回覆序號)物件。<br>
	 */
	void subscribeRealtimeTechnical(int requestID, JSONArray jArray) {
		// TODO Auto-generated method stub
		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			 /*500-01-Begin: 檢查有那些Server需要上傳資料，以清除不在此次訂閱之數據，主要是針對有多台ABus Server時所要做的處理。
			  * 例如：上次訂閱T1及H1開頭的數據，此次只訂閱T1開頭的數據，因此要將H1開頭的數據清除。*/
			Map<String, String> mustSendMap = new HashMap<String, String>();
			if (lineIDParamArray != null && lineIDParamArray.length() != 0) {
				mustSendMap.put(jabxKernel.getFirstLoginServerIPAndPort(), "");
			}
			// 500-01-End.
			
			// 500-02-Begin: 設定訂閱即時行情報導訊息查詢參數
			if (jArray != null && jArray.length() != 0) {
				// 記錄查詢參數之List
				JSONArray bakLineIDParamArray = new JSONArray();
				String sourceID;
				for (int i = 0, length = jArray.length();i < length;i++) {
					sourceID = jArray.getString(i);

					bakLineIDParamArray.put(sourceID);
				}
				lineIDParamArray = bakLineIDParamArray;
				bakLineIDParamArray = null;
			}else {
				if (lineIDParamArray != null) {
					lineIDParamArray = null;
				}
			}
			// 500-02-End.

			if (lineIDParamArray != null && lineIDParamArray.length() != 0) {
				Map<String, JABXQuoteDataInfo> mergeDataMap = new HashMap<String, JABXQuoteDataInfo>();
				JABXFixData fixData;
				JABXQuoteDataInfo mdInfo = new JABXQuoteDataInfo();
				List<JABXFixData> fdList;
				String idAndPort = jabxKernel.getFirstLoginServerIPAndPort();
				mergeDataMap.put(idAndPort, mdInfo);
						
				String lineID;
				for (int i = 0, length = lineIDParamArray.length();i < length;i++) {
					lineID = lineIDParamArray.getString(i);

					fdList = new ArrayList<JABXFixData>();

					fixData = new JABXFixData();
					fixData.setTag("3");// 訊息主代碼
					fixData.setValue(String.valueOf(JABXConst.ABUS_MAINTYPE_FREEFORMAT));
					fdList.add(fixData);
				
					fixData = new JABXFixData();
					fixData.setTag("4");// 訊息次代碼
					fixData.setValue(Integer.toHexString(JABXConst.ABUS_FREEMSG_LINE));
					fdList.add(fixData);
				
					fixData = new JABXFixData();
					fixData.setTag("5");// 指標記錄序號
					fixData.setValue(lineID);
					fdList.add(fixData);

					mdInfo.addCount(1);
					mdInfo.addFixDataList(fdList);
				}

				idAndPort = null;
				fdList = null;
				mdInfo = null;
				fixData = null;
				lineID = null;

				jabxKernel.getReservedQuoteData().putSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_REALTIMETECHNICAL), mergeDataMap);
				mergeDataMap = null;

				ZJABXGWHeartbitThread gwhThread = quoteTool.getGWHeartbitThread();
				List<String> lineIDList = new ArrayList<String>();
				for (int i = 0, length = lineIDParamArray.length();i < length;i++) {
					lineIDList.add(lineIDParamArray.getString(i));
				}
				if (gwhThread != null && gwhThread.isRunningFlag()) {
					gwhThread.addListOfLineID(lineIDList);
				}else {
					gwhThread = quoteTool.getNewGWHeartbitThread(lineIDList);
					gwhThread.setIsRunningFlag(true);
					gwhThread.start();
				}
			}else {
				jabxKernel.getReservedQuoteData().removeSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_REALTIMETECHNICAL));

				ZJABXGWHeartbitThread gwhThread = quoteTool.getGWHeartbitThread();
				if (gwhThread != null) {
					gwhThread.setIsRunningFlag(false);
				}
			}

			// 上傳Socket數據
			uploadSocketData(requestID, JABXConst.ABXFUN_SUBSCRIBE_REALTIMETECHNICAL, mustSendMap);
			mustSendMap = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeRealtimeTechnical()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeRealtimeTechnical()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeRealtimeTechnical()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_REALTIMETECHNICAL, requestID, "Execution Error!", nErrCode);
		}		
	}

	/**
	 * 送出指標連線心跳
	 * @param requestID
	 * @param listOfLineID
	 */
	void sendTechnicalKeepAlive(int requestID, List<String> listOfLineID) {
		ZJABXServerOverview soObj = (ZJABXServerOverview)jabxKernel.getServerOverview();
		ZJABXIPAndPortInfo ipiObj = jabxKernel.getIPAndPortInfo(JABXConst.SERVER_TYPE_3);
		ZJABXServerItem siObj = soObj.atIpAndPort(ipiObj.getIdAndPort());
		if (siObj == null) {// 若無法取得伺服器項目，則抛出Unknow Host錯誤
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SENDTECHNICAL_KEEPALIVE, requestID, ipiObj.getIdAndPort(), JABXErrCode.ABUS_UNKONWNHOST_ERROR);
			ipiObj = null;
			return;
		}
		// 若非GW Server，則判斷 主機是否已連線
		if (!ipiObj.isAssignedServer() && !siObj.getConnectStatus()) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SENDTECHNICAL_KEEPALIVE, requestID, ipiObj.getIdAndPort(), JABXErrCode.UNCONNECTING);
			ipiObj = null;
			return;
		}

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			// ＧＷ訂閱連線心跳
			JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();			
			// 設定訊息主代碼
			ctrlHeader.setByMsgMainType(JABXConst.ABUS_MAINTYPE_CONTROL);
			// 設定訊息次代碼
			ctrlHeader.setByMsgSubType(JABXConst.ABUS_CONTROL_NORMAL);
			// 設定abyKey
			ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), "R99", requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
			ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);// 設定資料壓縮方式

			// 記錄查詢參數之List
			List<JABXFixData> queryList = new ArrayList<JABXFixData>();			
			// 500-02-Begin: 設定查詢參數
			JABXFixData fixData = new JABXFixData();
			if (listOfLineID != null && listOfLineID.size() != 0) {
				fixData.setTag("1");// 設定筆數
				fixData.setValue(String.valueOf(listOfLineID.size()));
				queryList.add(fixData);

				for (int i = 0, size = listOfLineID.size();i < size;i++) {
					fixData = new JABXFixData();
					fixData.setTag("2");// 訊息主代碼
					fixData.setValue(Integer.toHexString(JABXConst.ABUS_MAINTYPE_FREEFORMAT));
					queryList.add(fixData);

					fixData = new JABXFixData();
					fixData.setTag("3");// 訊息次代碼
					fixData.setValue(Integer.toHexString(JABXConst.ABUS_FREEMSG_LINE));
					queryList.add(fixData);

					fixData = new JABXFixData();
					fixData.setTag("4");// 訂閱鍵值
					fixData.setValue(listOfLineID.get(i));
					queryList.add(fixData);
				}
			}

			fixData = null;
			// 500-02-End.
		
			// 將查詢參數轉換成byte[]
			byte[] queryAry = getBytesFromListFixData(queryList);
			queryList = null;

			JABXSocketParam sparam = new JABXSocketParam("R99", ctrlHeader, queryAry);
			if (ipiObj.isAssignedServer()) {// 是GW Server
				jabxLog.outputInfoAndLog("ZJABXQuoteRequest.sendTechnicalKeepAlive()", String.format("Connecting another session ->%s", ipiObj.getIdAndPort()));
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
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.sendTechnicalKeepAlive()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.sendTechnicalKeepAlive()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.sendTechnicalKeepAlive()", e.getMessage());
			e.printStackTrace();
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SENDTECHNICAL_KEEPALIVE, requestID, ipiObj.getIdAndPort(), nErrCode);
		}
	}

	/**
	 * 訂閱即時主力大單訊息
	 * @param requestID - int(API回覆碼)
	 * @param jArray - JSONArray(訂閱即時主力大單資訊)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;JSONArray中每單一物件為JSONObject物件，物件內容如下所示：<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JSConst.CU_EXCHANGEID: String(交易所代碼)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JSConst.MS_MINIMUM_AMOUNT: int(主力大單最小成交金額)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JSConst.MS_MINIMUM_VOLUME: int(主力大單最小成交量)<br>
	 */
	void subscribeSmartMaster(int requestID, JSONArray jArray) {
		// TODO Auto-generated method stub
		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_SUBSCRIBE_SMARTMASTER);

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			/* 500-01-Begin: 檢查有那些Server需要上傳資料，以清除不在此次訂閱之數據，主要是針對有多台ABus Server時所要做的處理。
			 * 例如：上次訂閱T1及H1開頭的數據，此次只訂閱T1開頭的數據，因此要將H1開頭的數據清除。*/
			Map<String, String> mustSendMap = new HashMap<String, String>();
			if (smartMasterParamArray != null && smartMasterParamArray.length() != 0) {
				JSONObject cond;
				String exchangeID, idAndPort;
				for (int i = 0, length = smartMasterParamArray.length();i < length;i++) {
					cond = smartMasterParamArray.getJSONObject(i);
					exchangeID = cond.getString(JSConst.CU_EXCHANGEID);
					idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
					mustSendMap.put(idAndPort, "");
				}
				cond = null;
				exchangeID = null;
				idAndPort = null;
			}
			// 500-01-End.

			//500-02-Begin: 設定訂閱主力大單查詢參數
			if (jArray != null && jArray.length() != 0) {
				// 記錄查詢參數之List
				JSONArray bakSmartMasterArray = new JSONArray();
				
				JSONObject cond;
				JSONObject bakCond;
				for (int i = 0, length = jArray.length();i < length;i++) {
					cond = jArray.getJSONObject(i);
					bakCond = new JSONObject();
					bakCond.put(JSConst.CU_EXCHANGEID, cond.getString(JSConst.CU_EXCHANGEID));
					if (cond.isNull(JSConst.MS_MINIMUM_AMOUNT)) {
						bakCond.put(JSConst.MS_MINIMUM_AMOUNT, 0);
					}else {
						bakCond.put(JSConst.MS_MINIMUM_AMOUNT, cond.getLong(JSConst.MS_MINIMUM_AMOUNT));
					}
					if (cond.isNull(JSConst.MS_MINIMUM_VOLUME)) {
						bakCond.put(JSConst.MS_MINIMUM_VOLUME, 0);
					}else {
						bakCond.put(JSConst.MS_MINIMUM_VOLUME, cond.getLong(JSConst.MS_MINIMUM_VOLUME));
					}
					bakSmartMasterArray.put(bakCond);
				}
				smartMasterParamArray = bakSmartMasterArray;
				bakSmartMasterArray = null;
			}else {
				if (smartMasterParamArray != null) {
					smartMasterParamArray = null;
				}
			}
			//500-02-End.

			if (smartMasterParamArray != null && smartMasterParamArray.length() != 0) {
				Map<String, JABXQuoteDataInfo> mergeDataMap = new HashMap<String, JABXQuoteDataInfo>();
				JABXFixData fixData;
				JSONObject condition;
				String exchangeID, idAndPort;
				JABXQuoteDataInfo mdInfo;
				List<JABXFixData> fdList;
				StringBuffer sb;
				for (int i = 0, length = smartMasterParamArray.length();i < length;i++) {
					condition = smartMasterParamArray.getJSONObject(i);
					exchangeID = condition.getString(JSConst.CU_EXCHANGEID);

					idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
					mdInfo = mergeDataMap.get(idAndPort);
					if (mdInfo == null) {
						mdInfo = new JABXQuoteDataInfo();
					}
					mergeDataMap.put(idAndPort, mdInfo);

					fdList = new ArrayList<JABXFixData>();

					fixData = new JABXFixData();
					fixData.setTag("3");// 訊息主代碼
					fixData.setValue(String.valueOf(JABXConst.ABUS_MAINTYPE_SMART));
					fdList.add(fixData);
	
					fixData = new JABXFixData();
					fixData.setTag("4");// 訊息次代碼
					fixData.setValue(Integer.toHexString(JABXConst.ABUS_SMARTMSG_MASTER));
					fdList.add(fixData);
	
					fixData = new JABXFixData();
					fixData.setTag("5");// 證券鍵值代碼
					sb = new StringBuffer();
					sb.append(fillLeftData(' ', exchangeID, 2));// 交易所代碼
					sb.append(fillLeftData(' ', String.valueOf(condition.getInt(JSConst.MS_MINIMUM_VOLUME)), 8));// 主力大單最小成交量
					sb.append(fillLeftData(' ', String.valueOf(condition.getInt(JSConst.MS_MINIMUM_AMOUNT)), 8));// 主力大單最小成交金額
					fixData.setValue(sb.toString());
					fdList.add(fixData);

					mdInfo.addCount(1);
					mdInfo.addFixDataList(fdList);
				}

				fixData = null;
				condition = null;
				exchangeID = null;
				idAndPort = null;
				mdInfo = null;
				fdList = null;
				sb = null;

				jabxKernel.getReservedQuoteData().putSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_SMARTMASTER), mergeDataMap);
				mergeDataMap = null;
			}else {
				jabxKernel.getReservedQuoteData().removeSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_SMARTMASTER));
			}

			// 上傳Socket數據
			uploadSocketData(requestID, JABXConst.ABXFUN_SUBSCRIBE_SMARTMASTER, mustSendMap);
			mustSendMap = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeSmartMaster()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeSmartMaster()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeSmartMaster()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_SMARTMASTER, requestID, "Execution Error!", nErrCode);
		}
	}

	/**
	 * 訂閱即時排名訊息
	 * @param requestID - int(API回覆碼)
	 * @param jArray - JSONArray(訂閱即時排名資訊)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;JSONArray中每單一物件為String(即時排名群組代碼)物件。
	 */
	void subscribeSmartRank(int requestID, JSONArray jArray) {

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_SUBSCRIBE_SMARTRANK);

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			/* 500-01-Begin: 檢查有那些Server需要上傳資料，以清除不在此次訂閱之數據，主要是針對有多台ABus Server時所要做的處理。
			 * 例如：上次訂閱T1及H1開頭的數據，此次只訂閱T1開頭的數據，因此要將H1開頭的數據清除。*/
			Map<String, String> mustSendMap = new HashMap<String, String>();
			if (smartRankParamArray != null && smartRankParamArray.length() != 0) {
				String groupID, exchangeID, idAndPort;
				for (int i = 0, length = smartRankParamArray.length();i < length;i++) {
					groupID = smartRankParamArray.getString(i);
					exchangeID = groupID.substring(0, 2);
					idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
					mustSendMap.put(idAndPort, "");
				}
				groupID = null;
				exchangeID = null;
				idAndPort = null;
			}
			// 500-01-End.

			//500-02-Begin: 設定訂閱即時排名查詢參數
			if (jArray != null && jArray.length() != 0) {
				// 記錄查詢參數之List
				JSONArray bakSmartRankArray = new JSONArray();
				
				String groupID;
				for (int i = 0, length = jArray.length();i < length;i++) {
					groupID = jArray.getString(i);
				
					bakSmartRankArray.put(groupID);
				}
				smartRankParamArray = bakSmartRankArray;
				bakSmartRankArray = null;
			}else {
				smartRankParamArray = null;
			}
			//500-02-End.

			if (smartRankParamArray != null && smartRankParamArray.length() != 0) {
				Map<String, JABXQuoteDataInfo> mergeDataMap = new HashMap<String, JABXQuoteDataInfo>();
				JABXFixData fixData;
				String groupID, exchangeID, idAndPort;
				JABXQuoteDataInfo mdInfo;
				List<JABXFixData> fdList;
				for (int i = 0, length = smartRankParamArray.length();i < length;i++) {
					groupID = smartRankParamArray.getString(i);
					exchangeID = groupID.substring(0, 2);

					idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
					mdInfo = mergeDataMap.get(idAndPort);
					if (mdInfo == null) {
						mdInfo = new JABXQuoteDataInfo();
					}
					mergeDataMap.put(idAndPort, mdInfo);

					fdList = new ArrayList<JABXFixData>();

					fixData = new JABXFixData();
					fixData.setTag("3");// 訊息主代碼
					fixData.setValue(String.valueOf(JABXConst.ABUS_MAINTYPE_SMART));
					fdList.add(fixData);
	
					fixData = new JABXFixData();
					fixData.setTag("4");// 訊息次代碼
					fixData.setValue(Integer.toHexString(JABXConst.ABUS_SMARTMSG_RANK));
					fdList.add(fixData);
	
					fixData = new JABXFixData();
					fixData.setTag("5");// 證券鍵值代碼
					fixData.setValue(groupID);
					fdList.add(fixData);

					mdInfo.addCount(1);
					mdInfo.addFixDataList(fdList);
				}

				fixData = null;
				groupID = null;
				exchangeID = null;
				idAndPort = null;
				mdInfo = null;
				fdList = null;

				jabxKernel.getReservedQuoteData().putSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_SMARTRANK), mergeDataMap);
				mergeDataMap = null;
			}else {
				jabxKernel.getReservedQuoteData().removeSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_SMARTRANK));
			}

			// 上傳Socket數據
			uploadSocketData(requestID, JABXConst.ABXFUN_SUBSCRIBE_SMARTRANK, mustSendMap);
			mustSendMap = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeSmartRank()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeSmartRank()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeSmartRank()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_SMARTRANK, requestID, "Execution Error!", nErrCode);
		}
	}

	/**
	 * 訂閱即時短線精靈訊息
	 * @param requestID - int(API回覆碼)
	 * @param jArray - JSONArray(訂閱即時短線精靈資訊)<br>
	 * &nbsp;&nbsp;JSONArray中每單一物件為String(短線精靈群組代碼)物件。
	 */
	void subscribeSmartShort(int requestID, JSONArray jArray) {

		jabxKernel.putInfoMapItem(String.valueOf(requestID), JABXConst.ABXFUN_SUBSCRIBE_SMARTSHORT);

		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			/* 500-01-Begin: 檢查有那些Server需要上傳資料，以清除不在此次訂閱之數據，主要是針對有多台ABus Server時所要做的處理。
			 * 例如：上次訂閱T1及H1開頭的數據，此次只訂閱T1開頭的數據，因此要將H1開頭的數據清除。*/
			Map<String, String> mustSendMap = new HashMap<String, String>();
			if (smartShortParamArray != null && smartShortParamArray.length() != 0) {
				String groupID, exchangeID, idAndPort;
				for (int i = 0, length = smartShortParamArray.length();i < length;i++) {
					groupID = smartShortParamArray.getString(i);
					exchangeID = groupID.substring(0, 2);
					idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
					mustSendMap.put(idAndPort, "");
				}
				groupID = null;
				exchangeID = null;
				idAndPort = null;
			}
			// 500-01-End.

			// 500-02-Begin: 設定訂閱即時智慧選股查詢參數
			if (jArray != null && jArray.length() != 0) {
				// 記錄查詢參數之List
				JSONArray bakSmartShortList = new JSONArray();
			
				String groupID;
				for (int i = 0, length = jArray.length();i < length;i++) {
					groupID = jArray.getString(i);
				
					bakSmartShortList.put(groupID);
				}
				smartShortParamArray = bakSmartShortList;
				bakSmartShortList = null;
			}else {
				smartShortParamArray = null;
			}
			//500-02-End.

			if (smartShortParamArray != null && smartShortParamArray.length() != 0) {
				Map<String, JABXQuoteDataInfo> mergeDataMap = new HashMap<String, JABXQuoteDataInfo>();
				JABXFixData fixData;
				String groupID, exchangeID, idAndPort;
				JABXQuoteDataInfo mdInfo;
				List<JABXFixData> fdList;
				for (int i = 0, length = smartShortParamArray.length();i < length;i++) {
					groupID = smartShortParamArray.getString(i);
					exchangeID = groupID.substring(0, 2);

					idAndPort = jabxKernel.getIPAndPortByExchangeID(exchangeID, JABXConst.SERVER_TYPE_0);
					mdInfo = mergeDataMap.get(idAndPort);
					if (mdInfo == null) {
						mdInfo = new JABXQuoteDataInfo();
					}
					mergeDataMap.put(idAndPort, mdInfo);

					fdList = new ArrayList<JABXFixData>();

					fixData = new JABXFixData();
					fixData.setTag("3");// 訊息主代碼
					fixData.setValue(String.valueOf(JABXConst.ABUS_MAINTYPE_SMART));
					fdList.add(fixData);
	
					fixData = new JABXFixData();
					fixData.setTag("4");// 訊息次代碼
					fixData.setValue(Integer.toHexString(JABXConst.ABUS_SMARTMSG_SHORT));
					fdList.add(fixData);

					fixData = new JABXFixData();
					fixData.setTag("5");// 證券鍵值代碼
					fixData.setValue(groupID);
					fdList.add(fixData);
				}

				fixData = null;
				groupID = null;
				exchangeID = null;
				idAndPort = null;
				mdInfo = null;
				fdList = null;

				jabxKernel.getReservedQuoteData().putSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_SMARTSHORT), mergeDataMap);
				mergeDataMap = null;
			}else {
				jabxKernel.getReservedQuoteData().removeSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_SMARTSHORT));
			}

			// 上傳Socket數據
			uploadSocketData(requestID, JABXConst.ABXFUN_SUBSCRIBE_SMARTSHORT, mustSendMap);
			mustSendMap = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeSmartShort()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeSmartShort()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeSmartShort()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_SMARTSHORT, requestID, "Execution Error!", nErrCode);
		}
	}

	/**
	 * 訂閱警示訊息
	 * @param requestID - int(API回覆碼)
	 * @param isSubscribe - boolean(是否訂閱;true.訂閱,false.取消訂閱)
	 */
	void subscribeSmartWarn(int requestID, boolean isSubscribe) {
		// TODO Auto-generated method stub
		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			/* 500-01-Begin: 檢查有那些Server需要上傳資料，以清除不在此次訂閱之數據，主要是針對有多台ABus Server時所要做的處理。
			 * 例如：上次訂閱T1及H1開頭的數據，此次只訂閱T1開頭的數據，因此要將H1開頭的數據清除。*/
			Map<String, String> mustSendMap = new HashMap<String, String>();
			if (isSubscribe) {
				mustSendMap.put(jabxKernel.getFirstLoginServerIPAndPort(), "");
			}
			// 500-01-End.

			// 500-02-Begin: 設定訂閱即時警示訊息查詢參數
			this.isSubscribeWarn = isSubscribe;
			// 500-02-End.
			
			if (isSubscribeWarn) {
				Map<String, JABXQuoteDataInfo> mergeDataMap = new HashMap<String, JABXQuoteDataInfo>();
				String idAndPort = jabxKernel.getFirstLoginServerIPAndPort();
				List<JABXFixData> fdList = new ArrayList<JABXFixData>();
				JABXQuoteDataInfo mdInfo = new JABXQuoteDataInfo();
				mergeDataMap.put(idAndPort, mdInfo);

				JABXFixData fixData;

				fixData = new JABXFixData();
				fixData.setTag("3");// 訊息主代碼
				fixData.setValue(String.valueOf(JABXConst.ABUS_MAINTYPE_SMART));
				fdList.add(fixData);

				fixData = new JABXFixData();
				fixData.setTag("4");// 訊息次代碼
				fixData.setValue(Integer.toHexString(JABXConst.ABUS_SMARTMSG_WARN));
				fdList.add(fixData);

				int userID = (int)jabxKernel.getLoginInfo().get(JSConst.LN_USERGWID);
				
				fixData = new JABXFixData();
				fixData.setTag("5");// 用戶id
				fixData.setValue(String.valueOf(userID));
				fdList.add(fixData);
				
				mdInfo.addCount(1);
				mdInfo.addFixDataList(fdList);
			
				idAndPort = null;
				fdList = null;
				mdInfo = null;
				fixData = null;

				jabxKernel.getReservedQuoteData().putSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_SMARTWARN), mergeDataMap);
				mergeDataMap = null;
			}else {
				jabxKernel.getReservedQuoteData().removeSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_SMARTWARN));
			}

			// 上傳Socket數據
			uploadSocketData(requestID, JABXConst.ABXFUN_SUBSCRIBE_SMARTWARN, mustSendMap);
			mustSendMap = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeSmartWarn()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeSmartWarn()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeSmartWarn()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_SMARTWARN, requestID, "Execution Error!", nErrCode);
		}
	}

	/**
	 * 訂閱即時ABus公告訊息
	 * @param requestID - int(API回覆碼)
	 * @param isSubscribe - boolean(是否訂閱;true.訂閱,false.取消訂閱)
	 */
	void subscribeSystemBulletin(int requestID, boolean isSubscribe) {
		// TODO Auto-generated method stub
		int nErrCode = JABXErrCode.NO_ERROR;
		try {
			/* 500-01-Begin: 檢查有那些Server需要上傳資料，以清除不在此次訂閱之數據，主要是針對有多台ABus Server時所要做的處理。
			 * 例如：上次訂閱T1及H1開頭的數據，此次只訂閱T1開頭的數據，因此要將H1開頭的數據清除。*/
			Map<String, String> mustSendMap = new HashMap<String, String>();
			if (isSubscribe) {
				mustSendMap.put(jabxKernel.getFirstLoginServerIPAndPort(), "");
			}
			// 500-01-End.

			// 500-02-Begin: 設定訂閱即時ABus公告查詢參數
			this.isSubscribeSystemBulletin = isSubscribe;
			// 500-02-End.

			if (isSubscribeSystemBulletin) {
				Map<String, JABXQuoteDataInfo> mergeDataMap = new HashMap<String, JABXQuoteDataInfo>();
				String idAndPort = jabxKernel.getFirstLoginServerIPAndPort();
				List<JABXFixData> fdList = new ArrayList<JABXFixData>();
				JABXQuoteDataInfo mdInfo = new JABXQuoteDataInfo();
				mergeDataMap.put(idAndPort, mdInfo);

				JABXFixData fixData;

				fixData = new JABXFixData();
				fixData.setTag("3");// 訊息主代碼
				fixData.setValue(String.valueOf(JABXConst.ABUS_MAINTYPE_FREEFORMAT));
				fdList.add(fixData);

				fixData = new JABXFixData();
				fixData.setTag("4");// 訊息次代碼
				fixData.setValue(Integer.toHexString(JABXConst.ABUS_FREEMSG_BROADCAST));
				fdList.add(fixData);

				fixData = new JABXFixData();
				fixData.setTag("5");// 交易所代碼
				fixData.setValue("");
				fdList.add(fixData);

				mdInfo.addCount(1);
				mdInfo.addFixDataList(fdList);
				
				idAndPort = null;
				fdList = null;
				mdInfo = null;
				fixData = null;

				jabxKernel.getReservedQuoteData().putSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_SYSTEMBULLETIN), mergeDataMap);
				mergeDataMap = null;
			}else {
				jabxKernel.getReservedQuoteData().removeSubscribeData(String.valueOf(JABXConst.ABXFUN_SUBSCRIBE_SYSTEMBULLETIN));
			}

			// 上傳Socket數據
			uploadSocketData(requestID, JABXConst.ABXFUN_SUBSCRIBE_SYSTEMBULLETIN, mustSendMap);
			mustSendMap = null;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeSystemBulletin()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeSystemBulletin()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXQuoteRequest.subscribeSystemBulletin()", e.getMessage());
			e.printStackTrace();
		}
		
		if (nErrCode != JABXErrCode.NO_ERROR) {
			addInformationPacket(jabxKernel, JABXConst.ABXFUN_SUBSCRIBE_SYSTEMBULLETIN, requestID, "Execution Error!", nErrCode);
		}
	}
}
