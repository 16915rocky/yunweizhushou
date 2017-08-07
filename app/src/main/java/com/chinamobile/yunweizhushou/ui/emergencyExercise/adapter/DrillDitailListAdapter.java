package com.chinamobile.yunweizhushou.ui.emergencyExercise.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.yunweizhushou.R;
import com.chinamobile.yunweizhushou.ui.adapter.AbsBaseAdapter;
import com.chinamobile.yunweizhushou.ui.emergencyExercise.bean.DrillDetailBean;

import java.util.List;

public class DrillDitailListAdapter extends AbsBaseAdapter<DrillDetailBean.DrillDetailListbean> {

	private List<DrillDetailBean.DrillDetailListbean> mList;
	private Context mContext;

	public DrillDitailListAdapter(Context context, List<DrillDetailBean.DrillDetailListbean> list, int resourceId) {
		super(context, list, resourceId);
		mList = list;
		mContext = context;
	}

	@Override
	protected View newView(View convertView, DrillDetailBean.DrillDetailListbean t, int position) {
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.top = convertView.findViewById(R.id.important_work_child_top);
			holder.middle = convertView.findViewById(R.id.important_work_child_middle);
			holder.bottom = convertView.findViewById(R.id.important_work_child_bottom);
			holder.content = (TextView) convertView.findViewById(R.id.important_work_child_content);
			holder.date = (TextView) convertView.findViewById(R.id.important_work_child_date);
			convertView.setTag(holder);
		}
		holder.content.setText(mList.get(position).getDrillDesc());
		holder.date.setText(mList.get(position).getFollowTime());
		if (position == 0) {
			holder.top.setVisibility(View.INVISIBLE);
		} else {
			holder.top.setVisibility(View.VISIBLE);
		}

		if (position == mList.size() - 1) {
			holder.bottom.setVisibility(View.INVISIBLE);
			holder.middle.setBackgroundResource(R.drawable.oval_double_green);
			holder.content.setTextColor(mContext.getResources().getColor(R.color.color_lightgreen));
			holder.date.setTextColor(mContext.getResources().getColor(R.color.color_lightgreen));
		} else {
			holder.bottom.setVisibility(View.VISIBLE);
			holder.middle.setBackgroundResource(R.drawable.oval_gray);
			holder.content.setTextColor(mContext.getResources().getColor(R.color.color_dark));
			holder.date.setTextColor(mContext.getResources().getColor(R.color.color_dark));
		}
		return convertView;
	}

	private static class ViewHolder {
		private View top, middle, bottom;
		private TextView content, date;
	}

}
