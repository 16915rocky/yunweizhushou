package com.chinamobile.yunweizhushou.ui.analogDetection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.analogDetection.bean.AnalogDetectionBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.widget.SelectDayDialog;
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

public class AnalogDetectionActivity extends BaseActivity {
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.list_title_3_item1)
    TextView listTitle3Item1;
    @BindView(R.id.list_title_3_item2)
    TextView listTitle3Item2;
    @BindView(R.id.list_title_3_item3)
    TextView listTitle3Item3;
    @BindView(R.id.list_title_3_item4)
    TextView listTitle3Item4;
    @BindView(R.id.common_list_3_listview)
    ListView commonList3Listview;
    private String 	currentDate,tempData ;
    private List<AnalogDetectionBean> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analog_detection);
        ButterKnife.bind(this);
        currentDate = Utils.getRequestTime5();
        tempData = Utils.getRequestTime6();
        initView();
        initRequest(currentDate);
        initEvent();
    }

    private void initEvent() {
        commonList3Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("alarm_time",list.get(i).getAlarm_time());
                intent.putExtra("area",list.get(i).getAlarm_area());
                intent.setClass(AnalogDetectionActivity.this,AnalogDetectionNextActivty.class);
                startActivity(intent);
            }
        });
        tvTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SelectDayDialog dialog = new SelectDayDialog(AnalogDetectionActivity.this);
                dialog.show();
                dialog.setBirthdayListener(new SelectDayDialog.OnBirthListener() {

                    @Override
                    public void onClick(String year, String month, String day) {
                        String currentDate1 = year + "-" + month + "-"+day;
                        tvTime.setText(currentDate1);
                        initRequest(year+":"+month+":"+day);
                    }
                });
            }
        });
    }

    private void initRequest(String time) {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "getAlarmInfo");
        map.put("cDate", time);
        startTask(HttpRequestEnum.enum_analog_detection, ConstantValueUtil.URL + "SmallBox?", map);

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
                case enum_analog_detection:
                    Type type = new TypeToken<List<AnalogDetectionBean>>() {
                    }.getType();
                    list = new Gson().fromJson(Utils.getJsonArray2(responseBean.getDATA()), type);
                    commonList3Listview.setAdapter(new CommonAdapter<AnalogDetectionBean>(AnalogDetectionActivity.this,R.layout.item_list_3,list) {
                        @Override
                        protected void convert(ViewHolder viewHolder, AnalogDetectionBean item, int position) {
                            viewHolder.setText(R.id.list_3_item1,item.getCdate());
                            viewHolder.setText(R.id.list_3_item2,item.getAlarm_area());
                            viewHolder.setText(R.id.list_3_item3,item.getContent());
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
    private void initView() {
        listTitle3Item1.setText("告警时间");
        listTitle3Item2.setText("告警范围");
        listTitle3Item3.setText("成功率");
        tvTime.setText(tempData);
        getTitleBar().setMiddleText("模拟探测");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
