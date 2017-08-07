package com.chinamobile.yunweizhushou.bean;

import java.util.List;

public class FaultTodayBean {

	private String accidentTitle;
	// private List<FaultLineChartDataBean> dataList;
	// private List<SimpleUserNameBean> focusList;
	private String id;
	private String pdlevel;
	private List<FaultProgressBean> progressList;
	private String accidentSource;
	private String createTime;
	private String duration;

	private String smsRecord;
	private String accidentDes;

	private String contantConfig;
	private String dplStatus;

	public String getContantConfig() {
		return contantConfig;
	}

	public String getDplStatus() {
		return dplStatus;
	}

	public void setContantConfig(String contantConfig) {
		this.contantConfig = contantConfig;
	}

	public void setDplStatus(String dplStatus) {
		this.dplStatus = dplStatus;
	}

	public String getAccidentDes() {
		return accidentDes;
	}

	public void setAccidentDes(String accidentDes) {
		this.accidentDes = accidentDes;
	}

	public String getSmsRecord() {
		return smsRecord;
	}

	public void setSmsRecord(String smsRecord) {
		this.smsRecord = smsRecord;
	}

	public String getAccidentSource() {
		return accidentSource;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setAccidentSource(String accidentSource) {
		this.accidentSource = accidentSource;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getAccidentTitle() {
		return accidentTitle;
	}

	// public List<FaultLineChartDataBean> getDataList() {
	// return dataList;
	// }
	//
	// public List<SimpleUserNameBean> getFocusList() {
	// return focusList;
	// }

	public String getId() {
		return id;
	}

	public String getPdlevel() {
		return pdlevel;
	}

	public List<FaultProgressBean> getProgressList() {
		return progressList;
	}

	public void setAccidentTitle(String accidentTitle) {
		this.accidentTitle = accidentTitle;
	}

	// public void setDataList(List<FaultLineChartDataBean> dataList) {
	// this.dataList = dataList;
	// }
	//
	// public void setFocusList(List<SimpleUserNameBean> focusList) {
	// this.focusList = focusList;
	// }

	public void setId(String id) {
		this.id = id;
	}

	public void setPdlevel(String pdlevel) {
		this.pdlevel = pdlevel;
	}

	public void setProgressList(List<FaultProgressBean> progressList) {
		this.progressList = progressList;
	}

}
