package com.chinamobile.yunweizhushou.ui.TaskExecMonitor.bean;

/**
 * Created by Administrator on 2017/11/14.
 */

public class TaskDetailListBean {
    private String job_id;
    private String create_time;
    private String log_type;
    private String op_info;
    private String container_code;
    private String host;
    private String ex_msg;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLog_type() {
        return log_type;
    }

    public void setLog_type(String log_type) {
        this.log_type = log_type;
    }

    public String getOp_info() {
        return op_info;
    }

    public void setOp_info(String op_info) {
        this.op_info = op_info;
    }

    public String getContainer_code() {
        return container_code;
    }

    public void setContainer_code(String container_code) {
        this.container_code = container_code;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getEx_msg() {
        return ex_msg;
    }

    public void setEx_msg(String ex_msg) {
        this.ex_msg = ex_msg;
    }
}
