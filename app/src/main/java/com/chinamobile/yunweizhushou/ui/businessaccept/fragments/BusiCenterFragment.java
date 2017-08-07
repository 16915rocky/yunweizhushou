package com.chinamobile.yunweizhushou.ui.businessaccept.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.RechargeFunctionListAdapter;
import com.chinamobile.yunweizhushou.ui.businessaccept.BusinessAcceptManageActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class BusiCenterFragment extends BaseFragment implements OnClickListener, BusinessAcceptManageActivity.switch2Busi2Listener {

	private ListView listview;
	private RechargeFunctionListAdapter adapter;
	private RechargeFunctionGraphBean beans;
	private TextView item1, item2, item3, item4;
	private String tag;
	private boolean isFirst = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_busi_center, container, false);
		((BusinessAcceptManageActivity) getActivity()).setSwitch2Busi2Listener(this);
		initView(view);
		initEvent();
		return view;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "waveGraph");
		map.put("fkId", "1065");
		map.put("time", "3h");
		map.put("region_center", tag);
		// map.put("user_id", "");
		startTask(HttpRequestEnum.enum_busi_accept_center, ConstantValueUtil.URL + "BusiFluct?", map, true);
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
			case enum_busi_accept_center:
				Type t = new TypeToken<RechargeFunctionGraphBean>() {
				}.getType();
				beans = new Gson().fromJson(responseBean.getDATA(), t);
				adapter = new RechargeFunctionListAdapter(getActivity(), beans.getItemsList());
				listview.setAdapter(adapter);
				break;

			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}

	private void initEvent() {
		item1.setOnClickListener(this);
		item2.setOnClickListener(this);
		item3.setOnClickListener(this);
		item4.setOnClickListener(this);

		item1.performClick();
	}

	private void initView(View view) {
		listview = (ListView) view.findViewById(R.id.busi_center_listview);
		item1 = (TextView) view.findViewById(R.id.busi_center_item1);
		item2 = (TextView) view.findViewById(R.id.busi_center_item2);
		item3 = (TextView) view.findViewById(R.id.busi_center_item3);
		item4 = (TextView) view.findViewById(R.id.busi_center_item4);
	}

	@Override
	public void onClick(View v) {
		resetAll();
		setClickEventBackChange((TextView) v);
		tag = v.getTag() + "";
		initRequest();
	}

	private void resetAll() {
		item1.setTextColor(getResources().getColor(R.color.color_lightblue));
		item2.setTextColor(getResources().getColor(R.color.color_lightblue));
		item3.setTextColor(getResources().getColor(R.color.color_lightblue));
		item4.setTextColor(getResources().getColor(R.color.color_lightblue));
		item1.setBackgroundResource(R.drawable.corner_rectangle_blue_stroke_bg);
		item2.setBackgroundResource(R.drawable.corner_rectangle_blue_stroke_bg);
		item3.setBackgroundResource(R.drawable.corner_rectangle_blue_stroke_bg);
		item4.setBackgroundResource(R.drawable.corner_rectangle_blue_stroke_bg);
	}

	private void setClickEventBackChange(TextView tv) {
		tv.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
		tv.setTextColor(getResources().getColor(R.color.color_white));
	}

	@Override
	public void switch2Busi2() {
		if (isFirst) {
			isFirst = false;
			initRequest();
		}
	}

}
