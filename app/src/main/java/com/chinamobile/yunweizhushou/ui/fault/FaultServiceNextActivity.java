package com.chinamobile.yunweizhushou.ui.fault;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.NameValueBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/7.
 */

public class FaultServiceNextActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_item1)
    TextView tvItem1;
    @BindView(R.id.tv_item2)
    TextView tvItem2;
    @BindView(R.id.tv_item3)
    TextView tvItem3;
    @BindView(R.id.tv_item4)
    TextView tvItem4;
    @BindView(R.id.tv_item5)
    TextView tvItem5;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.pie_chart)
    PieChart pieChart;
    private String title;
    private List<NameValueBean> mList;
    private String time="5m";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title=getIntent().getStringExtra("title");
        setContentView(R.layout.activity_fault_service_next);
        ButterKnife.bind(this);
        initView();
        initRequest();
        initEvent();



    }

    private void initView() {
        tvDesc.setText(title);
    }

    private void initEvent() {
        getTitleBar().setMiddleText("");
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvItem1.setOnClickListener(this);
        tvItem2.setOnClickListener(this);
        tvItem3.setOnClickListener(this);
        tvItem4.setOnClickListener(this);
        tvItem5.setOnClickListener(this);
    }

    private void initRequest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("action", "getKFPie");
        map.put("daytype", "three");
        map.put("title",title);
        startTask(HttpRequestEnum.enum_service_next, ConstantValueUtil.URL + "KFTopReq?", map,true);

    }
    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);

        switch (e) {
            case enum_service_next:
                Type type = new TypeToken<List<NameValueBean>>() {
                }.getType();
                mList=new Gson().fromJson(responseBean.getDATA(),type);
                initPieChart(pieChart);
                showPieChart(pieChart,mList);
            default:
                break;
        }
    }
    private void showPieChart(PieChart pieChart,List<NameValueBean> groupList) {
        ArrayList<Entry> yVals = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        int[] colors = new int[30];
        for(int i=0;i<=groupList.size()-1;i++){
            yVals.add(new Entry(Float.valueOf(groupList.get(i).getValue()), i));
            xVals.add(groupList.get(i).getName());
            colors[i] = ConstantValueUtil.colors2[i];
        }
        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setColors(colors);
        PieData data = new PieData(xVals, dataSet);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(13f);
        pieChart.setData(data);
        pieChart.invalidate();

    }

    private void initPieChart(PieChart pieChart) {
        pieChart.setNoDataText("暂无数据,请尝试刷新");
        Legend legend = pieChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        pieChart.setDescription("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_item1:
                resetAll();
                tvItem1.setBackgroundResource(R.color.color_blue);
                tvItem1.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                time="5m";
                initRequest();
                break;
            case R.id.tv_item2:
                resetAll();
                tvItem2.setBackgroundResource(R.color.color_blue);
                tvItem2.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                time="10m";
                initRequest();
                break;
            case R.id.tv_item3:
                resetAll();
                tvItem3.setBackgroundResource(R.color.color_blue);
                tvItem3.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                time="1h";
                initRequest();
                break;
            case R.id.tv_item4:
                resetAll();
                tvItem4.setBackgroundResource(R.color.color_blue);
                tvItem4.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                time="2h";
                initRequest();
                break;
            case R.id.tv_item5:
                resetAll();
                tvItem5.setBackgroundResource(R.color.color_blue);
                tvItem5.setTextColor(ContextCompat.getColor(this,R.color.color_white));
                time="6h";
                initRequest();
                break;
            default:break;

        }
    }
    public void resetAll(){
        tvItem1.setBackgroundResource(R.color.color_white);
        tvItem1.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        tvItem2.setBackgroundResource(R.color.color_white);
        tvItem2.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        tvItem3.setBackgroundResource(R.color.color_white);
        tvItem3.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        tvItem4.setBackgroundResource(R.color.color_white);
        tvItem4.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
        tvItem5.setBackgroundResource(R.color.color_white);
        tvItem5.setTextColor(ContextCompat.getColor(this,R.color.color_blue));
    }
}
