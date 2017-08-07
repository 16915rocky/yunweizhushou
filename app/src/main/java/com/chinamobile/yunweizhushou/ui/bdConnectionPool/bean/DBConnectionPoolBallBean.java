package com.chinamobile.yunweizhushou.ui.bdConnectionPool.bean;

import java.util.List;

public class DBConnectionPoolBallBean {
	private List<ConnectionPoolItem> itemsList;

	public List<ConnectionPoolItem> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ConnectionPoolItem> itemsList) {
		this.itemsList = itemsList;
	}

	public static class ConnectionPoolItem {
		private String code;
		private String name;
		private String state;
		private String rate;
		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getRate() {
			return rate;
		}

		public void setRate(String rate) {
			this.rate = rate;
		}

	}
}
