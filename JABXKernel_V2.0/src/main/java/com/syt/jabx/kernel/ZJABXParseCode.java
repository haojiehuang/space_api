package com.syt.jabx.kernel;

import java.io.IOException;

import org.json.JSONObject;

import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;
import com.syt.jabx.notify.IJABXNotifier;

/**
 * 封包解析之類別
 * @author Jason
 *
 */
final class ZJABXParseCode extends IJABXParseBase {

	/**
	 * 應用程序核心管理物件
	 */
	private JABXKernel jabxKernel;

	/**
	 * 處理訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * Socket解碼之工廠物件
	 */
	private IJABXParseFactory parseFactory;
	
	/**
	 * 訊息通知介面
	 */
	private IJABXNotifier notify;

	/**
	 * 處理Fix數據之物件
	 */
	private ZJABXFixProc fixProc;

	/**
	 * Constructor
	 * @param xcore - JABXCore
	 */
	public ZJABXParseCode(JABXKernel jabxKernel, IJABXLog jabxLog, IJABXParseFactory parseFactory) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
		this.parseFactory = parseFactory;
		this.notify = jabxKernel.getNotifier();
		this.fixProc = new ZJABXFixProc(jabxLog);
	}

	/**
	 * 取得訊息通知物件
	 * @return IJABXNotify
	 */
	public IJABXNotifier getNotify() {
		return notify;
	}

	/**
	 * 封包解析
	 * @param pkt - byte[](封包)
	 */
	public synchronized void parse(ZJABXPacket pkt) {
		
		JSONObject result = null;
		JSONObject sourceResult = pkt.getResult();

		byte type = pkt.getType();// 取得ZJABXPacket物件之類型(0.串流封包1.訊息結果物件)
		try {
			result = new JSONObject();
			result.put(JS_Result.ERR_CODE, JABXErrCode.NO_ERROR);
			result.put(JS_Result.IP_PORT, sourceResult.get(JS_Result.IP_PORT));
			JABXCtrlHeader ctrlHeader = pkt.getCtrlHeader();// 取得封包之標頭	
			if (type == 0) {
				byte cmpType = ctrlHeader.getByCompressType();// 取得封包之壓縮方式
				byte[] dataAry = null;// 數據內容之byte[]
				if (cmpType != JABXConst.ABUS_COMPRESS_NULL) {// 當資料壓縮格式不為ABUS_COMPRESS_NULL時
					// 處理Compress Header
					ZJABXCompressHeader cmHeader = processCompressHeader(pkt.getCompressHeader(), jabxLog);
					if (cmHeader.getErrorCode() == JABXErrCode.NO_ERROR) {// 數據正常
						int bodyLength = ctrlHeader.getDwDataLen() - JABXConst.COMPRESS_HEADER_LENGTH;
						int unCompressLength = cmHeader.getUnCompressLength();
						// 解壓縮
						dataAry = decompressBuffer(cmpType, pkt.getData(), bodyLength, unCompressLength);
					}else {// 數據異常
						result.put(JS_Result.ERR_CODE, cmHeader.getErrorCode());
					}
				}else {// 當資料未經壓縮時
					dataAry = pkt.getData();
				}

				if ((int)result.get(JS_Result.ERR_CODE) != JABXErrCode.NO_ERROR) {// 處理過程有異常時，設定處理訊息及發出通知
					result.put(JS_Result.NOTIFIED, true);
				}else {// 若無異常則開始解析封包
					// 取得解析封包的物件
					IJABXParseBody parseBody = parseFactory.getParseBody(jabxKernel, jabxLog, ctrlHeader, new Object());
					if (parseBody != null) {
						parseBody.parse(fixProc, result, dataAry, ctrlHeader, 0);
					}
					parseBody = null;
				}
			}else {
				result = sourceResult;
			}
		}catch(IOException e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.IO_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("ZJABXParseCode.parse()", e.getMessage());
			e.printStackTrace();
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("ZJABXParseCode.parse()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.NULL_POINTER);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("ZJABXParseCode.parse()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.UNKNOWN_ERROR);
			result.put(JS_Result.NOTIFIED, true);
			jabxLog.outputErrorAndLog("ZJABXParseCode.parse()", e.getMessage());
			e.printStackTrace();
		}

		this.notifyChange(jabxKernel.getNotifier(), result);
	}
}
