package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXActionListener;
import com.syt.jabx.notify.IJABXLog;

/**
 * 訂閱連線心跳之類別
 * @author jason
 *
 */
final class ZJABXGWHeartbitThread extends Thread implements IJABXActionListener {

	/**
	 * GW Heartbit睡眠時間(1:55秒)(單位為millisecond)
	 */
	static final int WAIT_GW_HEARTBIT_SLEEP_TIME = 115000; 

	/**
	 * 記錄指標連線心跳LineID之List物件
	 */
	private List<String> listOfLineID = new ArrayList<String>();

	/**
	 * listOfLineID所使用之Lock
	 */
	private byte[] listOfLineIDLock = new byte[0];

	/**
	 * 決定是否執行GWHeartbitThread的Flag
	 */
	private volatile boolean isRunningFlag = false;

	/**
	 * 管理股票即時報價及歷史數據下載之物件
	 */
	private JABXKernel jabxKernel;

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * 實作行情資訊請求介面之物件
	 */
	private ZJABXQuoteRequest quoteRequest;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	  @param quoteRequest - ZJABXQuoteRequest
	 * @param listOfLineID - List&lt;String&gt;
	 */
	public ZJABXGWHeartbitThread(JABXKernel jabxKernel, IJABXLog jabxLog, ZJABXQuoteRequest quoteRequest, List<String> listOfLineID) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
		this.quoteRequest = quoteRequest;
		this.listOfLineID.addAll(listOfLineID);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (isRunningFlag) {
				Thread.sleep(WAIT_GW_HEARTBIT_SLEEP_TIME);
			
				if (quoteRequest != null) {
					jabxKernel.addListener(this);
					quoteRequest.sendTechnicalKeepAlive(jabxKernel.getRequestID(), listOfLineID);
				}
			}	
		}catch(InterruptedException e) {
			jabxLog.outputInfoAndLog("ZJABXGWHeartbitThread.run()", e.getMessage());
		}
	}

	@Override
	public boolean resultPerformed(JSONObject result) {
		// TODO Auto-generated method stub
		if ((int)result.get(JS_Result.ERR_CODE) != JABXErrCode.NO_ERROR) {
			return false;
		}
		int funID = (int)result.get(JS_Result.FUNC_ID);
		switch (funID) {
		case JABXConst.ABXFUN_SENDTECHNICAL_KEEPALIVE:
			jabxKernel.removeListener(this);
			ZJABXLineHeartbitStatusOverview lhsOW = (ZJABXLineHeartbitStatusOverview)result.get(JS_Result.DATA);
			if (lhsOW != null) {
				List<String> listOfRemoveLineID = new ArrayList<String>();
				ZJABXLineHeartbitStatus lhs;
				for (int i = 0, size = lhsOW.getCount();i < size;i++) {
					lhs = lhsOW.atIndex(i);
					if (lhs.getResult() != 0) {
						listOfRemoveLineID.add(lhs.getKeyCode());
					}
				}
				removeLineID(listOfRemoveLineID);
			}
			break;
		}
		
		return true;
	}

	/**
	 * 設定記錄指標連線心跳LineID之List物件
	 * @param list - List&lt;String&gt;
	 */
	void addListOfLineID(List<String> tmpListOfLineID) {
		synchronized(listOfLineIDLock) {
			listOfLineID.clear();
			listOfLineID.addAll(tmpListOfLineID);
		}
	}

	/**
	 * 自listOfLineID中移除一lineID
	 * @param lineID - String
	 */
	void removeLineID(List<String> tmpListOfLineID) {
		synchronized(listOfLineIDLock) {
			if (listOfLineID == null || listOfLineID.size() == 0 
				|| tmpListOfLineID == null || tmpListOfLineID.size() == 0) {
				return;
			}

			String lineID, tmpLineID;
			for (int i = 0, size = tmpListOfLineID.size();i < size;i++) {
				tmpLineID = tmpListOfLineID.get(i);
				for (int j = 0, size2 = listOfLineID.size();j < size2;j++) {
					lineID = listOfLineID.get(j);
					if (tmpLineID.equals(lineID)) {
						listOfLineID.remove(j);
						break;
					}
				}
			}
			
			if (listOfLineID.size() == 0) {
				isRunningFlag = false;
			}
		}
	}

	/**
	 * 設定執行緒狀態(true.執行,false.停止)
	 * @param flag - boolean
	 */
	void setIsRunningFlag(boolean isRunningFlag) {
		this.isRunningFlag = isRunningFlag;
	}

	/**
	 * 取得執行緒狀態
	 * @return boolean
	 */
	boolean isRunningFlag() {
		return this.isRunningFlag;
	}
}
