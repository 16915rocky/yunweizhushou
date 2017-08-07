package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.BraceBroadcastBean;
import com.chinamobile.yunweizhushou.view.FlexibleTextView;

import java.util.List;

public class SearchAdapter extends BaseAdapter {

	private List<BraceBroadcastBean> mList;
	private Context mContext;

	public SearchAdapter(Context context, List<BraceBroadcastBean> list) {
		super();
		mList = list;
		mContext = context;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bracebraodcast, parent, false);
			holder.bottom = (LinearLayout) convertView.findViewById(R.id.timeasix_gone_layout);
			holder.time = (TextView) convertView.findViewById(R.id.brace_item_time);
			holder.type = (TextView) convertView.findViewById(R.id.brace_item_type);
			holder.content = (FlexibleTextView) convertView.findViewById(R.id.brace_item_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.bottom.setVisibility(View.GONE);
		holder.time.setText(mList.get(position).getBomcTime());
		holder.type.setText("【" + mList.get(position).getBomcName() + "】");
		holder.content.setText(mList.get(position).getBomcTitle());

		return convertView;
	}

	private static class ViewHolder {
		private TextView time, type;
		private FlexibleTextView content;
		private LinearLayout bottom;
	}

}
