package com.chinamobile.yunweizhushou.ui.analogDetection.bean;

/**
 * Created by Administrator on 2018/1/23.
 */

public class AnalogDetectionBean {
    private String  cdate;
    private String alarm_area;
    private String content;
    private String alarm_time;

    public String getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(String alarm_time) {
        this.alarm_time = alarm_time;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getAlarm_area() {
        return alarm_area;
    }

    public void setAlarm_area(String alarm_area) {
        this.alarm_area = alarm_area;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
