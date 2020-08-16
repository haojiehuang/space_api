package com.syt.jabx.kernel;

import java.util.List;

import org.json.JSONObject;

import com.syt.jabx.bean.JABXAServer;
import com.syt.jabx.bean.JABXQuoteCondition;

/**
 * 行情資訊請求介面
 * @author jason
 *
 */
public interface IJABXQuoteRequest {

	/**
	 * 下載檔案
	 * @param jsonObj - JSONObject<br>
	 * 對應參數如下所示，括號中有'*'，代表必填:<br>
	 * (A).JSConst.CU_REQUESTID: int(*,API回覆碼)<br>
	 * (B).JSConst.DN_FILETYPE: int(*,下載檔案類型)<br>
	 * 下載檔案類型之常數及不同檔案類型須填入之參數，如下所示:<br>
	 * 1.JABXConst.ABXFUN_DOWNLOAD_BLOCKCLASS(下載自定(板塊)分類資料)<br>
	 * &nbsp;&nbsp;(C).JSConst.DN_FILENO: String(*,交易所/區域 代碼)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T4:興櫃<br>
	 * 2.JABXConst.ABXFUN_DOWNLOAD_CLASSRELATIONSTOCKLIST(下載分類關連股票索引資料)<br>
	 * &nbsp;&nbsp;(C).JSConst.DN_FILENO: String(*,交易所/區域 代碼)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T4:興櫃<br>
	 * 3.JABXConst.ABXFUN_DOWNLOAD_STKTYPETREE(下載分類樹資料)<br>
	 * &nbsp;&nbsp;(C).JSConst.DN_FILENO: String(*,分類代碼)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;0：可取得大分類<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;9000000：取得手機版分類<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;9010000：取得PC版分類<br>
	 * 4.JABXConst.ABXFUN_DOWNLOAD_EXCHANGELIST(下載交易所名稱資料檔)<br>
	 * &nbsp;&nbsp;此檔案類型無其它參數<br>
	 * 5.JABXConst.ABXFUN_DOWNLOAD_FORMULACLASS(下載公式分類資料檔)<br>
	 * &nbsp;&nbsp;(C).JSConst.DN_FILENO: String(*,公式總分類代碼，由後台定義。)<br>
	 * 6.JABXConst.ABXFUN_DOWNLOAD_FORMULALIST(下載公式名稱資料檔)<br>
	 * &nbsp;&nbsp;(C).JSConst.DN_FILENO: String(*,公式總分類代碼，由後台定義。)<br>
	 * 6.JABXConst.ABXFUN_DOWNLOAD_FUTUREOPTION(下載期權股名檔)<br>
	 * &nbsp;&nbsp;(C).JSConst.DN_FILENO: String(*,交易所代碼或區域代碼)<br>
	 * &nsbp;&nsbp;(D).JSConst.DN_ENCODING: String(*,股名檔編碼)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T: 繁體<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;C: 簡體<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;E: 英文<br>
	 * 7.JABXConst.ABXFUN_DOWNLOAD_OTHERCLASS(下載其他分類設定檔)<br>
	 * &nbsp;&nbsp;此檔案類型無其它參數<br>
	 * 8.JABXConst.ABXFUN_DOWNLOAD_PRODUCTCLASS(下載產品功能分類資料檔)<br>
	 * &nbsp;&nbsp;此檔案類型無其它參數<br>
	 * 9.JABXConst.ABXFUN_DOWNLOAD_SMARTRANKCLASS(下載即時排名分類資料)<br>
	 * &nbsp;&nbsp;此檔案類型無其它參數<br>
	 * 10.JABXConst.ABXFUN_DOWNLOAD_SMARTSHORTCLASS(下載智慧選股(短線精靈)分類資料)<br>
	 * &nbsp;&nbsp;此檔案類型無其它參數<br>
	 * 11.JABXConst.ABXFUN_DOWNLOAD_SPREADCLASS(下載價差交易分類資料)<br>
	 * &nbsp;&nbsp;此檔案類型無其它參數<br>
	 * 12.JABXConst.ABXFUN_DOWNLOAD_STOCKCLASS(下載商品分類資料)<br>
	 * &nbsp;&nbsp;此檔案類型無其它參數<br>
	 * 13.JABXConst.ABXFUN_DOWNLOAD_STOCKRELATIONCLASSLIST(下載股票關連分類列表)<br>
	 * &nbsp;&nbsp;(C).JSConst.DN_FILENO: String(*,交易所/區域 代碼)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T4:興櫃<br>
	 * 14.JABXConst.ABXFUN_DOWNLOAD_STOCKRELATIONSTOCKLIST(下載交易所1股票關連交易所2股票資料檔)<br>
	 * &nbsp;&nbsp;(C).JSConst.DN_FILENO: String(*,交易所/區域 代碼)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T4:興櫃<br>
	 * &nbsp;&nbsp;(D).JSConst.DN_FILENO2: String(*,交易所/區域 代碼)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T4:興櫃<br>
	 * 15.JABXConst.ABXFUN_DOWNLOAD_STOCKRELATIONWARRANTSLIST(下載一般股票關連權證股票(港股)資料檔)<br>
	 * &nbsp;&nbsp;(C).JSConst.DN_FILENO: String(*,交易所/區域 代碼)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T4:興櫃<br>
	 * 16.JABXConst.ABXFUN_DOWNLOAD_STOCKTABLE(下載股票名稱(拼音)資料檔)<br>
	 * &nbsp;&nbsp;(C).JSConst.DN_FILENO: String(*,交易所代碼)<br>
	 * &nsbp;&nsbp;(D).JSConst.DN_ENCODING: String(*,股名檔編碼)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T: 繁體<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;C: 簡體<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;E: 英文<br>
	 * &nsbp;&nsbp;(E).JSConst.DN_PINYING_TYPE: String(*,股名檔類別)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;1-短股名檔<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;2-股名拼音股名檔<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;3-多股名拼音股名檔。股名拼音最多取 2 組。<br>
	 * 17.JABXConst.ABXFUN_DOWNLOAD_TECHNICALINDEXCLASS(下載技術指標分類資料檔)<br>
	 * &nbsp;&nbsp;(C).JSConst.DN_FILENO: String(*,技術指標總分類代碼，由後台定義。)<br>
	 * 18.JABXConst.ABXFUN_DOWNLOAD_TECHNICALINDEXLIST(下載技術指標名稱資料檔)<br>
	 * &nbsp;&nbsp;(C).JSConst.DN_FILENO: String(*,技術指標總分類代碼，由後台定義。)<br>
	 * 19.JABXConst.ABXFUN_DOWNLOAD_TRADECLASS(下載產業分類資料)<br>
	 * &nbsp;&nbsp;(C).JSConst.DN_FILENO: String(*,交易所/區域 代碼)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T4:興櫃<br>
	 * 20.JABXConst.ABXFUN_DOWNLOAD_TRADECLASS2(下載產業分類資料2)<br>
	 * &nbsp;&nbsp;(C).JSConst.DN_FILENO: String(*,交易所/區域 代碼)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;TW:台灣交易所<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T1:上市<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T2:上櫃<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T3:期貨/選擇權<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;T4:興櫃<br>
	 * 21.JABXConst.ABXFUN_DOWNLOAD_WARNCLASS(下載警示分類資料)<br>
	 * &nbsp;&nbsp;(C).JSConst.DN_FILENO: String(*,群組代碼)<br>
	 * @return String(非空白，代表傳入之參數不足或參數格式錯誤)
	 */
	public String downloadFile(JSONObject jsonObj);

