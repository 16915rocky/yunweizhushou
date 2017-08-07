package com.chinamobile.yunweizhushou.ui.functionAnalysis;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.functionAnalysis.bean.FunctionAnalysisDetailBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FunctionAnalysisDetailActivity extends BaseActivity {

	private TextView tag, content, stateTop, title, time, state, reason, method;
	private LineChart mLineChart;
	private String key;

	private FunctionAnalysisDetailBean bean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_function_analysis_detail);
		key = getIntent().getStringExtra("key");
		initView();
		initChart();
		initEvent();
		initRequest();
	}

	private void initChart() {
		mLineChart.setDrawGridBackground(false);
		Legend l = mLineChart.getLegend();
		l.setForm(LegendForm.LINE);
		l.setWordWrapEnabled(true);
		l.setTextColor(Color.GRAY);
		l.setPosition(LegendPosition.BELOW_CHART_LEFT);
		XAxis xAxis = mLineChart.getXAxis();
		xAxis.setTextColor(Color.GRAY);
		xAxis.setTextSize(6f);
		xAxis.setDrawGridLines(false);
		xAxis.setDrawAxisLine(false);
		xAxis.setPosition(XAxisPosition.BOTTOM);
		xAxis.setDrawLimitLinesBehindData(true);
		xAxis.setLabelRotationAngle(0);

		YAxis leftAxis = mLineChart.getAxisLeft();
		leftAxis.setTextColor(getResources().getColor(R.color.color_black));
		leftAxis.setDrawGridLines(true);
		leftAxis.setDrawAxisLine(false);
		leftAxis.setTextSize(6f);
		leftAxis.setDrawLimitLinesBehindData(true);

		YAxis rightAxis = mLineChart.getAxisRight();
		rightAxis.setEnabled(false);
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "propertyDesc");
		map.put("key", key);
		startTask(HttpRequestEnum.enum_function_analysis_detail, ConstantValueUtil.URL + "SpecialTreatment?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_function_analysis_detail:
			mLineChart.setNoDataText("正在加载..");
			mLineChart.setNoDataTextDescription("");
			mLineChart.setDescription("");
			// mLineChart.invalidate();
			bean = new Gson().fromJson(responseBean.getDATA(), FunctionAnalysisDetailBean.class);
			getTitleBar().setMiddleText(bean.getServiceName());

			tag.setText(bean.getDelLevel());
			content.setText(bean.getServiceName());
			stateTop.setText(bean.getAnanlysisResult());
			state.setText(bean.getAnanlysisResult());
			reason.setText(bean.getAnanlysisReason());
			time.setText(bean.getAnalysisTime());
			method.setText("");
			if (bean.getDelLevel().equals("轻微")) {
				tag.setBackgroundResource(R.drawable.oval_yellow);
			} else if (bean.getDelLevel().equals("一般")) {
				tag.setBackgroundResource(R.drawable.oval_orange);
			} else if (bean.getDelLevel().equals("严重")) {
				tag.setBackgroundResource(R.drawable.oval_red);
			}
			if (bean.getAnanlysisResult().equals("分析中")) {
				stateTop.setBackgroundResource(R.drawable.rect_gray_bg);
			} else if (bean.getAnanlysisResult().equals("实施中")) {
				stateTop.setBackgroundResource(R.drawable.rect_blue_bg);
			} else if (bean.getAnanlysisResult().equals("已完成")) {
				stateTop.setBackgroundResource(R.drawable.rect_green_bg);
			}

			try {
				JSONObject obj = new JSONObject(responseBean.getDATA());
				JSONObject jsonObject = obj.getJSONObject("itemsList");
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
						String date = timeStamp2Date(arrays.getJSONArray(i).getString(0), "MM-dd HH:mm");
						// System.out.println("date="+date);//运行输出:date=2016-08-04
						// 10:34:42
						xList.add(date);
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
						set.setAxisDependency(AxisDependency.LEFT);
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

			} catch (JSONException e1) {
				e1.printStackTrace();
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
	}

	private void initView() {
		tag = (TextView) findViewById(R.id.function_analysis_list_tag);
		content = (TextView) findViewById(R.id.function_analysis_list_content);
		stateTop = (TextView) findViewById(R.id.function_analysis_list_state);

		title = (TextView) findViewById(R.id.function_analysis_detail_title);
		time = (TextView) findViewById(R.id.function_analysis_detail_time);
		state = (TextView) findViewById(R.id.function_analysis_detail_state);
		reason = (TextView) findViewById(R.id.function_analysis_detail_reason);
		method = (TextView) findViewById(R.id.function_analysis_detail_method);

		mLineChart = (LineChart) findViewById(R.id.function_analysis_detail_chart);
	}

	/**
	 * 时间戳转换成日期格式字符串
	 * 
	 * @param seconds
	 *            精确到秒的字符串
	 * @param formatStr
	 * @return
	 */
	public String timeStamp2Date(String seconds, String format) {
		if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
			return "";
		}
		if (format == null || format.isEmpty()) {
			format = "yyyy-MM HH:mm";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds)));
	}
}
