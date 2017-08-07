package com.chinamobile.yunweizhushou.ui.backlogZone.bean;

import java.util.List;

public class BacklogZongListBean {
	private List<ItemsList> itemsList;

	public List<ItemsList> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ItemsList> itemsList) {
		this.itemsList = itemsList;
	}

	public static class ItemsList {
		private String ext1;
		private String name;
		private String state;
		private String value;
		private String city;

		public String getExt1() {
			return ext1;
		}

		public void setExt1(String ext1) {
			this.ext1 = ext1;
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

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

	}

}
