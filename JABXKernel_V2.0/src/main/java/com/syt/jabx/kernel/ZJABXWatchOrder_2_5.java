package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXOrder_2_5;

/**
 * 第2至5檔委買資訊之類別
 * @author Jason
 *
 */
final class ZJABXWatchOrder_2_5 implements IJABXOrder_2_5 {

	/**
	 * 總覧股票最佳買賣委託的物件
	 */
	private ZJABXBestOrder bestOrder;

	/**
	 * Constructor
	 */
	public ZJABXWatchOrder_2_5() {
		bestOrder = new ZJABXBestOrder(4);
	}

	/**
	 * 取得總覧股票最佳買賣委託的物件
	 * @return ZJABXBestOrder
	 */
	public ZJABXBestOrder getBestOrder() {
		return bestOrder;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getBidPrice2()
	 */
	@Override
	public int getBidPrice2() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(0).getBidPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getBidVolume2()
	 */
	@Override
	public int getBidVolume2() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(0).getBidVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getBidPrice3()
	 */
	@Override
	public int getBidPrice3() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(1).getBidPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getBidVolume3()
	 */
	@Override
	public int getBidVolume3() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(1).getBidVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getBidPrice4()
	 */
	@Override
	public int getBidPrice4() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(2).getBidPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getBidVolume4()
	 */
	@Override
	public int getBidVolume4() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(2).getBidVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getBidPrice5()
	 */
	@Override
	public int getBidPrice5() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(3).getBidPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getBidVolume5()
	 */
	@Override
	public int getBidVolume5() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(3).getBidVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getAskPrice2()
	 */
	@Override
	public int getAskPrice2() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(0).getAskPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getAskVolume2()
	 */
	@Override
	public int getAskVolume2() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(0).getAskVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getAskPrice3()
	 */
	@Override
	public int getAskPrice3() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(1).getAskPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getAskVolume3()
	 */
	@Override
	public int getAskVolume3() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(1).getAskVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getAskPrice4()
	 */
	@Override
	public int getAskPrice4() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(2).getAskPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getAskVolume4()
	 */
	@Override
	public int getAskVolume4() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(2).getAskVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getAskPrice5()
	 */
	@Override
	public int getAskPrice5() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(3).getAskPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_2_5#getAskVolume5()
	 */
	@Override
	public int getAskVolume5() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(3).getAskVolume();
	}

	/**
	 * 將類別中所用到的欄位設定為零或空白
	 */
	public void setDataToZeroOrSpace() {
		bestOrder.setDataToZeroOrSpace();
	}
}