package com.chinamobile.yunweizhushou.ui.hotZoneKTWY;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.hotZoneKTWY.adapter.HotZoneKTWYAdapter;
import com.chinamobile.yunweizhushou.ui.hotZoneKTWY.bean.HotZoneKTWYBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.CustomExpandableListView;
import com.chinamobile.yunweizhushou.view.MyexpanableListView;
import com.chinamobile.yunweizhushou.widget.SelectDayDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotZoneKTWYActivity extends BaseActivity {

    @BindView(R.id.pie_chart)
    PieChart pieChart;
    private BarChart barChart;
    private TextView tv_time;
    private CustomExpandableListView edlv_ktwy;
    private String currentDate;
    private List<HotZoneKTWYBean> groupList;
    private int currentIndex;
    private List<Integer> indexList = new ArrayList<>();
    private List<List<HotZoneKTWYBean>> childList;
    private HotZoneKTWYAdapter hotZoneKTWYAdapter;
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotzone_ktwy);
        ButterKnife.bind(this);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DATE);
        currentDate = Utils.getCurrentTime();
        initData();
        initView();
        initEvent();
        initRequest();
        initListDataRequest(currentDate);

    }


    private void initData() {

        childList = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            childList.add(new ArrayList<HotZoneKTWYBean>());
        }
    }


    private void initEvent() {
        getTitleBar().setMiddleText("开通网元失败专区");
        getTitleBar().setLeftButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_time.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                SelectDayDialog dialog = new SelectDayDialog(HotZoneKTWYActivity.this);
                dialog.show();
                dialog.setBirthdayListener(new SelectDayDialog.OnBirthListener() {

                    @Override
                    public void onClick(String year, String month, String day) {
                        String currentDate1 = year + "年" + month + "月" + day + "日";
                        currentDate = year + "-" + month + "-" + day;
                        tv_time.setText(currentDate1);
                        initListDataRequest(currentDate);
                    }
                });
            }
        });


        edlv_ktwy.setOnGroupClickListener(new MyexpanableListView.OnGroupClickListener() {

            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (edlv_ktwy.isGroupExpanded(groupPosition)) { // 监听父item是不是展开的
                    edlv_ktwy.collapseGroup(groupPosition);
                } else {
                    String desctiption = groupList.get(groupPosition).getDescription();
                    Log.i("ip", desctiption);
                    for (int i = 0; i < indexList.size(); i++) {
                        if (groupPosition == indexList.get(i)) {
                            return false;
                        }
                    }
                    currentIndex = groupPosition;
                    indexList.add(groupPosition);
                    initListChildRequest(desctiption);
                }
                return false;
            }


        });

    }


    private void initRequest() {
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("action", "findKTWYBar");
        startTask(HttpRequestEnum.enum_hotzone_findKTWYBar, ConstantValueUtil.URL + "HotZone?", maps, false);


    }

    private void initListDataRequest(String time) {
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("action", "findKTWYFailure");
        maps.put("time", time);
        startTask(HttpRequestEnum.enum_hotzone_findKTWYFailure, ConstantValueUtil.URL + "HotZone?", maps, false);

    }

    private void initListChildRequest(String desctiption) {
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("action", "findKTWYCityFailure");
        maps.put("description", desctiption);
        maps.put("time", currentDate);
        startTask(HttpRequestEnum.enum_hotzone_findKTWYCityFailure, ConstantValueUtil.URL + "HotZone?", maps, false);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_hotzone_findKTWYBar:
                try {
                    JSONObject jo = new JSONObject(responseBean.getDATA());
                    JSONArray ja1 = jo.getJSONArray("columns");
                    JSONArray ja2 = jo.getJSONArray("points");
                    initbarChart(barChart);
                    initonebarValue(ja1, ja2);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                break;
            case enum_hotzone_findKTWYFailure:
                if (responseBean.isSuccess()) {
                    Type t = new TypeToken<List<HotZoneKTWYBean>>() {
                    }.getType();
                    groupList = new Gson().fromJson(responseBean.getDATA(), t);
                    if(pieChart!=null) {
                        initPieChart(pieChart);
                        showPieChart(pieChart, groupList);
                    }
                    hotZoneKTWYAdapter = new HotZoneKTWYAdapter(groupList, childList, this,currentDate);
                    edlv_ktwy.setAdapter(hotZoneKTWYAdapter);
                }
            case enum_hotzone_findKTWYCityFailure:
                if (responseBean.isSuccess()) {
                    Type t = new TypeToken<List<HotZoneKTWYBean>>() {
                    }.getType();
                    List<HotZoneKTWYBean> itemList = new Gson().fromJson(responseBean.getDATA(), t);
                    childList.remove(currentIndex);
                    childList.add(currentIndex, itemList);
                    if (hotZoneKTWYAdapter == null) {
                        hotZoneKTWYAdapter = new HotZoneKTWYAdapter(groupList, childList, this,currentDate);
                    } else {
                        hotZoneKTWYAdapter.notifyDataSetChanged();
                    }
                }
            default:
                break;
        }
    }
    private void showPieChart(PieChart pieChart,List<HotZoneKTWYBean> groupList) {
        ArrayList<Entry> yVals = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        int[] colors = new int[30];
        for(int i=0;i<groupList.size()-1;i++){
            yVals.add(new Entry(Float.valueOf(groupList.get(i).getNum()), i));
            xVals.add(groupList.get(i).getDescription());
            colors[i] = ConstantValueUtil.colors2[i];
        }
        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setColors(colors);
        PieData data = new PieData(xVals, dataSet);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        pieChart.invalidate();

    }

    private void initPieChart(PieChart pieChart) {
        pieChart.setNoDataText("暂无数据,请尝试刷新");
        Legend legend = pieChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setPosition(LegendPosition.RIGHT_OF_CHART);
        pieChart.setDescription("");
    }

    private void initView() {
        barChart = (BarChart) findViewById(R.id.bt_common);
        tv_time = (TextView) findViewById(R.id.tv_time);
        edlv_ktwy = (CustomExpandableListView) findViewById(R.id.edlv_ktwy);
        edlv_ktwy.setGroupIndicator(null);
        tv_time.setText(year + "年" + month + "月" + day + "日");

    }


    private void initbarChart(BarChart barChart) {
        barChart.setDescription("");
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(false);
        //	tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        Legend l = barChart.getLegend();
        l.setPosition(LegendPosition.ABOVE_CHART_RIGHT);
        // l.setTypeface(tf);
        l.setYOffset(0f);
        l.setYEntrySpace(2f);
        l.setTextSize(10f);
        l.setEnabled(true);
        XAxis xl = barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);

        //  xl.setSpaceBetweenLabels(5);
        // xl.setEnabled(false);
//        xl.setTypeface(tf);

        YAxis leftAxis = barChart.getAxisLeft();
//        leftAxis.setTypeface(tf);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)       
        // 设置是否可以触摸
        barChart.setTouchEnabled(false);
        // 是否可以拖拽
        barChart.setDragEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.animateXY(2000, 3000);

    }


    private void initonebarValue(JSONArray columns, JSONArray points) throws JSONException {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < points.length(); i++) {
            String str = points.getJSONArray(i).getString(0);
            xVals.add(str);
        }
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < points.length(); i++) {
            Float value1 = (float) 0;
            if (!"".equals(points.getString(i))) {
                value1 = Float.parseFloat(points.getJSONArray(i).getString(1));
            }

            yVals1.add(new BarEntry(value1, i));
        }

        BarDataSet set2 = new BarDataSet(yVals1, "2017");
        set2.setColor(this.getResources().getColor(R.color.color_orange));
        ArrayList dataSets = new ArrayList();
        dataSets.add(set2);
        BarData data = new BarData(xVals, dataSets);
        data.setGroupSpace(80f);
//    data.setValueTypeface(tf);

        barChart.setData(data);
        barChart.invalidate();
    }
}
