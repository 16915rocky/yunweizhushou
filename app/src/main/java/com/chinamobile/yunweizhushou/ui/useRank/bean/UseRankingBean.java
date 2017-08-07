package com.chinamobile.yunweizhushou.ui.useRank.bean;

import java.util.List;

public class UseRankingBean {
	private List<ItemsList> itemsList;

	public void setItemsList(List<ItemsList> itemsList) {
		this.itemsList = itemsList;
	}

	public List<ItemsList> getItemsList() {
		return this.itemsList;
	}

	public static class ItemsList {
		private String consumerUseNum;
		private String menuName;
		private String useMenuNum;
		private String userName;

		public void setConsumerUseNum(String consumerUseNum) {
			this.consumerUseNum = consumerUseNum;
		}

		public String getConsumerUseNum() {
			return this.consumerUseNum;
		}

		public void setMenuName(String menuName) {
			this.menuName = menuName;
		}

		public String getMenuName() {
			return this.menuName;
		}

		public void setUseMenuNum(String useMenuNum) {
			this.useMenuNum = useMenuNum;
		}

		public String getUseMenuNum() {
			return this.useMenuNum;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getUserName() {
			return this.userName;
		}
	}
}
