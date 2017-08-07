package com.chinamobile.yunweizhushou.ui.userPerceptionIndex.bean;

import java.util.List;

public class KeiBean {

	private String class_name;
    private String class_id;
    private String cnt_2016;
    private String cnt_2017;
    private List<KeiBean> itemList;
    
    
	public String getCnt_2016() {
		return cnt_2016;
	}
	public void setCnt_2016(String cnt_2016) {
		this.cnt_2016 = cnt_2016;
	}
	public String getCnt_2017() {
		return cnt_2017;
	}
	public void setCnt_2017(String cnt_2017) {
		this.cnt_2017 = cnt_2017;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}

	public List<KeiBean> getItemList() {
		return itemList;
	}
	public void setItemList(List<KeiBean> itemList) {
		this.itemList = itemList;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

    
    
}
