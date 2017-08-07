package com.chinamobile.yunweizhushou.ui.functionAnalysis.bean;

import java.util.List;

public class FunctionAnalysisListBean {
	private String in;
	private String off;
	private List<FunctionAnalysisListSubBean> itemList;

	public String getIn() {
		return in;
	}

	public String getOff() {
		return off;
	}

	public List<FunctionAnalysisListSubBean> getItemList() {
		return itemList;
	}

	public void setIn(String in) {
		this.in = in;
	}

	public void setOff(String off) {
		this.off = off;
	}

	public void setItemList(List<FunctionAnalysisListSubBean> itemList) {
		this.itemList = itemList;
	}

}
