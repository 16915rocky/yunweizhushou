package com.chinamobile.yunweizhushou.ui.netChange.bean;

import java.util.ArrayList;
import java.util.List;

public class OnlineChangeBean {

	private String category;// 步骤分类
	private String id;// id
	private String name;// "确认上线系统模块",--步骤名称
	private String operate_time;// "未录入",--操作时间
	private String operator;// "未录入",--操作人
	private String remark;// --关注点
	private int seq_order;// 排序
	private String state;// --确认状态(0-未录入，1-同意，2-不同意)
	private List<String> sys = new ArrayList<>();// --系统点
	private String sysStr;
	private String sys_flag;// --是否选择系统点(0-不选，1-选择)
	private String title;// "20161122紧急上线"--事件标题

	public List<String> getSys() {
		return sys;
	}

	public void setSys(List<String> sys) {
		this.sys = sys;
	}

	public String getSysStr() {
		return sysStr;
	}

	public void setSysStr(String sysStr) {
		this.sysStr = sysStr;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperate_time() {
		return operate_time;
	}

	public void setOperate_time(String operate_time) {
		this.operate_time = operate_time;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getSeq_order() {
		return seq_order;
	}

	public void setSeq_order(int seq_order) {
		this.seq_order = seq_order;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSys_flag() {
		return sys_flag;
	}

	public void setSys_flag(String sys_flag) {
		this.sys_flag = sys_flag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
