package com.chinamobile.yunweizhushou.ui.selfRepair.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.selfRepair.bean.SelfRepairBean;

import java.util.List;

public class SelfRepairAdapter2 extends AbsBaseAdapter<SelfRepairBean> {

	private List<SelfRepairBean> mList;

	public SelfRepairAdapter2(Context context, List<SelfRepairBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, SelfRepairBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.item1);
			holder.hostip = (TextView) convertView.findViewById(R.id.item2);
			holder.server_name = (TextView) convertView.findViewById(R.id.item3);
			holder.fault_reason = (TextView) convertView.findViewById(R.id.item4);
			holder.fault_times = (TextView) convertView.findViewById(R.id.item5);
			convertView.setTag(holder);
		}
		holder.time.setText(mList.get(position).getTime());
		holder.hostip.setText(mList.get(position).getHostip());
		holder.server_name.setText(mList.get(position).getServer_name());
		holder.fault_reason.setText(mList.get(position).getFault_reason());
		holder.fault_times.setText(mList.get(position).getFault_times());

		return convertView;
	}

	private static class ViewHolder {
		private TextView fault_reason, fault_times, hostip, server_name, time;
	}
}
