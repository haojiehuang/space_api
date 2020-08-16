package com.syt.jabx.watch;

/**
 * 總覽個股價量關係明細的介面
 * @author Jason
 *
 */
public interface IJABXTradePV {

	/**
	 * 取得個股價量關係明細的筆數
	 * @return int
	 */
	public int getCount();
	
	/**
	 * 取得在索引值(index)之IJABXTradePV
	 * @param index - int(索引值，從 0 開始)
	 * @return IJABXTradePV
	 */
	public IJABXTradePVItem atIndex(int index);
}
