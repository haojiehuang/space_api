package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.watch.IJABXMinuteTradeItem;
import com.syt.jabx.watch.IJABXMinuteTradeOverview;
import com.syt.jabx.watch.IJABXQuoteItem;
import com.syt.jabx.watch.IJABXTradeItem;

/**
 * 股票成交分鐘明細索引的類別
 * @author Jason
 *
 */
final class ZJABXMinuteTradeOverview implements IJABXMinuteTradeOverview {

	/**
	 * 行情資訊工具物件
	 */
	private JABXQuoteTool quoteTool;

	/**
	 * 儲存IJABXMinuteTradeItem物件之List容器
	 */
	private List<IJABXMinuteTradeItem> itemList;

	/**
	 * 以時間存放IJABXMinuteTradeItem物件之Map容器
	 */
	private Map<Integer, IJABXMinuteTradeItem> itemMap;

	/**
	 * 總量
	 */
	private long totalVolume;

	/**
	 * 總金額
	 */
	private long amount;

	/**
	 * 股票訊息(用於保留資料)之類別
	 */
	private ZJABXReservedStkInfo stkInfo;

	/**
	 * Constructor
	 * @param quoteTool - JABXQuoteTool
	 */
	public ZJABXMinuteTradeOverview(JABXQuoteTool quoteTool) {
		this.quoteTool = quoteTool; 
		itemList = new ArrayList<IJABXMinuteTradeItem>();
		itemMap = new HashMap<Integer, IJABXMinuteTradeItem>();
		totalVolume = 0;
		amount = 0;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXMinuteTradeOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXMinuteTradeOverview#atIndex(int)
	 */
	@Override
	public synchronized IJABXMinuteTradeItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < itemList.size()) {
			return itemList.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXMinuteTradeItem至儲存容器中
	 * @param quoteItem - IJABXQuoteItem
	 * @param item - IJABXMinuteTradeItem
	 */
	public synchronized void addRebuildItem(IJABXQuoteItem quoteItem, ZJABXMinuteTradeItem item) {
		if (itemList.size() == 0) {
			addRebuildFirstItem(quoteItem, item);
			return;
		}
		if (stkInfo == null) {
			stkInfo = quoteTool.getStkInfo(quoteItem.getStkID());
		}
		int openHHMM = stkInfo.getOpenTime();
		int closeHHMM = stkInfo.getCloseTime();
		int tradeTimeInt = Integer.parseInt(item.getTradeTime());
		if (closeHHMM >= openHHMM) {// 早盤(當收盤時間大於等於開盤時間)
			if (tradeTimeInt > closeHHMM) {
				tradeTimeInt = closeHHMM;
			}
		}else {// 午盤(當收盤時間小於開盤時間)
			if (tradeTimeInt > closeHHMM && tradeTimeInt < openHHMM) {
				tradeTimeInt = closeHHMM;
			}else if (tradeTimeInt <= closeHHMM) {
				tradeTimeInt += 2400;
			}
		}
		item.setTradeDate(String.valueOf(quoteTool.getTradeDate(quoteItem.getStkID(), tradeTimeInt)));
		ZJABXMinuteTradeItem mtItem = (ZJABXMinuteTradeItem)itemMap.get(tradeTimeInt);
		if (mtItem == null) {
			ZJABXMinuteTradeItem copyItem;
			ZJABXMinuteTradeItem lastTradeItem = (ZJABXMinuteTradeItem)itemList.get(itemList.size() - 1);
			int lastTradeTimeInt = Integer.parseInt(lastTradeItem.getTradeTime());
 			int startInt;
 			if (lastTradeTimeInt % 100 == 59) {
 				startInt = lastTradeTimeInt + 41;
 			}else {
 				startInt = lastTradeTimeInt + 1;
 			}
 			int endTimeInt = tradeTimeInt;
 			
 			if (closeHHMM < openHHMM) {
 				if (startInt <= closeHHMM) {
 					startInt += 2400;
 				}
 				if (endTimeInt <= closeHHMM) {
 					endTimeInt += 2400;
 				}
 			}

 			StringBuffer tradeTimeSb = new StringBuffer();
 			int mm;
 			for (int i = startInt;i <= endTimeInt;i++) {
 				mm = i % 100;
 				if (mm == 60) {
 					i += 39;
 					continue;
 				}
 				if (i != endTimeInt) {
 					copyItem = new ZJABXMinuteTradeItem();
 					tradeTimeSb.delete(0, tradeTimeSb.length());
 					copyItem.setTradeDate(String.valueOf(quoteTool.getTradeDate(quoteItem.getStkID(), i)));
 					copyItem.setTradeTime(formatTime(tradeTimeSb, i).toString());
 					copyItem.setTradeAmount(0);
 					copyItem.setTradeVolume(0);
 					copyItem.setChangeRate("");
 					copyItem.setHighPrice(lastTradeItem.getHighPrice());
 					copyItem.setLowPrice(lastTradeItem.getLowPrice());
 					copyItem.setOpenPrice(lastTradeItem.getOpenPrice());
 					copyItem.setTradePrice(lastTradeItem.getTradePrice());
 					copyItem.setAmount(amount);
 					copyItem.setTotalVolume(totalVolume);
 					itemList.add(copyItem);
 					itemMap.put(i, copyItem);
 				}else {
 					amount += item.getTradeAmount();
 		 			totalVolume += item.getTradeVolume();
 		 			itemList.add(item);
 					itemMap.put(tradeTimeInt, item);
 					ZJABXMinuteTradeItem zItem = (ZJABXMinuteTradeItem)item;
 					zItem.setTotalVolume(totalVolume);
 					zItem.setAmount(amount);
 				}

 			}
		}else {
			if (mtItem.getHighPrice() < item.getHighPrice()) {
				mtItem.setHighPrice(item.getHighPrice());
			}
			if (mtItem.getLowPrice() > item.getLowPrice()) {
				mtItem.setLowPrice(item.getLowPrice());
			}
			mtItem.setTradePrice(item.getTradePrice());
			long tradeAmount = mtItem.getTradeAmount();
			long tmpAmount = item.getTradeAmount();
			amount += tmpAmount;
			mtItem.setTradeAmount(tradeAmount + tmpAmount);
			long tradeVolume = mtItem.getTradeVolume() + item.getTradeVolume();
			totalVolume += item.getTradeVolume();
			mtItem.setTradeVolume(tradeVolume);
			mtItem.setTotalVolume(totalVolume);
			mtItem.setAmount(amount);
		}
	}

	/**
	 * 添加第一筆數據
	 * @param quoteItem - IJABXQuoteItem
	 * @param item - IJABXMinuteTradeItem
	 */
	public void addRebuildFirstItem(IJABXQuoteItem quoteItem, ZJABXMinuteTradeItem item) {
		if (stkInfo == null) {
			stkInfo = quoteTool.getStkInfo(quoteItem.getStkID());
		}
		int openHHMM = stkInfo.getOpenTime();
		int closeHHMM = stkInfo.getCloseTime();
		int firstTime = Integer.parseInt(item.getTradeTime());
		item.setTradeDate(String.valueOf(quoteTool.getTradeDate(quoteItem.getStkID(), firstTime)));
		if (firstTime == openHHMM) {
			amount = item.getTradeAmount();
			totalVolume = item.getTradeVolume();
			item.setAmount(amount);
			item.setTotalVolume(totalVolume);
			itemList.add(item);
			itemMap.put(firstTime, item);
		}else if (firstTime > openHHMM) {
			StringBuffer tradeTimeSb = new StringBuffer();
			ZJABXMinuteTradeItem tmpItem;
			int mm;
			for (int i = openHHMM;i <= firstTime;i++) {
				mm = i % 100;
				if (mm == 60) {
					i += 39;
					continue;
				}
				tmpItem = new ZJABXMinuteTradeItem();
				if (i != firstTime) {
					tmpItem.setTradeDate(String.valueOf(quoteTool.getTradeDate(quoteItem.getStkID(), i)));
					tmpItem.setOpenPrice(stkInfo.getOpenRefPrice());
					tradeTimeSb.delete(0, tradeTimeSb.length());
					tmpItem.setTradeTime(formatTime(tradeTimeSb, i).toString());
					tmpItem.setHighPrice(tmpItem.getOpenPrice());
					tmpItem.setLowPrice(tmpItem.getOpenPrice());
					tmpItem.setTradePrice(tmpItem.getOpenPrice());
					itemList.add(tmpItem);
					itemMap.put(i, tmpItem);
				}else {
					item.setTradeDate(String.valueOf(quoteTool.getTradeDate(quoteItem.getStkID(), firstTime)));
					amount = item.getTradeAmount();
					totalVolume = item.getTradeVolume();
					item.setAmount(amount);
					item.setTotalVolume(totalVolume);
					itemList.add(item);
					itemMap.put(firstTime, item);
				}
			}
		}else {
			if (firstTime < closeHHMM) {
				int endTime = firstTime + 2400;
				StringBuffer tradeTimeSb = new StringBuffer();
				ZJABXMinuteTradeItem tmpItem;
				int mm;
				for (int i = openHHMM;i <= endTime;i++) {
					mm = i % 100;
					if (mm == 60) {
						i += 39;
						continue;
					}
					tmpItem = new ZJABXMinuteTradeItem();
					if (i != endTime) {
						tmpItem.setTradeDate(String.valueOf(quoteTool.getTradeDate(quoteItem.getStkID(), i)));
						tmpItem.setOpenPrice(stkInfo.getOpenRefPrice());
						tradeTimeSb.delete(0, tradeTimeSb.length());
						tmpItem.setTradeTime(formatTime(tradeTimeSb, i).toString());
						tmpItem.setHighPrice(tmpItem.getOpenPrice());
						tmpItem.setLowPrice(tmpItem.getOpenPrice());
						tmpItem.setTradePrice(tmpItem.getOpenPrice());
						itemList.add(tmpItem);
						itemMap.put(i, tmpItem);
					}else {
						item.setTradeDate(String.valueOf(quoteTool.getTradeDate(quoteItem.getStkID(), endTime)));
						amount = item.getTradeAmount();
						totalVolume = item.getTradeVolume();
						item.setAmount(amount);
						item.setTotalVolume(totalVolume);
						itemList.add(item);
						itemMap.put(endTime, item);
					}
				}
			}
		}
	}

	/**
	 * 添加一IJABXTradeItem數據至儲存容器中
	 * @param item
	 */
	public synchronized void addRealtimeItem(IJABXQuoteItem quoteItem, IJABXTradeItem item) {

		// 依股票代碼及交易時間取得對應分時之時間
		int tradeTime = quoteTool.getTradeTime(quoteItem.getStkID(), item.getTradeTime());

		StringBuffer tradeTimeSb = new StringBuffer();
		ZJABXMinuteTradeItem mtItem = (ZJABXMinuteTradeItem)itemMap.get(tradeTime);
		if (mtItem == null) {
			mtItem = new ZJABXMinuteTradeItem();
			mtItem.setTradeDate(String.valueOf(quoteTool.getTradeDate(quoteItem.getStkID(), tradeTime)));
			mtItem.setTradeTime(formatTime(tradeTimeSb, tradeTime).toString());
			mtItem.setHighPrice(item.getTradePrice());
			mtItem.setLowPrice(item.getTradePrice());
			mtItem.setOpenPrice(item.getTradePrice());
			mtItem.setTradePrice(item.getTradePrice());
			long tradeAmount = 1L * item.getTradePrice() * item.getTradeVolume() * (1000 / quoteTool.getPowerByDecimal(quoteItem.getStkID()));
			mtItem.setTradeAmount(tradeAmount);
			mtItem.setTradeVolume(item.getTradeVolume());
			addRebuildItem(quoteItem, mtItem);
		}else {
			if (mtItem.getHighPrice() < item.getTradePrice()) {
				mtItem.setHighPrice(item.getTradePrice());
			}
			if (mtItem.getLowPrice() > item.getTradePrice()) {
				mtItem.setLowPrice(item.getTradePrice());
			}
			mtItem.setTradePrice(item.getTradePrice());
			long tradeAmount = mtItem.getTradeAmount();
			long tmpAmount = 1L * item.getTradePrice() * item.getTradeVolume() * (1000 / quoteTool.getPowerByDecimal(quoteItem.getStkID()));
			amount += tmpAmount;
			mtItem.setTradeAmount(tradeAmount + tmpAmount);
			long tradeVolume = mtItem.getTradeVolume() + item.getTradeVolume();
			totalVolume += item.getTradeVolume();
			mtItem.setTradeVolume(tradeVolume);
		}
		mtItem.setAmount(amount);
		mtItem.setTotalVolume(totalVolume);
	}

	/**
	 * 清除itemList及itemMap中之物件
	 */
	public synchronized void clear() {
		itemList.clear();
		itemMap.clear();
		totalVolume = 0;
		amount = 0;
	}

	/**
	 * 格式化時分(HHmm)
	 * @param tradeTimeSb - StringBuffer
	 * @param tradeTime - int(HHmm)
	 * @return StringBuffer
	 */
	private StringBuffer formatTime(final StringBuffer tradeTimeSb, final int tradeTime) {
		int hour = tradeTime / 100;
		int minute = tradeTime % 100;
		if (hour >= 24) {// 轉換跨日之時間
			hour -= 24;
		}
		if (hour < 10) {
			tradeTimeSb.append('0');
		}
		tradeTimeSb.append(hour);
		if (minute < 10) {
			tradeTimeSb.append('0');
		}
		tradeTimeSb.append(minute);

		return tradeTimeSb;
	}
}