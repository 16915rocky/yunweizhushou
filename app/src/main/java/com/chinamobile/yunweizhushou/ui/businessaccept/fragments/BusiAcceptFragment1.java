package com.chinamobile.yunweizhushou.ui.businessaccept.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.RechargeFunctionListAdapter;
import com.chinamobile.yunweizhushou.ui.businessaccept.BusinessAcceptHorizenGraphActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class BusiAcceptFragment1 extends BaseFragment implements OnClickListener {

	private MyListView listview1, listview2;
	private TextView city1, city2, city3, city4, city5, city6, city7, city8, city9, city10, city11, city12;
	private MyRefreshLayout refreshLayout;
	private LinearLayout groupLayout1, groupLayout2;

	private int tag;
	private String currentCity;

	private RechargeFunctionListAdapter adapter, adapter2;
	private RechargeFunctionGraphBean beans, beans2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_business_accept, container, false);
		initView(view);
		initEvent();
		initRequest();

		city1.performClick();

		return view;

	}

	// 只有第一页有的
	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("time", "6h");
		map.put("fkId", "1044");
		startTask(HttpRequestEnum.enum_busi_accept_1, ConstantValueUtil.URL + "BusiFluct?", map, true);
	}

	private void initCityRequest(String code) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("time", "6h");
		map.put("region_code", code);
		map.put("fkId", "1046");
		startTask(HttpRequestEnum.enum_busi_accept_2, ConstantValueUtil.URL + "BusiFluct?", map, true);
	}

	private void initAllCityRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("time", "6h");
		map.put("fkId", "1045");
		startTask(HttpRequestEnum.enum_busi_accept_0, ConstantValueUtil.URL + "BusiFluct?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (refreshLayout.isRefreshing()) {
			refreshLayout.setRefreshing(false);
		}
		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_busi_accept_1:
				Type t = new TypeToken<RechargeFunctionGraphBean>() {
				}.getType();
				beans = new Gson().fromJson(responseBean.getDATA(), t);
				adapter = new RechargeFunctionListAdapter(getActivity(), beans.getItemsList());
				listview1.setAdapter(adapter);
				break;
			case enum_busi_accept_2:
			case enum_busi_accept_0:
				Type t2 = new TypeToken<RechargeFunctionGraphBean>() {
				}.getType();
				beans2 = new Gson().fromJson(responseBean.getDATA(), t2);
				adapter2 = new RechargeFunctionListAdapter(getActivity(), beans2.getItemsList());
				listview2.setAdapter(adapter2);

			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}

	private void initEvent() {
		city1.setOnClickListener(this);
		city2.setOnClickListener(this);
		city3.setOnClickListener(this);
		city4.setOnClickListener(this);
		city5.setOnClickListener(this);
		city6.setOnClickListener(this);
		city7.setOnClickListener(this);
		city8.setOnClickListener(this);
		city9.setOnClickListener(this);
		city10.setOnClickListener(this);
		city11.setOnClickListener(this);
		city12.setOnClickListener(this);

		refreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
				if (currentCity.contains(",")) {
					initAllCityRequest();
				} else {
					initCityRequest(currentCity);
				}
			}
		});

		listview1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), BusinessAcceptHorizenGraphActivity.class);
				intent.putExtra("fkId", "1044");
				startActivity(intent);
			}
		});
		listview2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), BusinessAcceptHorizenGraphActivity.class);

				if (currentCity.contains(",")) {
					intent.putExtra("fkId", "1045");
				} else {
					intent.putExtra("extraKey", "region_code");
					intent.putExtra("extraValue", currentCity);
					intent.putExtra("fkId", "1046");
				}
				startActivity(intent);
			}
		});
	}

	private void initView(View view) {
		listview1 = (MyListView) view.findViewById(R.id.busi_list_1);
		listview2 = (MyListView) view.findViewById(R.id.busi_list_2);
		city1 = (TextView) view.findViewById(R.id.busi_city_1);
		city2 = (TextView) view.findViewById(R.id.busi_city_2);
		city3 = (TextView) view.findViewById(R.id.busi_city_3);
		city4 = (TextView) view.findViewById(R.id.busi_city_4);
		city5 = (TextView) view.findViewById(R.id.busi_city_5);
		city6 = (TextView) view.findViewById(R.id.busi_city_6);
		city7 = (TextView) view.findViewById(R.id.busi_city_7);
		city8 = (TextView) view.findViewById(R.id.busi_city_8);
		city9 = (TextView) view.findViewById(R.id.busi_city_9);
		city10 = (TextView) view.findViewById(R.id.busi_city_10);
		city11 = (TextView) view.findViewById(R.id.busi_city_11);
		city12 = (TextView) view.findViewById(R.id.busi_city_12);
		refreshLayout = (MyRefreshLayout) view.findViewById(R.id.busi_refresh_layout);

		groupLayout1 = (LinearLayout) view.findViewById(R.id.busi_group_layout_1);
		groupLayout2 = (LinearLayout) view.findViewById(R.id.busi_group_layout_2);

		groupLayout1.setVisibility(View.GONE);
		groupLayout2.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		resetAll();
		setChecked((TextView) v);
		if (v.getId() == R.id.busi_city_1) {
			currentCity = v.getTag().toString();
			initAllCityRequest();
		} else if (v.getTag().toString().startsWith("5")) {
			currentCity = v.getTag().toString();
			initCityRequest(v.getTag().toString());
		}
	}

	private void resetAll() {
		city1.setBackgroundResource(R.drawable.oval_blue_stroke);
		city2.setBackgroundResource(R.drawable.oval_blue_stroke);
		city3.setBackgroundResource(R.drawable.oval_blue_stroke);
		city4.setBackgroundResource(R.drawable.oval_blue_stroke);
		city5.setBackgroundResource(R.drawable.oval_blue_stroke);
		city6.setBackgroundResource(R.drawable.oval_blue_stroke);
		city7.setBackgroundResource(R.drawable.oval_blue_stroke);
		city8.setBackgroundResource(R.drawable.oval_blue_stroke);
		city9.setBackgroundResource(R.drawable.oval_blue_stroke);
		city10.setBackgroundResource(R.drawable.oval_blue_stroke);
		city11.setBackgroundResource(R.drawable.oval_blue_stroke);
		city12.setBackgroundResource(R.drawable.oval_blue_stroke);
		city1.setTextColor(getActivity().getResources().getColor(R.color.color_blue));
		city2.setTextColor(getActivity().getResources().getColor(R.color.color_blue));
		city3.setTextColor(getActivity().getResources().getColor(R.color.color_blue));
		city4.setTextColor(getActivity().getResources().getColor(R.color.color_blue));
		city5.setTextColor(getActivity().getResources().getColor(R.color.color_blue));
		city6.setTextColor(getActivity().getResources().getColor(R.color.color_blue));
		city7.setTextColor(getActivity().getResources().getColor(R.color.color_blue));
		city8.setTextColor(getActivity().getResources().getColor(R.color.color_blue));
		city9.setTextColor(getActivity().getResources().getColor(R.color.color_blue));
		city10.setTextColor(getActivity().getResources().getColor(R.color.color_blue));
		city11.setTextColor(getActivity().getResources().getColor(R.color.color_blue));
		city12.setTextColor(getActivity().getResources().getColor(R.color.color_blue));
	}

	private void setChecked(TextView tv) {
		tv.setTextColor(getActivity().getResources().getColor(R.color.color_white));
		tv.setBackgroundResource(R.drawable.oval_blue);
	}

}
