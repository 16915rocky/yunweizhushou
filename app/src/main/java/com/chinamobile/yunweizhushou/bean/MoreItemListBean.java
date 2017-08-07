package com.chinamobile.yunweizhushou.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/30.
 */

public class MoreItemListBean {



    public MoreItemListBean() {
    }

    public MoreItemListBean(String d_name, List<MainPageGridBean> dirList) {
        this.d_name = d_name;
        this.dirList = dirList;
    }

    private String d_name;
    private List<MainPageGridBean> dirList;

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public List<MainPageGridBean> getDirList() {
        return dirList;
    }

    public void setDirList(List<MainPageGridBean> dirList) {
        this.dirList = dirList;
    }
}
