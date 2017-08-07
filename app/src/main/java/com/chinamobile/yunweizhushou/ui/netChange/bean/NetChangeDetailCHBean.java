package com.chinamobile.yunweizhushou.ui.netChange.bean;

import java.util.List;

public class NetChangeDetailCHBean {

	private NetChangeHeadCHBean pcdv;
	private List<NetChangeDetailListBean> itemsList;

	public NetChangeHeadCHBean getPcdv() {
		return pcdv;
	}

	public void setPcdv(NetChangeHeadCHBean pcdv) {
		this.pcdv = pcdv;
	}

	public List<NetChangeDetailListBean> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<NetChangeDetailListBean> itemsList) {
		this.itemsList = itemsList;
	}

	public static class NetChangeHeadCHBean {
		private String chr_changeCategory;
		private String chr_changeSystem;
		private String chr_chgNumber;
		private String chr_chgTitle;
		private String chr_mchrAssessmentLevel;
		private String chr_reportDepartment;
		private String chr_reportName;
		private String drop_approvalStatus;
		private String drop_chgStatus;
		private String sel_mselchangeType;
		private String submitdate;

		public String getChr_changeCategory() {
			return chr_changeCategory;
		}

		public String getChr_changeSystem() {
			return chr_changeSystem;
		}

		public String getChr_chgNumber() {
			return chr_chgNumber;
		}

		public String getChr_chgTitle() {
			return chr_chgTitle;
		}

		public String getChr_mchrAssessmentLevel() {
			return chr_mchrAssessmentLevel;
		}

		public String getChr_reportDepartment() {
			return chr_reportDepartment;
		}

		public String getChr_reportName() {
			return chr_reportName;
		}

		public String getDrop_approvalStatus() {
			return drop_approvalStatus;
		}

		public String getDrop_chgStatus() {
			return drop_chgStatus;
		}

		public String getSel_mselchangeType() {
			return sel_mselchangeType;
		}

		public String getSubmitdate() {
			return submitdate;
		}

		public void setChr_changeCategory(String chr_changeCategory) {
			this.chr_changeCategory = chr_changeCategory;
		}

		public void setChr_changeSystem(String chr_changeSystem) {
			this.chr_changeSystem = chr_changeSystem;
		}

		public void setChr_chgNumber(String chr_chgNumber) {
			this.chr_chgNumber = chr_chgNumber;
		}

		public void setChr_chgTitle(String chr_chgTitle) {
			this.chr_chgTitle = chr_chgTitle;
		}

		public void setChr_mchrAssessmentLevel(String chr_mchrAssessmentLevel) {
			this.chr_mchrAssessmentLevel = chr_mchrAssessmentLevel;
		}

		public void setChr_reportDepartment(String chr_reportDepartment) {
			this.chr_reportDepartment = chr_reportDepartment;
		}

		public void setChr_reportName(String chr_reportName) {
			this.chr_reportName = chr_reportName;
		}

		public void setDrop_approvalStatus(String drop_approvalStatus) {
			this.drop_approvalStatus = drop_approvalStatus;
		}

		public void setDrop_chgStatus(String drop_chgStatus) {
			this.drop_chgStatus = drop_chgStatus;
		}

		public void setSel_mselchangeType(String sel_mselchangeType) {
			this.sel_mselchangeType = sel_mselchangeType;
		}

		public void setSubmitdate(String submitdate) {
			this.submitdate = submitdate;
		}

	}
}
