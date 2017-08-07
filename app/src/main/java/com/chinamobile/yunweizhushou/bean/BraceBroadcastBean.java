package com.chinamobile.yunweizhushou.bean;

import java.io.Serializable;

public class BraceBroadcastBean implements Serializable {
	private String bomcName;
	private String bomcSource;
	private String bomcTime;
	private String bomcTitle;
	private String bomcType;
	private String id;
	private String commentNumber;
	private String bomcNumber;

	public String getBomcNumber() {
		return bomcNumber;
	}

	public void setBomcNumber(String bomcNumber) {
		this.bomcNumber = bomcNumber;
	}

	public String getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(String commentNumber) {
		this.commentNumber = commentNumber;
	}

	public String getBomcName() {
		return bomcName;
	}

	public String getBomcSource() {
		return bomcSource;
	}

	public String getBomcTime() {
		return bomcTime;
	}

	public String getBomcTitle() {
		return bomcTitle;
	}

	public String getBomcType() {
		return bomcType;
	}

	public String getId() {
		return id;
	}

	public void setBomcName(String bomcName) {
		this.bomcName = bomcName;
	}

	public void setBomcSource(String bomcSource) {
		this.bomcSource = bomcSource;
	}

	public void setBomcTime(String bomcTime) {
		this.bomcTime = bomcTime;
	}

	public void setBomcTitle(String bomcTitle) {
		this.bomcTitle = bomcTitle;
	}

	public void setBomcType(String bomcType) {
		this.bomcType = bomcType;
	}

	public void setId(String id) {
		this.id = id;
	}

}
