package com.syt.jabx.kernel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON參數驗證之類別
 * @author Jason
 *
 */
final class ZJABXValidation {

	private static JSONObject vMap = new JSONObject();
	
	static {
		URL url = ZJABXValidation.class.getResource("/com/syt/jabx/kernel/validation.ini");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), JABXConst.ABX_CHARSET));
			String line, head;
			int index;
			JSONArray aItem = null;
			while ((line = reader.readLine()) != null) {
				if (line.charAt(0) == '#') {
					continue;
				}
				if (line.charAt(0) == '[') {
					index = line.lastIndexOf(']');
					head = line.substring(1, index);
					aItem = new JSONArray();
					vMap.put(head, aItem);
					continue;
				}
				if (aItem != null) {
					aItem.put(line);
				}
			}
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 驗證傳入之JSONObject中的參數是否正確
	 * @param key - String
	 * @return String(空白代表驗證成功)
	 */
	public static String validate(String key, JSONObject src) {
		String result = "";
		JSONArray vArray = null;
		if (vMap.isNull(key) == false) {
			vArray = (JSONArray)vMap.get(key);
		}
		if (vArray == null) {
			result = "Cannot find validation info for (" + key + ") !";
		}else {
			StringBuffer errSb = new StringBuffer();
			String astr;
			String[] aArray;
			int length = vArray.length();
			for (int i = length;i < length;i++) {
				astr = (String)vArray.get(i);
				aArray = astr.split("|");
				if (src.isNull(aArray[1])) {
					errSb.append(aArray[2]).append(" is null;");
				}else {
					switch(aArray[0]) {
					case "0":
						try {
							Byte.parseByte(src.getString(aArray[1]));
						}catch(NumberFormatException e) {
							errSb.append(aArray[2]).append(" is not byte;");	
						}
						break;
					case "1":
						try {
							Short.parseShort(src.getString(aArray[1]));
						}catch(NumberFormatException e) {
							errSb.append(aArray[2]).append(" is not short;");	
						}
						break;
					case "2":
						try {
							Integer.parseInt(src.getString(aArray[1]));
						}catch(NumberFormatException e) {
							errSb.append(aArray[2]).append(" is not int;");	
						}
						break;
					case "a":
						try {
							src.getJSONArray(aArray[1]);
						}catch(JSONException e) {
							errSb.append(aArray[2]).append(" is not JSONArray;");
						}
						break;
					case "b":
						try {
							src.getBoolean(aArray[1]);
						}catch(JSONException e) {
							errSb.append(aArray[2]).append(" is not boolean;");
						}
						break;
					case "s":
						try {
							src.getString(aArray[1]);
						}catch(JSONException e) {
							errSb.append(aArray[2]).append(" is not String;");
						}
						break;
					}
				}
			}
			if (errSb.length() != 0) {
				errSb.deleteCharAt(errSb.length() - 1);
			}
			result = errSb.toString();
		}
		return result;
	}

}