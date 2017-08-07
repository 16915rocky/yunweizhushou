package com.chinamobile.yunweizhushou.ui.backlogZone.bean;

import java.util.List;

public class BacklogZongBean {

	private List<ItemsListBean> itemsList;

	public List<ItemsListBean> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ItemsListBean> itemsList) {
		this.itemsList = itemsList;
	}

	public static class ItemsListBean {
		private String code_desc;
		private String code_name;
		private String code_type_alias;
		private String code_value;
		private String extern_code_type;

		private List<ItemsListChildBean> itemsList;

		public String getCode_desc() {
			return code_desc;
		}

		public void setCode_desc(String code_desc) {
			this.code_desc = code_desc;
		}

		public String getCode_name() {
			return code_name;
		}

		public void setCode_name(String code_name) {
			this.code_name = code_name;
		}

		public String getCode_type_alias() {
			return code_type_alias;
		}

		public void setCode_type_alias(String code_type_alias) {
			this.code_type_alias = code_type_alias;
		}

		public String getCode_value() {
			return code_value;
		}

		public void setCode_value(String code_value) {
			this.code_value = code_value;
		}

		public String getExtern_code_type() {
			return extern_code_type;
		}

		public void setExtern_code_type(String extern_code_type) {
			this.extern_code_type = extern_code_type;
		}

		public List<ItemsListChildBean> getItemsList() {
			return itemsList;
		}

		public void setItemsList(List<ItemsListChildBean> itemsList) {
			this.itemsList = itemsList;
		}

		public static class ItemsListChildBean {
			private String code_desc;
			private String code_name;
			private String code_type_alias;
			private String code_value;
			private String extern_code_type;

			private List<ItemsListChildSubBean> itemsList;

			public String getCode_desc() {
				return code_desc;
			}

			public void setCode_desc(String code_desc) {
				this.code_desc = code_desc;
			}

			public String getCode_name() {
				return code_name;
			}

			public void setCode_name(String code_name) {
				this.code_name = code_name;
			}

			public String getCode_type_alias() {
				return code_type_alias;
			}

			public void setCode_type_alias(String code_type_alias) {
				this.code_type_alias = code_type_alias;
			}

			public String getCode_value() {
				return code_value;
			}

			public void setCode_value(String code_value) {
				this.code_value = code_value;
			}

			public String getExtern_code_type() {
				return extern_code_type;
			}

			public void setExtern_code_type(String extern_code_type) {
				this.extern_code_type = extern_code_type;
			}

			public List<ItemsListChildSubBean> getItemsList() {
				return itemsList;
			}

			public void setItemsList(List<ItemsListChildSubBean> itemsList) {
				this.itemsList = itemsList;
			}

			public static class ItemsListChildSubBean {
				private String code_desc;
				private String code_name;
				private String code_type_alias;
				private String code_value;
				private String extern_code_type;

				public String getCode_desc() {
					return code_desc;
				}

				public void setCode_desc(String code_desc) {
					this.code_desc = code_desc;
				}

				public String getCode_name() {
					return code_name;
				}

				public void setCode_name(String code_name) {
					this.code_name = code_name;
				}

				public String getCode_type_alias() {
					return code_type_alias;
				}

				public void setCode_type_alias(String code_type_alias) {
					this.code_type_alias = code_type_alias;
				}

				public String getCode_value() {
					return code_value;
				}

				public void setCode_value(String code_value) {
					this.code_value = code_value;
				}

				public String getExtern_code_type() {
					return extern_code_type;
				}

				public void setExtern_code_type(String extern_code_type) {
					this.extern_code_type = extern_code_type;
				}
			}
		}
	}
}
