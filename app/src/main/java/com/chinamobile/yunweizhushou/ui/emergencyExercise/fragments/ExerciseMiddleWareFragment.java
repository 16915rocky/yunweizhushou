package com.chinamobile.yunweizhushou.ui.emergencyExercise.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.SwipeListViewOnScrollListener;
import com.chinamobile.yunweizhushou.ui.emergencyExercise.TrillDetailActivity;
import com.chinamobile.yunweizhushou.ui.emergencyExercise.adapter.ExerciseMiddleWareAdapter;
import com.chinamobile.yunweizhushou.ui.emergencyExercise.bean.EmergencyExerciseBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExerciseMiddleWareFragment extends BaseFragment {

	private View rootView;
	private MyRefreshLayout mRefreshLayout;
	private ListView mListView;
	private ExerciseMiddleWareAdapter adapter;
	private List<EmergencyExerciseBean> data;
	private String title;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		title = arguments.getString("title");
		rootView = inflater.inflate(R.layout.fragment_exercise_middle_ware, container, false);
		initView();
		setEvent();
		initRequest();
		mRefreshLayout.setRefreshing(true);
		return rootView;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getDisasterDrill");
		map.put("drillType", title);
		startTask(HttpRequestEnum.enum_emergency_exercise, ConstantValueUtil.URL + "DisasterDrill?", map);
	}

	private void initView() {
		mRefreshLayout = (MyRefreshLayout) rootView.findViewById(R.id.middleRefreshLayout);
		mListView = (ListView) rootView.findViewById(R.id.middleListView);
	}

	private void setEvent() {
		mListView.setOnScrollListener(new SwipeListViewOnScrollListener(mRefreshLayout));
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(getActivity(), TrillDetailActivity.class);
				intent.putExtra("drillId", data.get(position).getDrillId());
				startActivity(intent);
			}
		});

		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (mRefreshLayout.isShown()) {
			mRefreshLayout.setRefreshing(false);
		}

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_emergency_exercise:
				if (data == null) {
					data = new ArrayList<>();
				}
				data.clear();
				Type type = new TypeToken<List<EmergencyExerciseBean>>() {
				}.getType();
				data = new Gson().fromJson(responseBean.getDATA(), type);
				adapter = new ExerciseMiddleWareAdapter(getActivity(), data);
				mListView.setAdapter(adapter);
				break;
			}
		}
	}
}
