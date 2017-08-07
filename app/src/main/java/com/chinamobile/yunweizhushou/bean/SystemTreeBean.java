package com.chinamobile.yunweizhushou.bean;

import java.util.List;

public class SystemTreeBean {

	private String item;
	private List<SystemTreeSubBean> itemsList;

	public String getItem() {
		return item;
	}

	public List<SystemTreeSubBean> getItemsList() {
		return itemsList;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setItemsList(List<SystemTreeSubBean> itemsList) {
		this.itemsList = itemsList;
	}

	public static class SystemTreeSubBean {
		private String id_2_name;
		private String id_3_name;
		private String system_level;

		public String getId_2_name() {
			return id_2_name;
		}

		public String getId_3_name() {
			return id_3_name;
		}

		public String getSystem_level() {
			return system_level;
		}

		public void setId_2_name(String id_2_name) {
			this.id_2_name = id_2_name;
		}

		public void setId_3_name(String id_3_name) {
			this.id_3_name = id_3_name;
		}

		public void setSystem_level(String system_level) {
			this.system_level = system_level;
		}

	}
}