	/**
	 * 股票報價數據下載(P50)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param listID - List&lt;String&gt;(交易所代碼 或 分類代碼 或 證券全代碼列表)
	 * @param iQuoteField - int(報價欄位代碼。可複選相加(XOR))
	 * @param iStart - int(要查詢的起始日期，格式為：YYYYMMDD，0表第一筆)
	 * @param iEnd - int(要查詢的結束日期，格式為：YYYYMMDD，0表最新一筆)
	 */
	public void downloadQuoteData(int requestID, List<String> listID, int iQuoteField, int iStart, int iEnd);

	/**
	 * 訂閱資料
	 * @param jsonObj - JSONObject<br>
	 * 對應參數如下所示，括號中有'*'，代表必填:<br>
	 * (A).JSConst.CU_REQUESTID: int(*,API回覆碼)<br>
	 * (B).JSConst.SE_FUNCID: int(*,訂閱功能代碼)<br>
	 * 訂閱功能代碼之常數及不同訂閱功能填入之參數，如下所示:<br>
	 * 1.JABXConst.ABXFUN_SUBSCRIBE_QUOTE(訂閱即時報價)<br>
	 * &nbsp;&nbsp;(C).JSConst.SE_SESSIONID: int(*,不同視窗訂閱時所使用之SessionID，可經由JABXToolkit類別之getSessionID()取得，<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;取得後要自行記錄起來，再次訂閱時再帶入。)<br>
	 * &nbsp;&nbsp;(D).JSConst.SE_QUOTE_INFO: JSONArray(*,訂閱資訊)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;JSONArray中每單一物件為JSONObject物件，格式如下所示：<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;[JSConst.CU_STKID:股票代碼;JSConst.CU_QUOTE_FIELD:訂閱欄位之int值]<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;訂閱欄位之int值，說明如下:<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(a).JABXConst.ABX_WATCH_STKBASEINFO:0x00000001(股票基本資料訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(b).JABXConst.ABX_WATCH_STKREFINFO:0x00000002(股票相關資料訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(c).JABXConst.ABX_WATCH_ORDER_1:0x00000004(第1檔委買賣訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(d).JABXConst.ABX_WATCH_ORDER_2_5:0x00000008(第2至5檔委買賣訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(e).JABXConst.ABX_WATCH_ORDER_6_10:0x00000010(第6至10檔委買賣訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(f).JABXConst.ABX_WATCH_TRADE:0x00000020(成交資訊訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(g).JABXConst.ABX_WATCH_TOTREFINFO:0x00000040(總委買賣量筆及成交筆訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(h).JABXConst.ABX_WATCH_MINUTE:0x00000080(其他一分鐘訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(i).JABXConst.ABX_WATCH_OTHERS:0x00000100(其他報價訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(j).JABXConst.ABX_WATCH_STATISTIC:0x00000200(交易所統計資料訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(k).JABXConst.ABX_WATCH_EXCHANGESTATUS:0x00000400(交易所交易狀態訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(l).JABXConst.ABX_WATCH_BROKERQUEUE:0x00000800(BrokerQueue-香港訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(m).JABXConst.ABX_WATCH_DETAILTRADE:0x00001000(逐筆成交上海,深圳訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(n).JABXConst.ABX_WATCH_DETAILORDER:0x00002000(逐筆委託-深圳訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(o).JABXConst.ABX_WATCH_OLDLOTTRADE:0x00004000(零股買賣成交-台灣訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(p).JABXConst.ABX_WATCH_VIRTUALTRADE:0x00008000(盤前虛擬撮合-上海,深圳訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(q).JABXConst.ABX_WATCH_1SECSNAPSHOT:0x00010000(一秒快照訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(r).JABXConst.ABX_WATCH_ORDERLIST:0x00020000(委買賣每檔明細-上海,深圳Level-2訊息)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(s).JABXConst.ABX_WATCH_ALL_CLASS:0xFFFFFFFF(取得所有即時報價之class)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;以下舉例說明：如要T12330這支股票及訂閱所有欄位，JSONObject如下所示：<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[JSConst.CU_STKID:"T12330";JSConst.CU_QUOTE_FIELD:JABXConst.ABX_WATCH_ALL_CLASS]<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;如要訂閱T12330的第1檔委買賣訊息及成交資訊訊息，JSONObject如下所示：<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[JSConst.CU_STKID:"T12330";JSConst.CU_QUOTE_FIELD:JABXConst.ABX_WATCH_ORDER_1|JABXConst.ABX_WATCH_TRADE]<br>
	 * 2.JABXConst.ABXFUN_SUBSCRIBE_SMARTSHORT(訂閱即時短線精靈訊息)<br>
	 * &nbsp;&nbsp;(C).JSConst.SE_SMARTSHORT_INFO: JSONArray(*,訂閱即時短線精靈資訊)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;JSONArray中每單一物件為String(短線精靈群組代碼)物件。<br>
	 * 3.JABXConst.ABXFUN_SUBSCRIBE_SMARTMASTER(訂閱即時主力大單訊息)<br>
	 * &nbsp;&nbsp;(C).JSConst.SE_SMARTMASTER_INFO: JSONArray(*,訂閱即時主力大單資訊)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;JSONArray中每單一物件為JSONObject物件，物件內容如下所示：<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JSConst.CU_EXCHANGEID: String(*,交易所代碼)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JSConst.MS_MINIMUM_AMOUNT: int(主力大單最小成交金額)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JSConst.MS_MINIMUM_VOLUME: int(主力大單最小成交量)<br>
	 * 4.JABXConst.ABXFUN_SUBSCRIBE_SMARTRANK(訂閱即時排名訊息)<br>
	 * &nbsp;&nbsp;(C).JSConst.SE_SMARTRANK_INFO: JSONArray(*,訂閱即時排名資訊)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;JSONArray中每單一物件為String(即時排名群組代碼)物件。<br>
	 * 5.JABXConst.ABXFUN_SUBSCRIBE_MARKETREPORT(訂閱即時行情報導訊息)<br>
	 * &nbsp;&nbsp;(C).JSConst.SE_MARKETREPORT_INFO: JSONArray(*,訂閱即時行情報導資訊)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;JSONArray中每單一物件為String(行情報導分類代碼)物件。<br>
	 * 6.JABXConst.ABXFUN_SUBSCRIBE_ADVERTISEMENT(訂閱即時廣告訊息)<br>
	 * &nbsp;&nbsp;(C).JSConst.SE_FLAG_INFO: boolean(*,是否訂閱;true.訂閱,false.取消訂閱)<br>
	 * 7.JABXConst.ABXFUN_SUBSCRIBE_EXCHANGEBULLETIN(訂閱即時交易所公告訊息)<br>
	 * &nbsp;&nbsp;(C).JSConst.SE_EXCHANGEBULLETIN_INFO: JSONArray(*,訂閱即時交易所公告資訊)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;JSONArray中每單一物件為String(交易所代碼)物件。<br>
	 * 8.JABXConst.ABXFUN_SUBSCRIBE_NEWS(訂閱即時新聞訊息)<br>
	 * &nbsp;&nbsp;(C).JSConst.SE_NEWS_INFO: JSONArray(*,訂閱即時新聞資訊)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;JSONArray中每單一物件為String(新聞來源代碼)物件。<br>
	 * 9.JABXConst.ABXFUN_SUBSCRIBE_PRODUCTBULLETIN(訂閱即時產品公告訊息)<br>
	 * &nbsp;&nbsp;(C).JSConst.SE_FLAG_INFO: boolean(*,是否訂閱;true.訂閱,false.取消訂閱)<br>
	 * 10.JABXConst.ABXFUN_SUBSCRIBE_SYSTEMBULLETIN(訂閱即時ABus公告訊息)<br>
	 * &nbsp;&nbsp;(C).JSConst.SE_FLAG_INFO: boolean(*,是否訂閱;true.訂閱,false.取消訂閱)<br>
	 * 11.JABXConst.ABXFUN_SUBSCRIBE_SMARTWARN(訂閱警示訊息)<br>
	 * &nbsp;&nbsp;(C).JSConst.SE_FLAG_INFO: boolean(*,是否訂閱;true.訂閱,false.取消訂閱)<br>
	 * 12.JABXConst.ABXFUN_SUBSCRIBE_REALTIMETECHNICAL(訂閱即時指標)<br>
	 * &nbsp;&nbsp;(C).JSConst.SE_REALTIMETECHNICAL_INFO: JSONArray(*,訂閱即時指標資訊)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;JSONArray中每單一物件為String(指標回覆序號)物件。<br>
	 * @return String(非空白，代表傳入之參數不足或參數格式錯誤)
	 */
	public String subscribe(JSONObject jsonObj);

