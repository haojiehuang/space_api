package com.syt.jabx.kernel;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXFixData;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析Control Header之Abstract Class
 * @author Jason
 *
 */
public abstract class IJABXParseBase extends ZIJABXInfoBase {

	/**
	 * 當壓縮格式不為ABUS_COMPRESS_NULL時之標題byte長度
	 */
	protected final static int CMP_HEADER_LENGTH = 32;

	/**
	 * getBytesFromListFixData()之Lock
	 */
	private byte[] getBytesFromListFixDataLock = new byte[0];

	/**
	 * 計算CheckSum所使用之Lock
	 */
	private byte[] checkSumLock = new byte[0];

	/**
	 * 串接Log用之StringBuffer物件
	 */
	protected StringBuffer logSb = new StringBuffer();

	/**
	 * 解析Socket Header數據後，轉成ZJABXCtrlHeader物件
	 * @param abyAry - byte[]
	 * @param jabxLog - IJABXLog
	 * @return JABXCtrlHeader
	 * @throws IOException -
	 */
	public JABXCtrlHeader getCtrlHeader(byte[] abyAry, final IJABXLog jabxLog) throws IOException {
		JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();

		DataInputStream istm = new DataInputStream(new ByteArrayInputStream(abyAry));
		istm.readByte();//1byte(起始碼)
		ctrlHeader.setLlSeqNo(istm.readLong());//8bytes(序號)
		ctrlHeader.setByMsgMainType(istm.readByte());//1byte(訊息主代碼)
		ctrlHeader.setByMsgSubType(istm.readInt());//4bytes(訊息次代碼)

		//500-01-Begin: 讀取AbyKey數據
		byte[] bAry;
		switch (ctrlHeader.getByMsgMainType()) {
		case JABXConst.ABUS_MAINTYPE_WATCHLIST:
		case JABXConst.ABUS_MAINTYPE_SMART:
		case JABXConst.ABUS_MAINTYPE_FREEFORMAT:
		case JABXConst.ABUS_MAINTYPE_TABLE:
			ZJABXAbyKeyReal abyKeyReal = new ZJABXAbyKeyReal();
			bAry = new byte[22];
			istm.read(bAry);//22bytes(證券代碼)
			abyKeyReal.setAbyCode(new String(bAry).trim());
			ctrlHeader.setAbyKey(abyKeyReal);
			abyKeyReal = null;
			break;
		default:
			JABXAbyKeyCtrl abyKeyCtrl = new JABXAbyKeyCtrl();
			bAry = new byte[4];
			istm.read(bAry);//4 bytes(功能代碼)
			abyKeyCtrl.setAbyFuncCode(new String(bAry).trim());
			abyKeyCtrl.setDwUserGWID(istm.readInt());//4 bytes(用戶GW帳號)
			abyKeyCtrl.setByProductID(istm.readByte());//1 byte(產品代碼)
			abyKeyCtrl.setByPlatformID(istm.readByte());//1 byte(平台代碼)
			abyKeyCtrl.setByChannelNo(istm.readByte());//1 byte(GW連線序號)
			abyKeyCtrl.setBySecurityKey(istm.readByte());//1 byte(安全碼)
			bAry = new byte[JABXConst.RESERVE_LENGTH];
			istm.read(bAry);//8 bytes(保留)
			abyKeyCtrl.setByAPCode(istm.readShort());//2 bytes(AP識別碼)
			ctrlHeader.setAbyKey(abyKeyCtrl);

			abyKeyCtrl = null;
		}
		bAry = null;
		//500-01-End.

		ctrlHeader.setByCompressType(istm.readByte());//1 byte(資料壓縮方式)
		ctrlHeader.setDwDataLen(istm.readInt());//4 bytes(資料長度)

		istm.close();
		istm = null;

		boolean isOutputRealtime = false;
		int mainType = ctrlHeader.getByMsgMainType();
		int subType = ctrlHeader.getByMsgSubType();
		
		switch(mainType) {
		case JABXConst.ABUS_MAINTYPE_WATCHLIST:
		case JABXConst.ABUS_MAINTYPE_FREEFORMAT:
		case JABXConst.ABUS_MAINTYPE_SMART:
		case JABXConst.ABUS_MAINTYPE_TABLE:
			isOutputRealtime = true;
			break;
		case JABXConst.ABUS_MAINTYPE_CONTROL:
			if (subType == JABXConst.ABUS_CONTROL_HEARTBIT) {
				isOutputRealtime = true;
			}
			break;
		}
		
		if (isOutputRealtime) {
			jabxLog.outputRealtimeMsg("IJABXParseBase.getCtrlHeader()", ctrlHeader.toString());
		}else {
			jabxLog.outputInfoAndLog("IJABXParseBase.getCtrlHeader()", ctrlHeader.toString());
		}

		return ctrlHeader;
	}

