package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.StereoMonitorBean2;

import java.util.List;

public class StereoMonitoringAdapter3 extends AbsBaseAdapter<StereoMonitorBean2> {

	private List<StereoMonitorBean2> mList;
	private int resourceId;

	public StereoMonitoringAdapter3(Context context, List<StereoMonitorBean2> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		this.resourceId=resourceId;
	}

	@Override
	protected View newView(View convertView, StereoMonitorBean2 t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.pv1 = (TextView) convertView.findViewById(R.id.pv1);
			holder.pv2 =  (TextView) convertView.findViewById(R.id.pv2);
			holder.pv3 = (TextView) convertView.findViewById(R.id.pv3);
			holder.pv4 =  (TextView) convertView.findViewById(R.id.pv4);
			holder.pv5 =  (TextView) convertView.findViewById(R.id.pv5);
			convertView.setTag(holder);
		}
		holder.pv1.setText(mList.get(position).getResthirdtype());
		holder.pv2.setText(mList.get(position).getPro_num());	
		holder.pv3.setText(mList.get(position).getMacip());		
		holder.pv4.setText(mList.get(position).getOk_num());				
		holder.pv5.setText(mList.get(position).getErr_num());
		

		return convertView;
	}

	private static class ViewHolder {
		private TextView pv1,pv3,pv2,pv4,pv5;		
	
		
	}
}
