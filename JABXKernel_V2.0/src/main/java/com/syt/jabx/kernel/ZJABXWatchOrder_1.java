package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXOrder_1;

/**
 * 取得第一檔委買賣的類別
 * @author Jason
 *
 */
final class ZJABXWatchOrder_1 implements IJABXOrder_1 {

	/**
	 * 股票最佳買賣委託項目的物件
	 */
	private ZJABXBestOrderItem item;

	/**
	 * Constructor
	 */
	public ZJABXWatchOrder_1() {
		item = new ZJABXBestOrderItem();
	}

	/**
	 * 取得股票最佳買賣委託項目的物件
	 * @return ZJABXBestOrderItem
	 */
	public ZJABXBestOrderItem getItem() {
		return item;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_1#getBidPrice1()
	 */
	@Override
	public int getBidPrice1() {
		// TODO Auto-generated method stub
		return item.getBidPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_1#getBidVolume1()
	 */
	@Override
	public int getBidVolume1() {
		// TODO Auto-generated method stub
		return item.getBidVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_1#getAskPrice1()
	 */
	@Override
	public int getAskPrice1() {
		// TODO Auto-generated method stub
		return item.getAskPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_1#getAskVolume1()
	 */
	@Override
	public int getAskVolume1() {
		// TODO Auto-generated method stub
		return item.getAskVolume();
	}

	/**
	 * 將類別中所用到的欄位設定為零或空白
	 */
	public void setDataToZeroOrSpace() {
		if (item != null) {
			item.setDataToZeroOrSpace();
		}
	}
}
