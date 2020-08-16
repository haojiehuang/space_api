package com.syt.jabx.kernel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析報價系統檔案查詢之類別
 * @author Jason
 *
 */
public final class JABXParseP00 extends IJABXParseBase implements IJABXParseBody {

	/**
	 * 應用程序核心管理物件
	 */
	private JABXKernel jabxKernel;

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * 行情資訊工具物件
	 */
	private JABXQuoteTool quoteTool;

	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 * @param quoteTool - JABXQuoteTool
	 */
	public JABXParseP00(JABXKernel jabxKernel, IJABXLog jabxLog, JABXQuoteTool quoteTool) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
		this.quoteTool = quoteTool;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXParseBody#parse(com.syt.jabx.kernel.ZJABXFixProc, JSONObject, byte[], com.syt.jabx.kernel.JABXCtrlHeader, int)
	 */
	@Override
	public void parse(final ZJABXFixProc fixProc, final JSONObject result,
			byte[] dataAry, JABXCtrlHeader ctrlHeader, int offset) {
		// TODO Auto-generated method stub
		logSb.delete(0, logSb.length());
		logSb.append(this.getClass().getSimpleName()).append(".parse()");
		try {
			jabxLog.outputInfoAndLog(logSb.toString(), new String(dataAry, JABXConst.ABX_CHARSET));
		}catch(Exception e) {
		}

		byte mainType = ctrlHeader.getByMsgMainType();
		int requestID = 0;// API回覆碼
		IJABXAbyKey iabyKey = ctrlHeader.getAbyKey();// 取得AbyKey
		if (mainType == JABXConst.ABUS_MAINTYPE_CONTROL) {
			JABXAbyKeyCtrl abyKey = (JABXAbyKeyCtrl)iabyKey; 
			requestID = toUsignShort(abyKey.getByAPCode());
		}

		// 取得下載的檔案名稱
		ZJABXParameter param = (ZJABXParameter)jabxKernel.getInfoMapValue(String.valueOf(requestID));
		int funcID = param.getFuncID();
		String downloadFileName = (String)param.getParameter();
		// Output Filename Information
		jabxLog.outputInfoAndLog("JABXParseP00.parse()", String.format("Downloading file name->%s", downloadFileName));
		// 500-01-Begin: 設定檔案路徑及附檔檔名
		StringBuffer sb = new StringBuffer();
		sb.append(jabxKernel.getAppDataPath().getAbsolutePath());// 取得檔案所在實際路徑
		sb.append(File.separator);
		sb.append(downloadFileName);
		sb.append(".dat");// 添加.dat附檔名
		String datFileName = sb.toString();
		sb = null;
		// 500-01-End.
		
		boolean isReadFile = false;

		try {
			List<String> baseDataList = null, patchDataList = null;
			JABXTagValue tagValue;
			int nRead = 0;// 已讀取byte數
			int tag;
			int errorCode = 0, recordCount = 0, dataCount = 0;
			int fileLength;// 檔案內容長度
			int fixDataLength = 0;// Fix資料讀取長度(若為0，則為正常讀法，不為0，則讀取固定長度之數據)
			String errorDesc = "", fileName = "";
			while (nRead < dataAry.length) {
				tagValue = new JABXTagValue();
				nRead += fixProc.readOneFixField(dataAry, nRead, fixDataLength, tagValue);
				tag = tagValue.getTag();
				if (tagValue.getValue() == null) {
					continue;
				}
				switch (tag) {
				case 1:// 錯誤代碼
					errorCode = fixProc.getIntValue(tagValue.getValue());
					break;
				case 2:// 錯誤訊息
					errorDesc = fixProc.getValue(tagValue.getValue(), "");
					break;
				case 3:// 資料筆數
					recordCount = fixProc.getIntValue(tagValue.getValue());
					jabxLog.outputInfoAndLog("JABXParseP00.parse()", String.format("Downloaded file counts->%d", recordCount));
					if (recordCount > 0) {
						baseDataList = new ArrayList<String>();
					}
					break;
				case 4:// 檔案名稱
					fileName = fixProc.getValue(tagValue.getValue(), "");
					// Output Information
					jabxLog.outputInfoAndLog("JABXParseP00.parse()", String.format("Downloaded file name->%s", fileName));
					break;
				case 5:// 檔案內容長度
					dataCount++;
					fileLength = fixProc.getIntValue(tagValue.getValue());// 檔案內容長度
					if (fileLength != 0) {
						fixDataLength = fileLength + 3;// 讀取回補訊息之Tag(總長度length + 3長度(6=加最後一分隔符號(0x01)，共3bytes))
					}
					break;
				case 6:// 檔案內容
					fixDataLength = 0;// 檔案內容讀完，要將fixDataLength重置為0
					byte[] fileContentAry = tagValue.getValue();// 檔案內容
					int index = fileName.indexOf(".dat");
					if (index != -1) {// 檔案名中有.dat
						isReadFile = true;
						copyByteAryToList(fileContentAry, baseDataList, JABXConst.ABX_CHARSET);//將fileContentAry轉換為List
					}else {// 檔案名中沒有.dat
						if (!isReadFile) {
							isReadFile = true;
							copyFileToList(datFileName, baseDataList, JABXConst.ABX_CHARSET);// 將檔案轉入baseDataList中
						}
						patchDataList = new ArrayList<String>();
						copyByteAryToList(fileContentAry, patchDataList, JABXConst.ABX_CHARSET);// 將fileContentAry轉換為List
						mergeBaseAndPatchData(baseDataList, patchDataList);// 將patchDataList資料合併到baseDataList中
					}
					if (dataCount == recordCount) {
						copyListToFile(baseDataList, datFileName, JABXConst.ABX_CHARSET);// 將合併好之baseDataList寫入檔案(.dat)
					}
					
					fileContentAry = null;
					break;
				}
			}

			if (errorCode == JABXErrCode.NO_ERROR) {
				setData(result, funcID, downloadFileName, datFileName);
			}
			result.put(JS_Result.FUNC_ID, funcID);
			result.put(JS_Result.STATUS_ID, requestID);
			result.put(JS_Result.ERR_CODE, errorCode);
			result.put(JS_Result.ERR_DESC, errorDesc);
			switch (funcID) {
			case JABXConst.ABXFUN_DOWNLOAD_ERRORCODE:
				result.put(JS_Result.NOTIFIED, false);
				break;
			case JABXConst.ABXFUN_DOWNLOAD_STOCKTABLE:
				result.put(JS_Result.NOTIFIED, false);
				ZJABXQuoteRequest quoteRequest = (ZJABXQuoteRequest)quoteTool.getRequest();
				quoteRequest.queryNewStockData(requestID, downloadFileName, result.get(JS_Result.DATA));
				quoteRequest = null;
				break;
			default:
				result.put(JS_Result.NOTIFIED, true);
			}
			
			baseDataList = null;
			patchDataList = null;
			tagValue = null;
			errorDesc = null;
			fileName = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseP00.parse()", e.getMessage());
			e.printStackTrace();
		}catch(IOException e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.IO_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("JABXParseP00.parse()", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 將List數據複製到File中
	 * @param list - List(String)
	 * @param filePath - String
	 * @param charSet - String
	 * @throws IOException
	 */
	private void copyListToFile(final List<String> list, final String filePath, String charSet) throws IOException {
					
		BufferedWriter out;

		String line;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), charSet));
			synchronized(list) {
				synchronized(out) {
					for (int i = 0, size = list.size();i < size;i++) {
						line = (String)list.get(i);
						out.write(line + "\r\n");
					}
				}
			}		
			out.close();		
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.copyListToFile()", e.getMessage());
			throw e;
		}
		
