package com.chinamobile.yunweizhushou.ui.produceLine;

/**
 * Created by Administrator on 2017/7/10.
 */

public class TimeSelectEvent {
    private String time;

    public TimeSelectEvent(String time) {
        this.time = time;
    }

    public TimeSelectEvent() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
