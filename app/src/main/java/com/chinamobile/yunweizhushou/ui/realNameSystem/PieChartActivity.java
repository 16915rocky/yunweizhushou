package com.chinamobile.yunweizhushou.ui.realNameSystem;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.realNameSystem.bean.RealNameSystemBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends BaseActivity {

	
	private PieChart pieChart;
	private String num1,num2,num3;
	private List<RealNameSystemBean> pieList;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		num1=getIntent().getStringExtra("num1");
		num2=getIntent().getStringExtra("num2");
		num3=getIntent().getStringExtra("num3");		
	    pieList = new ArrayList<RealNameSystemBean>();
	    RealNameSystemBean  rb1=new RealNameSystemBean("合法数量",num1);
	    RealNameSystemBean  rb2=new RealNameSystemBean("不合法数量",num2);
	    RealNameSystemBean  rb3=new RealNameSystemBean("不确定数量",num3);
	    pieList.add(rb1);
	    pieList.add(rb2);
	    pieList.add(rb3);
		setContentView(R.layout.realnamesystem_piechart);
		initView();
		inttEvent();
		initPieChart();
		ArrayList<Entry> yVals = new ArrayList<>();
		ArrayList<String> xVals = new ArrayList<>();
		int[] colors = new int[3];
		for (int i = 0; i < 3; i++) {
			xVals.add(pieList.get(i).getKey());
			yVals.add(new Entry(Float.valueOf(pieList.get(i).getValue()), i));
			colors[i] = ConstantValueUtil.colors2[i];
		}
		PieDataSet dataSet = new PieDataSet(yVals, "");
		dataSet.setColors(colors);
		PieData data = new PieData(xVals, dataSet);
		data.setValueTextColor(Color.WHITE);
		pieChart.setData(data);
		pieChart.invalidate();
	}

	private void inttEvent() {
		getTitleBar().setMiddleText("实名制饼图");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	private void initView() {
		pieChart=(PieChart) findViewById(R.id.realname_asset_pie_chart);
		
	}
	
	private void initPieChart() {
		pieChart.setNoDataText("暂无数据,请尝试刷新");
		Legend legend = pieChart.getLegend();
		legend.setWordWrapEnabled(true);
		legend.setPosition(LegendPosition.RIGHT_OF_CHART);
		pieChart.setDescription("");
	}

}