	/**
	 * 依功能別,requestID及Server物件設定ZJABXCtrlHeader物件
	 * @param mainType - byte(訊息主代碼)
	 * @param subType - int(訊息次代碼)
	 * @param funcCode - String
	 * @param requestID - int
	 * @param siObj - ZJABXServerItem
	 * @param jabxKernel - JABXKernel
	 * @return ZJABXCtrlHeader
	 */
	public JABXCtrlHeader getCtrlHeader(byte mainType, int subType, String funcCode, int requestID, ZJABXServerItem siObj, JABXKernel jabxKernel) {
		JABXCtrlHeader ctrlHeader = new JABXCtrlHeader();

		// 設定訊息主代碼
		ctrlHeader.setByMsgMainType(mainType);
		// 設定訊息次代碼
		ctrlHeader.setByMsgSubType(subType);
		// 設定abyKey
		ctrlHeader.setAbyKey(getAbyKey(siObj.getChannelNo(), funcCode, requestID, siObj.getSecurityKey(), jabxKernel.getLoginInfo()));
		// 設定資料壓縮方式
		ctrlHeader.setByCompressType(JABXConst.ABX_COMPRESS_NULL);

		return ctrlHeader;
	}

	/**
	 * 數據解壓縮
	 * @param cmpType - int(壓縮格式)
	 * @param abyBuf - byte[](壓縮數據)
	 * @param cmpSize - int(壓縮數據之長度)
	 * @param size - int(原數據之長度)
	 * @return byte[]
	 * @throws IOException -
	 */
	public byte[] decompressBuffer(int cmpType, byte[] abyBuf, int cmpSize, int size) throws IOException {
		byte[][] abyBufAry = new byte[1][];
		
		if (cmpType == JABXConst.ABUS_COMPRESS_HUFFMAN) {
			new ZJABXHuffmanCmp(abyBuf, cmpSize, size, abyBufAry); 
		}else if (cmpType == JABXConst.ABUS_COMPRESS_NULL) {
			abyBufAry[0] = abyBuf;
		}else if (cmpType == JABXConst.ABUS_COMPRESS_GZIP) {
			GZIPInputStream gzipIn = new GZIPInputStream(new BufferedInputStream(new ByteArrayInputStream(abyBuf)));		
			List<Byte> byteList = new ArrayList<Byte>();
			while (gzipIn.available() == 1) {
				byteList.add((byte)gzipIn.read());
			}
			gzipIn.close();
			gzipIn = null;

			abyBufAry[0] = new byte[byteList.size()];
			for (int i = 0;i < abyBufAry[0].length;i++) {
				abyBufAry[0][i] = byteList.get(i);
			}
			byteList = null;
		}else {
			abyBufAry[0] = null;
		}

		return abyBufAry[0];
	}

