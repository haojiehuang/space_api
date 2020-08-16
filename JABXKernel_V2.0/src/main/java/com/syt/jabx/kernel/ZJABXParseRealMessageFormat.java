package com.syt.jabx.kernel;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 解析即時訊息格式數據之類別
 * @author Jason
 *
 */
final class ZJABXParseRealMessageFormat {

	private static byte[] isImportNewsFlag = new byte[0];

	/**
	 * 取得即時訊息格式
	 * @param messageAry
	 * @return Object
	 */
	public static ZJABXRealtimeFormatItem getInstance(byte[] abyBuf) {

		if (abyBuf == null) {
			return null;
		}

		synchronized(isImportNewsFlag) {

			int nLine = 0;
			int nField = 0;
			int nPos = 0;
			int nStart = 0;
			ZJABXRealtimeFormatItem item = new ZJABXRealtimeFormatItem();
			String strVal;
			String strCharsetName = JABXConst.ABX_CHARSET;

			try {
				while (nPos < abyBuf.length) {
					if (abyBuf[nPos] == '.') {     
						if (nLine == 0) {			        
							switch (nField){
							case 0: // 訊息序號
								try {
									strVal = new String(abyBuf, nStart, nPos-nStart, strCharsetName);
									item.setSerialNo(Integer.parseInt(strVal));			        		
								}catch(UnsupportedEncodingException e) {
									e.printStackTrace();
								}							
								break;

							case 1: // 來源別
								try {
									strVal = new String(abyBuf, nStart, nPos-nStart, strCharsetName);								
									if (!strVal.equals("NULL")) {
										item.setSourceNo(strVal);
									}
								}catch(UnsupportedEncodingException e) {
									e.printStackTrace();
								} 							
								break;

							case 2: // 訊息型態
								try {
									strVal = new String(abyBuf, nStart, nPos-nStart, strCharsetName);								
									if (!strVal.equals("NULL")) {
										item.setCatalogNo(strVal);
									}
								}catch(UnsupportedEncodingException e) {
									e.printStackTrace();
								}			        	   
								break;

							case 3: // 訊息類別
								try {
									strVal = new String(abyBuf, nStart, nPos-nStart, strCharsetName);

									if (!strVal.equals("NULL")) {
										item.setMessageType(strVal);
									}
								}catch(UnsupportedEncodingException e) {
									e.printStackTrace();
								}			        	   
								break;			        	   

							case 4: // 訊息類別2
								try {
									strVal = new String(abyBuf, nStart, nPos-nStart, strCharsetName);
							
									if (!strVal.equals("NULL")) {
										item.setMessageType2(strVal);
									}
								}catch(UnsupportedEncodingException e) {
									e.printStackTrace();
								}			        	   
								break;	        	   
		        	   
							case 5: //新聞訊息內容型態
								try {
									strVal = new String(abyBuf, nStart, nPos-nStart, strCharsetName);
							
									if (!strVal.equals("NULL")) {
										item.setDataType(strVal);
									}
								}catch(UnsupportedEncodingException e) {
									e.printStackTrace();
								}			        	   
								break;       	   
		        	   
							default: // 相關連股票
								try {
									strVal = new String(abyBuf, nStart, nPos-nStart, strCharsetName);
							
									if (!strVal.equals("NULL")) {
										item.setRelationStock(strVal);
									}
								}catch(UnsupportedEncodingException e) {
									e.printStackTrace();
								}      	         	         		                  
								break;
							}

							nField++;
							nStart = nPos+1;
						}
					}else if (abyBuf[nPos] == 0x0d && abyBuf[nPos+1] == 0x0a) {
						if (nLine == 0) {
							nStart += 2;			       
						}else if (nLine == 1) {// 日期時間
							Calendar calendar = new GregorianCalendar();
							int nY, nM, nD, nH, nMt, nS;
			   
							strVal = new String(abyBuf, nStart, 2);			    	   
							nM = (Integer.parseInt(strVal));
							nStart += 3;
		    	   
							strVal = new String(abyBuf, nStart, 2);
							nD = Integer.parseInt(strVal);
							nStart += 3;

							strVal = new String(abyBuf, nStart, 4);
							nY = Integer.parseInt(strVal);
							nStart += 6;
		    	   
							strVal = new String(abyBuf, nStart, 2);
							nH = Integer.parseInt(strVal);
							nStart += 3;
		    	   
							strVal = new String(abyBuf, nStart, 2);
							nMt = Integer.parseInt(strVal);
							nStart += 3;

							strVal = new String(abyBuf, nStart, 2);
							nS = Integer.parseInt(strVal);			    	   

							calendar.set(nY, nM - 1, nD, nH, nMt, nS);
							item.setDataTime(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(calendar.getTime()));
		    	   
							nStart = nPos + 2;   
						}else if (nLine == 2) {// URL
							try {
								item.setUrl(new String(abyBuf, nStart, nPos-nStart, strCharsetName));	    				
							}catch(UnsupportedEncodingException e) {
								e.printStackTrace();
							}	
					
							nStart = nPos + 2;
						}else if (nLine == 3) {// 標題
							try {
								item.setTitle(new String(abyBuf, nStart, nPos-nStart, strCharsetName));    		    				
							}catch(UnsupportedEncodingException e) {
								e.printStackTrace();
							}	
					
							nStart = nPos + 2;
						}
		       
						nField = 0;    
						nPos++;
						nLine++;
					}

					nPos++;
				}

				strVal = null;
				strCharsetName = null;	
			}catch(Exception e) {
				e.printStackTrace();
			}
				
			return item;
		}
	}
}