package com.chinamobile.yunweizhushou.ui.hotZoneKQBK.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.hotZoneKQBK.bean.GraphBean;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RechargeFunctionListShadowAdapter extends BaseAdapter {

	private List<GraphBean> data;
	private Context mContext;
	private boolean isFloatState;

	public void setFloatState(boolean state) {
		this.isFloatState = state;
	}

	public RechargeFunctionListShadowAdapter(Context c, List<GraphBean> data) {
		this.mContext = c;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView==null){
			convertView = View.inflate(mContext, R.layout.item_graph_shadow_linechart, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		try {
			LineData lineData = getLineData(data.get(position).getColumns(),data.get(position).getPoints());
			showLineChart(holder.lineChart, lineData, 0);
			holder.textView.setText(data.get(position).getTitle());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	
	private class ViewHolder {
		private TextView textView;
		private LinearLayout layout;
		private LineChart lineChart;


		public ViewHolder(View view) {
			layout = (LinearLayout) view.findViewById(R.id.itemRechargeLayout);
			textView = (TextView) view.findViewById(R.id.rechargeDescription);
			lineChart = (LineChart) view.findViewById(R.id.lineChart);

		}
	}
	//生成LineData 数据
		private LineData getLineData(List<String> columns, List<List<String>> points) throws NumberFormatException, JSONException {
			 ArrayList<String> xValues = new ArrayList<String>();    
			 for (int i = 0; i < points.size(); i++) {    
		            // x轴显示的数据，这里默认使用数字下标显示    
		            xValues.add(points.get(i).get(0));    
		        }    
		    
		        // y轴的数据    
		        ArrayList<Entry> yValues = new ArrayList<Entry>();    
		        for (int i = 0; i < points.size(); i++) {    
		            yValues.add(new Entry(Float.parseFloat(points.get(i).get(1)), i));    
		        }    
		    
		        // create a dataset and give it a type    
		        // y轴的数据集合    
		        
		        LineDataSet lineDataSet = new LineDataSet(yValues, columns.get(1));    
		        // mLineDataSet.setFillAlpha(110);    
		        // mLineDataSet.setFillColor(Color.RED);    
		        
		    
		        //用y轴的集合来设置参数    
		        lineDataSet.setLineWidth(1.75f); // 线宽    
		        lineDataSet.setCircleSize(1f);// 显示的圆形大小    
		        lineDataSet.setColor(mContext.getResources().getColor(R.color.color_blue));// 显示颜色    
		        lineDataSet.setDrawFilled(true);
		        lineDataSet.setFillColor(mContext.getResources().getColor(R.color.color_dimgreen));
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
//	      mLegend.setTypeface(mTf);// 字体    
	    
	        lineChart.animateX(2500); // 立即执行的动画,x轴    
	      }    
}