	/**
	  * 處理Compress Header
	 * @param compressHeader - byte[]
	 * @param log - IJABXLog
	 * @return ZJABXCompressHeader
	 * @throws IOException -
	 * @throws OutOfMemoryError -
	 */
	public ZJABXCompressHeader processCompressHeader(final byte[] compressHeader, final IJABXLog log) 
			throws IOException, OutOfMemoryError {
		DataInputStream input = new DataInputStream(new ByteArrayInputStream(compressHeader));
		int nErrCode = JABXErrCode.NO_ERROR;
		int unCompressLength = 0;
		try {
			nErrCode = input.readInt();
			unCompressLength = input.readInt();
		}catch(IOException e) {
			nErrCode = JABXErrCode.IO_ERROR;
			log.outputErrorAndLog("ZAJABXParseBase.processCompressHeader()", e.getMessage());
			throw e;
		}catch(OutOfMemoryError e) {
			nErrCode = JABXErrCode.OUTOFMEMORY_ERROR;
			log.outputErrorAndLog("ZAJABXParseBase.processCompressHeader()", e.getMessage());
			throw e;
		}finally {
			try {
				input.close();
			}catch(Exception e) {
				log.outputErrorAndLog("ZAJABXParseBase.processCompressHeader()", e.getMessage());
			}
		}

		if (nErrCode != JABXErrCode.NO_ERROR) {
			return null;
		}

		ZJABXCompressHeader cmHeader = new ZJABXCompressHeader();
		cmHeader.setErrorCode(nErrCode);
		cmHeader.setUnCompressLength(unCompressLength);
		cmHeader.setReserve("");

		return cmHeader;
	}

	/**
	 * 將負數之byte值轉為正值
	 * @param b - byte
	 * @return int
	 */
	public int toUsignByte(byte b) {
		return b >= 0 ? b : b + 256;
	}

	/**
	 * 將負數之short值轉為正值
	 * @param s - short
	 * @return int
	 */
	public int toUsignShort(short s) {
		return s >= 0 ? s : s + 65536;
	}

	/**
	 * 將字串(src)以字元(fillChar)補滿為length長度之字串
	 * @param fillChar - char(要補滿的字元)
	 * @param src - String(原始字串)
	 * @param length - int(要補滿的長度)
	 * @return String
	 */
	public String fillLeftData(char fillChar, String src, int length) {
		if (src.length() > length) {
			return src;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = src.length();i < length;i++) {
			sb.append(fillChar);
		}
		sb.append(src);
		return sb.toString();
	}

	/**
	 * CheckSum運算
	 * @param b - byte(Source)
	 * @param bAry - byte[]
	 * @return byte
	 */
	public byte calcCheckSum(byte b, byte[] bAry) {
		synchronized(checkSumLock) {
			if (bAry != null) {
				byte tmp = b;
				for (int i = 0;i < bAry.length;i++) {
					tmp ^= bAry[i];
				}
				return tmp;
			}else {
				return b;
			}
		}
	}

	/**
	 * 取得list中組成Fix格式後之byte[]
	 * @param list - List&lt;ZJABXFixData&gt;
	 * @return byte[]
	 */
	public byte[] getBytesFromListFixData(final List<JABXFixData> list) {
		
		synchronized(getBytesFromListFixDataLock) {
			if (list == null || list.size() == 0) {
				return new byte[0];
			}
			StringBuffer sb = new StringBuffer();
	
			JABXFixData fixData;
			for (int i = 0, size = list.size();i < size;i++) {
				fixData = list.get(i);
				sb.append(fixData.getTag());
				sb.append('=');
				if (fixData.getCharSet() != null && !fixData.getCharSet().equals("")) {
					String value;
					try {
						value = new String(fixData.getValue().getBytes(fixData.getCharSet()));
					}catch(Exception e) {
						value = fixData.getValue();
					}
					sb.append(value);
				}else {
					sb.append(fixData.getValue());
				}
				sb.append('\u0001');
			}
		
			return sb.toString().getBytes();
		}
	}

