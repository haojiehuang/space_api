package com.syt.jabx.watch;


/**
 * 個股即時報價資訊的介面
 * @author jason
 *
 */
public interface IJABXQuoteItem {

	/**
	 * 取得證券代碼(前兩碼為交易所代碼)
	 * @return String
	 */
	public String getStkID();

	/**
	 * 取得交易所代碼
	 * @return String
	 */
	public String getExchangeID();

	/**
	 * 取得索引值(queryIndex)之物件
	 * @param queryIndex - int<br>
	 * 常數對應的物件如下：<br>
	 * JABXConst.ABXMSG_STKBASEINFO: IJABXStockBase<br>
	 * JABXConst.ABXMSG_STKREFINFO: IJABXStockReference<br>
	 * JABXConst.ABXMSG_ORDER_1: IJABXWatchOrder_1<br>
	 * JABXConst.ABXMSG_ORDER_2_5: IJABXWatchOrder_2_5<br>
	 * JABXConst.ABXMSG_ORDER_6_10: IJABXWatchOrder_6_10<br>
	 * JABXConst.ABXMSG_TRADE: IJABXTrade<br>
	 * JABXConst.ABXMSG_TOTREFINFO: IJABXWatchTotRefInfo<br>
	 * JABXConst.ABXMSG_1MININFO: IJABX1Minute<br>
	 * JABXConst.ABXMSG_OTHERS: IJABXWatchOthers<br>
	 * JABXConst.ABXMSG_STATISTIC: IJABXWatchStatistic<br>
	 * JABXConst.ABXMSG_EXCHANGESTATUS: IJABXWatchExchangeStatus<br>
	 * JABXConst.ABXMSG_BROKERQUEUE: IJABXBrokerQueueCollection<br>
	 * JABXConst.ABXMSG_DETAILTRADE: IJABXWatchDetailTrade<br>
	 * JABXConst.ABXMSG_DETAILORDER: IJABXWatchDetailOrder<br>
	 * JABXConst.ABXMSG_OLDLOT: IJABXWatchOldLot<br>
	 * JABXConst.ABXMSG_VIRTUALTRADE: IJABXWatchVirtualTrade<br>
	 * JABXConst.ABXMSG_1SECSNAPSHOT: IJABXWSecondSnapshot<br>
	 * JABXConst.ABXMSG_TRADE_OVERVIEW: IJABXWatchTradeOverview<br>
	 * JABXConst.ABXMSG_TOTREFINF_OVERVIEW: IJABXWatchTotRefInfoOverview<br>
	 * JABXConst.ABXMSG_STATISTIC_OVERVIEW: IJABXWatchStatisticOverview<br>
	 * JABXConst.ABXMSG_DETAILTRADE_OVERVIEW: IJABXWatchDetailTradeOverview<br>
	 * JABXConst.ABXMSG_DETAILORDER_OVERVIEW: IJABXWatchDetailOrderOverview<br>
	 * JABXConst.ABXMSG_MINUTETRADE_OVERVIEW: IJABXMinuteTradeOverview<br>
	 * JABXConst.ABXMSG_PRICETRADE_OVERVIEW: IJABXPriceTradeOverview
	 * @return Object
	 */
	public Object queryInterface(int queryIndex);
}
