package com.chinamobile.yunweizhushou.ui.netChange;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.netChange.adapter.OnlineChangeAdapter2;
import com.chinamobile.yunweizhushou.ui.netChange.bean.OnlineChangeBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class OnlineChangeActivity extends BaseActivity {

	private ListView listview;
	private List<OnlineChangeBean> list;
	private TextView headView;
	private FrameLayout framelayout;
	// private OnlineChangeAdapter1 mAdapter;
	private OnlineChangeAdapter2 mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.common_listview);
		initView();
		initEvent();
		initReqest();
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if (arg0 == 1 && arg1 == RESULT_OK) {
			initReqest();
		}
	}

	private void initEvent() {

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(OnlineChangeActivity.this, NetChangeChangeActivity.class);
				intent.putExtra("id", list.get(position).getId());
				intent.putExtra("name", list.get(position).getName());
				startActivityForResult(intent, 1);
			}
		});

		getTitleBar().setMiddleText("上线变更单");

		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initReqest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "checklist");
		map.put("type", getIntent().getStringExtra("type"));
		map.put("title", getIntent().getStringExtra("title"));
		startTask(HttpRequestEnum.enum_net_change_change_list, ConstantValueUtil.URL + "ChangeTask?", map);

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_net_change_change_list:
			if (responseBean.isSuccess()) {
				Type t = new TypeToken<List<OnlineChangeBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				if (mAdapter == null) {
					mAdapter = new OnlineChangeAdapter2(this, list, R.layout.fragment_net_change_online_1_4);
					listview.setAdapter(mAdapter);
					headView.setText(list.get(0).getName());
				} else {
					mAdapter.setData(list);
					mAdapter.notifyDataSetChanged();
				}
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private void initView() {

		headView = new TextView(OnlineChangeActivity.this);
		headView.setGravity(Gravity.CENTER);
		headView.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		headView.setPadding(10, 10, 10, 10);
		framelayout = (FrameLayout) findViewById(R.id.common_frame);
		listview = (ListView) findViewById(R.id.common_listview);
		framelayout.addView(headView);

	}

}