	/**
	 * 查詢用戶常用技術指標(P57)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 */
	public void getUserTechnicalIndexCommonSetup(int requestID);

	/**
	 * 查詢用戶技術指標參數預設值(P58)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param iTechnicalIndexID - int(技術指標代碼)
	 * @param period - int(周期代碼)
	 * @param bDefaultFlag - byte(預設值旗標。0-用戶設定值，1-系統預設值)
	 */
	public void getUserTechnicalIndexSetup(int requestID, int iTechnicalIndexID, int period, byte bDefaultFlag);

	/**
	 * 查詢(一個)分類關連(多個)股票(P07)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param classID - int(分類群組代碼)
	 */
	public void queryClassRelationStock(int requestID, int classID);

	/**
	 * 查詢交易所公告內容(N02)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param sExchangeID - String(交易所代碼)
	 * @param iSerialNo - int(公告訊息序號)
	 */
	public void queryExchangeBulletinContent(int requestID, String sExchangeID, int iSerialNo);

	/**
	 * 查詢專家選股訊息(I10)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param iExpertSelectID - int(專家選股代碼)
	 * @param iStart - int(要回補的起始日期，格式為：YYYYMMDD，0表第一筆)
	 * @param iEnd - int(要回補的結束日期，格式為：YYYYMMDD，0表最新一筆)
	 */
	public void queryExpertSelect(int requestID, int iExpertSelectID, int iStart, int iEnd);

