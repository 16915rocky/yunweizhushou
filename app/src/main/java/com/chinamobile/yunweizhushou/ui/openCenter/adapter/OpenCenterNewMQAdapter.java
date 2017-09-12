package com.chinamobile.yunweizhushou.ui.openCenter.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.openCenter.bean.OpenCenterMQBean;

import java.util.List;

public class OpenCenterNewMQAdapter extends AbsBaseAdapter<OpenCenterMQBean> {

	private List<OpenCenterMQBean> mList;
	private int tag = 0;

	public OpenCenterNewMQAdapter(Context context, List<OpenCenterMQBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	@Override
	protected View newView(View convertView, OpenCenterMQBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.group_id = (TextView) convertView.findViewById(R.id.tv_item1);
			holder.channel = (TextView) convertView.findViewById(R.id.tv_item2);
			holder.cluster_group = (TextView) convertView.findViewById(R.id.tv_item3);
			holder.pending = (TextView) convertView.findViewById(R.id.tv_item4);
			holder.enqueue = (TextView) convertView.findViewById(R.id.tv_item5);
			holder.dequeue = (TextView) convertView.findViewById(R.id.tv_item6);
			convertView.setTag(holder);
		}
		holder.group_id.setText(mList.get(position).getGroup_id());
		holder.channel.setText(mList.get(position).getChannel());
		holder.cluster_group.setText(mList.get(position).getCluster_group());
		holder.pending.setText(mList.get(position).getPending());
		holder.enqueue.setText(mList.get(position).getEnqueue());
		holder.dequeue.setText(mList.get(position).getDequeue());

		return convertView;
	}

	private static class ViewHolder {
		private TextView group_id, channel, cluster_group, pending,enqueue,dequeue;
	}
}
