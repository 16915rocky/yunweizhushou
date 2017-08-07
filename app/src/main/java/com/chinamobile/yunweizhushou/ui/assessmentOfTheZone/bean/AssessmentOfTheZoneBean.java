package com.chinamobile.yunweizhushou.ui.assessmentOfTheZone.bean;

public class AssessmentOfTheZoneBean {

	private String time;// 采集时间
	private String name1;// 一级指标
	private String name2;// 二级指标
	private String name3;// 三级指标
	private String value;// 考核成绩
	private String score;// 扣分风险 当数字大于0 则显示红色

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getName3() {
		return name3;
	}

	public void setName3(String name3) {
		this.name3 = name3;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}
