package com.chinamobile.yunweizhushou.ui.demandPanoramic.bean;

import java.util.List;

public class DemandDetailBean {
	private String businessUnit;
	private String charge_op_name;
	private String demandId;
	private String finishDate;
	private String focuser;
	private String functionDes;
	private String importance;
	private String planDate;
	private String planer;
	private String priority_id;
	private String proSub;
	private String pro_direction;
	private List<ProgressList> progressList;
	private String realizationWay;
	private String startDate;
	private String state;
	private String title;
	private String workingHours;
	private String busiOffice;

	public static class ProgressList {

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		private String date;
		private String description;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getCharge_op_name() {
		return charge_op_name;
	}

	public void setCharge_op_name(String charge_op_name) {
		this.charge_op_name = charge_op_name;
	}

	public String getDemandId() {
		return demandId;
	}

	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public String getFocuser() {
		return focuser;
	}

	public void setFocuser(String focuser) {
		this.focuser = focuser;
	}

	public String getFunctionDes() {
		return functionDes;
	}

	public void setFunctionDes(String functionDes) {
		this.functionDes = functionDes;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	public String getPlaner() {
		return planer;
	}

	public void setPlaner(String planer) {
		this.planer = planer;
	}

	public String getPriority_id() {
		return priority_id;
	}

	public void setPriority_id(String priority_id) {
		this.priority_id = priority_id;
	}

	public String getProSub() {
		return proSub;
	}

	public void setProSub(String proSub) {
		this.proSub = proSub;
	}

	public String getPro_direction() {
		return pro_direction;
	}

	public void setPro_direction(String pro_direction) {
		this.pro_direction = pro_direction;
	}

	public List<ProgressList> getProgressList() {
		return progressList;
	}

	public void setProgressList(List<ProgressList> progressList) {
		this.progressList = progressList;
	}

	public String getRealizationWay() {
		return realizationWay;
	}

	public void setRealizationWay(String realizationWay) {
		this.realizationWay = realizationWay;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}

	public String getBusiOffice() {
		return busiOffice;
	}

	public void setBusiOffice(String busiOffice) {
		this.busiOffice = busiOffice;
	}
	

}
