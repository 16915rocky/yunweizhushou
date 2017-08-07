package com.chinamobile.yunweizhushou.ui.main;

import com.chinamobile.yunweizhushou.bean.MainPageGridBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class FinishReturnResult {
    private List<MainPageGridBean> list;
    public FinishReturnResult(List<MainPageGridBean> list) {
        this.list=list;
    }

    public List<MainPageGridBean> getList() {
        return list;
    }
}
