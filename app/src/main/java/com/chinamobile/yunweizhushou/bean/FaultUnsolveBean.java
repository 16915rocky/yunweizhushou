package com.chinamobile.yunweizhushou.bean;

import java.util.List;

public class FaultUnsolveBean {

	private String accidentId;
	private String accidentTitle;
	private String handleGroup;
	private String pdlevel;
	private String time;
	private String dplStatus;
	private String chr_kfnottitle;
	
	public String getChr_kfnottitle() {
		return chr_kfnottitle;
	}

	public void setChr_kfnottitle(String chr_kfnottitle) {
		this.chr_kfnottitle = chr_kfnottitle;
	}

	private List<FaultProgressBean> progressList;

	public String getDplStatus() {
		return dplStatus;
	}

	public void setDplStatus(String dplStatus) {
		this.dplStatus = dplStatus;
	}

	public String getAccidentId() {
		return accidentId;
	}

	public String getAccidentTitle() {
		return accidentTitle;
	}

	public String getHandleGroup() {
		return handleGroup;
	}

	public String getPdlevel() {
		return pdlevel;
	}

	public String getTime() {
		return time;
	}

	public List<FaultProgressBean> getProgressList() {
		return progressList;
	}

	public void setAccidentId(String accidentId) {
		this.accidentId = accidentId;
	}

	public void setAccidentTitle(String accidentTitle) {
		this.accidentTitle = accidentTitle;
	}

	public void setHandleGroup(String handleGroup) {
		this.handleGroup = handleGroup;
	}

	public void setPdlevel(String pdlevel) {
		this.pdlevel = pdlevel;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setProgressList(List<FaultProgressBean> progressList) {
		this.progressList = progressList;
	}

}
