package com.syt.jabx.kernel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.syt.jabx.kmodel.IJABXExchangeInfo;
import com.syt.jabx.notify.IJABXLog;
import com.syt.jabx.query.IJABXStockCollection;
import com.syt.jabx.watch.IJABXMinuteTradeOverview;
import com.syt.jabx.watch.IJABXOrder_1;
import com.syt.jabx.watch.IJABXPriceTradeOverview;
import com.syt.jabx.watch.IJABXQuoteOverview;
import com.syt.jabx.watch.IJABXTradeOverview;

/**
 * 行情資訊工具類別
 * @author jason
 *
 */
public class JABXQuoteTool {

	/**
	 * 應用程序核心管理物件
	 */
	private JABXKernel jabxKernel;

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * 行情資訊請求物件
	 */
	private ZJABXQuoteRequest quoteRequest;

	/**
	 * 啟動GW Heartbit所使用之Thread
	 */
	private ZJABXGWHeartbitThread gwhThread;

	/**
	 * 總覽所有即時報價資訊的物件中
	 */
	private ZJABXQuoteOverview quoteOverview;

	/**
	 * 股名檔Collection的物件
	 */
	private ZJABXStockCollection stockCollection;

	/**
	 * 記錄股票之ZJABXReservedStkInfo之Map物件
	 */
	private Map<String, ZJABXReservedStkInfo> stkInfoMap = new HashMap<String, ZJABXReservedStkInfo>();

	/**
	 * stkInfoMap使用之Lock
	 */
	private byte[] stkInfoMapLock = new byte[0];

	/**
	 * 保留股票代碼所取得IJABXTradeOverview之Map物件
	 */
	private Map<String, IJABXTradeOverview> wtoMap = new HashMap<String, IJABXTradeOverview>();

	/**
	 * wtoMap所使用之Lock
	 */
	private byte[] wtoMapLock = new byte[0];

	/**
	 * 保留股票代碼所取得IJABXMinuteTradeOverview之Map物件
	 */
	private Map<String, IJABXMinuteTradeOverview> mtoMap = new HashMap<String, IJABXMinuteTradeOverview>();

	/**
	 * mtoMap所使用之Lock
	 */
	private byte[] mtoMapLock = new byte[0];

	/**
	 * 保留股票代碼所取得IJABXPriceTradeOverview之Map物件
	 */
	private Map<String, IJABXPriceTradeOverview> ptoMap = new HashMap<String, IJABXPriceTradeOverview>();

	/**
	 * ptoMap所使用之Lock
	 */
	private byte[] ptoMapLock = new byte[0];

	/**
	 * 保留股票基本資料之Map物件
	 */
	private Map<String, JSONObject> sbMap = new HashMap<String, JSONObject>();

	/**
	 * sbMap所使用之Lock
	 */
	private byte[] sbMapLock = new byte[0];

	/**
	 * 保留股票第一檔資訊之Map物件
	 */
	private Map<String, IJABXOrder_1> order1Map = new HashMap<String, IJABXOrder_1>();

	/**
	 * order1Map所使用之Lock
	 */
	private byte[] order1MapLock = new byte[0];

	/**
	 * 保留股票即時K資料之Map物件
	 */
	private Map<String, ZJABXRealtimeTechnical> rtMap = new HashMap<String, ZJABXRealtimeTechnical>();

