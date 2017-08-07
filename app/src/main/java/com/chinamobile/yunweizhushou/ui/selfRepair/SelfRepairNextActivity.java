package com.chinamobile.yunweizhushou.ui.selfRepair;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.SelectMouthDialog;
import com.chinamobile.yunweizhushou.ui.selfRepair.adapter.SelfRepairAdapter2;
import com.chinamobile.yunweizhushou.ui.selfRepair.bean.SelfRepairBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class SelfRepairNextActivity  extends BaseActivity implements OnClickListener{


	private TextView gzzy_next_tv;
	private ListView mListView;
	private String currentDate;
	private int year,month;
	private SelfRepairAdapter2 mAdapter;
	private List<SelfRepairBean> mList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guzhangziyu_next);
		Calendar cal = Calendar.getInstance();
		year=cal.get(Calendar.YEAR);
		month=cal.get(Calendar.MONTH) + 1;
		currentDate=year+"-"+month;
		initView();
		initEvent();
		initRequest();
	}

	private void initEvent() {
		getTitleBar().setMiddleText("故障自愈");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		gzzy_next_tv.setOnClickListener(this);
		
	}
	
	private void initRequest() {
		HashMap maps=new HashMap<String,String>();
		maps.put("action", "getAllOfSelfRecoveryDesc");
		maps.put("years", currentDate); 
		startTask(HttpRequestEnum.enum_guzhangziyu, ConstantValueUtil.URL + "ToubleSelfRecovery?", maps, true);
		
	}
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_guzhangziyu:
			if (responseBean.isSuccess()) {
				Type t = new TypeToken<List<SelfRepairBean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				mAdapter=new SelfRepairAdapter2(this, mList, R.layout.item_guzhangziyu);
				mListView.setAdapter(mAdapter);
			}
			break;
	
		default:
			break;
		}
	}

	private void initView() {
		gzzy_next_tv=(TextView) findViewById(R.id.gzzy_next_tv);
		mListView= (ListView) findViewById(R.id.gzzy_next_listView);
		gzzy_next_tv.setText(year+"年"+month+"月");
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gzzy_next_tv:
			SelectMouthDialog dialog = new SelectMouthDialog(this);
			dialog.show();
			dialog.setBirthdayListener(new SelectMouthDialog.OnBirthListener() {

				@Override
				public void onClick(String year, String month, String day) {
					String currentDate1 = year + "年" + month + "月";
					currentDate = year + "-" + month;
					gzzy_next_tv.setText(currentDate1);
					initRequest();
				}
			});
			break;

		default:
			break;
		}
		
	}
	

}
