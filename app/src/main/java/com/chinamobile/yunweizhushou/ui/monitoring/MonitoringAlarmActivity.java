	package com.chinamobile.yunweizhushou.ui.monitoring;

	import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.RechargeFunctionGraphBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.bean.StereoMonitorBean1;
import com.chinamobile.yunweizhushou.bean.StereoMonitorBean2;
import com.chinamobile.yunweizhushou.bean.StereoMonitorBean3;
import com.chinamobile.yunweizhushou.bean.StereoMonitoringFirstBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.adapter.RechargeFunctionListAdapter;
import com.chinamobile.yunweizhushou.ui.adapter.StereoMonitoringAdapter1;
import com.chinamobile.yunweizhushou.ui.adapter.StereoMonitoringAdapter2;
import com.chinamobile.yunweizhushou.ui.adapter.StereoMonitoringAdapter3;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.BottomScrollView;
import com.chinamobile.yunweizhushou.view.DashboardView;
import com.chinamobile.yunweizhushou.view.HighlightCR;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MonitoringAlarmActivity  extends BaseActivity implements OnClickListener{

	private TextView alarm_tv1,alarm_tv2,alarm_tv3,alarm_tv1_1,alarm_tv2_1,alarm_tv3_1,
	                 yewuceng_tv,jiekouceng_tv,yingyongceng_tv,db_tv,
	                 yewuceng_tv2,jiekouceng_tv2,yingyongceng_tv2,db_tv2;
	private LinearLayout chooselt1,chooselt2,chooselt3,choose_gaojing,choose_xingneng,choose_duixiangjuzheng,navigationBar1,navigationBar2,
	                    yewuceng_lt,jiekouceng_lt,yingyongceng_lt,db_lt;
	private ImageView strangle1,strangle2,strangle3;
	private BottomScrollView mScrollView;
	private String serious,general,main;
	private DashboardView dashboardView;
	private List<StereoMonitorBean1> mList;
	private List<StereoMonitorBean3> mList2;
	private List<StereoMonitorBean2> mList4;
	private StereoMonitoringAdapter1 mAdapter;
	private StereoMonitoringAdapter2 mAdapter3;
	private StereoMonitoringAdapter3 mAdapter4;
	private ListView mListView;
	private String name,app,busi,inter,db,value;
	private String chooseType="serious";
	private StereoMonitoringFirstBean smfb;
	private RechargeFunctionGraphBean beans2;
	private RechargeFunctionListAdapter adapter2;
	private int  changeNavigationBar=0;
	private  boolean isSvToBottom=false;
	private float mLastX;
    private float mLastY;
    private TextView warning_tv1,warning_tv2;
    private  boolean scrollFlag;
    /**
     * listview竖向滑动的阈值
     */
    private static final int THRESHOLD_Y_LIST_VIEW = 50;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		name=getIntent().getStringExtra("name");
		app=getIntent().getStringExtra("app");
		busi=getIntent().getStringExtra("busi");
		inter=getIntent().getStringExtra("inter");
		db=getIntent().getStringExtra("db");		
		value=getIntent().getStringExtra("value");		
		setContentView(R.layout.activity_monitoring_alarm);
		initView();
		initDashBoard();
		initEvent();
		initRequest1_1();
		initRequest1_2();
	}

	private void initEvent() {
		getTitleBar().setMiddleText(name);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		chooselt1.setOnClickListener(this);
		chooselt2.setOnClickListener(this);
		chooselt3.setOnClickListener(this);
		choose_gaojing.setOnClickListener(this);
		choose_xingneng.setOnClickListener(this);
		choose_duixiangjuzheng.setOnClickListener(this);
		yewuceng_lt.setOnClickListener(this);
		jiekouceng_lt.setOnClickListener(this);
		yingyongceng_lt.setOnClickListener(this);
		db_lt.setOnClickListener(this);
		mScrollView.setScrollToBottomListener(new BottomScrollView.OnScrollToBottomListener() {
			
			@Override
			public void onScrollToBottom() {
				isSvToBottom=true;
				
			}
			
			@Override
			public void onNotScrollToBottom() {
				//isSvToBottom=false;
				
			}
		});
		
		 // ListView滑动冲突解决
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    mLastY = event.getY();
                }
                if(action == MotionEvent.ACTION_MOVE) {
//                    int top = mListView.getChildAt(0).getTop();
//                    float nowY = event.getY();
                    if(isSvToBottom) {
                        // 允许scrollview拦截点击事件, scrollView滑动
                        mScrollView.requestDisallowInterceptTouchEvent(true);
                    	mListView.requestDisallowInterceptTouchEvent(false);
                    }
//                    }else if(top == 0 && nowY - mLastY > THRESHOLD_Y_LIST_VIEW) {
//                        // 允许scrollview拦截点击事件, scrollView滑动
//                        mScrollView.requestDisallowInterceptTouchEvent(false);
//                    } 
//                    else {
//                        // 不允许scrollview拦截点击事件， listView滑动
//                        mScrollView.requestDisallowInterceptTouchEvent(true);
//                    }
                }
                return false;
            }

			
        });
		mListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				  //判断状态  
                switch (scrollState) {  
                    // 当不滚动时  
                    case OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                        scrollFlag = false;  
                        mScrollView.requestDisallowInterceptTouchEvent(true);
                    	mListView.requestDisallowInterceptTouchEvent(false);
                        // 判断滚动到底部 、position是从0开始算起的  
                        if (mListView.getLastVisiblePosition() == (mListView  
                                .getCount() - 1)) {  
  
                            //TODO  
  
                        }  
                        // 判断滚动到顶部  
                        if (mListView.getFirstVisiblePosition() == 0) {  
                        	
                        	mScrollView.requestDisallowInterceptTouchEvent(false);
                        	mListView.requestDisallowInterceptTouchEvent(true);
                        }  
  
                        break;  
                    case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                    	mScrollView.requestDisallowInterceptTouchEvent(true);
                    	mListView.requestDisallowInterceptTouchEvent(false);
                        scrollFlag = true;  
                        break;  
                    case OnScrollListener.SCROLL_STATE_FLING:
                         // 当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时，即滚动时  
                    	mScrollView.requestDisallowInterceptTouchEvent(true);
                    	mListView.requestDisallowInterceptTouchEvent(false);
                                   scrollFlag = true;  
                        break;  
                }  

				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void initDashBoard() {
		 dashboardView.setArcColor(getResources().getColor(android.R.color.black));
		 List<HighlightCR> highlight2 = new ArrayList<>();
	        highlight2.add(new HighlightCR(120, 180, Color.parseColor("#EE0000")));
	        highlight2.add(new HighlightCR(300, 60, Color.parseColor("#EEEE00")));
	        highlight2.add(new HighlightCR(360, 60, Color.parseColor("#66CD00")));
	        
	        dashboardView.setStripeHighlightColorAndRange(highlight2);
		
	}
	

	private void initRequest1_1() {
		HashMap<String, String> map0 = new HashMap<String,String>();
		map0.put("action", "getTotalOfWarning");
		map0.put("system", name);
		startTask(HttpRequestEnum.enum_monitoring_alarm_stere, ConstantValueUtil.URL+"StereoMonitoring", map0,true);
					
	}
	
	private void initRequest1_2() {
		HashMap<String, String> map1 = new HashMap<String,String>();
		map1.put("action", "getListOfWarning");
		map1.put("system", name);
		map1.put("type", chooseType);
		startTask(HttpRequestEnum.enum_monitoring_alarm_stere_list, ConstantValueUtil.URL+"StereoMonitoring", map1,true);
		
	}
	private void initRequest2_1() {
		HashMap<String, String> map0 = new HashMap<String,String>();
		map0.put("action", "getTotalOfPerformance");
		map0.put("system", name);
		startTask(HttpRequestEnum.enum_monitoring_alarm_xingneng, ConstantValueUtil.URL+"StereoMonitoring", map0,true);		
		
	}
	
	private void initRequest2_2(String xntype) {
		HashMap<String, String> map1 = new HashMap<>();
		map1.put("action", "waveGraph");
		map1.put("time", "6h");
		map1.put("region_code", "");
		map1.put("pament_business", "");
		map1.put("fkId",xntype);
		startTask(HttpRequestEnum.enum_monitoring_alarm_xingneng_list, ConstantValueUtil.URL + "BusiFluct?", map1, true);
		
	}
	private void initRequest3_1() {
		HashMap<String, String> map0 = new HashMap<String,String>();
		map0.put("action", "getTotalOfMatrix");
		map0.put("system", name);
		startTask(HttpRequestEnum.enum_monitoring_alarm_duixiangjuzheng, ConstantValueUtil.URL+"StereoMonitoring", map0,true);		
		
	}
	
	private void initRequest3_2() {
		HashMap<String, String> map1 = new HashMap<>();
		map1.put("action", "getListOfClusterHost");
		map1.put("system", name);
		startTask(HttpRequestEnum.enum_monitoring_alarm_duixiangjuzheng_list, ConstantValueUtil.URL + "StereoMonitoring?", map1, true);
		
	}
	private void initRequest3_3() {
		HashMap<String, String> map1 = new HashMap<>();
		map1.put("action", "getListOfMatrixProcess");
		map1.put("system", name);
		startTask(HttpRequestEnum.enum_monitoring_alarm_duixiangjuzheng_list2, ConstantValueUtil.URL + "StereoMonitoring?", map1, true);
		
	}
	

	private void initView() {
		mScrollView= (BottomScrollView) findViewById(R.id.ama_scrollView);
	
		mScrollView.scrollTo(0, 0);
		mScrollView.smoothScrollTo(0, 0); 
		alarm_tv1=(TextView) findViewById(R.id.alarm_tv1);
		alarm_tv2=(TextView) findViewById(R.id.alarm_tv2);
		alarm_tv3=(TextView) findViewById(R.id.alarm_tv3);
		alarm_tv1_1=(TextView) findViewById(R.id.alarm_tv1_1);
		alarm_tv2_1=(TextView) findViewById(R.id.alarm_tv2_1);
		alarm_tv3_1=(TextView) findViewById(R.id.alarm_tv3_1);
		yewuceng_tv=(TextView) findViewById(R.id.yewuceng_tv);
		jiekouceng_tv=(TextView) findViewById(R.id.jiekouceng_tv);
		yingyongceng_tv=(TextView) findViewById(R.id.yingyongceng_tv);
		db_tv=(TextView) findViewById(R.id.db_tv);
		yewuceng_tv2=(TextView) findViewById(R.id.yewuceng_tv2);
		jiekouceng_tv2=(TextView) findViewById(R.id.jiekouceng_tv2);
		yingyongceng_tv2=(TextView) findViewById(R.id.yingyongceng_tv2);
		warning_tv1=(TextView) findViewById(R.id.warning_tv1);
		warning_tv2=(TextView) findViewById(R.id.warning_tv2);
		db_tv2=(TextView) findViewById(R.id.db_tv2);
		chooselt1=(LinearLayout) findViewById(R.id.chooselt1);
		chooselt2=(LinearLayout) findViewById(R.id.chooselt2);
		chooselt3=(LinearLayout) findViewById(R.id.chooselt3);
		yewuceng_lt=(LinearLayout) findViewById(R.id.yewuceng_lt);
		jiekouceng_lt=(LinearLayout) findViewById(R.id.jiekouceng_lt);
		yingyongceng_lt=(LinearLayout) findViewById(R.id.yingyongceng_lt);
		db_lt=(LinearLayout) findViewById(R.id.db_lt);
		choose_gaojing=(LinearLayout) findViewById(R.id.choose_gaojing);
		choose_xingneng=(LinearLayout) findViewById(R.id.choose_xingneng);
		choose_duixiangjuzheng=(LinearLayout) findViewById(R.id.choose_duixiangjuzheng);
		navigationBar1=(LinearLayout) findViewById(R.id.navigationBar1);
		navigationBar2=(LinearLayout) findViewById(R.id.navigationBar2);
		strangle1=(ImageView) findViewById(R.id.strangle1);
		strangle2= (ImageView) findViewById(R.id.strangle2);
		strangle3=(ImageView) findViewById(R.id.strangle3);
		dashboardView=(DashboardView) findViewById(R.id.dashboard_view);
		if("".equals(value)){
			dashboardView.setRealTimeValue(0);
		}else{
			dashboardView.setRealTimeValue(Integer.parseInt(value));
		}		
		mListView= (ListView) findViewById(R.id.monitoring_alarm_listView);
		alarm_tv1.setTextColor(getResources().getColor(R.color.color_white));
		alarm_tv1_1.setTextColor(getResources().getColor(R.color.color_white));
		
	}
	

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		
		
		switch (e) {
		case enum_monitoring_alarm_stere:
			if(responseBean.isSuccess()){
				try {
					JSONObject jo1=new JSONObject(responseBean.getDATA());
					serious=jo1.getString("serious");
					main=jo1.getString("main");
					general=jo1.getString("general");	
					alarm_tv1.setText(serious);
					alarm_tv2.setText(serious);
					alarm_tv3.setText(general);
					
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
			break;
		case enum_monitoring_alarm_stere_list:
				if(responseBean.isSuccess()){
					Type t = new TypeToken<List<StereoMonitorBean1>>(){}.getType();
					mList=new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
					mAdapter=new StereoMonitoringAdapter1(this, mList, R.layout.item_stereo_monitor);
					mAdapter.notifyDataSetChanged();
					mListView.setAdapter(mAdapter);					
			}else{
				warning_tv1.setVisibility(View.VISIBLE);
			}
			break;
		case enum_monitoring_alarm_xingneng:
			if(responseBean.isSuccess()){
				try {
					JSONObject jo1=new JSONObject(responseBean.getDATA());
					String business=jo1.getString("business");
					String interface2=jo1.getString("interface");
					String application=jo1.getString("application");	
					String db=jo1.getString("db");	
					yewuceng_tv.setText(business);
					jiekouceng_tv.setText(interface2);
					yingyongceng_tv.setText(application);
					db_tv.setText(db);
					
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
			break;
		case enum_monitoring_alarm_xingneng_list:
			if(responseBean.isSuccess()){
				Type t2 = new TypeToken<RechargeFunctionGraphBean>() {
				}.getType();
				beans2 = new Gson().fromJson(responseBean.getDATA(), t2);
				adapter2 = new RechargeFunctionListAdapter(this, beans2.getItemsList());
				//adapter2.notifyDataSetChanged();
				mListView.setAdapter(adapter2);
			}else{
				warning_tv2.setVisibility(View.VISIBLE);
			}
			break;
		case enum_monitoring_alarm_duixiangjuzheng:
			if(responseBean.isSuccess()){
				try {
					JSONObject jo11 = new JSONObject(responseBean.getDATA());
					String host=jo11.getString("host");
					String process=jo11.getString("process");
					String cluster=jo11.getString("cluster");
					alarm_tv1.setText(host);
					alarm_tv2.setText(process);
					alarm_tv3.setText(cluster);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			break;
		case enum_monitoring_alarm_duixiangjuzheng_list:
			if(responseBean.isSuccess()){
				Type t = new TypeToken<List<StereoMonitorBean3>>(){}.getType();
				mList2=new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				mAdapter3=new StereoMonitoringAdapter2(this, mList2, R.layout.item_stereomonitoring_progressbar2);
				mAdapter3.notifyDataSetChanged();
				mListView.setAdapter(mAdapter3);					
		    }else{
				warning_tv1.setVisibility(View.VISIBLE);
			}
			break;
		case enum_monitoring_alarm_duixiangjuzheng_list2:
			if(responseBean.isSuccess()){
				Type t = new TypeToken<List<StereoMonitorBean2>>(){}.getType();
				mList4=new Gson().fromJson(Utils.getJsonArray(responseBean.getDATA()), t);
				mAdapter4=new StereoMonitoringAdapter3(this, mList4, R.layout.item_steremonitoring_dxjuz_2);
				mAdapter4.notifyDataSetChanged();
				mListView.setAdapter(mAdapter4);					
			}else{
				warning_tv1.setVisibility(View.VISIBLE);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		restAll();
		if(v.getId()==R.id.choose_gaojing || v.getId()==R.id.choose_xingneng || v.getId()==R.id.choose_duixiangjuzheng){
			missTrangle();
		}	
		switch (v.getId()) {
		case R.id.chooselt1:
			mListView.setAdapter(null); 
			alarm_tv1.setTextColor(getResources().getColor(R.color.color_white));
			alarm_tv1_1.setTextColor(getResources().getColor(R.color.color_white));
			chooselt1.setBackgroundResource(R.drawable.corner_rectangle_deepblue_bg2);
			if(changeNavigationBar==1){
				initRequest3_2();
			}else{
				chooseType="serious";
				
				initRequest1_2();
			}
			break;
		case R.id.chooselt2:
			mListView.setAdapter(null); 
			alarm_tv2.setTextColor(getResources().getColor(R.color.color_white));
			alarm_tv2_1.setTextColor(getResources().getColor(R.color.color_white));
			chooselt2.setBackgroundResource(R.drawable.corner_rectangle_deepblue_bg2);
			if(changeNavigationBar==1){
				
				initRequest3_3();
			}else{
		     	chooseType="main";		
			    initRequest1_2();
			}
			break;
		case R.id.chooselt3:
			mListView.setAdapter(null); 
			alarm_tv3.setTextColor(getResources().getColor(R.color.color_white));
			alarm_tv3_1.setTextColor(getResources().getColor(R.color.color_white));
			chooselt3.setBackgroundResource(R.drawable.corner_rectangle_deepblue_bg2);
			if(changeNavigationBar!=1){
				chooseType="general";				
				initRequest1_2();	
			}else{
				mListView.setAdapter(null); 
				warning_tv1.setVisibility(View.VISIBLE);
			}			
			break;
		case R.id.yewuceng_lt:
			mListView.setAdapter(null); 
			yewuceng_tv.setTextColor(getResources().getColor(R.color.color_white));
			yewuceng_tv2.setTextColor(getResources().getColor(R.color.color_white));
			yewuceng_lt.setBackgroundResource(R.drawable.corner_rectangle_deepblue_bg2);
			initRequest2_2(busi);
			break;
		case R.id.jiekouceng_lt:
			mListView.setAdapter(null); 
			jiekouceng_tv.setTextColor(getResources().getColor(R.color.color_white));
			jiekouceng_tv2.setTextColor(getResources().getColor(R.color.color_white));
			jiekouceng_lt.setBackgroundResource(R.drawable.corner_rectangle_deepblue_bg2);
			initRequest2_2(inter);
			break;
		case R.id.yingyongceng_lt:
			mListView.setAdapter(null); 
			yingyongceng_tv.setTextColor(getResources().getColor(R.color.color_white));
			yingyongceng_tv2.setTextColor(getResources().getColor(R.color.color_white));
			yingyongceng_lt.setBackgroundResource(R.drawable.corner_rectangle_deepblue_bg2);
			initRequest2_2(app);
			break;
		case R.id.db_lt:
			mListView.setAdapter(null); 
			db_tv.setTextColor(getResources().getColor(R.color.color_white));
			db_tv2.setTextColor(getResources().getColor(R.color.color_white));
			db_lt.setBackgroundResource(R.drawable.corner_rectangle_deepblue_bg2);
			initRequest2_2(db);
			break;
		case R.id.choose_gaojing:
			mListView.setAdapter(null); 
			changeNavigationBar=0;
			chooseType="serious";
			strangle1.setVisibility(View.VISIBLE);
			navigationBar1.setVisibility(View.VISIBLE);
			navigationBar2.setVisibility(View.GONE);
			alarm_tv1.setTextColor(getResources().getColor(R.color.color_white));
			alarm_tv1_1.setTextColor(getResources().getColor(R.color.color_white));
			chooselt1.setBackgroundResource(R.drawable.corner_rectangle_deepblue_bg2);
			initRequest1_1();
			initRequest1_2();
			break;
		case R.id.choose_xingneng:
			mListView.setAdapter(null); 
			strangle2.setVisibility(View.VISIBLE);
			navigationBar1.setVisibility(View.GONE);
			navigationBar2.setVisibility(View.VISIBLE);
			yewuceng_lt.setBackgroundResource(R.drawable.corner_rectangle_deepblue_bg2);
			yewuceng_tv.setTextColor(getResources().getColor(R.color.color_white));
			yewuceng_tv2.setTextColor(getResources().getColor(R.color.color_white));
			initRequest2_1();
			initRequest2_2(busi);
			break;
		case R.id.choose_duixiangjuzheng:
			mListView.setAdapter(null); 
			changeNavigationBar=1;
			strangle3.setVisibility(View.VISIBLE);
			navigationBar1.setVisibility(View.VISIBLE);
			navigationBar2.setVisibility(View.GONE);
			alarm_tv1_1.setText("主机数");
			alarm_tv2_1.setText("进程数");
			alarm_tv3_1.setText("集群数");
			alarm_tv1.setTextColor(getResources().getColor(R.color.color_white));
			alarm_tv1_1.setTextColor(getResources().getColor(R.color.color_white));
			chooselt1.setBackgroundResource(R.drawable.corner_rectangle_deepblue_bg2);
			initRequest3_1();
			initRequest3_2();
			break;

		default:
			break;
		}
	
		
	}

	private void restAll() {
		alarm_tv1.setTextColor(getResources().getColor(R.color.color_black));
		alarm_tv1_1.setTextColor(getResources().getColor(R.color.color_black));
		alarm_tv2.setTextColor(getResources().getColor(R.color.color_black));
		alarm_tv2_1.setTextColor(getResources().getColor(R.color.color_black));
		alarm_tv3.setTextColor(getResources().getColor(R.color.color_black));
		alarm_tv3_1.setTextColor(getResources().getColor(R.color.color_black));
		yewuceng_tv.setTextColor(getResources().getColor(R.color.color_black));
		yewuceng_tv2.setTextColor(getResources().getColor(R.color.color_black));
		jiekouceng_tv.setTextColor(getResources().getColor(R.color.color_black));
		jiekouceng_tv2.setTextColor(getResources().getColor(R.color.color_black));
		yingyongceng_tv.setTextColor(getResources().getColor(R.color.color_black));
		yingyongceng_tv2.setTextColor(getResources().getColor(R.color.color_black));
		db_tv.setTextColor(getResources().getColor(R.color.color_black));
		db_tv2.setTextColor(getResources().getColor(R.color.color_black));
		warning_tv1.setVisibility(View.GONE);
		warning_tv2.setVisibility(View.GONE);
		chooselt1.setBackgroundResource(0);
		chooselt2.setBackgroundResource(0);
		chooselt3.setBackgroundResource(0);
		yewuceng_lt.setBackgroundResource(0);
		jiekouceng_lt.setBackgroundResource(0);
		yingyongceng_lt.setBackgroundResource(0);
		db_lt.setBackgroundResource(0);
		
		
	}
	private void missTrangle(){
		strangle1.setVisibility(View.GONE);
		strangle2.setVisibility(View.GONE);
		strangle3.setVisibility(View.GONE);
	}
	
	
	
	
	
	
	

}
