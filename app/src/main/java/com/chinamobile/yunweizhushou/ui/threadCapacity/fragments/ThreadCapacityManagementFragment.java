package com.chinamobile.yunweizhushou.ui.threadCapacity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.SwipeListViewOnScrollListener;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.DBConnectionPoolDetailActivity;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.adapter.DBConnectionPoolAdapter;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.adapter.DBConnectionPoolBallAdapter;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.bean.DBConnectionPoolBallBean;
import com.chinamobile.yunweizhushou.ui.bdConnectionPool.bean.DBConnectionPoolBean;
import com.chinamobile.yunweizhushou.ui.esbInterface.WaterBallGraphActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyGridView;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class ThreadCapacityManagementFragment extends BaseFragment {

	private DBConnectionPoolBean bean;
	private DBConnectionPoolBallBean ballBean;
	private MyListView mListView;
	private MyGridView mGridView;

	private TextView tvNormal;
	private TextView tvFoucus;
	private TextView tvOptimize;
	private TextView tvContentShow;

	private DBConnectionPoolAdapter adapter;
	private DBConnectionPoolBallAdapter gridAdapter;
	private ScrollView scrollView;

	private MyRefreshLayout mRefreshLayout;
	private View title;
	private LinearLayout item_charge_people;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_db_connection_pool, container, false);
		initView(view);
		setEvent();
		initRequest();
		return view;
	}

	private void initView(View v) {
		item_charge_people= (LinearLayout) v.findViewById(R.id.item_charge_people);
		item_charge_people.setVisibility(View.GONE);
		scrollView = (ScrollView) v.findViewById(R.id.scrollView);
		mListView = (MyListView) v.findViewById(R.id.dbConnectionListView);
		mGridView = (MyGridView) v.findViewById(R.id.dbFocusGridView);
		tvNormal = (TextView) v.findViewById(R.id.dbConnectionNormal);
		tvFoucus = (TextView) v.findViewById(R.id.dbConnectionFocus);
		tvOptimize = (TextView) v.findViewById(R.id.dbConnectionOptimize);
		tvContentShow = (TextView) v.findViewById(R.id.contentShow);

		mRefreshLayout = (MyRefreshLayout) v.findViewById(R.id.dbConnectionRefreshLayout);
		title = v.findViewById(R.id.title);
		title.setVisibility(View.GONE);

		mListView.setFocusable(false);
	}

	private void setEvent() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), DBConnectionPoolDetailActivity.class);
				intent.putExtra("fkId", "1012");
				intent.putExtra("title", bean.getItemsList().get(position).getName());
				intent.putExtra("id", "1012");
				intent.putExtra("busi", bean.getItemsList().get(position).getName());
				startActivity(intent);
			}
		});
		mListView.setOnScrollListener(new SwipeListViewOnScrollListener(mRefreshLayout));
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});

		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), WaterBallGraphActivity.class);
				intent.putExtra("name", ballBean.getItemsList().get(position).getName());
				intent.putExtra("fkId", "1012");
				intent.putExtra("userId", ballBean.getItemsList().get(position).getCode());
				startActivity(intent);
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("id", "1010");
		startTask(HttpRequestEnum.enum_thread_capacity_management, ConstantValueUtil.URL + "SpecialTreatment?", map,
				true);

		HashMap<String, String> map2 = new HashMap<>();
		map2.put("action", "getSpecial");
		map2.put("id", "1011");
		startTask(HttpRequestEnum.enum_thread_capacity_management_ball, ConstantValueUtil.URL + "SpecialTreatment?",
				map2);
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
			case enum_thread_capacity_management:
				Type type = new TypeToken<DBConnectionPoolBean>() {
				}.getType();
				bean = new Gson().fromJson(responseBean.getDATA(), type);

				tvNormal.setText("" + bean.getNormal());
				tvFoucus.setText("" + bean.getFocus());
				tvOptimize.setText("" + bean.getOptimize());
				adapter = new DBConnectionPoolAdapter(getActivity(), bean.getItemsList());
				mListView.setAdapter(adapter);

				scrollView.smoothScrollTo(0, 0);
				break;
			case enum_thread_capacity_management_ball:
				Type type2 = new TypeToken<DBConnectionPoolBallBean>() {
				}.getType();
				ballBean = new Gson().fromJson(responseBean.getDATA(), type2);

				if (ballBean.getItemsList().size() > 0) {
					tvContentShow.setVisibility(View.GONE);
				}
				gridAdapter = new DBConnectionPoolBallAdapter(getActivity(), ballBean.getItemsList());
				mGridView.setAdapter(gridAdapter);
				scrollView.smoothScrollTo(0, 0);
				break;
			}
		} else {
			tvContentShow.setVisibility(View.VISIBLE);
		}
	}

}
