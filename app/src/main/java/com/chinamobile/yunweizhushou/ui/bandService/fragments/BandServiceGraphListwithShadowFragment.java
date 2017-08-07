package com.chinamobile.yunweizhushou.ui.bandService.fragments;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BandServiceGraphListwithShadowFragment extends BaseFragment {

	private ListView listview;
	private String fkId;
	private RechargeFunctionListShadowAdapter adapter;
	private GraphBean backRateBean,workedRateBean,orderRateBean;
	private List<GraphBean> mList;
	private LinearLayout titleLt;
	private String action;


	 public BandServiceGraphListwithShadowFragment() {
		
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.common_listview, container, false);
		initView(view);
		initRequest();
		return view;
	}

	
	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "findRelatedRate");
		startTask(HttpRequestEnum.enum_govern_analysis_successrate_graph_list, ConstantValueUtil.URL + "BroadBand?",
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
				try {
					JSONObject jo=new JSONObject(responseBean.getDATA());
					String backRate=jo.getString("backRate");
					String workedRate=jo.getString("workedRate");
					String orderRate=jo.getString("orderRate");
					Type t = new TypeToken<GraphBean>() {
					}.getType();
					backRateBean = new Gson().fromJson(backRate, t);
					workedRateBean = new Gson().fromJson(workedRate, t);
					orderRateBean = new Gson().fromJson(orderRate, t);
					mList=new ArrayList<GraphBean>();
					mList.add(backRateBean);
					mList.add(workedRateBean);
					mList.add(orderRateBean);
					adapter = new RechargeFunctionListShadowAdapter(getActivity(), mList);
					listview.setAdapter(adapter);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
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
