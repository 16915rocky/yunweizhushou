package com.chinamobile.yunweizhushou.ui.capes;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.capes.bean.BusinessAcceptedGraphBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.widget.MyMarkerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RechargeFunctionGraphDetailActivity extends BaseActivity {

	private LineChart mLineChart;
	private BarChart mBarChart;
	private LinearLayout timeTypeLayout;
	private RechargeFunctionGraphBean bean;
	private RechargeFunctionGraphBean.ItemsList item;
	private BusinessAcceptedGraphBean bean2;
	private BusinessAcceptedGraphBean.ItemsList item2;
	private TextView back, title;
	private String[] timeArrays = new String[] { "1小时", "2小时", "6小时", "12小时", "1天", "2天", "1星期", "一个月" };
	private String[] timeValues = new String[] { "1h", "2h", "6h", "12h", "24h", "2d", "7d", "30d" };
	private List<TextView> textViews = new ArrayList<>();

	private Boolean isFloatString = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge_function_graph_detail);
		if (getIntent().getStringExtra("isFloatState") == null) {
			isFloatString = false;
		} else {
			isFloatString = true;
		}
		initView();
		initChart();
	}

	private void initView() {

		mLineChart = (LineChart) findViewById(R.id.rechargeGraphLineChart);
		mBarChart = (BarChart) findViewById(R.id.rechargeGraphBarChart);
		title = (TextView) findViewById(R.id.map_graph_detail_title);
		timeTypeLayout = (LinearLayout) findViewById(R.id.timeTypeLayout);

		back = (TextView) findViewById(R.id.goBack);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		for (int i = 0; i < timeArrays.length; i++) {
			TextView textView = new TextView(RechargeFunctionGraphDetailActivity.this);
			textView.setText(timeArrays[i]);
			textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 80));
			textView.setPadding(3, 3, 3, 3);
			textView.setGravity(Gravity.CENTER);
			textView.setOnClickListener(new TimeTypeOnClickListener(i));
			textView.setTextSize(14f);
			timeTypeLayout.addView(textView);
			textViews.add(textView);
		}
	}

	private class TimeTypeOnClickListener implements OnClickListener {
		private int position;

		public TimeTypeOnClickListener(int position) {
			super();
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			for (int i = 0; i < textViews.size(); i++) {
				TextView textView = textViews.get(i);
				textView.setBackgroundColor(Color.WHITE);
			}
			textViews.get(position).setBackgroundColor(Color.parseColor("#20545454"));
			initRequest(timeValues[position]);
		}
	}

	private void initChartValue(RechargeFunctionGraphBean.ItemsList item) {
		Context mContext = RechargeFunctionGraphDetailActivity.this;

		title.setText(item.getNAME());// title
		for (int i = 0; i < timeValues.length; i++) {
			if (timeValues.equals(item.getTIME())) {
				textViews.get(i).setBackgroundColor(Color.parseColor("#20545454"));
			}
		}

		if ("折线图".equals(item.getCHARTS_TYPE())) {
			mBarChart.setVisibility(View.GONE);
			List<ILineDataSet> dataSets = new ArrayList<>();
			List<String> xVals = new ArrayList<>();
			ArrayList<Entry> yVals = new ArrayList<>();

			Legend l = mLineChart.getLegend();
			l.setForm(LegendForm.LINE); // 设图最下面显示的类型
			l.setTextSize(15);
			l.setFormSize(10f);
			l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

			XAxis x = mLineChart.getXAxis();
			x.setPosition(XAxisPosition.BOTTOM);// 显示x位置 下方
			x.setDrawGridLines(false);// x轴九宫格
			// x.setLabelsToSkip(3);// 坐标之间距离
			x.setTextSize(10f);
			x.resetLabelsToSkip();// 自动计算 距离

			YAxis yr = mLineChart.getAxisRight();
			yr.setEnabled(false);

			if (item.getPOINTS() != null) {
				Float max = Float.valueOf(item.getPOINTS().get(0).get(1));
				Float min = Float.valueOf(item.getPOINTS().get(0).get(1));
				for (int i = 0; i < item.getPOINTS().size(); i++) {
					Float point = Float.valueOf(item.getPOINTS().get(i).get(1));
					min = min > point ? point : min;
					max = max < point ? point : max;
				}

				int pointsSize = item.getPOINTS().size();
				// 初始化X坐标点
				for (int i = 0; i < pointsSize; i++) {
					// xVals.add(Utils.getDateFromMonth2Min(bean.getPOINTS().get(i).get(0)));
					String tString = null;
					if (isFloatString) {
						tString = Utils.getDateFromMonth2Min(item.getPOINTS().get(i).get(0).replace(".00", ""));
					} else {
						tString = Utils.getDateFromMonth2Min(item.getPOINTS().get(i).get(0));
					}
					xVals.add(tString);
				}
				if (isShowHm(xVals)) {
					xVals.clear();
					for (int i = 0; i < pointsSize; i++) {
						String xVal = null;
						if (isFloatString) {
							xVal = Utils.getDateFromString2Day(item.getPOINTS().get(i).get(0).replace(".00", ""));
						} else {
							xVal = Utils.getDateFromString2Day(item.getPOINTS().get(i).get(0));
						}
						xVals.add(xVal);
					}
				}
				// 初始化Y坐标点
				for (int i = 0; i < pointsSize; i++) {
					yVals.add(new Entry(Float.valueOf(item.getPOINTS().get(i).get(1)), i));
				}
				for (int i = 0; i < item.getPOINTS().get(0).size() - 1; i++) {
					ArrayList<Entry> yValues = new ArrayList<>();
					for (int j = 0; j < item.getPOINTS().size(); j++) {
						if (isFloatString) {
							yValues.add(new Entry(Float.valueOf(item.getPOINTS().get(j).get(i + 1)), j));
						} else {
							yValues.add(new Entry(Float.valueOf(item.getPOINTS().get(j).get(i + 1)), j));
						}
					}
					LineDataSet set = new LineDataSet(yValues, item.getCOLUMNS().get(i + 1));
					set.setColor(mContext.getResources().getColor(ConstantValueUtil.colors[i]));
					set.setCircleColor(mContext.getResources().getColor(ConstantValueUtil.colors[i]));
					set.setLineWidth(1f);
					set.setCircleSize(1f);
					set.setFillAlpha(65);
					set.setDrawFilled(false);
					set.setDrawCubic(true);
					set.setFillColor(mContext.getResources().getColor(ConstantValueUtil.colors[i]));
					set.setHighlightEnabled(true);
					set.setDrawCircleHole(false);
					dataSets.add(set);
				}

				LineData datas = new LineData(xVals, dataSets);
				datas.setDrawValues(false);// 画value

				mLineChart.setData(datas);
				mLineChart.animateX(300);
				mLineChart.notifyDataSetChanged();
				mLineChart.invalidate();
			}
		} else if ("柱形图".equals(item.getCHARTS_TYPE())) {
			mLineChart.setVisibility(View.GONE);

			List<String> xVals = new ArrayList<String>(); // x轴坐标
			List<IBarDataSet> dataSets = new ArrayList<>();
			ArrayList<BarEntry> entries = new ArrayList<>();
			BarDataSet barDataSet;

			// X轴设定
			XAxis xAxis = mBarChart.getXAxis();
			xAxis.setPosition(XAxisPosition.BOTTOM);// X轴在下边
			xAxis.setTextSize(6f);
			xAxis.setDrawGridLines(true);
			if (item.getPOINTS() != null) {
				int pointsSize = item.getPOINTS().size();
				// 初始化X坐标点
				for (int i = 0; i < pointsSize; i++) {
					String xVal = null;
					if (isFloatString) {
						xVal = Utils.getDateFromString2Month(item.getPOINTS().get(i).get(0).replace(".00", ""));
					} else {
						xVal = Utils.getDateFromString2Month(item.getPOINTS().get(i).get(0));
					}
					xVals.add(xVal);
				}

				if (isShowHm(xVals)) {
					xVals.clear();
					for (int i = 0; i < pointsSize; i++) {
						String xVal = null;
						if (isFloatString) {
							xVal = Utils.getDateFromString2Day(item.getPOINTS().get(i).get(0).replace(".00", ""));
						} else {
							xVal = Utils.getDateFromString2Day(item.getPOINTS().get(i).get(0));
						}
						xVals.add(xVal);
					}
				}

				List<List<String>> itemsLists = item.getPOINTS();
				Float max = Float.valueOf(itemsLists.get(0).get(1));
				Float min = Float.valueOf(itemsLists.get(0).get(1));
				for (int i = 0; i < itemsLists.size(); i++) {
					Float yPoint = Float.valueOf(isFloatString == true ? itemsLists.get(i).get(1).replace(".00", "")
							: itemsLists.get(i).get(1));
					min = min > yPoint ? yPoint : min;
					max = max < yPoint ? yPoint : max;
				}

				YAxis yAxis = mBarChart.getAxisLeft();
				yAxis.setAxisMaxValue(max + 1f);
				yAxis.setStartAtZero(true);
				yAxis.setTextSize(10f);
				yAxis.setDrawGridLines(true);

				YAxis yRightAxis = mBarChart.getAxisRight();
				yRightAxis.setEnabled(false);

				for (int i = 0; i < itemsLists.size(); i++) {
					Float yVal = Float.valueOf(isFloatString == true ? itemsLists.get(i).get(1).replace(".00", "")
							: itemsLists.get(i).get(1));
					entries.add(new BarEntry(yVal > max ? yVal + 1f : yVal, i));
				}

				barDataSet = new BarDataSet(entries, item.getCOLUMNS().get(1));
				barDataSet.setColor(mContext.getResources().getColor(R.color.color_lightblue));
				barDataSet.setBarShadowColor(Color.parseColor("#20545454"));
				barDataSet.setBarSpacePercent(40f);
				barDataSet.setValueFormatter(new MyValueFormatter());

				dataSets.add(barDataSet);
				BarData barData = new BarData(xVals, dataSets);

				mBarChart.setData(barData);
				mBarChart.animateX(300);
				mBarChart.setVisibility(View.VISIBLE);
			}
		}
	}

	private void initChart() {
		mLineChart.setDrawGridBackground(false);// 是否显示表格颜色
		mLineChart.setDescription("");
		mLineChart.setNoDataText("暂无数据");
		mLineChart.setPinchZoom(true);
		mLineChart.setTouchEnabled(true);// 设置是否可触摸
		mLineChart.setScaleEnabled(true);// 是否缩放
		mLineChart.setDragEnabled(true);// 是否可拖动
		MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
		mLineChart.setMarkerView(mv);

		mBarChart.setDescription("");// 数据描述
		mBarChart.setDrawBarShadow(true);// 会在各条 bar 后面绘制灰色
		mBarChart.setNoDataTextDescription("暂无数据");// 如果没有数据的时候，会显示这个，类似ListView的EmptyView
		mBarChart.setDrawGridBackground(false); // 是否显示表格颜色
		mBarChart.setTouchEnabled(false);// 设置是否可以触摸
		mBarChart.setDragEnabled(false);// 是否可以拖拽
		mBarChart.setScaleEnabled(false);// 是否可以缩放
		mBarChart.setPinchZoom(false);

		// 设置比例图标示
		Legend mLegend = mBarChart.getLegend();
		mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
		mLegend.setForm(LegendForm.SQUARE); // 样式
		mLegend.setFormSize(10f);// 指示器的大小
		mLegend.setTextColor(Color.BLACK);// 颜色

		// 不绘制从Y轴出发的横向直线
		// barChart.getAxisLeft().setDrawGridLines(true);

		item = (RechargeFunctionGraphBean.ItemsList) getIntent().getSerializableExtra("positionList");

		if (item == null || item.getPOINTS() == null || item.getPOINTS().isEmpty()) {
			return;
		}
		initChartValue(item);// 初始化图
	}

	private void initRequest(String time) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("fkId", getIntent().getStringExtra("fkId"));
		map.put("time", time);
		String cityType = getIntent().getStringExtra("cityType");
		String cityValue = getIntent().getStringExtra("cityValue");
		if (cityType == null || cityValue == null) {

			return;
		} else {
			map.put(getIntent().getStringExtra("cityType"), getIntent().getStringExtra("cityValue"));
		}
		String pamentType = getIntent().getStringExtra("pamentType");
		String pamentValue = getIntent().getStringExtra("pamentValue");
		if (pamentType == null || pamentValue == null) {

		} else {
			map.put(getIntent().getStringExtra("pamentType"), getIntent().getStringExtra("pamentValue"));
		}
		map.put("waveId", getIntent().getStringExtra("waveId"));

		startTask(HttpRequestEnum.enum_recharge_function_wave_detail, ConstantValueUtil.URL + "BusiFluct?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(RechargeFunctionGraphDetailActivity.this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_recharge_function_wave_detail:
				Type type = new TypeToken<RechargeFunctionGraphBean>() {
				}.getType();

				bean = new Gson().fromJson(responseBean.getDATA(), type);
				List<RechargeFunctionGraphBean.ItemsList> itemsLists = bean.getItemsList();
				RechargeFunctionGraphBean.ItemsList item = null;
				for (int i = 0; i < itemsLists.size(); i++) {
					item = itemsLists.get(i);
				}
				if (item != null) {
					initChartValue(item);
				}
				break;
			default:
				break;
			}
		}
	}

	class TimeSpinnerAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return timeArrays.length;
		}

		@Override
		public Object getItem(int position) {
			return timeArrays[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(RechargeFunctionGraphDetailActivity.this);
			textView.setText(timeArrays[position]);
			textView.setPadding(3, 3, 3, 3);
			textView.setGravity(Gravity.CENTER);
			return textView;
		}
	}

	private class MyValueFormatter implements ValueFormatter {
		private DecimalFormat mFormat;

		public MyValueFormatter() {
			mFormat = new DecimalFormat("###0");
		}

		@Override
		public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
			// TODO Auto-generated method stub
			return mFormat.format(value);
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
