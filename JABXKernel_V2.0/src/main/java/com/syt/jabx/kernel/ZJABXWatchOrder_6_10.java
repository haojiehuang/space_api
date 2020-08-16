package com.syt.jabx.kernel;

import com.syt.jabx.watch.IJABXOrder_6_10;

/**
 * 第6至10檔委買資訊之類別
 * @author Jason
 *
 */
final class ZJABXWatchOrder_6_10 implements IJABXOrder_6_10 {

	/**
	 * 總覧股票最佳買賣委託的物件
	 */
	private ZJABXBestOrder bestOrder;

	/**
	 * Constructor
	 */
	public ZJABXWatchOrder_6_10() {
		bestOrder = new ZJABXBestOrder(5);
	}

	/**
	 * 取得總覧股票最佳買賣委託的物件
	 * @return ZJABXBestOrder
	 */
	public ZJABXBestOrder getBestOrder() {
		return bestOrder;
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getBidPrice6()
	 */
	@Override
	public int getBidPrice6() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(0).getBidPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getBidVolume6()
	 */
	@Override
	public int getBidVolume6() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(0).getBidVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getBidPrice7()
	 */
	@Override
	public int getBidPrice7() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(1).getBidPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getBidVolume7()
	 */
	@Override
	public int getBidVolume7() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(1).getBidVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getBidPrice8()
	 */
	@Override
	public int getBidPrice8() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(2).getBidPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getBidVolume8()
	 */
	@Override
	public int getBidVolume8() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(2).getBidVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getBidPrice9()
	 */
	@Override
	public int getBidPrice9() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(3).getBidPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getBidVolume9()
	 */
	@Override
	public int getBidVolume9() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(3).getBidVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getBidPrice10()
	 */
	@Override
	public int getBidPrice10() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(4).getBidPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getBidVolume10()
	 */
	@Override
	public int getBidVolume10() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(4).getBidVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getAskPrice6()
	 */
	@Override
	public int getAskPrice6() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(0).getAskPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getAskVolume6()
	 */
	@Override
	public int getAskVolume6() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(0).getAskVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getAskPrice7()
	 */
	@Override
	public int getAskPrice7() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(1).getAskPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getAskVolume7()
	 */
	@Override
	public int getAskVolume7() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(1).getAskVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getAskPrice8()
	 */
	@Override
	public int getAskPrice8() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(2).getAskPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getAskVolume8()
	 */
	@Override
	public int getAskVolume8() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(2).getAskVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getAskPrice9()
	 */
	@Override
	public int getAskPrice9() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(3).getAskPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getAskVolume9()
	 */
	@Override
	public int getAskVolume9() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(3).getAskVolume();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getAskPrice10()
	 */
	@Override
	public int getAskPrice10() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(4).getAskPrice();
	}

	/**
	 * @see com.syt.jabx.watch.IJABXOrder_6_10#getAskVolume10()
	 */
	@Override
	public int getAskVolume10() {
		// TODO Auto-generated method stub
		return bestOrder.atIndex(4).getAskVolume();
	}

	/**
	 * 將類別中所用到的欄位設定為零或空白
	 */
	public void setDataToZeroOrSpace() {
		bestOrder.setDataToZeroOrSpace();
	}
}