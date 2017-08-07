package com.chinamobile.yunweizhushou.ui.creditControl;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.creditControl.adapter.NewCreditControlAdapter5;
import com.chinamobile.yunweizhushou.ui.creditControl.bean.NewCreditControlBean5;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class CreditControl5Activity extends BaseActivity {

	private ListView listView;
	private String operation;
	private NewCreditControlAdapter5 mAdapter;
	private List<NewCreditControlBean5> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		operation=getIntent().getStringExtra("operation");
		setContentView(R.layout.common_listview);
		initView();
		initEvent();
		initRequest();
		
	}

	
	private void initEvent() {
		getTitleBar().setMiddleText("检查结果");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}


	private void initRequest() {
		HashMap<String, String> maps = new HashMap<String,String>();
		maps.put("action", "getListOfCheck");
		maps.put("operation", operation);
		startTask(HttpRequestEnum.enum_new_credit_control5, ConstantValueUtil.URL + "CreditControl?", maps,true);
		
	}


	private void initView() {
		listView=(ListView) findViewById(R.id.common_listview);
	
		
	}



	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if(responseBean==null){
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
		}
		switch (e) {
		case enum_new_credit_control5:
			if(responseBean.isSuccess()){
				Type t = new TypeToken<List<NewCreditControlBean5>>(){}.getType();
				list=new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				mAdapter=new NewCreditControlAdapter5(this, list, R.layout.item_new_credit_control);
				listView.setAdapter(mAdapter);				
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			
			break;

		default:
			break;
	}

	}
}
