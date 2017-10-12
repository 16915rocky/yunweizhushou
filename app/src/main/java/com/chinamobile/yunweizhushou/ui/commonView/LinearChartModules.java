package com.chinamobile.yunweizhushou.ui.commonView;

import android.content.Context;

import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */

public class LinearChartModules {
    private boolean isFloatState = true;


    public void setLineChart(Context context,LineChart lineChart, RechargeFunctionGraphBean.ItemsList bean) {
        List<ILineDataSet> dataSets = new ArrayList<>();
        List<String> xVals = new ArrayList<>();
        ArrayList<Entry> yVals = new ArrayList<>();
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription("");
        lineChart.setTouchEnabled(false);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setNoDataText("暂无数据");

        XAxis x = lineChart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);// 显示x位置 下方
        x.setDrawGridLines(false);// x轴九宫格
        x.setTextSize(6f);
        x.resetLabelsToSkip();

        if (bean.getPOINTS() != null) {

            Float max = Float.valueOf(bean.getPOINTS().get(0).get(1));
            Float min = Float.valueOf(bean.getPOINTS().get(0).get(1));
            for (int i = 0; i < bean.getPOINTS().size(); i++) {
                Float point = Float.valueOf(bean.getPOINTS().get(i).get(1));
                min = min > point ? point : min;
                max = max < point ? point : max;
            }

            YAxis yl = lineChart.getAxisLeft();
            yl.setDrawGridLines(true);
            yl.setTextSize(10f);

            YAxis yr = lineChart.getAxisRight();
            yr.setEnabled(false);

            Legend l = lineChart.getLegend();
            l.setForm(Legend.LegendForm.LINE); // 设图最下面显示的类型
            l.setFormSize(10f);
            l.setWordWrapEnabled(true);
            l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

            int pointsSize = bean.getPOINTS().size();
            // 初始化X坐标点
            for (int i = 0; i < pointsSize; i++) {
                // xVals.add(Utils.getDateFromMonth2Min(bean.getPOINTS().get(i).get(0)));
                String tString = Utils.getDateFromString2Month(isFloatState == true
                        ? bean.getPOINTS().get(i).get(0).replace(".00", "") : bean.getPOINTS().get(i).get(0));
                xVals.add(tString);
            }
            if (isShowHm(xVals)) {
                xVals.clear();
                for (int i = 0; i < pointsSize; i++) {
                    String xVal = Utils.getDateFromString2Day(isFloatState == true
                            ? bean.getPOINTS().get(i).get(0).replace(".00", "") : bean.getPOINTS().get(i).get(0));
                    xVals.add(xVal);
                }
            }

            // 初始化Y坐标点
            for (int i = 0; i < pointsSize; i++) {
                yVals.add(new Entry(Float.valueOf(bean.getPOINTS().get(i).get(1)), i));
            }

            for (int i = 0; i < bean.getPOINTS().get(0).size() - 1; i++) {
                ArrayList<Entry> yValues = new ArrayList<>();
                for (int j = 0; j < bean.getPOINTS().size(); j++) {
                    yValues.add(new Entry(Float.valueOf(bean.getPOINTS().get(j).get(i + 1)), j));
                }
                LineDataSet set = new LineDataSet(yValues, bean.getCOLUMNS().get(i + 1));
                set.setColor(context.getResources().getColor(ConstantValueUtil.colors[i]));
                set.setCircleColor(context.getResources().getColor(ConstantValueUtil.colors[i]));
                set.setLineWidth(1f);
                set.setCircleSize(1f);
                set.setFillAlpha(65);
                set.setDrawFilled(false);
                set.setDrawCubic(false);
                set.setFillColor(context.getResources().getColor(ConstantValueUtil.colors[i]));
                set.setHighlightEnabled(false);
                set.setDrawCircleHole(false);
                dataSets.add(set);
            }

            LineData datas = new LineData(xVals, dataSets);
            datas.setDrawValues(false);// 画value

            lineChart.setData(datas);
            lineChart.animateX(500);
        }

    }

    // 对x轴的数据进行判断 是否显示HH:mm
    private boolean isShowHm(List<String> xVals) {
        for (int i = 0; i < xVals.size(); i++) {
            if (!xVals.get(i).endsWith("00:00")) {
                return false;
            }
        }
        return true;
    }
}