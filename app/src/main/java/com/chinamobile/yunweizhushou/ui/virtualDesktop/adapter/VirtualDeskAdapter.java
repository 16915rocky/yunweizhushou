package com.chinamobile.yunweizhushou.ui.virtualDesktop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.KeyValueBean;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;

import java.util.List;

public class VirtualDeskAdapter extends AbsBaseAdapter<KeyValueBean> {

	private List<KeyValueBean> mList;

	public VirtualDeskAdapter(Context context, List<KeyValueBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, KeyValueBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.tv_item1);
			holder.value = (TextView) convertView.findViewById(R.id.tv_item2);
		
			convertView.setTag(holder);
		}
		holder.name.setText(mList.get(position).getName());
		holder.value.setText(mList.get(position).getValue());
		
		return convertView;
	}

	private static class ViewHolder {
		private TextView name, value;
	}
}
