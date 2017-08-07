package com.chinamobile.yunweizhushou.ui.capes.fragments;

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
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.backlogZone.GraphListActivity;
import com.chinamobile.yunweizhushou.ui.capes.adapter.Capes1Adapter;
import com.chinamobile.yunweizhushou.ui.capes.bean.Capes1Bean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class CapesFrament1 extends BaseFragment implements OnClickListener {
	private TextView today, month;
	private ListView mListview;
	private int state;
	private List<Capes1Bean> mList;
	private static final int TODAY = 1019;
	private static final int MONTH = 1020;
	private Capes1Adapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_capes_1, container, false);
		initView(view);
		initEvent();
		initData();
		initRequest();
		return view;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", state + "");
		map.put("code", "");
		map.put("busi", "");
		map.put("cond", "");
		startTask(HttpRequestEnum.enum_capes_list1, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_capes_list1:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<Capes1Bean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new Capes1Adapter(getActivity(), mList, R.layout.item_capes_1);
				mListview.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private void initData() {
		today.performClick();
		today.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
		today.setTextColor(getActivity().getResources().getColor(R.color.color_white));
		state = TODAY;
	}

	private void initEvent() {
		today.setOnClickListener(this);
		month.setOnClickListener(this);

		mListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				if (state == TODAY) {
					intent.putExtra("fkId", (TODAY - 2) + "");
				} else if (state == MONTH) {
					intent.putExtra("fkId", (MONTH - 2) + "");
				}
				intent.putExtra("name", mList.get(position).getCode());
				intent.putExtra("userId", "");
				intent.setClass(getActivity(), GraphListActivity.class);
				getActivity().startActivity(intent);
			}
		});
	}

	private void initView(View view) {
		month = (TextView) view.findViewById(R.id.capes_1_month);
		today = (TextView) view.findViewById(R.id.capes_1_today);
		mListview = (ListView) view.findViewById(R.id.capes_1_listview);
	}

	@Override
	public void onClick(View v) {
		resetAll();
		switch (v.getId()) {
		case R.id.capes_1_month:
			if (state == TODAY) {
				state = MONTH;
				month.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
				month.setTextColor(getActivity().getResources().getColor(R.color.color_white));
				initRequest();
			}
			break;
		case R.id.capes_1_today:
			if (state == MONTH) {
				state = TODAY;
				today.setBackgroundResource(R.drawable.corner_rectangle_lightblue_bg);
				today.setTextColor(getActivity().getResources().getColor(R.color.color_white));
				initRequest();
			}
			break;

		default:
			break;
		}
	}

	private void resetAll() {
		month.setTextColor(getActivity().getResources().getColor(R.color.color_lightblue));
		today.setTextColor(getActivity().getResources().getColor(R.color.color_lightblue));
		month.setBackgroundResource(R.drawable.corner_rectangle_white_bg);
		today.setBackgroundResource(R.drawable.corner_rectangle_white_bg);
	}

}
