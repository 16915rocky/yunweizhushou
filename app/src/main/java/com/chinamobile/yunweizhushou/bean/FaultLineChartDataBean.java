package com.chinamobile.yunweizhushou.bean;

public class FaultLineChartDataBean {
	private String time;
	// private String type;
	private int value;

	private int type;
	private int lineType;

	public int getLineType() {
		return lineType;
	}

	public void setLineType(int lineType) {
		this.lineType = lineType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public int getValue() {
		return value;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
