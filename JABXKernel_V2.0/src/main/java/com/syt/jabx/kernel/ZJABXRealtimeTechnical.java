package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.watch.IJABXRealtimeTechnical;

/**
 * 即時K類別
 * @author Jason
 *
 */
final class ZJABXRealtimeTechnical implements IJABXRealtimeTechnical {

	/**
	 * 週期代碼
	 */
	private int period;

	/**
	 * 週期之日期(格式為yyyyMMdd)
	 */
	private int periodDate;

	/**
	 * 週期之時間(格式為HHmm)
	 */
	private int periodTime;

	/**
	 * 開盤價
	 */
	private int openPrice;

	/**
	 * 最高價
	 */
	private int highPrice;

	/**
	 * 最低價
	 */
	private int lowPrice;

	/**
	 * 成交價
	 */
	private int tradePrice;

	/**
	 * 開盤參考價
	 */
	private int refOpenPrice;

	/**
	 * 成交量
	 */
	private long tradeVolume;

	/**
	 * 成交金額
	 */
	private long tradeAmount;

	/**
	 * 換手率
	 */
	private String changeRate;

	/**
	 * 交易時間之類別
	 */
	private JSONObject tradeTime;

	/**
	 * Constructor
	 */
	public ZJABXRealtimeTechnical() {
		changeRate = "0.000";
	}

	/**
	 * @see com.syt.jabx.watch.IJABXRealtimeTechnical#getPeriod()
	 */
	@Override
	public int getPeriod() {
		return period;
	}

	/**
	 * 設定週期代碼
	 * @param period - int
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXRealtimeTechnical#getPeriodDate()
	 */
	@Override
	public int getPeriodDate() {
		// TODO Auto-generated method stub
		return periodDate;
	}