	/**
	 * 查詢股票歷史報價數據(K線)(P51)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 iStart&gt;0 且 iEnd=0 且 iCount&gt;0 則 從iStart往後取iCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 iStart=0 且 iEnd&gt;0 且 iCount&gt;0 則 從iEnd往前取iCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 iStart=0 且 iEnd=0 且 iCount&gt;0 則 從最新一筆往前取iCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 iStart=0 且 iEnd=0 且 iCount=0 則 從第一筆取至最新一筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 iStart&gt;0 且 iEnd&gt;0 則 iCount沒有作用。<br>
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param sStockID - String(證券全代碼(交易所代碼+證券代碼))
	 * @param iPeriod - int(周期代碼。復權需加上復權基準值，限日線至60分線使用)
	 * @param iRecoverDate - int(定點復權起始日期，格式為：YYYYMMDD，0表無作用)
	 * @param iStart - int(要查詢的起始日期，格式為：YYYYMMDD，0表無作用)
	 * @param iEnd - int(要查詢的結束日期，格式為：YYYYMMDD，0表無作用)
	 * @param iCount - int(要查詢的筆數，與上述 iStart 或 iEnd 搭配使用)
	 */
	public void queryHistoryQuoteData(int requestID, String sStockID, int iPeriod, int iRecoverDate, int iStart, int iEnd, int iCount);

