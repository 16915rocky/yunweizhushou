package com.chinamobile.yunweizhushou.bean;

public class SystemTreeDialogBean {

	private String name;// 名称
	private String resdetail;// 资源描述
	private String macip;// 物理IP

	public SystemTreeDialogBean() {
		super();
	}

	public SystemTreeDialogBean(String name, String resdetail, String macip) {
		super();
		this.name = name;
		this.resdetail = resdetail;
		this.macip = macip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResdetail() {
		return resdetail;
	}

	public void setResdetail(String resdetail) {
		this.resdetail = resdetail;
	}

	public String getMacip() {
		return macip;
	}

	public void setMacip(String macip) {
		this.macip = macip;
	}

}
