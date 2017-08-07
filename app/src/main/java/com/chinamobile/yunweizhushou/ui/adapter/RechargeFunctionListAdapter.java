package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RechargeFunctionListAdapter extends BaseAdapter {

	private List<RechargeFunctionGraphBean.ItemsList> data;
	private Context mContext;
	private boolean isFloatState;

	public void setFloatState(boolean state) {
		this.isFloatState = state;
	}

	public RechargeFunctionListAdapter(Context c, List<RechargeFunctionGraphBean.ItemsList> data) {
		this.mContext = c;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_recharge_function_list, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		RechargeFunctionGraphBean.ItemsList bean = data.get(position);// 拿到一个表的对象
		if ("折线图".equals(data.get(position).getCHARTS_TYPE())) {
			holder.barChart.setVisibility(View.GONE);
			List<ILineDataSet> dataSets = new ArrayList<>();
			List<String> xVals = new ArrayList<>();
			ArrayList<Entry> yVals = new ArrayList<>();

			LineChart lineChart = holder.lineChart;

			lineChart.setDrawGridBackground(false);
			lineChart.setDescription("");
			lineChart.setTouchEnabled(false);
			lineChart.setDragEnabled(false);
			lineChart.setScaleEnabled(false);
			lineChart.setNoDataText("暂无数据");

			XAxis x = lineChart.getXAxis();
			x.setPosition(XAxisPosition.BOTTOM);// 显示x位置 下方
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
				l.setForm(LegendForm.LINE); // 设图最下面显示的类型
				l.setFormSize(10f);
				l.setWordWrapEnabled(true);
				l.setPosition(LegendPosition.BELOW_CHART_LEFT);

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
					set.setColor(mContext.getResources().getColor(ConstantValueUtil.colors[i]));
					set.setCircleColor(mContext.getResources().getColor(ConstantValueUtil.colors[i]));
					set.setLineWidth(1f);
					set.setCircleSize(1f);
					set.setFillAlpha(65);
					set.setDrawFilled(false);
					set.setDrawCubic(false);
					set.setFillColor(mContext.getResources().getColor(ConstantValueUtil.colors[i]));
					set.setHighlightEnabled(false);
					set.setDrawCircleHole(false);
					dataSets.add(set);
				}

				LineData datas = new LineData(xVals, dataSets);
				datas.setDrawValues(false);// 画value

				lineChart.setData(datas);
				lineChart.animateX(500);
			}
		} else if ("柱形图".equals(data.get(position).getCHARTS_TYPE())) {
			holder.lineChart.setVisibility(View.GONE);

			List<String> xVals = new ArrayList<String>(); // x轴坐标
			List<IBarDataSet> dataSets = new ArrayList<>();
			ArrayList<BarEntry> entries = new ArrayList<>();
			BarDataSet barDataSet;

			BarChart barChart = holder.barChart;
			barChart.setDescription("");// 数据描述
			barChart.setDrawBarShadow(true);// 会在各条 bar 后面绘制灰色
			barChart.setNoDataTextDescription("暂无数据");// 如果没有数据的时候，会显示这个，类似ListView的EmptyView
			barChart.setDrawGridBackground(false); // 是否显示表格颜色
			barChart.setTouchEnabled(false);// 设置是否可以触摸
			barChart.setDragEnabled(false);// 是否可以拖拽
			barChart.setScaleEnabled(false);// 是否可以缩放
			barChart.setPinchZoom(false);
			// 不绘制从Y轴出发的横向直线
			// barChart.getAxisLeft().setDrawGridLines(true);

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
			xAxis.setDrawGridLines(true);
			// xAxis.setLabelsToSkip(data.get(position).getCOLUMNS().size());

			if (bean.getPOINTS() != null) {
				int pointsSize = bean.getPOINTS().size();
				// 初始化X坐标点
				for (int i = 0; i < pointsSize; i++) {
					String xVal = Utils.getDateFromString2Month(isFloatState == true
							? bean.getPOINTS().get(i).get(0).replace(".00", "") : bean.getPOINTS().get(i).get(0));
					xVals.add(xVal);
				}
				if (isShowHm(xVals)) {
					xVals.clear();
					for (int i = 0; i < pointsSize; i++) {
						String xVal = Utils.getDateFromString2Day(isFloatState == true
								? bean.getPOINTS().get(i).get(0).replace(".00", "") : bean.getPOINTS().get(i).get(0));
						xVals.add(xVal);
					}
				}

				List<List<String>> itemsLists = data.get(position).getPOINTS();
				Float max = Float.valueOf(itemsLists.get(0).get(1));
				Float min = Float.valueOf(itemsLists.get(0).get(1));
				for (int i = 0; i < itemsLists.size(); i++) {
					Float yPoint = Float.valueOf(itemsLists.get(i).get(1));
					min = min > yPoint ? yPoint : min;
					max = max < yPoint ? yPoint : max;
				}

				YAxis yAxis = barChart.getAxisLeft();
				yAxis.setAxisMaxValue(max + 1f);
				yAxis.setStartAtZero(true);
				yAxis.setTextSize(10f);
				yAxis.setDrawGridLines(true);

				YAxis yRightAxis = barChart.getAxisRight();
				yRightAxis.setEnabled(false);

				for (int i = 0; i < itemsLists.size(); i++) {
					Float yVal = Float.valueOf(itemsLists.get(i).get(1));
					entries.add(new BarEntry(yVal > max ? yVal + 1f : yVal, i));
				}

				barDataSet = new BarDataSet(entries, data.get(position).getCOLUMNS().get(1));
				barDataSet.setColor(mContext.getResources().getColor(R.color.color_lightblue));
				barDataSet.setBarShadowColor(Color.parseColor("#20545454"));
				barDataSet.setValueFormatter(new MyValueFormatter());
				barDataSet.setBarSpacePercent(40f);

				dataSets.add(barDataSet);

				BarData barData = new BarData(xVals, dataSets);
				barChart.setData(barData);

				barChart.animateX(500);
			}
		}
		holder.textView.setText(bean.getNAME());
		return convertView;
	}

	class MyValueFormatter implements ValueFormatter {
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

	private class ViewHolder {
		private TextView textView;
		private LinearLayout layout;
		private LineChart lineChart;
		private BarChart barChart;

		public ViewHolder(View view) {
			layout = (LinearLayout) view.findViewById(R.id.itemRechargeLayout);
			textView = (TextView) view.findViewById(R.id.rechargeDescription);
			lineChart = (LineChart) view.findViewById(R.id.lineChart);
			barChart = (BarChart) view.findViewById(R.id.barChart);
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

	public String getDateFromMonth2Min(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		String time = sdf.format(Long.valueOf(date));
		return time;
	}
}
