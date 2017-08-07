package com.chinamobile.yunweizhushou.ui.hotZoneKTWY;

import android.os.Bundle;
import android.view.View;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.hotZoneKTWY.adapter.HotZoneKTWYNextAdapter;
import com.chinamobile.yunweizhushou.ui.hotZoneKTWY.adapter.HotZoneKTWYNextAdapter2;
import com.chinamobile.yunweizhushou.ui.hotZoneKTWY.bean.HotZoneKTWYCityDesBean;
import com.chinamobile.yunweizhushou.ui.hotZoneKTWY.bean.HotZoneKTWYCityDesBean2;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/25.
 */

public class HotZoneKTWYNextActivity extends BaseActivity {
    @BindView(R.id.lv_item1)
    MyListView lvItem1;
    @BindView(R.id.lv_item2)
    MyListView lvItem2;
    private String description;
    private String city,time;
    private List<HotZoneKTWYCityDesBean> busiList;
    private List<HotZoneKTWYCityDesBean2> errList;
    private HotZoneKTWYNextAdapter busiAdapter;
    private HotZoneKTWYNextAdapter2  errAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        description=getIntent().getStringExtra("description");
        city=getIntent().getStringExtra("city");
        time=getIntent().getStringExtra("time");
        setContentView(R.layout.activity_hotzone_ktwy_next);
        ButterKnife.bind(this);
        initRequest(description,city,time);
        initEvent();

    }

    private void initEvent() {
        getTitleBar().setMiddleText("");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRequest(String description,String city,String time) {
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("action", "findKTWYCityDes");
        maps.put("description", description);
        maps.put("city", city);
        maps.put("time", time);
        startTask(HttpRequestEnum.enum_hotzone_findKTWYFailure_next, ConstantValueUtil.URL + "HotZone?", maps,true);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_hotzone_findKTWYFailure_next:
                try {
                    JSONObject jo=new JSONObject(responseBean.getDATA());
                    String busi=jo.getString("busiList");
                    String err=jo.getString("errList");
                    Type t=new TypeToken<List<HotZoneKTWYCityDesBean>>(){}.getType();
                    Type t2=new TypeToken<List<HotZoneKTWYCityDesBean2>>(){}.getType();
                    busiList=new Gson().fromJson(busi,t);
                    errList=new Gson().fromJson(err,t2);
                    busiAdapter=new HotZoneKTWYNextAdapter(this,busiList,R.layout.item_list_2);
                    errAdapter=new HotZoneKTWYNextAdapter2(this,errList,R.layout.item_list_2);
                    lvItem1.setAdapter(busiAdapter);
                    lvItem2.setAdapter(errAdapter);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
