package com.chinamobile.yunweizhushou.ui.newFaceRecognititon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/15.
 */

public class NewFaceRecognititonFragment extends BaseFragment implements View.OnClickListener {


    Unbinder unbinder;
    @BindView(R.id.item1_1)
    TextView item11;
    @BindView(R.id.item1_2)
    TextView item12;
    @BindView(R.id.item1_3)
    TextView item13;
    @BindView(R.id.item2_1)
    TextView item21;
    @BindView(R.id.item2_2)
    TextView item22;
    @BindView(R.id.item2_3)
    TextView item23;
    @BindView(R.id.item3_1)
    TextView item31;
    @BindView(R.id.item3_2)
    TextView item32;
    @BindView(R.id.item3_3)
    TextView item33;
    @BindView(R.id.item4_1)
    TextView item41;
    @BindView(R.id.item4_2)
    TextView item42;
    @BindView(R.id.item4_3)
    TextView item43;
    @BindView(R.id.item5_1)
    TextView item51;
    @BindView(R.id.item5_2)
    TextView item52;
    @BindView(R.id.item5_3)
    TextView item53;
    @BindView(R.id.item6_1)
    TextView item61;
    @BindView(R.id.item6_2)
    TextView item62;
    @BindView(R.id.item6_3)
    TextView item63;
    @BindView(R.id.item7_1)
    TextView item71;
    @BindView(R.id.item7_2)
    TextView item72;
    @BindView(R.id.item7_3)
    TextView item73;
    @BindView(R.id.item8_1)
    TextView item81;
    @BindView(R.id.item8_2)
    TextView item82;
    @BindView(R.id.item8_3)
    TextView item83;
    @BindView(R.id.item9_1)
    TextView item91;
    @BindView(R.id.item9_2)
    TextView item92;
    @BindView(R.id.item9_3)
    TextView item93;
    @BindView(R.id.item10_1)
    TextView item101;
    @BindView(R.id.item10_2)
    TextView item102;
    @BindView(R.id.item10_3)
    TextView item103;
    @BindView(R.id.item11_1)
    TextView item111;
    @BindView(R.id.item11_2)
    TextView item112;
    @BindView(R.id.item11_3)
    TextView item113;
    @BindView(R.id.rt_face)
    MyRefreshLayout rtFace;
    @BindView(R.id.lt_select1)
    LinearLayout ltSelect1;
    @BindView(R.id.lt_select2)
    LinearLayout ltSelect2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_new_face_recognititon, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRequest();
        initEvent();
        return view;
    }

    private void initEvent() {
        ltSelect1.setOnClickListener(this);
        ltSelect2.setOnClickListener(this);
        rtFace.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRequest();
            }
        });
    }

    private void initRequest() {
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("action", "getOverview");
        startTask(HttpRequestEnum.enum_face_recognition, ConstantValueUtil.URL + "RealNameMonitoring?", maps);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (rtFace.isShown()) {
            rtFace.setRefreshing(false);
        }
        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_face_recognition:
                if (responseBean.isSuccess()) {
                    JSONObject jo = null;
                    JSONObject business = null;
                    JSONObject channel = null;
                    JSONObject crm = null;
                    JSONObject file_up = null;
                    JSONObject file_down = null;
                    JSONObject file_id = null;
                    JSONObject face_identify = null;
                    JSONObject crm_app = null;
                    JSONObject open_account = null;
                    JSONObject reenter_net = null;
                    JSONObject realname_register = null;


                    try {
                        jo = new JSONObject(responseBean.getDATA());
                        business = jo.getJSONObject("business");
                        item11.setText(business.getString("name"));
                        item12.setText(business.getString("num"));
                        item13.setText(business.getString("rate") + "%");
                        channel = jo.getJSONObject("channel");
                        item21.setText(channel.getString("name"));
                        item22.setText(channel.getString("num"));
                        item23.setText(channel.getString("rate") + "%");
                        crm = jo.getJSONObject("crm");
                        item31.setText(crm.getString("name"));
                        item32.setText(crm.getString("num"));
                        item33.setText(crm.getString("rate") + "%");
                        file_up = jo.getJSONObject("file_up");
                        item41.setText(file_up.getString("name"));
                        item42.setText(file_up.getString("num"));
                        item43.setText(file_up.getString("rate") + "%");
                        file_down = jo.getJSONObject("file_down");
                        item51.setText(file_down.getString("name"));
                        item52.setText(file_down.getString("num"));
                        item53.setText(file_down.getString("rate") + "%");
                        file_id = jo.getJSONObject("file_id");
                        item61.setText(file_id.getString("name"));
                        item62.setText(file_id.getString("num"));
                        item63.setText(file_id.getString("rate") + "%");
                        face_identify = jo.getJSONObject("face_identify");
                        item71.setText(face_identify.getString("name"));
                        item72.setText(face_identify.getString("num"));
                        item73.setText(face_identify.getString("rate") + "%");
                        crm_app = jo.getJSONObject("crm_app");
                        item81.setText(crm_app.getString("name"));
                        item82.setText(crm_app.getString("num"));
                        item83.setText(crm_app.getString("rate") + "%");
                        open_account = jo.getJSONObject("open_account");
                        item91.setText(open_account.getString("name"));
                        item92.setText(open_account.getString("num"));
                        item93.setText(open_account.getString("rate") + "%");
                        reenter_net = jo.getJSONObject("reenter_net");
                        item101.setText(reenter_net.getString("name"));
                        item102.setText(reenter_net.getString("num"));
                        item103.setText(reenter_net.getString("rate") + "%");
                        realname_register = jo.getJSONObject("realname_register");
                        item111.setText(realname_register.getString("name"));
                        item112.setText(realname_register.getString("num"));
                        item113.setText(realname_register.getString("rate") + "%");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                }
                break;

        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lt_select1:
                intent.setClass(getActivity(), FILEIDDetailActivity.class);
                break;
            case R.id.lt_select2:
                intent.setClass(getActivity(), CRMAPPDetailActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}
