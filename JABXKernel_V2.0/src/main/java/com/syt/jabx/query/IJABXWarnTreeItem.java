package com.syt.jabx.query;

/**
 * 警示樹子項目介面
 * @author jason
 *
 */
public interface IJABXWarnTreeItem {

	/**
	 * 取得群組代碼<br>
	 * 此群組代碼可當作函式IJABXWarnTreeOverview.getListByGroupID(String groupID)中<br>
	 * groupID的參數，取得此分類代碼下之子項目列表；以此方式不斷將 分類樹 展開。
	 * @return String
	 */
	public String getGroupID();

	/**
	 * 取得群組名稱
	 * @return String
	 */ 
	public String getGroupName();
	
	/**
	 * 取得分類種類
	 * @return String
	 */
	public String getClassType();

	/**
	 * 取得警示預設值
	 * @return String
	 */
	public String getDefaultValue();

	/**
	 * 取得警示預設時間(hhmm:預設時間，-1:盤中即時)
	 * @return String
	 */
	public String getDefaultTime();

	/**
	 * 取得特定股票類型的可使用旗標X(4)<br>
	 * 每個字元代表一個股票類型. 除了下列特定類型的股票外, 皆為可用. 序號由左至右. 0-不可, 1-可.<br>
	 * [0]-指數, [1]-保留, [2]-保留, [3]-期權<br>
	 * 例："１００１＂
	 * @return String
	 */
	public String getSpecialStockFlag();

	/**
	 * 取得個人化設定旗標X(4)<br>
	 * 每個字元代表一個設定旗標. 序號由左至右. 0-不可, 1-可.<br>
	 * [0]-個人化警示值, [1]-個人化警示時間, [2]-保留, [3]-保留<br>
	 * 例："１１００＂
	 * @return String
	 */
	public String getSpecialUserFlag();

	/**
	 * 取得警示註釋
	 * @return String
	 */
	public String getMark();
}
