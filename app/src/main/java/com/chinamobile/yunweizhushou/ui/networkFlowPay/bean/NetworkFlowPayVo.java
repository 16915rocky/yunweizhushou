package com.chinamobile.yunweizhushou.ui.networkFlowPay.bean;

public class NetworkFlowPayVo {

	private String taskName;// 任务名称
	private String taskInfo;// 执行环节
	private String taskMsg;// 异常信息
	private String startTime;// 开始时间
	private String endTime;// 执行时间
	private String taskState;// 执行状态

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(String taskInfo) {
		this.taskInfo = taskInfo;
	}

	public String getTaskMsg() {
		return taskMsg;
	}

	public void setTaskMsg(String taskMsg) {
		this.taskMsg = taskMsg;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

}
