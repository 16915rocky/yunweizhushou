package com.chinamobile.yunweizhushou.ui.logZone.bean;

public class LogZoneBean {

	private String index_id;
	private String time;// 采集时间
	private String name;// 日志对象
	private String max_range;// 阈值
	private String num;// 当前值
	private String state;// 1 代表红色 0代表绿色

	public String getIndex_id() {
		return index_id;
	}

	public void setIndex_id(String index_id) {
		this.index_id = index_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMax_range() {
		return max_range;
	}

	public void setMax_range(String max_range) {
		this.max_range = max_range;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public LogZoneBean(String time, String name, String max_range, String num, String state) {
		super();
		this.time = time;
		this.name = name;
		this.max_range = max_range;
		this.num = num;
		this.state = state;
	}

	public LogZoneBean() {
		super();
	}

}
