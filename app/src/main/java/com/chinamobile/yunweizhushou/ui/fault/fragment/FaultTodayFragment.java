package com.chinamobile.yunweizhushou.ui.fault.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.FaultTodayBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.SwipeListViewOnScrollListener;
import com.chinamobile.yunweizhushou.ui.adapter.FaultTodayNewAdapter;
import com.chinamobile.yunweizhushou.ui.fault.FaultTodayDetailActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FaultTodayFragment extends BaseFragment {

	private ListView mListView;
	private List<FaultTodayBean> dataList = new ArrayList<>();
	// private FaultTodayAdapter mAdapter;
	private FaultTodayNewAdapter mAdapter;
	private JSONArray mJsonArray;
	// private ImageView noError;
	private MyRefreshLayout myRefreshLayout;
	public static final String FAULT_TODAY_DETAIL = "fault_today_detail";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fault_today, container, false);
		initView(view);
		initRequest();
		initEvent();
		initHeadRequest();
		myRefreshLayout.setRefreshing(true);
		return view;
	}

	private void initHeadRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "summary");
		startTask(HttpRequestEnum.enum_faultmanage_today_head, ConstantValueUtil.URL + "Trouble?", map);
	}

	private void initEvent() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0) {
					return;
				}
				Intent intent = new Intent();
				intent.setClass(getActivity(), FaultTodayDetailActivity.class);
				// intent.putExtra("bean", dataList.get(position-1));
				intent.putExtra("id", dataList.get(position - 1).getId());
				startActivity(intent);
			}
		});

		mListView.setOnScrollListener(new SwipeListViewOnScrollListener(myRefreshLayout));

		myRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("action", "today");
		startTask(HttpRequestEnum.enum_faultmanage_today, ConstantValueUtil.URL + "Broadcast?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (myRefreshLayout.isShown()) {
			myRefreshLayout.setRefreshing(false);
		}
		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_faultmanage_today_head:

			if (responseBean.isSuccess()) {
				String data = responseBean.getDATA();
				String content = "";
				try {
					JSONObject obj = new JSONObject(data);
					if (obj.has("TOTAL")) {
						content = obj.getString("TOTAL");
					}
					View head = LayoutInflater.from(getActivity()).inflate(R.layout.head_of_viewpager_fragment, null);
					TextView tv = (TextView) head.findViewById(R.id.head_text);
					tv.setText(content);
					mListView.addHeaderView(head);
					if (mAdapter == null) {
						mAdapter = new FaultTodayNewAdapter(getActivity(), dataList, R.layout.item_fault_today_new);
						mListView.setAdapter(mAdapter);
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

			}

			break;
		case enum_faultmanage_today:
			if (responseBean.isSuccess()) {
				String data = "";
				try {
					mJsonArray = new JSONObject(responseBean.getDATA()).getJSONArray("itemsList");
					data = mJsonArray.toString();
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				// mListView.setVisibility(View.VISIBLE);
				// noError.setVisibility(View.GONE);
				Type type = new TypeToken<List<FaultTodayBean>>() {
				}.getType();
				dataList = new Gson().fromJson(data, type);
				mAdapter = new FaultTodayNewAdapter(getActivity(), dataList, R.layout.item_fault_today_new);
				mListView.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
				// mListView.setVisibility(View.GONE);
				// noError.setVisibility(View.VISIBLE);
			}
			break;
		default:
			break;
		}
	}

	private void initView(View view) {
		myRefreshLayout = (MyRefreshLayout) view.findViewById(R.id.fault_today_swipe_refresh);
		mListView = (ListView) view.findViewById(R.id.fault_today_listview);
		// noError = (ImageView) view.findViewById(R.id.frame_no_error);
	}

}
