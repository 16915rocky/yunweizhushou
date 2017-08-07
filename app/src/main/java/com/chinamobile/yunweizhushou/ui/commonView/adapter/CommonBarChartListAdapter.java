package com.chinamobile.yunweizhushou.ui.commonView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphNewBean;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class CommonBarChartListAdapter  extends BaseAdapter {
    private List<RechargeFunctionGraphNewBean.ItemsList> mList;
    private Context mContext;
    public CommonBarChartListAdapter(Context context,List<RechargeFunctionGraphNewBean.ItemsList> list){
        this.mContext=context;
        this.mList=list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
         ViewHolder viewHolder;
        if(view==null){
            viewHolder=new ViewHolder();
            view=LayoutInflater.from(mContext).inflate(R.layout.item_barchart,null);
            viewHolder.bt_name= (TextView) view.findViewById(R.id.bt_name);
            viewHolder.barChart= (BarChart) view.findViewById(R.id.bt_common);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.bt_name.setText(mList.get(i).getTitle());
        initbarChart(viewHolder.barChart);
        initonebarValue(viewHolder.barChart,mList.get(i).getColumns(),mList.get(i).getPoints());
        return  view;
    }
    class ViewHolder{
        private BarChart barChart;
        private TextView bt_name;
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


    private void initonebarValue(BarChart barChart,List<String> columns, List<List<String>> points)  {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < points.size(); i++) {
            String str = points.get(i).get(0);
            xVals.add(str);
        }
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < points.size(); i++) {
            Float value1 = (float) 0;
            if (!"".equals(points.get(i).get(1))) {
                value1 = Float.parseFloat(points.get(i).get(1));
            }

            yVals1.add(new BarEntry(value1, i));
        }

        BarDataSet set2 = new BarDataSet(yVals1, "");
        set2.setColor(mContext.getResources().getColor(R.color.color_orange));
        ArrayList dataSets = new ArrayList();
        dataSets.add(set2);
        BarData data = new BarData(xVals, dataSets);
        data.setGroupSpace(80f);
//    data.setValueTypeface(tf);

        barChart.setData(data);
        barChart.invalidate();
    }
}
