package com.chinamobile.yunweizhushou.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.StereoMonitorBean1;

import java.util.List;

public class StereoMonitoringAdapter1 extends AbsBaseAdapter<StereoMonitorBean1> {

	private List<StereoMonitorBean1> mList;
	private Context mContext;

	public StereoMonitoringAdapter1(Context context, List<StereoMonitorBean1> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		this.mContext=context;
	}

	@Override
	protected View newView(View convertView, StereoMonitorBean1 t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.sys = (TextView) convertView.findViewById(R.id.tv1);
			holder.event_time = (TextView) convertView.findViewById(R.id.tv2);
			holder.org_alarm_title = (TextView) convertView.findViewById(R.id.tv3);
			holder.org_alarm_txt = (TextView) convertView.findViewById(R.id.tv4);	
			holder.org_alarm_txt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					TextView tv1=(TextView)v;
					AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
					builder.setTitle("告警内容"); 
					builder.setMessage(tv1.getText());
					builder.create();
					builder.show();
					
				}
			});
			convertView.setTag(holder);
		}
		holder.sys.setText(mList.get(position).getSys());
		holder.event_time.setText(mList.get(position).getEvent_time());
		holder.org_alarm_title.setText(mList.get(position).getOrg_alarm_title());
		holder.org_alarm_txt.setText(mList.get(position).getOrg_alarm_txt());
		

		return convertView;
	}

	private static class ViewHolder {
		private TextView event_time, org_alarm_title, org_alarm_txt, sys;		
	}
}
