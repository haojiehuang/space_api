package com.syt.jabx.kernel;

import java.util.List;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXTagValue;
import com.syt.jabx.consts.JS_Result;
import com.syt.jabx.notify.IJABXLog;

/**
 * 解析廣播訊息-產品訊息之類別
 * @author Jason
 *
 */
public final class JABXParseFreeMsgProduct extends IJABXParseBase implements IJABXParseBody {

	/**
	 * 應用程序核心管理物件
	 */
	private JABXKernel jabxKernel;

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
	 * @param jabxKernel - JABXKernel
	 * @param jabxLog - IJABXLog
	 * @param srcObj - Object
	 */
	public JABXParseFreeMsgProduct(JABXKernel jabxKernel, IJABXLog jabxLog, Object srcObj) {
		this.jabxKernel = jabxKernel;
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

		Object returnObj = null;
		String catalogNo = "";
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
					catalogNo = item.getCatalogNo();
					if (catalogNo.equals("P")) {
						ZJABXProductBulletinItem pItem = transferData_P(item);
						if (srcObj instanceof ZJABXProductBulletinOverview) {// I00
							ZJABXProductBulletinOverview pboObj = (ZJABXProductBulletinOverview)srcObj;
							pboObj.addItem(pItem);
						}
						returnObj = pItem;
					}else if (catalogNo.equals("A")) {
						ZJABXMarketReportItem mItem = transferData_A(item);
						if (srcObj instanceof ZJABXMarketReportOverview) {// I02
							ZJABXMarketReportOverview mrObj = (ZJABXMarketReportOverview)srcObj;
							mrObj.addItem(mItem);
						}
						returnObj = mItem;
					}else if (catalogNo.equals("C")) {
						ZJABXAdvertisementItem cItem = transferData_C(item);
						if (srcObj instanceof ZJABXAdvertisementOverview) {// I06
							ZJABXAdvertisementOverview aObj = (ZJABXAdvertisementOverview)srcObj;
							aObj.addItem(cItem);
						}
						returnObj = cItem;
					}
					break;
				}
			}

			if (offset == 0) {// 即時數據
				result.put(JS_Result.FUNC_ID, JABXConst.ABXFUN_REALTIME);
				result.put(JS_Result.STATUS_ID, JABXConst.ABXMSG_PRODUCT);
				result.put(JS_Result.PARAM, catalogNo);
				result.put(JS_Result.DATA, returnObj);
				result.put(JS_Result.NOTIFIED, true);

				List<String> list = jabxKernel.getListOfProductClass();
				boolean isInList = false;
				for (int i = 0, size = list.size();i < size;i++) {
					if (catalogNo.equals(list.get(i))) {
						isInList = true;
						break;
					}
				}
				if (isInList) {
					result.put(JS_Result.NOTIFIED, true);
				}else {
					result.put(JS_Result.NOTIFIED, false);
				}
			}

			item = null;
			tagValue = null;
		}catch(OutOfMemoryError e) {
			result.put(JS_Result.ERR_CODE, JABXErrCode.OUTOFMEMORY_ERROR);
			if (offset == 0) {
				result.put(JS_Result.NOTIFIED, true);
			}
			jabxLog.outputErrorAndLog("JABXParseFreeMsgProduct.parse()", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 將ZJABXRealtimeFormatItem轉換為IJABXProductBulletinItem
	 * @param item - ZJABXRealtimeFormatItem
	 * @return ZJABXProductBulletinItem
	 */
	private ZJABXProductBulletinItem transferData_P(ZJABXRealtimeFormatItem item) {
		ZJABXProductBulletinItem pbItem = new ZJABXProductBulletinItem();
		pbItem.setSerialNo(item.getSerialNo());
		pbItem.setProductID(item.getSourceNo());
		pbItem.setBulletinType(item.getMessageType2());
		pbItem.setDataType(item.getDataType());
		pbItem.setDataTime(item.getDataTime());
		pbItem.setTitle(item.getTitle());
		return pbItem;
	}

	/**
	 * 將ZJABXRealtimeFormatItem轉換為IJABXMarketReportItem
	 * @param item - ZJABXRealtimeFormatItem
	 * @return ZJABXMarketReportItem
	 */
	private ZJABXMarketReportItem transferData_A(ZJABXRealtimeFormatItem item) {
		ZJABXMarketReportItem ebItem = new ZJABXMarketReportItem();
		ebItem.setSerialNo(item.getSerialNo());
		ebItem.setClassID(item.getSourceNo());
		ebItem.setDataType(item.getDataType());
		ebItem.setDataTime(item.getDataTime());
		ebItem.setTitle(item.getTitle());
		return ebItem;
	}

	/**
	 * 將ZJABXRealtimeFormatItem轉換為IJABXAdvertisementItem
	 * @param item - ZJABXRealtimeFormatItem
	 * @return IJABXAdvertisementItem
	 */
	private ZJABXAdvertisementItem transferData_C(ZJABXRealtimeFormatItem item) {
		ZJABXAdvertisementItem adObj = new ZJABXAdvertisementItem();
		adObj.setSerialNo(item.getSerialNo());
		adObj.setClassID(item.getSourceNo());
		adObj.setDataTime(item.getDataTime());
		adObj.setUrl(item.getUrl());
		adObj.setData(item.getTitle());
		return adObj;
	}
}