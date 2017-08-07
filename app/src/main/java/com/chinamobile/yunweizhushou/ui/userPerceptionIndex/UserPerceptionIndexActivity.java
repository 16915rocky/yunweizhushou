package com.chinamobile.yunweizhushou.ui.userPerceptionIndex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.userPerceptionIndex.adapter.KeiListAdapter;
import com.chinamobile.yunweizhushou.ui.userPerceptionIndex.bean.KeiItemBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class UserPerceptionIndexActivity  extends BaseActivity {
	

	private MyListView lvKei;
	private List<KeiItemBean> mlist;
	private KeiListAdapter keiAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_user_perception_index);
		initView();
		initReqeust();
		inEvent();
	}

	private void initReqeust() {
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("action", "currentRate");
		startTask(HttpRequestEnum.enum_2017kei, ConstantValueUtil.URL + "KEIServlet?", maps, true);
		
	}

	private void inEvent() {
		getTitleBar().setMiddleText("用户感知指标");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		lvKei.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				
					Intent intent=new Intent();
					intent.putExtra("KEIName", mlist.get(position).getClass_name());
					intent.putExtra("position", position+"");
					intent.putExtra("classId", mlist.get(position).getClass_id());
					intent.setClass(UserPerceptionIndexActivity.this, UserPerceptionIndexNext2Activity.class);
					startActivity(intent);
				
				
			}
		});
	
		
	}
	
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_2017kei:
			Type type = new TypeToken<List<KeiItemBean>>() {
			}.getType();	
			try {
				mlist=new Gson().fromJson(new JSONObject(responseBean.getDATA()).getJSONArray("itemList").toString(), type);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			keiAdapter=new KeiListAdapter(this,mlist,R.layout.item_kei_list);
			lvKei.setAdapter(keiAdapter);
			break;
		default:
			break;
		}
	}

	private void initView() {
		
		lvKei=(MyListView) findViewById(R.id.lv_kei);
		
	}



}
