package com.syt.jabx.kernel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 錯誤訊息定義之類別
 * @author Jason
 *
 */
final class ZJABXErrorInfo {

	private static Map<Integer, String> errMap = new HashMap<Integer, String>();
	
	static {
		URL url = ZJABXErrorInfo.class.getResource("/com/syt/jabx/kernel/errcode.txt");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), JABXConst.ABX_CHARSET));
			String line;
			String key, value;
			int index;
			while ((line = reader.readLine()) != null) {
				index = line.indexOf("|");
				if (index != -1) {
					key = line.substring(0, index);
					value = line.substring(index + 1).trim();
					errMap.put(Integer.valueOf(key), value);
				}
			}
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加錯誤訊息資料
	 * @param key - Integer
	 * @param value - String
	 */
	public static void addData(Integer key, String value) {
		errMap.put(key, value);
	}

	/**
	 * 取得錯誤描述
	 * @param key - Integer
	 * @return String
	 */
	public static String getErrorDesc(Integer key) {
		String value = errMap.get(key);
		if (value == null) {
			value = errMap.get(-1);
		}
		return value;
	}
}
