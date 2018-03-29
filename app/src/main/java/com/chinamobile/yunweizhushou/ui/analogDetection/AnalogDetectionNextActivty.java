package com.chinamobile.yunweizhushou.ui.analogDetection;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.analogDetection.bean.AnalogDetectionNextBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/23.
 */

public class AnalogDetectionNextActivty extends BaseActivity {
    @BindView(R.id.lt_list)
    ListView ltList;
    private List<AnalogDetectionNextBean> list;
    private String alarm_time,area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarm_time=getIntent().getStringExtra("alarm_time")==null ? "":getIntent().getStringExtra("alarm_time");
        area= getIntent().getStringExtra("area")==null ? "":getIntent().getStringExtra("area");
        setContentView(R.layout.activity_analog_detectin_next);
        ButterKnife.bind(this);
        initRequest();

    }
    private void initRequest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "getCenterAlarmInfo");
        map.put("alarm_time",alarm_time);
        map.put("area", area);
        startTask(HttpRequestEnum.enum_analog_detection_next, ConstantValueUtil.URL + "SmallBox?", map);

    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        if(responseBean.isSuccess()) {
            switch (e) {
                case enum_analog_detection_next:
                    Type type = new TypeToken<List<AnalogDetectionNextBean>>() {
                    }.getType();
                    list = new Gson().fromJson(Utils.getJsonArray2(responseBean.getDATA()), type);
                    ltList.setAdapter(new CommonAdapter<AnalogDetectionNextBean>(AnalogDetectionNextActivty.this,R.layout.item_list_ad_next,list) {
                        @Override
                        protected void convert(ViewHolder viewHolder, AnalogDetectionNextBean item, int position) {
                            viewHolder.setText(R.id.tv_item1,item.getArea());
                            viewHolder.setText(R.id.tv_item2,item.getRetCode());
                            viewHolder.setText(R.id.tv_item3,item.getCnt());
                            viewHolder.setText(R.id.tv_item4,item.getRetMessage());
                            viewHolder.setText(R.id.tv_item5,item.getTrace());
                            viewHolder.setOnClickListener(R.id.tv_item5, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AnalogDetectionNextActivty.this);
                                    builder.setMessage(view.getTextAlignment());
                                    builder.create().show();
                                }
                            });
                        }
                    });
                    break;
                default:
                    break;

            }
        }else{
            Utils.ShowErrorMsg(this, responseBean.getMSG());
        }
    }
}
