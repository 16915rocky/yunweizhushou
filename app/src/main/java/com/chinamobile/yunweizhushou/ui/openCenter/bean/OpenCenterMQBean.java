package com.chinamobile.yunweizhushou.ui.openCenter.bean;

/**
 * Created by Administrator on 2017/8/31.
 */

public class OpenCenterMQBean {
    private String channel;
    private String cluster_group;
    private String dequeue;
    private String enqueue;
    private String  group_id;
    private String  pending;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCluster_group() {
        return cluster_group;
    }

    public void setCluster_group(String cluster_group) {
        this.cluster_group = cluster_group;
    }

    public String getDequeue() {
        return dequeue;
    }

    public void setDequeue(String dequeue) {
        this.dequeue = dequeue;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getEnqueue() {
        return enqueue;
    }

    public void setEnqueue(String enqueue) {
        this.enqueue = enqueue;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }
}
