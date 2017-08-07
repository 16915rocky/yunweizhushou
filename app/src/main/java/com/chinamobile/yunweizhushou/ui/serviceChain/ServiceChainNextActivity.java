package com.chinamobile.yunweizhushou.ui.serviceChain;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.serviceChain.adapter.ServiceChainNextAdapter;
import com.chinamobile.yunweizhushou.ui.serviceChain.bean.ServcieChainBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class ServiceChainNextActivity extends BaseActivity {
	
	private ListView common_listview;
	private String sys_op_id,business_id,busi_name;
	private ServiceChainNextAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sys_op_id=getIntent().getStringExtra("sys_op_id");
		business_id=getIntent().getStringExtra("business_id");
		busi_name=getIntent().getStringExtra("busi_name");
		setContentView(R.layout.common_listview);
		
		initView();
		initRequest();
		initEvent();
	}

	private void initEvent() {
		getTitleBar().setMiddleText(busi_name);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	private void initRequest() {
		HashMap map=new HashMap<String,String>();
		map.put("action", "getListOfProcess");
		map.put("sys_op_id", sys_op_id);
		map.put("business_id", business_id);
		startTask(HttpRequestEnum.enum_serviceChain_next, ConstantValueUtil.URL + "BusiRadar?", map,false);
		
	}
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}

		switch (e) {
		case enum_serviceChain_next:
			if (responseBean == null) {
				Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
				return;
			}
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<ServcieChainBean>>() {
				}.getType();
				JSONArray ja;
				try {
					ja = new JSONObject(responseBean.getDATA()).getJSONArray("itemsList");
					mAdapter = new ServiceChainNextAdapter(this, ja);
					common_listview.setAdapter(mAdapter);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;
		default:
			break;
		}
	}

	private void initView() {
		common_listview=(ListView) findViewById(R.id.common_listview);
		common_listview.setDivider(null);
		
	}

}
