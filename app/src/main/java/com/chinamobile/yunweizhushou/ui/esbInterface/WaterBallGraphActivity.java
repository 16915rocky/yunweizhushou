package com.chinamobile.yunweizhushou.ui.esbInterface;

import android.content.Intent;
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
import com.chinamobile.yunweizhushou.widget.ShieldChildrenClickLinearLayout;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
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

public class WaterBallGraphActivity extends BaseActivity {

	private String name, userId, fkId;
	private LineChart mLineChart;
	private TextView title;
	private String doubleY;
	private YAxis rightAxis;
	private ShieldChildrenClickLinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.common_line_chart);
		name = getIntent().getStringExtra("name");
		userId = getIntent().getStringExtra("userId");
		fkId = getIntent().getStringExtra("fkId");
		initView();
		initChart();
		initEvent();
		initRequest();

	}

	private void initChart() {
		mLineChart.setDrawGridBackground(false);
		mLineChart.setDescription("");
		mLineChart.setTouchEnabled(false);
		mLineChart.setDragEnabled(false);
		mLineChart.setScaleEnabled(false);
		mLineChart.setNoDataText("正在加载..");
		Legend l = mLineChart.getLegend();
		l.setForm(LegendForm.LINE);
		l.setWordWrapEnabled(true);
		l.setTextColor(Color.GRAY);
		l.setPosition(LegendPosition.BELOW_CHART_LEFT);
		XAxis xAxis = mLineChart.getXAxis();
		xAxis.setTextColor(Color.GRAY);
		xAxis.setTextSize(4f);
		xAxis.setDrawGridLines(false);
		xAxis.setDrawAxisLine(false);
		xAxis.setPosition(XAxisPosition.BOTTOM);
		// xAxis.setDrawLimitLinesBehindData(true);
		xAxis.setLabelRotationAngle(0);

		YAxis leftAxis = mLineChart.getAxisLeft();
		leftAxis.setTextColor(getResources().getColor(R.color.color_black));
		leftAxis.setDrawGridLines(true);
		leftAxis.setDrawAxisLine(false);
		leftAxis.setTextSize(4f);
		leftAxis.setDrawLimitLinesBehindData(true);

		rightAxis = mLineChart.getAxisRight();
		rightAxis.setTextColor(getResources().getColor(R.color.color_black));
		rightAxis.setDrawGridLines(false);
		rightAxis.setDrawAxisLine(false);
		rightAxis.setTextSize(4f);
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("fkId", fkId);
		map.put("time", "");
		map.put("user_id", userId);
		map.put("pament_business", name);
		startTask(HttpRequestEnum.enum_govern_analysis_graph, ConstantValueUtil.URL + "BusiFluct?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_govern_analysis_graph:
			if (responseBean.isSuccess()) {
				String data = Utils.getJsonArray(responseBean.getDATA());
				try {
					JSONArray jsonArray = new JSONArray(data);
					JSONObject jsonObject = jsonArray.getJSONObject(0);
					List<String> type = new ArrayList<>();

					if (jsonObject.has("DOUBLEY")) {
						doubleY = jsonObject.getString("DOUBLEY");
					}

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
							xList.add(Utils.getDateFromMonth2Min(arrays.getJSONArray(i).getString(0)));
						}

						for (int i = 1; i < type.size(); i++) {
							List<String> child = new ArrayList<>();
							for (int j = 0; j < arrays.length(); j++) {
								child.add(arrays.getJSONArray(j).getString(i));
							}
							yLists.add(child);
						}

						for (int i = 0; i < yLists.size(); i++) {
							ArrayList<Entry> yValues = new ArrayList<>();
							for (int j = 0; j < yLists.get(i).size(); j++) {
								yValues.add(new Entry(Float.valueOf(yLists.get(i).get(j)), j));
							}
							LineDataSet set = new LineDataSet(yValues, type.get(i + 1));
							if (i == 0) {
								if (doubleY.equals("1")) {
									rightAxis.setEnabled(true);
									set.setAxisDependency(AxisDependency.RIGHT);
								} else {
									rightAxis.setEnabled(false);
									set.setAxisDependency(AxisDependency.LEFT);
								}
							} else {
								set.setAxisDependency(AxisDependency.LEFT);
							}
							set.setColor(getResources().getColor(ConstantValueUtil.colors[i]));
							set.setCircleColor(getResources().getColor(ConstantValueUtil.colors[i]));
							set.setLineWidth(1f);
							set.setCircleSize(1f);
							set.setFillAlpha(65);
							set.setDrawFilled(false);
							set.setDrawCubic(true);
							set.setFillColor(getResources().getColor(ConstantValueUtil.colors[i]));
							set.setHighlightEnabled(false);
							set.setDrawCircleHole(false);
							dataSets.add(set);
						}
						LineData datas = new LineData(xList, dataSets);
						datas.setDrawValues(false);
						mLineChart.animateXY(100, 100);
						mLineChart.setData(datas);
					}
					if (jsonObject.has("NAME")) {
						String str = jsonObject.getString("NAME");
						getTitleBar().setMiddleText(str);
						title.setText(str);
					}

				} catch (JSONException e1) {
					e1.printStackTrace();
				}

			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private void initEvent() {
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(WaterBallGraphActivity.this, WaterBallHorizenGraphActivity.class);
				intent.putExtra("name", name);
				intent.putExtra("fkId", fkId);
				intent.putExtra("userId", userId);
				startActivity(intent);
			}
		});
	}

	private void initView() {
		title = (TextView) findViewById(R.id.common_linechart_title);
		mLineChart = (LineChart) findViewById(R.id.common_linechart);
		layout = (ShieldChildrenClickLinearLayout) findViewById(R.id.common_linechart_layout);
	}

}
