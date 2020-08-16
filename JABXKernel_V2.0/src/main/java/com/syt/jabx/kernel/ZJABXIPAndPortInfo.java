package com.syt.jabx.kernel;

/**
 * 主機代碼及埠號資訊
 * @author Jason
 *
 */
final class ZJABXIPAndPortInfo {
	
	/**
	 * 主機代碼及埠號
	 */
	private String idAndPort;

	/**
	 * 是否為指定的Server(1.回報主機,2.交易主機或3.GW主機)
	 */
	private boolean isAssignedServer;

	/**
	 * Constructor
	 * @param idAndPort - String
	 * @param isAssignedServer - boolean
	 */
	public ZJABXIPAndPortInfo(String idAndPort, boolean isAssignedServer) {
		this.idAndPort = idAndPort;
		this.isAssignedServer = isAssignedServer;
	}

	/**
	 * 取得主機代碼及埠號
	 * @return String
	 */
	public String getIdAndPort() {
		return this.idAndPort;
	}

	/**
	 * 是否為指定的Server(1.回報主機,2.交易主機或3.GW主機)
	 * @return boolean
	 */
	public boolean isAssignedServer() {
		return isAssignedServer;
	}
}