	/**
	 * 設定週期之日期(格式為yyyyMMdd)
	 * @param periodDate - int
	 */
	public void setTradeDate(int periodDate) {
		this.periodDate = periodDate;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXRealtimeTechnical#getPeriodTime()
	 */
	@Override
	public int getPeriodTime() {
		// TODO Auto-generated method stub
		return periodTime;
	}

	/**
	 * 設定週期之時間(格式為HHmm)
	 * @param periodTime - int
	 */
	public void setPeriodTime(int periodTime) {
		this.periodTime = periodTime;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXRealtimeTechnical#getOpenPrice()
	 */
	@Override
	public int getOpenPrice() {
		// TODO Auto-generated method stub
		return openPrice;
	}

	/**
	 * 設定開盤價
	 * @param openPrice - int
	 */
	public void setOpenPrice(int openPrice) {
		this.openPrice = openPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXRealtimeTechnical#getHighPrice()
	 */
	@Override
	public int getHighPrice() {
		// TODO Auto-generated method stub
		return highPrice;
	}

	/**
	 * 設定最高價
	 * @param highPrice - int
	 */
	public void setHighPrice(int highPrice) {
		this.highPrice = highPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXRealtimeTechnical#getLowPrice()
	 */
	@Override
	public int getLowPrice() {
		// TODO Auto-generated method stub
		return lowPrice;
	}

	/**
	 * 設定最低價
	 * @param lowPrice - int
	 */
	public void setLowPrice(int lowPrice) {
		this.lowPrice = lowPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXRealtimeTechnical#getTradePrice()
	 */
	@Override
	public int getTradePrice() {
		// TODO Auto-generated method stub
		return tradePrice;
	}

	/**
	 * 設定成交價
	 * @param tradePrice - int
	 */
	public void setTradePrice(int tradePrice) {
		this.tradePrice = tradePrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXRealtimeTechnical#getRefOpenPrice()
	 */
	@Override
	public int getRefOpenPrice() {
		// TODO Auto-generated method stub
		return refOpenPrice;
	}

	/**
	 * 設定開盤參考價
	 * @param refOpenPrice - int
	 */
	public void setRefOpenPrice(int refOpenPrice) {
		this.refOpenPrice = refOpenPrice;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXRealtimeTechnical#getTradeVolume()
	 */
	@Override
	public long getTradeVolume() {
		// TODO Auto-generated method stub
		return tradeVolume;
	}

	/**
	 * 設定成交量
	 * @param tradeVolume - long
	 */
	public void setTradeVolume(long tradeVolume) {
		this.tradeVolume = tradeVolume;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXRealtimeTechnical#getTradeAmount()
	 */
	@Override
	public long getTradeAmount() {
		// TODO Auto-generated method stub
		return tradeAmount;
	}

	/**
	 * 設定成交金額
	 * @param tradeAmount - long
	 */
	public void setTradeAmount(long tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXRealtimeTechnical#getChangeRate()
	 */
	@Override
	public String getChangeRate() {
		// TODO Auto-generated method stub
		return changeRate;
	}

	/**
	 * 設定換手率
	 * @param changeRate - String
	 */
	public void setChangeRate(String changeRate) {
		this.changeRate = changeRate;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TradeDate: ").append(periodDate).append(" ");
		sb.append("TradeTime: ").append(periodTime).append(" ");
		sb.append("OpenPrice: ").append(openPrice).append(" ");
		sb.append("HighPrice: ").append(highPrice).append(" ");
		sb.append("LowPrice: ").append(lowPrice).append(" ");
		sb.append("TradePrice: ").append(tradePrice).append(" ");
		sb.append("RefOpenPrice: ").append(refOpenPrice).append(" ");
		sb.append("TradeVolume: ").append(tradeVolume).append(" ");
		sb.append("TradeAmount: ").append(tradeAmount).append(" ");
		sb.append("ChangeRate: ").append(changeRate).append(" ");
		return sb.toString();
	}

	/**
	 * 週期為日週月年線之資料計算
	 * @param jabxKernel - JABXKernel
	 * @param quoteTool - JABXQuoteTool
	 * @param stkID - String
	 * @param trade - ZJABXTrade
	 */
	private void calcData1(JABXKernel jabxKernel, JABXQuoteTool quoteTool, int period, String stkID, ZJABXTrade trade) {
		// 將週期之時間重置為0
		periodTime = 0;
		// 依股票代碼取得交易日期
		int lastTradeDate = quoteTool.getLastTradeDate(stkID);
		// 取得交易日所對應之週期日期
		int calcPeriodDate = jabxKernel.getDayPeriod(period, lastTradeDate);

		if (periodDate < calcPeriodDate) {
			periodDate = calcPeriodDate;
			openPrice = trade.getTradePrice();
			highPrice = trade.getTradePrice();
			lowPrice = trade.getTradePrice();
			refOpenPrice = trade.getTradePrice();
			tradeVolume = trade.getTradeVolume();
			tradeAmount = 1L * trade.getTradePrice() * trade.getTradeVolume() * (1000 / quoteTool.getPowerByDecimal(stkID));
		}else {
			if (trade.getTradePrice() > highPrice) {
				highPrice = trade.getTradePrice();
			}
			if (trade.getTradePrice() < lowPrice) {
				lowPrice = trade.getTradePrice();
			}
			tradeVolume += trade.getTradeVolume();
			tradeAmount += 1L * trade.getTradePrice() * trade.getTradeVolume() * (1000 / quoteTool.getPowerByDecimal(stkID));
		}
		tradePrice = trade.getTradePrice();
	}

	/**
	 * 週期為分線類型之資料計算
	 * @param jabxKernel - JABXKernel
	 * @param quoteTool - JABXQuoteTool
	 * @param period - int
	 * @param stkID - String
	 * @param trade - ZJABXTrade
	 */
	private void calcData2(JABXKernel jabxKernel, JABXQuoteTool quoteTool, int period, String stkID, ZJABXTrade trade) {
		// 依股票代碼及交易時間取得對應分時之時間(若為午後盤，凌晨至收盤時間為+2400後之時間)
		int calcTradeTime = quoteTool.getTradeTime(stkID, trade.getTradeTime());
		// 依股票代碼及交易時間取得週期之日期
		int calcPeriodDate = quoteTool.getTradeDate(stkID, calcTradeTime);

		int calcPeriodTime;
		if (period == JABXConst.ABX_PRICEDATA_MIN) {
			calcPeriodTime = calcTradeTime;
		}else {
			if (tradeTime == null) {
				tradeTime = new JSONObject();
				JSONObject stkBase = quoteTool.getSbMapItem(stkID);
				tradeTime.put(JSConst.TT_EXCHANGE, stkID.substring(0,  2));
				tradeTime.put(JSConst.TT_OPENHHMM, stkBase.getInt(JSConst.WATCH_OPEN_HHMM));
				tradeTime.put(JSConst.TT_CLOSEHHMM, stkBase.getInt(JSConst.WATCH_CLOSE_HHMM));
				tradeTime.put(JSConst.TT_RESTHHMM1, stkBase.getInt(JSConst.WATCH_REST_HHMM1));
				tradeTime.put(JSConst.TT_REOPENHHMM1, stkBase.getInt(JSConst.WATCH_REOPEN_HHMM1));
			}
			if (calcTradeTime >= 2400) {
				calcPeriodTime = jabxKernel.getMinutePeriod(tradeTime, period, calcTradeTime - 2400);
			}else {
				calcPeriodTime = jabxKernel.getMinutePeriod(tradeTime, period, calcTradeTime);
			}
		}

		if (periodDate < calcPeriodDate) {
			periodDate = calcPeriodDate;
			periodTime = calcPeriodTime;
			openPrice = trade.getTradePrice();
			highPrice = trade.getTradePrice();
			lowPrice = trade.getTradePrice();
			refOpenPrice = trade.getTradePrice();
			tradeVolume = trade.getTradeVolume();
			tradeAmount = 1L * trade.getTradePrice() * trade.getTradeVolume() * (1000 / quoteTool.getPowerByDecimal(stkID));
		}else if (periodDate == calcPeriodDate) {
			if (periodTime < calcPeriodTime) {
				periodTime = calcPeriodTime;
				openPrice = trade.getTradePrice();
				highPrice = trade.getTradePrice();
				lowPrice = trade.getTradePrice();
				refOpenPrice = trade.getTradePrice();
				tradeVolume = trade.getTradeVolume();
				tradeAmount = 1L * trade.getTradePrice() * trade.getTradeVolume() * (1000 / quoteTool.getPowerByDecimal(stkID));
			}else {
				tradePrice = trade.getTradePrice();
				if (trade.getTradePrice() > highPrice) {
					highPrice = trade.getTradePrice();
				}
				if (trade.getTradePrice() < lowPrice) {
					lowPrice = trade.getTradePrice();
				}
				tradeVolume += trade.getTradeVolume();
				tradeAmount += 1L * trade.getTradePrice() * trade.getTradeVolume() * (1000 / quoteTool.getPowerByDecimal(stkID));
			}
		}else {
			if (trade.getTradePrice() > highPrice) {
				highPrice = trade.getTradePrice();
			}
			if (trade.getTradePrice() < lowPrice) {
				lowPrice = trade.getTradePrice();
			}
			tradeVolume += trade.getTradeVolume();
			tradeAmount += 1L * trade.getTradePrice() * trade.getTradeVolume() * (1000 / quoteTool.getPowerByDecimal(stkID));
		}
		tradePrice = trade.getTradePrice();
	}

	/**
	 * 依據ZJABXTrade物件，計算ZJABXRealtimeTechnical物件之數據
	 * @param jabxKernel - JABXKernel
	 * @param quoteTool - JABXQuoteTool
	 * @param stkID - String
	 * @param trade - ZJABXTrade
	 */
	void calcData(JABXKernel jabxKernel, JABXQuoteTool quoteTool, String stkID, ZJABXTrade trade) {

		int cPeriod = 0;
		if (period > 200) {
			cPeriod = period - 200;
		}else if (period > 100) {
			cPeriod = period - 100;
		}else {
			cPeriod = period;
		}

		switch (cPeriod) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			calcData1(jabxKernel, quoteTool, cPeriod, stkID, trade);
			break;
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
			calcData2(jabxKernel, quoteTool, cPeriod, stkID, trade);
			break;
		}
	}
	
}