	/**
	 * rtMap所使用之Lock
	 */
	private byte[] rtMapLock = new byte[0];

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 */
	public JABXQuoteTool(JABXKernel jabxKernel, IJABXLog jabxLog) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
		quoteRequest = new ZJABXQuoteRequest(jabxKernel, jabxLog, this);
		quoteOverview = new ZJABXQuoteOverview(this);
		stockCollection = new ZJABXStockCollection();
	}

	/**
	 * 取得行情資訊請求物件
	 * @return IJABXQuoteRequest
	 */
	public IJABXQuoteRequest getRequest() {
		return quoteRequest;
	}

	/**
	 * 取得一個新的訂閱連線心跳之Thread物件
	 * @param listOfLineID - List&lt;String&gt;
	 * @return ZJABXGWHeartbitThread
	 */
	ZJABXGWHeartbitThread getNewGWHeartbitThread(List<String> listOfLineID) {
		gwhThread = new ZJABXGWHeartbitThread(jabxKernel, jabxLog, quoteRequest, listOfLineID);
		return gwhThread;
	}

	/**
	 * 取得訂閱連線心跳之Thread物件
	 * @return ZJABXGWHeartbitThread
	 */
	ZJABXGWHeartbitThread getGWHeartbitThread() {
		return gwhThread;
	}

	/**
	 * 停止訂閱連線心跳之Thread
	 */
	public void stopThread() {
		if (gwhThread != null) {
			gwhThread.setIsRunningFlag(false);
			gwhThread.interrupt();
		}
	}

	/**
	 * 取得總覽所有即時報價資訊的物件
	 * @return IJABXQuoteOverview
	 */
	public IJABXQuoteOverview getQuoteOverview() {
		return this.quoteOverview;
	}

	/**
	 * 取得股名檔Collection的物件
	 * @return IJABXStockCollection
	 */
	public IJABXStockCollection getStockCollection() {
		return this.stockCollection;
	}

	/**
	 * 以stkId為key添加一ZJABXReservedStkInfo之物件
	 * @param stkId - String
	 * @param stkInfo - ZJABXReservedStkInfo
	 */
	void putStkInfoItem(String stkId, ZJABXReservedStkInfo stkInfo) {
		synchronized (stkInfoMapLock) {
			stkInfoMap.put(stkId, stkInfo);
		}
	}

	/**
	 * 以stkId為key移除一ZJABXReservedStkInfo物件
	 * @param stkId - String
	 */
	void removeStkInfoItem(String stkId) {
		synchronized (stkInfoMapLock) {
			stkInfoMap.remove(stkId);
		}
	}

	/**
	 * 取得以stkId為key之ZJABXReservedStkInfo
	 * @param stkId - String
	 * @return ZJABXReservedStkInfo
	 */
	ZJABXReservedStkInfo getStkInfo(String stkId) {
		synchronized (stkInfoMapLock) {
			return stkInfoMap.get(stkId);
		}
	}

	/**
	 * 以stkId為key添加一IJABXTradeOverview物件
	 * @param stkId - String
	 * @param toObj - IJABXTradeOverview
	 */
	void putWtoMapItem(String stkId, IJABXTradeOverview toObj) {
		synchronized (wtoMapLock) {
			wtoMap.put(stkId, toObj);
		}
	}

	/**
	 * 以stkId為key移除一IJABXTradeOverview物件
	 * @param stkId - String
	 */
	void removeWtoMapItem(String stkId) {
		synchronized (wtoMapLock) {
			wtoMap.remove(stkId);
		}
	}

	/**
	 * 取得以stkId為key之IJABXTradeOverview物件
	 * @param stkId - String
	 * @return IJABXTradeOverview
	 */
	IJABXTradeOverview getWtoMapItem(String stkId) {
		synchronized (wtoMapLock) {
			return wtoMap.get(stkId);
		}
	}

	/**
	 * 以stkId為key添加一IJABXMinuteTradeOverview物件
	 * @param stkId - String
	 * @param mtoObj - IJABXMinuteTradeOverview
	 */
	void putMtoMapItem(String stkId, IJABXMinuteTradeOverview mtoObj) {
		synchronized (mtoMapLock) {
			mtoMap.put(stkId, mtoObj);
		}
	}

	/**
	 * 以stkId為key移除一IJABXMinuteTradeOverview物件
	 * @param stkId - String
	 */
	void removeMtoMapItem(String stkId) {
		synchronized (mtoMapLock) {
			mtoMap.remove(stkId);
		}
	}

	/**
	 * 取得以stkId為key之IJABXMinuteTradeOverview物件
	 * @param stkId - String
	 * @return IJABXMinuteTradeOverview
	 */
	IJABXMinuteTradeOverview getMtoMapItem(String stkId) {
		synchronized (mtoMapLock) {
			return mtoMap.get(stkId);
		}
	}

	/**
	 * 以stkId為key添加一IJABXPriceTradeOverview物件
	 * @param stkId - String
	 * @param ptoObj - IJABXPriceTradeOverview
	 */
	void putPtoMapItem(String stkId, IJABXPriceTradeOverview ptoObj) {
		synchronized (ptoMapLock) {
			ptoMap.put(stkId, ptoObj);
		}
	}

	/**
	 * 以stkId為key移除一IJABXPriceTradeOverview物件
	 * @param stkId - String
	 */
	void removePtoMapItem(String stkId) {
		synchronized (ptoMapLock) {
			ptoMap.remove(stkId);
		}
	}

	/**
	 * 取得以stkId為key之IJABXPriceTradeOverview物件
	 * @param stkId - String
	 * @return IJABXPriceTradeOverview
	 */
	IJABXPriceTradeOverview getPtoMapItem(String stkId) {
		synchronized (ptoMapLock) {
			return ptoMap.get(stkId);
		}
	}

	/**
	 * 取得以stkId為key之JSONObject物件
	 * @param stkId - String
	 * @return JSONObject
	 */
	JSONObject getSbMapItem(String stkId) {
		synchronized (sbMapLock) {
			return sbMap.get(stkId);
		}
	}

	/**
	 * 以stkId為key添加一JSONObject物件
	 * @param stkId - String
	 * @param sbObj - JSONObject
	 */
	void putSbMapItem(String stkId, JSONObject sbObj) {
		synchronized (sbMapLock) {
			sbMap.put(stkId, sbObj);
		}
	}

	/**
	 * 以stkId為key移除一IJABXStockBase物件
	 * @param stkId - String
	 */
	void removeSbMapItem(String stkId) {
		synchronized (sbMapLock) {
			sbMap.remove(stkId);
		}
	}

	/**
	 * 取得以stkId為key之IJABXOrder_1物件
	 * @param stkId - String
	 * @return IJABXOrder_1
	 */
	IJABXOrder_1 getOrder1MapItem(String stkId) {
		synchronized (order1MapLock) {
			return order1Map.get(stkId);
		}
	}

	/**
	 * 以stkId為key添加一IJABXStockBase物件
	 * @param stkId - String
	 * @param sbObj - IJABXOrder_1
	 */
	void putOrder1MapItem(String stkId, IJABXOrder_1 sbObj) {
		synchronized (sbMapLock) {
			order1Map.put(stkId, sbObj);
		}
	}

	/**
	 * 以stkId為key移除一IJABXOrder_1物件
	 * @param stkId - String
	 */
	void removeOrder1MapItem(String stkId) {
		synchronized (sbMapLock) {
			sbMap.remove(stkId);
		}
	}

	/**
	 * 取得以stkId為key之ZJABXRealtimeTechnical物件
	 * @param stkId - String
	 * @return ZJABXRealtimeTechnical
	 */
	ZJABXRealtimeTechnical getRtMapItem(String stkId) {
		synchronized (rtMapLock) {
			return rtMap.get(stkId);
		}
	}

	/**
	 * 以stkId為key添加一ZJABXRealtimeTechnical物件
	 * @param stkId - String
	 * @param sbObj - ZJABXRealtimeTechnical
	 */
	void putRtMapItem(String stkId, ZJABXRealtimeTechnical sbObj) {
		synchronized (rtMapLock) {
			rtMap.put(stkId, sbObj);
		}
	}

	/**
	 * 以stkId為key移除一ZJABXRealtimeTechnical物件
	 * @param stkId - String
	 */
	void removeRtMapItem(String stkId) {
		synchronized (rtMapLock) {
			rtMap.remove(stkId);
		}
	}

	/**
	 * 清除某一股票代碼保留之資料(除了股票基本資料)
	 * @param stkID - String
	 */
	void clearReservedData(String stkID) {
		removeStkInfoItem(stkID);
		removeWtoMapItem(stkID);
		removeMtoMapItem(stkID);
		removePtoMapItem(stkID);
		removeOrder1MapItem(stkID);
		removeRtMapItem(stkID);
	}

	/**
	 * 清除某一股票代碼保留之資料
	 * @param stkID - String
	 */
	void clearReservedAllData(String stkID) {
		removeStkInfoItem(stkID);
		removeWtoMapItem(stkID);
		removeMtoMapItem(stkID);
		removePtoMapItem(stkID);
		removeSbMapItem(stkID);
		removeOrder1MapItem(stkID);
		removeRtMapItem(stkID);
	}

	/**
	 * 依股票代碼及交易時間取得對應分時之時間(HHmm)
	 * @param stkID - Strin
	 * @param sourTradeTime - int(格式為HHmmsssss, 後3碼sss為millisecond)
	 * @return int(若為午後盤，則時間為+2400後之時間)
	 */
	int getTradeTime(String stkID, int sourTradeTime) {
		int tradeHour = sourTradeTime / 10000000;// 取得交易之時
		int tradeMinute = sourTradeTime / 100000 % 100;// 取得交易之分
		int tradeSecond = sourTradeTime / 1000 % 100;// 取得交易之秒
		int tradeMillisecond = sourTradeTime % 1000;// 取得交易之毫秒
		if (tradeSecond != 0 || (tradeSecond == 0 && tradeMillisecond != 0)) {// 如果秒不為0或者秒為0，但毫秒不為0
			tradeMinute++;// 分加1
			if (tradeMinute == 60) {// 如果分為60
				tradeHour++;// 時加1
				tradeMinute = 0;// 分清為0
				if (tradeHour == 24) {
					tradeHour = 0;
				}
			}
		}

		int tradeTime = tradeHour * 100 + tradeMinute;
		ZJABXReservedStkInfo stkInfo = getStkInfo(stkID);
		int openHHMM = stkInfo.getOpenTime();
		int closeHHMM = stkInfo.getCloseTime();
		if (closeHHMM < openHHMM) {
			if (tradeTime <= closeHHMM) {
				tradeTime += 2400;
			}
		}
		
		return tradeTime;
	}

	/**
	 * 取得將yyyyMMdd加上num日後之日期(yyyyMMdd)
	 * @param yyyyMMdd - String
	 * @param num - int
	 * @return int(yyyyMMdd)
	 */
	private int addDaysToInt(int yyyyMMdd, int num) {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, yyyyMMdd / 10000);
		calendar.set(Calendar.MONTH, yyyyMMdd /100 % 100 - 1);
		calendar.set(Calendar.DATE, yyyyMMdd % 100);
		calendar.add(Calendar.DATE, num);
		return Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
	}

	/**
	 * 依股票代碼及交易時間取得交易日期
	 * @param stkID - String(股票代碼)
	 * @param tradeTime - int(交易時間hhmm)
	 * @return int(交易日期)
	 */
	int getTradeDate(String stkID, int tradeTime) {
		ZJABXExchangeInfoOverview eiObj = jabxKernel.getExchangeInfoOverview();
		IJABXExchangeInfo eInfo = eiObj.getExchangeInfo(stkID.substring(0, 2));
		int lastTradeDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		ZJABXReservedStkInfo stkInfo = getStkInfo(stkID);
		if (stkInfo != null) {
			lastTradeDate = stkInfo.getLastTradeDate();
		}
		int tradeDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		if (eInfo.getExchangeNote().equals("M")) {// S.證券交易,F.期貨交易,M.多時交易
			tradeDate = addDaysToInt(lastTradeDate, -1);
		}else {
			tradeDate = lastTradeDate;
		}

		int hour = tradeTime / 100;
		if (hour >= 24) {
			tradeDate = addDaysToInt(tradeDate, 1);
		}

		return tradeDate;
	}

	/**
	 * 依股票代碼取得交易日期
	 * @param stkID - String
	 * @return int
	 */
	int getLastTradeDate(String stkID) {
		ZJABXExchangeInfoOverview eiObj = jabxKernel.getExchangeInfoOverview();
		IJABXExchangeInfo eInfo = eiObj.getExchangeInfo(stkID.substring(0, 2));
		int lastTradeDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		ZJABXReservedStkInfo stkInfo = getStkInfo(stkID);
		if (stkInfo != null) {
			lastTradeDate = stkInfo.getLastTradeDate();
		}
		int tradeDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		if (eInfo.getExchangeNote().equals("M")) {// S.證券交易,F.期貨交易,M.多時交易
			tradeDate = addDaysToInt(lastTradeDate, -1);
		}else {
			tradeDate = lastTradeDate;
		}

		return tradeDate;
	}

	/**
	 * 取得依小數點位數之power值
	 * @param stkID - String
	 * @return long
	 */
	long getPowerByDecimal(String stkID) {
		ZJABXReservedStkInfo stkInfo = getStkInfo(stkID);
		int decimal = 2;
		if (stkInfo != null) {
			decimal = stkInfo.getDecimal();
		}
		long power = 1;
		for (int i = 0;i < decimal;i++) {
			power *= 10; 
		}
		return power;
	}
}
