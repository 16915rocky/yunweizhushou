package com.chinamobile.yunweizhushou.ui.serviceChain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.serviceChain.adapter.BusinessHealthAdapter;
import com.chinamobile.yunweizhushou.ui.serviceChain.bean.BusinessHealthBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/18.
 */

public class BusinessHealthFragment extends BaseFragment {
    @BindView(R.id.tv_title_name1)
    TextView tvTitleName1;
    @BindView(R.id.tv_title_num1)
    TextView tvTitleNum1;
    @BindView(R.id.tv_title_name2)
    TextView tvTitleName2;
    @BindView(R.id.tv_title_num2)
    TextView tvTitleNum2;
    @BindView(R.id.tv_title_name3)
    TextView tvTitleName3;
    @BindView(R.id.tv_title_num3)
    TextView tvTitleNum3;
    @BindView(R.id.tv_title_name4)
    TextView tvTitleName4;
    @BindView(R.id.tv_title_num4)
    TextView tvTitleNum4;
    @BindView(R.id.lv_bh)
    MyListView lvBh;
    Unbinder unbinder;
    @BindView(R.id.rt_bh)
    MyRefreshLayout rtBh;
    private List<BusinessHealthBean> mList;
    private BusinessHealthAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_health, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRequest();
        initEvent();
        return view;
    }

    private void initEvent() {
        lvBh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), BusinessHealthNextActivity.class);
                intent.putExtra("title", mList.get(i).getSys_name() + "" + mList.get(i).getBusi_name() + "得分明细");
                intent.putExtra("id", mList.get(i).getId());
                startActivity(intent);
            }
        });
        rtBh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRequest();
            }
        });
    }

    private void initRequest() {
        HashMap map = new HashMap<String, String>();
        map.put("action", "getScoreSummary");
        startTask(HttpRequestEnum.enum_serviceChain_next, ConstantValueUtil.URL + "HealthManager?", map, false);

    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if(rtBh.isShown()){
            rtBh.setRefreshing(false);
        }
        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }

        switch (e) {
            case enum_serviceChain_next:
                if (responseBean == null) {
                    Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
                    return;
                }
                if (responseBean.isSuccess()) {
                    try {
                        JSONObject jo = new JSONObject(responseBean.getDATA());
                        JSONObject summary = jo.getJSONObject("summary");
                        String sub_health = summary.getString("sub_health");
                        String ill = summary.getString("ill");
                        String good = summary.getString("good");
                        String little_empty = summary.getString("little_empty");
                        tvTitleNum1.setText(good);
                        tvTitleNum2.setText(sub_health);
                        tvTitleNum3.setText(little_empty);
                        tvTitleNum4.setText(ill);
                        Type type = new TypeToken<List<BusinessHealthBean>>() {
                        }.getType();
                        mList = new Gson().fromJson(jo.getString("scoreList"), type);
                        mAdapter = new BusinessHealthAdapter(getActivity(), mList, R.layout.item_lv_bh);
                        lvBh.setAdapter(mAdapter);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }


                } else {
                    Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
