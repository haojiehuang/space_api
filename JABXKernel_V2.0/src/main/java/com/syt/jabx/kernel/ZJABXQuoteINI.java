package com.syt.jabx.kernel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 設定檔定義之類別
 * @author Jason
 *
 */
final class ZJABXQuoteINI {

	private static int fixdataLengthType = 1;
	
	static {
		URL url = ZJABXQuoteINI.class.getResource("/com/syt/jabx/kernel/quote.ini");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			String key, value;
			int index;
			while ((line = reader.readLine()) != null) {
				index = line.indexOf("|");
				if (index != -1) {
					key = line.substring(0, index);
					value = line.substring(index + 1).trim();
					if (key.equals("FIXDATA_LENGTH_TYPE")) {
						fixdataLengthType = Integer.parseInt(value);
					}
				}
			}
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取得資料長度類型
	 * @return int
	 */
	public static int getFixdataLengthType() {
		return fixdataLengthType;
	}
}
