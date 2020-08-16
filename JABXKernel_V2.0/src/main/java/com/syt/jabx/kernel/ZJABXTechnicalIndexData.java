package com.syt.jabx.kernel;

import com.syt.jabx.query.IJABXTechnicalIndexData;

/**
 * 技術指標數據的類別
 * @author Jason
 *
 */
final class ZJABXTechnicalIndexData implements IJABXTechnicalIndexData {

	/**
	 * 證券全代碼
	 */
	private String id;

	/**
	 * 指標記錄序號
	 */
	private int lineID;

	/**
	 * 技術指標代碼
	 */
	private int technicalIndexID;

	/**
	 * 周期代碼
	 */
	private int period;

	/**
	 * 指標線設定
	 */
	private String lineSetup;

	/**
	 * 技術指標數據總筆數
	 */
	private int dataTotalCount;

	/**
	 * 技術指標數據
	 */
	private String data;

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexData#getID()
	 */
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return id;
	}

	/**
	 * 設定證券全代碼
	 * @param id - String
	 */
	public void setID(String id) {
		this.id = id;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexData#getLineID()
	 */
	@Override
	public int getLineID() {
		// TODO Auto-generated method stub
		return lineID;
	}

	/**
	 * 設定指標記錄序號
	 * @param lineID - int
	 */
	public void setLineID(int lineID) {
		this.lineID = lineID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexData#getTechnicalIndexID()
	 */
	@Override
	public int getTechnicalIndexID() {
		// TODO Auto-generated method stub
		return technicalIndexID;
	}

	/**
	 * 設定技術指標代碼
	 * @param technicalIndexID - int
	 */
	public void setTechnicalIndexID(int technicalIndexID) {
		this.technicalIndexID = technicalIndexID;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexData#getPeriod()
	 */
	@Override
	public int getPeriod() {
		// TODO Auto-generated method stub
		return period;
	}

	/**
	 * 設定周期代碼
	 * @param period - int
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexData#getLineSetup()
	 */
	@Override
	public String getLineSetup() {
		// TODO Auto-generated method stub
		return lineSetup;
	}

	/**
	 * 設定指標線設定
	 * @param lineSetup - String
	 */
	public void setLineSetup(String lineSetup) {
		this.lineSetup = lineSetup;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexData#getDataTotalCount()
	 */
	@Override
	public int getDataTotalCount() {
		// TODO Auto-generated method stub
		return dataTotalCount;
	}

	/**
	 * 設定技術指標數據總筆數
	 * @param dataTotalCount - int
	 */
	public void setDataTotalCount(int dataTotalCount) {
		this.dataTotalCount = dataTotalCount;
	}

	/**
	 * @see com.syt.jabx.query.IJABXTechnicalIndexData#getData()
	 */
	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return data;
	}

	/**
	 * 設定技術指標數據
	 * @param data - String
	 */
	public void setData(String data) {
		this.data = data;
	}

}