	/**
	 * 即時報價數據下載(P49)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param targetList - List&lt;String&gt;(排行目標的列表)
	 * @param quoteID - int(報價欄位)
	 */
	public void queryLastQuoteData(int requestID, List<String> targetList, int quoteID);

	/**
	 * 查詢行情報導內容(I03)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param iMarketReportDate - int(行情報導日期，格式: yyyyMMdd)
	 * @param iMarketReportNo - int(行情報導序號)
	 * @param bContentType - byte(回補內容類別。0-內文(預設)，1-摘要)
	 */
	public void queryMarketReportContent(int requestID, int iMarketReportDate, int iMarketReportNo, byte bContentType);

	/**
	 * 查詢新聞內容(N12)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param iSourceID - int(新聞來源代碼)
	 * @param iSerialNo - int(新聞訊息序號)
	 */
	public void queryNewsContent(int requestID, int iSourceID, int iSerialNo);

	/**
	 * 查詢產品公告內容(I01)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param iSerialNo - int(產品公告序號)
	 */
	public void queryProductBulletinContent(int requestID, int iSerialNo);

	/**
	 * 即時排行查詢(P40)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param groupID - int(排行項目的GroupID)
	 * @param targetList - List&lt;String&gt;(排行目標的列表)
	 * @param quoteID - int(報價欄位)
	 * @param recordCount - int(查詢筆數)
	 */
	public void querySortQuoteData(int requestID, int groupID, List<String> targetList, int quoteID, int recordCount);

	/**
	 * 查詢股票除權息資料(P03)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param stockID - String(證券全代碼(交易所代碼+證券代碼))
	 * @param startIdx - int(起始索引值，從 0 開始，0 表第一筆。)
	 * @param endIdx - int(截止索引，從 1 開始，0 表最後一筆。)
	 */
	public void queryStockDivide(int requestID, String stockID, int startIdx, int endIdx);

	/**
	 * 查詢(一個)股票關連(多個)分類(P06)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param stkID - String(證券全代碼)
	 */
	public void queryStockRelationClass(int requestID, String stkID);

	/**
	 * 查詢技術指標數據(P52)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 iStart&gt;0 且 iEnd=0 且 iCount&gt;0 則 從iStart往後取iCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 iStart=0 且 iEnd&gt;0 且 iCount&gt;0 則 從iEnd往前取iCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 iStart=0 且 iEnd=0 且 iCount&gt;0 則 從最新一筆往前取iCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 iStart=0 且 iEnd=0 且 iCount=0 則 從第一筆取至最新一筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 iStart&gt;0 且 iEnd&gt;0 則 iCount沒有作用。
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param sStockID - String(證券全代碼)
	 * @param iPeriod - int(周期代碼。復權需加上復權基準值，限日線至60分線使用。)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;1.日線<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;2.周線<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;3.月線<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;4.季線<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;5.半年線<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;6.年線<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;10.1分線<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;11.5分線<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;12.10分線<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;13.15分線<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;14.20分線<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;15.30分線<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;16.60分線<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;100.往前復權基準值<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;200.往後復權基準值
	 * @param iTechnicalIndexID - int(技術指標代碼)
	 * @param sParameter - String(技術指標參數值。各參數間以分號(;)分隔，"參數值1;參數值2;…"。空字串("")代表前一次使用者定義之預設值。)
	 * @param iDataDate - int(技術指標計算起始日期，格式為：YYYYMMDD，0表無作用。) 
	 * @param iRecoverDate - int(定點復權起始日期，格式為：YYYYMMDD，0表無作用。)
	 * @param iStart - int(要查詢的起始日期，格式為：YYYYMMDD，0表無作用。)
	 * @param iEnd - int(要查詢的結束日期，格式為：YYYYMMDD，0表無作用。)
	 * @param iCount - int(要查詢的筆數，與上述 iStart 或 iEnd 搭配使用。)
	 */
	public void queryTechnicalIndexData(int requestID, String sStockID, int iPeriod, int iTechnicalIndexID, String sParameter, int iDataDate, int iRecoverDate, int iStart, int iEnd, int iCount);	
	
	/**
	 * 查詢技術指標公式定義(P56)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param iTechnicalIndexID - int(技術指標代碼)
	 */
	public void queryTechnicalIndexDefine(int requestID, int iTechnicalIndexID);

	/**
	 * 檔差表查詢(P01)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param exchange - String(交易所代碼)
	 */
	public void queryTickDiff(int requestID, String exchange);

	/**
	 * 權證相關資料查詢(P02)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param stockID - String(證券全代碼(交易所代碼+證券代碼))
	 */
	public void queryWarrantRelative(int requestID, String stockID);

