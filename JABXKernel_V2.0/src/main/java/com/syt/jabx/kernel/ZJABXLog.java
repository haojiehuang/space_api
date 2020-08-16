package com.syt.jabx.kernel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.syt.jabx.notify.IJABXLog;
import com.syt.jabx.notify.IJABXOutputMsg;

/**
 * 記錄Log之類別
 * @author jason
 *
 */
final class ZJABXLog implements IJABXLog {

	/**
	 * 輸出串流之Buffer size
	 */
	private final static int BUFFERED_SIZE = 512;

	/**
	 * Log檔名稱
	 */
	private final static String LOGFILE_NAME = "_jabx.log";

	/**
	 * Debug使用之Log檔名稱
	 */
	private final static String DEBUG_FILE = "_debug.log";

	/**
	 * 錯誤訊息之Log檔名稱
	 */
	private final static String ERROR_FILE = "_err.log";

	/**
	 * 應用程序之Log檔存放路徑
	 */
	private File appLogFile;

	/**
	 * 輸出訊息之介面
	 */
	private IJABXOutputMsg outputMsg;

	/**
	 * 登入帳號
	 */
	private String account;

	/**
	 * Constructor
	 * @param outputMsg - IJABXOutputMsg
	 * @param appLogFile - File
	 */
	public ZJABXLog(IJABXOutputMsg outputMsg, File appLogFile) {
		this.outputMsg = outputMsg;
		this.appLogFile = appLogFile;
		this.account = "#UnLogin";
	}

	/**
	 * 輸出訊息至Log File
	 * @param info - String(Log訊息)
	 */
	private void writeLog(String info) {

		File logFile = null;
		boolean isLogFileOK = false;
		if (outputMsg != null) {
			if (outputMsg.getFilePath() != null && !outputMsg.getFilePath().equals("")) {
				String currDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
				logFile = new File(appLogFile, currDate + LOGFILE_NAME);
				try {
					isLogFileOK = true;
					if (!logFile.exists()) {
						logFile.createNewFile();
					}
				}catch(Exception e) {
					isLogFileOK = false;
					e.printStackTrace();
				}
			}
		}

		if (!isLogFileOK) {
			return;
		}

		synchronized(logFile) {
			OutputStream out;
			Writer osw, bw;
			try {				
				out = new FileOutputStream(logFile, true);
				osw = new OutputStreamWriter(out, JABXConst.ABX_CHARSET);
				bw = new BufferedWriter(osw, BUFFERED_SIZE);
			
				StringBuffer sb = new StringBuffer();
				sb.append(info).append("\r\n");
				bw.write(sb.toString());
				sb = null;
			
				bw.close();
				osw.close();
				out.close();
			}catch(Exception mle) {
				mle.printStackTrace();
			}finally {
				bw = null;
				osw = null;
				out = null;
			}
		}
	}

	/**
	 * 輸出訊息至Log File
	 * @param loginData - String(用戶登入資料)
	 * @param info - String(Log訊息)
	 */
	private void writeDebugLog(String loginData, String info) {
		
		boolean isLogFileOK = false;
		File debugLogFile = null;
		String fileHead = loginData.replaceAll("\\|", "_");
		if (outputMsg != null) {
			if (outputMsg.getFilePath() != null && !outputMsg.getFilePath().equals("")) {
				String currDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
				debugLogFile = new File(appLogFile, fileHead + "_" + currDate + DEBUG_FILE);
				try {
					if (!debugLogFile.exists()) {
						debugLogFile.createNewFile();
					}
					isLogFileOK = true;
				}catch(Exception e) {
					isLogFileOK = false;
					e.printStackTrace();
				}
			}
		}

		if (!isLogFileOK) {
			return;
		}

		synchronized(debugLogFile) {
			OutputStream out;
			Writer osw, bw;
			try {				
				out = new FileOutputStream(debugLogFile, true);
				osw = new OutputStreamWriter(out, JABXConst.ABX_CHARSET);
				bw = new BufferedWriter(osw, BUFFERED_SIZE);
			
				StringBuffer sb = new StringBuffer();
				sb.append(info).append("\r\n");
				bw.write(sb.toString());
				sb = null;
			
				bw.close();
				osw.close();
				out.close();
			}catch(Exception mle) {
				mle.printStackTrace();
			}finally {
				bw = null;
				osw = null;
				out = null;
			}
		}
	}

	/**
	 * 輸出訊息至Error Log File
	 * @param info - String(Log訊息)
	 */
	private void writeErrorLog(String info) {

		File errorLogFile = null;
		boolean isLogFileOK = false;
		if (outputMsg != null) {
			if (outputMsg.getFilePath() != null && !outputMsg.getFilePath().equals("")) {
				String currDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
				errorLogFile = new File(appLogFile, currDate + ERROR_FILE);
				try {
					if (!errorLogFile.exists()) {
						errorLogFile.createNewFile();
					}
				}catch(Exception e) {
					isLogFileOK = false;
					e.printStackTrace();
				}
			}
		}

		if (!isLogFileOK) {
			return;
		}

		synchronized(errorLogFile) {
			OutputStream out;
			Writer osw, bw;
			try {				
				out = new FileOutputStream(errorLogFile, true);
				osw = new OutputStreamWriter(out, JABXConst.ABX_CHARSET);
				bw = new BufferedWriter(osw, BUFFERED_SIZE);
			
				StringBuffer sb = new StringBuffer();
				sb.append(info).append("\r\n");
				bw.write(sb.toString());
				sb = null;
			
				bw.close();
				osw.close();
				out.close();
			}catch(Exception mle) {
				mle.printStackTrace();
			}finally {
				bw = null;
				osw = null;
				out = null;
			}
		}
	}

	@Override
	public void outputInfoAndLog(String funcName, String msg) {
		// TODO Auto-generated method stub
		if (outputMsg == null) {
			return;
		}
		if (outputMsg.isOutputInfo()) {
			outputMsg.outputInfo(account, funcName, msg);
		}
		if (outputMsg.isWriteLog()) {
			writeLog(outputMsg.mergeTimeInfo(account, funcName, msg));
		}
		if (outputMsg.getLogCheckIDS().contains(account)) {
			writeDebugLog(account, outputMsg.mergeTimeInfo(account, funcName, msg));
		}
	}

	@Override
	public void outputRealtimeMsg(String funcName, String msg) {
		// TODO Auto-generated method stub
		if (outputMsg == null) {
			return;
		}
		if (outputMsg.isOutputRealtime()) {
			outputMsg.outputInfo(account, funcName, msg);
			writeLog(outputMsg.mergeTimeInfo(account, funcName, msg));
		}
		if (outputMsg.getLogCheckIDS().contains(account)) {
			writeDebugLog(account, outputMsg.mergeTimeInfo(account, funcName, msg));
		}		
	}

	@Override
	public void outputErrorAndLog(String funcName, String msg) {
		// TODO Auto-generated method stub
		if (outputMsg == null) {
			return;
		}

		outputMsg.outputInfo(account, funcName, msg);
		writeErrorLog(outputMsg.mergeTimeInfo(account, funcName, msg));
		if (outputMsg.getLogCheckIDS().contains(account)) {
			writeDebugLog(account, outputMsg.mergeTimeInfo(account, funcName, msg));
		}
	}

	/**
	 * 設定用戶帳號
	 * @param account
	 */
	public void setAccount(String account) {
		this.account = account;
	}
}
