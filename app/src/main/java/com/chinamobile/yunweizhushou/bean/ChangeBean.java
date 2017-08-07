package com.chinamobile.yunweizhushou.bean;

public class ChangeBean {
	private String id;// 主键
	private String hardWare;// 变更类别
	private String saveHardWare;// 变更子类别
	private String changeGroup;// 变更组别
	private String content;// 变更内容
	private String approvalLevel;// 审批级别
	private String riskLevel;// 风险等级
	private String operator;// 实施人员
	private String mobile;// 人员号码
	private String beginDate;// 开始时间
	private String endDate;// 结束时间
	private String influence;// 业务影响

	public String getId() {
		return id;
	}

	public String getHardWare() {
		return hardWare;
	}

	public String getSaveHardWare() {
		return saveHardWare;
	}

	public String getChangeGroup() {
		return changeGroup;
	}

	public String getContent() {
		return content;
	}

	public String getApprovalLevel() {
		return approvalLevel;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public String getOperator() {
		return operator;
	}

	public String getMobile() {
		return mobile;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getInfluence() {
		return influence;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setHardWare(String hardWare) {
		this.hardWare = hardWare;
	}

	public void setSaveHardWare(String saveHardWare) {
		this.saveHardWare = saveHardWare;
	}

	public void setChangeGroup(String changeGroup) {
		this.changeGroup = changeGroup;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setInfluence(String influence) {
		this.influence = influence;
	}

}