	/**
	 * 回補廣告訊息(I06)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 */
	public void rebuildAdvertisement(int requestID);

	/**
	 * 回補逐筆委託-深圳資料(R14)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中碼)
	 * @param stockID - String(證券全代碼(交易所代碼+證券代碼))
	 * @param startIdx - int(起始索引值，從 0 開始，0 表第一筆。)
	 * @param endIdx - int(截止索引，從 1 開始，0 表最後一筆。)
	 */
	public void rebuildDetailOrder(int requestID, String stockID, int startIdx, int endIdx);

	/**
	 * 回補逐筆成交上海,深圳資料(R13)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param stockID - String(證券全代碼(交易所代碼+證券代碼))
	 * @param startIdx - int(起始索引值，從 0 開始，0 表第一筆。)
	 * @param endIdx - int(截止索引，從 1 開始，0 表最後一筆。)
	 */
	public void rebuildDetailTrade(int requestID, String stockID, int startIdx, int endIdx);

	/**
	 * 回補當日交易所公告標題(N00)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param listOfExchangeID - List&lt;String&gt;(交易所代碼列表)
	 * @param iStart - int(要回補的起始時間，格式為：HHmmss，90000表9點0分，0表無作用)
	 * @param iEnd - int(要回補的結束時間，格式為：HHmmss，133000表13點30分，0表無作用)
	 * @param iCount - int(要回補的筆數，與上述 iStart 或 iEnd 搭配使用)
	 */
	public void rebuildExchangeBulletin(int requestID, List<String> listOfExchangeID, int iStart, int iEnd, int iCount);

	/**
	 * 回補個股歷史交易所公告標題(N00)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中碼)
	 * @param sStockID - String(證券全代碼)
	 * @param iStart - int(要回補的起始時間，格式為：HHmmss，90000表9點0分，0表無作用)
	 * @param iEnd - int(要回補的結束時間，格式為：HHmmss，133000表13點30分，0表無作用)
	 * @param iCount - int(要回補的筆數，與上述 iStart 或 iEnd 搭配使用)
	 */
	public void rebuildHistoryExchangeBulletin(int requestID, String sStockID, int iStart, int iEnd, int iCount);

	/**
	 * 回補個股歷史新聞標題(N11)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param sStockID - String(證券全代碼)
	 * @param iStart - int(要回補的起始時間，格式為：HHmmss，90000表9點0分，0表無作用)
	 * @param iEnd - int(要回補的結束時間，格式為：HHmmss，133000表13點30分，0表無作用)
	 * @param iCount - int(要回補的筆數，與上述 iStart 或 iEnd 搭配使用)
	 */
	public void rebuildHistoryNews(int requestID, String sStockID, int iStart, int iEnd, int iCount);

	/**
	 * 回補行情報導標題(I02)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中碼)
	 * @param listOfClassID - List&lt;String&gt;(行情報導分類代碼列表)
	 * @param iStart - int(要回補的起始日期，格式為：yyyyMMdd，0表第一筆)
	 * @param iEnd - int(要回補的結束日期，格式為：yyyyMMdd，0表最新一筆)
	 */
	public void rebuildMarketReport(int requestID, List<String> listOfClassID, int iStart, int iEnd);

	/**
	 * 回補成交分鐘明細(多筆)(P51)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param stockID - String(證券全代碼(交易所代碼+證券代碼))
	 */
	public void rebuildMinuteTrade(int requestID, String stockID);

	/**
	 * 回補當日新聞標題(N10)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param listOfSourceID - List&lt;String&gt;(新聞來源代碼列表)
	 * @param iStart - int(要回補的起始時間，格式為：HHmmss，90000表9點0分，0表無作用)
	 * @param iEnd - int(要回補的結束時間，格式為：HHmmss，133000表13點30分，0表無作用)
	 * @param iCount - int(要回補的筆數，與上述 iStart 或 iEnd 搭配使用)
	 */
	public void rebuildNews(int requestID, List<String> listOfSourceID, int iStart, int iEnd, int iCount);

	/**
	 * 回補零股買賣成交-台灣資料(R15)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx&gt;0 且 endIdx=0 且 nCount&gt;0 則 從startIdx往後取nCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx=0 且 endIdx&gt;0 且 nCount&gt;0 則 從endIdx往前取nCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx=0 且 endIdx=0 且 nCount&gt;0 則 從最新一筆往前取nCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx=0 且 endIdx=0 且 nCount=0 則 從第一筆取至最新一筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx&gt;0 且 endIdx&gt;0 則 nCount沒有作用。
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param stockID - String(證券全代碼(交易所代碼+證券代碼))
	 * @param startIdx - int(要回補的起始時間，格式為：HHmm，900表9點0分，0表無作用。)
	 * @param endIdx - int(要回補的結束時間，格式為：HHmm，1330表13點30分，0表無作用。)
	 * @param nCount - int(要回補的筆數，與上述 startIdx 或 endIdx 搭配使用。)
	 */
	public void rebuildOldLotTrade(int requestID, String stockID, int startIdx, int endIdx, int nCount);

