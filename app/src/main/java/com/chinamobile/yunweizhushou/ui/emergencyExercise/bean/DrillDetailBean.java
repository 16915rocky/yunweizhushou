package com.chinamobile.yunweizhushou.ui.emergencyExercise.bean;

import java.util.List;

public class DrillDetailBean {
	private List<DrillDetailListbean> itemsList;
	private DrillDetailSubbean dlById;

	public List<DrillDetailListbean> getItemsList() {
		return itemsList;
	}

	public DrillDetailSubbean getDlById() {
		return dlById;
	}

	public void setItemsList(List<DrillDetailListbean> itemsList) {
		this.itemsList = itemsList;
	}

	public void setDlById(DrillDetailSubbean dlById) {
		this.dlById = dlById;
	}

	public static class DrillDetailSubbean {
		private String drillCheckName;
		private String drillId;
		private String drillOpName;
		private String drillResult;
		private String drillScene;
		private String drillState;
		private String drillSystem;
		private String drillSystemDrillTarget;
		private String drillTarget;
		private String drillType;
		private String endDate;
		private String startDate;

		public String getDrillCheckName() {
			return drillCheckName;
		}

		public String getDrillId() {
			return drillId;
		}

		public String getDrillOpName() {
			return drillOpName;
		}

		public String getDrillResult() {
			return drillResult;
		}

		public String getDrillScene() {
			return drillScene;
		}

		public String getDrillState() {
			return drillState;
		}

		public String getDrillSystem() {
			return drillSystem;
		}

		public String getDrillSystemDrillTarget() {
			return drillSystemDrillTarget;
		}

		public String getDrillTarget() {
			return drillTarget;
		}

		public String getDrillType() {
			return drillType;
		}

		public String getEndDate() {
			return endDate;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setDrillCheckName(String drillCheckName) {
			this.drillCheckName = drillCheckName;
		}

		public void setDrillId(String drillId) {
			this.drillId = drillId;
		}

		public void setDrillOpName(String drillOpName) {
			this.drillOpName = drillOpName;
		}

		public void setDrillResult(String drillResult) {
			this.drillResult = drillResult;
		}

		public void setDrillScene(String drillScene) {
			this.drillScene = drillScene;
		}

		public void setDrillState(String drillState) {
			this.drillState = drillState;
		}

		public void setDrillSystem(String drillSystem) {
			this.drillSystem = drillSystem;
		}

		public void setDrillSystemDrillTarget(String drillSystemDrillTarget) {
			this.drillSystemDrillTarget = drillSystemDrillTarget;
		}

		public void setDrillTarget(String drillTarget) {
			this.drillTarget = drillTarget;
		}

		public void setDrillType(String drillType) {
			this.drillType = drillType;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

	}

	public static class DrillDetailListbean {
		private String drillDesc;
		private String drillId;
		private String followTime;

		public String getDrillDesc() {
			return drillDesc;
		}

		public String getDrillId() {
			return drillId;
		}

		public String getFollowTime() {
			return followTime;
		}

		public void setDrillDesc(String drillDesc) {
			this.drillDesc = drillDesc;
		}

		public void setDrillId(String drillId) {
			this.drillId = drillId;
		}

		public void setFollowTime(String followTime) {
			this.followTime = followTime;
		}

	}
}
