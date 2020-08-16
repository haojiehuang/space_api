package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import com.syt.jabx.query.IJABXWarrantRelative;
import com.syt.jabx.query.IJABXWarrantRelativeItem;

/**
 * 權證相關類別
 * @author jason
 *
 */
final class ZJABXWarrantRelative implements IJABXWarrantRelative {

	/**
	 * 權證履約價
	 */
	private int strikePrice;

	/**
	 * 權證換股比率
	 */
	private double converRatio;

	/**
	 * 權證到期日
	 */
	private int maturityDate;

	/**
	 * 儲存權證標的項目的List物件
	 */
	private List<IJABXWarrantRelativeItem> list;

	/**
	 * Constructor
	 */
	public ZJABXWarrantRelative() {
		list = new ArrayList<IJABXWarrantRelativeItem>();
	}

	/**
	 * @see com.syt.jabx.query.IJABXWarrantRelative#getStrikePrice()
	 */
	@Override
	public int getStrikePrice() {
		// TODO Auto-generated method stub
		return strikePrice;
	}

	/**
	 * 設定權證履約價
	 * @param strikePrice - int
	 */
	public void setStrikePrice(int strikePrice) {
		this.strikePrice = strikePrice;
	}

	/**
	 * @see com.syt.jabx.query.IJABXWarrantRelative#getConverRatio()
	 */
	@Override
	public double getConverRatio() {
		// TODO Auto-generated method stub
		return converRatio;
	}

	/**
	 * 設定權證換股比率
	 * @param converRatio - double
	 */
	public void setConverRatio(double converRatio) {
		this.converRatio = converRatio;
	}

	/**
	 * @see com.syt.jabx.query.IJABXWarrantRelative#getMaturityDate()
	 */
	@Override
	public int getMaturityDate() {
		// TODO Auto-generated method stub
		return maturityDate;
	}

	/**
	 * 設定權證到期日
	 * @param maturityDate - int
	 */
	public void setMaturityDate(int maturityDate) {
		this.maturityDate = maturityDate;
	}

	/**
	 * @see com.syt.jabx.query.IJABXWarrantRelative#getCount()
	 */
	@Override
	public synchronized int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/**
	 * @see com.syt.jabx.query.IJABXWarrantRelative#atIndex(int)
	 */
	@Override
	public synchronized IJABXWarrantRelativeItem atIndex(int index) {
		// TODO Auto-generated method stub
		if (index < list.size()) {
			return list.get(index);
		}else {
			return null;
		}
	}

	/**
	 * 添加一權證標的項目至list中
	 * @param item - IJABXWarrantRelativeItem
	 */
	public synchronized void addItem(IJABXWarrantRelativeItem item) {
		list.add(item);
	}

	/**
	 * 清除list中之物件
	 */
	public synchronized void clear() {
		list.clear();
	}
}
