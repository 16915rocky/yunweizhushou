package com.chinamobile.yunweizhushou.ui.logZone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.GraphListActivity2;
import com.chinamobile.yunweizhushou.ui.logZone.adapter.AbnormalLogAdapter6;
import com.chinamobile.yunweizhushou.ui.logZone.bean.LogZoneBean6;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class LogZoneFrament2 extends BaseFragment {

	private List<LogZoneBean6> mList;
	private ListView mListView;
	private AbnormalLogAdapter6 mAdapter;
	private String sysId;
	private LinearLayout lt;
	private TextView tv1;
	private String index_id;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.common_listview_notitle, container, false);
		sysId = getActivity().getIntent().getStringExtra("extraValue");
		Bundle arguments = getArguments();
		if(arguments!=null){
			index_id=arguments.getString("index_id");
		}
		initView(view);
		initEvent();
		initRequest();
		return view;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getSpecial");
		map.put("busi", sysId);
		map.put("id", "1059");

		startTask(HttpRequestEnum.enum_log_zone2, ConstantValueUtil.URL + "SpecialTreatment?", map, true);
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_log_zone2:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<LogZoneBean6>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				if (mList.size() != 0) {
					tv1.setVisibility(View.VISIBLE);
					String timeContent = mList.get(1).getInsert_time();
					tv1.setText(timeContent);
				}
				mAdapter = new AbnormalLogAdapter6(getActivity(), mList, R.layout.fragement_log_zone_table3_2);
				mListView.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;
		default:
			break;
		}
	}

	private void initEvent() {

	}

	private void initView(View view) {
		mListView = (ListView) view.findViewById(R.id.common_listview);
		tv1 = (TextView) view.findViewById(R.id.time);

		// lt=(LinearLayout) view.findViewById(R.id.common_listview_notitle_lt);
		mListView.setBackgroundColor(this.getResources().getColor(R.color.color_lightgray3));
		mListView.setDividerHeight(0);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("fkId", "1110");
				intent.putExtra("extraKey", "pament_business");
				intent.putExtra("extraValue", mList.get(position).getException_key());
				intent.putExtra("extraKey2", "region_channel");
				intent.putExtra("extraValue2", index_id);
				intent.setClass(getActivity(), GraphListActivity2.class);
				getActivity().startActivity(intent);

			}
		});

	}

}
