package com.chinamobile.yunweizhushou.ui.capes.bean;

import java.util.List;

public class ReconciliationScheduleDetailBean {
	/**
	 * end_time : 2016-09-01 09:34:16 ext1 : 01 ext3 : 账单表清理 start_time :
	 * 2016-09-01 09:33:26 state : 正常结束 task_code : CleanEmendB
	 */

	private List<ItemsListBean> itemsList;

	public List<ItemsListBean> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ItemsListBean> itemsList) {
		this.itemsList = itemsList;
	}

	public static class ItemsListBean {
		private String end_time;
		private String ext1;
		private String ext3;
		private String start_time;
		private String state;
		private String task_code;

		private String plan_starttime;
		private String plan_endtime;

		public String getPlan_starttime() {
			return plan_starttime;
		}

		public String getPlan_endtime() {
			return plan_endtime;
		}

		public void setPlan_starttime(String plan_starttime) {
			this.plan_starttime = plan_starttime;
		}

		public void setPlan_endtime(String plan_endtime) {
			this.plan_endtime = plan_endtime;
		}

		public String getEnd_time() {
			return end_time;
		}

		public void setEnd_time(String end_time) {
			this.end_time = end_time;
		}

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

		public String getStart_time() {
			return start_time;
		}

		public void setStart_time(String start_time) {
			this.start_time = start_time;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getTask_code() {
			return task_code;
		}

		public void setTask_code(String task_code) {
			this.task_code = task_code;
		}
	}

}
