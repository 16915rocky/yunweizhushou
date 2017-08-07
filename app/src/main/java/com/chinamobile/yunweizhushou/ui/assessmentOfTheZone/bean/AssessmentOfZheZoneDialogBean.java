package com.chinamobile.yunweizhushou.ui.assessmentOfTheZone.bean;

public class AssessmentOfZheZoneDialogBean {

	private String name3;
	private String name4;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName3() {
		return name3;
	}

	public void setName3(String name3) {
		this.name3 = name3;
	}

	public String getName4() {
		return name4;
	}

	public void setName4(String name4) {
		this.name4 = name4;
	}

	public AssessmentOfZheZoneDialogBean() {
		super();
	}

	public AssessmentOfZheZoneDialogBean(String name3, String name4, String type) {
		super();
		this.name3 = name3;
		this.name4 = name4;
		this.type = type;
	}

}
