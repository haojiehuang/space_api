package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.syt.jabx.watch.IJABXPriceTradeItem;
import com.syt.jabx.watch.IJABXPriceTradeOverview;
import com.syt.jabx.watch.IJABXTrade;

/**
 * 成交價量明細索引類別
 * @author jason
 *
 */
final class ZJABXPriceTradeOverview implements IJABXPriceTradeOverview {

	/**
	 * 成交序號
	 */
	private int seqNo;

	/**
	 * 儲放ZJABXPriceTradeItem之List物件
	 */
	private List<ZJABXPriceTradeItem> list;

	/**
	 * Constructor
	 */
	public ZJABXPriceTradeOverview() {
		list = new ArrayList<ZJABXPriceTradeItem>();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXPriceTradeOverview#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXPriceTradeOverview#atIndex(int)
	 */
	@Override
	public synchronized IJABXPriceTradeItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 添加一IJABXPriceTradeItem至list中
	 * @param item - IJABXPriceTradeItem
	 */
	public synchronized void addItem(ZJABXPriceTradeItem item) {
		list.add(item);
		Collections.sort(list);
	}

	/**
	 * 依據IJABXTrade內容添加一IJABXPriceTradeItem至list中
	 * @param trade - IJABXTrade
	 */
	public synchronized void addItem(IJABXTrade trade) {
		boolean inList = false;
		ZJABXPriceTradeItem ptObj = null;
		for (int i = 0, size = list.size();i < size;i++) {
			ptObj = (ZJABXPriceTradeItem)list.get(i);
			if (trade.getTradePrice() == ptObj.getTradePrice()) {
				inList = true;
				break;
			}
		}
		if (inList) {
			ptObj.setTradeVolume(trade.getTradeVolume() + ptObj.getTradeVolume());
			if (trade.getVolumeInOutFlag() == 1) {
				ptObj.setInVolume(trade.getTradeVolume() + ptObj.getInVolume());
			}else if (trade.getVolumeInOutFlag() == 2) {
				ptObj.setOutVolume(trade.getTradeVolume() + ptObj.getOutVolume());
			}
		}else {
			ptObj = new ZJABXPriceTradeItem();
			ptObj.setTradePrice(trade.getTradePrice());
			ptObj.setTradeVolume(trade.getTradeVolume());
			if (trade.getVolumeInOutFlag() == 1) {
				ptObj.setInVolume(trade.getTradeVolume());
			}else if (trade.getVolumeInOutFlag() == 2) {
				ptObj.setOutVolume(trade.getTradeVolume());
			}
			list.add(ptObj);
		}
		Collections.sort(list);
	}

	/**
	 * 取得成交序號
	 * @return int
	 */
	public int getSeqNo() {
		return seqNo;
	}

	/**
	 * 設定成交序號
	 * @param seqNo - int
	 */
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * 清除list中之物件
	 */
	public synchronized void clear() {
		seqNo = 0;
		list.clear();
	}
}
