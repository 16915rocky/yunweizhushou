package com.chinamobile.yunweizhushou.ui.serviceChain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.serviceChain.adapter.ServiceChainAdapter;
import com.chinamobile.yunweizhushou.ui.serviceChain.bean.ServcieChainBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class ServiceChainActivity extends BaseActivity implements OnClickListener {

	
	private TextView tv_title1,tv_title2,tv_title3,tv_search;
	private ListView lv_common3;
	private EditText et_search;
	private ServiceChainAdapter mAdapter;
	private List<ServcieChainBean> mList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_list_3_nodivider);
		initView();
		initRequest("");
		initEvent();
		
	}

	private void initEvent() {
		getTitleBar().setMiddleText("业务链");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tv_search.setOnClickListener(this);
		lv_common3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent();
				intent.putExtra("sys_op_id", mList.get(position).getSys_op_id());
				intent.putExtra("business_id", mList.get(position).getBusiness_id());
				intent.putExtra("busi_name", mList.get(position).getBusi_name());
				intent.setClass(ServiceChainActivity.this, ServiceChainNextActivity.class);
				startActivity(intent);
			}
		});
		
	}

	private void initRequest(String busi_name) {
		HashMap map=new HashMap<String,String>();
		map.put("action", "getListOfBusi");
		map.put("busi_name", busi_name);
		startTask(HttpRequestEnum.enum_serviceChain, ConstantValueUtil.URL + "BusiRadar?", map,false);
		
	}
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}

		switch (e) {
		case enum_serviceChain:
			if (responseBean == null) {
				Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
				return;
			}
			if (responseBean.isSuccess()) {
				Type type = new TypeToken<List<ServcieChainBean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);
				mAdapter = new ServiceChainAdapter(this, mList, R.layout.item_list_3_arrow);
				lv_common3.setAdapter(mAdapter);
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;
		default:
			break;
		}
	}

	private void initView() {
		tv_title1=(TextView) findViewById(R.id.list_title_4_item1);
		tv_title2=(TextView) findViewById(R.id.list_title_4_item2);
		tv_title3=(TextView) findViewById(R.id.list_title_4_item3);
		tv_title1.setText("业务分组");
		tv_title2.setText("渠道");
		tv_title3.setText("业务名称");
		lv_common3=(ListView) findViewById(R.id.common_list_4_listview);
		et_search=(EditText) findViewById(R.id.search_edittext);
		et_search.setHint("请按业务名称搜索");
		tv_search=(TextView) findViewById(R.id.search_btn);
		
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_btn:
			 initRequest(et_search.getText().toString());
			break;

		default:
			break;
		}
		
	}

	
	
	

}
