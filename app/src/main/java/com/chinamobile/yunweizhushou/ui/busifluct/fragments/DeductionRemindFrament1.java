package com.chinamobile.yunweizhushou.ui.busifluct.fragments;

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
import com.chinamobile.yunweizhushou.ui.busifluct.adapter.DeductionRemind1Adapter;
import com.chinamobile.yunweizhushou.ui.busifluct.adapter.DeductionRemind2Adapter;
import com.chinamobile.yunweizhushou.ui.busifluct.bean.DeductionRemindBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class DeductionRemindFrament1 extends BaseFragment {

	private ListView mListview;
	private List<DeductionRemindBean> mList;
	private DeductionRemind1Adapter mAdapter1;
	private DeductionRemind2Adapter mAdapter2;
	private TextView tv1, tv2, tv3, tv4;
	private String tag;
	private LinearLayout head;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		tag=arguments.getString("tag",tag);
		View view = inflater.inflate(R.layout.common_list_4, container, false);
		initView(view);
		initEvent();
		initRequest();
		return view;
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", tag);
		startTask(HttpRequestEnum.enum_deduction_remind_backlog, ConstantValueUtil.URL + "Deduction?", map, true);

	}

	private void initEvent() {
		mListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), GraphListActivity2.class);
				intent.putExtra("fkId", tag.equals("backlog") ? "1081" : "1082");
				intent.putExtra("extraKey", "pament_business");
				intent.putExtra("extraValue",
						tag.equals("backlog") ? mList.get(position).getTab() : mList.get(position).getBusi());
				intent.putExtra("extraKey2", "region_channel");
				intent.putExtra("extraValue2",
						tag.equals("backlog") ? mList.get(position).getSys() : mList.get(position).getSys());

				startActivity(intent);
			}
		});

	}

	private void initView(View view) {
		head = (LinearLayout) view.findViewById(R.id.common_list_4_head);
		head.setVisibility(View.GONE);
		mListview = (ListView) view.findViewById(R.id.common_list_4_listview);
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.common_list_title_4);
		tv1 = (TextView) layout.findViewById(R.id.list_title_4_item1);
		tv2 = (TextView) layout.findViewById(R.id.list_title_4_item2);
		tv3 = (TextView) layout.findViewById(R.id.list_title_4_item3);
		tv4 = (TextView) layout.findViewById(R.id.list_title_4_item4);

		tv1.setText("采集时间");
		if (!tag.equals("anomaly")) {
			tv2.setText("积压表");
		} else {
			tv2.setText("异常点");
		}
		tv3.setText("积压名称");
		tv4.setText("积压量");
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_deduction_remind_backlog:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<DeductionRemindBean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				if (tag.equals("backlog")) {
					mAdapter1 = new DeductionRemind1Adapter(getActivity(), mList, R.layout.item_list_4);
					mListview.setAdapter(mAdapter1);
				} else {
					mAdapter2 = new DeductionRemind2Adapter(getActivity(), mList, R.layout.item_list_4);
					mListview.setAdapter(mAdapter2);
				}
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;
		default:
			break;
		}
	}

}
