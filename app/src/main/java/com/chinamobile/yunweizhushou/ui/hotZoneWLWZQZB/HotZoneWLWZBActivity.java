package com.chinamobile.yunweizhushou.ui.hotZoneWLWZQZB;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.hotZoneWLWZQZB.adapter.HotZoneWLWZBAdapter;
import com.chinamobile.yunweizhushou.ui.hotZoneWLWZQZB.bean.HotZoneWLWZBBean;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.chinamobile.yunweizhushou.widget.SelectDayDialog;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class HotZoneWLWZBActivity extends BaseActivity {
	
	private LineChart lineChart;
	private TextView tv_time,lt_name;
	private String currentDate;
	private List<HotZoneWLWZBBean> mList;
	private int currentIndex;
	private HotZoneWLWZBAdapter hotZoneWLWZBAdapter;
	private int year;
	private int month;
	private int date;
	private MyListView lv_wlwzb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotzone_wlwzb);
		Calendar c = Calendar.getInstance();
		year=c.get(Calendar.YEAR);
		month=c.get(Calendar.MONTH)+1;
		date=c.get(Calendar.DATE);
		currentDate = Utils.getCurrentTime();
		initView();
		initEvent();
		initRequest();
		initListDataRequest(currentDate);
		
	}

	private void initView() {
		lineChart= (LineChart) findViewById(R.id.lt_common);
		tv_time=(TextView) findViewById(R.id.tv_time);
		lt_name=(TextView) findViewById(R.id.lt_name);
		lv_wlwzb=(MyListView)findViewById(R.id.lv_wlwzb);
		tv_time.setText(year+"年"+month+"月"+date+"日");
		
	}

	private void initEvent() {
		getTitleBar().setMiddleText("物联网指标专区");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tv_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SelectDayDialog dialog = new SelectDayDialog(HotZoneWLWZBActivity.this);
				dialog.show();
				dialog.setBirthdayListener(new SelectDayDialog.OnBirthListener() {

					@Override
					public void onClick(String year, String month, String day) {
						String currentDate1 = year + "年" + month + "月"+day+"日";
						currentDate = year + "-" + month+"-"+day;
						tv_time.setText(currentDate);
					
						initListDataRequest(currentDate);
						
					}
				});
			}
		});
		
		
	}


	private void initRequest() {
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("action", "findWLWZBSucc");
		startTask(HttpRequestEnum.enum_hotzone_findWLWZBSucc, ConstantValueUtil.URL + "HotZone?", maps, false);
		
	}
	private void initListDataRequest(String time) {
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("action", "findWLWZBTarget");
		maps.put("time", time);
		startTask(HttpRequestEnum.enum_hotzone_findWLWZBState, ConstantValueUtil.URL + "HotZone?", maps, false);
		
	}

	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_hotzone_findWLWZBSucc:
			try {
				JSONObject jo=new JSONObject(responseBean.getDATA());
				JSONObject jo2=new JSONObject(jo.getString("succLine"));
				JSONArray ja1= jo2.getJSONArray("columns");
				JSONArray ja2= jo2.getJSONArray("points");
				String total=jo.getString("total");
				LineData lineData = getLineData(ja1, ja2);
				showChart(lineChart, lineData, 0);
				lt_name.setText("平均成功率:"+total+"%");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			break;
		case enum_hotzone_findWLWZBState:
			if(responseBean.isSuccess()){
				Type t=new TypeToken<List<HotZoneWLWZBBean>>(){}.getType();
				mList=new Gson().fromJson(responseBean.getDATA(), t);
				hotZoneWLWZBAdapter=new HotZoneWLWZBAdapter(this, mList, R.layout.item_hotzone_wlwzb);
				lv_wlwzb.setAdapter(hotZoneWLWZBAdapter);
			}else{
				lv_wlwzb.setAdapter(null);
			}
			break;
		default:
			break;
		}
	}

	
	//生成LineData 数据
	private LineData getLineData(JSONArray columns, JSONArray points) throws NumberFormatException, JSONException {
		 ArrayList<String> xValues = new ArrayList<String>();    
		 for (int i = 0; i < points.length(); i++) {    
	            // x轴显示的数据，这里默认使用数字下标显示    
	            xValues.add(points.getJSONArray(i).getString(0));    
	        }    
	    
	        // y轴的数据    
	        ArrayList<Entry> yValues = new ArrayList<Entry>();    
	        for (int i = 0; i < points.length(); i++) {    
	            yValues.add(new Entry(Float.parseFloat(points.getJSONArray(i).getString(1)), i));    
	        }    
	    
	        // create a dataset and give it a type    
	        // y轴的数据集合    
	        
	        LineDataSet lineDataSet = new LineDataSet(yValues, null);    
	        // mLineDataSet.setFillAlpha(110);    
	        // mLineDataSet.setFillColor(Color.RED);    
	        
	    
	        //用y轴的集合来设置参数    
	        lineDataSet.setLineWidth(1.75f); // 线宽    
	        lineDataSet.setCircleSize(3f);// 显示的圆形大小    
	        lineDataSet.setColor(getResources().getColor(R.color.color_deepgreen));// 显示颜色    
	        //lineDataSet.setCircleColor(Color.WHITE);// 圆形的颜色    
	        //lineDataSet.setHighLightColor(Color.WHITE); // 高亮的线的颜色    
	        lineDataSet.setDrawFilled(true);
	        lineDataSet.setFillColor(getResources().getColor(R.color.color_dimgreen));
	        ArrayList<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();    
	        lineDataSets.add(lineDataSet); // add the datasets    
	    
	        // create a data object with the datasets    
	        LineData lineData = new LineData(xValues, lineDataSets);
	    
	        return lineData;    
	}

	 // 设置显示的样式    
    private void showChart(LineChart lineChart, LineData lineData, int color) {    
        lineChart.setDrawBorders(false);  //是否在折线图上添加边框    
    
        // no description text    
        lineChart.setDescription("");// 数据描述    
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview    
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");    
            
        // enable / disable grid background    
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色    
      //  lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度    
        
        // enable touch gestures    
        lineChart.setTouchEnabled(true); // 设置是否可以触摸    
        
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);//设置x轴的显示位置
        YAxis rightAxis=lineChart.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
    
        // enable scaling and dragging    
        lineChart.setDragEnabled(true);// 是否可以拖拽    
        lineChart.setScaleEnabled(true);// 是否可以缩放    
    
        // if disabled, scaling can be done on x- and y-axis separately    
        lineChart.setPinchZoom(false);//     
    
        // lineChart.setBackgroundColor(color);// 设置背景    
        
        // add data    
        lineChart.setData(lineData); // 设置数据    
    
        // get the legend (only possible after setting data)    
      Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的    
      	mLegend.setEnabled(false);
        // modify the legend ...    
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);    
        //mLegend.setForm(LegendForm.CIRCLE);// 样式    
        //mLegend.setFormSize(6f);// 字体    
       // mLegend.setTextColor(Color.WHITE);// 颜色    
//      mLegend.setTypeface(mTf);// 字体    
    
        lineChart.animateX(2500); // 立即执行的动画,x轴    
      }    
    
    
  
}
