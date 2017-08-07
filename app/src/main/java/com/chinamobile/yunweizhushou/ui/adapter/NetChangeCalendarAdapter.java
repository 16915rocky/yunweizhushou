package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.NetChangeCalendarDayBean;

import java.util.List;

public class NetChangeCalendarAdapter extends AbsBaseAdapter<NetChangeCalendarDayBean> {

	private List<NetChangeCalendarDayBean> mList;

	public NetChangeCalendarAdapter(Context context, List<NetChangeCalendarDayBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
	}

	@Override
	protected View newView(View convertView, NetChangeCalendarDayBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.brace_item_time);
			holder.title = (TextView) convertView.findViewById(R.id.brace_item_type);
			holder.content = (TextView) convertView.findViewById(R.id.brace_item_content);
			convertView.setTag(holder);
		}
		holder.time.setText(mList.get(position).getTime());
		if (mList.get(position).getTitle().equals("变更后台表")) {
			holder.title.setText("【平台变更】");
		} else if (mList.get(position).getTitle().equals("发布信息表")) {
			holder.title.setText("【上线发布】");
		}
		holder.content.setText(mList.get(position).getContent());

		return convertView;
	}

	private static class ViewHolder {
		private TextView time, title, content;
	}

}
