package com.chinamobile.yunweizhushou.ui.autoCheck.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.autoCheck.adapter.AutoCheckUnsolveAdapter;
import com.chinamobile.yunweizhushou.ui.autoCheck.bean.AutoCheckUnsolveBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class AutoCheckTodayFragment extends BaseFragment {

	private ListView listview;
	private TextView tv;
	private AutoCheckUnsolveAdapter mAdapter;
	private List<AutoCheckUnsolveBean> bean;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.common_listview_notitle, container, false);
		initView(view);
		initRequest();
		return view;

	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getTodayOfInspection");
		startTask(HttpRequestEnum.enum_auto_check_today, ConstantValueUtil.URL + "AutomaticInspection?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_auto_check_today:
			if (responseBean.isSuccess()) {
				tv.setText(responseBean.getTOTAL());
				Type type = new TypeToken<List<AutoCheckUnsolveBean>>() {
				}.getType();
				bean = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new AutoCheckUnsolveAdapter(getActivity(), bean, R.layout.item_autocheck_today);
				listview.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private void initView(View view) {
		listview = (ListView) view.findViewById(R.id.common_listview);
		listview.setDivider(null);
		View head = LayoutInflater.from(getActivity()).inflate(R.layout.head_of_viewpager_fragment, null);
		tv = (TextView) head.findViewById(R.id.head_text);
		listview.addHeaderView(head);
	}
}
