package com.chinamobile.yunweizhushou.ui.functionAnalysis.bean;

import java.util.List;

public class FunctionAnalysisBean {
	private String DESC;
	private List<FunctionAnalysisSubBean> itemList;

	public String getDESC() {
		return DESC;
	}

	public List<FunctionAnalysisSubBean> getItemList() {
		return itemList;
	}

	public void setDESC(String dESC) {
		DESC = dESC;
	}

	public void setItemList(List<FunctionAnalysisSubBean> itemList) {
		this.itemList = itemList;
	}

}