	/**
	 * 解析多筆之回補數據中之某一筆數據
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 * @param parseFactory - IJABXParseFactory
	 * @param fixProc - JABXFixProc
	 * @param result - JSONObject
	 * @param rebuildAry - byte[]
	 * @param map - Map&lt;String, IJABXParseBody&gt;
	 * @param srcObj - Object
	 * @throws IOException -
	 */
	public void parseRebuildCountData(final JABXKernel jabxKernel, 
			final IJABXLog jabxLog, 
			final IJABXParseFactory parseFactory,
			final ZJABXFixProc fixProc, 
			final JSONObject result, final byte[] rebuildAry, 
			Map<String, IJABXParseBody> map, 
			Object srcObj) throws IOException {	
		
		byte[] headerAry, bodyAry;
		headerAry = new byte[JABXConst.CTRL_HEADER_SIZE];//訊息內容之Control Header
		System.arraycopy(rebuildAry, 0, headerAry, 0, JABXConst.CTRL_HEADER_SIZE);
		JABXCtrlHeader subHeader = getCtrlHeader(headerAry, jabxLog);//Control Header
		
		byte cmpType = subHeader.getByCompressType();
		bodyAry = null;
		if (cmpType != JABXConst.ABUS_COMPRESS_NULL) {//當資料壓縮格式不為ABUS_COMPRESS_NULL時
			//處理Compress Header
			byte[] tmpAry = new byte[JABXConst.COMPRESS_HEADER_LENGTH];
			System.arraycopy(rebuildAry, 0, tmpAry, 0, tmpAry.length);
			ZJABXCompressHeader cmHeader = processCompressHeader(tmpAry, jabxLog);
			if (cmHeader != null) {//數據正常
				int bodyLength = subHeader.getDwDataLen() - JABXConst.COMPRESS_HEADER_LENGTH;
				tmpAry = new byte[bodyLength];
				System.arraycopy(rebuildAry, JABXConst.COMPRESS_HEADER_LENGTH, tmpAry, 0, tmpAry.length);
				int unCompressLength = cmHeader.getUnCompressLength();
				//解壓縮
				bodyAry = decompressBuffer(cmpType, tmpAry, bodyLength, unCompressLength);
				tmpAry = null;
			}
		}else {
			bodyAry = rebuildAry;
		}
		String key = String.format("%d-%d", subHeader.getByMsgMainType(), subHeader.getByMsgSubType());
		IJABXParseBody parseBody = map.get(key);
		if (parseBody == null) {
			parseBody = parseFactory.getParseBody(jabxKernel, jabxLog, subHeader, srcObj);
			if (parseBody != null) {
				map.put(key, parseBody);
			}
		}

		if (parseBody != null) {
			parseBody.parse(fixProc, result, bodyAry, subHeader, JABXConst.CTRL_HEADER_SIZE);
		}
		parseBody = null;
		
		subHeader = null;
		headerAry = null;
		bodyAry = null;
	}

	/**
	 * 取得ZIJABXAbyKey
	 * @param channelNo - byte(ChannelNo)
	 * @param funcCode - String(功能代碼)
	 * @param requestID - int(API回覆碼)
	 * @param securityKey - int(安全碼)
	 * @param loginInfo - JSConst
	 * @return ZIJABXAbyKey
	 */
	public IJABXAbyKey getAbyKey(byte channelNo, String funcCode, int requestID, int securityKey, JSONObject loginInfo) {

		JABXAbyKeyCtrl abyKey = new JABXAbyKeyCtrl();

		abyKey.setAbyFuncCode(funcCode);
		abyKey.setDwUserGWID(loginInfo.getInt(JSConst.LN_USERGWID));
		abyKey.setByProductID((byte)loginInfo.getInt(JSConst.LN_PRODUCTID));
		abyKey.setByPlatformID((byte)loginInfo.getInt(JSConst.LN_PLATFORMID));
		abyKey.setByChannelNo(channelNo);
		abyKey.setBySecurityKey((byte)securityKey);
		abyKey.setByAPCode((short)requestID);

		return abyKey;
	}

	/**
	 * 添加一系統通知訊息之ZJABXPacket至resultProc中
	 * @param jabxKernel - JABXKernel
	 * @param funcID - int(功能代碼)
	 * @param statusID - int(狀態碼)
	 * @param ipAndPort - String(ip:port)
	 * @param errorCode - int(錯誤碼)
	 */
	public void addInformationPacket(final JABXKernel jabxKernel, int funcID, int statusID, String ipAndPort, int errorCode) {
		if (jabxKernel == null || jabxKernel.getResultProc() == null) {
			return;
		}
		JSONObject result = new JSONObject();
		result.put(JS_Result.FUNC_ID, funcID);
		result.put(JS_Result.STATUS_ID, statusID);
		result.put(JS_Result.IP_PORT, ipAndPort);
		result.put(JS_Result.ERR_CODE, errorCode);
		result.put(JS_Result.NOTIFIED, true);

		ZJABXPacket pkt = new ZJABXPacket();
		pkt.setType((byte)1);
		pkt.setResult(result);
		
		jabxKernel.getResultProc().putStreamPkt(pkt);
		
		pkt = null;
		result = null;
	}
}
