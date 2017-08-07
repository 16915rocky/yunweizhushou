package com.chinamobile.yunweizhushou.ui.functionAnalysis.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.functionAnalysis.FunctionAnalysisDetailActivity;
import com.chinamobile.yunweizhushou.ui.functionAnalysis.adapter.FunctionAnalysisListAdapter;
import com.chinamobile.yunweizhushou.ui.functionAnalysis.bean.FunctionAnalysisBean;
import com.chinamobile.yunweizhushou.ui.functionAnalysis.bean.FunctionAnalysisListBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionAnalysisTotalFragment extends BaseFragment implements OnClickListener {

	private ListView mListview;
	private FunctionAnalysisBean bean;
	private FunctionAnalysisListBean listBean;
	private TextView text1, text2, text3, text4, title, content1, content2, content3, content4, doing, done;
	private View doingBar, doneBar;
	private LinearLayout doingLayout, doneLayout;
	private PieChart pieChart;
	private FunctionAnalysisListAdapter mAdapter;
	private String state = "in";

	public static final int[] VORDIPLOM_COLORS = { Color.rgb(192, 255, 140), Color.rgb(255, 247, 140),
			Color.rgb(255, 208, 140), Color.rgb(140, 234, 255), Color.rgb(255, 140, 157) };
	private int[] colors;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.common_listview_notitle, container, false);

		initView(view);
		initData();
		initEvent();
		initRequest();
		initListRequest(state);
		return view;
	}

	private void initListRequest(String state) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "propertyList");
		map.put("state", state);
		startTask(HttpRequestEnum.enum_function_analysis_list, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "property");
		startTask(HttpRequestEnum.enum_function_analysis_total, ConstantValueUtil.URL + "SpecialTreatment?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_function_analysis_total:
			if (responseBean.isSuccess()) {
				bean = new Gson().fromJson(responseBean.getDATA(), FunctionAnalysisBean.class);
				content1.setText(bean.getItemList().get(0).getName());
				text1.setText(bean.getItemList().get(0).getNum());
				content2.setText(bean.getItemList().get(1).getName());
				text2.setText(bean.getItemList().get(1).getNum());
				content3.setText(bean.getItemList().get(2).getName());
				text3.setText(bean.getItemList().get(2).getNum());
				content4.setText(bean.getItemList().get(3).getName());
				text4.setText(bean.getItemList().get(3).getNum());
				title.setText(bean.getDESC());
				ArrayList<Entry> yVals = new ArrayList<>();
				ArrayList<String> xVals = new ArrayList<>();

				for (int i = 1; i < bean.getItemList().size(); i++) {
					Entry entry = new Entry(Float.valueOf(bean.getItemList().get(i).getNum()), i);
					yVals.add(entry);
					xVals.add(bean.getItemList().get(i).getName());
				}

				pieChart.getLegend().setEnabled(false);
				pieChart.setDescription("");

				PieDataSet dataSet = new PieDataSet(yVals, "");
				// dataSet.setColors(VORDIPLOM_COLORS);
				dataSet.setColors(colors);
				PieData data = new PieData(xVals, dataSet);
				data.setValueTextColor(Color.WHITE);
				pieChart.setData(data);
				pieChart.animateXY(100, 100);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;
		case enum_function_analysis_list:

			if (responseBean.isSuccess()) {
				listBean = new Gson().fromJson(responseBean.getDATA(), FunctionAnalysisListBean.class);
				doing.setText(listBean.getIn());
				done.setText(listBean.getOff());
				mAdapter = new FunctionAnalysisListAdapter(getActivity(), listBean.getItemList(),
						R.layout.item_function_analysis_total);
				mListview.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;
		default:
			break;
		}
	}

	private void initData() {

		colors = new int[] {
				// getResources().getColor(R.color.color_dark),
				getResources().getColor(R.color.color_lightgreen), getResources().getColor(R.color.color_deepgreen),
				getResources().getColor(R.color.color_red) };

		View head = LayoutInflater.from(getActivity()).inflate(R.layout.head_function_analysis_total, null);
		initHeadView(head);
		mListview.addHeaderView(head);
	}

	private void initHeadView(View head) {
		text1 = (TextView) head.findViewById(R.id.function_analysis_text1);
		text2 = (TextView) head.findViewById(R.id.function_analysis_text2);
		text3 = (TextView) head.findViewById(R.id.function_analysis_text3);
		text4 = (TextView) head.findViewById(R.id.function_analysis_text4);
		content1 = (TextView) head.findViewById(R.id.function_analysis_content1);
		content2 = (TextView) head.findViewById(R.id.function_analysis_content2);
		content3 = (TextView) head.findViewById(R.id.function_analysis_content3);
		content4 = (TextView) head.findViewById(R.id.function_analysis_content4);
		title = (TextView) head.findViewById(R.id.function_analysis_title);
		pieChart = (PieChart) head.findViewById(R.id.function_analysis_piechart);
		doing = (TextView) head.findViewById(R.id.function_analysis_doing);
		done = (TextView) head.findViewById(R.id.function_analysis_done);
		doingBar = head.findViewById(R.id.function_analysis_doing_bar);
		doneBar = head.findViewById(R.id.function_analysis_done_bar);
		doingLayout = (LinearLayout) head.findViewById(R.id.function_analysis_doing_layout);
		doneLayout = (LinearLayout) head.findViewById(R.id.function_analysis_done_layout);
	}

	private void initView(View view) {
		mListview = (ListView) view.findViewById(R.id.common_listview);
	}

	private void initEvent() {

		doingLayout.setOnClickListener(this);
		doneLayout.setOnClickListener(this);

		mListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position > 0) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), FunctionAnalysisDetailActivity.class);
					intent.putExtra("key", listBean.getItemList().get(position - 1).getServiceKey());
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.function_analysis_doing_layout:
			doing.setTextColor(getResources().getColor(R.color.color_lightblue));
			done.setTextColor(Color.BLACK);
			doingBar.setVisibility(View.VISIBLE);
			doneBar.setVisibility(View.GONE);

			if (!state.equals("in")) {
				state = "in";
				initListRequest(state);
			}
			break;
		case R.id.function_analysis_done_layout:
			done.setTextColor(getResources().getColor(R.color.color_lightblue));
			doing.setTextColor(Color.BLACK);
			doneBar.setVisibility(View.VISIBLE);
			doingBar.setVisibility(View.GONE);

			if (!state.equals("off")) {
				state = "off";
				initListRequest(state);
			}
			break;

		default:
			break;
		}
	}
}
