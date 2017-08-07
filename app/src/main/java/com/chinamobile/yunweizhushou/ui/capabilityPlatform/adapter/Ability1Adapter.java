package com.chinamobile.yunweizhushou.ui.capabilityPlatform.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.capabilityPlatform.bean.Ability1Bean;

import java.util.List;

public class Ability1Adapter extends AbsBaseAdapter<Ability1Bean> {

	private List<Ability1Bean> mList;

	public Ability1Adapter(Context context, List<Ability1Bean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, Ability1Bean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.ability_1_time);
			holder.name = (TextView) convertView.findViewById(R.id.ability_1_name);
			holder.times = (TextView) convertView.findViewById(R.id.ability_1_times);
			holder.serveNum = (TextView) convertView.findViewById(R.id.ability_1_serveNum);
			holder.failNum = (TextView) convertView.findViewById(R.id.ability_1_failNum);
			convertView.setTag(holder);
		}
		holder.name.setText(mList.get(position).getName());
		holder.serveNum.setText(mList.get(position).getNum());
		holder.times.setText(mList.get(position).getValue());
		holder.time.setText(mList.get(position).getAvg());
		holder.failNum.setText(mList.get(position).getErr());

		return convertView;
	}

	private static class ViewHolder {
		private TextView name, serveNum, times, time, failNum;
	}
}
