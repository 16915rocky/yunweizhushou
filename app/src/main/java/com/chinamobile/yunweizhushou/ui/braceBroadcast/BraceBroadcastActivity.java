package com.chinamobile.yunweizhushou.ui.braceBroadcast;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.BraceBroadcastBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.BraceBroadcastAdapter;
import com.chinamobile.yunweizhushou.ui.fault.CalendarActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class BraceBroadcastActivity extends BaseActivity {

	private List<BraceBroadcastBean> list;
	private ListView listview;
	private BraceBroadcastAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bracebroadcast);

		initEvent();
		initRequest();

		listview = (ListView) findViewById(R.id.bracebroadcast_list);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			HashMap<String, String> map = new HashMap<>();
			map.put("action", "bomcNumber");
			map.put("id", (String) msg.obj);
			startTask(HttpRequestEnum.enum_bracebroadcast_addread, ConstantValueUtil.URL + "Broadcast?", map);
		};
	};

	private void initRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		// map.put("action", "listVo");
		map.put("action", "listBroad");
		startTask(HttpRequestEnum.enum_bracebroadcast, ConstantValueUtil.URL + "Broadcast?", map);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_bracebroadcast:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<BraceBroadcastBean>>() {
				}.getType();
				list = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new BraceBroadcastAdapter(this, list, R.layout.item_bracebraodcast, mHandler, false);
				listview.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private void initEvent() {
		getTitleBar().setMiddleText("支撑播报");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		getTitleBar().setRightButton1(R.mipmap.icon_brace_timeasix, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(BraceBroadcastActivity.this, CalendarActivity.class);
				i.putExtra("type", CalendarActivity.TYPE_BROADCAST);
				startActivity(i);
			}
		});
	}
}
