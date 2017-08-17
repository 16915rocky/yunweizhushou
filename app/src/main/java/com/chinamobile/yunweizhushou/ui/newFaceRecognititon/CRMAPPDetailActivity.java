package com.chinamobile.yunweizhushou.ui.newFaceRecognititon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.newFaceRecognititon.adapter.CRMAPPDetailAdapter;
import com.chinamobile.yunweizhushou.ui.newFaceRecognititon.bean.CRMAPPDetailBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/15.
 */

public class CRMAPPDetailActivity extends BaseActivity {


    @BindView(R.id.common_list_5_listview)
    MyListView commonList5Listview;
    private List<CRMAPPDetailBean> mList;
    private CRMAPPDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm_app_detail);
        ButterKnife.bind(this);
        initRequest();
        initEvent();
    }

    private void initEvent() {
        getTitleBar().setMiddleText("地市详情");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initRequest() {
        HashMap map2 = new HashMap<String, String>();
        map2.put("action", "getCRMAPPDetail");
        startTask(HttpRequestEnum.enum_crm_app_Detail, ConstantValueUtil.URL + "RealNameMonitoring?", map2, true);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);

        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_crm_app_Detail:
                Type t = new TypeToken<List<CRMAPPDetailBean>>() {
                }.getType();
                mList = new Gson().fromJson(responseBean.getDATA(), t);
                mAdapter = new CRMAPPDetailAdapter(this, mList, R.layout.item_crm_app_detail);
                View view = LayoutInflater.from(this).inflate(R.layout.item_crm_app_detail_title, null);
                commonList5Listview.addHeaderView(view);
                commonList5Listview.setAdapter(mAdapter);
                break;
            default:
                break;
        }
    }

}
