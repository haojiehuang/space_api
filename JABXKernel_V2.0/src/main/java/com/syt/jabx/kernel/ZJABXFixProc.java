package com.syt.jabx.kernel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.notify.IJABXLog;

/**
 * 處理Fix數據之類別(API內部使用)
 * @author Jason
 *
 */
final class ZJABXFixProc {

	private IJABXLog jabxLog;

	private List<Byte> tagList;
	private List<Byte> contentList;

	/**
	 * Constructor
	 * @param log - IJABXLog
	 */
	public ZJABXFixProc(IJABXLog log) {
		this.jabxLog = log;
		tagList = new ArrayList<Byte>();
		contentList = new ArrayList<Byte>();
	}
	
	/**
	 * 自abyVal中讀取數據，組成一JABXTagValue物件，設定到tagValue中<br/>
	 * 若length為0,則從offset讀至字元0x01<br/>
	 * 若length不為0，則從offset讀取legnth長度字元
	 * @param abyVal - byte[]
	 * @param offset - int(偏移量)
	 * @param length - int(讀取長度)
	 * @param tagValue - JABXTagValue
	 * @return int(已讀取byte數)
	 * @throws JABXOutOfMemoryError
	 */
	public int readOneFixField(final byte[] abyVal, final int offset, final int length, final JABXTagValue tagValue) throws OutOfMemoryError {
		if (abyVal == null || tagValue == null) {
			return 0;
		}
		
		int nRead = 0;
		try {
			tagList.clear();
			contentList.clear();
			
			boolean isTag = true;
			int tag = 0;
			Byte iResult;
			for (int i = offset;i < abyVal.length;i++) {
				nRead++;
				iResult = new Byte(abyVal[i]);
				if (isTag) {// tag
					if ((char)iResult.byteValue() == '=') {
						isTag = false;
						try {
							tag = Integer.parseInt(byteListToString(tagList, ""));
						}catch(NumberFormatException e) {// 解決最後一碼為CheckSum為=時，Tag無法轉換的問題
							tag = Integer.MIN_VALUE;
						}
					}else {
						tagList.add(iResult);
					}
				}else {// content
					if (length == 0) {// length為0時之處理
						if (iResult == 0x01) {// 0x01代表內容結束
							tagValue.setTag(tag);
							tagValue.setValue(byteListToByteAry(contentList));// 設定內容
							break;
						}
					}else {// length不為0時之處理
						if (nRead == length) {// 已讀取length長度之數據
							if (iResult != 0x01) {// 若length長度最後一個字元不為0x01，則將字元加入contentList中
								contentList.add(iResult);
							}
							tagValue.setTag(tag);
							tagValue.setValue(byteListToByteAry(contentList));// 設定內容
							break;
						}
					}
					contentList.add(iResult);
					if (i == abyVal.length - 1) {// 數據異常(已至數據之最後一個字元，但數據長度不足length時之處理)
						tagValue.setTag(tag);
						tagValue.setValue(byteListToByteAry(contentList));// 設定內容
					}
				}
			}
		}catch(OutOfMemoryError e) {
			jabxLog.outputErrorAndLog("JABXFixProc.readOneFixField()", e.getMessage());
			throw e;
		}catch(Exception e) {
			jabxLog.outputErrorAndLog("JABXFixProc.readOneFixField()", e.getMessage());
			e.printStackTrace();
		}
		return nRead;
	}

	/**
	 * 將一List&lt;Byte&gt;之數據轉換為String
	 * @param list - List&lt;Byte&gt;
	 * @param charSet - String
	 * @return String
	 * @throws JABXOutOfMemoryError
	 */
	private String byteListToString(final List<Byte> list, String charSet) throws OutOfMemoryError {
		if (list == null || list.size() == 0) {
			return "";
		}
		String target = "";
		try {
			byte[] bAry = new byte[list.size()];
			for (int i = 0;i < bAry.length;i++) {
				bAry[i] = list.get(i);
			}

			if (charSet == null || charSet.equals("")) {
				target = new String(bAry);
			}else {
				try {
					target = new String(bAry, charSet);
				}catch(Exception e) {
					target = new String(bAry);
				}
			}
			bAry = null;
		}catch(OutOfMemoryError e) {
			jabxLog.outputErrorAndLog("JABXFixProc.byteListToString()", e.getMessage());
			throw e;
		}

		return target;
	}
			
	/**
	 * 將一List(byte[])之數據轉換為byte[]
	 * @param list - List(byte[])
	 * @return byte[]
	 * @throws JABXOutOfMemoryError
	 */
	private byte[] byteListToByteAry(final List<Byte> list) throws OutOfMemoryError {
		if (list == null || list.size() == 0) {
			return new byte[0];
		}
		byte[] targetAry = null;
		try {
			targetAry = new byte[list.size()];
			for (int i = 0;i < targetAry.length;i++) {
				targetAry[i] = list.get(i);
			}
		}catch(OutOfMemoryError e) {
			jabxLog.outputErrorAndLog("JABXFixProc.byteListToByteAry()", e.getMessage());
			throw e;
		}

		return targetAry;
	}

	/**
	 * 自byte[]取得String值
	 * @param bAry - byte[]
	 * @param charSet - String(字元編碼方式)
	 * @return String
	 */
	public String getValue(byte[] bAry, String charSet) {
		if (charSet != null && !charSet.equals("")) {
			try {
				return new String(bAry, charSet);
			}catch(UnsupportedEncodingException e) {
				return new String(bAry);
			}
		}
		return new String(bAry);
	}

	/**
	 * 自byte[]取得byte值
	 * @param bAry - byte[]
	 * @return byte
	 */
	public byte getByteValue(byte[] bAry) {
		String str = new String(bAry);
		if (str.equals("")) {
			return 0;
		}
		byte b = 0;
		try {
			b = Byte.parseByte(str.trim());
		}catch(NumberFormatException e) {
		}
		return b;
	}

	/**
	 * 自byte[]取得short值
	 * @param bAry - byte[]
	 * @return short
	 */
	public short getShortValue(byte[] bAry) {
		String str = new String(bAry);
		if (str.equals("")) {
			return 0;
		}
		short s = 0;
		try {
			s = Short.parseShort(str.trim());
		}catch(NumberFormatException e) {
		}
		return s;
	}

	/**
	 * 自byte[]取得int值
	 * @param bAry - byte[]
	 * @return int
	 */
	public int getIntValue(byte[] bAry) {
		String str = new String(bAry);
		if (str.equals("")) {
			return 0;
		}
		int i = 0;
		try {
			i = Integer.parseInt(str.trim());
		}catch(NumberFormatException e) {
		}
		return i;
	}

	/**
	 * 自byte[]取得long值
	 * @param bAry - byte[]
	 * @return long
	 */
	public long getLongValue(byte[] bAry) {
		String str = new String(bAry);
		if (str.equals("")) {
			return 0;
		}
		long l = 0;
		try {
			l = Long.parseLong(str.trim());
		}catch(NumberFormatException e) {
		}
		return l;
	}

	/**
	 * 自byte[]取得double值
	 * @param bAry - byte[]
	 * @return double
	 */
	public double getDoubleValue(byte[] bAry) {
		String str = new String(bAry);
		if (str.equals("")) {
			return 0;
		}
		double d = 0;
		try {
			d = Double.parseDouble(str.trim());
		}catch(NumberFormatException e) {
		}
		return d;
	}
}
