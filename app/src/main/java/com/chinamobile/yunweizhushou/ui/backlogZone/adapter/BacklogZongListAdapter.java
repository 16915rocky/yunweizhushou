package com.chinamobile.yunweizhushou.ui.backlogZone.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.backlogZone.bean.BacklogZongListBean;

public class BacklogZongListAdapter extends BaseAdapter {

	private Context context;
	private BacklogZongListBean bean;

	public BacklogZongListAdapter(Context context, BacklogZongListBean bean) {
		this.context = context;
		this.bean = bean;
	}

	@Override
	public int getCount() {
		return bean.getItemsList() == null ? 0 : bean.getItemsList().size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_backlog_zong_list, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		BacklogZongListBean.ItemsList item = bean.getItemsList().get(position);

		holder.name.setText(item.getName() + "\n" + item.getCity());
		holder.threshold.setText(item.getExt1());
		holder.current.setText(item.getValue());
		if (item.getState().equals("1")) {
			holder.current.setBackgroundColor(Color.RED);
		} else if (item.getState().equals("0")) {
			holder.current.setBackgroundColor(Color.GREEN);
		}
		return convertView;
	}

	private class ViewHolder {
		private TextView name;
		private TextView threshold;// 阀值
		private TextView current;

		public ViewHolder(View view) {
			name = (TextView) view.findViewById(R.id.backlogZongName);
			threshold = (TextView) view.findViewById(R.id.backlogThreshold);
			current = (TextView) view.findViewById(R.id.backlogCurrentValue);
		}
	}

}
