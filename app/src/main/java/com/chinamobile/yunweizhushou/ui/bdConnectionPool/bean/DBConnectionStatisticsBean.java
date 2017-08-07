package com.chinamobile.yunweizhushou.ui.bdConnectionPool.bean;

public class DBConnectionStatisticsBean {
	private String name;
	private String normal;
	private String optimize;
	private String warning;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNormal() {
		return normal;
	}

	public void setNormal(String normal) {
		this.normal = normal;
	}

	public String getOptimize() {
		return optimize;
	}

	public void setOptimize(String optimize) {
		this.optimize = optimize;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public DBConnectionStatisticsBean(String name, String normal, String optimize, String warning) {
		super();
		this.name = name;
		this.normal = normal;
		this.optimize = optimize;
		this.warning = warning;
	}

	public DBConnectionStatisticsBean() {
		super();
	}
}
