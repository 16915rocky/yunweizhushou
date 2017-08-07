package com.chinamobile.yunweizhushou.ui.busifluct.bean;

public class DeductionRemindBean {
	private String org_time;// 采集时间
	private String sys;// 积压名称
	private String num;// 积压量
	private String tab;// 积压表
	private String busi;// 异常点

	public String getBusi() {
		return busi;
	}

	public void setBusi(String busi) {
		this.busi = busi;
	}

	public String getOrg_time() {
		return org_time;
	}

	public void setOrg_time(String org_time) {
		this.org_time = org_time;
	}

	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

}
