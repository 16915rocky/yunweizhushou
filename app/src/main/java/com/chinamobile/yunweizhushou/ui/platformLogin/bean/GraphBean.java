package com.chinamobile.yunweizhushou.ui.platformLogin.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */

public class GraphBean {
    private List<String> COLUMNS;
    private String TIME;
    private String DOUBLEY;
    private String NAME;
    private List<List<String>> POINTS;
    private String CHARTS_TYPE;
    private String msg;
    private String waveId;
    private String X;
    private String YTITLE;

    public List<String> getCOLUMNS() {
        return COLUMNS;
    }

    public void setCOLUMNS(List<String> COLUMNS) {
        this.COLUMNS = COLUMNS;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getDOUBLEY() {
        return DOUBLEY;
    }

    public void setDOUBLEY(String DOUBLEY) {
        this.DOUBLEY = DOUBLEY;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public List<List<String>> getPOINTS() {
        return POINTS;
    }

    public void setPOINTS(List<List<String>> POINTS) {
        this.POINTS = POINTS;
    }

    public String getCHARTS_TYPE() {
        return CHARTS_TYPE;
    }

    public void setCHARTS_TYPE(String CHARTS_TYPE) {
        this.CHARTS_TYPE = CHARTS_TYPE;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getWaveId() {
        return waveId;
    }

    public void setWaveId(String waveId) {
        this.waveId = waveId;
    }

    public String getX() {
        return X;
    }

    public void setX(String x) {
        X = x;
    }

    public String getYTITLE() {
        return YTITLE;
    }

    public void setYTITLE(String YTITLE) {
        this.YTITLE = YTITLE;
    }
}
