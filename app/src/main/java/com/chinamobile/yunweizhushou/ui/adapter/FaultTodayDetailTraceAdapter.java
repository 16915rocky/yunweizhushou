package com.chinamobile.yunweizhushou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.bean.FaultProgressBean;

import java.util.List;

public class FaultTodayDetailTraceAdapter extends AbsBaseAdapter<FaultProgressBean> {
	private Context context;
	private List<FaultProgressBean> mList;

	public FaultTodayDetailTraceAdapter(Context context, List<FaultProgressBean> list, int resourceId) {
		super(context, list, resourceId);
		this.context = context;
		mList = list;
	}

	@Override
	protected View newView(View convertView, FaultProgressBean t, int position) {
		HolderView holder = (HolderView) convertView.getTag();
		if (holder == null) {
			holder = new HolderView();
			holder.line1 = convertView.findViewById(R.id.fault_today_trace_line1);
			holder.line2 = convertView.findViewById(R.id.fault_today_trace_line2);
			holder.dot = convertView.findViewById(R.id.fault_today_trace_dot);
			holder.tv = (TextView) convertView.findViewById(R.id.fault_today_trace_text);
			convertView.setTag(holder);
		}
		holder.line1.setVisibility(View.VISIBLE);
		holder.line2.setVisibility(View.VISIBLE);
		if (position == 0) {
			holder.line1.setVisibility(View.INVISIBLE);
			holder.dot.setBackgroundResource(R.drawable.fault_today_finish_dot);
			holder.tv.setTextColor(context.getResources().getColor(R.color.color_deepgreen));
		} else {
			holder.dot.setBackgroundResource(R.drawable.fault_today_unfinish_dot);
		}

		if (position == mList.size() - 1) {
			holder.line2.setVisibility(View.INVISIBLE);
		}
		holder.tv.setText(mList.get(position).getTime() + " " + mList.get(position).getContent());
		return convertView;
	}

	private class HolderView {
		private View line1, line2, dot;
		private TextView tv;
	}

}
