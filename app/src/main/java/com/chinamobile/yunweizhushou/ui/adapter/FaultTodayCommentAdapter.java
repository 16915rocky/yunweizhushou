package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.FaultTodayCommentBean;

import java.util.List;

public class FaultTodayCommentAdapter extends AbsBaseAdapter<FaultTodayCommentBean> {

	private List<FaultTodayCommentBean> mList;

	public FaultTodayCommentAdapter(Context context, List<FaultTodayCommentBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, FaultTodayCommentBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.fault_today_comment_name);
			holder.content = (TextView) convertView.findViewById(R.id.fault_today_comment_content);
			holder.time = (TextView) convertView.findViewById(R.id.fault_today_comment_time);
			convertView.setTag(holder);
		}
		holder.name.setText(mList.get(position).getUserName());
		holder.content.setText(mList.get(position).getContent());
		holder.time.setText(mList.get(position).getCreateTime());

		return convertView;
	}

	private class ViewHolder {

		private TextView name, content, time;
	}

}
