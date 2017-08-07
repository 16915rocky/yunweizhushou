package com.chinamobile.yunweizhushou.ui.netChange.bean;

import java.util.List;

public class NetChangeDetailRMBean {

	private NetChangeHeadRMBean ordv;
	private List<NetChangeDetailListBean> itemsList;

	public NetChangeHeadRMBean getOrdv() {
		return ordv;
	}

	public List<NetChangeDetailListBean> getItemsList() {
		return itemsList;
	}

	public void setOrdv(NetChangeHeadRMBean ordv) {
		this.ordv = ordv;
	}

	public void setItemsList(List<NetChangeDetailListBean> itemsList) {
		this.itemsList = itemsList;
	}

	public static class NetChangeHeadRMBean {
		private String chk_mSelInterrrupt;
		private String chk_mSelInterrrupt2;
		private String chr_chgTitle;
		private String chr_class;
		private String chr_mchrAssessmentLevel;
		private String chr_pubNumber;
		private String chr_relatedSystemCategory;
		private String chr_reportgroup;
		private String chr_reportname;
		private String dpl_impact;
		private String drop_approvalStatus;
		private String drop_chgStatus;
		private String submitdate1;

		public String getChk_mSelInterrrupt() {
			return chk_mSelInterrrupt;
		}

		public String getChk_mSelInterrrupt2() {
			return chk_mSelInterrrupt2;
		}

		public String getChr_chgTitle() {
			return chr_chgTitle;
		}

		public String getChr_class() {
			return chr_class;
		}

		public String getChr_mchrAssessmentLevel() {
			return chr_mchrAssessmentLevel;
		}

		public String getChr_pubNumber() {
			return chr_pubNumber;
		}

		public String getChr_relatedSystemCategory() {
			return chr_relatedSystemCategory;
		}

		public String getChr_reportgroup() {
			return chr_reportgroup;
		}

		public String getChr_reportname() {
			return chr_reportname;
		}

		public String getDpl_impact() {
			return dpl_impact;
		}

		public String getDrop_approvalStatus() {
			return drop_approvalStatus;
		}

		public String getDrop_chgStatus() {
			return drop_chgStatus;
		}

		public String getSubmitdate1() {
			return submitdate1;
		}

		public void setChk_mSelInterrrupt(String chk_mSelInterrrupt) {
			this.chk_mSelInterrrupt = chk_mSelInterrrupt;
		}

		public void setChk_mSelInterrrupt2(String chk_mSelInterrrupt2) {
			this.chk_mSelInterrrupt2 = chk_mSelInterrrupt2;
		}

		public void setChr_chgTitle(String chr_chgTitle) {
			this.chr_chgTitle = chr_chgTitle;
		}

		public void setChr_class(String chr_class) {
			this.chr_class = chr_class;
		}

		public void setChr_mchrAssessmentLevel(String chr_mchrAssessmentLevel) {
			this.chr_mchrAssessmentLevel = chr_mchrAssessmentLevel;
		}

		public void setChr_pubNumber(String chr_pubNumber) {
			this.chr_pubNumber = chr_pubNumber;
		}

		public void setChr_relatedSystemCategory(String chr_relatedSystemCategory) {
			this.chr_relatedSystemCategory = chr_relatedSystemCategory;
		}

		public void setChr_reportgroup(String chr_reportgroup) {
			this.chr_reportgroup = chr_reportgroup;
		}

		public void setChr_reportname(String chr_reportname) {
			this.chr_reportname = chr_reportname;
		}

		public void setDpl_impact(String dpl_impact) {
			this.dpl_impact = dpl_impact;
		}

		public void setDrop_approvalStatus(String drop_approvalStatus) {
			this.drop_approvalStatus = drop_approvalStatus;
		}

		public void setDrop_chgStatus(String drop_chgStatus) {
			this.drop_chgStatus = drop_chgStatus;
		}

		public void setSubmitdate1(String submitdate1) {
			this.submitdate1 = submitdate1;
		}

	}
}
