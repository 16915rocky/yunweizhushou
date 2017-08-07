package com.chinamobile.yunweizhushou.ui.reconciliationSchedule.bean;

import java.util.List;

public class ReconciliationScheduleBean {

	private List<ItemList> itemsList;

	public List<ItemList> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ItemList> itemsList) {
		this.itemsList = itemsList;
	}

	public static class ItemList {
		private String ext1;
		private String ext3;
		private String region_id;
		private String state;

		public String getExt1() {
			return ext1;
		}

		public void setExt1(String ext1) {
			this.ext1 = ext1;
		}

		public String getExt3() {
			return ext3;
		}

		public void setExt3(String ext3) {
			this.ext3 = ext3;
		}

		public String getRegion_id() {
			return region_id;
		}

		public void setRegion_id(String region_id) {
			this.region_id = region_id;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	}
}
