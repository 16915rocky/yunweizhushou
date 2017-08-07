package com.chinamobile.yunweizhushou.ui.capitalrecorded.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.bean.RityajiImageBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.GraphListActivity2;
import com.chinamobile.yunweizhushou.ui.adapter.RechargeFunctionListAdapter;
import com.chinamobile.yunweizhushou.ui.capitalrecorded.RityajiDetailActivity;
import com.chinamobile.yunweizhushou.ui.capitalrecorded.adapters.ImageBlockAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyGridView;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class RityajiFragment2 extends BaseFragment implements OnClickListener {

	private int fkId;
	private MyListView listview;
	private MyGridView gridview;
	private TextView city1, city2, city3, city4, city5, city6, city7, city8, city9, city10, city11, city12;
	private RechargeFunctionListAdapter adapter;
	private RechargeFunctionGraphBean beans;
	private List<RityajiImageBean> imageList;
	private ImageBlockAdapter imageAdapter;
	private MyRefreshLayout refreshLayout;
	private String tag;





	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_rityaji2, container, false);
		View citys = view.findViewById(R.id.rityaji_city2);
		Bundle arguments = getArguments();
		fkId=arguments.getInt("fkId");
		initView(view);
		initCitys(citys);
		initEvent();

		return view;

	}

	private void initRequest(int fkId, String tag) {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("fkId", fkId + "");
		map.put("time","");
		map.put("region_code", tag);
		startTask(HttpRequestEnum.enum_rityaji_2_city, ConstantValueUtil.URL + "BusiFluct?", map, true);

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
			case enum_rityaji_2_city:
				Type t = new TypeToken<RechargeFunctionGraphBean>() {
				}.getType();
				beans = new Gson().fromJson(responseBean.getDATA(), t);
				adapter = new RechargeFunctionListAdapter(getActivity(), beans.getItemsList());
				listview.setAdapter(adapter);

				Type imgType = new TypeToken<List<RityajiImageBean>>() {
				}.getType();
				imageList = new Gson().fromJson(Utils.getImgArray(responseBean.getDATA()), imgType);
				imageAdapter = new ImageBlockAdapter(getActivity(), imageList, R.layout.item_image_block);
				gridview.setAdapter(imageAdapter);
				break;

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

		city1.performClick();

		refreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest(fkId, tag);
			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				if (fkId == 1054) {
					intent.setClass(getActivity(), RityajiDetailActivity.class);
					intent.putExtra("fkId", imageList.get(position).getFkId());
					intent.putExtra("title", imageList.get(position).getTitle());
				} else {
					intent.setClass(getActivity(), GraphListActivity2.class);
					intent.putExtra("extraKey", "region_channel");
					intent.putExtra("extraValue", imageList.get(position).getTitle());
					intent.putExtra("fkId", "1064");
				}
				startActivity(intent);
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
		listview = (MyListView) view.findViewById(R.id.rityaji2_listview);
		gridview = (MyGridView) view.findViewById(R.id.rityaji2_gridview);
		refreshLayout = (MyRefreshLayout) view.findViewById(R.id.rityaji2_refresh);
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
		tag = v.getTag() + "";
		initRequest(fkId, v.getTag() + "");
	}

}
