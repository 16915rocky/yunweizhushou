package com.chinamobile.yunweizhushou.ui.hotZoneKQBK.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.hotZoneKQBK.adapter.RechargeFunctionListShadowAdapter;
import com.chinamobile.yunweizhushou.ui.hotZoneKQBK.bean.GraphBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class GraphListwithShadowFragment extends BaseFragment {

	private ListView listview;
	private String fkId;
	private RechargeFunctionListShadowAdapter adapter;
	private List<GraphBean> mList;
	private LinearLayout titleLt;
	private String action;
	
	 public GraphListwithShadowFragment() {
		
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.common_listview, container, false);
		Bundle arguments = getArguments();
		if(arguments!=null){
			action=arguments.getString("action");
			fkId=arguments.getString("fkId");

		}
		initView(view);
		initRequest();
		return view;
	}

	
	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", action);
		startTask(HttpRequestEnum.enum_govern_analysis_successrate_graph_list, ConstantValueUtil.URL + "HotZone?",
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
		case enum_govern_analysis_successrate_graph_list:
			if (responseBean.isSuccess()) {
				Type t = new TypeToken<List<GraphBean>>() {
				}.getType();
				mList = new Gson().fromJson(responseBean.getDATA(), t);
				adapter = new RechargeFunctionListShadowAdapter(getActivity(), mList);
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
