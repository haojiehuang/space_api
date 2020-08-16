package com.syt.jabx.kernel;

import java.io.IOException;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXSocketParam;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 處理Gateway Session之類別(非同步)<br/>
 * @author Jason
 *
 */
final class ZJABXChannelSocket extends IJABXParseBase implements Runnable {

	/**
	 * 管理股票即時報價及歷史數據下載之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * 連線GW之Server
	 */
	private ZJABXServerItem gwServer;

	/**
	 * 封包解析之物件
	 */
	private ZJABXParseCode parseCode;

	/**
	 * 保存Socket傳送參數之物件
	 */
	private JABXSocketParam sparam;
	
	/**
	 * Constructor
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 * @param gwServer - ZJABXServerItem
	 * @param sparam - ZJABXSocketParam
	 */
	public ZJABXChannelSocket(JABXKernel jabxKernel, IJABXLog jabxLog, ZJABXServerItem gwServer, JABXSocketParam sparam) {
		this.jabxLog = jabxLog;
		this.gwServer = gwServer;
		this.parseCode = jabxKernel.getParseCode();
		this.sparam = sparam;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		socketProcess(sparam);
		jabxLog.outputInfoAndLog("ZJABXChannelSocket.run()", String.format("Host:Port %s->Stop running", gwServer.getHostPort()));
	}

	/**
	 * Socket數據處理
	 * @param sparam - ZJABXSocketParam
	 */
	private void socketProcess(final JABXSocketParam sparam) {
		int nErrCode = JABXErrCode.NO_ERROR;		
		try {
			ZJABXSocket socket = new ZJABXSocket(jabxLog, gwServer);
			socket.connect();
			socket.outputData(sparam);

			byte[] headerAry = new byte[JABXConst.CTRL_HEADER_SIZE];//new一塊儲放Header之記憶體
			socket.readFully(headerAry);
			JABXCtrlHeader ctrlHeader = getCtrlHeader(headerAry, jabxLog);
			headerAry = null;

			byte[] bodyAry, cmpHeaderAry = null;
			int bodyLength;
			byte calcCheckSum = ctrlHeader.calcCheckSum();
			if (ctrlHeader.getByCompressType() != JABXConst.ABUS_COMPRESS_NULL) {
				bodyLength = ctrlHeader.getDwDataLen() - CMP_HEADER_LENGTH;
				cmpHeaderAry = new byte[CMP_HEADER_LENGTH];
				socket.readFully(cmpHeaderAry);
				calcCheckSum = calcCheckSum(calcCheckSum, cmpHeaderAry);
			}else {
				bodyLength = ctrlHeader.getDwDataLen();
			}
			bodyAry = new byte[bodyLength];//new一塊儲放Body之記憶體
			socket.readFully(bodyAry);
			calcCheckSum = calcCheckSum(calcCheckSum, bodyAry);
			byte svCheckSum = socket.readByte();
			if (svCheckSum == calcCheckSum) {//CheckSum相同
				ZJABXPacket pkt = new ZJABXPacket();
				pkt.setCtrlHeader(ctrlHeader);
				JSONObject result = new JSONObject();
				result.put(JS_Result.IP_PORT, gwServer.getIPAndPort());
				pkt.setResult(result);
				pkt.setCompressHeader(cmpHeaderAry);
				pkt.setData(bodyAry);
				pkt.setType((byte)0);
				parseCode.parse(pkt);
				result = null;
			}else {//CheckSum不同
				//zzzzzzzzzz
			}
			socket.closeStreamAndSocket();
		}catch(IOException e) {
			nErrCode = JABXErrCode.IO_ERROR;
			jabxLog.outputErrorAndLog("ZJABXChannelSocket.socketProcess()", e.getMessage());
			e.printStackTrace();
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			jabxLog.outputErrorAndLog("ZJABXChannelSocket.socketProcess()", e.getMessage());
			e.printStackTrace();
		}catch(NullPointerException e) {
			nErrCode = JABXErrCode.NULL_POINTER;
			jabxLog.outputErrorAndLog("ZJABXChannelSocket.socketProcess()", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			nErrCode = JABXErrCode.UNKNOWN_ERROR;
			jabxLog.outputErrorAndLog("ZJABXChannelSocket.socketProcess()", e.getMessage());
			e.printStackTrace();
		}
		if (nErrCode != JABXErrCode.NO_ERROR) {
			JSONObject result = new JSONObject();
			result.put(JS_Result.IP_PORT, gwServer.getIPAndPort());
			result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_SESSION);
			result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_SOCKET_PROCESS);
			result.put(JS_Result.ERR_CODE, nErrCode);
			result.put(JS_Result.NOTIFIED, true);

			ZJABXPacket pkt = new ZJABXPacket();
			pkt.setType((byte)1);
			pkt.setResult(result);
			
			parseCode.parse(pkt);
			
			pkt = null;
			result = null;			
		}		
	}

}
