package com.syt.jabx.core;

import com.syt.jabx.kernel.IJABXParseBody;
import com.syt.jabx.kernel.IJABXParseFactory;
import com.syt.jabx.kernel.JABXAbyKeyCtrl;
import com.syt.jabx.kernel.JABXConst;
import com.syt.jabx.kernel.JABXCtrlHeader;
import com.syt.jabx.kernel.JABXKernel;
import com.syt.jabx.kernel.JABXParseFreeMsgBroadcast;
import com.syt.jabx.kernel.JABXParseFreeMsgExchange;
import com.syt.jabx.kernel.JABXParseFreeMsgLine;
import com.syt.jabx.kernel.JABXParseFreeMsgNews;
import com.syt.jabx.kernel.JABXParseFreeMsgProduct;
import com.syt.jabx.kernel.JABXParseFreeMsgTrade;
import com.syt.jabx.kernel.JABXParseHeartbit;
import com.syt.jabx.kernel.JABXParseI00;
import com.syt.jabx.kernel.JABXParseI01;
import com.syt.jabx.kernel.JABXParseI02;
import com.syt.jabx.kernel.JABXParseI03;
import com.syt.jabx.kernel.JABXParseI06;
import com.syt.jabx.kernel.JABXParseI10;
import com.syt.jabx.kernel.JABXParseN00;
import com.syt.jabx.kernel.JABXParseN01;
import com.syt.jabx.kernel.JABXParseN02;
import com.syt.jabx.kernel.JABXParseN10;
import com.syt.jabx.kernel.JABXParseN11;
import com.syt.jabx.kernel.JABXParseN12;
import com.syt.jabx.kernel.JABXParseP00;
import com.syt.jabx.kernel.JABXParseP01;
import com.syt.jabx.kernel.JABXParseP02;
import com.syt.jabx.kernel.JABXParseP03;
import com.syt.jabx.kernel.JABXParseP04;
import com.syt.jabx.kernel.JABXParseP06;
import com.syt.jabx.kernel.JABXParseP07;
import com.syt.jabx.kernel.JABXParseP10;
import com.syt.jabx.kernel.JABXParseP40;
import com.syt.jabx.kernel.JABXParseP49;
import com.syt.jabx.kernel.JABXParseP50;
import com.syt.jabx.kernel.JABXParseP51;
import com.syt.jabx.kernel.JABXParseP52;
import com.syt.jabx.kernel.JABXParseP53;
import com.syt.jabx.kernel.JABXParseP56;
import com.syt.jabx.kernel.JABXParseP57;
import com.syt.jabx.kernel.JABXParseP58;
import com.syt.jabx.kernel.JABXParseP59;
import com.syt.jabx.kernel.JABXParseP90;
import com.syt.jabx.kernel.JABXParseR00;
import com.syt.jabx.kernel.JABXParseR01;
import com.syt.jabx.kernel.JABXParseR06;
import com.syt.jabx.kernel.JABXParseR07;
import com.syt.jabx.kernel.JABXParseR10;
import com.syt.jabx.kernel.JABXParseR13;
import com.syt.jabx.kernel.JABXParseR14;
import com.syt.jabx.kernel.JABXParseR15;
import com.syt.jabx.kernel.JABXParseR16;
import com.syt.jabx.kernel.JABXParseR50;
import com.syt.jabx.kernel.JABXParseR51;
import com.syt.jabx.kernel.JABXParseR52;
import com.syt.jabx.kernel.JABXParseR96;
import com.syt.jabx.kernel.JABXParseR99;
import com.syt.jabx.kernel.JABXParseS10;
import com.syt.jabx.kernel.JABXParseSmartMsgMaster;
import com.syt.jabx.kernel.JABXParseSmartMsgRank;
import com.syt.jabx.kernel.JABXParseSmartMsgShort;
import com.syt.jabx.kernel.JABXParseSmartMsgWarn;
import com.syt.jabx.kernel.JABXParseTradeBills;
import com.syt.jabx.kernel.JABXParseU00;
import com.syt.jabx.kernel.JABXParseU02;
import com.syt.jabx.kernel.JABXParseU03;
import com.syt.jabx.kernel.JABXParseU04;
import com.syt.jabx.kernel.JABXParseU05;
import com.syt.jabx.kernel.JABXParseU06;
import com.syt.jabx.kernel.JABXParseU07;
import com.syt.jabx.kernel.JABXParseU08;
import com.syt.jabx.kernel.JABXParseU09;
import com.syt.jabx.kernel.JABXParseU11;
import com.syt.jabx.kernel.JABXParseU12;
import com.syt.jabx.kernel.JABXParseU20;
import com.syt.jabx.kernel.JABXParseWatch1MinInfo;
import com.syt.jabx.kernel.JABXParseWatch1SecSnapshot;
import com.syt.jabx.kernel.JABXParseWatchBrokerQueue;
import com.syt.jabx.kernel.JABXParseWatchDetailOrder;
import com.syt.jabx.kernel.JABXParseWatchDetailTrade;
import com.syt.jabx.kernel.JABXParseWatchExchangeStatus;
import com.syt.jabx.kernel.JABXParseWatchOldLot;
import com.syt.jabx.kernel.JABXParseWatchOrder_1;
import com.syt.jabx.kernel.JABXParseWatchOrder_2_5;
import com.syt.jabx.kernel.JABXParseWatchOrder_6_10;
import com.syt.jabx.kernel.JABXParseWatchOthers;
import com.syt.jabx.kernel.JABXParseWatchStatistic;
import com.syt.jabx.kernel.JABXParseWatchStkBaseInfo;
import com.syt.jabx.kernel.JABXParseWatchStkRefInfo;
import com.syt.jabx.kernel.JABXParseWatchTotRefInfo;
import com.syt.jabx.kernel.JABXParseWatchTrade;
import com.syt.jabx.kernel.JABXParseWatchVirtualTrade;
import com.syt.jabx.kernel.JABXQuoteTool;
import com.syt.jabx.kernel.JABXUserTool;
import com.syt.jabx.notify.IJABXLog;

