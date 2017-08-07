package com.chinamobile.yunweizhushou.ui.userperception;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.PerceptionsOfTOP5Bean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.GraphListActivity2;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.ui.userperception.adapters.PerceptionsNextAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class UserPerceptionNextActivity2 extends BaseActivity {


	private ListView listview_user_perception2_next;
	private List<PerceptionsOfTOP5Bean> mList;
	private PerceptionsNextAdapter mAdapter;
	private LinearLayout extraLayout;
	private EditText searchContent;
	private TextView searchBtn, totalTextview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_perception_next);
		initView();
		initEvent();
		initRequest("");
		
	}

	private void initEvent() {
		getTitleBar().setMiddleText("全部感知数据列表");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(searchContent.getText().toString())) {
					initRequest(searchContent.getText().toString());
				} else {
					Utils.ShowErrorMsg(UserPerceptionNextActivity2.this, "请输入正确关键字");
				}
			}
		});
		listview_user_perception2_next.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				
				Intent intent=new Intent();
				intent.putExtra("extraKey", "fkId");
				intent.putExtra("extraValue", "1119");
				intent.putExtra("extraKey2", "pament_business");
				intent.putExtra("extraValue2", mList.get(position).getRegion_name());
				intent.setClass(UserPerceptionNextActivity2.this, GraphListActivity2.class);
				startActivity(intent);
			}
		});
		
	}

	private void initRequest(String kpi_name) {
		HashMap<String, String> map1 = new HashMap<>();
		map1.put("action", "getListOfAllPerceptions");
		map1.put("kpi_name", kpi_name);
		startTask(HttpRequestEnum.enum_entireperception_list_next, ConstantValueUtil.URL + "EntirePerception?", map1, true);
		
	}

	private void initView() {
		extraLayout=(LinearLayout)findViewById(R.id.lt_extra);
		listview_user_perception2_next=(ListView) findViewById(R.id.listview_user_perception2_next);
		extraLayout.setVisibility(View.VISIBLE);
		
	
		searchContent = (EditText) findViewById(R.id.search_edittext);
		searchContent.setHint("请输入组织名称关键词");
		searchBtn = (TextView) findViewById(R.id.search_btn);
	
	
		
	}
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
	/*	if (refreshLayout.isRefreshing()) {
			refreshLayout.setRefreshing(false);
		}*/
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_entireperception_list_next:
				Type t = new TypeToken<List<PerceptionsOfTOP5Bean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				mAdapter = new PerceptionsNextAdapter(this, mList, R.layout.item_perception_next);
				listview_user_perception2_next.setAdapter(mAdapter);
				break;
			default:
				break;
			}
		}
	}
}
