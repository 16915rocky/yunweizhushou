package com.chinamobile.yunweizhushou.ui.openCenter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.RechargeFunctionListAdapter;
import com.chinamobile.yunweizhushou.ui.businessaccept.BusinessAcceptHorizenGraphActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class OpenCenterGraphFragment extends BaseFragment {

	private ListView listview;
	private RechargeFunctionListAdapter adapter;
	private RechargeFunctionGraphBean beans;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.common_listview_notitle, container, false);

		initView(view);
		initRequest();
		initEvent();
		return view;
	}

	private void initEvent() {
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), BusinessAcceptHorizenGraphActivity.class);
				intent.putExtra("fkId", "1067");
				startActivity(intent);
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("pament_business", "");
		map.put("user_id", "");
		map.put("fkId", "1067");
		map.put("time", "6h");
		startTask(HttpRequestEnum.enum_opencenter_handle, ConstantValueUtil.URL + "BusiFluct?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean.isSuccess()) {
			Type t = new TypeToken<RechargeFunctionGraphBean>() {
			}.getType();
			beans = new Gson().fromJson(responseBean.getDATA(), t);
			adapter = new RechargeFunctionListAdapter(getActivity(), beans.getItemsList());
			listview.setAdapter(adapter);

		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}

	private void initView(View view) {
		listview = (ListView) view.findViewById(R.id.common_listview);
	}
}
