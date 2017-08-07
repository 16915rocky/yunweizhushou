package com.chinamobile.yunweizhushou.ui.netChange.bean;

import java.util.List;

public class NetChangeBean2 {
	private String test;
	private String change;
	private String leave;
	private String solve;
	private String onlineTotal;
	private String onlineSucc;
	private String changeTotal;
	private String changeSucc;
	private List<ItemListBean> itemsList;

	public String getTest() {
		return test;
	}

	public void setTest(String teString) {
		this.test = teString;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public String getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

	public String getSolve() {
		return solve;
	}

	public void setSolve(String solve) {
		this.solve = solve;
	}

	public String getOnlineTotal() {
		return onlineTotal;
	}

	public void setOnlineTotal(String onlineTotal) {
		this.onlineTotal = onlineTotal;
	}

	public String getOnlineSucc() {
		return onlineSucc;
	}

	public void setOnlineSucc(String onlineSucc) {
		this.onlineSucc = onlineSucc;
	}

	public String getChangeTotal() {
		return changeTotal;
	}

	public void setChangeTotal(String changeTotal) {
		this.changeTotal = changeTotal;
	}

	public String getChangeSucc() {
		return changeSucc;
	}

	public void setChangeSucc(String changeSucc) {
		this.changeSucc = changeSucc;
	}

	public List<ItemListBean> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ItemListBean> itemsList) {
		this.itemsList = itemsList;
	}

	public static class ItemListBean {
		private String title;
		private String time;
		private String state;
		private String online_plan;
		private String types;

		public String getOnline_plan() {
			return online_plan;
		}

		public String getTypes() {
			return types;
		}

		public void setOnline_plan(String online_plan) {
			this.online_plan = online_plan;
		}

		public void setTypes(String types) {
			this.types = types;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	}
}
