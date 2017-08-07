package com.chinamobile.yunweizhushou.ui.fault.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.FaultUnsolveBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.SwipeListViewOnScrollListener;
import com.chinamobile.yunweizhushou.ui.adapter.FaultUnsolveAdapter;
import com.chinamobile.yunweizhushou.ui.fault.FaultManageActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class FaultUnsolveFragment extends BaseFragment implements FaultManageActivity.SwitchToUnsolvePagerListener {

	private ListView listView;
	private List<FaultUnsolveBean> dataList;
	private FaultUnsolveAdapter mAdapter;
	private MyRefreshLayout mRefreshLayout;
	private ImageView noError;
	private boolean isFirst = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fault_unsolve, container, false);
		initView(view);
		initEvent();
		((FaultManageActivity) getActivity()).setOnSwicthToUnsolveListener(this);
		return view;
	}

	@Override
	public void switchToUnsolve() {
		if (isFirst) {
			isFirst = false;
			initRequest();
		}
	}

	private void initEvent() {
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});

		listView.setOnScrollListener(new SwipeListViewOnScrollListener(mRefreshLayout));
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("action", "left");
		startTask(HttpRequestEnum.enum_faultmanage_unsolve, ConstantValueUtil.URL + "Broadcast?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (mRefreshLayout.isShown()) {
			mRefreshLayout.setRefreshing(false);
		}
		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_faultmanage_unsolve:
			if (responseBean.isSuccess()) {
				listView.setVisibility(View.VISIBLE);
				noError.setVisibility(View.GONE);
				String data = Utils.getJsonArray(responseBean.getDATA());
				Type type = new TypeToken<List<FaultUnsolveBean>>() {
				}.getType();
				dataList = new Gson().fromJson(data, type);
				mAdapter = new FaultUnsolveAdapter(getActivity(), dataList, R.layout.item_fault_unsolve);
				listView.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
				listView.setVisibility(View.GONE);
				noError.setVisibility(View.VISIBLE);
			}
			break;
		default:
			break;
		}
	}

	private void initView(View view) {
		listView = (ListView) view.findViewById(R.id.fault_unsolve_listview);
		noError = (ImageView) view.findViewById(R.id.frame_no_error);
		mRefreshLayout = (MyRefreshLayout) view.findViewById(R.id.fault_unsolve_swipe_refresh);
	}

}
