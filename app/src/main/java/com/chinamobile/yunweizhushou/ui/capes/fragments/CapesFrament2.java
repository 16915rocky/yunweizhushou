package com.chinamobile.yunweizhushou.ui.capes.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionBean;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.RechargeFunctionListAdapter;
import com.chinamobile.yunweizhushou.ui.backlogZone.GraphListActivity;
import com.chinamobile.yunweizhushou.ui.capes.RechargeFunctionGraphDetailActivity;
import com.chinamobile.yunweizhushou.ui.capes.adapter.Capes2LeftAdapter;
import com.chinamobile.yunweizhushou.ui.capes.bean.Capes2LeftBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class CapesFrament2 extends BaseFragment implements OnClickListener {
	private TextView current, fail;
	private ListView mListview, graphListview;
	private static final int FAIL = 0;
	private static final int CURRENT = 1021;
	private int tag;
	private List<Capes2LeftBean> mListLeft;
	private Capes2LeftAdapter mAdapterLeft;
	private LinearLayout searchLayout, listLayout;
	private TextView noMsgTextview, searchBtn, selectTextview;
	private RechargeFunctionBean bean;

	private int popupWindowSelectPosition;
	private PopupWindow mPopupWindow;
	private LinearLayout selectLayout;
	private ImageView selectArrow;
	private ListView selectListView;
	private String selectKey, selectValue;
	private RechargeFunctionListAdapter graphAdapter;
	private RechargeFunctionGraphBean beans;

	private boolean hasShownChart;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_capes_2, container, false);
		initView(view);
		initData();
		initEvent();
		initLeftRequest();
		return view;
	}

	private void initLeftRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", tag + "");
		map.put("code", "");
		map.put("busi", "");
		map.put("cond", "");
		startTask(HttpRequestEnum.enum_capes_left_list2, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
	}

	private void initSearchRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("fkId", "1020");
		map.put("time", "6h");
		map.put(selectKey, selectValue);
		startTask(HttpRequestEnum.enum_capes_2_graph, ConstantValueUtil.URL + "BusiFluct?", map, true);

	}

	private void initSelectListRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveParam");
		map.put("upId", "1020");
		startTask(HttpRequestEnum.enum_capes_2_select_list, ConstantValueUtil.URL + "BusiFluct?", map);

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_capes_left_list2:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<Capes2LeftBean>>() {
				}.getType();
				mListLeft = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapterLeft = new Capes2LeftAdapter(getActivity(), mListLeft, R.layout.item_capes_2_left);
				mListview.setAdapter(mAdapterLeft);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;

		case enum_capes_2_graph:
			if (responseBean.isSuccess()) {
				Type t = new TypeToken<RechargeFunctionGraphBean>() {
				}.getType();
				beans = new Gson().fromJson(responseBean.getDATA(), t);
				graphAdapter = new RechargeFunctionListAdapter(getActivity(), beans.getItemsList());
				if (!hasShownChart) {
					hasShownChart = true;
				}
				showChartLayout();
				graphListview.setAdapter(graphAdapter);

			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;
		case enum_capes_2_select_list:
			if (responseBean.isSuccess()) {
				bean = new Gson().fromJson(responseBean.getDATA(), RechargeFunctionBean.class);
				SpinnerListAdapter adapter = new SpinnerListAdapter(bean.getItemsList().get(0).getItemsList());
				selectListView.setAdapter(adapter);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}

			break;

		default:
			break;
		}
	}

	private void initData() {
		current.performClick();
		current.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
		current.setTextColor(getActivity().getResources().getColor(R.color.color_white));
		tag = CURRENT;
		View selectView = LayoutInflater.from(getActivity()).inflate(R.layout.common_listview_notitle, null);
		selectListView = (ListView) selectView.findViewById(R.id.common_listview);
		mPopupWindow = new PopupWindow(selectView, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		// 初始设置显示ListLayout
		showListLayout();
	}

	private void showListLayout() {
		listLayout.setVisibility(View.VISIBLE);
		searchLayout.setVisibility(View.GONE);
		graphListview.setVisibility(View.GONE);
	}

	private void showChartLayout() {
		listLayout.setVisibility(View.GONE);
		searchLayout.setVisibility(View.VISIBLE);
		if (hasShownChart) {
			graphListview.setVisibility(View.VISIBLE);
			noMsgTextview.setVisibility(View.GONE);
		} else {
			graphListview.setVisibility(View.GONE);
			noMsgTextview.setVisibility(View.VISIBLE);
		}
	}

	private void initEvent() {
		current.setOnClickListener(this);
		fail.setOnClickListener(this);
		selectLayout.setOnClickListener(this);
		searchBtn.setOnClickListener(this);
		graphListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), 	RechargeFunctionGraphDetailActivity.class);
				intent.putExtra("fkId", "1020");

				intent.putExtra("cityType",
						bean.getItemsList().get(0).getItemsList().get(popupWindowSelectPosition).getType());
				intent.putExtra("cityValue",
						bean.getItemsList().get(0).getItemsList().get(popupWindowSelectPosition).getValue());
				intent.putExtra("time", "6h");
				intent.putExtra("waveId", beans.getItemsList().get(position).getWaveId());
				// Utils.ShowErrorMsg(getActivity(),
				// bean.getItemsList().get(0).getItemsList().get(popupWindowSelectPosition).getType());
				// Utils.ShowErrorMsg(getActivity(),
				// bean.getItemsList().get(0).getItemsList().get(popupWindowSelectPosition).getValue());

				intent.putExtra("positionList", beans.getItemsList().get(position));
				startActivity(intent);
			}
		});

		selectListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mPopupWindow.dismiss();
				selectArrow.setImageResource(R.mipmap.icon_recharge_tab_bottom);
				selectKey = bean.getItemsList().get(0).getItemsList().get(position).getType();
				selectValue = bean.getItemsList().get(0).getItemsList().get(position).getValue();
				selectTextview.setText(bean.getItemsList().get(0).getItemsList().get(position).getName());
				popupWindowSelectPosition = position;
				if (!hasShownChart) {
					selectTextview.setTextColor(getActivity().getResources().getColor(R.color.color_lightblue));
				}
			}
		});

		mListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("fkId", "1019");
				intent.putExtra("name", mListLeft.get(position).getName());
				intent.putExtra("userId", mListLeft.get(position).getKpi());
				intent.setClass(getActivity(), GraphListActivity.class);
				getActivity().startActivity(intent);
			}
		});
	}

	private void initView(View view) {
		current = (TextView) view.findViewById(R.id.capes_2_current);
		fail = (TextView) view.findViewById(R.id.capes_2_failnum);
		mListview = (ListView) view.findViewById(R.id.capes_2_listview);
		graphListview = (ListView) view.findViewById(R.id.capes_2_graph_listview);

		listLayout = (LinearLayout) view.findViewById(R.id.capes_2_list_layout);
		searchLayout = (LinearLayout) view.findViewById(R.id.capes_2_search_layout);
		noMsgTextview = (TextView) view.findViewById(R.id.capes_2_nomsg_textview);
		selectLayout = (LinearLayout) view.findViewById(R.id.capes_2_select_btn);
		selectArrow = (ImageView) view.findViewById(R.id.capes_2_select_image);
		searchBtn = (TextView) view.findViewById(R.id.capes_2_search_btn);
		selectTextview = (TextView) view.findViewById(R.id.capes_2_select_textview);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.capes_2_current:
			if (tag == FAIL) {
				resetAll();
				showListLayout();
				tag = CURRENT;
				current.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
				current.setTextColor(getActivity().getResources().getColor(R.color.color_white));
				initLeftRequest();
			}
			break;
		case R.id.capes_2_failnum:
			if (tag == CURRENT) {
				resetAll();
				showChartLayout();
				tag = FAIL;
				fail.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
				fail.setTextColor(getActivity().getResources().getColor(R.color.color_white));
			}
			break;
		case R.id.capes_2_select_btn:
			if (!mPopupWindow.isShowing()) {
				initSelectListRequest();
				selectArrow.setImageResource(R.mipmap.icon_recharge_tab_top);
				mPopupWindow.showAsDropDown(searchLayout, 0, 0, Gravity.TOP);
			} else {
				mPopupWindow.dismiss();
				selectArrow.setImageResource(R.mipmap.icon_recharge_tab_bottom);
			}
			break;
		case R.id.capes_2_search_btn:
			initSearchRequest();
			break;

		default:
			break;
		}
	}

	private void resetAll() {
		current.setTextColor(getActivity().getResources().getColor(R.color.color_lightblue));
		fail.setTextColor(getActivity().getResources().getColor(R.color.color_lightblue));
		current.setBackgroundResource(R.drawable.corner_rectangle_white_bg);
		fail.setBackgroundResource(R.drawable.corner_rectangle_white_bg);
	}

	// popupWindow列表适配器
	class SpinnerListAdapter extends BaseAdapter {

		private List<RechargeFunctionBean.ItemsChildList> list;

		public SpinnerListAdapter(List<RechargeFunctionBean.ItemsChildList> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(getActivity());
			textView.setBackgroundColor(getResources().getColor(R.color.color_white));
			textView.setText(list.get(position).getName());
			textView.setTextSize(16f);
			textView.setGravity(Gravity.CENTER);
			textView.setPadding(10, 10, 10, 10);
			return textView;
		}
	}

}
