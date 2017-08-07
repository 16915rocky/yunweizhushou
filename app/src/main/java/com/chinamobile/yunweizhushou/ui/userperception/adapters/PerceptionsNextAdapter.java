package com.chinamobile.yunweizhushou.ui.userperception.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.PerceptionsOfTOP5Bean;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;

import java.util.List;

public class PerceptionsNextAdapter extends AbsBaseAdapter<PerceptionsOfTOP5Bean> {

	private List<PerceptionsOfTOP5Bean> mList;
	private int newPosition;
	private Context mContext;

	public PerceptionsNextAdapter(Context context, List<PerceptionsOfTOP5Bean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		this.mContext=context;
	}

	@Override
	protected View newView(View convertView, PerceptionsOfTOP5Bean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		newPosition=position+1;
		if (holder == null) {
			holder = new ViewHolder();
			holder.start_time = (TextView) convertView.findViewById(R.id.tv_perception_item1);
			holder.channel_type = (TextView) convertView.findViewById(R.id.tv_perception_item2);
			holder.region_name = (TextView) convertView.findViewById(R.id.tv_perception_item3);
			holder.city_name  = (TextView) convertView.findViewById(R.id.tv_perception_item4);
			holder.kpi_name = (TextView) convertView.findViewById(R.id.tv_perception_item5);
			holder.kpi_value = (TextView) convertView.findViewById(R.id.tv_perception_item6);
			convertView.setTag(holder);
		}
		
	
		holder.start_time.setText(mList.get(position).getStart_time());
		holder.channel_type.setText(mList.get(position).getChannel_type());
		holder.region_name.setText(mList.get(position).getRegion_name());
		holder.city_name.setText(mList.get(position).getCity_name());
		holder.kpi_name.setText(mList.get(position).getKpi_name());
		holder.kpi_value.setText(mList.get(position).getKpi_value());

		return convertView;
	}

	private static class ViewHolder {
		private TextView channel_type, city_name, kpi_name, kpi_value, region_name,start_time;
	}
}
