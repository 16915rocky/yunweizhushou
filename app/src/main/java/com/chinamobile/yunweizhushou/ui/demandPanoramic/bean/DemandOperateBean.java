package com.chinamobile.yunweizhushou.ui.demandPanoramic.bean;

import java.util.List;

public class DemandOperateBean {

	private List<ItemsList> itemsList;

	public List<ItemsList> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ItemsList> itemsList) {
		this.itemsList = itemsList;
	}

	public static class ItemsList {
		private String item;
		private List<ItemsListChild> itemsList;

		public String getItem() {
			return item;
		}

		public void setItem(String item) {
			this.item = item;
		}

		public List<ItemsListChild> getItemsList() {
			return itemsList;
		}

		public void setItemsList(List<ItemsListChild> itemsList) {
			this.itemsList = itemsList;
		}

	}

	public static class ItemsListChild {
		private String id;
		private String title;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}

}