/**
 * 實作Socket解碼之工廠類別
 * @author jason
 *
 */
final class ZJABXParseFactory implements IJABXParseFactory {

	/**
	 * getParseBody所使用之Lock
	 */
	private static byte[] bodyLock = new byte[0];

	/**
	 * 解析Heartbit之物件
	 */
	private JABXParseHeartbit parseHeartbit;

	/**
	 * 用戶相關工具物件
	 */
	private JABXUserTool userTool;

	/**
	 * 行情資訊工具物件
	 */
	private JABXQuoteTool quoteTool;
	
	/**
	 * Constructor
	 */
	public ZJABXParseFactory() {
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXParseBody#parse(com.syt.jabx.kernel.JABXFixProc, com.syt.jabx.kernel.JABXResult, byte[], JABXCtrlHeader, int)
	 */
	@Override
	public IJABXParseBody getParseBody(final JABXKernel jabxKernel, 
			final IJABXLog jabxLog, 
			final JABXCtrlHeader ctrlHeader, 
			final Object srcObj) {
		
		byte mainType = ctrlHeader.getByMsgMainType();
		int subType = ctrlHeader.getByMsgSubType();
		String funcCode;

		IJABXParseBody parseBody = null;
		
		synchronized(bodyLock) {
			parseBody = null;
		
			switch (mainType) {
			case JABXConst.ABUS_MAINTYPE_CONTROL:
				JABXAbyKeyCtrl abyKeyCtrl;
				switch (subType) {
				case JABXConst.ABUS_CONTROL_HEARTBIT:// Heartbit
					if (parseHeartbit == null) {
						parseHeartbit = new JABXParseHeartbit(jabxLog);
					}
					parseBody = parseHeartbit;
					break;
				case JABXConst.ABUS_CONTROL_LOGONRESP:// 登入回傳訊息
					parseBody = new JABXParseU00(jabxKernel, jabxLog, userTool);
					break;
				case JABXConst.ABUS_CONTROL_SUBSCRIBERESP:// 訂閱回傳訊息
					parseBody = new JABXParseR00(jabxKernel, jabxLog, quoteTool, this);
					break;
				case JABXConst.ABUS_CONTROL_REBUILDRESP:// Rebuild回傳訊息
				case JABXConst.ABUS_CONTROL_NORMALRESP:// Normal回傳訊息
					abyKeyCtrl = (JABXAbyKeyCtrl)ctrlHeader.getAbyKey();
					funcCode = abyKeyCtrl.getAbyFuncCode();
					parseBody = getFuncCodeParseBody(jabxKernel, jabxLog, funcCode);
					break;
				}
				break;
			case JABXConst.ABUS_MAINTYPE_WATCHLIST:// 即時訊息
				switch (subType) {
				case JABXConst.ABUS_WATCH_STKBASEINFO:// 股票基本資料訊息
					parseBody = new JABXParseWatchStkBaseInfo(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_STKREFINFO:// 股票相關資料訊息
					parseBody = new JABXParseWatchStkRefInfo(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_ORDER_1:// 第一檔委買賣
					parseBody = new JABXParseWatchOrder_1(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_ORDER_2_5:// 第2至5檔委買資訊
					parseBody = new JABXParseWatchOrder_2_5(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_ORDER_6_10:// 第6至10檔委買資訊
					parseBody = new JABXParseWatchOrder_6_10(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_TRADE:// 成交資訊
					parseBody = new JABXParseWatchTrade(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_TOTREFINFO:// 總委買賣量筆及成交筆資訊
					parseBody = new JABXParseWatchTotRefInfo(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_1MININFO:// 其他一分鐘資訊
					parseBody = new JABXParseWatch1MinInfo(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_OTHERS:// 其他報價資訊
					parseBody = new JABXParseWatchOthers(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_STATISTIC:// 交易所統計訊息
					parseBody = new JABXParseWatchStatistic(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_EXCHANGESTATUS:// 交易所交易狀態訊息
					parseBody = new JABXParseWatchExchangeStatus(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_BROKERQUEUE:// BrokerQueue香港訊息
					parseBody = new JABXParseWatchBrokerQueue(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_DETAILTRADE:// 逐筆成交訊息
					parseBody = new JABXParseWatchDetailTrade(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_DETAILORDER:// 逐筆委託訊息
					parseBody = new JABXParseWatchDetailOrder(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_OLDLOT:// 零股買賣成交訊息(Just for TW)
					parseBody = new JABXParseWatchOldLot(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_VIRTUALTRADE:// 盤前虛擬撮合訊息(Just for SSE,SZSE,HK)
					parseBody = new JABXParseWatchVirtualTrade(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				case JABXConst.ABUS_WATCH_1SECSNAPSHOT:// 一秒快照訊息
					parseBody = new JABXParseWatch1SecSnapshot(jabxKernel, jabxLog, quoteTool, srcObj);
					break;
				}
				break;
			case JABXConst.ABUS_MAINTYPE_TABLE:
				break;
			case JABXConst.ABUS_MAINTYPE_SMART:
				switch (subType) {
				case JABXConst.ABUS_SMARTMSG_SHORT:// 短線精靈資料訊息
					parseBody = new JABXParseSmartMsgShort(jabxLog, srcObj);
					break;
				case JABXConst.ABUS_SMARTMSG_RANK:// 即時排名訊息
					parseBody = new JABXParseSmartMsgRank(jabxLog, srcObj);
					break;
				case JABXConst.ABUS_SMARTMSG_MASTER:// 主力大單訊息
					parseBody = new JABXParseSmartMsgMaster(jabxLog, srcObj);
					break;
				case JABXConst.ABUS_SMARTMSG_WARN:// 用戶警示訊息
					parseBody = new JABXParseSmartMsgWarn(jabxLog);
					break;
				}
				break;
			case JABXConst.ABUS_MAINTYPE_FREEFORMAT:
				switch (subType) {
				case JABXConst.ABUS_FREEMSG_BROADCAST:// 廣播訊息
					parseBody = new JABXParseFreeMsgBroadcast(jabxLog);
					break;
				case JABXConst.ABUS_FREEMSG_EXCHANGE:// 交易所訊息
					parseBody = new JABXParseFreeMsgExchange(jabxLog, srcObj);
					break;
				case JABXConst.ABUS_FREEMSG_NEWS:// 新聞訊息
					parseBody = new JABXParseFreeMsgNews(jabxLog, srcObj);
					break;
				case JABXConst.ABUS_FREEMSG_PRODUCT:// 產品訊息
					parseBody = new JABXParseFreeMsgProduct(jabxKernel, jabxLog, srcObj);
					break;
				case JABXConst.ABUS_FREEMSG_TRADE:// 交易回報訊息
					parseBody = new JABXParseFreeMsgTrade(jabxLog);
					break;
				case JABXConst.ABUS_FREEMSG_LINE:// 即時指標訊息
					parseBody = new JABXParseFreeMsgLine(jabxLog);
					break;
				}
				break;
			}
		
			return parseBody;
		}
	}

	/**
	 * 依funcCode(功能代碼)傳回解析數據之物件
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 * @param funcCode - String(功能代碼)
	 * @return IJABXParseBody
	 */
	private IJABXParseBody getFuncCodeParseBody(JABXKernel jabxKernel, IJABXLog jabxLog, String funcCode) {
		IJABXParseBody parseBody = null;
		switch (funcCode) {
		case "I00":// 解析I00回傳數據
			parseBody = new JABXParseI00(jabxKernel, jabxLog, this);
			break;
		case "I01":// 解析I01回傳數據
			parseBody = new JABXParseI01(jabxKernel, jabxLog);
			break;
		case "I02":// 解析I02回傳數據
			parseBody = new JABXParseI02(jabxKernel, jabxLog, this);
			break;
		case "I03":// 解析I03回傳數據
			parseBody = new JABXParseI03(jabxKernel, jabxLog);
			break;
		case "I06":// 解析I06回傳數據
			parseBody = new JABXParseI06(jabxKernel, jabxLog, this);
			break;
		case "I10":// 解析I10回傳數據
			parseBody = new JABXParseI10(jabxLog);
			break;
		case "N00":// 解析N00回傳數據
			parseBody = new JABXParseN00(jabxKernel, jabxLog, this);
			break;
		case "N01":// 解析N01回傳數據
			parseBody = new JABXParseN01(jabxKernel, jabxLog, this);
			break;
		case "N02":// 解析N02回傳數據
			parseBody = new JABXParseN02(jabxKernel, jabxLog);
			break;
		case "N10":// 解析N10回傳數據
			parseBody = new JABXParseN10(jabxKernel, jabxLog, this);
			break;
		case "N11":// 解析N11回傳數據
			parseBody = new JABXParseN11(jabxKernel, jabxLog, this);
			break;
		case "N12":// 解析N12回傳數據
			parseBody = new JABXParseN12(jabxKernel, jabxLog);
			break;
		case "P00":// 解析P00回傳數據
			parseBody = new JABXParseP00(jabxKernel, jabxLog, quoteTool);
			break;
		case "P01":// 解析P01回傳數據
			parseBody = new JABXParseP01(jabxLog);
			break;
		case "P02":// 解析P02回傳數據
			parseBody = new JABXParseP02(jabxLog);
			break;
		case "P03":// 解析P03回傳數據
			parseBody = new JABXParseP03(jabxKernel, jabxLog);
			break;
		case "P04":// 解析P04回傳數據
			parseBody = new JABXParseP04(jabxKernel, jabxLog, quoteTool);
			break;
		case "P06":// 解析P06回傳數據
			parseBody = new JABXParseP06(jabxKernel, jabxLog);
			break;
		case "P07":// 解析P07回傳數據
			parseBody = new JABXParseP07(jabxKernel, jabxLog);
			break;
		case "P10":// 解析P10回傳數據
			parseBody = new JABXParseP10(jabxKernel, jabxLog, quoteTool);
			break;
		case "P40":// 解析P40回傳數據
			parseBody = new JABXParseP40(jabxLog);
			break;
		case "P49":// 解析P49回傳數據
			parseBody = new JABXParseP49(jabxLog);
			break;
		case "P50":// 解析P50回傳數據
			parseBody = new JABXParseP50(jabxLog);
			break;
		case "P51":// 解析P51回傳數據
			parseBody = new JABXParseP51(jabxKernel, jabxLog, quoteTool);
			break;
		case "P52":// 解析P52回傳數據
			parseBody = new JABXParseP52(jabxKernel, jabxLog);
			break;
		case "P53":// 解析P53回傳數據
			parseBody = new JABXParseP53(jabxKernel, jabxLog);
			break;
		case "P56":// 解析P56回傳數據
			parseBody = new JABXParseP56(jabxKernel, jabxLog);
			break;
		case "P57":// 解析P57回傳數據
			parseBody = new JABXParseP57(jabxLog);
			break;
		case "P58":// 解析P58回傳數據
			parseBody = new JABXParseP58(jabxKernel, jabxLog);
			break;
		case "P59":// 解析P59回傳數據
			parseBody = new JABXParseP59(jabxKernel, jabxLog);
			break;
		case "P90":// 解析P90回傳數據
			parseBody = new JABXParseP90(jabxKernel, jabxLog);
			break;
		case "R01":// 解析R01回傳數據
			parseBody = new JABXParseR01(jabxKernel, jabxLog, quoteTool, this);
			break;
		case "R06":// 解析R06回傳數據
			parseBody = new JABXParseR06(jabxKernel, jabxLog, quoteTool, this);
			break;
		case "R07":// 解析R07回傳數據
			parseBody = new JABXParseR07(jabxKernel, jabxLog, quoteTool, this);
			break;
		case "R10":// 解析R10回傳數據
			parseBody = new JABXParseR10(jabxKernel, jabxLog, quoteTool, this);
			break;
		case "R13":// 解析R13回傳數據
			parseBody = new JABXParseR13(jabxKernel, jabxLog, quoteTool, this);
			break;
		case "R14":// 解析R14回傳數據
			parseBody = new JABXParseR14(jabxKernel, jabxLog, quoteTool, this);
			break;
		case "R15":// 解析R15回傳數據
			parseBody = new JABXParseR15(jabxKernel, jabxLog, quoteTool, this);
			break;
		case "R16":// 解析R16回傳數據
			parseBody = new JABXParseR16(jabxKernel, jabxLog, quoteTool, this);
			break;
		case "R50":// 解析R50回傳數據
			parseBody = new JABXParseR50(jabxKernel, jabxLog, this);
			break;
		case "R51":// 解析R51回傳數據
			parseBody = new JABXParseR51(jabxKernel, jabxLog, this);
			break;
		case "R52":// 解析R52回傳數據
			parseBody = new JABXParseR52(jabxKernel, jabxLog, this);
			break;
		case "R96":// 解析R96回傳數據
			parseBody = new JABXParseR96(jabxKernel, jabxLog, this);
			break;
		case "R99":// 解析R99回傳數據
			parseBody = new JABXParseR99(jabxLog);
			break;
		case "S10":
			parseBody = new JABXParseS10(jabxLog);
			break;
		case "U02":// 解析U02回傳數據
			parseBody = new JABXParseU02(jabxKernel, jabxLog);
			break;
		case "U03":// 解析U03回傳數據
			parseBody = new JABXParseU03(jabxLog);
			break;
		case "U04":// 解析U04回傳數據
			parseBody = new JABXParseU04(jabxKernel, jabxLog);
			break;
		case "U05":// 解析U05回傳數據
			parseBody = new JABXParseU05(jabxKernel, jabxLog);
			break;
		case "U06":// 解析U06回傳數據
			parseBody = new JABXParseU06(jabxKernel, jabxLog);
			break;
		case "U07":// 解析U07回傳數據
			parseBody = new JABXParseU07(jabxKernel, jabxLog);
			break;
		case "U08":// 解析U08回傳數據
			parseBody = new JABXParseU08(jabxKernel, jabxLog);
			break;
		case "U09":// 解析U09回傳數據
			parseBody = new JABXParseU09(jabxKernel, jabxLog);
			break;
		case "U11":// 解析U11回傳數據
			parseBody = new JABXParseU11(jabxKernel, jabxLog);
			break;
		case "U12":// 解析U12回傳數據
			parseBody = new JABXParseU12(jabxKernel, jabxLog);
			break;
		case "U20":// 解析U20回傳數據
			parseBody = new JABXParseU20(jabxLog);
			break;
		default:
			if (funcCode.startsWith("B") || funcCode.startsWith("T")) {//解析B及T開頭的回傳數據
				parseBody = new JABXParseTradeBills(jabxLog);
			}
		}
		return parseBody;
	}

	/**
	 * 設定用戶相關工具物件
	 * @param userTool - JABXUserTool
	 */
	void setUserTool(JABXUserTool userTool) {
		this.userTool = userTool;
	}

	/**
	 * 設定行情資訊工具物件
	 * @param quoteTool - JABXQuoteTool
	 */
	void setQuoteTool(JABXQuoteTool quoteTool) {
		this.quoteTool = quoteTool;
	}
}
