package com.chinamobile.yunweizhushou.ui.yunweiAsset;

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
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YunweiAssetDetail2Activity extends BaseActivity {

	private TextView title1, title2, title3;
	private PieChart pieChart;
	private HorizontalBarChart barChart;
	private CombinedChart combinedChart;
	private String type;
	private List<Bean> pieList, barList;

	private List<Float> list1 = new ArrayList<>();
	private List<Float> list2 = new ArrayList<>();
	private List<Float> list3 = new ArrayList<>();
	private List<Float> list4 = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_yunwei_asset_detail2);
		type = getIntent().getStringExtra("type");
		initView();
		initPieChart();
		initBarChart();
		initCombinedChart();
		initEvent();
		initRequest();
		if (type.equals("1") || type.equals("2")) {
			initCombinedRequest();
		} else {
			combinedChart.setVisibility(View.GONE);
			title3.setVisibility(View.GONE);
		}
	}

	private void initCombinedRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getHistogram");
		map.put("type", type);
		startTask(HttpRequestEnum.enum_yunwei_asset_detail_combined, ConstantValueUtil.URL + "Accounting?", map);
	}

	private void initCombinedChart() {
		combinedChart.setDescription("");
		combinedChart.setDrawBarShadow(false);
		combinedChart.setDrawGridBackground(false);
		combinedChart.setDrawOrder(new DrawOrder[] { DrawOrder.BAR, DrawOrder.LINE });
		combinedChart.setPinchZoom(false);
		combinedChart.setScaleEnabled(false);
		MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
		combinedChart.setMarkerView(mv);

		YAxis rightAxis = combinedChart.getAxisRight();
		rightAxis.setEnabled(false);

		YAxis leftAxis = combinedChart.getAxisLeft();
		leftAxis.setDrawGridLines(false);

		XAxis xAxis = combinedChart.getXAxis();
		xAxis.setDrawGridLines(false);
		xAxis.setPosition(XAxisPosition.BOTTOM);

	}

	private void initBarChart() {
		barChart.setDescription("");// 数据描述
		barChart.setDrawBarShadow(false);// 会在各条 bar 后面绘制灰色
		barChart.setNoDataTextDescription("暂无数据");// 如果没有数据的时候，会显示这个，类似ListView的EmptyView
		barChart.setDrawGridBackground(false); // 是否显示表格颜色
		barChart.setTouchEnabled(false);// 设置是否可以触摸
		barChart.setDragEnabled(false);// 是否可以拖拽
		barChart.setScaleEnabled(false);// 是否可以缩放
		barChart.setPinchZoom(false);
		// 不绘制从Y轴出发的横向直线
		YAxis left = barChart.getAxisLeft();
		// left.setDrawGridLines(false);
		// left.setDrawAxisLine(false);

		// 设置比例图标示
		Legend mLegend = barChart.getLegend();
		mLegend.setPosition(LegendPosition.BELOW_CHART_LEFT);
		mLegend.setForm(LegendForm.SQUARE); // 样式
		mLegend.setFormSize(10f);// 指示器的大小
		mLegend.setTextColor(Color.BLACK);// 颜色
		mLegend.setWordWrapEnabled(true);

		// X轴设定
		XAxis xAxis = barChart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);// X轴在下边
		xAxis.setTextSize(6f);
		// xAxis.setDrawAxisLine(false);
		// xAxis.setDrawGridLines(false);

	}

	private void initPieChart() {
		pieChart.setNoDataText("暂无数据,请尝试刷新");
		Legend legend = pieChart.getLegend();
		legend.setWordWrapEnabled(true);
		legend.setPosition(LegendPosition.RIGHT_OF_CHART);
		pieChart.setDescription("");
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getTheSpecificData");
		map.put("type", type);
		startTask(HttpRequestEnum.enum_yunwei_asset_detail2, ConstantValueUtil.URL + "Accounting?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_yunwei_asset_detail2:
				Type t = new TypeToken<List<Bean>>() {
				}.getType();
				pieList = new Gson().fromJson(Utils.getJsonArrayX(responseBean.getDATA(), "map1"), t);
				ArrayList<Entry> yVals = new ArrayList<>();
				ArrayList<String> xVals = new ArrayList<>();
				int[] colors = new int[pieList.size()];
				for (int i = 0; i < pieList.size(); i++) {
					xVals.add(pieList.get(i).getName());
					yVals.add(new Entry(Float.valueOf(pieList.get(i).getValue()), i));
					colors[i] = ConstantValueUtil.colors2[i];
				}

				PieDataSet dataSet = new PieDataSet(yVals, "");
				dataSet.setColors(colors);
				PieData data = new PieData(xVals, dataSet);
				data.setValueTextColor(Color.WHITE);
				pieChart.setData(data);
				pieChart.invalidate();

				barList = new Gson().fromJson(Utils.getJsonArrayX(responseBean.getDATA(), "map2"), t);
				ArrayList<BarEntry> yVals2 = new ArrayList<>();
				ArrayList<String> xVals2 = new ArrayList<>();
				for (int i = 0; i < barList.size(); i++) {
					xVals2.add(barList.get(barList.size() - i - 1).getName());
					yVals2.add(new BarEntry(Float.valueOf(barList.get(i).getValue()), barList.size() - i - 1));
				}
				BarDataSet set = new BarDataSet(yVals2, "类别");
				set.setDrawValues(true);
				set.setValueTextSize(8f);
				set.setColor(getResources().getColor(R.color.color_lightblue));
				ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
				dataSets.add(set);
				BarData barData = new BarData(xVals2, dataSets);
				barChart.setData(barData);
				barChart.invalidate();
				break;

			case enum_yunwei_asset_detail_combined:

				try {
					String d = responseBean.getDATA();

					// bar
					ArrayList<String> xBarVal = new ArrayList<>();
					ArrayList<BarEntry> yBarVal = new ArrayList<BarEntry>();
					JSONObject jo = new JSONObject(d);
					JSONArray columns = jo.getJSONArray("COLUMNS");
					for (int i = 1; i < columns.length() - 1; i++) {
						xBarVal.add(columns.getString(i));
					}
					String lineTag = columns.getString(columns.length() - 1);
					JSONArray points = jo.getJSONArray("POINTS");
					for (int i = 0; i < xBarVal.size(); i++) {
						for (int j = 0; j < points.length(); j++) {
							if (i == 0) {
								list1.add(Float.valueOf(points.getJSONArray(j).getString(i + 1)));
							} else if (i == 1) {
								list2.add(Float.valueOf(points.getJSONArray(j).getString(i + 1)));
							} else if (i == 2) {
								list3.add(Float.valueOf(points.getJSONArray(j).getString(i + 1)));
							} else if (i == 3) {
								list4.add(Float.valueOf(points.getJSONArray(j).getString(i + 1)));
							}
						}
					}

					ArrayList<String> xList = new ArrayList<>();

					for (int i = 0; i < points.length(); i++) {
						xList.add(points.getJSONArray(i).getString(0));
					}

					for (int i = 0; i < list1.size(); i++) {
						yBarVal.add((new BarEntry(
								new float[] { list1.get(i), list2.get(i), list3.get(i), list4.get(i) }, i)));
					}
					BarDataSet set1 = new BarDataSet(yBarVal, "");
					set1.setColors(getColors());
					set1.setDrawValues(false);
					set1.setHighlightEnabled(false);
					set1.setStackLabels(
							new String[] { xBarVal.get(0), xBarVal.get(1), xBarVal.get(2), xBarVal.get(3) });

					ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
					sets.add(set1);

					String[] x = new String[xList.size()];
					for (int i = 0; i < xList.size(); i++) {
						x[i] = xList.get(i);
					}

					// line
					LineData ld = new LineData();
					ArrayList<Entry> entries = new ArrayList<Entry>();
					for (int i = 0; i < points.length(); i++) {
						entries.add(
								new Entry(Float.valueOf(points.getJSONArray(i).getString(columns.length() - 1)), i));
					}
					LineDataSet set2 = new LineDataSet(entries, lineTag);
					set2.setColor(ConstantValueUtil.colors2[xBarVal.size()]);
					set2.setLineWidth(2f);
					set2.setCircleSize(0f);
					set2.setCircleColor(ConstantValueUtil.colors2[xBarVal.size()]);
					set2.setDrawCubic(false);
					set2.setDrawValues(true);
					set2.setValueTextSize(8f);
					set2.setValueTextColor(ConstantValueUtil.colors2[xBarVal.size()]);
					ld.addDataSet(set2);

					CombinedData cd = new CombinedData(x);
					BarData bd = new BarData(xList, sets);
					cd.setData(bd);
					cd.setData(ld);
					combinedChart.setData(cd);
					combinedChart.invalidate();

					String title = jo.getString("TITLE");
					title3.setText("周变化趋势图");

				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				break;

			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}

	private int[] getColors() {
		int[] c = new int[4];
		for (int i = 0; i < 4; i++) {
			c[i] = ConstantValueUtil.colors2[i];
		}
		return c;
	}

	private void initEvent() {
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		getTitleBar().setMiddleText(getIntent().getStringExtra("name"));
	}

	private void initView() {
		title1 = (TextView) findViewById(R.id.piechart_title);
		title2 = (TextView) findViewById(R.id.barchart_title);
		title3 = (TextView) findViewById(R.id.combinechart_title);
		pieChart = (PieChart) findViewById(R.id.yunwei_asset_pie_chart);
		barChart = (HorizontalBarChart) findViewById(R.id.yunwei_asset_bar_chart);
		combinedChart = (CombinedChart) findViewById(R.id.yunwei_asset_combine_chart);

		title1.setText("分类统计");
		title2.setText("按系统分类TOP10");
	}

	class Bean {
		private String name;
		private String value;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

}
