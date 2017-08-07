package com.chinamobile.yunweizhushou.ui.capitalrecorded.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.RechargeFunctionListAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class RityajiFragment1 extends BaseFragment implements OnClickListener {

	private MyListView listview1, listview2;
	private TextView city1, city2, city3, city4, city5, city6, city7, city8, city9, city10, city11, city12;
	private RechargeFunctionListAdapter adapter, adapter2;
	private RechargeFunctionGraphBean beans, beans2;
	private ScrollView scrollView;
	private MyRefreshLayout refreshLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_rityaji1, container, false);
		View citys = view.findViewById(R.id.rityaji_city1);
		initView(view);
		initCitys(citys);
		initEvent();
		initRequest();
		return view;

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

		city1.performClick();

		refreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});
	}

	private void initCitys(View citys) {
		city1 = (TextView) citys.findViewById(R.id.busi_city_1);
		city2 = (TextView) citys.findViewById(R.id.busi_city_2);
		city3 = (TextView) citys.findViewById(R.id.busi_city_3);
		city4 = (TextView) citys.findViewById(R.id.busi_city_4);
		city5 = (TextView) citys.findViewById(R.id.busi_city_5);
		city6 = (TextView) citys.findViewById(R.id.busi_city_6);
		city7 = (TextView) citys.findViewById(R.id.busi_city_7);
		city8 = (TextView) citys.findViewById(R.id.busi_city_8);
		city9 = (TextView) citys.findViewById(R.id.busi_city_9);
		city10 = (TextView) citys.findViewById(R.id.busi_city_10);
		city11 = (TextView) citys.findViewById(R.id.busi_city_11);
		city12 = (TextView) citys.findViewById(R.id.busi_city_12);

	}

	private void initView(View view) {
		listview1 = (MyListView) view.findViewById(R.id.rityaji1_list1);
		listview2 = (MyListView) view.findViewById(R.id.rityaji1_list2);
		scrollView = (ScrollView) view.findViewById(R.id.rityaji1_scrollview);
		refreshLayout = (MyRefreshLayout) view.findViewById(R.id.rityaji1_refresh);
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

	@Override
	public void onClick(View v) {
		resetAll();
		setChecked((TextView) v);
		if (v.getId() == R.id.busi_city_1) {
			initAllRequest();
		} else {
			initCityRequest(v.getTag() + "");
		}
	}

	private void initCityRequest(String id) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("fkId", "1055");
		map.put("time", "");
		map.put("region_code", id);
		startTask(HttpRequestEnum.enum_rityaji_1_city, ConstantValueUtil.URL + "BusiFluct?", map, true);
	}

	private void initAllRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("fkId", "1053");
		map.put("time", "");
		startTask(HttpRequestEnum.enum_rityaji_1_all_citys, ConstantValueUtil.URL + "BusiFluct?", map, true);
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("fkId", "1052");
		map.put("time", "");
		startTask(HttpRequestEnum.enum_rityaji_1_total, ConstantValueUtil.URL + "BusiFluct?", map);

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
			Type t = new TypeToken<RechargeFunctionGraphBean>() {
			}.getType();
			switch (e) {
			case enum_rityaji_1_total:
				beans = new Gson().fromJson(responseBean.getDATA(), t);
				adapter = new RechargeFunctionListAdapter(getActivity(), beans.getItemsList());
				listview1.setAdapter(adapter);
				scrollView.smoothScrollTo(0, 0);
				break;
			case enum_rityaji_1_all_citys:
			case enum_rityaji_1_city:
				beans2 = new Gson().fromJson(responseBean.getDATA(), t);
				adapter2 = new RechargeFunctionListAdapter(getActivity(), beans2.getItemsList());
				listview2.setAdapter(adapter2);
				break;
			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}

	}

}