		out = null;
		line = null;
	}		
			
	/**
	 * 將byte[]數據複製到List中
	 * @param bAry - byte[]
	 * @param list - List(String)
	 * @param charSet - String
	 * @throws IOException
	 * @throws OutOfMemoryError
	 */
	private void copyByteAryToList(byte[] bAry, final List<String> list, String charSet) throws IOException, OutOfMemoryError {
				
		BufferedReader in;

		String line;
		try {
			in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bAry), charSet));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					list.add(line);
				}
			}		
			in.close();
		}catch(OutOfMemoryError e) {
			jabxLog.outputErrorAndLog("JABXParseP00.copyByteAryToList()", e.getMessage());
			throw e;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.copyByteAryToList()", e.getMessage());
			throw e;
		}
		
		in = null;
		line = null;
	}		
			
	/**
	 * 將File數據複製到List中
	 * @param bAry - byte[]
	 * @param list - List(String)
	 * @param charSet - String
	 * @throws IOException
	 */
	private void copyFileToList(String file, final List<String> list, String charSet) throws IOException {
				
		BufferedReader in;

		String line;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(file), charSet));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					list.add(line);
				}
			}		
			in.close();				
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.copyFileToList()", e.getMessage());
			throw e;
		}
		
		in = null;
		line = null;
	}		

	/**
	 * 將Patch檔之patchDataList數據合併到baseDataList中
	 * @param baseDataList - List&lt;String&gt;
	 * @param patchDataList - List&lt;String&gt;
	 * @throws OutOfMemoryError
	 */
	private void mergeBaseAndPatchData(final List<String> baseDataList, final List<String> patchDataList) throws OutOfMemoryError {
		try {
			String text, baseText;
			int changeIndex = 0;
			for (int i = 0, size = patchDataList.size();i < size;i++) {
				text = patchDataList.get(i);
				if (i == 0) {
				
				}else if (i == 1) {
				
				}else {
					if (text.startsWith("@@")) {
						changeIndex = Integer.parseInt(text.substring(text.lastIndexOf("+") + 1, text.lastIndexOf(","))) - 2;
					}else {
						changeIndex++;
						if (text.startsWith("-")) {
							if (changeIndex >= baseDataList.size()) {//若所取得的索引值，大於等於資料的筆數，代表資料異常，中斷處理
								break;
							}		
							baseText = baseDataList.get(changeIndex);
							if (!baseText.equals(text.substring(1))) {//若要刪除的數據，與實際的數據不符，代表資料異常，中斷處理
								break;
							}
							baseDataList.remove(changeIndex);
							changeIndex--;
						}else if (text.startsWith("+")) {
							if (changeIndex > baseDataList.size()) {//若要新增的數據，大於資料筆數，代表資料異常，中斷處理
								break;
							}
							baseDataList.add(changeIndex, text.substring(1));
						}										
					}
				}			
			}
		
			text = null;
			baseText = null;
		}catch(OutOfMemoryError e) {
			jabxLog.outputErrorAndLog("JABXParseP00.mergeBaseAndPatchData()", e.getMessage());
			throw e;
		}
	}

	/**
	 * 依功能代碼(funcID)設定result中之data
	 * @param result - JSONObject
	 * @param funcID - int(功能代碼)
	 * @param datFileName - String(檔案之實際路徑)
	 * 
	 */
	private void setData(final JSONObject result, final int funcID, final String downloadFileName, final String datFileName) {
		Object data = null;
		switch (funcID) {
		case JABXConst.ABXFUN_DOWNLOAD_ERRORCODE:// ABUS錯誤代碼對照檔
			transferErrorCodeFileToMemory(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_EXCHANGELIST:// 交易所名稱資料檔
			data = transferExchangeTableFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_FORMULACLASS:// 公式分類資料檔
			data = transferFormulaClassFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_FORMULALIST:// 公式列表資料檔
			data = transferFormulaListFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_TECHNICALINDEXCLASS:// 技術指標分類資料檔
			data = transferTechnicalIndexClassFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_TECHNICALINDEXLIST:// 技術指標列表資料檔
			data = transferTechnicalIndexListFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_PRODUCTCLASS:// 產品功能類別檔
			data = transferProductClassFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_STOCKTABLE: // 查詢股票名稱(拼音)資料檔
			data = transferStockTableFileToClass(downloadFileName, datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_STOCKRELATIONSTOCKLIST:// 查詢交易所1股票關連交易所2股票列表
			data = transferStockRelationStockListFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_STOCKRELATIONWARRANTSLIST:// 查詢一般股票關連權證股票(港股)資料檔
			data = transferStockRelationWarrantsListFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_CLASSRELATIONSTOCKLIST:// 查詢分類關連股票列表
			data = transferClassRelationStockListFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_STOCKRELATIONCLASSLIST:// 查詢股票關連分類索引檔
			data = transferStockRelationClassListFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_STOCKCLASS:// 查詢商品分類資料
			data = transferStockClassFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_TRADECLASS:// 查詢產業分類資料
			data = transferTradeClassFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_BLOCKCLASS:// 查詢自定(板塊)分類資料
			data = transferBlockClassFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_SMARTSHORTCLASS:// 查詢智慧選股(短線精靈)分類資料
			data = transferSmartShortClassFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_SMARTRANKCLASS:// 查詢即時排名分類資料檔
			data = transferSmartRankClassFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_SPREADCLASS:// 查詢價差交易分類資料
			data = transferSpreadClassFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_OTHERCLASS:// 查詢其他分類資料
			data = transferOtherClassFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_TRADECLASS2:// 查詢產業分類資料2
			data = transferTradeClass2FileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_STKTYPETREE:// 查詢分類樹
			data = transferStktypeTreeFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_FUTUREOPTION:// 查詢期權股名檔
			data = transferFutureOptionTableFileToClass(datFileName);
			break;
		case JABXConst.ABXFUN_DOWNLOAD_WARNCLASS:// 查詢警示定義檔
			data = transferWarnClassFileToClass(datFileName);
			break;
		}
		result.put(JS_Result.DATA, data);
	}

	/**
	 * 將ABUS錯誤代碼對照檔轉載入Memory中
	 * @param datFileName - String(檔案之實際路徑)
	 */
	private void transferErrorCodeFileToMemory(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return;
		}

		BufferedReader in;

		String line;
		try {
			String[] strAry;
			int lineNum = 0;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					strAry = line.split("\\|");
					if (strAry != null && strAry.length >= 2) {
						ZJABXErrorInfo.addData(Integer.valueOf(strAry[0]), strAry[1]);
					}
				}
			}		
			in.close();

			strAry = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferErrorCodeFileToMemory()", e.getMessage());
			e.printStackTrace();
		}
		
		in = null;
		line = null;
	}

	/**
	 * 將交易所名檔檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferExchangeTableFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXExchangeList exchangeTable = new ZJABXExchangeList();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXExchangeListItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					if (dataAry.length >= 3) {
						item = new ZJABXExchangeListItem();
						item.setID(dataAry[0]);
						item.setName(dataAry[1]);
						item.setAttribute(dataAry[2]);
						exchangeTable.addItem(item);
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferExchangeTableFileToClass()", e.getMessage());
			e.printStackTrace();
		}
		
		in = null;
		line = null;
		
		return exchangeTable;
	}

	/**
	 * 將公式分類檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferFormulaClassFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXFormulaClass formulaClass = new ZJABXFormulaClass();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXFormulaClassItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 4) {
						item = new ZJABXFormulaClassItem();
						isFormatError = false;
						try {
							item.setClassID(Integer.parseInt(dataAry[0]));
							item.setClassName(dataAry[1]);
							item.setParentClassID(Integer.parseInt(dataAry[2]));
							item.setType(Integer.parseInt(dataAry[3]));
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferFormulaClassFileToClass()", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							formulaClass.addItem(item);
						}
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferFormulaClassFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;

		return formulaClass;
	}

	/**
	 * 將公式列表檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferFormulaListFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXFormulaList formulaList = new ZJABXFormulaList();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXFormulaListItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 5) {
						item = new ZJABXFormulaListItem();
						isFormatError = false;
						try {
							item.setFormulaID(Integer.parseInt(dataAry[0]));
							item.setClassID(Integer.parseInt(dataAry[1]));
							item.setName(dataAry[2]);
							item.setChineseName(dataAry[3]);
							item.setPosType(Integer.parseInt(dataAry[4]));
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferFormulaListFileToClass()", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							formulaList.addItem(item);
						}
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferFormulaListFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;

		return formulaList;
	}

	/**
	 * 將技術指標分類檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferTechnicalIndexClassFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXTechnicalIndexClass technicalIndexClass = new ZJABXTechnicalIndexClass();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXTechnicalIndexClassItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 4) {
						item = new ZJABXTechnicalIndexClassItem();
						isFormatError = false;
						try {
							item.setClassID(Integer.parseInt(dataAry[0]));
							item.setName(dataAry[1]);
							item.setParentClassID(Integer.parseInt(dataAry[2]));
							item.setType(Integer.parseInt(dataAry[3]));
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferTechnicalIndexClassFileToClass()", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							technicalIndexClass.addItem(item);
						}
					}
				}
			}
			in.close();
			
			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferTechnicalIndexClassFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;

		return technicalIndexClass;
	}

	/**
	 * 將技術指標分類列表檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferTechnicalIndexListFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXTechnicalIndexList technicalIndexList = new ZJABXTechnicalIndexList();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXTechnicalIndexListItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 4) {
						item = new ZJABXTechnicalIndexListItem();
						isFormatError = false;
						try {
							item.setTechnicalIndexID(Integer.parseInt(dataAry[0]));
							item.setClassID(Integer.parseInt(dataAry[1]));
							item.setName(dataAry[2]);
							item.setChineseName(dataAry[3]);
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferTechnicalIndexListFileToClass()", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							technicalIndexList.addItem(item);
						}
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferTechnicalIndexListFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;

		return technicalIndexList;
	}

	/**
	 * 將技術指標分類列表檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferProductClassFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXProductClass productClass = new ZJABXProductClass();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXProductClassItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					if (dataAry.length >= 2) {
						item = new ZJABXProductClassItem();
						item.setShelfID(dataAry[0]);
						item.setShelfName(dataAry[1]);
						productClass.addItem(item);
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferProductClassFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;

		return productClass;
	}

	/**
	 * 將股名檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferStockTableFileToClass(final String downloadFileName, final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		ZJABXStockOverview stockOverview = new ZJABXStockOverview();
		BufferedReader in;
		String line;
		try {
			int lineNum = 0;
			String[] dataAry;
			List<String> listOfPinying;
			ZJABXStockItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					if (dataAry.length >= 2) {
						item = new ZJABXStockItem();
						item.setID(dataAry[0]);
						item.setName(dataAry[1]);
						if (dataAry.length > 2) {// 大於2代表資料中有股名拼音數據
							listOfPinying = new ArrayList<String>();
							for (int i = 2;i < dataAry.length;i++) {
								listOfPinying.add(dataAry[i]);
							}
							item.setListOfPinying(listOfPinying);
						}
						stockOverview.addItem(item);
					}
				}
			}
			in.close();

			if (jabxKernel.isReservedData()) {
				ZJABXStockCollection stockCollection = (ZJABXStockCollection)quoteTool.getStockCollection();
				stockCollection.put(downloadFileName, stockOverview);
				stockCollection = null;
			}
			dataAry = null;
			listOfPinying = null;
			item = null;			
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferStockTableFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return stockOverview;
	}

	/**
	 * 將交易所1股票關連交易所2股票資料檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferStockRelationStockListFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXStockRelationStockList srsList = new ZJABXStockRelationStockList();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXStockRelationStockListItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 3) {
						isFormatError = false;
						item = new ZJABXStockRelationStockListItem();
						try {
							item.setID(dataAry[0]);
							item.setRelationClassID(Integer.parseInt(dataAry[1]));
							item.setID2(dataAry[2]);
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferStockRelationStockListFileToClass()", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							srsList.addItem(item);
						}
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferStockRelationStockListFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return srsList;
	}

	/**
	 * 將股票關連權證股票索引資料檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferStockRelationWarrantsListFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXStockRelationWarrantsList srsList = new ZJABXStockRelationWarrantsList();
		try {
			int lineNum = 0;
			String[] dataAry;
			List<String> listOfWarrantsID;
			ZJABXStockRelationWarrantsListItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					if (dataAry.length >= 2) {
						item = new ZJABXStockRelationWarrantsListItem();
						item.setID(dataAry[0]);
						listOfWarrantsID = new ArrayList<String>();
						for (int i = 1;i < dataAry.length;i++) {// 陣列從索引1開始為WarrantsID
							listOfWarrantsID.add(dataAry[i]);
						}
						item.setListOfWarrantsID(listOfWarrantsID);
						srsList.addItem(item);
					}
				}
			}
			in.close();

			dataAry = null;
			listOfWarrantsID = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferStockRelationWarrantsListFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return srsList;
	}

	/**
	 * 將分類關連股票索引檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferClassRelationStockListFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXClassRelationStockList crsList = new ZJABXClassRelationStockList();
		try {
			int lineNum = 0;
			String[] dataAry;
			List<String> listOfID;
			ZJABXClassRelationStockListItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 2) {
						isFormatError = false;
						item = new ZJABXClassRelationStockListItem();
						try {
							item.setClassID(Integer.parseInt(dataAry[0]));
							listOfID = new ArrayList<String>();
							for (int i = 1;i < dataAry.length;i++) {// 陣列從索引1開始為ID
								listOfID.add(dataAry[i]);
							}
							item.setListOfStockID(listOfID);
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferClassRelationStockListFileToClass()", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							crsList.addItem(item);
						}
					}
				}
			}
			in.close();

			dataAry = null;
			listOfID = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferClassRelationStockListFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return crsList;
	}

	/**
	 * 將股票關連分類索引檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferStockRelationClassListFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXStockRelationClassList srcList = new ZJABXStockRelationClassList();
		try {
			int lineNum = 0;
			String[] dataAry;
			List<Integer> listOfClassID;
			ZJABXStockRelationClassListItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 2) {
						isFormatError = false;
						item = new ZJABXStockRelationClassListItem();
						try {
							item.setClassID(dataAry[0]);
							listOfClassID = new ArrayList<Integer>();
							for (int i = 1;i < dataAry.length;i++) {// 陣列從索引1開始為ClassID
								listOfClassID.add(Integer.parseInt(dataAry[i]));
							}
							item.setListOfID(listOfClassID);
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferStockRelationClassListFileToClass()", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							srcList.addItem(item);
						}
					}
				}
			}
			in.close();

			dataAry = null;
			listOfClassID = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferStockRelationClassListFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return srcList;
	}

	/**
	 * 將商品大分類設定檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferStockClassFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXStockClass stockClass = new ZJABXStockClass();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXStockClassItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 5) {
						item = new ZJABXStockClassItem();
						isFormatError = false;
						try {
							item.setClassID(Integer.parseInt(dataAry[0]));
							item.setName(dataAry[1]);
							item.setExchangeID(dataAry[2]);
							item.setCode(dataAry[3]);
							item.setNote(dataAry[4]);
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferStockClassFileToClass()", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							stockClass.addItem(item);
						}
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferStockClassFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return stockClass;
	}

	/**
	 * 將產業分類設定檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferTradeClassFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXTradeClass tradeClass = new ZJABXTradeClass();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXTradeClassItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 5) {
						item = new ZJABXTradeClassItem();
						isFormatError = false;
						try {
							item.setClassID(Integer.parseInt(dataAry[0]));
							item.setName(dataAry[1]);
							item.setExchangeID(dataAry[2]);
							item.setCode(dataAry[3]);
							item.setAttribute(dataAry[4]);
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferTradeClassFileToClass()", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							tradeClass.addItem(item);
						}
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferTradeClassFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return tradeClass;
	}

	/**
	 * 將產業分類2設定檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferTradeClass2FileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXTradeClass2 tradeClass2 = new ZJABXTradeClass2();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXTradeClass2Item item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 7) {
						item = new ZJABXTradeClass2Item();
						isFormatError = false;
						try {
							item.setClassID(Integer.parseInt(dataAry[0]));
							item.setName(dataAry[1]);
							item.setCode(dataAry[2]);
							item.setAttribute(dataAry[3]);
							item.setQuoteID(dataAry[4]);
							item.setStkID(dataAry[5]);
							item.setTradeID(dataAry[6]);
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferTradeClass2FileToClass", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							tradeClass2.addItem(item);
						}
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferTradeClass2FileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return tradeClass2;
	}

	/**
	 * 將自定(板塊)分類設定檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferBlockClassFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXBlockClass blockClass = new ZJABXBlockClass();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXBlockClassItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 5) {
						item = new ZJABXBlockClassItem();
						isFormatError = false;
						try {
							item.setClassID(Integer.parseInt(dataAry[0]));
							item.setName(dataAry[1]);
							item.setExchangeID(dataAry[2]);
							item.setAttribute(dataAry[3]);
							item.setParentClassID(Integer.parseInt(dataAry[4]));
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferBlockClassFileToClass()", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							blockClass.addItem(item);
						}
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferBlockClassFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return blockClass;
	}

	/**
	 * 將短線精靈分類設定檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferSmartShortClassFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXSmartShortClass smartShortClass = new ZJABXSmartShortClass();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXSmartShortClassItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 3) {
						item = new ZJABXSmartShortClassItem();
						isFormatError = false;
						try {
							item.setClassID(Integer.parseInt(dataAry[0]));
							item.setName(dataAry[1]);
							item.setExchangeID(dataAry[2]);
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferSmartShortClassFileToClass()", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							smartShortClass.addItem(item);
						}
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferSmartShortClassFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return smartShortClass;
	}

	/**
	 * 將排名分類設定檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferSmartRankClassFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXSmartRankClass smartRankClass = new ZJABXSmartRankClass();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXSmartRankClassItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 4) {
						item = new ZJABXSmartRankClassItem();
						isFormatError = false;
						try {
							item.setMarketType(dataAry[0]);
							item.setClassID(Integer.parseInt(dataAry[1]));
							item.setName(dataAry[2]);
							item.setExchangeID(dataAry[3]);
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferSmartRankClassFileToClass()", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							smartRankClass.addItem(item);
						}
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferSmartRankClassFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return smartRankClass;
	}

	/**
	 * 將期貨價差交易分類設定檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferSpreadClassFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXSpreadClass spreadClass = new ZJABXSpreadClass();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXSpreadClassItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 5) {
						item = new ZJABXSpreadClassItem();
						isFormatError = false;
						try {
							item.setClassID(Integer.parseInt(dataAry[0]));
							item.setName(dataAry[1]);
							item.setExchangeID(dataAry[2]);
							item.setCode(dataAry[3]);
							item.setNote(dataAry[4]);
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferSpreadClassFileToClass()", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							spreadClass.addItem(item);
						}
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferSpreadClassFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return spreadClass;
	}

	/**
	 * 將其他分類設定檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferOtherClassFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXOtherClass otherClass = new ZJABXOtherClass();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXOtherClassItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 5) {
						item = new ZJABXOtherClassItem();
						isFormatError = false;
						try {
							item.setClassID(Integer.parseInt(dataAry[0]));
							item.setName(dataAry[1]);
							item.setExchangeID(dataAry[2]);
							item.setCode(dataAry[3]);
							item.setNote(dataAry[4]);
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferOtherClassFileToClass()", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							otherClass.addItem(item);
						}
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferOtherClassFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return otherClass;
	}

	/**
	 * 將分類樹檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferStktypeTreeFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXClassTreeOverview ctoObj = new ZJABXClassTreeOverview();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXClassTreeItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					if (dataAry.length >= 4) {
						item = new ZJABXClassTreeItem();
						item.setClassID(dataAry[0]);
						item.setClassName(dataAry[1]);
						item.setClassType(dataAry[2]);
						if (dataAry.length >= 5) {
							item.setClassDescription(dataAry[4]);
						}
						ctoObj.addItem(item, dataAry[3]);
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferStktypeTreeFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return ctoObj;
	}

	/**
	 * 將期權股名檔轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferFutureOptionTableFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXFutureOptionOverview foOverview = new ZJABXFutureOptionOverview();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXFutureOptionItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					boolean isFormatError;
					if (dataAry.length >= 7) {
						isFormatError = false;
						item = new ZJABXFutureOptionItem();
						try {
							item.setStkID(dataAry[0].trim());
							item.setTradeID(dataAry[1].trim());
							item.setQuoteID(dataAry[2].trim());
							item.setSettleMonth(dataAry[3].trim());
							item.setPrice(dataAry[4].trim());
							item.setCallPut(dataAry[5].trim());
							item.setName(dataAry[6].trim());
						}catch(NumberFormatException e) {
							jabxLog.outputErrorAndLog("JABXParseP00->transferFutureOptionTableFileToClass()", "NumberFormatException");
							isFormatError = true;
							e.printStackTrace();
						}
						if (!isFormatError) {
							foOverview.addItem(item);
						}
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferFutureOptionTableFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return foOverview;
	}

	/**
	 * 將警示定義樹轉為對應之class
	 * @param datFileName - String(實際檔案路徑)
	 * @return Object
	 */
	private Object transferWarnClassFileToClass(final String datFileName) {

		File file = new File(datFileName);
		if (!file.exists()) {// 檢查檔案是否存在，若不存在直接回null
			return null;
		}

		BufferedReader in;

		String line;
		ZJABXWarnTreeOverview wtoObj = new ZJABXWarnTreeOverview();
		try {
			int lineNum = 0;
			String[] dataAry;
			ZJABXWarnTreeItem item;
			in = new BufferedReader(new InputStreamReader(new FileInputStream(datFileName), JABXConst.ABX_CHARSET));
			synchronized(in) {
				while ((line = in.readLine()) != null) {
					if (lineNum == 0) {// 第一行為CheckSumn及檔案序號，所以跳過
						lineNum = 1;
						continue;
					}
					dataAry = line.split("\\|");
					if (dataAry.length >= 9) {
						item = new ZJABXWarnTreeItem();
						item.setGroupID(dataAry[0]);
						item.setGroupName(dataAry[1]);
						item.setClassType(dataAry[2]);
						item.setDefaultValue(dataAry[4]);
						item.setDefaultTime(dataAry[5]);
						item.setSpecialStockFlag(dataAry[6]);
						item.setSpecialUserFlag(dataAry[7]);
						item.setMark(dataAry[8]);
						wtoObj.addItem(item, dataAry[3]);
					}
				}
			}
			in.close();

			dataAry = null;
			item = null;
		}catch(IOException e) {
			jabxLog.outputErrorAndLog("JABXParseP00.transferWarnClassFileToClass()", e.getMessage());
			e.printStackTrace();
		}

		in = null;
		line = null;
		
		return wtoObj;
	}
}