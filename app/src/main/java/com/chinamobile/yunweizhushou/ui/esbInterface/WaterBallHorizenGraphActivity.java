package com.chinamobile.yunweizhushou.ui.esbInterface;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
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

public class WaterBallHorizenGraphActivity extends BaseActivity implements OnClickListener {

	private LineChart mLineChart;
	private TextView back, title;
	private YAxis rightAxis;
	private String doubleY;
	private LinearLayout timepickerLayout;
	private String name, userId, fkId, waveId;
	private String time = "6h";
	private String[] times = new String[] { "1h", "2h", "6h", "12h", "1d", "2d", "7d", "30d" };
	private String[] values = new String[] { "1小时", "2小时", "6小时", "12小时", "1天", "2天", "1星期", "1个月" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_graph_detail);

		name = getIntent().getStringExtra("name");
		userId = getIntent().getStringExtra("userId");
		fkId = getIntent().getStringExtra("fkId");
		if (getIntent().hasExtra("waveId")) {
			waveId = getIntent().getStringExtra("waveId");
		} else {
			waveId = "";
		}
		initView();
		initEvent();
		initChart();
		initTimePicker();
		initRequest();
	}

	private void initTimePicker() {

		for (int i = 0; i < times.length; i++) {
			TextView tv = new TextView(this);
			tv.setPadding(3, 10, 3, 10);
			tv.setGravity(Gravity.CENTER);
			tv.setText(values[i]);
			tv.setTag(times[i]);
			if (times[i].equals("6h")) {
				tv.setBackgroundColor(getResources().getColor(R.color.color_gray));
			}
			tv.setOnClickListener(this);
			timepickerLayout.addView(tv);
		}
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("fkId", fkId);
		map.put("time", time);
		map.put("user_id", userId);
		map.put("pament_business", name);
		map.put("waveId", waveId);
		startTask(HttpRequestEnum.enum_govern_analysis_graph_detail, ConstantValueUtil.URL + "BusiFluct?", map, true);
	}

	private void initChart() {
		mLineChart.setDrawGridBackground(false);
		mLineChart.setDescription("");
		mLineChart.setNoDataText("正在加载..");

		MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
		mLineChart.setMarkerView(mv);

		mLineChart.setTouchEnabled(true);
		mLineChart.setDragEnabled(true);
		mLineChart.setScaleEnabled(true);
		mLineChart.setPinchZoom(true);

		Legend l = mLineChart.getLegend();
		l.setForm(LegendForm.LINE);
		l.setTextColor(Color.GRAY);
		l.setWordWrapEnabled(true);
		l.setPosition(LegendPosition.BELOW_CHART_LEFT);

		XAxis xAxis = mLineChart.getXAxis();
		xAxis.setTextColor(Color.GRAY);
		xAxis.setTextSize(8f);
		xAxis.setDrawGridLines(false);
		xAxis.setDrawAxisLine(false);
		xAxis.setPosition(XAxisPosition.BOTTOM);

		YAxis leftAxis = mLineChart.getAxisLeft();
		leftAxis.setTextColor(getResources().getColor(R.color.color_black));
		leftAxis.setDrawGridLines(true);
		leftAxis.setDrawAxisLine(false);
		leftAxis.setTextSize(8f);

		rightAxis = mLineChart.getAxisRight();
		rightAxis.setTextColor(getResources().getColor(R.color.color_black));
		rightAxis.setDrawGridLines(false);
		rightAxis.setDrawAxisLine(false);
		rightAxis.setTextSize(8f);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}

		switch (e) {
		case enum_govern_analysis_graph_detail:

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
							set.setHighlightEnabled(true);
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

	private void initView() {
		mLineChart = (LineChart) findViewById(R.id.common_graph_detail_linechart);
		back = (TextView) findViewById(R.id.common_graph_detail_back);
		title = (TextView) findViewById(R.id.common_graph_detail_title);
		timepickerLayout = (LinearLayout) findViewById(R.id.common_graph_detail_timepicker);
	}

	private void initEvent() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		resetAll();
		for (int i = 0; i < values.length; i++) {
			if (v.getTag().equals(times[i])) {
				time = times[i];
				initRequest();
				v.setBackgroundColor(getResources().getColor(R.color.color_gray));
				break;
			}
		}
	}

	private void resetAll() {
		for (int i = 0; i < timepickerLayout.getChildCount(); i++) {
			View view = timepickerLayout.getChildAt(i);
			view.setBackgroundColor(getResources().getColor(R.color.color_white));
		}
	}

}
