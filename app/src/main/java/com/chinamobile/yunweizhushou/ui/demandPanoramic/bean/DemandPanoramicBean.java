package com.chinamobile.yunweizhushou.ui.demandPanoramic.bean;

public class DemandPanoramicBean {
	private DemandOperateBean.ItemsListChild childItem;
	private String item;

	public String getItem() {
		return item;
	}

	public DemandOperateBean.ItemsListChild getChildItem() {
		return childItem;
	}

	public void setChildItem(DemandOperateBean.ItemsListChild childItem) {
		this.childItem = childItem;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public DemandPanoramicBean(DemandOperateBean.ItemsListChild childItem, String item) {
		super();
		this.childItem = childItem;
		this.item = item;
	}

}
