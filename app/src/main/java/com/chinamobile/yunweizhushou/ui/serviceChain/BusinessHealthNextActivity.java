package com.chinamobile.yunweizhushou.ui.serviceChain;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/18.
 */

public class BusinessHealthNextActivity extends BaseActivity {
    @BindView(R.id.item1_1)
    TextView item11;
    @BindView(R.id.item1_2)
    TextView item12;
    @BindView(R.id.item2_1)
    TextView item21;
    @BindView(R.id.item2_2)
    TextView item22;
    @BindView(R.id.item3_1)
    TextView item31;
    @BindView(R.id.item3_2)
    TextView item32;
    @BindView(R.id.item4_1)
    TextView item41;
    @BindView(R.id.item4_2)
    TextView item42;
    private String title,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title=getIntent().getStringExtra("title");
        id=getIntent().getStringExtra("id");
        setContentView(R.layout.item_bh_next);
        ButterKnife.bind(this);
        initRequest();
        iniEvent();
    }

    private void iniEvent() {
        getTitleBar().setMiddleText(title);
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRequest() {
        HashMap map=new HashMap<String,String>();
        map.put("action", "getScoreDetail");
        map.put("id", id);
        startTask(HttpRequestEnum.enum_serviceChain_next, ConstantValueUtil.URL + "HealthManager?", map,false);

    }
    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);

        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }

        switch (e) {
            case enum_serviceChain_next:
                if (responseBean == null) {
                    Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
                    return;
                }
                if (responseBean.isSuccess()) {
                    try {
                        JSONObject jo=new JSONObject(responseBean.getDATA());
                        String busi_desc = jo.getString("busi_desc");
                        String busi_score = jo.getString("busi_score");
                        String business_id = jo.getString("business_id");
                        String create_time = jo.getString("create_time");
                        String direct_desc = jo.getString("direct_desc");
                        String direct_score = jo.getString("direct_score");
                        String succ_avg_desc = jo.getString("succ_avg_desc");
                        String succ_desc = jo.getString("succ_desc");
                        String succ_now_desc = jo.getString("succ_now_desc");
                        String succ_score = jo.getString("succ_score");
                        String sys_busi_name = jo.getString("sys_busi_name");
                        String sys_op_id = jo.getString("sys_op_id");
                        String time_desc = jo.getString("time_desc");
                        String time_score = jo.getString("time_score");
                        item11.setText(time_score);
                        item12.setText(time_desc);
                        item21.setText(busi_score);
                        item22.setText(busi_desc);
                        item31.setText(succ_score);
                        item32.setText(succ_desc);
                        item41.setText(direct_score);
                        item42.setText(direct_desc);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    Utils.ShowErrorMsg(this, responseBean.getMSG());
                }
                break;
            default:
                break;
        }
    }
}
