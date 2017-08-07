package com.chinamobile.yunweizhushou.ui.complaint.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.complaint.adapter.ComplainTodayListAdapter;
import com.chinamobile.yunweizhushou.ui.complaint.bean.ComplainTodayBean;
import com.chinamobile.yunweizhushou.ui.complaint.bean.ComplaintTodayPopupRankBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyGridView;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComplainTodayFragment extends BaseFragment implements OnClickListener {

	private TextView supportComplain, serviceComplain, todayNum, monthNum, todayPer, monthPer, yearPer, yearText,
			yearNum, hotComplain;
	private ImageView todayState, monthState, yearState;
	private ComplainTodayBean todayBean;
	private MyGridView topGridView, increaseGridView;
	private ComplainTodayListAdapter mAdapter;
	private LinearLayout nochartLayout, chartLayout;
	private LineChart mLineChart;
	private TextView chartBtn1, chartBtn2, chartTitle;
	private LinearLayout addHotLayout;
	private String doubleY;
	private boolean isGuangyi;
	private ListView popupListView;
	private List<ComplaintTodayPopupRankBean> popupList;
	private TextView popupTitle;
	private PopupWindow popupWindow;
	private View popupView;
	private ImageView cancel;
	private LinearLayout totalLayout;
	private String name;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_complain_today, container, false);
		initView(view);
		initPopup();
		initEvent();
		// initSupportRequest();
		return view;
	}

	private void initPopupRequest(String name) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "todayNext");
		map.put("busiName", name);
		startTask(HttpRequestEnum.enum_complain_popup, ConstantValueUtil.URL + "ComplaintsBulletin?", map, true);
	}

	private void initPopupRequest2(String name) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "todayIncreaseNext");
		map.put("busiName", name);
		startTask(HttpRequestEnum.enum_complain_popup, ConstantValueUtil.URL + "ComplaintsBulletin?", map, true);
	}

	private void initPopup() {
		popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_complaint_today_rank, null);
		popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		popupListView = (ListView) popupView.findViewById(R.id.complaint_today_rank_pupop_list);
		cancel = (ImageView) popupView.findViewById(R.id.popup_cancel);
		popupTitle = (TextView) popupView.findViewById(R.id.complaint_today_rank_pupop_title);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0));
	}

	private void initSupportRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "today");
		startTask(HttpRequestEnum.enum_complain_today, ConstantValueUtil.URL + "ComplaintsBulletin?", map, true);
	}

	private void initServiceRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "kftoday");
		startTask(HttpRequestEnum.enum_complain_today, ConstantValueUtil.URL + "ComplaintsBulletin?", map, true);
	}

	private void initChartRequest(String id) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "graph");
		map.put("id", id);
		map.put("time", "");
		startTask(HttpRequestEnum.enum_complain_chart, "http://m360.zj.chinamobile.com/360webapp/BusiFluct?", map,
				true);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_complain_today:
			if (responseBean.isSuccess()) {
				todayBean = new Gson().fromJson(responseBean.getDATA(), ComplainTodayBean.class);
				todayNum.setText(todayBean.getToday());
				monthNum.setText(todayBean.getMonth());
				yearNum.setText(todayBean.getYear());
				todayPer.setText(todayBean.getTodayIncrease());
				monthPer.setText(todayBean.getMonthIncrease());
				// yearPer.setText(todayBean.getYearIncrease());

				if (isGuangyi) {
					yearPer.setVisibility(View.VISIBLE);
					yearText.setVisibility(View.VISIBLE);
					yearState.setVisibility(View.VISIBLE);
					yearState.setImageResource(todayBean.getYearCode().equals("1") ? R.mipmap.icon_arrow_up
							: R.mipmap.icon_arrow_down);
					yearPer.setText(todayBean.getYearIncrease());
				} else {
					yearState.setVisibility(View.GONE);
					yearPer.setVisibility(View.GONE);
					yearText.setVisibility(View.GONE);
				}

				todayState.setImageResource(
						todayBean.getTodayCode().equals("1") ? R.mipmap.icon_arrow_up : R.mipmap.icon_arrow_down);
				monthState.setImageResource(
						todayBean.getMonthCode().equals("1") ? R.mipmap.icon_arrow_up : R.mipmap.icon_arrow_down);

				mAdapter = new ComplainTodayListAdapter(getActivity(), todayBean.getTop10List(),
						R.layout.item_complain_today);
				topGridView.setAdapter(mAdapter);
				mAdapter = new ComplainTodayListAdapter(getActivity(), todayBean.getTop10IncreaseList(),
						R.layout.item_complain_today);
				increaseGridView.setAdapter(mAdapter);

				if (!TextUtils.isEmpty(todayBean.getHots())) {
					addHotLayout.setVisibility(View.VISIBLE);
					addHotLayout.removeAllViews();
					addHotLayout.setPadding(30, 0, 30, 0);
					String[] tips = todayBean.getHots().split(";");
					for (int i = 0; i < tips.length; i++) {
						TextView tv = new TextView(getActivity());
						tv.setTextSize(12);
						tv.setText(tips[i]);
						Drawable drawable = getActivity().getResources().getDrawable(R.mipmap.icon_hot);
						drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
						tv.setCompoundDrawables(drawable, null, null, null);
						addHotLayout.addView(tv);
					}
				} else {
					addHotLayout.setVisibility(View.GONE);
				}
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;
		case enum_complain_chart:
			String data = responseBean.getDATA();
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
				if (jsonObject.has("TITLE")) {
					String str = jsonObject.getString("TITLE");
					chartTitle.setText(str);
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			break;

		case enum_complain_popup:
			if (responseBean.isSuccess()) {
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
				Type t = new TypeToken<List<ComplaintTodayPopupRankBean>>() {
				}.getType();
				popupList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				ComplaintPopupRankAdapter adapter = new ComplaintPopupRankAdapter(getActivity(), popupList,
						R.layout.item_complain_today);
				popupListView.setAdapter(adapter);
				popupTitle.setText(name);
				popupWindow.showAtLocation(totalLayout, Gravity.CENTER, 0, 0);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;
		default:
			break;
		}
	}

	private void initEvent() {
		supportComplain.setOnClickListener(this);
		serviceComplain.setOnClickListener(this);
		hotComplain.setOnClickListener(this);
		chartBtn1.setOnClickListener(this);
		chartBtn2.setOnClickListener(this);

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
			}
		});

		supportComplain.performClick();

		if (!isGuangyi) {
			topGridView.setEnabled(true);
			topGridView.setFocusable(true);
			topGridView.setClickable(true);
		} else {
			topGridView.setEnabled(false);
			topGridView.setClickable(false);
			topGridView.setFocusable(false);
		}
		topGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				name = todayBean.getTop10List().get(position).getName();
				initPopupRequest(name);
			}
		});

		increaseGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				name = todayBean.getTop10IncreaseList().get(position).getName();
				initPopupRequest2(name);
			}
		});

	}

	private void initView(View view) {
		supportComplain = (TextView) view.findViewById(R.id.complain_btn_support);
		serviceComplain = (TextView) view.findViewById(R.id.complain_btn_service);
		hotComplain = (TextView) view.findViewById(R.id.complain_btn_hot);
		todayNum = (TextView) view.findViewById(R.id.complain_num_today);
		monthNum = (TextView) view.findViewById(R.id.complain_num_month);
		yearNum = (TextView) view.findViewById(R.id.complain_num_year);
		todayPer = (TextView) view.findViewById(R.id.complain_percent_today);
		monthPer = (TextView) view.findViewById(R.id.complain_percent_month);
		yearPer = (TextView) view.findViewById(R.id.complain_percent_year);
		todayState = (ImageView) view.findViewById(R.id.complain_today_state);
		monthState = (ImageView) view.findViewById(R.id.complain_month_state);
		yearState = (ImageView) view.findViewById(R.id.complain_year_state);
		yearText = (TextView) view.findViewById(R.id.complain_year_huan);

		mLineChart = (LineChart) view.findViewById(R.id.complain_today_chart);
		initChart();
		chartBtn1 = (TextView) view.findViewById(R.id.complain_today_chart_btn1);
		chartBtn2 = (TextView) view.findViewById(R.id.complain_today_chart_btn2);
		chartTitle = (TextView) view.findViewById(R.id.complain_today_chart_title);

		nochartLayout = (LinearLayout) view.findViewById(R.id.complain_today_nochart_layout);
		chartLayout = (LinearLayout) view.findViewById(R.id.complain_today_chart_layout);

		addHotLayout = (LinearLayout) view.findViewById(R.id.complain_hot_tips_layout);

		topGridView = (MyGridView) view.findViewById(R.id.complain_preday_num_gridview);
		increaseGridView = (MyGridView) view.findViewById(R.id.complain_preday_increase_num_gridview);

		totalLayout = (LinearLayout) view.findViewById(R.id.complaint_today_total_layout);
	}

	private void initChart() {
		mLineChart.setDrawGridBackground(false);
		mLineChart.setDescription("");
		mLineChart.setTouchEnabled(false);
		mLineChart.setDragEnabled(false);
		mLineChart.setScaleEnabled(false);
		mLineChart.setPinchZoom(false);
		mLineChart.setNoDataText("正在加载..");
		Legend l = mLineChart.getLegend();
		l.setForm(LegendForm.LINE);
		l.setTextColor(Color.GRAY);
		l.setWordWrapEnabled(true);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.complain_btn_support:
			isGuangyi = false;
			resetAll();
			initSupportRequest();
			supportComplain.setTextColor(getResources().getColor(R.color.color_white));
			supportComplain.setBackgroundResource(R.drawable.rect_blue_bg);
			transformLayout(1);
			break;
		case R.id.complain_btn_service:
			isGuangyi = true;
			resetAll();
			initServiceRequest();
			serviceComplain.setTextColor(getResources().getColor(R.color.color_white));
			serviceComplain.setBackgroundResource(R.drawable.rect_blue_bg);
			transformLayout(1);
			break;
		case R.id.complain_btn_hot:
			resetAll();
			hotComplain.setTextColor(getResources().getColor(R.color.color_white));
			hotComplain.setBackgroundResource(R.drawable.rect_blue_bg);
			transformLayout(2);
			initChartRequest("10906");
			break;
		case R.id.complain_today_chart_btn1:
			initChartRequest("10906");
			break;
		case R.id.complain_today_chart_btn2:
			initChartRequest("10907");
			break;
		default:
			break;
		}
	}

	private void transformLayout(int type) {
		switch (type) {
		case 1:
			if (nochartLayout.getVisibility() == View.GONE) {
				nochartLayout.setVisibility(View.VISIBLE);
				chartLayout.setVisibility(View.GONE);
			}
			break;
		case 2:
			if (chartLayout.getVisibility() == View.GONE) {
				chartLayout.setVisibility(View.VISIBLE);
				nochartLayout.setVisibility(View.GONE);
			}
			break;

		default:
			break;
		}
	}

	private void resetAll() {
		serviceComplain.setTextColor(getResources().getColor(R.color.color_blue));
		serviceComplain.setBackgroundResource(R.drawable.rect_blue_stroke_bg);
		supportComplain.setTextColor(getResources().getColor(R.color.color_blue));
		supportComplain.setBackgroundResource(R.drawable.rect_blue_stroke_bg);
		hotComplain.setTextColor(getResources().getColor(R.color.color_blue));
		hotComplain.setBackgroundResource(R.drawable.rect_blue_stroke_bg);
	}

	class ComplaintPopupRankAdapter extends AbsBaseAdapter<ComplaintTodayPopupRankBean> {

		private List<ComplaintTodayPopupRankBean> list;

		public ComplaintPopupRankAdapter(Context context, List<ComplaintTodayPopupRankBean> list, int resourceId) {
			super(context, list, resourceId);
			this.list = list;
		}

		@Override
		protected View newView(View convertView, ComplaintTodayPopupRankBean t, int position) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder();
				holder.index = (TextView) convertView.findViewById(R.id.complain_today_item_index);
				holder.name = (TextView) convertView.findViewById(R.id.complain_today_item_name);
				holder.point = (TextView) convertView.findViewById(R.id.complain_today_item_num);
				convertView.setTag(holder);
			}
			holder.index.setText(fillZero(position));
			if (position == 0) {
				holder.index.setBackgroundResource(R.drawable.oval_orange);
			} else if (position == 1) {
				holder.index.setBackgroundResource(R.drawable.oval_yellow);
			} else if (position == 2) {
				holder.index.setBackgroundResource(R.drawable.oval_blue);
			} else {
				holder.index.setBackgroundResource(R.drawable.oval_gray);
			}
			holder.name.setText(list.get(position).getName());
			holder.point.setText(list.get(position).getNumber());
			return convertView;
		}
	}

	private static class ViewHolder {
		TextView index, name, point;
	}

	private String fillZero(int position) {
		int pos = position + 1;
		if (pos < 10) {
			return "0" + pos;
		}
		return "" + pos;
	}
}
