package com.chinamobile.yunweizhushou.ui.useRank.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.useRank.adapter.UseRankingAdapter;
import com.chinamobile.yunweizhushou.ui.useRank.bean.UseRankingBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class UseRankingFragment extends BaseFragment {

	private View rootView;
	private ListView mListView;

	private String action;

	private UseRankingAdapter adapter;
	private UseRankingBean bean;
	private int id;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle arguments = getArguments();
		if(arguments!=null) {
			action = arguments.getString("action");
			id = arguments.getInt("id");
		}
		rootView = inflater.inflate(R.layout.fragment_use_ranking, container, false);
		initView();
		initRequest();
		return rootView;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", action);
		startTask(HttpRequestEnum.enum_use_ranking, ConstantValueUtil.URL + "HotSpotRankings?", map);
	}

	private void initView() {
		mListView = (ListView) rootView.findViewById(R.id.rankingListView);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_use_ranking:
				Type type = new TypeToken<UseRankingBean>() {
				}.getType();
				bean = new Gson().fromJson(responseBean.getDATA(), type);
				adapter = new UseRankingAdapter(getActivity(), bean.getItemsList(), id);
				mListView.setAdapter(adapter);
				break;
			}
		}
	}
}
