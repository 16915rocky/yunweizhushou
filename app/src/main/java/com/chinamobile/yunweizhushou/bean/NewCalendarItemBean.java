package com.chinamobile.yunweizhushou.bean;

public class NewCalendarItemBean {
	private String value;
	private String busiName;

	public NewCalendarItemBean() {
	}

	public NewCalendarItemBean(String busiName) {
		this.busiName = busiName;
	}

	public String getValue() {
		return value;
	}

	public String getBusiName() {
		return busiName;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}

}
