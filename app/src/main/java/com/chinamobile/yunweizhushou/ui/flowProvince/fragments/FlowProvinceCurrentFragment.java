package com.chinamobile.yunweizhushou.ui.flowProvince.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.flowProvince.bean.FlowPrivinceBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.google.gson.Gson;

import java.util.HashMap;

public class FlowProvinceCurrentFragment extends BaseFragment {

	private LinearLayout layout1, layout2, layout3, layout4, layout5, layout6, layout7, layout8, layout9, layout10;
	private TextView title1, title2, title3, title4, title5, title6, title7, title8, title9, title10;
	private TextView content1, content2, content3, content4, content5, content6, content7, content8, content9,
			content10;
	private FlowPrivinceBean bean;

	private MyRefreshLayout mRefreshLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_flow_province, container, false);
		initView(view);
		initRequest();
		initEvent();
		return view;
	}

	private void initEvent() {
		mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				initRequest();
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "flowPay");
		startTask(HttpRequestEnum.enum_flow_province, ConstantValueUtil.URL + "FlowPayment?", map);

	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {

		super.onTaskFinish(e, responseBean);
		mRefreshLayout.setRefreshing(false);
		if (responseBean == null) {
			Utils.ShowErrorMsg(getActivity(), Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_flow_province:
			if (responseBean.isSuccess()) {
				bean = new Gson().fromJson(responseBean.getDATA(), FlowPrivinceBean.class);
				content1.setText(bean.getEsbCsAmsFmwwwRefund001() + "%");
				content2.setText(bean.getEsbCsAmsFmwwworderQry001() + "%");
				content3.setText(bean.getAccountTOQuantity());
				content4.setText(bean.getfRbacklog() + "笔");
				content5.setText(bean.getAccountAWoOverstock() + "笔");
				content6.setText(bean.getfREAmount() + "笔");
				content7.setText(bean.getAccountAEAmount() + "笔");
				content8.setText(bean.getOpenTOQuantity());
				content9.setText(bean.getCfTSSystem());
				content10.setText(bean.gettSPSSGroup());
			} else {
				Utils.ShowErrorMsg(getActivity(), responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	private void initView(View view) {

		mRefreshLayout = (MyRefreshLayout) view.findViewById(R.id.flow_provice_refresh);

		layout1 = (LinearLayout) view.findViewById(R.id.flow_provice_layout1);
		layout2 = (LinearLayout) view.findViewById(R.id.flow_provice_layout2);
		layout3 = (LinearLayout) view.findViewById(R.id.flow_provice_layout3);
		layout4 = (LinearLayout) view.findViewById(R.id.flow_provice_layout4);
		layout5 = (LinearLayout) view.findViewById(R.id.flow_provice_layout5);
		layout6 = (LinearLayout) view.findViewById(R.id.flow_provice_layout6);
		layout7 = (LinearLayout) view.findViewById(R.id.flow_provice_layout7);
		layout8 = (LinearLayout) view.findViewById(R.id.flow_provice_layout8);
		layout9 = (LinearLayout) view.findViewById(R.id.flow_provice_layout9);
		layout10 = (LinearLayout) view.findViewById(R.id.flow_provice_layout10);
		title1 = (TextView) layout1.findViewById(R.id.flow_province_title);
		title2 = (TextView) layout2.findViewById(R.id.flow_province_title);
		title3 = (TextView) layout3.findViewById(R.id.flow_province_title);
		title4 = (TextView) layout4.findViewById(R.id.flow_province_title);
		title5 = (TextView) layout5.findViewById(R.id.flow_province_title);
		title6 = (TextView) layout6.findViewById(R.id.flow_province_title);
		title7 = (TextView) layout7.findViewById(R.id.flow_province_title);
		title8 = (TextView) layout8.findViewById(R.id.flow_province_title);
		title9 = (TextView) layout9.findViewById(R.id.flow_province_title);
		title10 = (TextView) layout10.findViewById(R.id.flow_province_title);
		content1 = (TextView) layout1.findViewById(R.id.flow_province_content);
		content2 = (TextView) layout2.findViewById(R.id.flow_province_content);
		content3 = (TextView) layout3.findViewById(R.id.flow_province_content);
		content4 = (TextView) layout4.findViewById(R.id.flow_province_content);
		content5 = (TextView) layout5.findViewById(R.id.flow_province_content);
		content6 = (TextView) layout6.findViewById(R.id.flow_province_content);
		content7 = (TextView) layout7.findViewById(R.id.flow_province_content);
		content8 = (TextView) layout8.findViewById(R.id.flow_province_content);
		content9 = (TextView) layout9.findViewById(R.id.flow_province_content);
		content10 = (TextView) layout10.findViewById(R.id.flow_province_content);
		title1.setText("流量统付\n充值受理");
		title2.setText("流量统付\n充值查询");
		title3.setText("当天订购\n总量");
		title4.setText("免费资源\n积压量");
		title5.setText("异步工单\n积压量");
		title6.setText("免费资源\n异常量");
		title7.setText("异步工单\n错误量");
		title8.setText("当天订购\n总量");
		title9.setText("流量支撑系\n统对账");
		title10.setText("流量支撑系\n统集团统付");
	}

	// private boolean containsNumber(String str) {
	// if (str.matches("^[0-9]+$")) {
	// return true;
	// }
	// return false;
	// }
	//
	// private String needAddPer(String str){
	// return containsNumber(str)?str+"%":str;
	// }
	//
	// private String needAddBI(String str){
	// return containsNumber(str)?str+"笔":str;
	// }

}
