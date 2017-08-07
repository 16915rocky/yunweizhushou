package com.chinamobile.yunweizhushou.logZone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.GraphListActivity2;
import com.chinamobile.yunweizhushou.logZone.adapter.AbnormalLogAdapter6;
import com.chinamobile.yunweizhushou.logZone.adapter.LogZoneAdapter3;
import com.chinamobile.yunweizhushou.logZone.bean.LogZoneBean6;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogZoneFragmentOfExpandable extends BaseFragment {

	private ExpandableListView expandListview;
	// private List<String> groupList;
	private List<List<LogZoneBean6>> childList;
	private LogZoneAdapter3 mAdapter;
	private AbnormalLogAdapter6 mAdapter2;
	private String id;
	private int currentIndex;
	private List<Integer> indexList = new ArrayList<>();
	private String sysId;
	private List<LogZoneBean6> groupList;
	private String ips;
	private int num = 3;
	private TextView tv1;
	private int type = 0;
	private int pos = 0;
	private int poss = 999;
	private String index_id;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		sysId = getActivity().getIntent().getStringExtra("extraValue");
		View view = inflater.inflate(R.layout.common_expandable_listview_notitle, container, false);
		Bundle arguments = getArguments();
		if(arguments!=null){
			index_id=arguments.getString("index_id");
		}
		initView(view);
		initData();
		initrequest(0);
		initEvent();
		return view;
	}

	private void initEvent() {

		expandListview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				if (expandListview.isGroupExpanded(groupPosition)) { // 监听父item是不是展开的
					expandListview.collapseGroup(groupPosition);
				} else {
					ips = groupList.get(groupPosition).getName();
					Log.i("ip", ips);
					// for (int i = 0; i < 2; i++) {
					// LogZoneBean6 ln = new LogZoneBean6();
					// ln.setDoc_count(i+"");
					// ln.setName(i+"");
					// ArrayList<LogZoneBean6> list=new
					// ArrayList<LogZoneBean6>();
					// list.add(ln);
					// childList.add(list);
					// }
					// mAdapter.notifyDataSetChanged();
					// expandListview.expandGroup(groupPosition);
					for (int i = 0; i < indexList.size(); i++) {
						if (groupPosition == indexList.get(i)) {
							return false;
						}
					}
					currentIndex = groupPosition;
					indexList.add(groupPosition);
					initrequest(1);

				}

				return false;
			}
		});
		expandListview.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
					long id) {
				Intent intent = new Intent();
				intent.putExtra("fkId", "1111");
				intent.putExtra("extraKey", "pament_business");
				intent.putExtra("extraValue", childList.get(groupPosition).get(childPosition).getName());
				intent.putExtra("extraKey2", "region_channel");
				intent.putExtra("extraValue2", index_id);
				intent.setClass(getActivity(), GraphListActivity2.class);
				getActivity().startActivity(intent);
				return false;
			}
		});

		// if(poss!=999){
		// initrequest(1);
		// if(expandListview.isGroupExpanded(poss)){ //监听父item是不是展开的
		// expandListview.collapseGroup(poss);
		// }else{
		// ips=groupList.get(poss).getName();
		// Log.i("ip", ips);
		// addDate();
		//
		// mAdapter.notifyDataSetChanged();
		// expandListview.expandGroup(poss);
		// }
		// }

	}

	private void initData() {

		childList = new ArrayList<>();

		for (int i = 0; i < 20; i++) {
			childList.add(new ArrayList<LogZoneBean6>());
		}
	}

	private void initrequest(int type) {
		if (type == 0) {
			HashMap<String, String> map = new HashMap<>();
			map.put("action", "getListOfAbnormalLog");
			map.put("sysId", sysId);
			startTask(HttpRequestEnum.enum_log_zone3, ConstantValueUtil.URL + "SpecialTreatment?", map);
		} else {
			HashMap<String, String> map2 = new HashMap<>();
			map2.put("action", "getListOfProcess");
			map2.put("sysId", sysId);
			map2.put("ip", ips);
			startTask(HttpRequestEnum.enum_log_zone4, ConstantValueUtil.URL + "SpecialTreatment?", map2);
		}

	}

	private void initView(View view) {
		expandListview = (ExpandableListView) view.findViewById(R.id.common_expandable_listview_notitle);
		expandListview.setBackgroundColor(this.getResources().getColor(R.color.color_lightgray3));
		tv1 = (TextView) view.findViewById(R.id.time);
		expandListview.setDividerHeight(0);
		expandListview.setGroupIndicator(null);// 控件默认的左边箭头去掉

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {

		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_log_zone3:
			if (responseBean.isSuccess()) {
				Type t1 = new TypeToken<List<LogZoneBean6>>() {
				}.getType();
				groupList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t1);
				if (groupList.size() != 0) {
					String timeContent = groupList.get(0).getInsert_time();
					tv1.setText(timeContent);
					tv1.setVisibility(View.VISIBLE);
				}
				// childList=new ArrayList<List<LogZoneBean6>>();
				mAdapter = new LogZoneAdapter3(getActivity(), groupList, childList);
				expandListview.setAdapter(mAdapter);

			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;
		case enum_log_zone4:
			if (responseBean.isSuccess()) {
				Type t2 = new TypeToken<List<LogZoneBean6>>() {
				}.getType();
				// childList.clear();
				List<LogZoneBean6> itemList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t2);
				childList.remove(currentIndex);
				childList.add(currentIndex, itemList);
				if (mAdapter == null) {
					mAdapter = new LogZoneAdapter3(getActivity(), groupList, childList);
					expandListview.setAdapter(mAdapter);
				} else {
					mAdapter.notifyDataSetChanged();
				}

			}
		default:
			break;
		}

	}

}
