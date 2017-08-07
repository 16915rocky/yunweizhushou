package com.chinamobile.yunweizhushou.ui.esbInterface.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import com.chinamobile.yunweizhushou.ui.esbInterface.WaterBallHorizenGraphActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class GraphListFragment extends BaseFragment {

	private ListView listview;
	private String name, fkId, userId;
	private RechargeFunctionListAdapter adapter;
	private RechargeFunctionGraphBean beans;
	private LinearLayout  title;

	private String defaultKey = "pament_business";
	
	public GraphListFragment(){
		
	}

	// private String defaultKey2="region_channel";
	// private String key2Value="";
	
	

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.common_listview);
//	
//		/*
//		 * if(getIntent().hasExtra("Key2")){
//		 * defaultKey2=getIntent().getStringExtra("Key2");
//		 * if(getIntent().getStringExtra("Value2")!=null){
//		 * key2Value=getIntent().getStringExtra("Value2"); } }
//		 */
//		initView();
//		initEvent();
//		initRequest();
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view=inflater.inflate(R.layout.common_listview, container, false);
		name = getActivity().getIntent().getStringExtra("name");
		userId = getActivity().getIntent().getStringExtra("userId");
		Bundle arguments = getArguments();
		if(arguments!=null) {
			fkId = arguments.getString("fkId");
			name = arguments.getString("name");
		}
		if (getActivity().getIntent().hasExtra("extraKey")) {
			defaultKey = getActivity().getIntent().getStringExtra("extraKey");
		}
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
				intent.setClass(getActivity(), WaterBallHorizenGraphActivity.class);
				intent.putExtra("name", name);
				intent.putExtra("fkId", fkId);
				intent.putExtra("userId", userId);
				intent.putExtra("waveId", beans.getItemsList().get(position).getWaveId());
				startActivity(intent);
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("fkId", fkId);
		map.put(defaultKey, name);
		// map.put(defaultKey2, key2Value);
		map.put("time", "6h");
		map.put("user_id", userId);
		if ("1024".equals(fkId)) {// 积压专区图表
			map.put("user_id", userId);
		}
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
		listview =  (ListView) view.findViewById(R.id.common_listview);
		title =  (LinearLayout) view.findViewById(R.id.titleid);
		title.setVisibility(View.GONE);
	}
}
