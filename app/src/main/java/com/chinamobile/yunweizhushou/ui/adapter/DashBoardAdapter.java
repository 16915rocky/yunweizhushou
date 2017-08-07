package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.StereoMonitoringFirstBean;
import com.chinamobile.yunweizhushou.view.DashboardView;
import com.chinamobile.yunweizhushou.view.HighlightCR;

import java.util.ArrayList;
import java.util.List;

public class DashBoardAdapter extends AbsBaseAdapter<StereoMonitoringFirstBean> {

	private List<StereoMonitoringFirstBean> mList;
	private Context context;

	public DashBoardAdapter(Context context, List<StereoMonitoringFirstBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		this.context=context;
	}

	@Override
	protected View newView(View convertView, StereoMonitoringFirstBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
			holder.dashboard_view = (DashboardView) convertView.findViewById(R.id.dashboard_view);
			holder.dashboard_view.setArcColor(context.getResources().getColor(android.R.color.white));
			convertView.setTag(holder);
		}
		initDashBoard(holder.dashboard_view);
		holder.tv1.setText(mList.get(position).getName());
		if("".equals(mList.get(position).getValue())){
			holder.dashboard_view.setRealTimeValue(0);
		}else{
			holder.dashboard_view.setRealTimeValue(Float.parseFloat(mList.get(position).getValue()));
		}
		return convertView;
	}

	private static class ViewHolder {
		private TextView tv1;
		private DashboardView dashboard_view;
	}
	
	private void initDashBoard(DashboardView dashboard_view) {

		 dashboard_view.setArcColor(context.getResources().getColor(android.R.color.black));
		 List<HighlightCR> highlight2 = new ArrayList<>();
	        highlight2.add(new HighlightCR(120, 180, Color.parseColor("#EE0000")));
	        highlight2.add(new HighlightCR(300, 60, Color.parseColor("#EEEE00")));
	        highlight2.add(new HighlightCR(360, 60, Color.parseColor("#66CD00")));
	        
	        dashboard_view.setStripeHighlightColorAndRange(highlight2);
		
	}
}
