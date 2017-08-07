package com.chinamobile.yunweizhushou.bean;

import java.util.List;

public class RechargeFunctionBean {
	private List<ItemsList> itemsList;

	public void setItemsList(List<ItemsList> itemsList) {
		this.itemsList = itemsList;
	}

	public List<ItemsList> getItemsList() {
		return this.itemsList;
	}

	public static class ItemsList {
		private String item;
		private String itemValue;
		private List<ItemsChildList> itemsList;

		public String getItemValue() {
			return itemValue;
		}

		public void setItemValue(String itemValue) {
			this.itemValue = itemValue;
		}

		public void setItem(String item) {
			this.item = item;
		}

		public String getItem() {
			return this.item;
		}

		public void setItemsList(List<ItemsChildList> itemsList) {
			this.itemsList = itemsList;
		}

		public List<ItemsChildList> getItemsList() {
			return this.itemsList;
		}
	}

	public static class ItemsChildList {
		private String name;
		private String type;
		private String value;

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getType() {
			return this.type;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

}
