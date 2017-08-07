package com.chinamobile.yunweizhushou.ui.threadCapacity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.SwipeListViewOnScrollListener;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.adapter.DBConnectionPoolBallAdapter;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.bean.DBConnectionPoolBallBean;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.bean.DBConnectionStatisticsBean;
import com.chinamobile.yunweizhushou.ui.esbInterface.WaterBallGraphActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyGridView;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class OtherThreadCapacityFragment extends BaseFragment {

	private MyGridView mGridView;
	private TextView normal, focus, optimize;
	private DBConnectionPoolBallBean ballBean;
	private DBConnectionStatisticsBean statisticsBean;// 球类型统计
	private DBConnectionPoolBallAdapter adapter;
	private ScrollView scrollView;
	private MyRefreshLayout mRefreshLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_other_thread_capacity, container, false);
		initView(view);
		initRequest();
		initEvent();
		return view;
	}

	private void initView(View view) {
		mGridView = (MyGridView) view.findViewById(R.id.dbDetailGridView);
		scrollView = (ScrollView) view.findViewById(R.id.detailScrollView);
		normal = (TextView) view.findViewById(R.id.detailNormal);
		focus = (TextView) view.findViewById(R.id.detailFocus);
		optimize = (TextView) view.findViewById(R.id.detailOptimize);
		mRefreshLayout = (MyRefreshLayout) view.findViewById(R.id.dbConnectionRefreshLayout);
		mGridView.setFocusable(false);
	}

	private void initEvent() {
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), WaterBallGraphActivity.class);
				// intent.setClass(getActivity(), GraphListActivity.class);
				intent.putExtra("name", ballBean.getItemsList().get(position).getName());
				intent.putExtra("userId", ballBean.getItemsList().get(position).getCode() + "");
				intent.putExtra("fkId", "1013");
				startActivity(intent);
			}
		});
		mGridView.setOnScrollListener(new SwipeListViewOnScrollListener(mRefreshLayout));
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});
	}

	private void initRequest() {
		// 球型列表
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", "1014");
		startTask(HttpRequestEnum.enum_pool_analysis_ball, ConstantValueUtil.URL + "SpecialTreatment?", map);

		// 统计数 3种状态
		HashMap<String, String> map2 = new HashMap<>();
		map2.put("action", "getSpecial");
		map2.put("id", "1013");
		startTask(HttpRequestEnum.enum_pool_analysis_statistics, ConstantValueUtil.URL + "SpecialTreatment?", map2);
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
			case enum_pool_analysis_ball:
				Type type2 = new TypeToken<DBConnectionPoolBallBean>() {
				}.getType();
				ballBean = new Gson().fromJson(responseBean.getDATA(), type2);
				adapter = new DBConnectionPoolBallAdapter(getActivity(), ballBean.getItemsList(), "otherThread");
				mGridView.setAdapter(adapter);
				scrollView.smoothScrollTo(0, 0);
				break;
			case enum_pool_analysis_statistics:
				Type type = new TypeToken<DBConnectionStatisticsBean>() {
				}.getType();
				statisticsBean = new Gson().fromJson(responseBean.getDATA(), type);
				normal.setText(statisticsBean.getNormal());
				focus.setText(statisticsBean.getWarning());
				optimize.setText(statisticsBean.getOptimize());
				break;
			}
		} else {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
		}
	}
}
