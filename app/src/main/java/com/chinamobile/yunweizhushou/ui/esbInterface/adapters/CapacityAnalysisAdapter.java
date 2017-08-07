package com.chinamobile.yunweizhushou.ui.esbInterface.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.GovernNewListBean;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;

import java.util.List;

import cn.fanrunqi.waveprogress.WaveProgressView;

public class CapacityAnalysisAdapter extends AbsBaseAdapter<GovernNewListBean> {

	private List<GovernNewListBean> mList;
	private Context mContext;
	private int resourceId;

	public CapacityAnalysisAdapter(Context context, List<GovernNewListBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		mContext = context;
        this.resourceId=resourceId;
	}

	@Override
	protected View newView(View convertView, GovernNewListBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.capacity_title);
			holder.water = (WaveProgressView) convertView.findViewById(R.id.capacity_waterprogress);
			if(resourceId==R.layout.item_yunwei_mc){
				holder.aperson = (TextView) convertView.findViewById(R.id.capacity_aperson);
			}
		}
		holder.title.setText(mList.get(position).getName());
		if(resourceId==R.layout.item_yunwei_mc){
			holder.aperson.setText(mList.get(position).getAperson()); 
		}
		holder.water.setMaxProgress(100);
		holder.water.setCurrent(Math.round(Float.parseFloat(mList.get(position).getRate())) , mList.get(position).getRate()+"%");
		if (mList.get(position).getState().equals("1")) {
			holder.water.setWaveColor("#19E620");
		} else if (mList.get(position).getState().equals("2")) {
			holder.water.setWaveColor("#F0E43D");
		} else if (mList.get(position).getState().equals("3")) {
			holder.water.setWaveColor("#ff0000");
		}
		return convertView;
	}

	private class ViewHolder {
		private TextView title,aperson;
		private WaveProgressView water;
	}

}
