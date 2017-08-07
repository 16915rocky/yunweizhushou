package com.chinamobile.yunweizhushou.bean;

public class FaultProgressBean {
	private String accidentId;
	private String content;
	private String id;
	private String progress;
	private String time;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAccidentId() {
		return accidentId;
	}

	public String getContent() {
		return content;
	}

	public String getId() {
		return id;
	}

	public String getProgress() {
		return progress;
	}

	public void setAccidentId(String accidentId) {
		this.accidentId = accidentId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}
}
