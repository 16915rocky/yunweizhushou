package com.chinamobile.yunweizhushou.ui.fault;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.FaultRiskOrKeyNextBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.FaultRiskOrKeyNextAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class FaultRiskOrKeyNextActivity  extends BaseActivity {

	private TextView tv1_fault_riskwarning_item,tv2_fault_riskwarning_item;
	private String id,title,request;
	private ListView mListView;
	private List<FaultRiskOrKeyNextBean> mList;
	private FaultRiskOrKeyNextAdapter  mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		id=getIntent().getStringExtra("id");
		title=getIntent().getStringExtra("title");
		request=getIntent().getStringExtra("request");
		setContentView(R.layout.faultriskorkey_next_listview);
		initView();
		inintEvent();
		initRequest();
	}

	private void inintEvent() {
		getTitleBar().setMiddleText(title);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	private void initView() {
		
		mListView=(ListView) findViewById(R.id.common_listview);
		
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", request);
		map.put("id", id);
		startTask(HttpRequestEnum.enum_fault_riskwarning_next, ConstantValueUtil.URL + "Broadcast?", map);
		
	}

	
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
	
		switch (e) {
		case enum_fault_riskwarning_next:
			try {
				
					Type type = new TypeToken<List<FaultRiskOrKeyNextBean>>() {
					}.getType();
					mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
					mAdapter = new FaultRiskOrKeyNextAdapter(this, mList, R.layout.fault_riskwarning_item);
					mListView.setAdapter(mAdapter);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
	

}
