package com.chinamobile.yunweizhushou.ui.assessmentOfTheZone.bean;

import java.util.List;

public class AssessmentBean {
	private List<AssessmentSubbean> itemsList;
	private String item;

	public List<AssessmentSubbean> getItemsList() {
		return itemsList;
	}

	public String getItem() {
		return item;
	}

	public void setItemsList(List<AssessmentSubbean> itemsList) {
		this.itemsList = itemsList;
	}

	public void setItem(String item) {
		this.item = item;
	}

}
