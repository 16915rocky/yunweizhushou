package com.chinamobile.yunweizhushou.ui.serviceLogQuery.bean;

/**
 * Created by Administrator on 2017/7/27.
 */

public class ServiceLogQueryBean {
    private String op_code;
    private String return_result;
    private String return_desc    ;
    private String service_key;
    private String sys_op_id;
    private String op_date;
    private String error_stack_trace;

    public String getSys_op_id() {
        return sys_op_id;
    }

    public void setSys_op_id(String sys_op_id) {
        this.sys_op_id = sys_op_id;
    }

    public String getError_stack_trace() {
        return error_stack_trace;
    }

    public void setError_stack_trace(String error_stack_trace) {
        this.error_stack_trace = error_stack_trace;
    }

    public String getOp_date() {
        return op_date;
    }

    public void setOp_date(String op_date) {
        this.op_date = op_date;
    }

    public String getOp_code() {
        return op_code;
    }

    public void setOp_code(String op_code) {
        this.op_code = op_code;
    }

    public String getReturn_result() {
        return return_result;
    }

    public void setReturn_result(String return_result) {
        this.return_result = return_result;
    }

    public String getReturn_desc() {
        return return_desc;
    }

    public void setReturn_desc(String return_desc) {
        this.return_desc = return_desc;
    }

    public String getService_key() {
        return service_key;
    }

    public void setService_key(String service_key) {
        this.service_key = service_key;
    }
}
