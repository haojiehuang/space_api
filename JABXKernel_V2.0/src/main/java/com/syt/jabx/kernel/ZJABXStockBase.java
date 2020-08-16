package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXStockBase;

/**
 * 股票基本資料之類別
 * @author Jason
 *
 */
final class ZJABXStockBase implements IJABXStockBase {

	/**
	 * 股票繁中名稱
	 */
	private String stkNameT;

	/**
	 * 股票簡中名稱
	 */
	private String stkNameS;

	/**
	 * 股票英文名稱
	 */
	private String stkNameE;

	/**
	 * 履約價
	 */
	private int strikePrice;

	/**
	 * 商品代碼
	 */
	private String classType;

	/**
	 * 商品群組代碼 
	 */
	private int classGroupID;

	/**
	 * 產業代碼
	 */
	private String tradeType;

	/**
	 * 產業群組代碼
	 */
	private int tradeGroupID;

	/**
	 * 交易代號
	 */
	private String tradeCode;

	/**
	 * 小數位數或分數之分母
	 */
	private int decimal;

	/**
	 * 證券屬性
	 */
	private String stkNote;

	/**
	 * 交易單位
	 */
	private int lotSize;

	/**
	 * 時區
	 */
	private byte timeZone;

	/**
	 * 開盤時間
	 */
	private short openHHMM;

	/**
	 * 收盤時間
	 */
	private short closeHHMM;

	/**
	 * 休息時間1
	 */
	private short restHHMM1;

	/**
	 * 再開盤時間1
	 */
	private short reOpenHHMM1;

	/**
	 * 休息時間2
	 */
	private short restHHMM2;

	/**
	 * 再開盤時間2
	 */
	private short reOpenHHMM2;

	/**
	 * 盤前時間
	 */
	private short preOpenHHMM;

	/**
	 * 檔差
	 */
	private int tickDiff;

	/**
	 * 交易幣別
	 */
	private String currencyCode;

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getStkNameT()
	 */
	@Override
	public String getStkNameT() {
		// TODO Auto-generated method stub
		return stkNameT;
	}

