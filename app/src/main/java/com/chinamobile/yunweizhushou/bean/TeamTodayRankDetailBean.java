package com.chinamobile.yunweizhushou.bean;

public class TeamTodayRankDetailBean {
	private String name;
	private String score;
	private String sortNumber;

	public TeamTodayRankDetailBean() {
		super();
	}

	public TeamTodayRankDetailBean(String name, String score, String sortNumber) {
		super();
		this.name = name;
		this.score = score;
		this.sortNumber = sortNumber;
	}

	public String getName() {
		return name;
	}

	public String getScore() {
		return score;
	}

	public String getSortNumber() {
		return sortNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public void setSortNumber(String sortNumber) {
		this.sortNumber = sortNumber;
	}

}
