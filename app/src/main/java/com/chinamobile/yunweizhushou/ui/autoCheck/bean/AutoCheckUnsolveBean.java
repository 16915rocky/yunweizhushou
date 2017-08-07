package com.chinamobile.yunweizhushou.ui.autoCheck.bean;

import java.util.List;

public class AutoCheckUnsolveBean {

	private String charge_op_name;
	private String function_des;
	private String ins_system;
	private String inspection_id;
	private String priority_id;
	private String state;
	private String title;
	private String working_hours;
	private List<AutoCheckUnsolveListBean> isList;

	public String getPriority_id() {
		return priority_id;
	}

	public String getState() {
		return state;
	}

	public String getTitle() {
		return title;
	}

	public String getWorking_hours() {
		return working_hours;
	}

	public void setPriority_id(String priority_id) {
		this.priority_id = priority_id;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setWorking_hours(String working_hours) {
		this.working_hours = working_hours;
	}

	public String getCharge_op_name() {
		return charge_op_name;
	}

	public String getFunction_des() {
		return function_des;
	}

	public String getIns_system() {
		return ins_system;
	}

	public String getInspection_id() {
		return inspection_id;
	}

	public List<AutoCheckUnsolveListBean> getIsList() {
		return isList;
	}

	public void setCharge_op_name(String charge_op_name) {
		this.charge_op_name = charge_op_name;
	}

	public void setFunction_des(String function_des) {
		this.function_des = function_des;
	}

	public void setIns_system(String ins_system) {
		this.ins_system = ins_system;
	}

	public void setInspection_id(String inspection_id) {
		this.inspection_id = inspection_id;
	}

	public void setIsList(List<AutoCheckUnsolveListBean> isList) {
		this.isList = isList;
	}

	public static class AutoCheckUnsolveListBean {
		private String follow_time;
		private String solution_desc;

		public String getFollow_time() {
			return follow_time;
		}

		public String getSolution_desc() {
			return solution_desc;
		}

		public void setFollow_time(String follow_time) {
			this.follow_time = follow_time;
		}

		public void setSolution_desc(String solution_desc) {
			this.solution_desc = solution_desc;
		}

	}
}
