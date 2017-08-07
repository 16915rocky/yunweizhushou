package com.chinamobile.yunweizhushou.ui.networkAcceptance;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.widget.MyMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendDirection;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetworkAcceptanceGraphActivity extends BaseActivity {

	private LineChart mLineChart, chart2;
	private TextView title, title2;
	private String action;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_network_acceptance_graph);
		action = getIntent().getStringExtra("action");
		initView();
		initData();
		initChart(mLineChart);
		initChart(chart2);
		initRequest();
	}

	private void initData() {
		if (!action.equals("getRecentForCus")) {
			chart2.setVisibility(View.GONE);
			title2.setVisibility(View.GONE);
		}
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", action);
		startTask(HttpRequestEnum.enum_network_total_graph, ConstantValueUtil.URL + "ChangeTask?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_network_total_graph:
			String data = responseBean.getDATA();

			if (action.equals("getRecentForCus")) {
				String string = Utils.getJsonArray(data);
				try {
					JSONArray array = new JSONArray(string);
					invert2Chart(array.get(0).toString(), mLineChart, title);
					invert2Chart(array.get(1).toString(), chart2, title2);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

			} else {
				invert2Chart(data, mLineChart, title);
			}
			break;
		default:
			break;
		}
	}

	private void invert2Chart(String data, LineChart mLineChart, TextView title) {
		try {
			JSONObject jsonObject = new JSONObject(data);
			List<String> type = new ArrayList<>();
			if (jsonObject.has("COLUMNS")) {
				JSONArray array = jsonObject.getJSONArray("COLUMNS");
				for (int i = 0; i < array.length(); i++) {
					type.add(array.getString(i));
				}
			}
			List<List<String>> yLists = new ArrayList<>();
			List<String> xList = new ArrayList<>();

			ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

			if (jsonObject.has("POINTS")) {

				JSONArray arrays = jsonObject.getJSONArray("POINTS");
				for (int i = 0; i < arrays.length(); i++) {
					xList.add(arrays.getJSONArray(i).getString(0));
				}

				float max = 0;
				for (int i = 1; i < type.size(); i++) {
					List<String> child = new ArrayList<>();
					for (int j = 0; j < arrays.length(); j++) {
						Float y = Float.valueOf(arrays.getJSONArray(j).getString(i).replaceAll("%", ""));
						max = y > max ? y : max;
						child.add(arrays.getJSONArray(j).getString(i));
					}
					yLists.add(child);
				}
				mLineChart.getAxisLeft().setAxisMaxValue(max);

				for (int i = 0; i < yLists.size(); i++) {
					ArrayList<Entry> yValues = new ArrayList<>();
					for (int j = 0; j < yLists.get(i).size(); j++) {
						yValues.add(new Entry(Float.valueOf(yLists.get(i).get(j).replaceAll("%", "")), j));
					}
					LineDataSet set = new LineDataSet(yValues, type.get(i + 1));
					set.setAxisDependency(AxisDependency.LEFT);
					set.setColor(getResources().getColor(ConstantValueUtil.colors[i]));
					set.setCircleColor(getResources().getColor(ConstantValueUtil.colors[i]));
					set.setLineWidth(1f);
					set.setCircleSize(1f);
					set.setDrawFilled(true);
					set.setDrawCubic(false);
					set.setFillColor(getResources().getColor(ConstantValueUtil.colors[i]));
					set.setHighlightEnabled(true);
					set.setDrawCircleHole(false);
					dataSets.add(set);
				}
				LineData datas = new LineData(xList, dataSets);
				datas.setDrawValues(false);
				mLineChart.animateXY(100, 100);
				mLineChart.setData(datas);
			}
			if (jsonObject.has("TITLE")) {
				String t = jsonObject.getString("TITLE");
				title.setText(t);
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}

	private void initView() {
		getTitleBar().setMiddleText("");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mLineChart = (LineChart) findViewById(R.id.activity_network_linechart);
		chart2 = (LineChart) findViewById(R.id.activity_network_linechart2);
		title = (TextView) findViewById(R.id.chart_title);
		title2 = (TextView) findViewById(R.id.chart_title2);
	}

	private void initChart(LineChart mLineChart) {
		mLineChart.setDrawGridBackground(false);
		mLineChart.setDescription("");
		mLineChart.setTouchEnabled(true);
		mLineChart.setDragEnabled(true);
		mLineChart.setScaleEnabled(true);
		mLineChart.setPinchZoom(true);
		mLineChart.setNoDataText("暂无数据");
		MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
		mLineChart.setMarkerView(mv);
		Legend l = mLineChart.getLegend();
		l.setForm(LegendForm.SQUARE);
		l.setTextColor(Color.GRAY);
		l.setWordWrapEnabled(true);
		l.setTextSize(12f);
		l.setDirection(LegendDirection.LEFT_TO_RIGHT);
		l.setPosition(LegendPosition.BELOW_CHART_LEFT);
		XAxis xAxis = mLineChart.getXAxis();
		xAxis.setTextColor(Color.GRAY);
		xAxis.setTextSize(12f);
		xAxis.setDrawGridLines(false);
		xAxis.setDrawAxisLine(false);
		xAxis.setPosition(XAxisPosition.BOTTOM);
		xAxis.resetLabelsToSkip();
		YAxis leftAxis = mLineChart.getAxisLeft();
		leftAxis.setTextColor(getResources().getColor(R.color.color_black));
		leftAxis.setDrawGridLines(true);
		leftAxis.setDrawAxisLine(false);
		leftAxis.setTextSize(12f);
		YAxis rightAxis = mLineChart.getAxisRight();
		rightAxis.setEnabled(false);
	}
}