	/**
	 * 回補某一分鐘之成交明細(R96)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param stockID - String(證券全代碼(交易所代碼+證券代碼))
	 * @param hhmm - int(時分)
	 */
	public void rebuildOneMinuteTrade(int requestID, String stockID, int hhmm);

	/**
	 * 個股成交分價查詢(P10)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param stockID - String(證券全代碼(交易所代碼+證券代碼))
	 */
	public void rebuildPriceTrade(int requestID, String stockID);

	/**
	 * 回補產品公告標題(I00)
	 * 公告類型代碼如下所示：<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;1.ABX_NOTICE_NORMAL: 一般公告<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;2.ABX_NOTICE_URGENT: 緊急公告<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;3.ABX_NOTICE_PRODUCT: 版本更新
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param iBulletinType - int(公告類型代碼)
	 */
	public void rebuildProductBulletin(int requestID, int iBulletinType);

	/**
	 * 回補最新報價(R01)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param list - List&lt;JABXQuoteCondition&gt;
	 */
	public void rebuildQuote(int requestID, List<JABXQuoteCondition> list);

	/**
	 * 回補主力大單明細(多筆)(R52)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param marketID - String(主力大單市場別)
	 * @param masterType - String(大單類別)
	 * @param limitType - byte(大單額度類別，0: 成交量(手,張)，1: 成交金額(萬元))
	 * @param limit - int(大單額度)
	 * @param startTime - int(起始時間(HHmm或0)
	 * @param endTime - int(截止時間(HHmm或0)
	 * @param rebuildCount - int(回補筆數)
	 */
	public void rebuildSmartMaster(int requestID, String marketID, String masterType, byte limitType, int limit, int startTime, int endTime, int rebuildCount);

	/**
	 * 回補即時排名明細(多筆)(R51)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param listOfGroupID - List&lt;String&gt;(即時排名群組全代碼列表)
	 * @param iCount - int(排名筆數)
	 */
	public void rebuildSmartRank(int requestID, List<String> listOfGroupID, int iCount);

	/**
	 * 回補智慧選股(短線精靈)明細(R50)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中碼)
	 * @param listOfGroupID - List&lt;String&gt;(智慧選股群組全代碼列表)
	 * @param iStart - int(要回補的起始時間，格式為：HHmm，900表9點0分，0表無作用)
	 * @param iEnd - int(要回補的結束時間，格式為：HHmm，1330表13點30分，0表無作用)
	 * @param iCount - int(要回補的筆數，與上述 iStart 或 iEnd 搭配使用)
	 */
	public void rebuildSmartShort(int requestID, List<String> listOfGroupID, int iStart, int iEnd, int iCount);

	/**
	 * 回補多筆交易所統計資料訊息(R10)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx&gt;0 且 endIdx=0 且 nCount&gt;0 則 從startIdx往後取nCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx=0 且 endIdx&gt;0 且 nCount&gt;0 則 從endIdx往前取nCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx=0 且 endIdx=0 且 nCount&gt;0 則 從最新一筆往前取nCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx=0 且 endIdx=0 且 nCount=0 則 從第一筆取至最新一筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx&gt;0 且 endIdx&gt;0 則 nCount沒有作用。
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param stockID - String(證券全代碼(交易所代碼+證券代碼))
	 * @param startIdx - int(要回補的起始時間，格式為：HHmm，900表9點0分，0表無作用。)
	 * @param endIdx - int(要回補的結束時間，格式為：HHmm，1330表13點30分，0表無作用。)
	 * @param nCount - int(要回補的筆數，與上述 startIdx 或 endIdx 搭配使用。)
	 */
	public void rebuildStatistic(int requestID, String stockID, int startIdx, int endIdx, int nCount);

	/**
	 * 回補多筆總委買賣量筆及成交筆訊息(R07)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx&gt;0 且 endIdx=0 且 nCount&gt;0 則 從startIdx往後取nCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx=0 且 endIdx&gt;0 且 nCount&gt;0 則 從endIdx往前取nCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx=0 且 endIdx=0 且 nCount&gt;0 則 從最新一筆往前取nCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx=0 且 endIdx=0 且 nCount=0 則 從第一筆取至最新一筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx&gt;0 且 endIdx&gt;0 則 nCount沒有作用。
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param stockID - String(證券全代碼(交易所代碼+證券代碼))
	 * @param startIdx - int(要回補的起始時間，格式為：HHmm，900表9點0分，0表無作用。)
	 * @param endIdx - int(要回補的結束時間，格式為：HHmm，1330表13點30分，0表無作用。)
	 * @param nCount - int(要回補的筆數，與上述 startIdx 或 endIdx 搭配使用。)
	 */
	public void rebuildTotRefInfo(int requestID, String stockID, int startIdx, int endIdx, int nCount);

