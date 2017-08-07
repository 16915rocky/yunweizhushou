package com.chinamobile.yunweizhushou.ui.userperception.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.PerceptionsOfTOP5Bean;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;

import java.util.List;

public class PerceptionsOfTOP5Adapter extends AbsBaseAdapter<PerceptionsOfTOP5Bean> {

	private List<PerceptionsOfTOP5Bean> mList;
	private int newPosition;
	private Context mContext;

	public PerceptionsOfTOP5Adapter(Context context, List<PerceptionsOfTOP5Bean> list, int resourceId) {
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
			holder.oval = (TextView) convertView.findViewById(R.id.tv_perception_item1);
			holder.channel_type = (TextView) convertView.findViewById(R.id.tv_perception_item2);
			holder.region_name = (TextView) convertView.findViewById(R.id.tv_perception_item3);
			holder.kpi_name  = (TextView) convertView.findViewById(R.id.tv_perception_item4);
			holder.kpi_value = (TextView) convertView.findViewById(R.id.tv_perception_item5);
			convertView.setTag(holder);
		}
		if(newPosition==1){
			holder.oval.setBackground(mContext.getResources().getDrawable(R.drawable.oval_orange));
		}else if(newPosition==2){
			holder.oval.setBackground(mContext.getResources().getDrawable(R.drawable.oval_yellow));
		}else if(newPosition==3){
			holder.oval.setBackground(mContext.getResources().getDrawable(R.drawable.oval_blue));
		}else if(newPosition==4){
			holder.oval.setBackground(mContext.getResources().getDrawable(R.drawable.oval_gray));
		}else if(newPosition==5){
			holder.oval.setBackground(mContext.getResources().getDrawable(R.drawable.oval_gray));
		}
	
		holder.oval.setText("0"+newPosition);
		holder.channel_type.setText(mList.get(position).getChannel_type());
		holder.region_name.setText(mList.get(position).getRegion_name());
		holder.kpi_name.setText(mList.get(position).getKpi_name());
		holder.kpi_value.setText(mList.get(position).getKpi_value()+"ms");

		return convertView;
	}

	private static class ViewHolder {
		private TextView oval,channel_type, city_name, kpi_name, kpi_value, region_name,start_time;
	}
}
