package com.chinamobile.yunweizhushou.logZone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
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

public class GraphListFragment2 extends BaseFragment {

	private ListView listview;
	private String fkId;
	private RechargeFunctionListAdapter adapter;
	private RechargeFunctionGraphBean beans;
	private LinearLayout titleLt;

	private String extraKey = "", extraValue = "";
	private String extraKey2 = "", extraValue2 = "";
	 public GraphListFragment2() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.common_listview, container, false);

		Bundle arguments = getArguments();
		if(arguments!=null){
			fkId=arguments.getString("fkId");
		}
		extraKey = getActivity().getIntent().getStringExtra("extraKey");
		extraValue = getActivity().getIntent().getStringExtra("extraValue");
		extraKey2 = getActivity().getIntent().getStringExtra("extraKey2");
		extraValue2 = getActivity().getIntent().getStringExtra("extraValue2");

		initView(view);
		initEvent();
		initRequest();
		return view;
	}

	private void initEvent() {

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), BusinessAcceptHorizenGraphActivity.class);
				intent.putExtra("extraKey", extraKey);
				intent.putExtra("extraValue", extraValue);
				intent.putExtra("extraKey2", extraKey2);
				intent.putExtra("extraValue2", extraValue2);
				intent.putExtra("fkId", fkId);
				intent.putExtra("position", position);
				// Bundle bundle = new Bundle();
				// bundle.putInt("position", position);
				// intent.putExtra("bundle", bundle);
				startActivity(intent);
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("fkId", fkId);
		if (!TextUtils.isEmpty(extraKey)) {
			map.put(extraKey, extraValue);
		}
		if (!TextUtils.isEmpty(extraKey2)) {
			map.put(extraKey2, extraValue2);
		}
		map.put("time", "6h");
		startTask(HttpRequestEnum.enum_govern_analysis_successrate_graph_lists, ConstantValueUtil.URL + "BusiFluct?",
				map, true);

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_govern_analysis_successrate_graph_lists:
			if (responseBean.isSuccess()) {
				Type t = new TypeToken<RechargeFunctionGraphBean>() {
				}.getType();
				beans = new Gson().fromJson(responseBean.getDATA(), t);
				adapter = new RechargeFunctionListAdapter(getActivity(), beans.getItemsList());
				listview.setAdapter(adapter);

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
		titleLt = (LinearLayout) view.findViewById(R.id.titleid);
		titleLt.setVisibility(View.GONE);
	}

}
