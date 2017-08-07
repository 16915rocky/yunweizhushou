package com.chinamobile.yunweizhushou.ui.userPerceptionIndex;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.userPerceptionIndex.adapter.KeiExpanableListViewAdapter;
import com.chinamobile.yunweizhushou.ui.userPerceptionIndex.bean.KeiBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyexpanableListView;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserPerceptionIndexNextActivity  extends BaseActivity {
	
	private BarChart barChart;
	private MyexpanableListView    expandListview;
	private List<List<KeiBean>> childList;
	private int currentIndex;
	private KeiExpanableListViewAdapter mAdapter;
	private List<Integer> indexList = new ArrayList<>();
	private List<KeiBean> parentList;
	private int height=0;
	private String KEIName;
	private View view_line;
	private String position,classId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		KEIName=getIntent().getStringExtra("KEIName");
		position=getIntent().getStringExtra("position");
		classId=getIntent().getStringExtra("classId");
		setContentView(R.layout.activity_user_perception_index_next);
		initView();
		initData();
		if("0".equals(position)){
			initReqeust2();
		}else{
			expandListview.setVisibility(View.GONE);
		}
		initReqeust(classId);
		initEvent();
	}

	private void initEvent() {
		getTitleBar().setMiddleText(KEIName);
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		

		expandListview.setOnGroupClickListener(new MyexpanableListView.OnGroupClickListener() {

			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				if (expandListview.isGroupExpanded(groupPosition)) { // 监听父item是不是展开的
					expandListview.collapseGroup(groupPosition);
				} else {
					String class_id = parentList.get(groupPosition).getClass_id();
					Log.i("ip", class_id);
					for (int i = 0; i < indexList.size(); i++) {
						if (groupPosition == indexList.get(i)) {
							return false;
						}
					}
					currentIndex = groupPosition;
					indexList.add(groupPosition);
					initReqeust3(class_id);

				}
				return false;
			}

			

			
		});
		
	}
	private void initReqeust(String classId) {
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("action", "barGraph");
		maps.put("classId", classId);
		
		startTask(HttpRequestEnum.enum_2017kei_barGraph, ConstantValueUtil.URL + "KEIServlet?", maps, true);
		
		
	}
	private void initReqeust2() {
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("action", "getList");
		startTask(HttpRequestEnum.enum_2017kei_expandlistView, ConstantValueUtil.URL + "KEIServlet?", maps, true);
		
		
	}
	
	private void initReqeust3(String class_id) {
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("action", "childList");
		maps.put("class_id", class_id);
		startTask(HttpRequestEnum.enum_2017kei_expandlistView_child, ConstantValueUtil.URL + "KEIServlet?", maps, false);
		
	}
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_2017kei_barGraph:
			try {
				JSONObject jo=new JSONObject(responseBean.getDATA());
				JSONArray ja1= jo.getJSONArray("columns");
				JSONArray ja2= jo.getJSONArray("points");
				initbarChart();
				if("0".equals(position)){
					initbarValue(ja1, ja2);
				}else{
					initonebarValue(ja1, ja2);
				}
				initbarValue(ja1, ja2);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			break;
		case enum_2017kei_expandlistView:
			if (responseBean.isSuccess()) {
				Type t1 = new TypeToken<List<KeiBean>>() {
				}.getType();
				String str;
				try {
					str = new JSONObject(responseBean.getDATA()).getJSONArray("itemList").toString();
					parentList = new Gson().fromJson(str, t1);
//					//动态设置高度
//					View kei_parent= LayoutInflater.from(this).inflate(R.layout.item_kei_next, null);
//					kei_parent.measure(0, 0);
//					int temp1=kei_parent.getMeasuredHeight();
//					height+=temp1*parentList.size();	
//					LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) expandListview.getLayoutParams(); 
//					linearParams.height=height;
//					expandListview.setLayoutParams(linearParams);
					mAdapter = new KeiExpanableListViewAdapter(this, parentList, childList,expandListview);
					expandListview.setAdapter(mAdapter);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				Utils.ShowErrorMsg(this, responseBean.getMSG());
			}
			break;
		case enum_2017kei_expandlistView_child:
			if (responseBean.isSuccess()) {
				Type t2 = new TypeToken<List<KeiBean>>() {
				}.getType();
				// childList.clear();
				try {
					String str = new JSONObject(responseBean.getDATA()).getJSONArray("itemList").toString();
					List<KeiBean> itemList = new Gson().fromJson(str, t2);
					childList.remove(currentIndex);
					childList.add(currentIndex, itemList);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				if (mAdapter == null) {
					mAdapter = new KeiExpanableListViewAdapter(this, parentList, childList,expandListview);
					expandListview.setAdapter(mAdapter);
					
					
				} else {
					mAdapter.notifyDataSetChanged();
				}

			}
			break;
		default:
			break;
		}
		
	}
	
	private void initbarValue(JSONArray columns,JSONArray points) throws JSONException{
		
		ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i <points.length(); i++) {
        	String str = points.getJSONArray(i).getString(0);
            xVals.add(str);
        }
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        for(int i=0;i<points.length();i++){
        	Float value1=(float) 0;
        	if(!"".equals(points.getString(i))){
        		 value1=Float.parseFloat(points.getJSONArray(i).getString(1));
        	}
        	
        	yVals1.add(new BarEntry(value1, i));
        }
        for(int j=0;j<points.length();j++){
        	Float value2=(float) 0;
        	if(!"".equals(points.getString(j))){
        		value2=Float.parseFloat(points.getJSONArray(j).getString(2));
        	}
        	
        	yVals2.add(new BarEntry(value2, j));
        }
       
        // create 3 datasets with different types
        BarDataSet set1 = new BarDataSet(yVals1, "2016");     
        BarDataSet set2 = new BarDataSet(yVals2, "2017");     
        set1.setColor(this.getResources().getColor(R.color.color_orange));
        set2.setColor(this.getResources().getColor(R.color.color_lightblue));
        ArrayList dataSets = new ArrayList();
        dataSets.add(set1);
        dataSets.add(set2);
        BarData data = new BarData(xVals, dataSets);
        data.setGroupSpace(80f);
//        data.setValueTypeface(tf);
       
        barChart.setData(data);
        barChart.invalidate();
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
        set2.setColor(this.getResources().getColor(R.color.color_lightblue));
        ArrayList dataSets = new ArrayList();
        dataSets.add(set2);
        BarData data = new BarData(xVals, dataSets);
        data.setGroupSpace(80f);
//        data.setValueTypeface(tf);
       
        barChart.setData(data);
        barChart.invalidate();
}

	private void initView() {
		barChart = (BarChart)findViewById(R.id.bt_user_perception_index);
		expandListview=(MyexpanableListView) findViewById(R.id.expandablelistview);
		view_line=findViewById(R.id.view_line);
		view_line.bringToFront();
		expandListview.setGroupIndicator(null);  
		
	}
	private void initData() {

		childList = new ArrayList<>();

		for (int i = 0; i < 20; i++) {
			childList.add(new ArrayList<KeiBean>());
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

}
