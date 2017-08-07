package com.chinamobile.yunweizhushou.ui.openCenter.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.OpenCenterBacklogBean;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;

import java.util.List;

public class OpenCenterBacklogAdapter extends AbsBaseAdapter<OpenCenterBacklogBean> {

	private List<OpenCenterBacklogBean> mList;
	private int tag = 0;

	public OpenCenterBacklogAdapter(Context context, List<OpenCenterBacklogBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	@Override
	protected View newView(View convertView, OpenCenterBacklogBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.ability_2_center);
			holder.cname = (TextView) convertView.findViewById(R.id.ability_2_name);
			holder.name = (TextView) convertView.findViewById(R.id.ability_2_times);
			holder.num = (TextView) convertView.findViewById(R.id.ability_2_failNum);
			convertView.setTag(holder);
		}
		holder.time.setText(mList.get(position).getTime());
		holder.cname.setText(mList.get(position).getCname());
		holder.name.setText(mList.get(position).getName());
		holder.num.setText(mList.get(position).getNum());

		return convertView;
	}

	private static class ViewHolder {
		private TextView time, cname, name, num;
	}
}
