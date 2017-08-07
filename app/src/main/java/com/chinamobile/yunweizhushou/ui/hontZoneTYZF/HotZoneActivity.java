package com.chinamobile.yunweizhushou.ui.hontZoneTYZF;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.hontZoneTYZF.adapter.HotZoneExpanableAdapter;
import com.chinamobile.yunweizhushou.ui.hontZoneTYZF.bean.HotZoneKTWYFailureBean;
import com.chinamobile.yunweizhushou.ui.hontZoneTYZF.bean.HotZoneParentItem;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.widget.SelectDayDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class HotZoneActivity  extends BaseActivity {
	
	private BarChart barChart;
	private TextView bt_name,tv_time;
	private String currentDate;
	private ListView lv_hotzone;
	private List<HotZoneParentItem> parentList;
	private List<Integer> indexList = new ArrayList<>();
	private List<HotZoneKTWYFailureBean> childList;
	private HotZoneExpanableAdapter mAdapter;
	private int currentIndex;
	private  String city;
	private MyHandler myHandler;
	private HashMap map;
	private View view;
	private int position;
	private int year;
	private int month;
	private int day;
	private static final int REQUEST_CODE = 0x1;
	 private static class MyHandler  extends Handler {
	        private WeakReference<Context> reference;
	        public MyHandler(Context context){
	            reference=new WeakReference<>(context);
	        }

	        @Override
	        public void handleMessage(Message msg) {
	            super.handleMessage(msg);
	            HotZoneActivity hotZoneActivity = (HotZoneActivity)reference.get();
	                switch (msg.what) {
	                    case REQUEST_CODE:
	                    	hotZoneActivity.map=(HashMap) msg.obj;
	                    	hotZoneActivity.city=(String) hotZoneActivity.map.get("city");
	                    	hotZoneActivity.view=(View) hotZoneActivity.map.get("view");
	                    	hotZoneActivity.position=(int) hotZoneActivity.map.get("position");
	                    	hotZoneActivity.initRequest4(hotZoneActivity.currentDate,hotZoneActivity.city);
	                    	
	                        break;
	                    default:
	                        break;

	                }
	            }

	        }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotzone);
		Calendar c = Calendar.getInstance();
		myHandler=new MyHandler(HotZoneActivity.this);
		year=c.get(Calendar.YEAR);
		month=c.get(Calendar.MONTH)+1;
		day=c.get(Calendar.DATE);
		currentDate = Utils.getCurrentTime();
		initView();
		initRequest();
		initReqeust2(currentDate);
		initEvent();
	}

	private void initEvent() {
		
		getTitleBar().setMiddleText("统一支付失败专区");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tv_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SelectDayDialog dialog = new SelectDayDialog(HotZoneActivity.this);
				dialog.show();
				dialog.setBirthdayListener(new SelectDayDialog.OnBirthListener() {

					@Override
					public void onClick(String year, String month, String day) {
						String currentDate1 = year + "年" + month + "月"+day+"日";
						currentDate = year + "-" + month+"-"+day;
						tv_time.setText(currentDate1);
						initReqeust2(currentDate);
					}
				});
			}
		});
		lv_hotzone.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				String city=parentList.get(position).getCity();
				Message msg = myHandler.obtainMessage();
                msg.what = REQUEST_CODE;
                HashMap maps=new HashMap<String,Object>();
                maps.put("city", city);
                maps.put("view", view);
                maps.put("position", position);
                msg.obj = maps;
                msg.sendToTarget();
				
				
				
			
				
			}
		});
			

	
	}
	

	private void initRequest() {
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("action", "findTYZFBar");
		startTask(HttpRequestEnum.enum_hotzone_findTYZFBar, ConstantValueUtil.URL + "HotZone?", maps, false);
		
	}
	private void initReqeust2(String time) {
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("action", "findTYZFCityFailure");
		maps.put("time", time);
		startTask(HttpRequestEnum.enum_hotzone_findTYZFCityFailure, ConstantValueUtil.URL + "HotZone?", maps, true);
		
	
	}
	public void initRequest4(String time,String city){
		HashMap<String, String> hashMap = new HashMap<String,String>();
		hashMap.put("action", "findTYZFFailureDes");
		hashMap.put("time", time);
		hashMap.put("city", city);
		startTask(HttpRequestEnum.enum_hotzone_findTYZFFailureDes, ConstantValueUtil.URL + "HotZone?", hashMap, false);
	}
	
	
	

	private void initView() {
		barChart = (BarChart)findViewById(R.id.bt_common);
		bt_name=(TextView) findViewById(R.id.bt_name);
		tv_time=(TextView) findViewById(R.id.tv_time);
		lv_hotzone= (ListView) findViewById(R.id.lv_hotzone);
		tv_time.setText(year+"年"+month+"月"+day+"日");
	
		
	}
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_hotzone_findTYZFBar:
			try {
				JSONObject jo=new JSONObject(responseBean.getDATA());
				JSONArray ja1= jo.getJSONArray("columns");
				JSONArray ja2= jo.getJSONArray("points");
				initbarChart();
				initonebarValue(ja1, ja2);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			break;
		case enum_hotzone_findTYZFCityFailure:
			if (responseBean.isSuccess()) {
				Type t1 = new TypeToken<List<HotZoneParentItem>>() {
				}.getType();
				String str;
				try {
					str = responseBean.getDATA();
					parentList = new Gson().fromJson(str, t1);
					mAdapter = new HotZoneExpanableAdapter(parentList, HotZoneActivity.this);
					lv_hotzone.setAdapter(mAdapter);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;
		case enum_hotzone_findTYZFFailureDes:
			if (responseBean.isSuccess()) {
				Type t2 = new TypeToken<List<HotZoneKTWYFailureBean>>() {
				}.getType();
				try {
					String str = responseBean.getDATA();
					childList= new Gson().fromJson(str, t2);
					mAdapter.showPieChart(view, position, childList);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			

			}
			break;
		default:
			break;
		}
		
	}
	
	
	
	
	private void initbarChart() {
		barChart.setDescription("");
		barChart.setPinchZoom(false);
		barChart.setDrawBarShadow(false);
		barChart.setDrawGridBackground(false);
		barChart.setDrawBorders(false);
	//	tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        Legend l = barChart.getLegend();
        l.setPosition(LegendPosition.ABOVE_CHART_RIGHT);
       // l.setTypeface(tf);
        l.setYOffset(0f);
        l.setYEntrySpace(2f);	
        l.setTextSize(10f);
        l.setEnabled(true);
        XAxis xl = barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        
      //  xl.setSpaceBetweenLabels(5);
       // xl.setEnabled(false);
//        xl.setTypeface(tf);

        YAxis leftAxis = barChart.getAxisLeft();
//        leftAxis.setTypeface(tf);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)       
        // 设置是否可以触摸
        barChart.setTouchEnabled(false);
        // 是否可以拖拽
        barChart.setDragEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.animateXY(2000,3000);

	}


private void initonebarValue(JSONArray columns,JSONArray points) throws JSONException{
	
	ArrayList<String> xVals = new ArrayList<String>();
    for (int i = 0; i <points.length(); i++) {
    	String str = points.getJSONArray(i).getString(0);
        xVals.add(str);
    }
    ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
    for(int i=0;i<points.length();i++){
    	Float value1=(float) 0;
    	if(!"".equals(points.getString(i))){
    		 value1=Float.parseFloat(points.getJSONArray(i).getString(1));
    	}
    	
    	yVals1.add(new BarEntry(value1, i));
    }
 
    BarDataSet set2 = new BarDataSet(yVals1, "2017");     
    set2.setColor(this.getResources().getColor(R.color.color_orange));
    ArrayList dataSets = new ArrayList();
    dataSets.add(set2);
    BarData data = new BarData(xVals, dataSets);
    data.setGroupSpace(80f);
//    data.setValueTypeface(tf);
   
    barChart.setData(data);
    barChart.invalidate();
}



}
