package com.chinamobile.yunweizhushou.ui.orderCenter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class OrderCenterBroadbandFragment extends BaseFragment implements OnClickListener {

	private TextView tv1, tv2;
	private ListView myListView;
	private RechargeFunctionGraphBean beans2;
	private RechargeFunctionListAdapter adapter2;
	private int selected = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_ordercenter_broadband, container, false);
		initView(view);
		initEvent();
		initRequest(0);
		return view;

	}

	private void initRequest(int id) {
		HashMap<String, String> map = new HashMap<>();
		if (id == 0) {
			map.put("action", "waveGraph");
			map.put("time", "6h");
			map.put("region_code", "");
			map.put("pament_business", "");
			map.put("fkId", "1086");
			startTask(HttpRequestEnum.enum_order_broadband, ConstantValueUtil.URL + "BusiFluct?", map, true);
		} else if (id == 1) {
			map.put("action", "waveGraph");
			map.put("time", "6h");
			map.put("region_code", "");
			map.put("pament_business", "");
			map.put("fkId", "1087");
			startTask(HttpRequestEnum.enum_order_broadband, ConstantValueUtil.URL + "BusiFluct?", map, true);
		}

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_order_broadband:
				Type t2 = new TypeToken<RechargeFunctionGraphBean>() {
				}.getType();
				beans2 = new Gson().fromJson(responseBean.getDATA(), t2);
				adapter2 = new RechargeFunctionListAdapter(getActivity(), beans2.getItemsList());
				myListView.setAdapter(adapter2);
				// myListView.setOnItemClickListener(new
				// GraphListItemOnClickListener(beans2.getItemsList()));

			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}

	private void initEvent() {
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);

		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), BusinessAcceptHorizenGraphActivity.class);
				if (selected == 0) {
					intent.putExtra("fkId", "1086");
				} else {
					intent.putExtra("fkId", "1087");
				}
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});
	}

	private void initView(View view) {
		tv1 = (TextView) view.findViewById(R.id.tv1);
		tv2 = (TextView) view.findViewById(R.id.tv2);
		myListView = (ListView) view.findViewById(R.id.myListView);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv1:
			selected = 0;
			initRequest(selected);
			break;
		case R.id.tv2:
			selected = 1;
			initRequest(selected);
			break;

		default:
			break;
		}
	}

}
