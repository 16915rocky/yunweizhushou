package com.chinamobile.yunweizhushou.ui.fault.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.fault.FaultManageActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
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

public class FaultRecentFragment extends BaseFragment implements FaultManageActivity.SwitchToRecentPagerListener {

	private LineChart mLineChart;
	private TextView title, head;
	private boolean isFirst = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_linechart_with_head, container, false);
		initView(view);
		((FaultManageActivity) getActivity()).setOnSwicthToRecentListener(this);
		initEvent();
		return view;
	}

	private void initEvent() {

	}

	private void initView(View view) {
		title = (TextView) view.findViewById(R.id.fragment_linechart_title);
		head = (TextView) view.findViewById(R.id.fragment_linechart_head_text);
		mLineChart = (LineChart) view.findViewById(R.id.fragment_linechart);
		initChart();
	}

	private void initChart() {
		mLineChart.setDrawGridBackground(false);
		mLineChart.setDescription("");
		mLineChart.setTouchEnabled(true);
		mLineChart.setDragEnabled(true);
		mLineChart.setScaleEnabled(true);
		mLineChart.setPinchZoom(true);
		mLineChart.setNoDataText("正在加载..");
		Legend l = mLineChart.getLegend();
		l.setForm(LegendForm.SQUARE);
		l.setTextColor(Color.GRAY);
		l.setWordWrapEnabled(true);
		l.setTextSize(12f);
		l.setDirection(LegendDirection.LEFT_TO_RIGHT);
		l.setPosition(LegendPosition.BELOW_CHART_LEFT);
		XAxis xAxis = mLineChart.getXAxis();
		xAxis.setTextColor(Color.GRAY);
		xAxis.setTextSize(4f);
		xAxis.setDrawGridLines(false);
		xAxis.setDrawAxisLine(false);
		xAxis.setPosition(XAxisPosition.BOTTOM);
		xAxis.setDrawLimitLinesBehindData(true);
		xAxis.setLabelRotationAngle(0);
		YAxis leftAxis = mLineChart.getAxisLeft();
		leftAxis.setTextColor(getResources().getColor(R.color.color_black));
		leftAxis.setDrawGridLines(true);
		leftAxis.setDrawAxisLine(false);
		leftAxis.setTextSize(8f);
		leftAxis.setDrawLimitLinesBehindData(true);
		YAxis rightAxis = mLineChart.getAxisRight();
		rightAxis.setEnabled(false);
	}

	@Override
	public void switchToRecent() {
		if (isFirst) {
			isFirst = false;
			initRequest();
		}
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "recent");
		startTask(HttpRequestEnum.enum_faultnamage_recent, ConstantValueUtil.URL + "Trouble?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_faultnamage_recent:
			String total = responseBean.getTOTAL();
			head.setText(total);
			String data = responseBean.getDATA();

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
							yValues.add(new Entry(Integer.valueOf(yLists.get(i).get(j)), j));
						}
						LineDataSet set = new LineDataSet(yValues, type.get(i + 1));
						set.setAxisDependency(AxisDependency.LEFT);
						set.setColor(getResources().getColor(ConstantValueUtil.colors[i]));
						set.setCircleColor(getResources().getColor(ConstantValueUtil.colors[i]));
						set.setLineWidth(1f);
						set.setCircleSize(1f);
						set.setFillAlpha(65);
						set.setDrawFilled(true);
						set.setDrawCubic(false);
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
				if (jsonObject.has("TITLE")) {
					String t = jsonObject.getString("TITLE");
					title.setText(t);
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
}
