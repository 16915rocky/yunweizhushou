package com.chinamobile.yunweizhushou.bean;

import java.io.Serializable;

public class MainPageFragmentBean implements Serializable {
	private String title;
	private String subTitle;
	private int imageId;
	private String className;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public MainPageFragmentBean(String title, String subTitle, int imageId, String c) {
		this.title = title;
		this.subTitle = subTitle;
		this.imageId = imageId;
		this.className = c;
	}

	public MainPageFragmentBean() {
	}
}
