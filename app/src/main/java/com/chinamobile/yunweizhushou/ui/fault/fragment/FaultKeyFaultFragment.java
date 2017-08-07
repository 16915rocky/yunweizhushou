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
import com.chinamobile.yunweizhushou.bean.FaultKeyBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseFragment;
import com.chinamobile.yunweizhushou.ui.adapter.FaultKeyFaultAdapter;
import com.chinamobile.yunweizhushou.ui.fault.FaultManageActivity;
import com.chinamobile.yunweizhushou.ui.fault.FaultRiskOrKeyNextActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.widget.SelectMouthDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class FaultKeyFaultFragment  extends BaseFragment implements FaultManageActivity.SwitchToFollowPagerListener {

	private ListView listview_error_key_fault;
	private TextView tv_time_select;
	private String currentDate;
	private List<FaultKeyBean> mList;
	private FaultKeyFaultAdapter mAdapter;
	private boolean isFirst = true;
	private LinearLayout lt_extra;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_error_key_fault, container, false);
		currentDate = Utils.getRequestTime4();
		initView(view);
		((FaultManageActivity) getActivity()).setOnSwicthToFollowListener(this);
		
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
		
		listview_error_key_fault.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int positison, long arg3) {
				Intent intent=new Intent();
				intent.setClass(getActivity(), FaultRiskOrKeyNextActivity.class);
				intent.putExtra("id", mList.get(positison).getId());
				intent.putExtra("title", mList.get(positison).getTitle());
				intent.putExtra("request", "getFollowsOfKeyFault");
				startActivity(intent);
			}
		});
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getListOfKeyFault");
		map.put("date", currentDate);
		startTask(HttpRequestEnum.enum_fault_faultkey, ConstantValueUtil.URL + "Broadcast?", map);
		
	}
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
	
		switch (e) {
		case enum_fault_faultkey:
			try {
				if(responseBean.isSuccess()){
			    listview_error_key_fault.setVisibility(View.VISIBLE);
			    lt_extra.setVisibility(View.GONE);
				Type type = new TypeToken<List<FaultKeyBean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new FaultKeyFaultAdapter(getActivity(),mList,R.layout.item_error_key_fault);
				listview_error_key_fault.setAdapter(mAdapter);
				}else{
					listview_error_key_fault.setVisibility(View.GONE);
					lt_extra.setVisibility(View.VISIBLE);
//					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//					TextView tv=new TextView(getActivity());
//					tv.setText("暂无数据");
//					tv.setTextSize(30);
//					tv.setGravity(Gravity.CENTER);
//					tv.setLayoutParams(lp);
//					lt_fk.addView(tv);
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
		listview_error_key_fault=(ListView) view.findViewById(R.id.listview_error_key_fault);
		tv_time_select=(TextView) view.findViewById(R.id.tv_time_select);
		lt_extra= (LinearLayout) view.findViewById(R.id.lt_extra);
		tv_time_select.setText(currentDate.substring(0, 4) + "年" + currentDate.substring(5, 7) + "月");
		
	}

	@Override
	public void switchToFollow() {
		if (isFirst) {
			isFirst = false;
			initRequest();
		}
		
	}
	

}
