package com.chinamobile.yunweizhushou.ui.bdConnectionPool.bean;

import java.util.List;

public class DBConnectionPoolBean {

	private int optimize;
	private int normal;
	private int warning;
	private List<ConnectionPoolChildItem> itemsList;

	public int getOptimize() {
		return optimize;
	}

	public void setOptimize(int optimize) {
		this.optimize = optimize;
	}

	public int getNormal() {
		return normal;
	}

	public void setNormal(int normal) {
		this.normal = normal;
	}

	public int getFocus() {
		return warning;
	}

	public void setWarning(int warning) {
		this.warning = warning;
	}

	public List<ConnectionPoolChildItem> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ConnectionPoolChildItem> itemsList) {
		this.itemsList = itemsList;
	}

	public static class ConnectionPoolChildItem {
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
	}
}
