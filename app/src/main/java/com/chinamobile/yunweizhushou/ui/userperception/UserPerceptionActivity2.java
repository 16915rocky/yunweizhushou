package com.chinamobile.yunweizhushou.ui.userperception;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.PerceptionsOfProvinceBean;
import com.chinamobile.yunweizhushou.bean.PerceptionsOfTOP5Bean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.GraphListActivity2;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.chinamobile.yunweizhushou.view.SwitchView;
import com.chinamobile.yunweizhushou.ui.userperception.adapters.PerceptionsOfTOP5Adapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class UserPerceptionActivity2  extends BaseActivity implements OnClickListener {


	private TextView tv_huzhou_fluency,tv_huzhou_slow,tv_huzhou_pause,tv_huzhou_interrupt,
					 tv_hangzhou_fluency,tv_hangzhou_slow,tv_hangzhou_pause,tv_hangzhou_interrupt,
					 tv_jiaxing_fluency,tv_jiaxing_slow,tv_jiaxing_pause,tv_jiaxing_interrupt,
					 tv_jinhua_fluency,tv_jinhua_slow,tv_jinhua_pause,tv_jinhua_interrupt,
					 tv_lishui_fluency,tv_lishui_slow,tv_lishui_pause,tv_lishui_interrupt,
					 tv_ningbo_fluency,tv_ningbo_slow,tv_ningbo_pause,tv_ningbo_interrupt,
					 tv_shaoxing_fluency,tv_shaoxing_slow,tv_shaoxing_pause,tv_shaoxing_interrupt,
					 tv_taizhou_fluency,tv_taizhou_slow,tv_taizhou_pause,tv_taizhou_interrupt,
					 tv_wenzhou_fluency,tv_wenzhou_slow,tv_wenzhou_pause,tv_wenzhou_interrupt,
					 tv_zhoushan_fluency,tv_zhoushan_slow,tv_zhoushan_pause,tv_zhoushan_interrupt,
					 tv_quzhou_fluency,tv_quzhou_slow,tv_quzhou_pause,tv_quzhou_interrupt;
	private TextView tv_fluency,tv_interrupt,tv_pause,tv_slow,tv_more,tv_more2;
	private LinearLayout lt_huzhou,lt_jiaxing,lt_hangzhou,lt_shaoxing,lt_ningbo,lt_jinhua,lt_quzhou,lt_taizhou,lt_lishui,lt_wenzhou,lt_zhoushan;
	private SwitchView switchview1,switchview2;
	private RelativeLayout lt_cornofright;
	private ListView listview1_user_perception2,listview2_user_perception2;
	private  String cond="yyt、qd";
	private List<PerceptionsOfProvinceBean> mList;
	private List<PerceptionsOfTOP5Bean> TOP5mList1,TOP5mList2;
	private PerceptionsOfTOP5Adapter mAdapter1,mAdapter2;
	private boolean btn1=true,btn2=true;
	private ScrollView scrollview;
	private  Animation scaleAnimation;  
	private TextView tv_huzhou_oval1,tv_huzhou_oval2,tv_huzhou_oval3,
				     tv_hangzhou_oval1,tv_hangzhou_oval2,tv_hangzhou_oval3,
				     tv_jiaxing_oval1,tv_jiaxing_oval2,tv_jiaxing_oval3,
				     tv_zhoushan_oval1,tv_zhoushan_oval2,tv_zhoushan_oval3,
				     tv_ningbo_oval1,tv_ningbo_oval2,tv_ningbo_oval3,
				     tv_shaoxing_oval1,tv_shaoxing_oval2,tv_shaoxing_oval3,
				     tv_jinhua_oval1,tv_jinhua_oval2,tv_jinhua_oval3,
				     tv_taizhou_oval1,tv_taizhou_oval2,tv_taizhou_oval3,
				     tv_lishui_oval1,tv_lishui_oval2,tv_lishui_oval3,
				     tv_wenzhou_oval1,tv_wenzhou_oval2,tv_wenzhou_oval3,
				     tv_quzhou_oval1,tv_quzhou_oval2,tv_quzhou_oval3;
	private MyRefreshLayout refreshLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_user_perception2);
	  initView();
	  scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scaleanim);
	  initRequest();
	  initEvent();
	}

	private void initEvent() {
		getTitleBar().setMiddleText("用户感知");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		 
		tv_more.setOnClickListener(this);
		tv_more2.setOnClickListener(this);
		lt_huzhou.setOnClickListener(this);
		lt_jiaxing.setOnClickListener(this);
		lt_hangzhou.setOnClickListener(this);
		lt_shaoxing.setOnClickListener(this);
		lt_ningbo.setOnClickListener(this);
		lt_jinhua.setOnClickListener(this);
		lt_quzhou.setOnClickListener(this);
		lt_taizhou.setOnClickListener(this);
		lt_lishui.setOnClickListener(this);
		lt_wenzhou.setOnClickListener(this);
		lt_zhoushan.setOnClickListener(this);
		lt_cornofright.setOnClickListener(this);
		switchview1.setOnSwitchChangeListener(new SwitchView.OnSwitchChangeListener() {
			
			@Override
			public void onSwitchChanged(boolean open) {
				btn1=open;
				if(btn1==false && btn2==true){
					cond="qd";
					initRequest();
				}else if(btn1==true && btn2==true){
					cond="yyt、qd";
					initRequest();
				}else if(btn1==true && btn2==false){
					cond="yyt";
					initRequest();
				}else if(btn1==false && btn2==false){
					Toast.makeText(UserPerceptionActivity2.this, "不能同时关闭", Toast.LENGTH_SHORT).show();
					switchview1.setSwitchStatus(true);
				}
				
			}
		});
		switchview2.setOnSwitchChangeListener(new SwitchView.OnSwitchChangeListener() {
			
			@Override
			public void onSwitchChanged(boolean open) {
				btn2=open;
				if(btn1==false && btn2==true){
					cond="qd";
					initRequest();
				}else if(btn1==true && btn2==true){
					cond="yyt、qd";
					initRequest();
				}else if(btn1==true && btn2==false){
					cond="yyt";
					initRequest();
				}else if(btn1==false && btn2==false){
					Toast.makeText(UserPerceptionActivity2.this, "不能同时关闭", Toast.LENGTH_SHORT).show();
					switchview2.setSwitchStatus(true);
				}
				
			}
		});
		
		listview1_user_perception2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					
				Intent intent=new Intent();
				intent.putExtra("extraKey", "fkId");
				intent.putExtra("extraValue", "1118");
				intent.putExtra("extraKey2", "pament_business");
				intent.putExtra("extraValue2", TOP5mList1.get(position).getKpi_name());
				intent.setClass(UserPerceptionActivity2.this, GraphListActivity2.class);
				startActivity(intent);
				
			}
		});
		listview2_user_perception2.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				
				Intent intent=new Intent();
				intent.putExtra("extraKey", "fkId");
				intent.putExtra("extraValue", "1118");
				intent.putExtra("extraKey2", "pament_business");
				intent.putExtra("extraValue2", TOP5mList2.get(position).getKpi_name());
				intent.setClass(UserPerceptionActivity2.this, GraphListActivity2.class);
				startActivity(intent);
			}
		});
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				  initRequest();
			}
		});
		
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("action", "getPerceptionsOfProvince");
		map.put("cond", cond);
		startTask(HttpRequestEnum.enum_entireperception, ConstantValueUtil.URL + "EntirePerception?", map, true);
		
		HashMap<String, String> map1 = new HashMap<>();
		map1.put("action", "getPerceptionsOfTOP5");
		map1.put("cond", cond);
		startTask(HttpRequestEnum.enum_entireperception_list, ConstantValueUtil.URL + "EntirePerception?", map1, true);
		
	}
	
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (refreshLayout.isRefreshing()) {
			refreshLayout.setRefreshing(false);
		}
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_entireperception:
				
				try {
					JSONObject jo=new JSONObject(responseBean.getDATA());
					tv_fluency.setText(jo.getString("fluency"));
					tv_slow.setText(jo.getString("slow"));
					tv_pause.setText(jo.getString("pause"));
					tv_interrupt.setText(jo.getString("interrupt"));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Type t2 = new TypeToken<List<PerceptionsOfProvinceBean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t2);
				tv_hangzhou_fluency.setText(mList.get(0).getFluency());
				tv_hangzhou_slow.setText(mList.get(0).getSlow());
				tv_hangzhou_pause.setText(mList.get(0).getPause());
				tv_hangzhou_interrupt.setText(mList.get(0).getInterrupt());
				if(!"0".equals(mList.get(0).getSlow())){
					tv_hangzhou_oval1.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(0).getPause())){
					tv_hangzhou_oval2.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(0).getInterrupt())){
					tv_hangzhou_oval3.startAnimation(scaleAnimation); 
				}
				tv_huzhou_fluency.setText(mList.get(1).getFluency());
				tv_huzhou_slow.setText(mList.get(1).getSlow());
				tv_huzhou_pause.setText(mList.get(1).getPause());
				tv_huzhou_interrupt.setText(mList.get(1).getInterrupt());
				if(!"0".equals(mList.get(1).getSlow())){
					 tv_huzhou_oval1.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(1).getPause())){
					tv_huzhou_oval2.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(1).getInterrupt())){
					tv_huzhou_oval3.startAnimation(scaleAnimation); 
				}
				
				tv_jiaxing_fluency.setText(mList.get(2).getFluency());
				tv_jiaxing_slow.setText(mList.get(2).getSlow());
				tv_jiaxing_pause.setText(mList.get(2).getPause());
				tv_jiaxing_interrupt.setText(mList.get(2).getInterrupt());
				if(!"0".equals(mList.get(2).getSlow())){
					 tv_jiaxing_oval1.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(2).getPause())){
					tv_jiaxing_oval2.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(2).getInterrupt())){
					tv_jiaxing_oval3.startAnimation(scaleAnimation); 
				}
				
				tv_jinhua_fluency.setText(mList.get(3).getFluency());
				tv_jinhua_slow.setText(mList.get(3).getSlow());
				tv_jinhua_pause.setText(mList.get(3).getPause());
				tv_jinhua_interrupt.setText(mList.get(3).getInterrupt());
				if(!"0".equals(mList.get(3).getSlow())){
					 tv_jinhua_oval1.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(3).getPause())){
					tv_jinhua_oval2.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(3).getInterrupt())){
					tv_jinhua_oval3.startAnimation(scaleAnimation); 
				}
				
				tv_lishui_fluency.setText(mList.get(4).getFluency());
				tv_lishui_slow.setText(mList.get(4).getSlow());
				tv_lishui_pause.setText(mList.get(4).getPause());
				tv_lishui_interrupt.setText(mList.get(4).getInterrupt());
				if(!"0".equals(mList.get(4).getSlow())){
					 tv_lishui_oval1.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(4).getPause())){
					tv_lishui_oval2.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(4).getInterrupt())){
					tv_lishui_oval1.startAnimation(scaleAnimation); 
				}
				
				tv_ningbo_fluency.setText(mList.get(5).getFluency());
				tv_ningbo_slow.setText(mList.get(5).getSlow());
				tv_ningbo_pause.setText(mList.get(5).getPause());
				tv_ningbo_interrupt.setText(mList.get(5).getInterrupt());
				if(!"0".equals(mList.get(5).getSlow())){
					 tv_ningbo_oval1.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(5).getPause())){
					tv_ningbo_oval2.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(5).getInterrupt())){
					tv_ningbo_oval3.startAnimation(scaleAnimation); 
				}
				
				tv_shaoxing_fluency.setText(mList.get(6).getFluency());
				tv_shaoxing_slow.setText(mList.get(6).getSlow());
				tv_shaoxing_pause.setText(mList.get(6).getPause());
				tv_shaoxing_interrupt.setText(mList.get(6).getInterrupt());
				if(!"0".equals(mList.get(6).getSlow())){
					 tv_shaoxing_oval1.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(6).getPause())){
					tv_shaoxing_oval2.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(6).getInterrupt())){
					tv_shaoxing_oval3.startAnimation(scaleAnimation); 
				}
				
				tv_taizhou_fluency.setText(mList.get(7).getFluency());
				tv_taizhou_slow.setText(mList.get(7).getSlow());
				tv_taizhou_pause.setText(mList.get(7).getPause());
				tv_taizhou_interrupt.setText(mList.get(7).getInterrupt());
				if(!"0".equals(mList.get(7).getSlow())){
					 tv_taizhou_oval1.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(7).getPause())){
					tv_taizhou_oval2.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(7).getInterrupt())){
					tv_taizhou_oval3.startAnimation(scaleAnimation); 
				}
				
				tv_wenzhou_fluency.setText(mList.get(8).getFluency());
				tv_wenzhou_slow.setText(mList.get(8).getSlow());
				tv_wenzhou_pause.setText(mList.get(8).getPause());
				tv_wenzhou_interrupt.setText(mList.get(8).getInterrupt());
				if(!"0".equals(mList.get(8).getSlow())){
					 tv_wenzhou_oval1.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(8).getPause())){
					tv_wenzhou_oval2.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(8).getInterrupt())){
					tv_wenzhou_oval3.startAnimation(scaleAnimation); 
				}
				tv_zhoushan_fluency.setText(mList.get(9).getFluency());
				tv_zhoushan_slow.setText(mList.get(9).getSlow());
				tv_zhoushan_pause.setText(mList.get(9).getPause());
				tv_zhoushan_interrupt.setText(mList.get(9).getInterrupt());
				if(!"0".equals(mList.get(9).getSlow())){
					 tv_zhoushan_oval1.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(9).getPause())){
					tv_zhoushan_oval2.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(9).getInterrupt())){
					tv_zhoushan_oval3.startAnimation(scaleAnimation); 
				}
				
				tv_quzhou_fluency.setText(mList.get(10).getFluency());
				tv_quzhou_slow.setText(mList.get(10).getSlow());
				tv_quzhou_pause.setText(mList.get(10).getPause());
				tv_quzhou_interrupt.setText(mList.get(10).getInterrupt());
				if(!"0".equals(mList.get(10).getSlow())){
					 tv_quzhou_oval1.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(10).getPause())){
					tv_quzhou_oval2.startAnimation(scaleAnimation); 
				}
				if(!"0".equals(mList.get(10).getInterrupt())){
					tv_quzhou_oval3.startAnimation(scaleAnimation); 
				}
			case enum_entireperception_list:
				
				if (responseBean.isSuccess()) {
					JSONObject jo;
					try {
						Type type = new TypeToken<List<PerceptionsOfTOP5Bean>>() {
						}.getType();
						jo = new JSONObject(responseBean.getDATA());
						TOP5mList1 = new Gson().fromJson(jo.getJSONArray("worstList").toString(), type);
						TOP5mList2 = new Gson().fromJson(jo.getJSONArray("bestList").toString(), type);
						LinearLayout.LayoutParams lp1 =(LinearLayout.LayoutParams) listview1_user_perception2.getLayoutParams();
						int num1=TOP5mList1.size()+1;
						lp1.height=((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40*5, getResources().getDisplayMetrics()));
						listview1_user_perception2.setLayoutParams(lp1);
						//
						LinearLayout.LayoutParams lp2 =(LinearLayout.LayoutParams) listview1_user_perception2.getLayoutParams();
						int num2=TOP5mList2.size()+1;
						lp2.height=((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40*5, getResources().getDisplayMetrics()));
						listview2_user_perception2.setLayoutParams(lp2);
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					mAdapter1 = new PerceptionsOfTOP5Adapter(UserPerceptionActivity2.this, TOP5mList1, R.layout.item_perceptionoftop5);
					listview1_user_perception2.setAdapter(mAdapter1);
					mAdapter2 = new PerceptionsOfTOP5Adapter(UserPerceptionActivity2.this, TOP5mList2, R.layout.item_perceptionoftop5);
					listview2_user_perception2.setAdapter(mAdapter2);
				} else {
					Utils.ShowErrorMsg(this, responseBean.getMSG());
				}
				break;
			default:
				break;
			}
		}
	}

	private void initView() {
		refreshLayout= (MyRefreshLayout) findViewById(R.id.user_perception_refreshlayout);
		tv_huzhou_fluency=(TextView) findViewById(R.id.tv_huzhou_fluency);
		tv_huzhou_slow=(TextView) findViewById(R.id.tv_huzhou_slow);
		tv_huzhou_pause=(TextView) findViewById(R.id.tv_huzhou_pause);
		tv_huzhou_interrupt=(TextView) findViewById(R.id.tv_huzhou_interrupt);
		
		tv_hangzhou_fluency=(TextView) findViewById(R.id.tv_hangzhou_fluency);
		tv_hangzhou_slow=(TextView) findViewById(R.id.tv_hangzhou_slow);
		tv_hangzhou_pause=(TextView) findViewById(R.id.tv_hangzhou_pause);
		tv_hangzhou_interrupt=(TextView) findViewById(R.id.tv_hangzhou_interrupt);
		
		tv_jiaxing_fluency=(TextView) findViewById(R.id.tv_jiaxing_fluency);
		tv_jiaxing_slow=(TextView) findViewById(R.id.tv_jiaxing_slow);
		tv_jiaxing_pause=(TextView) findViewById(R.id.tv_jiaxing_pause);
		tv_jiaxing_interrupt=(TextView) findViewById(R.id.tv_jiaxing_interrupt);
		
		tv_jinhua_fluency=(TextView) findViewById(R.id.tv_jinhua_fluency);
		tv_jinhua_slow=(TextView) findViewById(R.id.tv_jinhua_slow);
		tv_jinhua_pause=(TextView) findViewById(R.id.tv_jinhua_pause);
		tv_jinhua_interrupt=(TextView) findViewById(R.id.tv_jinhua_interrupt);
		
		tv_lishui_fluency=(TextView) findViewById(R.id.tv_lishui_fluency);
		tv_lishui_slow=(TextView) findViewById(R.id.tv_lishui_slow);
		tv_lishui_pause=(TextView) findViewById(R.id.tv_lishui_pause);
		tv_lishui_interrupt=(TextView) findViewById(R.id.tv_lishui_interrupt);
		
		tv_ningbo_fluency=(TextView) findViewById(R.id.tv_ningbo_fluency);
		tv_ningbo_slow=(TextView) findViewById(R.id.tv_ningbo_slow);
		tv_ningbo_pause=(TextView) findViewById(R.id.tv_ningbo_pause);
		tv_ningbo_interrupt=(TextView) findViewById(R.id.tv_ningbo_interrupt);
		
		tv_shaoxing_fluency=(TextView) findViewById(R.id.tv_shaoxing_fluency);
		tv_shaoxing_slow=(TextView) findViewById(R.id.tv_shaoxing_slow);
		tv_shaoxing_pause=(TextView) findViewById(R.id.tv_shaoxing_pause);
		tv_shaoxing_interrupt=(TextView) findViewById(R.id.tv_shaoxing_interrupt);
		
		tv_taizhou_fluency=(TextView) findViewById(R.id.tv_taizhou_fluency);
		tv_taizhou_slow=(TextView) findViewById(R.id.tv_taizhou_slow);
		tv_taizhou_pause=(TextView) findViewById(R.id.tv_taizhou_pause);
		tv_taizhou_interrupt=(TextView) findViewById(R.id.tv_taizhou_interrupt);
		
		tv_wenzhou_fluency=(TextView) findViewById(R.id.tv_wenzhou_fluency);
		tv_wenzhou_slow=(TextView) findViewById(R.id.tv_wenzhou_slow);
		tv_wenzhou_pause=(TextView) findViewById(R.id.tv_wenzhou_pause);
		tv_wenzhou_interrupt=(TextView) findViewById(R.id.tv_wenzhou_interrupt);
		
		tv_zhoushan_fluency=(TextView) findViewById(R.id.tv_zhoushan_fluency);
		tv_zhoushan_slow=(TextView) findViewById(R.id.tv_zhoushan_slow);
		tv_zhoushan_pause=(TextView) findViewById(R.id.tv_zhoushan_pause);
		tv_zhoushan_interrupt=(TextView) findViewById(R.id.tv_zhoushan_interrupt);
		
		tv_quzhou_fluency=(TextView) findViewById(R.id.tv_quzhou_fluency);
		tv_quzhou_slow=(TextView) findViewById(R.id.tv_quzhou_slow);
		tv_quzhou_pause=(TextView) findViewById(R.id.tv_quzhou_pause);
		tv_quzhou_interrupt=(TextView) findViewById(R.id.tv_quzhou_interrupt);
		tv_huzhou_oval1=(TextView) findViewById(R.id.tv_huzhou_oval1);
		tv_huzhou_oval2=(TextView) findViewById(R.id.tv_huzhou_oval2);
		tv_huzhou_oval3=(TextView) findViewById(R.id.tv_huzhou_oval3);
		tv_hangzhou_oval1=(TextView) findViewById(R.id.tv_hangzhou_oval1);
		tv_hangzhou_oval2=(TextView) findViewById(R.id.tv_hangzhou_oval2);
		tv_hangzhou_oval3=(TextView) findViewById(R.id.tv_hangzhou_oval3);
		tv_huzhou_oval1=(TextView) findViewById(R.id.tv_huzhou_oval1);
		tv_huzhou_oval2=(TextView) findViewById(R.id.tv_huzhou_oval2);
		tv_huzhou_oval3=(TextView) findViewById(R.id.tv_huzhou_oval3);
		tv_jiaxing_oval1=(TextView) findViewById(R.id.tv_jiaxing_oval1);
		tv_jiaxing_oval2=(TextView) findViewById(R.id.tv_jiaxing_oval2);
		tv_jiaxing_oval3=(TextView) findViewById(R.id.tv_jiaxing_oval3);
		tv_zhoushan_oval1=(TextView) findViewById(R.id.tv_zhoushan_oval1);
		tv_zhoushan_oval2=(TextView) findViewById(R.id.tv_zhoushan_oval2);
		tv_zhoushan_oval3=(TextView) findViewById(R.id.tv_zhoushan_oval3);
		tv_shaoxing_oval1=(TextView) findViewById(R.id.tv_shaoxing_oval1);
		tv_shaoxing_oval2=(TextView) findViewById(R.id.tv_shaoxing_oval2);
		tv_shaoxing_oval3=(TextView) findViewById(R.id.tv_shaoxing_oval3);
		tv_ningbo_oval1=(TextView) findViewById(R.id.tv_ningbo_oval1);
		tv_ningbo_oval2=(TextView) findViewById(R.id.tv_ningbo_oval2);
		tv_ningbo_oval3=(TextView) findViewById(R.id.tv_ningbo_oval3);
		tv_jinhua_oval1=(TextView) findViewById(R.id.tv_jinhua_oval1);
		tv_jinhua_oval2=(TextView) findViewById(R.id.tv_jinhua_oval2);
		tv_jinhua_oval3=(TextView) findViewById(R.id.tv_jinhua_oval3);
		tv_quzhou_oval1=(TextView) findViewById(R.id.tv_quzhou_oval1);
		tv_quzhou_oval2=(TextView) findViewById(R.id.tv_quzhou_oval2);
		tv_quzhou_oval3=(TextView) findViewById(R.id.tv_quzhou_oval3);
		tv_taizhou_oval1=(TextView) findViewById(R.id.tv_taizhou_oval1);
		tv_taizhou_oval2=(TextView) findViewById(R.id.tv_taizhou_oval2);
		tv_taizhou_oval3=(TextView) findViewById(R.id.tv_taizhou_oval3);
		tv_lishui_oval1=(TextView) findViewById(R.id.tv_lishui_oval1);
		tv_lishui_oval2=(TextView) findViewById(R.id.tv_lishui_oval2);
		tv_lishui_oval3=(TextView) findViewById(R.id.tv_lishui_oval3);
		tv_wenzhou_oval1=(TextView) findViewById(R.id.tv_wenzhou_oval1);
		tv_wenzhou_oval2=(TextView) findViewById(R.id.tv_wenzhou_oval2);
		tv_wenzhou_oval3=(TextView) findViewById(R.id.tv_wenzhou_oval3);
		
	
		
		
		tv_fluency=(TextView) findViewById(R.id.tv_fluency);
		tv_interrupt=(TextView) findViewById(R.id.tv_interrupt);
		tv_pause=(TextView) findViewById(R.id.tv_pause);
		tv_slow=(TextView) findViewById(R.id.tv_slow);
		tv_more=(TextView) findViewById(R.id.tv_more);
		tv_more2=(TextView) findViewById(R.id.tv_more2);
		
		switchview1=(SwitchView) findViewById(R.id.switchview1);
		switchview2=(SwitchView) findViewById(R.id.switchview2);
		switchview1.setSwitchStatus(true);
		switchview2.setSwitchStatus(true);
		
		listview1_user_perception2=(ListView) findViewById(R.id.listview1_user_perception2);		
		listview2_user_perception2=(ListView) findViewById(R.id.listview2_user_perception2);		
		scrollview= (ScrollView) findViewById(R.id.scrollview);		
		scrollview.fullScroll(ScrollView.FOCUS_DOWN);
		lt_huzhou=(LinearLayout) findViewById(R.id.lt_huzhou);
		lt_jiaxing=(LinearLayout) findViewById(R.id.lt_jiaxing);
		lt_hangzhou=(LinearLayout) findViewById(R.id.lt_hangzhou);
		lt_shaoxing=(LinearLayout) findViewById(R.id.lt_shaoxing);
		lt_ningbo=(LinearLayout) findViewById(R.id.lt_ningbo);
		lt_zhoushan=(LinearLayout) findViewById(R.id.lt_zhoushan);
		lt_jinhua=(LinearLayout) findViewById(R.id.lt_jinhua);
		lt_quzhou=(LinearLayout) findViewById(R.id.lt_quzhou);
		lt_taizhou=(LinearLayout) findViewById(R.id.lt_taizhou);
		lt_lishui=(LinearLayout) findViewById(R.id.lt_lishui);
		lt_wenzhou=(LinearLayout) findViewById(R.id.lt_wenzhou);
		lt_cornofright=(RelativeLayout) findViewById(R.id.lt_cornofright);
	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent();
		switch (v.getId()) {
		case R.id.tv_more:
			intent.setClass(this, UserPerceptionNextActivity2.class);
			break;
		case R.id.tv_more2:
			intent.setClass(this, UserPerceptionNextActivity2.class);
			break;
		case R.id.lt_huzhou:
			intent.putExtra("extraKey", "fkId");
			intent.putExtra("extraValue", "1119");
			intent.putExtra("extraKey2", "pament_business");
			intent.putExtra("extraValue2","湖州");
			intent.setClass(UserPerceptionActivity2.this, GraphListActivity2.class);
			break;
		case R.id.lt_jiaxing:
			intent.putExtra("extraKey", "fkId");
			intent.putExtra("extraValue", "1119");
			intent.putExtra("extraKey2", "pament_business");
			intent.putExtra("extraValue2","嘉兴");
			intent.setClass(UserPerceptionActivity2.this, GraphListActivity2.class);
			break;
		case R.id.lt_hangzhou:
			intent.putExtra("extraKey", "fkId");
			intent.putExtra("extraValue", "1119");
			intent.putExtra("extraKey2", "pament_business");
			intent.putExtra("extraValue2","杭州");
			intent.setClass(UserPerceptionActivity2.this, GraphListActivity2.class);
			break;
		case R.id.lt_shaoxing:
			intent.putExtra("extraKey", "fkId");
			intent.putExtra("extraValue", "1119");
			intent.putExtra("extraKey2", "pament_business");
			intent.putExtra("extraValue2","绍兴");
			intent.setClass(UserPerceptionActivity2.this, GraphListActivity2.class);
			break;
		case R.id.lt_ningbo:
			intent.putExtra("extraKey", "fkId");
			intent.putExtra("extraValue", "1119");
			intent.putExtra("extraKey2", "pament_business");
			intent.putExtra("extraValue2","宁波");
			intent.setClass(UserPerceptionActivity2.this, GraphListActivity2.class);
			break;
		case R.id.lt_zhoushan:
			intent.putExtra("extraKey", "fkId");
			intent.putExtra("extraValue", "1119");
			intent.putExtra("extraKey2", "pament_business");
			intent.putExtra("extraValue2","舟山");
			intent.setClass(UserPerceptionActivity2.this, GraphListActivity2.class);
			break;
		case R.id.lt_taizhou:
			intent.putExtra("extraKey", "fkId");
			intent.putExtra("extraValue", "1119");
			intent.putExtra("extraKey2", "pament_business");
			intent.putExtra("extraValue2","台州");
			intent.setClass(UserPerceptionActivity2.this, GraphListActivity2.class);
			break;
		case R.id.lt_jinhua:
			intent.putExtra("extraKey", "fkId");
			intent.putExtra("extraValue", "1119");
			intent.putExtra("extraKey2", "pament_business");
			intent.putExtra("extraValue2","金华");
			intent.setClass(UserPerceptionActivity2.this, GraphListActivity2.class);
			break;
		case R.id.lt_quzhou:
			intent.putExtra("extraKey", "fkId");
			intent.putExtra("extraValue", "1119");
			intent.putExtra("extraKey2", "pament_business");
			intent.putExtra("extraValue2","衢州");
			intent.setClass(UserPerceptionActivity2.this, GraphListActivity2.class);
			break;
		case R.id.lt_lishui:
			intent.putExtra("extraKey", "fkId");
			intent.putExtra("extraValue", "1119");
			intent.putExtra("extraKey2", "pament_business");
			intent.putExtra("extraValue2","丽水");
			intent.setClass(UserPerceptionActivity2.this, GraphListActivity2.class);
			break;
		case R.id.lt_wenzhou:
			intent.putExtra("extraKey", "fkId");
			intent.putExtra("extraValue", "1119");
			intent.putExtra("extraKey2", "pament_business");
			intent.putExtra("extraValue2","温州");
			intent.setClass(UserPerceptionActivity2.this, GraphListActivity2.class);
			break;
		case R.id.lt_cornofright:
			intent.putExtra("extraKey", "fkId");
			intent.putExtra("extraValue", "1120");
			intent.setClass(UserPerceptionActivity2.this, GraphListActivity2.class);
			break;
			
		default:
			break;
		}
		startActivity(intent);
	}
	

}
