package com.syt.jabx.kernel;

import org.json.JSONObject;

import com.syt.jabx.watch.IJABXQuoteItem;

/**
 * 實作IJABXQuoteItem(個股即時報價資訊的介面)之類別
 * @author Jason
 *
 */
final class ZJABXQuoteItem implements IJABXQuoteItem {

	/**
	 * 證券代碼(前兩碼為交易所代碼)
	 */
	private String stkID;

	/**
	 * JSONObject(股票基本資料)
	 */
	private JSONObject stkBaseInfo;

	/**
	 * JSONObject(股票相關資料的物件)
	 */
	private JSONObject stkRefInfo;

	/**
	 * 取得第一檔委買賣的物件
	 */
	private ZJABXWatchOrder_1 order_1;

	/**
	 * 取得第2至5檔委買賣的物件
	 */
	private ZJABXWatchOrder_2_5 order_2_5;

	/**
	 * 取得第6至10檔委買賣的物件
	 */
	private ZJABXWatchOrder_6_10 order_6_10;

	/**
	 * 股票之即時成交資訊的物件
	 */
	private ZJABXTrade trade;

	/**
	 * 即時總委買賣量筆及成交筆資訊的物件
	 */
	private ZJABXWatchTotRefInfo totRefInfo;
	
	/**
	 * 股票之其他一分鐘資訊的物件
	 */
	private ZJABX1Minute oneMinInfo;

	/**
	 * 股票其他報價資訊的物件
	 */
	private ZJABXWatchOthers others;

	/**
	 * 交易所統計訊息的物件
	 */
	private ZJABXWatchStatistic statistic;

	/**
	 * 交易所交易狀態訊息的物件
	 */
	private JSONObject exchangeStatus;

	/**
	 * 逐筆成交訊息(目前只support SSE,SZSE)的物件
	 */
	private ZJABXWatchDetailTrade detailTrade;

	/**
	 * 逐筆委託訊息(目前只support SZSE)的物件
	 */
	private ZJABXWatchDetailOrder detailOrder;

	/**
	 * 零股買賣成交訊息(Just for TW)的物件
	 */
	private ZJABXWatchOldLot oldLot;

	/**
	 * 盤前虛擬撮合訊息(Just for SSE,SZSE,HK)的物件
	 */
	private ZJABXWatchVirtualTrade virtualTrade;

	/**
	 * 一秒快照訊息的物件
	 */
	private ZJABXWSecondSnapshot oneSecSnapshot;

	/**
	 * 總覽股票成交分筆的物件
	 */
	private ZJABXWatchTradeOverview tradeOverview;

	/**
	 * 總覽股票成交分筆的物件
	 */
	private ZJABXWatchTotRefInfoOverview totRefInfoOverview;

	/**
	 * 總覽總委買賣量筆及成交筆資訊的物件
	 */
	private ZJABXWatchStatisticOverview statisticOverview;

	/**
	 * 總覽逐筆成交訊息(目前只support SSE,SZSE)的物件
	 */
	private ZJABXWatchDetailTradeOverview detailTradeOverview;

	/**
	 * 總覽逐筆委託訊息(目前只support SZSE)的物件
	 */
	private ZJABXWatchDetailOrderOverview detailOrderOverview;

	/**
	 * 總覧股票分鐘明細的物件
	 */
	private ZJABXMinuteTradeOverview minuteTradeOverview;

	/**
	 * 經紀排位集合的物件
	 */
	private ZJABXBrokerQueueCollection brokerQueueCollection;

	/**
	 * 成交價量明細索引物件
	 */
	private ZJABXPriceTradeOverview priceTradeOverview;

	/**
	 * @see com.syt.jabx.watch.IJABXQuoteItem#getStkID()
	 */
	@Override
	public String getStkID() {
		// TODO Auto-generated method stub
		return stkID;
	}

