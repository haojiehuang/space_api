package com.syt.jabx.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 記錄即時報價資訊之類別
 * @author jason
 *
 */
public class JABXQuoteDataInfo {

	/**
	 * 總筆數
	 */
	private int dataCount;

	/**
	 * 保留所有JABXFixData之List物件
	 */
	private List<JABXFixData> fixDataList;

	/**
	 * Constructor
	 */
	public JABXQuoteDataInfo() {
		dataCount = 0;
		fixDataList = new ArrayList<JABXFixData>();
	}

	/**
	 * 加num筆數
	 * @param num - int
	 */
	public void addCount(int num) {
		dataCount += num;
	}

	/**
	 * 取得總筆數
	 * @return int
	 */
	public int getDataCount() {
		return this.dataCount;
	}

	/**
	 * 取得保存所有JABXFixData之List物件
	 * @return List&lt;JABXFixData&gt;
	 */
	public List<JABXFixData> getFixDataList() {
		return this.fixDataList;
	}

	/**
	 * 添加JABXFixData之List物件
	 * @param addList - List&lt;JABXFixData&gt;
	 */
	public void addFixDataList(List<JABXFixData> addList) {
		this.fixDataList.addAll(addList);
	}
}
