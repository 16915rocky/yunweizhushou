package com.chinamobile.yunweizhushou.ui.complaint.bean;

import java.util.List;

public class ComplainTodayBean {
	private String month;
	private String today;
	private String year;
	private String todayIncrease;
	private String monthIncrease;
	private String yearIncrease;
	private String monthCode;
	private String todayCode;
	private String yearCode;
	private List<ComplainTodayItemBean> top10List;
	private List<ComplainTodayItemBean> top10IncreaseList;

	private String hots;

	public String getYearIncrease() {
		return yearIncrease;
	}

	public String getYearCode() {
		return yearCode;
	}

	public void setYearIncrease(String yearIncrease) {
		this.yearIncrease = yearIncrease;
	}

	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	public String getHots() {
		return hots;
	}

	public void setHots(String hots) {
		this.hots = hots;
	}

	public String getTodayIncrease() {
		return todayIncrease;
	}

	public String getMonth() {
		return month;
	}

	public String getToday() {
		return today;
	}

	public String getYear() {
		return year;
	}

	public String getMonthIncrease() {
		return monthIncrease;
	}

	public String getMonthCode() {
		return monthCode;
	}

	public String getTodayCode() {
		return todayCode;
	}

	public List<ComplainTodayItemBean> getTop10List() {
		return top10List;
	}

	public List<ComplainTodayItemBean> getTop10IncreaseList() {
		return top10IncreaseList;
	}

	public void setTodayIncrease(String todayIncrease) {
		this.todayIncrease = todayIncrease;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setMonthIncrease(String monthIncrease) {
		this.monthIncrease = monthIncrease;
	}

	public void setMonthCode(String monthCode) {
		this.monthCode = monthCode;
	}

	public void setTodayCode(String todayCode) {
		this.todayCode = todayCode;
	}

	public void setTop10List(List<ComplainTodayItemBean> top10List) {
		this.top10List = top10List;
	}

	public void setTop10IncreaseList(List<ComplainTodayItemBean> top10IncreaseList) {
		this.top10IncreaseList = top10IncreaseList;
	}

}
