package com.chinamobile.yunweizhushou.ui.selfRepair;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.common.MainApplication;
import com.chinamobile.yunweizhushou.ui.selfRepair.bean.SelfRepairBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelfRepairActivity extends BaseActivity implements OnClickListener{

	private ListView gzzy_listview;
	private List<SelfRepairBean> mList;
	private SelfRepairAdapter3 mAdapter;
	private JSONArray self_desc;
	List<String> list1,list2,list3,list4;
	private ScrollView mScrollView;
	private AlertDialog alertDialog;
	private TextView tv_phone,tv_name;
	private  ImageView img_charge_people;
	
	private TextView  gzzy_tv1,gzzy_tv2,gzzy_tv3,gzzy_tv4,more,
					gzzy_top3_tv1_1,gzzy_top3_tv1_2,gzzy_top3_tv1_3,gzzy_top3_tv1_4,gzzy_top3_tv1_5,gzzy_top3_tv1_6,
					gzzy_top3_tv2_1,gzzy_top3_tv2_2,gzzy_top3_tv2_3,gzzy_top3_tv2_4,gzzy_top3_tv2_5,gzzy_top3_tv2_6;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guzhangziyu);
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
		gzzy_top3_tv1_1.setOnClickListener(this);
		gzzy_top3_tv1_2.setOnClickListener(this);
		gzzy_top3_tv1_3.setOnClickListener(this);
	
		gzzy_top3_tv2_1.setOnClickListener(this);
		gzzy_top3_tv2_2.setOnClickListener(this);
		gzzy_top3_tv2_3.setOnClickListener(this);
		more.setOnClickListener(this);
	
		
	}

	private void initRequest() {
		HashMap maps=new HashMap<String,String>();
		maps.put("action", "first");
		startTask(HttpRequestEnum.enum_guzhangziyu,ConstantValueUtil.URL + "ToubleSelfRecovery?", maps, true);
		
		HashMap map2=new HashMap<String,String>();
		map2.put("action", "getChargerOfModule");
		map2.put("id", "1043");
		startTask(HttpRequestEnum.enum_charge_people, ConstantValueUtil.URL + "User?", map2, true);
		
	}
	private void initRequest2(String ip) {
		HashMap maps=new HashMap<String,String>();
		maps.put("action", "getDetailsOfHost");
		maps.put("key",ip);
		startTask(HttpRequestEnum.enum_guzhangziyu_dialog,ConstantValueUtil.URL + "ToubleSelfRecovery?", maps, true);
		
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
				
				JSONObject jo;
				try {
					jo = new JSONObject(responseBean.getDATA());
					list1=new ArrayList<String>();
					list2=new ArrayList<String>();
					list3=new ArrayList<String>();
					list4=new ArrayList<String>();
				
					String guardian_days=jo.getString("guardian_days");
					String most_date=jo.getString("most_date");
					String most_num=jo.getString("most_num");
					String selfrecovery_num=jo.getString("selfrecovery_num");
					JSONArray jsonArray1 = jo.getJSONArray("self_host_front");
					self_desc = jo.getJSONArray("self_desc");
					gzzy_tv1.setText(guardian_days+"天");
					gzzy_tv2.setText(selfrecovery_num+"次");
					gzzy_tv3.setText(most_date);
					gzzy_tv4.setText(most_num+"次");
					for(int i=0;i<jsonArray1.length();i++){
						JSONObject  temp  = (JSONObject) jsonArray1.get(i);
						String name1 = temp.getString("name");
						String num1 = temp.getString("num");
						list1.add(name1);
						list2.add(num1);
						
					}
					JSONArray jsonArray2 = jo.getJSONArray("self_process_front");
					for(int i=0;i<jsonArray2.length();i++){
						JSONObject  temp  = (JSONObject) jsonArray2.get(i);
						String name2 = temp.getString("name");
						String num2 = temp.getString("num");
						list3.add(name2);
						list4.add(num2);
						
					}
					gzzy_top3_tv1_1.setText(list1.get(0));
					gzzy_top3_tv1_2.setText(list2.get(0));
					gzzy_top3_tv1_3.setText(list1.get(1));
					gzzy_top3_tv1_4.setText(list2.get(1));
					gzzy_top3_tv1_5.setText(list1.get(2));
					gzzy_top3_tv1_6.setText(list2.get(2));
					gzzy_top3_tv2_1.setText(list3.get(0));
					gzzy_top3_tv2_2.setText(list4.get(0));
					gzzy_top3_tv2_3.setText(list3.get(1));
					gzzy_top3_tv2_4.setText(list4.get(1));
					gzzy_top3_tv2_5.setText(list3.get(2));
					gzzy_top3_tv2_6.setText(list4.get(2));
				
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			
				Type t = new TypeToken<List<SelfRepairBean>>() {
				}.getType();
				mList = new Gson().fromJson(self_desc.toString(), t);
				LinearLayout.LayoutParams lp =(LinearLayout.LayoutParams) gzzy_listview.getLayoutParams();
				lp.height=((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150*mList.size(), getResources().getDisplayMetrics()));
				gzzy_listview.setLayoutParams(lp);
				//mAdapter = new SelfRepairAdapter2(this, mList, R.layout.item_guzhangziyu);
				mAdapter=new SelfRepairAdapter3();
				gzzy_listview.setAdapter(mAdapter);
			}
			break;
		case enum_guzhangziyu_dialog:
			if (responseBean.isSuccess()) {
				try {
					JSONObject  jo2=new JSONObject(responseBean.getDATA());
					String aperson=jo2.getString("aperson");
					String beservice=jo2.getString("beservice");
					String macip=jo2.getString("macip");
					String name=jo2.getString("name");
					String resdetail=jo2.getString("resdetail");
					String resthirdtype=jo2.getString("resthirdtype");
					String states=jo2.getString("states");
					String sysclass=jo2.getString("sysclass");
					String system=jo2.getString("system");
					StringBuilder sb=new StringBuilder();
					sb.append("类型:"+resthirdtype+"\n");
					sb.append("主机名:"+name+"\n");
					sb.append("资源描述:"+resdetail+"\n");
					sb.append("状态:"+states+"\n");
					sb.append("对应系统:"+system+"\n");
					sb.append("系统级别:"+sysclass+"\n");
					sb.append("责任人:"+aperson+"\n");
					sb.append("主机IP:"+macip);
					showListDialog(sb.toString());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			break;
		case enum_charge_people:
			try {
				JSONObject jo=new JSONObject(responseBean.getDATA());
				String charger=jo.getString("charger");
				String phone=jo.getString("phone");
				String url=jo.getString("picture");
				tv_name.setText(charger);
				tv_phone.setText(phone);
				if (url != null && !TextUtils.isEmpty(url)) {
					MainApplication.mImageLoader.displayImage(url, img_charge_people);
				}
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
	private void showListDialog(String str) {
		alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.show();
	    Window window = alertDialog.getWindow();  
	    window.setContentView(R.layout.custom_dialog1);  
	    TextView dialog_tv1=(TextView) window.findViewById(R.id.dialog_tv1);
	    TextView dialog_tv2=(TextView) window.findViewById(R.id.dialog_tv2);
	    dialog_tv2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				alertDialog.dismiss();
				
			}
		});
	    dialog_tv1.setText(str);
	   
	}

	private void initView() {
		gzzy_tv1=(TextView) findViewById(R.id.gzzy_tv1);
		gzzy_tv2=(TextView) findViewById(R.id.gzzy_tv2);
		gzzy_tv3=(TextView) findViewById(R.id.gzzy_tv3);
		gzzy_tv4=(TextView) findViewById(R.id.gzzy_tv4);		
		more=(TextView) findViewById(R.id.more);		
		gzzy_top3_tv1_1=(TextView) findViewById(R.id.gzzy_top3_tv1_1);
		gzzy_top3_tv1_2=(TextView) findViewById(R.id.gzzy_top3_tv1_2);
		gzzy_top3_tv1_3=(TextView) findViewById(R.id.gzzy_top3_tv1_3);
		gzzy_top3_tv1_4=(TextView) findViewById(R.id.gzzy_top3_tv1_4);
		gzzy_top3_tv1_5=(TextView) findViewById(R.id.gzzy_top3_tv1_5);
		gzzy_top3_tv1_6=(TextView) findViewById(R.id.gzzy_top3_tv1_6);
		gzzy_top3_tv2_1=(TextView) findViewById(R.id.gzzy_top3_tv2_1);
		gzzy_top3_tv2_2=(TextView) findViewById(R.id.gzzy_top3_tv2_2);
		gzzy_top3_tv2_3=(TextView) findViewById(R.id.gzzy_top3_tv2_3);
		gzzy_top3_tv2_4=(TextView) findViewById(R.id.gzzy_top3_tv2_4);
		gzzy_top3_tv2_5=(TextView) findViewById(R.id.gzzy_top3_tv2_5);
		gzzy_top3_tv2_6=(TextView) findViewById(R.id.gzzy_top3_tv2_6);
		gzzy_listview= (ListView) findViewById(R.id.gzzy_listview);
		mScrollView=(ScrollView) findViewById(R.id.mScrollView);
		img_charge_people=(ImageView) findViewById(R.id.img_charge_people);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);

	}
	// 1：有点绕，基本思路，就是让scrollView优先于childView获取到焦点，
	 private void disableAutoScrollToBottom() {
	         mScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
	         mScrollView.setFocusable(true);
	         mScrollView.setFocusableInTouchMode(true);
	         mScrollView.setOnTouchListener(new View.OnTouchListener() {
	             @Override
	             public boolean onTouch(View v, MotionEvent event) {
	                 v.requestFocusFromTouch();
	                 return false;
	             }
	         });
	     }
	 // 恢复默认状态，禁掉scrollview的focus，这样就允许childview自动滑动
	     private void enableChildAutoScrollToBottom() {
	         mScrollView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
	         mScrollView.setFocusable(false);
	         mScrollView.setFocusableInTouchMode(false);
	         mScrollView.setOnTouchListener(null);
	     }
	
	private class SelfRepairAdapter3 extends BaseAdapter{

		@Override
		public int getCount() {
			
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView==null){
				convertView=LayoutInflater.from(SelfRepairActivity.this).inflate(R.layout.item_guzhangziyu,null);
				holder = new ViewHolder();
				holder.time = (TextView) convertView.findViewById(R.id.item1);
				holder.hostip = (TextView) convertView.findViewById(R.id.item2);
				holder.server_name = (TextView) convertView.findViewById(R.id.item3);
				holder.fault_reason = (TextView) convertView.findViewById(R.id.item4);
				holder.fault_times = (TextView) convertView.findViewById(R.id.item5);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			holder.time.setText(mList.get(position).getTime());
			holder.hostip.setText(mList.get(position).getHostip());
			holder.server_name.setText(mList.get(position).getServer_name());
			holder.fault_reason.setText(mList.get(position).getFault_reason());
			holder.fault_times.setText(mList.get(position).getFault_times());
			
			return convertView;
		}
		
		private  class ViewHolder{
			private TextView fault_reason, fault_times, hostip, server_name, time;
			
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gzzy_top3_tv1_1:
			initRequest2(list1.get(0));
			break;
		case R.id.gzzy_top3_tv1_2:
			initRequest2(list1.get(1));
			break;
		case R.id.gzzy_top3_tv1_3:
			initRequest2(list1.get(2));
			break;
		case R.id.gzzy_top3_tv2_1:
			initRequest2(list3.get(0));
			break;
		case R.id.gzzy_top3_tv2_2:
			initRequest2(list3.get(1));
			break;
		case R.id.gzzy_top3_tv2_3:
			initRequest2(list3.get(2));
			break;
		case R.id.more:
			Intent intent=new Intent();
			intent.setClass(this, SelfRepairNextActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}
	

}
