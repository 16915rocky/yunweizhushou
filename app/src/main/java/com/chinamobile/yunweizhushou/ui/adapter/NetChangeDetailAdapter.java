package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.NetChangeDetailListBean;

import java.util.List;

public class NetChangeDetailAdapter extends AbsBaseAdapter<NetChangeDetailListBean> {

	private List<NetChangeDetailListBean> mList;

	public NetChangeDetailAdapter(Context context, List<NetChangeDetailListBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, NetChangeDetailListBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.num = (TextView) convertView.findViewById(R.id.net_change_detail_num);
			holder.state = (TextView) convertView.findViewById(R.id.net_change_detail_state);
			holder.content1 = (TextView) convertView.findViewById(R.id.net_change_detail_content1);
			holder.person = (TextView) convertView.findViewById(R.id.net_change_detail_person);
			holder.group = (TextView) convertView.findViewById(R.id.net_change_detail_group);
			holder.time = (TextView) convertView.findViewById(R.id.net_change_detail_time);
			holder.content2 = (TextView) convertView.findViewById(R.id.net_change_detail_content2);
			convertView.setTag(holder);
		}

		holder.num.setText(mList.get(position).getChr_taskNumber());
		holder.state.setText(mList.get(position).getChr_taskStatus());
		holder.content1.setText(mList.get(position).getChr_taskTitlehole());
		holder.person.setText(mList.get(position).getChr_taskName());
		holder.group.setText(mList.get(position).getChr_taskGroup());
		holder.time.setText(mList.get(position).getSubmitdate2());
		holder.content2.setText(mList.get(position).getChr_executeResult());

		return convertView;
	}

	private static class ViewHolder {
		private TextView num, state, content1, person, group, time, content2;
	}

}
