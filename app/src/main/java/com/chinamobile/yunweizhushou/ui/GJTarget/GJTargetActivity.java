package com.chinamobile.yunweizhushou.ui.GJTarget;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.NameValueBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.hotZoneKQBK.adapter.RechargeFunctionListShadowAdapter;
import com.chinamobile.yunweizhushou.ui.hotZoneKQBK.bean.GraphBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bangcle.uihijacksdk.BangcleUihijackSDK.getActivity;

/**
 * Created by Administrator on 2017/7/14.
 */

public class GJTargetActivity extends BaseActivity {
    @BindView(R.id.lv)
    MyListView lv;
    @BindView(R.id.barChart)
    HorizontalBarChart barChart;
    @BindView(R.id.title_left_button)
    ImageButton titleLeftButton;
    @BindView(R.id.title_middle_text)
    TextView titleMiddleText;
    @BindView(R.id.title_middle_search)
    EditText titleMiddleSearch;
    @BindView(R.id.tv_title_barChart)
    TextView tvTitleBarChart;
    private List<GraphBean> mList;
    private RechargeFunctionListShadowAdapter mAdapter;
    private List<NameValueBean> barList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gj_target);
        ButterKnife.bind(this);
        initEvent();
        initBarChart();
        initRequest();
    }

    private void initEvent() {
        getTitleBar().setLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getTitleBar().setMiddleText("告警专区");
    }

    private void initBarChart() {
        barChart.setDescription("");// 数据描述
        barChart.setDrawBarShadow(false);// 会在各条 bar 后面绘制灰色
        barChart.setNoDataTextDescription("暂无数据");// 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.setTouchEnabled(false);// 设置是否可以触摸
        barChart.setDragEnabled(false);// 是否可以拖拽
        barChart.setScaleEnabled(false);// 是否可以缩放
        barChart.setPinchZoom(false);
        // 不绘制从Y轴出发的横向直线
        YAxis left = barChart.getAxisLeft();
        // left.setDrawGridLines(false);
        // left.setDrawAxisLine(false);

        // 设置比例图标示
        Legend mLegend = barChart.getLegend();
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        mLegend.setForm(Legend.LegendForm.SQUARE); // 样式
        mLegend.setFormSize(10f);// 指示器的大小
        mLegend.setTextColor(Color.BLACK);// 颜色
        mLegend.setWordWrapEnabled(true);

        // X轴设定
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);// X轴在下边
        xAxis.setTextSize(6f);
        // xAxis.setDrawAxisLine(false);
        // xAxis.setDrawGridLines(false);

    }

    private void initRequest() {
        HashMap map2 = new HashMap<String, String>();
        map2.put("action", "findGJTarget");
        map2.put("id", "1041");
        startTask(HttpRequestEnum.enum_gj_target, ConstantValueUtil.URL + "HotZone?", map2, true);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_gj_target:
                try {
                    JSONObject data = new JSONObject(responseBean.getDATA());
                    String amount = data.getString("amount");
                    String rate = data.getString("rate");
                    String sysTop = data.getString("sysTop");
                    List<String> strList = new ArrayList<String>();
                    strList.add(amount);
                    strList.add(rate);
                    Type t = new TypeToken<List<GraphBean>>() {
                    }.getType();
                    mList = new Gson().fromJson(strList.toString(), t);
                    mAdapter = new RechargeFunctionListShadowAdapter(getActivity(), mList);
                    lv.setAdapter(mAdapter);
                    Type t2 = new TypeToken<List<NameValueBean>>() {
                    }.getType();
                    barList = new Gson().fromJson(sysTop, t2);
                    setBarChartValue(barList);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                break;
            default:
                break;
        }
    }

    public void setBarChartValue(List<NameValueBean> barList) {
        ArrayList<BarEntry> yVals2 = new ArrayList<>();
        ArrayList<String> xVals2 = new ArrayList<>();
        for (int i = 0; i < barList.size(); i++) {
            xVals2.add(barList.get(barList.size() - i - 1).getName());
            yVals2.add(new BarEntry(Float.valueOf(barList.get(i).getValue()), barList.size() - i - 1));
        }
        BarDataSet set = new BarDataSet(yVals2, "类别");
        set.setDrawValues(true);
        set.setValueTextSize(8f);
        set.setColor(getResources().getColor(R.color.color_lightblue));
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set);
        BarData barData = new BarData(xVals2, dataSets);
        barChart.setData(barData);
        barChart.invalidate();
    }
}