	/**
	 * 回補多筆成交資訊(R06)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startSeqNo&gt;0 且 endSeqNo=0 且 nCount&gt;0 則 從startSeqNo往後取nCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startSeqNo=0 且 endSeqNo&gt;0 且 nCount&gt;0 則 從endIdx往前取nCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startSeqNo=0 且 endSeqNo=0 且 nCount&gt;0 則 從最新一筆往前取nCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startSeqNo=0 且 endSeqNo=0 且 nCount=0 則 從第一筆取至最新一筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startSeqNo&gt;0 且 endSeqNo&gt;0 則 nCount沒有作用。
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param stockID - String(證券全代碼(交易所代碼+證券代碼))
	 * @param startSeqNo - int(起始序號，從 0 開始，0 表第一筆。)
	 * @param endSeqNo - int(截止序號，從 1 開始，0 表最後一筆。)
	 * @param nCount - int(要回補的筆數，與上述 startIdx 或 endIdx 搭配使用。)
	 */
	public void rebuildTrade(int requestID, String stockID, int startSeqNo, int endSeqNo, int nCount);
	
	/**
	 * 回補盤前虛擬撮合-上海,深圳資料(R16)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx&gt;0 且 endIdx=0 且 nCount&gt;0 則 從startIdx往後取nCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx=0 且 endIdx&gt;0 且 nCount&gt;0 則 從endIdx往前取nCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx=0 且 endIdx=0 且 nCount&gt;0 則 從最新一筆往前取nCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx=0 且 endIdx=0 且 nCount=0 則 從第一筆取至最新一筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 startIdx&gt;0 且 endIdx&gt;0 則 nCount沒有作用。
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param stockID - String(證券全代碼(交易所代碼+證券代碼))
	 * @param startIdx - int(要回補的起始時間，格式為：HHmm，900表9點0分，0表無作用。)
	 * @param endIdx - int(要回補的結束時間，格式為：HHmm，1330表13點30分，0表無作用。)
	 * @param nCount - int(要回補的筆數，與上述 startIdx 或 endIdx 搭配使用。)
	 */
	public void rebuildVirtualTrade(int requestID, String stockID, int startIdx, int endIdx, int nCount);

	/**
	 * 技術指標數據再查詢(P53)<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 iStart&gt;0 且 iEnd=0 且 iCount&gt;0 則 從iStart往後取iCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 iStart=0 且 iEnd&gt;0 且 iCount&gt;0 則 從iEnd往前取iCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 iStart=0 且 iEnd=0 且 iCount&gt;0 則 從最新一筆往前取iCount筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 iStart=0 且 iEnd=0 且 iCount=0 則 從第一筆取至最新一筆。<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;當 iStart&gt;0 且 iEnd&gt;0 則 iCount沒有作用。
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param sStockID - String(證券全代碼)
	 * @param lineID - int(指標記錄序號)<br>
	 * @param iStart - int(要查詢的起始日期，格式為：YYYYMMDD，0表無作用。)
	 * @param iEnd - int(要查詢的結束日期，格式為：YYYYMMDD，0表無作用。)
	 * @param iCount - int(要查詢的筆數，與上述 iStart 或 iEnd 搭配使用。)
	 */
	public void reQueryTechnicalIndexData(int requestID, String sStockID, int lineID, int iStart, int iEnd, int iCount);

	/**
	 * 訂閱所有股票訊息(Used for server side)
	 * @param requestID - int
	 * @param list - List&lt;JABXAServer&gt;
	 */
	public void serverSideSubscribe(int requestID, List<JABXAServer> list);

	/**
	 * 更新用戶常用技術指標(P59)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param iTechnicalIndexID - int(技術指標代碼)
	 * @param usedFlag - byte(常用公式旗標。0-不常用，1-常用)
	 */
	public void setUserTechnicalIndexCommonSetup(int requestID, int iTechnicalIndexID, byte usedFlag);

	/**
	 * 更新用戶技術指標參數預設值(P59)
	 * @param requestID - int(API回覆碼，以JABXToolkit.getRequestID()方式取得，取得之後傳入參數requestID中)
	 * @param iTechnicalIndexID - int(技術指標代碼)
	 * @param period - int(周期代碼)
	 * @param sParameter - String(技術指標參數預設值。各參數間以分號(;)分隔，"參數1預設值;參數2預設值;...")
	 */
	public void setUserTechnicalIndexSetup(int requestID, int iTechnicalIndexID, int period, String sParameter);

}
