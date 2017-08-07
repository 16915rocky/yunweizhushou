package com.chinamobile.yunweizhushou.ui.moneyoutCheck;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MoneyoutCheckPieActivity extends BaseActivity {

	private PieChart pieChart;
	private String date, code, type, title;
	private List<Bean> list;
	private MyRefreshLayout refresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_pie_chart1);
		date = getIntent().getStringExtra("date");
		type = getIntent().getStringExtra("type");
		code = getIntent().getStringExtra("code");
		title = getIntent().getStringExtra("title");
		initView();
		initEvent();
		initRequest();
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "pieOfCJ");
		map.put("type", type);
		map.put("date", date);
		map.put("code", code);
		startTask(HttpRequestEnum.enum_moneyout_check_pie, ConstantValueUtil.URL + "CoreLine?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (refresh.isShown()) {
			refresh.setRefreshing(false);
		}

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_moneyout_check_pie:

				Type t = new TypeToken<List<Bean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);

				ArrayList<Entry> yVals = new ArrayList<>();
				ArrayList<String> xVals = new ArrayList<>();
				int[] colors = new int[list.size()];
				for (int i = 0; i < list.size(); i++) {
					xVals.add(list.get(i).getName());
					yVals.add(new Entry(Float.valueOf(list.get(i).getValue()), i));
					colors[i] = ConstantValueUtil.colors2[i];
				}
				pieChart.setUsePercentValues(false);
				pieChart.setNoDataText("暂无数据,请尝试刷新");
				Legend legend = pieChart.getLegend();
				legend.setWordWrapEnabled(true);
				legend.setPosition(LegendPosition.RIGHT_OF_CHART);
				PieDataSet dataSet = new PieDataSet(yVals, "");
				dataSet.setColors(colors);
				pieChart.setDescription("");
				PieData data = new PieData(xVals, dataSet);
				data.setValueTextColor(Color.WHITE);
				pieChart.setData(data);
				pieChart.invalidate();

				break;
			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}

	private void initEvent() {
		refresh.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});

		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		getTitleBar().setMiddleText(title);
	}

	private void initView() {
		refresh = (MyRefreshLayout) findViewById(R.id.common_pie_chart_refresh);
		pieChart = (PieChart) findViewById(R.id.common_pie_chart);
	}

	class Bean {
		private String name;
		private String value;

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}
}
