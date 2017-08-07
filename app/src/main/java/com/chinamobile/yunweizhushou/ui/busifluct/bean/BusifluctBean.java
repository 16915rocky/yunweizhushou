package com.chinamobile.yunweizhushou.ui.busifluct.bean;

public class BusifluctBean {
	/**
	 * color : 0 value : 0
	 */

	private ItemBean order_anomaly;
	/**
	 * color : 0 value : 259774
	 */

	private ItemBean overtime_6d;
	/**
	 * color : 0 value : 0
	 */

	private ItemBean billing_backlog;
	/**
	 * color : 1 value : 503
	 */

	private ItemBean overtime_72h;
	/**
	 * color : 0 value : 97
	 */

	private ItemBean pro_failure;
	/**
	 * color : 0 value : 0
	 */

	private ItemBean order_backlog;
	/**
	 * color : 1 value : 13477
	 */

	private ItemBean nobilling_backlog;
	/**
	 * color : 0 value : 0
	 */

	private ItemBean sms_backlog;

	public ItemBean getOrder_anomaly() {
		return order_anomaly;
	}

	public void setOrder_anomaly(ItemBean order_anomaly) {
		this.order_anomaly = order_anomaly;
	}

	public ItemBean getOvertime_6d() {
		return overtime_6d;
	}

	public void setOvertime_6d(ItemBean overtime_6d) {
		this.overtime_6d = overtime_6d;
	}

	public ItemBean getBilling_backlog() {
		return billing_backlog;
	}

	public void setBilling_backlog(ItemBean billing_backlog) {
		this.billing_backlog = billing_backlog;
	}

	public ItemBean getOvertime_72h() {
		return overtime_72h;
	}

	public void setOvertime_72h(ItemBean overtime_72h) {
		this.overtime_72h = overtime_72h;
	}

	public ItemBean getPro_failure() {
		return pro_failure;
	}

	public void setPro_failure(ItemBean pro_failure) {
		this.pro_failure = pro_failure;
	}

	public ItemBean getOrder_backlog() {
		return order_backlog;
	}

	public void setOrder_backlog(ItemBean order_backlog) {
		this.order_backlog = order_backlog;
	}

	public ItemBean getNobilling_backlog() {
		return nobilling_backlog;
	}

	public void setNobilling_backlog(ItemBean nobilling_backlog) {
		this.nobilling_backlog = nobilling_backlog;
	}

	public ItemBean getSms_backlog() {
		return sms_backlog;
	}

	public void setSms_backlog(ItemBean sms_backlog) {
		this.sms_backlog = sms_backlog;
	}

	public static class ItemBean {
		private String color;
		private String value;

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

}
