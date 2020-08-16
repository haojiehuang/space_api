package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syt.jabx.kmodel.IJABXWarningItem;
import com.syt.jabx.kmodel.IJABXWarningOverview;

/**
 * 總覧警示設定項目之類別
 * @author jason
 *
 */
final class ZJABXWarningOverview implements IJABXWarningOverview {

	/**
	 * 記錄IJABXStkInfo之List物件
	 */
	private List<String> stkList;

	/**
	 * list所使用Lock物件 
	 */
	private byte[] listLock = new byte[0];

	/**
	 * 記錄股票代碼之Map物件
	 */
	private Map<String, String> stkMap;

	/**
	 * 以股票代碼為Key記錄List<IJABXWarningItem>之物件
	 */
	private Map<String, List<IJABXWarningItem>> listMap;

	/**
	 * Constructor
	 */
	public ZJABXWarningOverview() {
		stkList = new ArrayList<String>();
		stkMap = new HashMap<String, String>();
		listMap = new HashMap<String, List<IJABXWarningItem>>();
	}

	/**
	 * @see com.syt.jabx.kmodel.query.IJABXWarningOverview#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		synchronized (listLock) {
			return stkList.size();
		}
	}

	/**
	 * @see com.syt.jabx.kmodel.query.IJABXWarningOverview#atIndex(int)
	 */
	@Override
	public String atIndex(int index) {
		// TODO Auto-generated method stub
		synchronized (listLock) {
			if (index >= stkList.size()) {
				return null;
			}else {
				return stkList.get(index);
			}
			
		}
	}

	/**
	 * @see com.syt.jabx.kmodel.query.IJABXWarningOverview#getList(String)
	 */
	@Override
	public List<IJABXWarningItem> getList(String stkID) {
		// TODO Auto-generated method stub
		return listMap.get(stkID);
	}

	/**
	 * 添加IJABXWarningItem物件至Map中，以及添加list物件
	 * @param item
	 */
	public void addItem(IJABXWarningItem item) {
		if (item == null) {
			return;
		}
		
		synchronized (listLock) {
			String stkID = stkMap.get(item.getStkID());
			if (stkID == null) {
				stkID = item.getStkID();
				stkList.add(stkID);
			}
			stkMap.put(stkID, stkID);
			List<IJABXWarningItem> list = listMap.get(stkID);
			if (list == null) {
				list = new ArrayList<IJABXWarningItem>();
			}
			listMap.put(stkID, list);
			list.add(item);
		}
	}
}
