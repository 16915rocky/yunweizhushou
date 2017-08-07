package com.chinamobile.yunweizhushou.ui.networkFlowPay.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.networkFlowPay.adapter.EnetTrafficSys1Adapter;
import com.chinamobile.yunweizhushou.ui.networkFlowPay.bean.NetworkFlowPayVo;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.widget.SelectMouthDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class EnetTrafficSysFrament extends BaseFragment implements OnClickListener {

	private ListView mListview;
	private List<NetworkFlowPayVo> mList;
	private EnetTrafficSys1Adapter mAdapter1;
	private EnetTrafficSys1Adapter mAdapter2;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;// tv6為時間 tv7為写死本文
	private String tag;
	private LinearLayout head;
	private LinearLayout linearLayout2;
	private String d;// 获取時間參數



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Bundle arguments = getArguments();
		tag=arguments.getString("tag");
		View view = inflater.inflate(R.layout.common_list_5, container, false);
		initView(view);
		initEvent();
		d = Utils.getRequestTime4();
		String da = d.substring(0, 4) + "年" + d.substring(5, 7) + "月";
		tv6.setText(da);
		initRequest(d);
		return view;
	}

	private void initEvent() {
		tv6.setOnClickListener(this);
	}

	private void initRequest(String date) {
		HashMap<String, String> map = new HashMap<>();
		if ("settlement".equals(tag)) {
			map.put("action", "settlement");
			map.put("date", date);
			startTask(HttpRequestEnum.enum_enet_traffic_sys_tsib, ConstantValueUtil.URL + "NetworkFlowPay?", map, true);
		} else {
			map.put("action", "accountExtract");
			map.put("date", date);
			startTask(HttpRequestEnum.enum_enet_traffic_sys_ate, ConstantValueUtil.URL + "NetworkFlowPay?", map, true);
		}

	}

	private void initView(View view) {
		head = (LinearLayout) view.findViewById(R.id.common_list_5_head);
		head.setVisibility(View.GONE);
		linearLayout2 = (LinearLayout) view.findViewById(R.id.common_list_5_extra);
		linearLayout2.setVisibility(View.VISIBLE);

		View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_1_3, null);
		linearLayout2.addView(v);
		tv6 = (TextView) v.findViewById(R.id.item_1_3_time);
		tv7 = (TextView) v.findViewById(R.id.item_1_3_tv2);
		if ("settlement".equals(tag)) {
			tv7.setText("正常入库时间为每月1号到10号");
		} else {
			tv7.setText("正常抽取时间为每月10号到25号");
		}
		mListview = (ListView) view.findViewById(R.id.common_list_5_listview);
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.common_list_title_5);
		tv1 = (TextView) layout.findViewById(R.id.list_title_5_item1);
		tv2 = (TextView) layout.findViewById(R.id.list_title_5_item2);
		tv3 = (TextView) layout.findViewById(R.id.list_title_5_item3);
		tv4 = (TextView) layout.findViewById(R.id.list_title_5_item4);
		tv5 = (TextView) layout.findViewById(R.id.list_title_5_item5);

		tv1.setText("开始时间");
		tv2.setText("执行时间");
		tv3.setText("执行环节");
		tv4.setText("异常信息");
		tv5.setText("任务名称");
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_enet_traffic_sys_tsib:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<NetworkFlowPayVo>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);

				mAdapter1 = new EnetTrafficSys1Adapter(getActivity(), mList, R.layout.item_list_5);
				mListview.setAdapter(mAdapter1);
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
		case enum_enet_traffic_sys_ate:
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<NetworkFlowPayVo>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);

				mAdapter2 = new EnetTrafficSys1Adapter(getActivity(), mList, R.layout.item_list_5);
				mListview.setAdapter(mAdapter2);

			} else {
				if (mAdapter1 != null) {
					mAdapter1.clearData();
				} else if (mAdapter2 != null) {
					mAdapter2.clearData();
				}
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.item_1_3_time:
			SelectMouthDialog dialog = new SelectMouthDialog(getActivity());
			dialog.show();
			dialog.setBirthdayListener(new SelectMouthDialog.OnBirthListener() {

				@Override
				public void onClick(String year, String month, String day) {
					d = year + "-" + month;
					initRequest(d);
					String date = year + "年" + month + "月";
					tv6.setText(date);
				}
			});
			break;

		default:
			break;
		}
	}

}
