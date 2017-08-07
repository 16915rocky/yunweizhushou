package com.chinamobile.yunweizhushou.bean;

public class GovernMainBeanTemp {
	private String progress;
	private String title;
	private String handleGroup;
	private String name;
	private String type;
	private String handler;

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHandleGroup() {
		return handleGroup;
	}

	public void setHandleGroup(String handleGroup) {
		this.handleGroup = handleGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public GovernMainBeanTemp() {
		super();
	}

	public GovernMainBeanTemp(String progress, String title, String handleGroup, String name, String type,
			String handler) {
		super();
		this.progress = progress;
		this.title = title;
		this.handleGroup = handleGroup;
		this.name = name;
		this.type = type;
		this.handler = handler;
	}
}
