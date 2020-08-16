package com.syt.jabx.kernel;

import java.util.List;

/**
 * 伺服器之類別
 * @author Jason
 *
 */
final class ZJABXServerItem implements Cloneable {

	/**
	 * 主機屬性(0-報價主機、1-交易主機、2-回報主機、3-ABUSGW主機)
	 */
	private byte hostType;

	/**
	 * 主機IP
	 */
	private String hostIP;

	/**
	 * 主機埠號
	 */
	private short hostPort;

	/**
	 * 主機連線狀態
	 */
	private boolean connectStatus;

	/**
	 * 交易所列表
	 */
	private List<String> exchangeIDCollection;

	/**
	 * 連線逾時時間
	 */
	private int ntimeout;

	/**
	 * 連線存活時間
	 */
	private int naliveTime;

	/**
	 * 連線序號
	 */
	private byte channelNo;

	/**
	 * 安全碼
	 */
	private Integer securityKey;

	/**
	 * 訂閱之後Server所給的連線ID(第一次訂閱為0)
	 */
	private volatile int sessionID;

	/**
	 * 讀取傳送Socket參數的Lock
	 */
	public byte[] socketParamLock = new byte[0];

	/**
	 * 是否阻擋傳送Socket參數資料
	 */
	public volatile boolean isBlockingQueue;

	/**
	 * connectStatus使用之Lock
	 */
	private byte[] connectStatusLock = new byte[0];

	/**
	 * Constructor
	 */
	public ZJABXServerItem() {
		securityKey = new Integer(0);
	}

	/**
	 * 取得主機類型<br>
	 * 0-報價主機<br>
	 * 1-交易主機<br>
	 * 2-回報主機<br>
	 * 3-ABUSGW主機<br>
	 * @return byte
	 */
	public byte getHostType() {
		return hostType;
	}

	/**
	 * 設定主機屬性(0-報價主機、1-交易主機、2-回報主機、3-ABUSGW主機)
	 * @param hostType - byte
	 */
	public void setHostType(byte hostType) {
		this.hostType = hostType;
	}

	/**
	 * 取得主機ip
	 * @return String
	 */
	public String getHostIP() {
		return hostIP;
	}

	/**
	 * 設定主機ip
	 * @param hostIP - String
	 */
	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}
	
	/**
	 * 取得主機埠號
	 * @return short
	 */
	public short getHostPort() {
		return hostPort;
	}

	/**
	 * 設定主機埠號
	 * @param hostPort - short
	 */
	public void setHostPort(short hostPort) {
		this.hostPort = hostPort;
	}

	/**
	 * 取得連線狀態(true.連線,false.斷線)
	 * @return boolean
	 */
	public boolean getConnectStatus() {
		synchronized(connectStatusLock) {
			return connectStatus;
		}
	}

	/**
	 * 設定連線狀態
	 * @param b - boolean
	 */
	public void setConnectStatus(boolean b) {
		synchronized(connectStatusLock) {
			this.connectStatus = b;
		}
	}

	/**
	 * 取得交易所列表
	 * @return List&lt;String&gt;
	 */
	public List<String> getExchangeIDCollection() {
		// TODO Auto-generated method stub
		return exchangeIDCollection;
	}

	/**
	 * 設定交易所列表
	 * @param exchangeIDCollection - List&lt;String&gt;
	 */
	public void setExchangeIDCollection(List<String> exchangeIDCollection) {
		this.exchangeIDCollection = exchangeIDCollection;
	}

	/**
	 * 取得連線逾時時間
	 * @return int
	 */
	public int getNtimeout() {
		return ntimeout;
	}

	/**
	 * 設定連線逾時時間
	 * @param ntimeout - int
	 */
	public void setNtimeout(int ntimeout) {
		this.ntimeout = ntimeout;
	}

	/**
	 * 取得連線存活時間
	 * @return int
	 */
	public int getNaliveTime() {
		return naliveTime;
	}

	/**
	 * 設定連線存活時間
	 * @param naliveTime - int
	 */
	public void setNaliveTime(int naliveTime) {
		this.naliveTime = naliveTime;
	}

	/**
	 * 取得連線序號
	 * @return byte
	 */
	public byte getChannelNo() {
		return channelNo;
	}

	/**
	 * 設定連線序號
	 * @param channelNo - byte
	 */
	public void setChannelNo(byte channelNo) {
		this.channelNo = channelNo;
	}

	/**
	 * 取得安全碼
	 * @return Integer
	 */
	public Integer getSecurityKey() {
		return securityKey;
	}

	/**
	 * 設定安全碼
	 * @param securityKey - Integer
	 */
	public void setSecurityKey(Integer securityKey) {
		this.securityKey = securityKey;
	}

	/**
	 * 取得訂閱之後Server所給的連線ID(第一次訂閱為0)
	 * @return int
	 */
	public int getSessionID() {
		return sessionID;
	}

	/**
	 * 設定訂閱之後Server所給的連線ID(第一次訂閱為0)
	 * @param sessionID - int
	 */
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * 取得主機IP和埠號("hostIP:hostPort")
	 * @return String
	 */
	public String getIPAndPort() {
		return hostIP + ":" + hostPort;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	protected Object clone() {
		ZJABXServerItem obj = null;
		try {
			obj = (ZJABXServerItem)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}		
		return obj;
	}

	/**
	 * 複製JABXServerItem物件
	 */
	public ZJABXServerItem cloneObj() {
		return (ZJABXServerItem)clone();
	}
}
