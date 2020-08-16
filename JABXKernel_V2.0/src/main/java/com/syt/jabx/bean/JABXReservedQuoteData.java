package com.syt.jabx.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.syt.jabx.bean.JABXQuoteDataInfo;

/**
 * 保留訂閱即時資料之類別
 * @author jason
 *
 */
public class JABXReservedQuoteData {

	/**
	 * 保存訂閱即時資料之Map物件
	 */
	private Map<String, Map<String, JABXQuoteDataInfo>> subscribeDataMap;

	/**
	 * subscribeDataMap所使用之Lock
	 */
	private byte[] mapLock = new byte[0];

	/**
	 * Constructor
	 */
	public JABXReservedQuoteData() {
		subscribeDataMap = new HashMap<String, Map<String, JABXQuoteDataInfo>>();
	}

	/**
	 * 將要保留之訂閱即時資料放入Map中
	 * @param key - String(訂閱分類鍵值)
	 * @param quoteMap - Map&lt;String, JABXQuoteDataInfo&gt;
	 */
	public void putSubscribeData(String key, Map<String, JABXQuoteDataInfo> quoteMap) {
		synchronized(mapLock) {
			this.subscribeDataMap.put(key, quoteMap);
		}
	}

	/**
	 * 自保留之訂閱資料中移除鍵值為key之數據
	 * @param key - StringString(訂閱分類鍵值)
	 */
	public void removeSubscribeData(String key) {
		synchronized(mapLock) {
			this.subscribeDataMap.remove(key);
		}
	}

	public Map<String, JABXQuoteDataInfo> getMergeDataInfo() {
		synchronized(mapLock) {
			Map<String, JABXQuoteDataInfo> mergeDataMap = new HashMap<String, JABXQuoteDataInfo>();
			
			Set<String> keySet = this.subscribeDataMap.keySet();
			Iterator<String> it = keySet.iterator();
			String key;
			Map<String, JABXQuoteDataInfo> value;

			Set<String> contentKeySet;
			Iterator<String> contentIt;
			String idAndPort;
			JABXQuoteDataInfo contentValue, mergeValue;

			while(it.hasNext()) {
				key = it.next();
				value = this.subscribeDataMap.get(key);
				contentKeySet = value.keySet();
				contentIt = contentKeySet.iterator();
				while(contentIt.hasNext()) {
					idAndPort = contentIt.next();
					contentValue = value.get(idAndPort);
					
					mergeValue = mergeDataMap.get(idAndPort);
					if (mergeValue == null) {
						mergeValue = new JABXQuoteDataInfo();
					}
					mergeValue.addCount(contentValue.getDataCount());
					mergeValue.addFixDataList(contentValue.getFixDataList());
					mergeDataMap.put(idAndPort, mergeValue);
				}
			}
			
			return mergeDataMap;
		}		
	}
}
