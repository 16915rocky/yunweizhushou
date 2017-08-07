package com.chinamobile.yunweizhushou.ui.fault;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.FmohNextBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.FaultManageOfHistoryNextAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaultManageOfHistoryNextActivity extends BaseActivity implements OnClickListener {

	private TextView tv1,tv2,tv_city,tv_time;
	private LinearLayout lt1,lt2,lt3;
	private ListView mlistView;
	private String key,gzNumber;
	private String city="000";
	private List<FmohNextBean> mList;
// popupWindow
	private PopupWindow popupWindow;
	private LinearLayout popupLeftMenu;
	private LinearLayout popupRightMenu;
	private int selectId;
	private FaultManageOfHistoryNextAdapter mAdapter;
	private int position3;
	private boolean iscity;
	private String[] cityStr=new String[]{"全省","衢州","杭州","湖州","嘉兴","宁波","绍兴","温州","台州","丽水","金华","舟山"};
	private String[] timeStr=new String[]{"5m","10m","30m","60m"};
	//000 全省；570衢州；571 杭州；572湖州；573嘉兴；574 宁波；575绍兴；577温州；576 台州；578丽水；579金华；580舟山
	private String queryCity="000",queryTime="60m";
	private Map<String,String> maps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		key=getIntent().getStringExtra("key");
		gzNumber=getIntent().getStringExtra("gzNumber");
	    setContentView(R.layout.activity_faultmanage_ofhistorynext);
		initView();
		initData();
		initRequest();
		initEvent();
	}


	private void initData() {
		maps= new HashMap<String,String>();
		maps.put("全省", "000");
		maps.put("衢州", "570");
		maps.put("杭州", "571");
		maps.put("湖州", "572");
		maps.put("嘉兴", "573");
		maps.put("宁波", "574");
		maps.put("绍兴", "575");
		maps.put("温州", "576");
		maps.put("台州", "577");
		maps.put("丽水", "578");
		maps.put("金华", "579");
		maps.put("舟山", "580");
		
	}


	private void initEvent() {
		getTitleBar().setMiddleText("故障");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		lt1.setOnClickListener(this);
		lt2.setOnClickListener(this);
		lt3.setOnClickListener(this);
			
		
		
	}

	private void initRequest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("action", "getCountOfConsult");
		map.put("key", key);//微信
		map.put("time", queryTime);//1500m
		map.put("city", queryCity);//000
		map.put("gzNumber", gzNumber);
		startTask(HttpRequestEnum.enum_faultmanage_unsolve_next, ConstantValueUtil.URL + "Broadcast?", map,true);
		
	}

	private void initView() {
		lt1= (LinearLayout) findViewById(R.id.fmohnext_table1);
		lt2= (LinearLayout) findViewById(R.id.fmohnext_table2);
		lt3= (LinearLayout) findViewById(R.id.fmohnext_query);
		tv_city=(TextView) findViewById(R.id.fmohnext_city);
		tv_time=(TextView) findViewById(R.id.fmohnext_time);
		mlistView=(ListView) findViewById(R.id.fmohnext_listview);
		
	}
	
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);

		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_faultmanage_unsolve_next:
			if (responseBean.isSuccess()) {
				try {
					JSONObject jo=new JSONObject(responseBean.getDATA());
					String total=jo.getString("total");
					Toast.makeText(this, "故障公告数"+total+"个", Toast.LENGTH_SHORT).show();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Type type = new TypeToken<List<FmohNextBean>>() {
				}.getType();
				mList = new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), type);		
				mAdapter=new FaultManageOfHistoryNextAdapter(this, mList, R.layout.item_list_3);
				mlistView.setAdapter(mAdapter);

			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fmohnext_table1:
			showRightPopupWindow(cityStr);
			iscity=true;
			
			break;
		case R.id.fmohnext_table2:
			showRightPopupWindow(timeStr);
			iscity=false;
			break;
		case R.id.fmohnext_query:
			String citys=(String) tv_city.getText();
			queryTime=(String) tv_time.getText();
			queryCity=maps.get(citys);
			
			if(queryCity!=null && queryTime!=null){
				initRequest();
			}else{
				Toast.makeText(this, "请选择选项", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
		
	}
	
	/**
	 * 菜单点击事件(左右Tab共同点击事件)
	 */
	private class OnSubMenuClickListener implements OnClickListener {

		private int position2;

		public OnSubMenuClickListener(int position) {
			position3 = position;
			this.position2 = position;

		}

		// 点击业务和地市菜单选择项
		@Override
		public void onClick(View view) {
			
			if(iscity){
				String cityName = cityStr[position2];
				tv_city.setText(cityName);
			}else{
				String time= timeStr[position2];
				tv_time.setText(time);
			}
			
			popupWindow.dismiss();
		}
	}
	
	/**
	 * 地市生成菜单
	 * 
	 * @param
	 * @param rightMenu
	 */
	private void initRightMenu(String[] str, LinearLayout rightMenu, String a) {
		rightMenu.removeAllViews();
		for (int i = 0; i <str.length; i++) {
			View subView = View.inflate(FaultManageOfHistoryNextActivity.this, R.layout.activity_backlog_zong_menu_right, null);
			TextView textView = (TextView) subView.findViewById(R.id.backlogZongRightMenu);
			textView.setText(str[i]);
			textView.setOnClickListener(new OnSubMenuClickListener(i));
			textView.setBackgroundColor(Color.WHITE);
			if (i == str.length - 1) {
				View v = subView.findViewById(R.id.rightMenuLine);
				v.setVisibility(View.GONE);
			}
			rightMenu.addView(subView);
		}
	}
	// 地市菜单
	private void showRightPopupWindow(String[] str) {
		View myView = View.inflate(this, R.layout.item_backlog_zong_popup, null);
		popupLeftMenu = (LinearLayout) myView.findViewById(R.id.backlogPopupLeftMenu);
		popupRightMenu = (LinearLayout) myView.findViewById(R.id.backlogZongFragment);
		ScrollView scrollView = (ScrollView) myView.findViewById(R.id.leftScrollView);
		scrollView.setVisibility(View.GONE);

		initRightMenu(str, popupRightMenu, null);

		popupWindow = new PopupWindow(myView, LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, true);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		popupWindow.setBackgroundDrawable(dw);
		popupWindow.showAsDropDown(lt1, 0, 10);
//		popupWindow.setOnDismissListener(new OnDismissListener() {
//
//			@Override
//			public void onDismiss() {
//				Animation animation = AnimationUtils.loadAnimation(FaultManageOfHistoryNextActivity.this,
//						R.anim.backlog_zong_tab_close);
//				animation.setFillAfter(true);
//				//tabRightImage.startAnimation(animation);
//			}
//		});
	}
}
