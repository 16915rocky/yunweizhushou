package com.chinamobile.yunweizhushou.ui.fault.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.FaultRiskWarningBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.common.SelectMouthDialog;
import com.chinamobile.yunweizhushou.ui.adapter.FaultRiskWaringAdapter;
import com.chinamobile.yunweizhushou.ui.fault.FaultManageActivity;
import com.chinamobile.yunweizhushou.ui.fault.FaultRiskOrKeyNextActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class FaultRiskWarningFragment  extends BaseFragment implements FaultManageActivity.SwitchToTypicalPagerListener {

	private ListView listview_error_riskwarning;
	private TextView tv_time_select;
	private String currentDate;
	private List<FaultRiskWarningBean> mList;
	private FaultRiskWaringAdapter mAdapter;
	private boolean isFirst = true;
	private LinearLayout lt_erpw,lt_extra;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_error_risk_pre_warning, container, false);
		currentDate = Utils.getRequestTime4();
		initView(view);
		((FaultManageActivity) getActivity()).setOnSwicthToTypicalListener(this);
		initRequest();
		ininEvent();
		return view;
	}

	private void ininEvent() {
		tv_time_select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SelectMouthDialog dialog = new SelectMouthDialog(getActivity());
				dialog.show();
				dialog.setBirthdayListener(new SelectMouthDialog.OnBirthListener() {

					@Override
					public void onClick(String year, String month, String day) {
						String currentDate1 = year + "年" + month + "月";
						currentDate = year + "-" + month;
						tv_time_select.setText(currentDate1);
						initRequest();
					}
				});
			}
		});
		listview_error_riskwarning.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int positison, long arg3) {
				Intent intent=new Intent();
				intent.setClass(getActivity(), FaultRiskOrKeyNextActivity.class);
				intent.putExtra("id", mList.get(positison).getId());
				intent.putExtra("title", mList.get(positison).getTitle());
				intent.putExtra("request", "getFollowsOfRiskWarning");
				startActivity(intent);
			}
		});
		
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getListOfRiskWarning");
		map.put("date", currentDate);
		startTask(HttpRequestEnum.enum_fault_riskwarning, ConstantValueUtil.URL + "Broadcast?", map);
		
	}
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
	
		switch (e) {
		case enum_fault_riskwarning:
			try {
				if(responseBean.isSuccess()){
				listview_error_riskwarning.setVisibility(View.VISIBLE);
				lt_extra.setVisibility(View.GONE);
				Type type = new TypeToken<List<FaultRiskWarningBean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new FaultRiskWaringAdapter(getActivity(), mList, R.layout.item_error_risk_pre_warning);
				listview_error_riskwarning.setAdapter(mAdapter);
				}else{
					listview_error_riskwarning.setVisibility(View.GONE);
//					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//					TextView tv=new TextView(getActivity());
//					tv.setText("暂无数据");
//					tv.setTextSize(30);
//					tv.setGravity(Gravity.CENTER);
//					tv.setLayoutParams(lp);
//					lt_extra.addView(tv);
					lt_extra.setVisibility(View.VISIBLE);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	private void initView(View view) {
		listview_error_riskwarning=(ListView) view.findViewById(R.id.listview_error_riskwarning);
		tv_time_select=(TextView) view.findViewById(R.id.tv_time_select);
		lt_erpw= (LinearLayout) view.findViewById(R.id.lt_erpw);
		lt_extra= (LinearLayout) view.findViewById(R.id.lt_extra);
		tv_time_select.setText(currentDate.substring(0, 4) + "年" + currentDate.substring(5, 7) + "月");
		
	}



	@Override
	public void switchToTypical() {
		if (isFirst) {
			isFirst = false;
			initRequest();
		}
		
	}
	

}