	/**
	 * 設定證券代碼
	 * @param stkID - String
	 */
	public void setStkID(String stkID) {
		this.stkID = stkID;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXQuoteItem#getExchangeID()
	 */
	@Override
	public String getExchangeID() {
		// TODO Auto-generated method stub
		if (stkID != null && stkID.length() > 2) {
			return stkID.substring(0, 2);
		}else {
			return "";
		}
	}

	/**
	 * 取得股票基本資料的JSONObject
	 * @return
	 */
	public JSONObject getStkBaseInfo() {
		return stkBaseInfo;
	}

	/**
	 * 設定股票基本資料的JSONObject
	 * @param stkBaseInfo - JSONObject
	 */
	public void setStkBaseInfo(JSONObject stkBaseInfo) {
		this.stkBaseInfo = stkBaseInfo;
	}

	/**
	 * 取得股票相關資料的JSONObject
	 * @return
	 */
	public JSONObject getStkRefInfo() {
		return stkRefInfo;
	}

	/**
	 * 設定股票相關資料的JSONObject
	 * @param stkRefInfo - JSONObject
	 */
	public void setStkRefInfo(JSONObject stkRefInfo) {
		this.stkRefInfo = stkRefInfo;
	}

	/**
	 * 取得第一檔委買賣的物件
	 * @return ZJABXOrder_1
	 */
	public ZJABXWatchOrder_1 getOrder_1() {
		return order_1;
	}

	/**
	 * 設定第一檔委買賣的物件
	 * @param order_1 - ZJABXOrder_1
	 */
	public void setOrder_1(ZJABXWatchOrder_1 order_1) {
		this.order_1 = order_1;
	}

	/**
	 * 取得第2至5檔委買賣的物件
	 * @return ZJABXOrder_2_5
	 */
	public ZJABXWatchOrder_2_5 getOrder_2_5() {
		return order_2_5;
	}

	/**
	 * 設定第2至5檔委買賣的物件
	 * @param order_2_5 - ZJABXOrder_2_5
	 */
	public void setOrder_2_5(ZJABXWatchOrder_2_5 order_2_5) {
		this.order_2_5 = order_2_5;
	}

	/**
	 * 取得第6至10檔委買賣的物件
	 * @return ZJABXOrder_6_10
	 */
	public ZJABXWatchOrder_6_10 getOrder_6_10() {
		return order_6_10;
	}

	/**
	 * 設定第6至10檔委買賣的物件
	 * @param order_6_10 - ZJABXOrder_6_10
	 */
	public void setOrder_6_10(ZJABXWatchOrder_6_10 order_6_10) {
		this.order_6_10 = order_6_10;
	}

	/**
	 * 取得股票之成交資訊物件
	 * @return ZJABXTrade
	 */
	public ZJABXTrade getTrade() {
		// TODO Auto-generated method stub
		return trade;
	}

	/**
	 * 設定股票之成交資訊物件
	 * @param trade - ZJABXTrade
	 */
	public void setTrade(ZJABXTrade trade) {
		this.trade = trade;
	}

	/**
	 * 取得即時總覽總委買賣量筆及成交筆資訊的物件
	 * @return ZJABXTotRefInfo
	 */
	public ZJABXWatchTotRefInfo getTotRefInfo() {
		return totRefInfo;
	}

	/**
	 * 設定即時總覽總委買賣量筆及成交筆資訊的物件
	 * @param totRefInfo - ZJABXTotRefInfo
	 */
	public void setTotRefInfo(ZJABXWatchTotRefInfo totRefInfo) {
		this.totRefInfo = totRefInfo;
	}

	/**
	 * 取得股票之其他一分鐘資訊物件
	 * @return ZJABXOneMinInfo
	 */
	public ZJABX1Minute getOneMinInfo() {
		return oneMinInfo;
	}

	/**
	 * 設定股票之其他一分鐘資訊物件
	 * @param oneMinInfo - ZJABXOneMinInfo
	 */
	public void setOneMinInfo(ZJABX1Minute oneMinInfo) {
		this.oneMinInfo = oneMinInfo;
	}

	/**
	 * 取得其他報價資訊的物件
	 * @return ZJABXOthers
	 */
	public ZJABXWatchOthers getOthers() {
		// TODO Auto-generated method stub
		return others;
	}

	/**
	 * 設定其他報價資訊的物件
	 * @param others - ZJABXOthers
	 */
	public void setOthers(ZJABXWatchOthers others) {
		this.others = others;
	}

	/**
	 * 取得交易所統計訊息的物件
	 * @return ZJABXStatistic
	 */
	public ZJABXWatchStatistic getStatistic() {
		// TODO Auto-generated method stub
		return statistic;
	}

	/**
	 * 設定交易所統計訊息的物件
	 * @param statistic - ZJABXStatistic
	 */
	public void setStatistic(ZJABXWatchStatistic statistic) {
		this.statistic = statistic;
	}

	/**
	 * 取得交易所交易狀態訊息的JSONObject
	 * @return JSONObject
	 */
	public JSONObject getExchangeStatus() {
		return exchangeStatus;
	}

	/**
	 * 設定交易所交易狀態訊息的JSONObject
	 * @param exchangeStatus - JSONObject
	 */
	public void setExchangeStatus(JSONObject exchangeStatus) {
		this.exchangeStatus = exchangeStatus;
	}

	/**
	 * 取得逐筆成交訊息(目前只support SSE,SZSE)的物件
	 * @return ZJABXDetailTrade
	 */
	public ZJABXWatchDetailTrade getDetailTrade() {
		return detailTrade;
	}

	/**
	 * 設定逐筆成交訊息(目前只support SSE,SZSE)的物件
	 * @param detailTrade - ZJABXDetailTrade
	 */
	public void setDetailTrade(ZJABXWatchDetailTrade detailTrade) {
		this.detailTrade = detailTrade;
	}

	/**
	 * 取得逐筆委託訊息(目前只support SZSE)的物件
	 * @return ZJABXDetailOrder
	 */
	public ZJABXWatchDetailOrder getDetailOrder() {
		return detailOrder;
	}

	/**
	 * 設定逐筆委託訊息(目前只support SZSE)的物件
	 * @param detailOrder - ZJABXDetailOrder
	 */
	public void setDetailOrder(ZJABXWatchDetailOrder detailOrder) {
		this.detailOrder = detailOrder;
	}

	/**
	 * 取得零股買賣成交訊息(Just for TW)的物件
	 * @return ZJABXOldLot
	 */
	public ZJABXWatchOldLot getOldLot() {
		return oldLot;
	}

	/**
	 * 設定零股買賣成交訊息(Just for TW)的物件
	 * @param oldLot - ZJABXOldLot
	 */
	public void setOldLot(ZJABXWatchOldLot oldLot) {
		this.oldLot = oldLot;
	}

	/**
	 * 取得盤前虛擬撮合訊息(Just for SSE,SZSE,HK)的物件
	 * @return ZJABXVirtualTrade
	 */
	public ZJABXWatchVirtualTrade getVirtualTrade() {
		return virtualTrade;
	}

	/**
	 * 設定盤前虛擬撮合訊息(Just for SSE,SZSE,HK)的物件
	 * @param virtualTrade - ZJABXVirtualTrade
	 */
	public void setVirtualTrade(ZJABXWatchVirtualTrade virtualTrade) {
		this.virtualTrade = virtualTrade;
	}

	/**
	 * 取得一秒快照訊息的物件
	 * @return ZJABX1SecSnapshot
	 */
	public ZJABXWSecondSnapshot getOneSecSnapshot() {
		return oneSecSnapshot;
	}

	/**
	 * 設定一秒快照訊息的物件
	 * @param oneSecSnapshot - ZJABX1SecSnapshot
	 */
	public void setOneSecSnapshot(ZJABXWSecondSnapshot oneSecSnapshot) {
		this.oneSecSnapshot = oneSecSnapshot;
	}

	/**
	 * 取得總覽股票成交分筆的物件
	 * @return ZJABXWatchTradeDir
	 */
	public ZJABXWatchTradeOverview getTradeOverview() {
		return tradeOverview;
	}

	/**
	 * 設定總覽股票成交分筆的物件
	 * @param tradeOverview - ZJABXWatchTradeOverview
	 */
	public void setTradeOverview(ZJABXWatchTradeOverview tradeOverview) {
		this.tradeOverview = tradeOverview;
	}

	/**
	 * 取得總覽總委買賣量筆及成交筆資訊的物件
	 * @return ZJABXWatchTotRefInfoOverview
	 */
	public ZJABXWatchTotRefInfoOverview getTotRefInfoOverview() {
		return totRefInfoOverview;
	}

	/**
	 * 設定總覽總委買賣量筆及成交筆資訊的物件
	 * @param totRefInfoOverview - ZJABXWatchTotRefInfoOverview
	 */
	public void setTotRefInfoOverview(ZJABXWatchTotRefInfoOverview totRefInfoOverview) {
		this.totRefInfoOverview = totRefInfoOverview;
	}

	/**
	 * 取得總覽總委買賣量筆及成交筆資訊的物件
	 * @return ZJABXWatchStatisticOverview
	 */
	public ZJABXWatchStatisticOverview getStatisticOverview() {
		return statisticOverview;
	}

	/**
	 * 設定總覽總委買賣量筆及成交筆資訊的物件
	 * @param statisticOverview - ZJABXWatchStatisticOverview
	 */
	public void setStatisticOverview(ZJABXWatchStatisticOverview statisticOverview) {
		this.statisticOverview = statisticOverview;
	}

	/**
	 * 取得總覽逐筆成交訊息(目前只support SSE,SZSE)的物件
	 * @return ZJABXWatchDetailTradeOverview
	 */
	public ZJABXWatchDetailTradeOverview getDetailTradeOverview() {
		return detailTradeOverview;
	}

	/**
	 * 設定總覽逐筆成交訊息(目前只support SSE,SZSE)的物件
	 * @param detailTradeOverview - ZJABXWatchDetailTradeOverview
	 */
	public void setDetailTradeOverview(ZJABXWatchDetailTradeOverview detailTradeOverview) {
		this.detailTradeOverview = detailTradeOverview;
	}

	/**
	 * 取得總覽逐筆委託訊息(目前只support SZSE)的物件
	 * @return ZJABXWatchDetailOrderOverview
	 */
	public ZJABXWatchDetailOrderOverview getDetailOrderOverview() {
		return detailOrderOverview;
	}

	/**
	 * 設定總覽逐筆委託訊息(目前只support SZSE)的物件
	 * @param detailOrderOverview - ZJABXWatchDetailOrderOverview
	 */
	public void setDetailOrderOverview(ZJABXWatchDetailOrderOverview detailOrderOverview) {
		this.detailOrderOverview = detailOrderOverview;
	}

	/**
	 * 取得總覽股票分鐘明細的物件
	 * @return ZJABXMinuteTradeOverview
	 */
	public ZJABXMinuteTradeOverview getMinuteTradeOverview() {
		return minuteTradeOverview;
	}

	/**
	 * 設定總覽股票分鐘明細的物件
	 * @param tradeDir - ZJABXMinuteTradeOverview
	 */
	public void setMinuteTradeOverview(ZJABXMinuteTradeOverview minuteTradeOverview) {
		this.minuteTradeOverview = minuteTradeOverview;
	}

	/**
	 * 取得經紀排位集合的物件
	 * @return ZJABXBrokerQueueCollection
	 */
	public ZJABXBrokerQueueCollection getBrokerQueueCollection() {
		return brokerQueueCollection;
	}

	/**
	 * 設定經紀排位集合的物件
	 * @param brokerQueueCollection - ZJABXBrokerQueueCollection
	 */
	public void setBrokerQueueCollection(ZJABXBrokerQueueCollection brokerQueueCollection) {
		this.brokerQueueCollection = brokerQueueCollection;
	}

	/**
	 * 取得成交價量明細索引物件
	 * @return ZJABXPriceTradeOverview
	 */
	public ZJABXPriceTradeOverview getPriceTradeOverview() {
		return priceTradeOverview;
	}

	/**
	 * 設定成交價量明細索引物件
	 * @param priceTradeOverview - ZJABXPriceTradeOverview
	 */
	public void setPriceTradeOverview(ZJABXPriceTradeOverview priceTradeOverview) {
		this.priceTradeOverview = priceTradeOverview;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXQuoteItem#queryInterface(int)
	 */
	@Override
	public Object queryInterface(int queryIndex) {
		Object obj = null;
		switch (queryIndex) {
		case JABXConst.ABXMSG_STKBASEINFO:
			obj = stkBaseInfo;
			break;
		case JABXConst.ABXMSG_STKREFINFO:
			obj = stkRefInfo;
			break;
		case JABXConst.ABXMSG_ORDER_1:
			obj = order_1;
			break;
		case JABXConst.ABXMSG_ORDER_2_5:
			obj = order_2_5;
			break;
		case JABXConst.ABXMSG_ORDER_6_10:
			obj = order_6_10;
			break;
		case JABXConst.ABXMSG_TRADE:
			obj = trade;
			break;
		case JABXConst.ABXMSG_TOTREFINFO:
			obj = totRefInfo;
			break;
		case JABXConst.ABXMSG_1MININFO:
			obj = oneMinInfo;
			break;
		case JABXConst.ABXMSG_OTHERS:
			obj = others;
			break;
		case JABXConst.ABXMSG_STATISTIC:
			obj = statistic;
			break;
		case JABXConst.ABXMSG_EXCHANGESTATUS:
			obj = exchangeStatus;
			break;
		case JABXConst.ABXMSG_BROKERQUEUE:
			obj = brokerQueueCollection;
			break;
		case JABXConst.ABXMSG_DETAILTRADE:
			obj = detailTrade;
			break;
		case JABXConst.ABXMSG_DETAILORDER:
			obj = detailOrder;
			break;
		case JABXConst.ABXMSG_OLDLOT:
			obj = oldLot;
			break;
		case JABXConst.ABXMSG_VIRTUALTRADE:
			obj = virtualTrade;
			break;
		case JABXConst.ABXMSG_1SECSNAPSHOT:
			obj = oneSecSnapshot;
			break;
		case JABXConst.ABXMSG_TRADE_OVERVIEW:
			obj = tradeOverview;
			break;
		case JABXConst.ABXMSG_TOTREFINF_OVERVIEW:
			obj = totRefInfoOverview;
			break;
		case JABXConst.ABXMSG_STATISTIC_OVERVIEW:
			obj = statisticOverview;
			break;
		case JABXConst.ABXMSG_DETAILTRADE_OVERVIEW:
			obj = detailTradeOverview;
			break;
		case JABXConst.ABXMSG_DETAILORDER_OVERVIEW:
			obj = detailOrderOverview;
			break;
		case JABXConst.ABXMSG_MINUTETRADE_OVERVIEW:
			obj = minuteTradeOverview;
			break;
		case JABXConst.ABXMSG_PRICETRADE_OVERVIEW:
			obj = priceTradeOverview;
			break;
		default:
			obj = null;
		}
		return obj;
	}
	
	public void clear() {

		if (order_1 != null) {
			order_1.setDataToZeroOrSpace();
		}
		if (order_2_5 != null) {
			order_2_5.setDataToZeroOrSpace();
		}
		if (order_6_10 != null) {
			order_6_10.setDataToZeroOrSpace();
		}
		if (trade != null) {
			trade.setDataToZeroOrSpace();
		}
		if (totRefInfo != null) {
			totRefInfo.setDataToZeroOrSpace();
		}
		if (oneMinInfo != null) {
			oneMinInfo.setDataToZeroOrSpace();
		}
		if (others != null) {
			others.setDataToZeroOrSpace();
		}
		if (statistic != null) {
			statistic.setDataToZeroOrSpace();
		}
		if (detailTrade != null) {
			detailTrade.setDataToZeroOrSpace();
		}
		if (detailOrder != null) {
			detailOrder.setDataToZeroOrSpace();
		}
		if (oldLot != null) {
			oldLot.setDataToZeroOrSpace();
		}
		if (virtualTrade != null) {
			virtualTrade.setDataToZeroOrSpace();
		}
		if (oneSecSnapshot != null) {
			oneSecSnapshot.setDataToZeroOrSpace();
		}
		if (tradeOverview != null) {
			tradeOverview.clear();
		}
		if (totRefInfoOverview != null) {
			totRefInfoOverview.clear();
		}
		if (statisticOverview != null) {
			statisticOverview.clear();
		}
		if (detailTradeOverview != null) {
			detailTradeOverview.clear();
		}
		if (detailOrderOverview != null) {
			detailOrderOverview.clear();
		}
		if (minuteTradeOverview != null) {
			minuteTradeOverview.clear();
		}
		if (brokerQueueCollection != null) {
			brokerQueueCollection.clear();
		}
		if (priceTradeOverview != null) {
			priceTradeOverview.clear();
		}
	}
}