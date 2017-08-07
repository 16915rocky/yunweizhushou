package com.chinamobile.yunweizhushou.ui.levelStandar.bean;

import java.util.List;

public class DutyGroupBean {
	private String longNum;
	private String shortNum;
	private String system;
	private List<DutyChildBean> rotaList;

	public String getLongNum() {
		return longNum;
	}

	public String getShortNum() {
		return shortNum;
	}

	public String getSystem() {
		return system;
	}

	public List<DutyChildBean> getRotaList() {
		return rotaList;
	}

	public void setLongNum(String longNum) {
		this.longNum = longNum;
	}

	public void setShortNum(String shortNum) {
		this.shortNum = shortNum;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public void setRotaList(List<DutyChildBean> rotaList) {
		this.rotaList = rotaList;
	}

}
