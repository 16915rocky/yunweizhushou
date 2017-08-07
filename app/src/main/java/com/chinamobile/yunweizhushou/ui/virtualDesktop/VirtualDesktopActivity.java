package com.chinamobile.yunweizhushou.ui.virtualDesktop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.KeyValueBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.ui.virtualDesktop.adapter.VirtualDeskAdapter;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.chinamobile.yunweizhushou.view.MyListView;
import com.chinamobile.yunweizhushou.view.MyRefreshLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VirtualDesktopActivity extends BaseActivity {
	
	private LineChart line_chart_vd;
	private HorizontalBarChart hbar_chart;
	private MyListView lv_install_top10,lv_active_top10;
	private VirtualDeskAdapter activeAdapter,installAdapter;
	private List<KeyValueBean> activeList,installList;
	private TextView tv_fled,tv_zhfwl;
	private MyRefreshLayout rt_virtual_desktop;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_virtual_desktop);
		initView();
		initEvent();
		initRequest();
	}

	private void initEvent() {
		getTitleBar().setMiddleText("虚拟桌面运营专区");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		rt_virtual_desktop.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				initRequest();
				
			}
		});
		
	}

	private void initRequest() {
		HashMap map0=new HashMap<String,String>();	
		map0.put("action", "getTotalAccessWave");
		startTask(HttpRequestEnum.enum_virtual_desktop_TotalAccessWave, ConstantValueUtil.URL+"VirtualDesktop?", map0);
		HashMap map1=new HashMap<String,String>();	
		map1.put("action", "getKindsHeatWave");
		startTask(HttpRequestEnum.enum_virtual_desktop_KindsHeatWave, ConstantValueUtil.URL+"VirtualDesktop?", map1);
		HashMap map2=new HashMap<String,String>();	
		map2.put("action", "getTop10");
		startTask(HttpRequestEnum.enum_virtual_desktop_Top10, ConstantValueUtil.URL+"VirtualDesktop?", map2);
	}
	
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if(rt_virtual_desktop.isShown()){
			rt_virtual_desktop.setRefreshing(false);
		}
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		switch (e) {
		case enum_virtual_desktop_TotalAccessWave:
			if (responseBean.isSuccess()) {
				try {
					JSONObject jo = new JSONObject(responseBean.getDATA());
					JSONArray columns = jo.getJSONArray("columns");
					JSONArray points = jo.getJSONArray("points");
					String title = jo.getString("title");
					LineData lineData = getLineData(columns,points);
					showChart(line_chart_vd, lineData, 0);
					tv_zhfwl.setText(title);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			
			}
			break;
		case enum_virtual_desktop_KindsHeatWave:
			if (responseBean.isSuccess()) {
				try {
					JSONObject jo = new JSONObject(responseBean.getDATA());
					JSONArray columns = jo.getJSONArray("columns");
					JSONArray points = jo.getJSONArray("points");
					String title = jo.getString("title");
					setbarChartData(hbar_chart,columns,points);
					showBarChart(hbar_chart);
					tv_fled.setText(title);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				
			}
			break;
		case enum_virtual_desktop_Top10:
			if(responseBean.isSuccess()){
				try {
					JSONObject jo = new JSONObject(responseBean.getDATA());
					JSONArray active = jo.getJSONArray("activeList");
					JSONArray install = jo.getJSONArray("installList");
					Type type=new TypeToken<List<KeyValueBean>>(){}.getType();
					activeList= new Gson().fromJson(active.toString(), type);
					installList= new Gson().fromJson(install.toString(), type);
					activeAdapter=new VirtualDeskAdapter(this,activeList,R.layout.view_virtual_desktop_top10_item);
					installAdapter=new VirtualDeskAdapter(this,installList,R.layout.view_virtual_desktop_top10_item);
					lv_active_top10.setAdapter(activeAdapter);
					lv_install_top10.setAdapter(installAdapter);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			

		default:
			break;
		}
	}

	private void initView() {
		line_chart_vd=(LineChart) findViewById(R.id.line_chart_vd);
		hbar_chart=(HorizontalBarChart) findViewById(R.id.hbar_chart);
		lv_active_top10=(MyListView) findViewById(R.id.lv_active_top10);
		lv_install_top10=(MyListView) findViewById(R.id.lv_install_top10);
		tv_zhfwl=(TextView) findViewById(R.id.tv_zhfwl);
		tv_fled=(TextView) findViewById(R.id.tv_fled);
		rt_virtual_desktop=(MyRefreshLayout) findViewById(R.id.rt_virtual_desktop);
		
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
    
    
    private void showBarChart(BarChart barChart) {
		barChart.setDescription("");// 数据描述
		barChart.setDrawBarShadow(false);// 会在各条 bar 后面绘制灰色
		barChart.setNoDataTextDescription("暂无数据");// 如果没有数据的时候，会显示这个，类似ListView的EmptyView
		barChart.setDrawGridBackground(false); // 是否显示表格颜色
		barChart.setTouchEnabled(true);// 设置是否可以触摸
		barChart.setDragEnabled(true);// 是否可以拖拽
		barChart.setScaleEnabled(true);// 是否可以缩放
		barChart.setPinchZoom(true);
		barChart.animateX(2500);
		// 不绘制从Y轴出发的横向直线
		YAxis left = barChart.getAxisLeft();
		// left.setDrawGridLines(false);
		// left.setDrawAxisLine(false);

		// 设置比例图标示
		Legend mLegend = barChart.getLegend();
		mLegend.setEnabled(false);
		mLegend.setPosition(LegendPosition.BELOW_CHART_LEFT);
		mLegend.setForm(LegendForm.SQUARE); // 样式
		mLegend.setFormSize(10f);// 指示器的大小
		mLegend.setTextColor(Color.BLACK);// 颜色
		mLegend.setWordWrapEnabled(true);

		// X轴设定
		XAxis xAxis = barChart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);// X轴在下边
		xAxis.setTextSize(6f);
		// xAxis.setDrawAxisLine(false);
		// xAxis.setDrawGridLines(false);

	}
    
    public void setbarChartData(HorizontalBarChart barChart,JSONArray columns,JSONArray points) throws JSONException{
	
		ArrayList<BarEntry> yVals2 = new ArrayList<>();
		ArrayList<String> xVals2 = new ArrayList<>();
		for (int i =0; i<points.length(); i++) {
			xVals2.add(columns.getString(i));
			yVals2.add(new BarEntry(Float.valueOf(points.getString(points.length()-i-1)), points.length()-i-1));
		}
		BarDataSet set = new BarDataSet(yVals2, null);
		set.setDrawValues(true);
		set.setValueTextSize(8f);
		set.setColor(getResources().getColor(R.color.color_lightblue));
		ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
		dataSets.add(set);
		BarData barData = new BarData(xVals2, dataSets);
		barChart.setData(barData);
		barChart.invalidate();
    }
	

}
