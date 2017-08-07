package com.chinamobile.yunweizhushou.ui.serviceChain.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.serviceChain.bean.ServcieChainBean;

import java.util.List;

public class ServiceChainAdapter extends AbsBaseAdapter<ServcieChainBean> {

	private List<ServcieChainBean> mList;

	public ServiceChainAdapter(Context context, List<ServcieChainBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, ServcieChainBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.group_name = (TextView) convertView.findViewById(R.id.list_3_item1);
			holder.sys_name = (TextView) convertView.findViewById(R.id.list_3_item2);
			holder.busi_name = (TextView) convertView.findViewById(R.id.list_3_item3);
			convertView.setTag(holder);
		}
		holder.group_name.setText(mList.get(position).getGroup_name());
		holder.sys_name.setText(mList.get(position).getSys_name());
		holder.busi_name.setText(mList.get(position).getBusi_name());
	

		return convertView;
	}

	private static class ViewHolder {
		private TextView group_name, sys_name, busi_name;
	}
}
