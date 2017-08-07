package com.chinamobile.yunweizhushou.ui.planManagement;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.KeyValueBean;
import com.chinamobile.yunweizhushou.bean.ResponseBean;
import com.chinamobile.yunweizhushou.common.BaseActivity;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.chinamobile.yunweizhushou.utils.HttpRequestEnum;
import com.chinamobile.yunweizhushou.utils.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
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

public class PlanManagementActivity extends BaseActivity implements OnClickListener{
	
	private TextView tv_item1_0,tv_item1_1,tv_item2_0,tv_item2_1,tv_item3_0,tv_item3_1,tv_item4_0,tv_item4_1;
	private TextView tv_today,tv_yesterday,tv_week,tv_month,tv_sum;
	private TextView tv_gaojing,tv_dingshi,tv_shoudong;
	private PieChart pie_chart_plan_management;
	private LineChart line_chart1_plan_management,line_chart2_plan_management;
	private List<KeyValueBean> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan_management);
		initView();
		initRequest0();
		initRequest1("today");
		initSelectorColor();
		tv_today.setTextColor(getResources().getColor(R.color.color_lightblue));
		initEvent();
	}

	private void initEvent() {
		getTitleBar().setMiddleText("自动化平台运营专区");
		getTitleBar().setLeftButton(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tv_today.setOnClickListener(this); 
		tv_yesterday.setOnClickListener(this); 
		tv_week.setOnClickListener(this); 
		tv_month.setOnClickListener(this); 
		
	}

	private void initRequest0() {
		HashMap<String, String> map0 = new HashMap<>();
		map0.put("action", "getPMGroup");
		startTask(HttpRequestEnum.enum_plan_management_pmgroup, ConstantValueUtil.URL + "PlanManagement?", map0);
	}
	private void initRequest1(String time ) {
		HashMap<String, String> map0 = new HashMap<>();
		map0.put("action", "getPMTotal");
		map0.put("time", time);
		startTask(HttpRequestEnum.enum_plan_management_pmtotal, ConstantValueUtil.URL + "PlanManagement?", map0);
	}
	
	@Override
	protected void onTaskFinish(HttpRequestEnum e, ResponseBean responseBean) {
		super.onTaskFinish(e, responseBean);
		if (responseBean == null) {
			Utils.ShowErrorMsg(this, Utils.ERROR_MSG);
			return;
		}
		if (responseBean.isSuccess()) {
			switch (e) {
			case enum_plan_management_pmgroup:
				Type t = new TypeToken<List<KeyValueBean>>() {
				}.getType();
				list = new Gson().fromJson(responseBean.getDATA(), t);
				tv_item1_0.setText(list.get(0).getValue());
				tv_item1_1.setText(list.get(0).getName());
				tv_item2_0.setText(list.get(1).getValue());
				tv_item2_1.setText(list.get(1).getName());
				tv_item3_0.setText(list.get(2).getValue());
				tv_item3_1.setText(list.get(2).getName());
				tv_item4_0.setText(list.get(3).getValue());
				tv_item4_1.setText(list.get(3).getName());
			case enum_plan_management_pmtotal:
				try {
					JSONObject data=new JSONObject(responseBean.getDATA());
					JSONObject pmTotal = data.getJSONObject("pmTotal");
					if(pmTotal.getString("total")!=null){
						tv_sum.setText(pmTotal.getString("total"));
					}
					if(pmTotal.getString("hand")!=null){
						tv_shoudong.setText(pmTotal.getString("hand"));
					}
					if(pmTotal.getString("time")!=null){
						tv_dingshi.setText(pmTotal.getString("time"));
					}
					if(pmTotal.getString("alarm")!=null){
						tv_gaojing.setText(pmTotal.getString("alarm"));
					}
					
					JSONObject pmResult=data.getJSONObject("pmResult");
					String succ=pmResult.getString("succ");
					String fail=pmResult.getString("fail");
					initPieChart(pie_chart_plan_management);
					showPieChart(pie_chart_plan_management, succ, fail );
					JSONObject  timeWave=data.getJSONObject("timeWave");
					JSONObject  numWave=data.getJSONObject("numWave");
					LineData timeWaveLineData = getLineData(timeWave.getJSONArray("columns"), timeWave.getJSONArray("points"));
					LineData numWaveLineData = getLineData(numWave.getJSONArray("columns"), numWave.getJSONArray("points"));
					showLineChart(line_chart1_plan_management, timeWaveLineData, 0);
					showLineChart(line_chart2_plan_management, numWaveLineData, 0);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			default:
				break;
			}
		} else {
			Utils.ShowErrorMsg(this, responseBean.getMSG());
		}
	}
	


	private void showPieChart(PieChart pieChart,String succ,String fail) {
		ArrayList<Entry> yVals = new ArrayList<>();
		ArrayList<String> xVals = new ArrayList<>();
		int[] colors = new int[2];
		xVals.add("成功率");
		yVals.add(new Entry(Float.valueOf(succ), 0));
		colors[0] = ConstantValueUtil.colors2[0];
		xVals.add("失败率");
		yVals.add(new Entry(Float.valueOf(fail), 1));
		colors[1] = ConstantValueUtil.colors2[1];
		PieDataSet dataSet = new PieDataSet(yVals, "");
		dataSet.setColors(colors);
		PieData data = new PieData(xVals, dataSet);
		data.setValueTextColor(Color.WHITE);
		pieChart.setData(data);
		pieChart.invalidate();
		
	}

	private void initPieChart(PieChart pieChart) {
		pieChart.setNoDataText("暂无数据,请尝试刷新");
		Legend legend = pieChart.getLegend();
		legend.setWordWrapEnabled(true);
		legend.setPosition(LegendPosition.RIGHT_OF_CHART);
		pieChart.setDescription("");
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
	        
	        LineDataSet lineDataSet = new LineDataSet(yValues, columns.getString(1));    
	        // mLineDataSet.setFillAlpha(110);    
	        // mLineDataSet.setFillColor(Color.RED);    
	        
	    
	        //用y轴的集合来设置参数    
	        lineDataSet.setLineWidth(1.75f); // 线宽    
	        lineDataSet.setCircleSize(1f);// 显示的圆形大小    
	        lineDataSet.setColor(getResources().getColor(R.color.color_blue));// 显示颜色    
	        lineDataSet.setDrawFilled(true);
	        lineDataSet.setFillColor(getResources().getColor(R.color.color_dimgreen));
	        //lineDataSet.setCircleColor(Color.WHITE);// 圆形的颜色    
	        //lineDataSet.setHighLightColor(Color.WHITE); // 高亮的线的颜色    
	        ArrayList<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();    
	        lineDataSets.add(lineDataSet); // add the datasets    
	        // create a data object with the datasets    
	        LineData lineData = new LineData(xValues, lineDataSets);
	        return lineData;    
	}
	
	 // 设置显示的样式    
    private void showLineChart(LineChart lineChart, LineData lineData, int color) {    
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
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
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
      	mLegend.setEnabled(true);
        // modify the legend ...    
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);    
        //mLegend.setForm(LegendForm.CIRCLE);// 样式    
        //mLegend.setFormSize(6f);// 字体    
       // mLegend.setTextColor(Color.WHITE);// 颜色    
//      mLegend.setTypeface(mTf);// 字体    
    
        lineChart.animateX(2500); // 立即执行的动画,x轴    
      }    
	private void initView() {
		tv_item1_0=(TextView) findViewById(R.id.tv_item1_0);
		tv_item1_1=(TextView) findViewById(R.id.tv_item1_1);
		tv_item2_0=(TextView) findViewById(R.id.tv_item2_0);
		tv_item2_1=(TextView) findViewById(R.id.tv_item2_1);
		tv_item3_0=(TextView) findViewById(R.id.tv_item3_0);
		tv_item3_1=(TextView) findViewById(R.id.tv_item3_1);
		tv_item4_0=(TextView) findViewById(R.id.tv_item4_0);
		tv_item4_1=(TextView) findViewById(R.id.tv_item4_1);
		tv_today=(TextView) findViewById(R.id.tv_today);
		tv_yesterday=(TextView) findViewById(R.id.tv_yesterday);
		tv_week=(TextView) findViewById(R.id.tv_week);
		tv_month=(TextView) findViewById(R.id.tv_month);
		tv_sum=(TextView) findViewById(R.id.tv_sum);
		tv_shoudong=(TextView) findViewById(R.id.tv_shoudong);
		tv_dingshi=(TextView) findViewById(R.id.tv_dingshi);
		tv_gaojing=(TextView) findViewById(R.id.tv_gaojing);
		pie_chart_plan_management= (PieChart) findViewById(R.id.pie_chart_plan_management);
		line_chart1_plan_management= (LineChart) findViewById(R.id.line_chart1_plan_management);
		line_chart2_plan_management= (LineChart) findViewById(R.id.line_chart2_plan_management);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View  v ) {
		initSelectorColor();
		switch(v.getId()){
			case R.id.tv_today:
				tv_today.setTextColor(getResources().getColor(R.color.color_lightblue));
				initRequest1("today");
				break;
			case R.id.tv_yesterday:	
				tv_yesterday.setTextColor(getResources().getColor(R.color.color_lightblue));
				initRequest1("yes");
				break;
			case R.id.tv_week:	
				tv_week.setTextColor(getResources().getColor(R.color.color_lightblue));
				initRequest1("week");
				break;
			case R.id.tv_month:	
				tv_month.setTextColor(getResources().getColor(R.color.color_lightblue));
				initRequest1("month");
				break;
				default:
				break;
		}
		
	}
	public void initSelectorColor(){
		tv_today.setTextColor(getResources().getColor(R.color.color_black));
		tv_yesterday.setTextColor(getResources().getColor(R.color.color_black));
		tv_week.setTextColor(getResources().getColor(R.color.color_black));
		tv_month.setTextColor(getResources().getColor(R.color.color_black));
	}

}
