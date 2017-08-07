package com.chinamobile.yunweizhushou.ui.netChange.bean;

public class NetChangeErrorBean {
	private String bug_level;
	private String online_plan;// 变更编号
	private String requirename;// 需求名称
	private String question;// 问题描述
	private String resons;// 原因分析
	private String methods;// 改进措施
	private String type;// 原因分类
	private String deal;// 责任方
	private String resove;// 1已解决 2未解决

	public String getBug_level() {
		return bug_level;
	}

	public void setBug_level(String bug_level) {
		this.bug_level = bug_level;
	}

	public String getOnline_plan() {
		return online_plan;
	}

	public String getRequirename() {
		return requirename;
	}

	public String getQuestion() {
		return question;
	}

	public String getResons() {
		return resons;
	}

	public String getMethods() {
		return methods;
	}

	public String getType() {
		return type;
	}

	public String getDeal() {
		return deal;
	}

	public String getResove() {
		return resove;
	}

	public void setOnline_plan(String online_plan) {
		this.online_plan = online_plan;
	}

	public void setRequirename(String requirename) {
		this.requirename = requirename;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setResons(String resons) {
		this.resons = resons;
	}

	public void setMethods(String methods) {
		this.methods = methods;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDeal(String deal) {
		this.deal = deal;
	}

	public void setResove(String resove) {
		this.resove = resove;
	}

}
