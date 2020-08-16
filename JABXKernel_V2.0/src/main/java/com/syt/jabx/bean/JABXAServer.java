package com.syt.jabx.bean;

/**
 * Server資訊類別(Server端應用程序使用)
 * @author jason
 *
 */
public class JABXAServer {

	/**
	 * 主機ip或host name
	 */
	private String host;

	/**
	 * 主機連線埠
	 */
	private short port;

	/**
	 * 封包序號
	 */
	private String packNo;

	/**
	 * 取得主機ip或host name
	 * @return String
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 設定主機ip或host name
	 * @param host - String
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 取得主機連線埠
	 * @return short
	 */
	public short getPort() {
		return port;
	}

	/**
	 * 設定主機連線埠
	 * @param port - short
	 */
	public void setPort(short port) {
		this.port = port;
	}

	/**
	 * 取得封包序號
	 * @return String
	 */
	public String getPackNo() {
		return packNo;
	}

	/**
	 * 設定封包序號
	 * @param packNo - String
	 */
	public void setPackNo(String packNo) {
		this.packNo = packNo;
	}

	public String getHostAndPort() {
		return host + ":" + port;
	}
}
