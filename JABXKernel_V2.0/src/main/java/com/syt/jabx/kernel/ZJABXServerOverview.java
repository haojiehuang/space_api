package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 伺服器索引之類別
 * @author Jason
 *
 */
final class ZJABXServerOverview {

	/**
	 * 存放所有伺服器的Map物件
	 */
	private Map<String, ZJABXServerItem> itemMap;

	/**
	 * 依主機屬性為Key存放id:port的List的Map物件
	 */
	private Map<Byte, List<String>> typeIdPortMap;

	/**
	 * 依交易所為Key存放id:port的Map物件
	 */
	private Map<String, String> exchangeIdPortMap;

	/**
	 * 僅存放主機屬性為0或2的id:port的List
	 */
	private List<String> ipPortList;
	
	/**
	 * itemMap所使用之Lock
	 */
	private byte[] itemMapLock = new byte[0];

	/**
	 * idPortList所使用之Lock
	 */
	private byte[] ipPortListLock = new byte[0];

	/**
	 * Constructor
	 */
	public ZJABXServerOverview() {
		itemMap = new HashMap<String, ZJABXServerItem>();
		typeIdPortMap = new HashMap<Byte, List<String>>();
		exchangeIdPortMap = new HashMap<String, String>();
		ipPortList = new ArrayList<String>();
	}

	/**
	 * 取得Server筆數
	 * @return int
	 */
	public int getCount() {
		synchronized(ipPortListLock) {
			return ipPortList.size();
		}
	}

	/**
	 * 取得在索引index之伺服器物件
	 * @param index - int
	 * @return ZJABXServerItem
	 */
	public ZJABXServerItem atIndex(int index) {
		synchronized(ipPortListLock) {
			if (index < ipPortList.size()) {
				return atIpAndPort(ipPortList.get(index));
			}else {
				return null;
			}
		}
	}

	/**
	 * 依主機及埠號取得伺服器物件
	 * @param ipAndPort - String
	 * @return ZJABXServerItem
	 */
	public ZJABXServerItem atIpAndPort(String ipAndPort) {
		ZJABXServerItem tmpServer = null;
		synchronized(itemMapLock) {
			tmpServer = itemMap.get(ipAndPort);
			return tmpServer;
		}
	}

	/**
	 * 添加一組ipAndPort至List
	 * @param ipAndPort - String
	 */
	private void addIdPortList(final String ipAndPort, final List<String> list) {
		synchronized(ipPortListLock) {
			boolean isInList = false;
			for (int i = 0, size = list.size();i < size;i++) {
				if (ipAndPort.equals(list.get(i))) {
					isInList = true;
					break;
				}
			}
			if (!isInList) {
				list.add(ipAndPort);
			}
		}
	}

	/**
	 * 添加一伺服器項目
	 * @param item - ZJABXServerItem
	 */
	public void addItem(ZJABXServerItem item) {
		synchronized(itemMapLock) {
			itemMap.put(item.getIPAndPort(), item);
			String idAndPort = item.getIPAndPort();
			// 500-01-Begin: 依交易所存放資料
			List<String> exchangeIDCollection = item.getExchangeIDCollection();
			if (exchangeIDCollection != null && exchangeIDCollection.size() != 0) {
				for (int i = 0, size = exchangeIDCollection.size();i < size;i++) {
					exchangeIdPortMap.put(exchangeIDCollection.get(i), idAndPort);
				}
			}
			// 500-01-End.
			// 500-10-Begin: 依主機屬性存放資料
			byte hostType = item.getHostType();
			List<String> list = typeIdPortMap.get(hostType);
			if (list == null) {
				list = new ArrayList<String>();
				list.add(idAndPort);
			}else {
				addIdPortList(idAndPort, list);
			}
			typeIdPortMap.put(hostType, list);
			
			if (hostType == 0 || hostType == 2) {//主機屬性為：0-報價主機或2-回報主機
				addIdPortList(idAndPort, ipPortList);
				ZJABXServerItem siObj;
				for (int i = 0, size = ipPortList.size();i < size;i++) {
					siObj = atIpAndPort(ipPortList.get(i));
					siObj.setChannelNo((byte)i);
				}
			}
			// 500-10-End.
			
		}
	}

	/**
	 * 清除itemMap, typeIdPortMap, exchangeIdPortMap及idPortList中之物件
	 */
	public void clear() {
		synchronized(itemMapLock) {
			itemMap.clear();
			typeIdPortMap.clear();
			exchangeIdPortMap.clear();
			ipPortList.clear();;
		}
	}
	
	/**
	 * 設定某一idAndPort的連線狀態
	 * @param ipAndPort - String("hostIP:hostPort")
	 * @param status - boolean
	 */
	public void setConnectStatus(String ipAndPort, boolean status) {
		synchronized(itemMapLock) {
			ZJABXServerItem mapItem = itemMap.get(ipAndPort);
			if (mapItem != null) {
				mapItem.setConnectStatus(status);
			}
		}
	}
	
	/**
	 * 設定某一idAndPort的sessionID
	 * @param ipAndPort - String("hostIP:hostPort")
	 * @param sessionID - int
	 */
	public void setSessionID(String ipAndPort, int sessionID) {
		synchronized(itemMapLock) {
			ZJABXServerItem mapItem = itemMap.get(ipAndPort);
			if (mapItem != null) {
				mapItem.setSessionID(sessionID);
			}
		}
	}

	/**
	 * 設定某一idAndPort的securityKey
	 * @param idAndPort - String("hostIP:hostPort")
	 * @param securityKey - int
	 */
	public void setSecurityKey(String idAndPort, int securityKey) {
		synchronized(itemMapLock) {
			ZJABXServerItem mapItem = itemMap.get(idAndPort);
			if (mapItem != null) {
				mapItem.setSecurityKey(securityKey);
			}
		}
	}

	/**
	 * 取得某一ipAndPort的channelNo
	 * @param ipAndPort - String
	 * @return byte
	 */
	public byte getChannelNo(String ipAndPort) {
		synchronized(itemMapLock) {
			ZJABXServerItem item = atIpAndPort(ipAndPort);
			if (item != null) {
				return item.getChannelNo();
			}else {
				return -1;
			}
		}
	}

	/**
	 * 依主機屬性取得List物件
	 * @param hostType - byte
	 * @return List&lt;String&gt;
	 */
	public List<String> getServerListByHostType(byte hostType) {
		synchronized(itemMapLock) {
			return typeIdPortMap.get(hostType);
		}
	}

	/**
	 * 依交易所代碼取得主機代碼及埠號
	 * @param exchangeID - String
	 * @return String("hostIP:hostPort")
	 */
	public String getIpAndPortByExchangeID(String exchangeID) {
		synchronized(itemMapLock) {
			return exchangeIdPortMap.get(exchangeID);
		}
	}
}