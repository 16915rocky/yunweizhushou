package com.chinamobile.yunweizhushou.ui.fault.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.fault.FaultManageActivity;
import com.chinamobile.yunweizhushou.ui.fault.FaultServiceNextActivity;
import com.chinamobile.yunweizhushou.ui.fault.HorizenGraphActivity;
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

import static com.chinamobile.yunweizhushou.R.id.lt_extra;

public class FaultServiceFragment extends BaseFragment implements OnClickListener, FaultManageActivity.SwitchToServicePagerListener {

	private LineChart mLineChart;
	private LinearLayout ltExtra;
	private TextView leftBtn, rightBtn, title;
	private String doubleY;
	private boolean isFirst = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_fault_service, container, false);

		initView(view);
		((FaultManageActivity) getActivity()).setOnSwicthToServiceListener(this);
		initEvent();
		initChart();
		initChartRequest("10906");
		return view;

	}

	@Override
	public void switchToService() {
		if (isFirst) {
			isFirst = false;
			leftBtn.performClick();
		}
	}

	private void initEvent() {
		leftBtn.setOnClickListener(this);
		rightBtn.setOnClickListener(this);
	}

	private void initView(View view) {

		ltExtra = (LinearLayout) view.findViewById(lt_extra);
		title = (TextView) view.findViewById(R.id.fault_service_chart_title);
		leftBtn = (TextView) view.findViewById(R.id.fault_service_chart_btn1);
		rightBtn = (TextView) view.findViewById(R.id.fault_service_chart_btn2);
		mLineChart = (LineChart) view.findViewById(R.id.fault_service_chart);
		rightBtn.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
	}

	private void initChartRequest(String id) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "graph");
		map.put("id", id);
		map.put("time", "");
		startTask(HttpRequestEnum.enum_complain_chart, ConstantValueUtil.URL +"BusiFluct?", map,
				true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}

		switch (e) {
		case enum_complain_chart:
			
			String data = responseBean.getDATA();
			if(!"".equals(data)){
			try {
				JSONObject jsonObject = new JSONObject(data);
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
				//添加波动线描述
				if(type.size()>1){
					for(int i=1;i<type.size();i++) {
						View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_fault_cshs, null);
						View vLine = view.findViewById(R.id.v_line);
						TextView tvDesc = (TextView) view.findViewById(R.id.tv_desc);
						vLine.setBackgroundResource(ConstantValueUtil.colors[i-1]);
						tvDesc.setText(type.get(i));
						ltExtra.addView(view);
						view.setTag(type.get(i));
						view.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View view) {
								Intent intent = new Intent();
								intent.putExtra("title",view.getTag().toString());
								intent.setClass(getActivity(), FaultServiceNextActivity.class);
								startActivity(intent);
							}
						});
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
							yValues.add(new Entry(Integer.valueOf(yLists.get(i).get(j)), j));
						}
						LineDataSet set = new LineDataSet(yValues, type.get(i + 1));
						if (i == 1) {
							if (doubleY.equals("1")) {
								set.setAxisDependency(AxisDependency.RIGHT);
							} else {
								set.setAxisDependency(AxisDependency.LEFT);
							}
						}
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
				if (jsonObject.has("NAME")) {
					String str = jsonObject.getString("NAME");
					title.setText(str);
				}
			
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			}else{
				mLineChart.animateXY(100, 100);
				mLineChart.setData(null);
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fault_service_chart_btn1:
			initChartRequest("10906");
			leftBtn.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
			rightBtn.setBackgroundResource(R.drawable.corner_rectangle_lightgray2_bg3);
			break;
		case R.id.fault_service_chart_btn2:
		/*	initChartRequest("10907");
			leftBtn.setBackgroundResource(R.drawable.corner_rectangle_lightgray2_bg3);
			rightBtn.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);*/
			Intent intent = new Intent();
			intent.setClass(getActivity(), HorizenGraphActivity.class);
			intent.putExtra("action", "graph");
			intent.putExtra("id", "10907");
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	private void initChart() {
		mLineChart.setDrawGridBackground(false);
		mLineChart.setDescription("");
		mLineChart.setTouchEnabled(false);
		mLineChart.setDragEnabled(false);
		mLineChart.setScaleEnabled(false);
		mLineChart.setPinchZoom(false);
		mLineChart.setNoDataText("当前无公告内容");
		Legend l = mLineChart.getLegend();
		l.setForm(LegendForm.LINE);
		l.setTextColor(Color.GRAY);
		l.setWordWrapEnabled(true);
		l.setDirection(LegendDirection.LEFT_TO_RIGHT);
		l.setPosition(LegendPosition.BELOW_CHART_LEFT);
		l.setEnabled(false);
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
		mLineChart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), HorizenGraphActivity.class);
				intent.putExtra("action", "graph");
				intent.putExtra("id", "10906");
				startActivity(intent);
			}
		});
	}

}
