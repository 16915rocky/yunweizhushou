package com.chinamobile.yunweizhushou.logZone.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.logZone.bean.LogZoneBean;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;

import java.util.List;

public class LogZoneAdapter extends AbsBaseAdapter<LogZoneBean> {

	private List<LogZoneBean> mList;
	private Context context;

	public LogZoneAdapter(Context context, List<LogZoneBean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		this.context = context;
	}

	@Override
	protected View newView(View convertView, LogZoneBean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.logzone_tv1);
			holder.name = (TextView) convertView.findViewById(R.id.logzone_tv2);
			holder.max_range = (TextView) convertView.findViewById(R.id.logzone_tv3);
			holder.num = (TextView) convertView.findViewById(R.id.logzone_tv4);

			convertView.setTag(holder);
		}
		holder.time.setText(mList.get(position).getTime());
		holder.name.setText(mList.get(position).getName());
		holder.max_range.setText(mList.get(position).getMax_range());
		holder.num.setText(mList.get(position).getNum());
		if ("2".equals(mList.get(position).getState())) {
			holder.num.setBackgroundColor(context.getResources().getColor(R.color.color_red));
		} else if ("1".equals(mList.get(position).getState())) {
			holder.num.setBackgroundColor(context.getResources().getColor(R.color.color_yellow));
		} else {
			holder.num.setBackgroundColor(context.getResources().getColor(R.color.color_green));
		}
		return convertView;
	}

	private static class ViewHolder {
		private TextView time, name, max_range, num, state;
	}
}
