package com.syt.jabx.kernel;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析廣播訊息-交易所訊息之類別
 * @author Jason
 *
 */
public final class JABXParseFreeMsgExchange extends IJABXParseBase implements IJABXParseBody {

	/**
	 * 輸出訊息及Log之物件
	 */
	private IJABXLog jabxLog;

	/**
	 * 儲存資料源
	 */
	private Object srcObj;

	/**
	 * Constructor
	 * @param jabxLog - IJABXLog
	 * @param srcObj - Object
	 */
	public JABXParseFreeMsgExchange(IJABXLog jabxLog, Object srcObj) {
		this.jabxLog = jabxLog;
		this.srcObj = srcObj;
	}

	/**
	 * @see com.syt.jabx.kernel.IJABXParseBody#parse(com.syt.jabx.kernel.ZJABXFixProc, JSONObject, byte[], com.syt.jabx.kernel.JABXCtrlHeader, int)
	 */
	@Override
	public void parse(final ZJABXFixProc fixProc, final JSONObject result,
			byte[] dataAry, JABXCtrlHeader ctrlHeader, int offset) {
		// TODO Auto-generated method stub
		logSb.delete(0, logSb.length());
		logSb.append(this.getClass().getSimpleName()).append(".parse()");
		try {
			if (offset != 0) {
				jabxLog.outputInfoAndLog(logSb.toString(), new String(dataAry, JABXConst.ABX_CHARSET));
			}else {
				jabxLog.outputRealtimeMsg(logSb.toString(), new String(dataAry, JABXConst.ABX_CHARSET));
			}
		}catch(Exception e) {
		}

		ZJABXExchangeBulletinItem ebItem = new ZJABXExchangeBulletinItem();
		try{
			ZJABXRealtimeFormatItem item = null;
			JABXTagValue tagValue;
			int nRead = offset;// 已讀取byte數
			int dataLength = 0;
			int fixDataLength = 0;// Fix資料讀取長度(若為0，則為正常讀法，不為0，則讀取固定長度之數據)
			int tag;
			while (nRead < dataAry.length) {
				tagValue = new JABXTagValue();
				nRead += fixProc.readOneFixField(dataAry, nRead, fixDataLength, tagValue);
				tag = tagValue.getTag();
				if (tagValue.getValue() == null) {
					continue;
				}
				switch (tag) {
				case JABXConst.FIX_DATA_LENGTH_NUM:// 資料長度
					dataLength = fixProc.getIntValue(tagValue.getValue());//訊息長度
					if (dataLength != 0) {
						fixDataLength = dataLength + String.valueOf(JABXConst.FIX_DATA_CONTENT).length() + 2;// 讀取回補訊息之Tag(總長度length + 長度(JABXConst.FIX_DATA_CONTENT=加最後一分隔符號(0x01)))
					}
					break;
				default:
					fixDataLength = 0;// 訊息內容讀完，要將fixDataLength重置為0
					item = ZJABXParseRealMessageFormat.getInstance(tagValue.getValue());
					ebItem = transferData(item);
					if (srcObj instanceof ZJABXExchangeBulletinOverview) {// N00或N01
						ZJABXExchangeBulletinOverview ebObj = (ZJABXExchangeBulletinOverview)srcObj;
						ebObj.addItem(ebItem);
					}
					break;
				}
			}

			if (offset == 0) {
				result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REALTIME);
				result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_EXCHANGEBULLETIN);
				result.put(JS_Result.DATA, ebItem);
				result.put(JS_Result.NOTIFIED, true);
			}

			item = null;
			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			if (offset == 0) {
				result.put(JS_Result.NOTIFIED, true);
			}
			jabxLog.outputErrorAndLog("JABXParseFreeMsgExchange.parse()", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 將ZJABXRealtimeFormatItem轉換為IJABXExchangeBulletinItem
	 * @param item - ZJABXRealtimeFormatItem
	 * @return ZJABXExchangeBulletinItem
	 */
	private ZJABXExchangeBulletinItem transferData(ZJABXRealtimeFormatItem item) {
		ZJABXExchangeBulletinItem ebItem = new ZJABXExchangeBulletinItem();
		ebItem.setSerialNo(item.getSerialNo());
		ebItem.setExchangeID(item.getSourceNo());
		ebItem.setClassID(item.getMessageType());
		ebItem.setBulletinType(item.getMessageType2());
		ebItem.setDataType(item.getDataType());
		List<String> listOfStockID = new ArrayList<String>();
		String relationStock = item.getRelationStock();
		if (relationStock != null && !relationStock.equals("")) {
			String[] stockIDAry = item.getRelationStock().split("\\.");
			for (int i = 0;i < stockIDAry.length;i++) {
				listOfStockID.add(stockIDAry[i]);
			}
		}
		ebItem.setListOfStockID(listOfStockID);
		ebItem.setDataTime(item.getDataTime());
		ebItem.setTitle(item.getTitle());
		return ebItem;
	}
}