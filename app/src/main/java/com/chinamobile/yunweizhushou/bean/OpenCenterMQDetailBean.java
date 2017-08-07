package com.chinamobile.yunweizhushou.bean;

public class OpenCenterMQDetailBean {
	private String channel;
	private String cluster_group;
	private String dequeue;
	private String enqueue;
	private String group_id;
	private String pending;

	public String getChannel() {
		return channel;
	}

	public String getCluster_group() {
		return cluster_group;
	}

	public String getDequeue() {
		return dequeue;
	}

	public String getEnqueue() {
		return enqueue;
	}

	public String getGroup_id() {
		return group_id;
	}

	public String getPending() {
		return pending;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setCluster_group(String cluster_group) {
		this.cluster_group = cluster_group;
	}

	public void setDequeue(String dequeue) {
		this.dequeue = dequeue;
	}

	public void setEnqueue(String enqueue) {
		this.enqueue = enqueue;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public void setPending(String pending) {
		this.pending = pending;
	}

}
