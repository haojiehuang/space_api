package com.syt.jabx.kernel;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.JSONObject;

import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 實作Runnable介面之結果分派最終類別
 * @author Jason
 *
 */
final class ZJABXResultProc extends IJABXParseBase implements Runnable {

	/**
	 * 應用程序核心管理物件
	 */
	private JABXKernel jabxKernel; 

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * 封包解析之物件
	 */
	private ZJABXParseCode parseCode;

	/**
	 * 存放ZJABXPacket之BlockingQueue
	 */
	private BlockingQueue<ZJABXPacket> streamPktQue;

	/**
	 * 執行緒的Lock
	 */
	private byte[] threadLock = new byte[0];
	
	/**
	 * 執行緒是否執行(true.執行,false.不執行)
	 */
	private volatile boolean isRunFlag = true;

	/**
	 * body已讀取完成旗標
	 */
	private boolean isFullBody = false;
	
	/**
	 * 記錄Socket Header之物件
	 */
	private JABXCtrlHeader ctrlHeader;
	
	/**
	 * 記錄Socket Header之byte[]陣列
	 */
	private byte[] headerAry;
	
	/**
	 * 記錄目前已讀取Socket Header之byte數
	 */
	private int nReadHeader;

	/**
	 * Body之長度
	 */
	private int bodyLength;

	/**
	 * 記錄Socket Body之byte[]陣列
	 */
	private byte[] bodyAry;

	/**
	 * 記錄目前已讀取Socket Body之byte數
	 */
	private int nReadBody;

	/**
	 * 是否讀取Compress Header
	 */
	private boolean isReadCmpHeader;

	/**
	 * 當壓縮格式不為ABUS_COMPRESS_NULL時之標題byte[]
	 */
	private byte[] cmpHeaderAry;

	/**
	 * Compress Header已讀取完成旗標
	 */
	private boolean isFullCmpHeader = false;

	/**
	 * 已讀取之Compress Header數據
	 */
	private int nReadCmpHeader;

	/**
	 * 記錄訊息之StringBuffer
	 */
	private StringBuffer infoSb = new StringBuffer();