	/**
	 * 設定股票繁中名稱
	 * @param stkNameT - String
	 */
	public void setStkNameT(String stkNameT) {
		this.stkNameT = stkNameT;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getStkNameS()
	 */
	@Override
	public String getStkNameS() {
		// TODO Auto-generated method stub
		return stkNameS;
	}

	/**
	 * 設定股票簡中名稱
	 * @param stkNameS - String
	 */
	public void setStkNameS(String stkNameS) {
		this.stkNameS = stkNameS;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getStkNameE()
	 */
	@Override
	public String getStkNameE() {
		// TODO Auto-generated method stub
		return stkNameE;
	}

	/**
	 * 設定股票英文名稱
	 * @param stkNameE - String
	 */
	public void setStkNameE(String stkNameE) {
		this.stkNameE = stkNameE;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getStrikePrice()
	 */
	@Override
	public int getStrikePrice() {
		// TODO Auto-generated method stub
		return strikePrice;
	}

	/**
	 * 設定履約價
	 * @param strikePrice - int
	 */
	public void setStrikePrice(int strikePrice) {
		this.strikePrice = strikePrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getClassType()
	 */
	@Override
	public String getClassType() {
		// TODO Auto-generated method stub
		return classType;
	}

	/**
	 * 設定商品代碼
	 * @param classType - String
	 */
	public void setClassType(String classType) {
		this.classType = classType;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getClassGroupID()
	 */
	@Override
	public int getClassGroupID() {
		// TODO Auto-generated method stub
		return classGroupID;
	}

	/**
	 * 設定商品群組代碼 
	 * @param classGroupID - int
	 */
	public void setClassGroupID(int classGroupID) {
		this.classGroupID = classGroupID;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getTradeType()
	 */
	@Override
	public String getTradeType() {
		// TODO Auto-generated method stub
		return tradeType;
	}

	/**
	 * 設定產業代碼
	 * @param tradeType - String
	 */
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getTradeGroupID()
	 */
	@Override
	public int getTradeGroupID() {
		// TODO Auto-generated method stub
		return tradeGroupID;
	}

	/**
	 * 設定產業群組代碼
	 * @param tradeGroupID - int
	 */
	public void setTradeGroupID(int tradeGroupID) {
		this.tradeGroupID = tradeGroupID;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getTradeCode()
	 */
	@Override
	public String getTradeCode() {
		// TODO Auto-generated method stub
		return tradeCode;
	}

	/**
	 * 設定交易代號
	 * @param tradeCode - String
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getDecimal()
	 */
	@Override
	public int getDecimal() {
		// TODO Auto-generated method stub
		return decimal;
	}

	/**
	 * 設定小數位數或分數之分母
	 * @param decimal - int
	 */
	public void setDecimal(int decimal) {
		this.decimal = decimal;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getStkNote()
	 */
	@Override
	public String getStkNote() {
		// TODO Auto-generated method stub
		return stkNote;
	}

	/**
	 * 設定證券屬性
	 * @param stkNote - String
	 */
	public void setStkNote(String stkNote) {
		this.stkNote = stkNote;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getLotSize()
	 */
	@Override
	public int getLotSize() {
		// TODO Auto-generated method stub
		return lotSize;
	}

	/**
	 * 設定交易單位
	 * @param lotSize - int
	 */
	public void setLotSize(int lotSize) {
		this.lotSize = lotSize;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getTimeZone()
	 */
	@Override
	public byte getTimeZone() {
		// TODO Auto-generated method stub
		return timeZone;
	}

	/**
	 * 設定時區
	 * @param timeZone - byte
	 */
	public void setTimeZone(byte timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getOpenHHMM()
	 */
	@Override
	public short getOpenHHMM() {
		// TODO Auto-generated method stub
		return openHHMM;
	}

	/**
	 * 設定開盤時間 
	 * @param openHHMM - short(HHmm(時分))
	 */
	public void setOpenHHMM(short openHHMM) {
		this.openHHMM = openHHMM;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getCloseHHMM()
	 */
	@Override
	public short getCloseHHMM() {
		// TODO Auto-generated method stub
		return closeHHMM;
	}

	/**
	 * 設定收盤時間 
	 * @param closeHHMM - short(HHmm(時分))
	 */
	public void setCloseHHMM(short closeHHMM) {
		this.closeHHMM = closeHHMM;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getRestHHMM1()
	 */
	@Override
	public short getRestHHMM1() {
		// TODO Auto-generated method stub
		return restHHMM1;
	}

	/**
	 * 設定休息時間1
	 * @param restHHMM1 - short(HHmm(時分))
	 */
	public void setRestHHMM1(short restHHMM1) {
		this.restHHMM1 = restHHMM1;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getReOpenHHMM1()
	 */
	@Override
	public short getReOpenHHMM1() {
		// TODO Auto-generated method stub
		return reOpenHHMM1;
	}

	/**
	 * 設定再開盤時間1
	 * @param reOpenHHMM1 - short(HHmm(時分))
	 */
	public void setReOpenHHMM1(short reOpenHHMM1) {
		this.reOpenHHMM1 = reOpenHHMM1;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getRestHHMM2()
	 */
	@Override
	public short getRestHHMM2() {
		// TODO Auto-generated method stub
		return restHHMM2;
	}

	/**
	 * 設定休息時間2
	 * @param restHHMM2 - short(HHmm(時分))
	 */
	public void setRestHHMM2(short restHHMM2) {
		this.restHHMM2 = restHHMM2;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getReOpenHHMM2()
	 */
	@Override
	public short getReOpenHHMM2() {
		// TODO Auto-generated method stub
		return reOpenHHMM2;
	}

	/**
	 * 設定再開盤時間2
	 * @param reOpenHHMM2 - short(HHmm(時分))
	 */
	public void setReOpenHHMM2(short reOpenHHMM2) {
		this.reOpenHHMM2 = reOpenHHMM2;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getPreOpenHHMM()
	 */
	@Override
	public short getPreOpenHHMM() {
		// TODO Auto-generated method stub
		return preOpenHHMM;
	}

	/**
	 * 設定盤前時間
	 * @param preOpenHHMM - short(HHmm(時分))
	 */
	public void setPreOpenHHMM(short preOpenHHMM) {
		this.preOpenHHMM = preOpenHHMM;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getTickDiff()
	 */
	@Override
	public int getTickDiff() {
		// TODO Auto-generated method stub
		return tickDiff;
	}

	/**
	 * 設定檔差
	 * @param tickDiff - int
	 */
	public void setTickDiff(int tickDiff) {
		this.tickDiff = tickDiff;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXStockBase#getCurrencyCode()
	 */
	@Override
	public String getCurrencyCode() {
		// TODO Auto-generated method stub
		return currencyCode;
	}

	/**
	 * 設定交易幣別
	 * @param currencyCode - String
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 將類別中所用到的欄位設定為零或空白
	 */
	public void setDataToZeroOrSpace() {
		stkNameT = "";
		stkNameS = "";
		stkNameE = "";
		strikePrice = 0;
		classType = "";
		classGroupID = 0;
		tradeType = "";
		tradeGroupID = 0;
		tradeCode = "";
		decimal = 0;
		stkNote = "";
		lotSize = 0;
		timeZone = 0;
		openHHMM = 0;
		closeHHMM = 0;
		restHHMM1 = 0;
		reOpenHHMM1 = 0;
		restHHMM2 = 0;
		reOpenHHMM2 = 0;
		preOpenHHMM = 0;
		tickDiff = 0;
		currencyCode = "";		
	}
}
