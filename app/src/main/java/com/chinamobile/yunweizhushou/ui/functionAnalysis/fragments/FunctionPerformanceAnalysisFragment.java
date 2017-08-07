package com.chinamobile.yunweizhushou.ui.functionAnalysis.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.functionAnalysis.adapter.FunctionPerformanceListAdapter;
import com.chinamobile.yunweizhushou.ui.functionAnalysis.bean.FunctionAnalysisPerformancebean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FunctionPerformanceAnalysisFragment extends BaseFragment {

	private ListView mListView;
	// headerView组件
	private PieChart pieChart;
	private FunctionAnalysisPerformancebean bean;
	private FunctionAnalysisPerformancebean chartBean;

	private int[] colors;

	private int colorId = 0;
	private int[] colors2 = new int[] { R.color.color_blue, R.color.color_lightgreen, R.color.color_yellow,
			R.color.color_pink, R.color.color_violet, R.color.color_lightblue, R.color.color_deepyellow,
			R.color.color_yellowgreen, R.color.color_dark, R.color.color_greenblue };

	private View head;

	private GridView mGridView;

	private List<LegendInfo> legendList = new ArrayList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.common_listview_notitle, container, false);
		initView(rootView);
		initData();
		initChartRequest();
		initRequest();
		return rootView;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "propertyTop");
		startTask(HttpRequestEnum.enum_function_analysis_performance_list, ConstantValueUtil.URL + "SpecialTreatment?",
				map);
	}

	private void initData() {
		colors = new int[] { getResources().getColor(R.color.color_deepgreen),
				getResources().getColor(R.color.color_orange), getResources().getColor(R.color.color_red) };
	}

	private void initChartRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "propertyAnaly");
		startTask(HttpRequestEnum.enum_function_analysis_performance, ConstantValueUtil.URL + "SpecialTreatment?", map);
	}

	private void initView(View view) {
		mListView = (ListView) view.findViewById(R.id.common_listview);
		mListView.setDivider(null);

		head = View.inflate(getActivity(), R.layout.fragment_performance_analysis_head, null);
		mGridView = (GridView) head.findViewById(R.id.chartLegendGridView);
		pieChart = (PieChart) head.findViewById(R.id.performance_analysis_piechart);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_function_analysis_performance:
				Type type2 = new TypeToken<FunctionAnalysisPerformancebean>() {
				}.getType();
				chartBean = new Gson().fromJson(responseBean.getDATA(), type2);

				ArrayList<Entry> yVals = new ArrayList<>();
				ArrayList<String> xVals = new ArrayList<>();
				if (chartBean.getItemsList() == null || chartBean.getItemsList().size() == 0) {
					return;
				}
				for (int i = 0; i < chartBean.getItemsList().size(); i++) {
					Entry entry = new Entry(Float.valueOf(chartBean.getItemsList().get(i).getNum()), i);
					yVals.add(entry);
					xVals.add(chartBean.getItemsList().get(i).getName());

					LegendInfo legendInfo = new LegendInfo();
					legendInfo.setText(chartBean.getItemsList().get(i).getName());
					if (colors.length >= chartBean.getItemsList().size()) {
						legendInfo.setColor(colors[i]);
					} else {
						legendInfo.setColor(colors2[colorId++]);
					}
					legendList.add(legendInfo);
				}
				mGridView.setNumColumns(chartBean.getItemsList().size() > 5 ? 5 : chartBean.getItemsList().size());
				pieChart.getLegend().setEnabled(false);
				pieChart.setDescription("");
				PieDataSet dataSet = new PieDataSet(yVals, "");

				if (colors.length >= legendList.size()) {

				} else {
					colors = new int[legendList.size()];
					for (int i = 0; i < legendList.size(); i++) {
						colors[i] = legendList.get(i).getColor();
					}
				}

				dataSet.setColors(colors);
				PieData data = new PieData(xVals, dataSet);
				data.setValueTextColor(Color.WHITE);
				pieChart.setData(data);
				pieChart.invalidate();

				mGridView.setAdapter(new PieChartLegendAdapter());
				break;
			case enum_function_analysis_performance_list:
				Type type = new TypeToken<FunctionAnalysisPerformancebean>() {
				}.getType();
				bean = new Gson().fromJson(responseBean.getDATA(), type);

				FunctionPerformanceListAdapter adapter = new FunctionPerformanceListAdapter(getActivity(),
						bean.getItemsList());
				mListView.addHeaderView(head);
				mListView.setAdapter(adapter);
				break;
			}
		}
	}

	class PieChartLegendAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return legendList.size();
		}

		@Override
		public Object getItem(int position) {
			return legendList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getActivity(), R.layout.item_function_performance_chart_legend, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.legendColor.setBackgroundColor(legendList.get(position).getColor());
			holder.legendText.setText(legendList.get(position).getText());
			return convertView;
		}
	}

	protected class ViewHolder {
		private View legendColor;
		private TextView legendText;

		public ViewHolder(View v) {
			this.legendColor = v.findViewById(R.id.legendColor);
			this.legendText = (TextView) v.findViewById(R.id.legendText);
		}
	}

	class LegendInfo {
		private int color;
		private String text;

		public int getColor() {
			return color;
		}

		public void setColor(int color) {
			this.color = color;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public LegendInfo(int color, String text) {
			super();
			this.color = color;
			this.text = text;
		}

		public LegendInfo() {
			super();
			// TODO Auto-generated constructor stub
		}
	}
}
