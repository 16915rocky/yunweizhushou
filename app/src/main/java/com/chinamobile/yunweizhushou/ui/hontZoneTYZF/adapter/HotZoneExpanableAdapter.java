package com.chinamobile.yunweizhushou.ui.hontZoneTYZF.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.hontZoneTYZF.bean.HotZoneKTWYFailureBean;
import com.chinamobile.yunweizhushou.ui.hontZoneTYZF.bean.HotZoneParentItem;
import com.chinamobile.yunweizhushou.utils.ConstantValueUtil;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

public class HotZoneExpanableAdapter extends BaseAdapter {

	private List<HotZoneParentItem> mList;
	private Context mContext;
	private View mLastView;  
    private int mLastPosition;  
	
	public HotZoneExpanableAdapter(List<HotZoneParentItem> mList, Context mContext) {
		super();
		this.mList = mList;
		this.mContext = mContext;
	}

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
	public View getView(int position, View view, ViewGroup arg2) {
		ViewHolder  viewHolder=null;
		if(view==null){
			viewHolder=new ViewHolder();
			view=LayoutInflater.from(mContext).inflate(R.layout.item_hotzone_parent, null);
			viewHolder.tv_item1=(TextView) view.findViewById(R.id.tv_item1);
			viewHolder.tv_item2=(TextView) view.findViewById(R.id.tv_item2);
			viewHolder.common_pie_chart= (PieChart) view.findViewById(R.id.common_pie_chart);
			view.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) view.getTag();
		}
		viewHolder.tv_item1.setText(mList.get(position).getCity());
		viewHolder.tv_item2.setText(mList.get(position).getNum());
		return view;
	}
	public  class ViewHolder{
		private TextView tv_item1,tv_item2;
		private PieChart common_pie_chart;
	}
	
	private static void setPieChart(List<HotZoneKTWYFailureBean> list, PieChart pieChart){
		ArrayList<Entry> yVals = new ArrayList<>();
		ArrayList<String> xVals = new ArrayList<>();
		int[] colors = new int[2];
		for (int i = 0; i < 2; i++) {
			xVals.add(list.get(i).getDescription());
			yVals.add(new Entry(Float.valueOf(list.get(i).getNum()), i));
			colors[i] = ConstantValueUtil.colors2[i];
		}
		pieChart.setUsePercentValues(false);
		pieChart.setNoDataText("暂无数据,请尝试刷新");
		Legend legend = pieChart.getLegend();
		legend.setWordWrapEnabled(true);
		legend.setPosition(LegendPosition.RIGHT_OF_CHART);
		PieDataSet dataSet = new PieDataSet(yVals, "");
		dataSet.setColors(colors);
		pieChart.setDescription("");
		PieData data = new PieData(xVals, dataSet);
		data.setValueTextColor(Color.WHITE);
		pieChart.setData(data);
		pieChart.invalidate();
	}
    public void showPieChart(View view,int position,List<HotZoneKTWYFailureBean> list){
    	if(mLastView != null && mLastPosition != position ) {  
            ViewHolder holder = (ViewHolder) mLastView.getTag();  
            switch(holder.common_pie_chart.getVisibility()) {  
            case View.VISIBLE:  
                holder.common_pie_chart.setVisibility(View.GONE);  
                break;  
            default :  
                break;  
            }  
        }  
        mLastPosition = position;  
        mLastView = view;  
        ViewHolder holder = (ViewHolder) view.getTag();  
        switch(holder.common_pie_chart.getVisibility()) {  
        case View.GONE:  
        	setPieChart(list, holder.common_pie_chart);
            holder.common_pie_chart.setVisibility(View.VISIBLE);  
            break;  
        case View.VISIBLE:  
            holder.common_pie_chart.setVisibility(View.GONE);  
            break;  
        }  
    }
}
