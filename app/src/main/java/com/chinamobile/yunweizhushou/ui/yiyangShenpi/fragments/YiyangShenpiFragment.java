package com.chinamobile.yunweizhushou.ui.yiyangShenpi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.yiyangShenpi.YiyangShenpiNextActivity;
import com.chinamobile.yunweizhushou.ui.yiyangShenpi.adapter.YiyangShenpiAdapter;
import com.chinamobile.yunweizhushou.ui.yiyangShenpi.bean.YiyangShenpiBean1;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class YiyangShenpiFragment extends BaseFragment {

	private List<YiyangShenpiBean1>  mList;
	private ListView mListView;
	private YiyangShenpiAdapter mAdapter;
	private String type;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		if(arguments!=null){
			type=arguments.getString("type");
		}
		View view=inflater.inflate(R.layout.common_listview_notitle2, container, false);
		initView(view);
		initRequest();
		initEvent();
		
		return view;
	}

	private void initEvent() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent=new Intent();
				intent.putExtra("type", type);
				intent.putExtra("sendId", mList.get(position).getId());
				intent.putExtra("sendAuth_type", mList.get(position).getAuth_type());
				intent.setClass(getActivity(), YiyangShenpiNextActivity.class);
				startActivity(intent);
			}
		});
		
	}
	private void initRequest() {
		if("待审批".equals(type)){
			HashMap<String, String> map1 = new HashMap<>();
			map1.put("action", "getPendingList");
			startTask(HttpRequestEnum.enum_yiyangshenpi_unapproved, ConstantValueUtil.URL + "Approve?", map1, false);
		}else{
			HashMap<String, String> map2 = new HashMap<>();
			map2.put("action", "getApprovedList");
			startTask(HttpRequestEnum.enum_yiyangshenpi_approved, ConstantValueUtil.URL + "Approve?", map2, false);
		}
	}

	private void initView(View view) {
		mListView=(ListView) view.findViewById(R.id.common_listview);
		
	}
	
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		
		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_yiyangshenpi_unapproved:
			if (responseBean.isSuccess()) {
				Type t = new TypeToken<List<YiyangShenpiBean1>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				mAdapter = new YiyangShenpiAdapter(getActivity(), mList, R.layout.yiyangshengpi_item);
				mListView.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;
		case enum_yiyangshenpi_approved:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<YiyangShenpiBean1>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new YiyangShenpiAdapter(getActivity(), mList, R.layout.yiyangshengpi_item);
				mListView.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;
		default:
			break;
		}
		
	}

}