	/**
	 * Constructor
	 * @param jabxLog
	 * @param jabxKernel
	 */
	public ZJABXResultProc(JABXKernel jabxKernel, IJABXLog jabxLog) {
		this.jabxKernel = jabxKernel;
		this.jabxLog = jabxLog;
		this.parseCode = jabxKernel.getParseCode();
		streamPktQue = new LinkedBlockingQueue<ZJABXPacket>();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			boolean isBreakFlag = false;
			int nErrCode = JABXErrCode.NO_ERROR;
			while (isRunFlag) {
				synchronized(threadLock) {
					if (streamPktQue.isEmpty()) {
						try {
							threadLock.wait();
						}catch(Exception e) {
						}
					}
					nErrCode = processAllData();
					switch (nErrCode) {
					case JABXErrCode.IO_ERROR:
					case JABXErrCode.OUTOFMEMORY_ERROR:
					case JABXErrCode.NULL_POINTER:
					case JABXErrCode.UNKNOWN_ERROR:
						isBreakFlag = true;
						break;
					}
					if (isBreakFlag) {
						break;
					}
				}
			}
			removeAllStreamPkt();
			if (isBreakFlag) {
				jabxKernel.stop();
				jabxKernel.destroy();
			}
		}catch(InterruptedException ie) {
			jabxLog.outputInfoAndLog("ZJABXResultThread.run()", ie.getMessage());
		}
		jabxLog.outputInfoAndLog("ZJABXResultThread.run()", "Stop running");
	}

	private int processAllData() {
		int nErrCode = JABXErrCode.NO_ERROR;
		while (isRunFlag) {
			if (streamPktQue.isEmpty()) {
				break;
			}
			nErrCode = processData();
			if (nErrCode != JABXErrCode.NO_ERROR) {
				break;
			}
		}
		return nErrCode;
	}

	/**
	 * Socket封包及數據異常處理
	 * return int
	 */
	private int processData() {
		int nErrCode = JABXErrCode.NO_ERROR;
		JSONObject sourceResult = null;
		byte[] dataAry;
		try {
			ZJABXPacket streamPkt = takeStreamPkt();
			
			if (streamPkt.getType() == 1) {// 類型為1，代表讀取過程異常，直接decode返回
				return nErrCode;
			}
			sourceResult = streamPkt.getResult();
			dataAry = streamPkt.getData();
			int nRead = 0;// 已讀取數據之byte數
			while (nRead < dataAry.length) {// 解析Socket Header及Body
				if (ctrlHeader == null) {// 先取得Header
					if (headerAry == null) {// 若headerAry為空，代表尚未讀取任何Header數據
						nReadHeader = 0;// 初始已讀取Header之byte數
						headerAry = new byte[JABXConst.CTRL_HEADER_SIZE];// new一塊儲放Header之記憶體
					}

					int unRead = dataAry.length - nRead;// 未讀取之數據byte數
					int unReadHeader = headerAry.length - nReadHeader;// 尚未讀取之Header byte數
					if (unRead < unReadHeader) {// Header無法讀取完成
						// 複製數據到headerAry中
						System.arraycopy(dataAry, nRead, headerAry, nReadHeader, unRead);
						nRead += unRead;// 設定已讀取數據byte數
						nReadHeader += unRead;// 設定已讀取Control Header之byte數
					}else {// Control Header可讀取完成
						// 複製數據到headerAry中
						System.arraycopy(dataAry, nRead, headerAry, nReadHeader, unReadHeader);
						nRead += unReadHeader;// 設定已讀取數據byte數
						ctrlHeader = getCtrlHeader(headerAry, jabxLog);// 取得Control Header
						headerAry = null;
						bodyAry = null;
						cmpHeaderAry = null;
					}
					if (ctrlHeader != null) {
						if (ctrlHeader.getByCompressType() != JABXConst.ABUS_COMPRESS_NULL) {
							isReadCmpHeader = true;
						}
					}else {
						isReadCmpHeader = false;
					}
				}else {
					if (bodyAry == null) {// 若Body為null
						if (isReadCmpHeader) {// 讀取Compress Header數據
							if (cmpHeaderAry == null) {
								nReadCmpHeader = 0;
								isFullCmpHeader = false;
								cmpHeaderAry = new byte[CMP_HEADER_LENGTH];
								int unReadCmpHeader = CMP_HEADER_LENGTH;
								int unRead = dataAry.length - nRead;
								if (unRead < unReadCmpHeader) {// Compress Header無法讀取完成
									// 複製數據到cmpHeaderAry中
									System.arraycopy(dataAry, nRead, cmpHeaderAry, 0, unRead);
									nRead += unRead;// 設定已讀取數據byte數
									nReadCmpHeader = unRead;// 設定已讀取Compress Header byte數
								}else {// Compress Header可讀取完成
									// 複製數據到cmpHeaderAry中
									System.arraycopy(dataAry, nRead, cmpHeaderAry, 0, cmpHeaderAry.length);
									nRead += cmpHeaderAry.length;// 設定已讀取數據byte數
									isFullCmpHeader = true;// Compress Header已讀取完成
								}
							}else {
								if (!isFullCmpHeader) {
									// 尚未讀取之Compress Header byte數
									int unReadCmpHeader = CMP_HEADER_LENGTH - cmpHeaderAry.length;
									int unRead = dataAry.length - nRead;// 尚未讀取之數據長度
									if (unRead < unReadCmpHeader) {// Compress Header無法讀取完成
										// 複製數據到cmpHeaderAry中
										System.arraycopy(dataAry, nRead, cmpHeaderAry, nReadCmpHeader, unRead);
										nRead += unRead;// 設定已讀取數據byte數
										nReadCmpHeader += unRead;// 設定已讀取Compress Header byte數
									}else {// Compress Header可讀取完成
										// 複製數據到cmpHeaderAry中
										System.arraycopy(dataAry, nRead, cmpHeaderAry, nReadCmpHeader, unReadCmpHeader);
										nRead += unReadCmpHeader;// 設定已讀取數據byte數
										isFullCmpHeader = true;// Compress Header已讀取完成
									}
								}
							}
							if (isFullCmpHeader) {
								isReadCmpHeader = false;
							}
							continue;
						}
						isFullBody = false;// Body已讀取完成旗標
						nReadBody = 0;// 初始化已讀取Body之byte數
						if (ctrlHeader.getByCompressType() != JABXConst.ABUS_COMPRESS_NULL) {
							bodyLength = ctrlHeader.getDwDataLen() - CMP_HEADER_LENGTH;
						}else {
							bodyLength = ctrlHeader.getDwDataLen();
						}
						if (bodyLength <= 0) {
							bodyAry = new byte[0];
							isFullBody = true;
						}else {
							bodyAry = new byte[bodyLength];// new一塊儲放Body之記憶體
							int unReadBody = bodyLength;// 尚未讀取之Body byte數
							int unRead = dataAry.length - nRead;// 尚未讀取之數據長度
							if (unRead < unReadBody) {// Body無法讀取完成
								// 複製數據到bodyAry中
								System.arraycopy(dataAry, nRead, bodyAry, 0, unRead);
								nRead += unRead;// 設定已讀取數據byte數
								nReadBody = unRead;// 設定已讀取Body byte數
							}else {// Body可讀取完成
								// 複製數據到bodyAry中
								System.arraycopy(dataAry, nRead, bodyAry, 0, bodyAry.length);
								nRead += bodyAry.length;// 設定已讀取數據byte數
								isFullBody = true;// Body已讀取完成
							}
						}
					}else {
						if (!isFullBody) {// 若Body尚未讀取完成
							int unReadBody = bodyLength - nReadBody;// 尚未讀取之Body byte數
							int unRead = dataAry.length - nRead;// 尚未讀取之數據長度
							if (unRead < unReadBody) {// Body無法讀取完成
								// 複製數據到bodyAry中
								System.arraycopy(dataAry, nRead, bodyAry, nReadBody, unRead);
								nRead += unRead;// 設定已讀取數據byte數
								nReadBody += unRead;// 設定已讀取Body byte數
							}else {// Body可讀取完成
								// 複製數據到bodyAry中
								System.arraycopy(dataAry, nRead, bodyAry, nReadBody, unReadBody);
								nRead += unReadBody;// 設定已讀取數據byte數
								isFullBody = true;// Body已讀取完成
							}
						}else {// Body已讀取完成
							byte svCheckSum = dataAry[nRead];// 讀取Socket之CheckSum
							nRead++;// 設定已讀取數據byte數
							// 計算已讀取數據之CheckSum
							byte calcCheckSum;
							if (ctrlHeader.getByCompressType() != JABXConst.ABUS_COMPRESS_NULL) {
								calcCheckSum = calcCheckSum(ctrlHeader.calcCheckSum(), cmpHeaderAry);
								calcCheckSum = calcCheckSum(calcCheckSum, bodyAry);
							}else {								
								calcCheckSum = calcCheckSum(ctrlHeader.calcCheckSum(), bodyAry);
							}

							infoSb.delete(0, infoSb.length());
							infoSb.append("M:").append(ctrlHeader.getByMsgMainType()).append(",");
							infoSb.append("S:").append(ctrlHeader.getByMsgSubType()).append(",");
							infoSb.append("L:").append(ctrlHeader.getDwDataLen()).append(",");
							if (jabxKernel.getApiEdition() == '0') {
								jabxLog.outputInfoAndLog("ZJABXResultProc.processData()", infoSb.toString());
							}
							if (svCheckSum == calcCheckSum) {// CheckSum相同
								ZJABXPacket pkt = new ZJABXPacket();
								pkt.setResult(sourceResult);
								pkt.setCtrlHeader(ctrlHeader);
								pkt.setCompressHeader(cmpHeaderAry);
								pkt.setData(bodyAry);
								pkt.setType((byte)0);
								parseCode.parse(pkt);
							}else {// CheckSum不同
								infoSb.delete(0, infoSb.length());
								infoSb.append("SVCheckSum:").append(svCheckSum).append(",");
								infoSb.append("CheckSum:").append(calcCheckSum).append(",");
								infoSb.append("CmpType:").append(ctrlHeader.getByCompressType());
								jabxLog.outputErrorAndLog("ZJABXResultProc.processData()", infoSb.toString());
							}
							ctrlHeader = null;
						}
					}
				}
			}
		}catch(IOException e) {
			nErrCode = JABXErrCode.IO_ERROR;
			jabxLog.outputRealtimeMsg("ZJABXResultProc.processData()", e.getMessage());
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputRealtimeMsg("ZJABXResultProc.processData()", e.getMessage());
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputRealtimeMsg("ZJABXResultProc.processData()", e.getMessage());
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputRealtimeMsg("ZJABXResultProc.processData()", e.getMessage());
		}
		dataAry = null;

		if (nErrCode != JABXErrCode.NO_ERROR) {
			JSONObject result = new JSONObject();
			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_SESSION);
			result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_SOCKET_PROCESS);
			if (sourceResult != null) {
				result.put(JS_Result.IP_PORT, sourceResult.get(JS_Result.IP_PORT));
			}
			result.put(JS_Result.ERR_CODE, nErrCode);
			result.put(JS_Result.NOTIFIED, true);

			ZJABXPacket pkt = new ZJABXPacket();
			pkt.setType((byte)1);
			pkt.setResult(result);

			parseCode.parse(pkt);
			
			pkt = null;
			result = null;
		}

		return nErrCode;
	}

	/**
	 * 放ZJABXPacket物件至streamPktQue中
	 * @param pkt - ZJABXPacket
	 */	
	public void putStreamPkt(ZJABXPacket pkt) {
		synchronized(threadLock) {
		try {
			streamPktQue.put(pkt);
			threadLock.notifyAll();
		}catch(InterruptedException ie) {
			jabxLog.outputInfoAndLog("ZJABXResultThread.putStream()", ie.toString());
		}
		}
	}
		
	/**
	 * 自streamPktQue中取出一ZJABXPacket物件
	 * @return ZJABXPacket
	 */	
	private ZJABXPacket takeStreamPkt() throws InterruptedException {
		try {
			return streamPktQue.take();
		}catch(InterruptedException ie){
			jabxLog.outputInfoAndLog("ZJABXResultThread.takeStream()", ie.toString());
		    throw ie;
		}
	}	

	/**
	 * 清除streamPktQue中之所有物件
	 * @throws InterruptedException
	 */
	public void removeAllStreamPkt() throws InterruptedException {
		streamPktQue.clear();
	}

	/**
	 * 執行緒是否執行(true.執行,false.不執行)
	 * @return boolean
	 */
	public boolean isRunFlag() {
		return isRunFlag;
	}

	/**
	 * 設定執行緒是否執行(true.執行,false.不執行)
	 * @param isRunFlag - boolean
	 */
	public void setRunFlag(boolean isRunFlag) {
		this.isRunFlag = isRunFlag;
		if (!isRunFlag) {
			synchronized(threadLock) {
				threadLock.notifyAll();
			}
		}
	}
}