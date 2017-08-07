package com.chinamobile.yunweizhushou.ui.userPerceptionIndex.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.SelectMouthDialog;
import com.chinamobile.yunweizhushou.ui.userPerceptionIndex.adapter.CommonListAdapter;
import com.chinamobile.yunweizhushou.ui.userPerceptionIndex.bean.TmValueBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
import butterknife.Unbinder;

import static com.chinamobile.yunweizhushou.R.id.tv_time;

/**
 * Created by Administrator on 2017/7/21.
 */

public class UserPerceptionCommonTabFragment extends BaseFragment {


    @BindView(R.id.bt_name)
    TextView btName;
    @BindView(R.id.bt_common)
    BarChart btCommon;
    @BindView(R.id.lv_upin)
    ListView lvUpin;
    Unbinder unbinder;
    @BindView(tv_time)
    TextView tvTime;
    private  String currentDate;
    private int year;
    private int month;
    private List<TmValueBean> mList;
    private CommonListAdapter  mAadpter;
    private String methodBarName,methodListName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        methodBarName=arguments.getString("methodBarName");
        methodListName=arguments.getString("methodListName");
        View view = inflater.inflate(R.layout.fragment_user_perpcetion_common, container, false);
        unbinder = ButterKnife.bind(this, view);
        Calendar c = Calendar.getInstance();
        year=c.get(Calendar.YEAR);
        month=c.get(Calendar.MONTH)+1;
        currentDate = Utils.getCurrentTime();
        tvTime.setText(year+"年"+month+"月");
        initRequest();
        initListDataRequest(year+"-"+month);
        initEvent();
        return view;
    }

    private void initEvent() {
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectMouthDialog dialog = new SelectMouthDialog(getActivity());
                dialog.show();
                dialog.setBirthdayListener(new SelectMouthDialog.OnBirthListener() {

                    @Override
                    public void onClick(String year, String month, String day) {
                        String currentDate1 = year + "年" + month + "月";
                        currentDate = year + "-" + month;
                        tvTime.setText(currentDate1);
                        initListDataRequest(currentDate);
                    }
                });
            }
        });
    }


    private void initRequest() {
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("action", methodBarName);
        maps.put("class_id", "PRFSN-4");
        startTask(HttpRequestEnum.enum_user_perception_common_barchart, ConstantValueUtil.URL + "KEIServlet?", maps, false);
    }

    private void initListDataRequest(String time) {
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("action", methodListName);
        maps.put("class_id", "PRFSN-4");
        maps.put("time", time);
        startTask(HttpRequestEnum.enum_user_peception_common_list, ConstantValueUtil.URL + "KEIServlet?", maps, false);
    }

    @Override
    protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
        super.onTaskFinish(e, responseBean);
        if (responseBean == null) {
            Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
            return;
        }
        switch (e) {
            case enum_user_perception_common_barchart:
                try {
                    JSONObject jo = new JSONObject(responseBean.getDATA());
                    JSONArray ja1 = jo.getJSONArray("columns");
                    JSONArray ja2 = jo.getJSONArray("points");
                    String title = jo.getString("title");
                    if(!TextUtils.isEmpty(title) && btName!=null){
                        btName.setText(title);
                    }
                    if(btCommon!=null){
                        initbarChart(btCommon);
                    }
                    if(ja1!=null &&ja2!=null && btCommon!=null){
                        initonebarValue(btCommon,ja1, ja2);
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                break;
            case enum_user_peception_common_list:
                Type t =new TypeToken<List<TmValueBean>>(){}.getType();
                mList=new Gson().fromJson(responseBean.getDATA(),t);
                mAadpter=new CommonListAdapter(getActivity(),mList,R.layout.item_list_2);
                if(mAadpter!=null && lvUpin!=null) {
                  lvUpin.setAdapter(mAadpter);
                }
            default:
                break;
        }
    }

    private void initbarChart(BarChart barChart) {
        barChart.setDescription("");
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(false);
        //	tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        Legend l = barChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
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


    private void initonebarValue(BarChart barChart ,JSONArray columns, JSONArray points) throws JSONException {

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

        BarDataSet set2 = new BarDataSet(yVals1, "");
        set2.setColor(this.getResources().getColor(R.color.color_orange));
        ArrayList dataSets = new ArrayList();
        dataSets.add(set2);
        BarData data = new BarData(xVals, dataSets);
        data.setGroupSpace(80f);
//    data.setValueTypeface(tf);

        barChart.setData(data);
        barChart.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
