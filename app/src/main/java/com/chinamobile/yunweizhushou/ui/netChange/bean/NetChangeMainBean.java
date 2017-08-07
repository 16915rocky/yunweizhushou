package com.chinamobile.yunweizhushou.ui.netChange.bean;

import java.util.List;

public class NetChangeMainBean {

	private List<NetChangeMainSubBean> itemsList;
	private String onLineRelease;
	private String platformChange;

	public List<NetChangeMainSubBean> getItemsList() {
		return itemsList;
	}

	public String getOnLineRelease() {
		return onLineRelease;
	}

	public String getPlatformChange() {
		return platformChange;
	}

	public void setItemsList(List<NetChangeMainSubBean> itemsList) {
		this.itemsList = itemsList;
	}

	public void setOnLineRelease(String onLineRelease) {
		this.onLineRelease = onLineRelease;
	}

	public void setPlatformChange(String platformChange) {
		this.platformChange = platformChange;
	}

	public static class NetChangeMainSubBean {
		private String chr_chgTitle;
		private String chr_reportGroup;
		private String chr_reportName;
		private String influence;
		private String submitDate;
		private String chr_chgnumber;

		public String getChr_chgnumber() {
			return chr_chgnumber;
		}

		public void setChr_chgnumber(String chr_chgnumber) {
			this.chr_chgnumber = chr_chgnumber;
		}

		public String getChr_chgTitle() {
			return chr_chgTitle;
		}

		public String getChr_reportGroup() {
			return chr_reportGroup;
		}

		public String getChr_reportName() {
			return chr_reportName;
		}

		public String getInfluence() {
			return influence;
		}

		public String getSubmitDate() {
			return submitDate;
		}

		public void setChr_chgTitle(String chr_chgTitle) {
			this.chr_chgTitle = chr_chgTitle;
		}

		public void setChr_reportGroup(String chr_reportGroup) {
			this.chr_reportGroup = chr_reportGroup;
		}

		public void setChr_reportName(String chr_reportName) {
			this.chr_reportName = chr_reportName;
		}

		public void setInfluence(String influence) {
			this.influence = influence;
		}

		public void setSubmitDate(String submitDate) {
			this.submitDate = submitDate;
		}
	}

}
